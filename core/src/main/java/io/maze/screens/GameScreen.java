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
import io.maze.core.Inventory;
import io.maze.core.InventoryItem;
import io.maze.core.Main;
import io.maze.entities.Guard;
import io.maze.entities.Player;
import io.maze.objects.Exam;
import io.maze.objects.Locker;

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

    private ArrayList<Locker> lockers;
    private Inventory inventory;
    private BitmapFont inventoryFont;

    private float speedBoostTimer = 0f;
    private boolean hasSpeedBoost = false;
    private float origninalPlayerSpeed;

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
        
        this.lockers = new ArrayList<>() {{
            add(new Locker("locker1"));
            add(new Locker("locker2"));
            add(new Locker("locker3"));
            add(new Locker("locker4"));
            add(new Locker("locker5"));
            add(new Locker("locker6"));
            add(new Locker("locker7"));
        }};

        this.inventory = new Inventory(3);
        this.inventoryFont = new BitmapFont();
        this.inventoryFont.getData().setScale(0.03f);

        //position lockers
        for (Locker locker : lockers) {
            locker.setPosition(objectObjects);
        }

        camera = new OrthographicCamera();
        camera.zoom = 0.5f;
        camera.setToOrtho(false, 16, 9);

        viewport = new FitViewport(16,9,camera);

        player = new Player();
        this.origninalPlayerSpeed = player.getSpeed(); //set the player's base speed

        guards = new ArrayList<>() {
            {
                //(x, y, horixzontal, speed)
                //rescale using * 1.5f which is 45/30 to fit the new 45x45 tiled map
                //add(new Guard(14.5f *1.5f, 28.5f *1.5f, false, 0.5f));
                add(new Guard(10f, 10f, false, 0.5f));

                //add(new Guard(13f *1.5f, 28f *1.5f, false, 0.5f));
                //add(new Guard(10f *1.5f, 27.5f *1.5f, false, 0.5f));
                //add(new Guard(8.5f *1.5f, 27f *1.5f, false, 0.5f));
                //add(new Guard(14.5f *1.5f, 24.5f *1.5f, false, 3f));
                //add(new Guard(12.5f *1.5f, 23f *1.5f, false, 3f));
                //add(new Guard(10.5f *1.5f, 21.5f *1.5f, false, 3f));
                //add(new Guard(11f *1.5f, 21.5f *1.5f, true, 3f));
                //add(new Guard(4f *1.5f, 16.5f *1.5f, true, 5f));
                //add(new Guard(4f *1.5f, 16f *1.5f, false, 1f));
                //add(new Guard(5f *1.5f, 16.5f *1.5f, false, 1f));
                //add(new Guard(6.5f *1.5f, 11f *1.5f, false, 1.5f));
                //add(new Guard(9f *1.5f, 8.5f *1.5f, true, 4f));
                //add(new Guard(14f *1.5f, 3f *1.5f, false, 3f));
                //add(new Guard(17f *1.5f, 4f *1.5f, false, 3f));
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
        completedExamNames.clear();

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

        //inventory item usage with the number keys 1-3
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            int usedItem = inventory.useItem(0);
            if (usedItem != InventoryItem.EMPTY) {
                applyItemEffect(usedItem);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            int usedItem = inventory.useItem(1);
            if (usedItem != InventoryItem.EMPTY) {
                applyItemEffect(usedItem);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            int usedItem = inventory.useItem(2);
            if (usedItem != InventoryItem.EMPTY) {
                applyItemEffect(usedItem);
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
            //handling locker interaction event
            for (Locker locker: lockers) {
                if (CollisionChecker.isColliding(player, locker) && !locker.isOpened()) {
                    //if the locker hasn't already been opened and the player is colliding with it then gain an item in inventory
                    int item = locker.open();
                    if (item != InventoryItem.EMPTY){
                        if (inventory.addItem(item)) {
                            //add item to inventory
                            System.out.println("Added item to inventory: " + item);
                        } else {
                            System.out.println("Inventory full!");
                        }
            
                    }
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
        //update speed boost timer
        if (hasSpeedBoost) {
            speedBoostTimer -= delta;
            if (speedBoostTimer <= 0) {
                player.setSpeed(origninalPlayerSpeed);
            }
        }

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

        //first draw the game world
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

        //draw HUD elements last (on top of everything else)
        hudViewport.apply();
        game.getBatch().setProjectionMatrix(hudCamera.combined);
        game.getBatch().begin();

        font.draw(game.getBatch(), "Time Left: " + (int)timeLeft / 60 + ":" + (int)timeLeft % 60, 12f, 8f);
        font.draw(game.getBatch(),  "Positive Events: " + (positiveEvents + ""), 1f, 8f);
        font.draw(game.getBatch(),  "Negative Events: " + (negativeEvents + ""), 1f, 7.5f);
        font.draw(game.getBatch(),  "Hidden Events: " + (hiddenEvents + ""), 1f, 7f);

        //draw inventory
        for (int i = 0; i < inventory.getSlots().size(); i++) {
            float xPos = 6.8f + i * 1f; //position each slot 1 unit apart
            float slotSize = 0.8f; //the size of each inventory slot

            //draw the white slot background first

            //draw the item texture using the same size as the slot size to rescale it
            game.getBatch().draw(inventory.getSlots().get(i).getTexture(), xPos, 1f, slotSize, slotSize);
            
            //draw slot numbers
            inventoryFont.draw(game.getBatch(), String.valueOf(i+1), xPos + 0.1f, 1.7f);
        }
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

    private void applyItemEffect(int itemType) {
        switch (itemType) {
            case InventoryItem.APPLE:
                //increase the player's speed temporarily
                hasSpeedBoost = true;
                speedBoostTimer = 10f; //speed boost for 10 seconds
                player.setSpeed(player.getSpeed() + 2f);
            case InventoryItem.COOKIE:
                //EXAMPLE -> give the player some time back
                timeLeft += 30f;
                break;
        }
    }
}
