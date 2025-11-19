package io.maze.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.maze.core.CollisionChecker;
import io.maze.core.Main;
import io.maze.entities.Guard;
import io.maze.entities.Player;
import io.maze.objects.Exam;

/**
 * Represents the game screen of the application.
 *
 * @see com.badlogic.gdx.Screen Screen
 */
public class GameScreen implements Screen {
    final Main game;

    private final FitViewport viewport;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;

    private final Player player;

    private final MapObjects collisionObjects, objectObjects, checkpointObjects, finishObjects;
    private List<String> reachedCheckpoints;

    private ArrayList<Guard> guards;

    private final ArrayList<Exam> exams;
    private List<String> completedExamNames;

    private Boolean isPaused = false;

    private int score, positiveEvents, negativeEvents, hiddenEvents;

    private final FitViewport hudViewport;
    private final OrthographicCamera hudCamera;
    private final BitmapFont font;
    private float timeLeft;

    /**
     * Instantiates a new Game Screen.
     *
     * @param game the game is passed in every time we create a new
     *             screen in order to access the spritebatch
     *
     */

    public GameScreen(final Main game){
        this.game = game;

        this.map = new TmxMapLoader().load("map/map.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);

        this.collisionObjects = map.getLayers().get("collisions").getObjects();
        this.objectObjects = map.getLayers().get("objects").getObjects();
        this.checkpointObjects = map.getLayers().get("checkpoints").getObjects();
        this.finishObjects = map.getLayers().get("finish").getObjects();

        camera = new OrthographicCamera();
        camera.zoom = 0.5f;
        camera.setToOrtho(false, 16, 9);

        viewport = new FitViewport(16,9,camera);

        player = new Player();

        //new scale factor to fit the 50x50 map instead of the old 30x30
        //scale formula = old x/y x (50/30)
        guards = new ArrayList<>() {
            {
                add(new Guard(24.17f, 47.5f, false, 0.5f));
                add(new Guard(21.67f, 46.6f, false, 0.5f));
                add(new Guard(16.67f, 45.83f, false, 0.5f));
                add(new Guard(14.17f, 45f, false, 0.5f));
                add(new Guard(24.17f, 40.83f, false, 3f));
                add(new Guard(20.83f, 38.33f, false, 3f));
                add(new Guard(17.5f, 35.83f, false, 3f));
                add(new Guard(18.33f, 35.83f, true, 3f));
                add(new Guard(6.67f, 27.5f, true, 5f));
                add(new Guard(6.67f, 26.67f, false, 1f));
                add(new Guard(8.33f, 27.5f, false, 1f));
                add(new Guard(10.83f, 18.33f, false, 1.5f));
                add(new Guard(15f, 14.17f, true, 4f));
                add(new Guard(23.33f, 5f, false, 3f));
                add(new Guard(28.33f, 6.67f, false, 3f));
            }
        };

        exams = new ArrayList<>(){
            {
                add(new Exam("exam1"));
                add(new Exam("exam2"));
                add(new Exam("exam3"));
                add(new Exam("exam4"));
                add(new Exam("exam5"));
                add(new Exam("exam6"));
                add(new Exam("exam7"));
                add(new Exam("exam8"));
                add(new Exam("exam9"));
                add(new Exam("exam10"));
            }
        };

        for (Exam exam: exams) {
            exam.setPosition(objectObjects);
        }

        completedExamNames = new ArrayList<>();

        score = 0;
        positiveEvents = 0;
        negativeEvents = 0;
        hiddenEvents = 0;

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, 16, 9);
        hudViewport = new FitViewport(16,9,hudCamera);
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.03f);

