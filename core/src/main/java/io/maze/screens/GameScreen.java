package io.maze.screens;

import com.badlogic.gdx.maps.MapObjects;
import io.maze.core.CollisionChecker;
import io.maze.entities.Guard;
import io.maze.entities.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.maze.core.Main;
import io.maze.objects.Exam;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    final Main game;

    private final FitViewport viewport;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;

    private final Player player;

    private final MapObjects collisionObjects;
    private final MapObjects objectObjects;
    private final MapObjects checkpointObjects;

    private final Guard badGuard;

    private boolean isMovingUpDown;

    private final ArrayList<Exam> exams;
    private List<String> completedExamNames;

    private int score;

    public GameScreen(final Main game){
        this.game = game;

        this.map = new TmxMapLoader().load("map/testTileMap.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
        this.collisionObjects = map.getLayers().get("Collision").getObjects();
        this.objectObjects = map.getLayers().get("Objects").getObjects();
        this.checkpointObjects = map.getLayers().get("Checkpoints").getObjects();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 9);

        viewport = new FitViewport(16,9,camera);

        player = new Player();

        player.setX(viewport.getWorldWidth() / 2f - 1 / 2f);
        player.setY(viewport.getWorldHeight() - 1f);
        player.setSpawnPoint(player.getX(), player.getY());

        player.updateSpritePositions();

        badGuard = new Guard();

        badGuard.setX(viewport.getWorldWidth() + 14f);
        badGuard.setY(viewport.getWorldHeight());

        badGuard.updateSpritePositions();

        isMovingUpDown = true;

        exams = new ArrayList<>(){
            {
                add(new Exam("test1"));
                add(new Exam("test2"));
                add(new Exam("test3"));
                add(new Exam("test4"));
                add(new Exam("test5"));
                add(new Exam("test6"));
                add(new Exam("test7"));
                add(new Exam("test8"));
                add(new Exam("test9"));
            }
        };

        for (Exam exam: exams) {
            exam.setPosition(objectObjects);
        }

        completedExamNames = new ArrayList<>();

        score = 0;
    }

    @Override
    public void show(){

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    private void input() {
        float delta = Gdx.graphics.getDeltaTime();

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
                    score++;
                    completedExamNames.add(exam.getName());
                }
            }
        }
    }

    private void logic() {
        float delta = Gdx.graphics.getDeltaTime();

        if (CollisionChecker.isColliding(player, checkpointObjects)) {
            player.setSpawnPoint(player.getX(), player.getY());
        }

        if (CollisionChecker.isColliding(player, badGuard)) {
            player.respawn();
        }
        // NPC movement
        // true if moving up, false if moving down
        if (isMovingUpDown){
            badGuard.setActiveSprite(badGuard.getBackSprite());
            badGuard.setDeltaY(badGuard.getSpeed() * delta);
            badGuard.setDeltaX(0);
            badGuard.getActiveSprite().translateY(badGuard.getDeltaY());

            if (CollisionChecker.isColliding(badGuard, collisionObjects)){
                isMovingUpDown = false;
            }

        } else{
            badGuard.setActiveSprite(badGuard.getFrontSprite());
            badGuard.setDeltaY(-badGuard.getSpeed() * delta);
            badGuard.setDeltaX(0);
            badGuard.getActiveSprite().translateY(badGuard.getDeltaY());

            if (CollisionChecker.isColliding(badGuard, collisionObjects)){
                isMovingUpDown = true;
            }
        }
    }

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

        badGuard.setX(badGuard.getActiveSprite().getX());
        badGuard.setY(badGuard.getActiveSprite().getY());

        game.getBatch().begin();

        player.updateSpritePositions();
        badGuard.updateSpritePositions();

        player.getActiveSprite().draw(game.getBatch());
        badGuard.getActiveSprite().draw(game.getBatch());

        for (Exam exam: exams){
            exam.getSprite().draw(game.getBatch());
        }

        game.getBatch().end();
    }

    @Override
    public void dispose() {
        game.getBatch().dispose();
        player.dispose();

        map.dispose();
        mapRenderer.dispose();
    }
}