        timeLeft = 300f;
    }

    public void unPause(){
        this.isPaused = false;
    }

    @Override
    public void pause(){
        this.isPaused = true;
    }

    public int getScore(){
        return this.score;
    }

    @Override
    public void show(){

    }

    /**
     * Sets the screen's new width and height.
     *
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * @param delta the delta time
     */
    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1 / 30f);
        // Press ESC to pause
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.pause();
            game.setScreen(new PauseScreen(game, this)); // pass current screen
            return; // don’t update game logic this frame
        }

        input(delta);
        logic(delta);
        draw();
    }
    /**
     * Determines what actions to perform on a given input from the player.
     * <p>
     * Mainly handles player movement, but additionally interaction with {@link io.maze.objects.Exam exams}.
     */
    private void input(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            // Set the player's velocity
            // Set the sprite corresponding to the direction of key press down
            player.setActiveSprite(player.getBackSprite());
            player.setDeltaY(player.getSpeed() * delta);
            player.setDeltaX(0);

            if (!CollisionChecker.isColliding(player, collisionObjects, completedExamNames)) {
                player.getActiveSprite().translateY(player.getDeltaY());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setActiveSprite(player.getFrontSprite());
            player.setDeltaY(-player.getSpeed() * delta);
            player.setDeltaX(0);

            if (!CollisionChecker.isColliding(player, collisionObjects, completedExamNames)) {
                player.getActiveSprite().translateY(player.getDeltaY());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setActiveSprite(player.getLeftSprite());
            player.setDeltaX(-player.getSpeed() * delta);
            player.setDeltaY(0);

            if (!CollisionChecker.isColliding(player, collisionObjects, completedExamNames)) {
                player.getActiveSprite().translateX(player.getDeltaX());
            }

        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setActiveSprite(player.getRightSprite());
            player.setDeltaX(player.getSpeed() * delta);
            player.setDeltaY(0);

            if (!CollisionChecker.isColliding(player, collisionObjects, completedExamNames)) {
                player.getActiveSprite().translateX(player.getDeltaX());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            for (Exam exam: exams) {
                if (CollisionChecker.isColliding(player, exam) && !exam.isCompleted()) {
                    exam.setCompleted();
                    score+=100;
                    completedExamNames.add(exam.getName());
                    positiveEvents++;
                }
            }
        }
    }
    /**
     * Handles the various events that occur throughout the game such as:
     * <ul>
     * <li> checkpoints
     * <li> guard movement
     * <li> timer
     * <li> game completion
     * <li> game failure
     * </ul>
     */
    private void logic(float delta) {

        if (CollisionChecker.isColliding(player, checkpointObjects)) {
            player.setSpawnPoint(player.getX(), player.getY());
            hiddenEvents++;
        }

        if (CollisionChecker.isColliding(player, finishObjects)) {
            score += (int)timeLeft;
            game.setScreen(new WinScreen(game, this));
        }

        for (Guard guard : guards) {
            if (CollisionChecker.isColliding(player, guard)) {
                player.respawn();
                score-=10;
                negativeEvents++;
            }
        }
        // NPC movement
        // true if moving up, false if moving down
        for (Guard guard : guards) {
            if (guard.isMovingHorizontally()) {
                if (guard.getSpeed() < 0) {
                    guard.setActiveSprite(guard.getLeftSprite());
                } else {
                    guard.setActiveSprite(guard.getRightSprite());
                }

                guard.setDeltaX(guard.getSpeed() * delta);
                guard.setDeltaY(0);
                guard.getActiveSprite().translateX(guard.getDeltaX());

            } else {
                if (guard.getSpeed() < 0) {
                    guard.setActiveSprite(guard.getFrontSprite());
                } else {
                    guard.setActiveSprite(guard.getBackSprite());
                }

                guard.setDeltaY(guard.getSpeed() * delta);
                guard.setDeltaX(0);
                guard.getActiveSprite().translateY(guard.getDeltaY());

            }
            if (CollisionChecker.isColliding(guard, collisionObjects)){
                guard.setSpeed(-guard.getSpeed());
            }
        }

        if (timeLeft > 0) {
            timeLeft -= delta;
        }
        else {
            game.setScreen(new GameOverScreen(game));
        }
    }

    /** Draws map, text, characters and objects to screen. Additionally, updates camera movement.*/
    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        mapRenderer.setView(camera);
        mapRenderer.render();

        viewport.getCamera().position.set(player.getCenterX(), player.getCenterY(), 0);
        viewport.getCamera().update();

        viewport.apply();
        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);

        player.setX(player.getActiveSprite().getX());
        player.setY(player.getActiveSprite().getY());

        game.getBatch().begin();

        player.updateSpritePositions();

        player.getActiveSprite().draw(game.getBatch());

        for (Guard guard : guards) {
            guard.setX(guard.getActiveSprite().getX());
            guard.setY(guard.getActiveSprite().getY());
            guard.updateSpritePositions();
            guard.getActiveSprite().draw(game.getBatch());
        }

        for (Exam exam: exams){
            exam.getSprite().draw(game.getBatch());
        }

        game.getBatch().end();

        hudViewport.apply();
        game.getBatch().setProjectionMatrix(hudCamera.combined);
        game.getBatch().begin();
        font.draw(game.getBatch(), "Time Left: " + (int)timeLeft + "s", 12f, 8f);
        font.draw(game.getBatch(),  "Positive Events: " + (positiveEvents + ""), 1f, 8f);
        font.draw(game.getBatch(),  "Negative Events: " + (negativeEvents + ""), 1f, 7.5f);
        font.draw(game.getBatch(),  "Hidden Events: " + (hiddenEvents + ""), 1f, 7f);
        game.getBatch().end();
    }

    @Override
    public void dispose() {
        player.dispose();
        map.dispose();
        mapRenderer.dispose();
        for (Guard guard : guards) {
            guard.dispose();
        }
        for (Exam exam: exams){
            exam.dispose();
        }
        font.dispose();
    }
}
