package io.maze.screens;

import io.maze.core.CollisionChecker;
import io.maze.entities.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.maze.core.Main;

public class GameScreen implements Screen {
    final Main game;

    private final FitViewport viewport;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;

    private final Player player;

    private final MapObjects mapObjects;

    public GameScreen(final Main game){
        this.game = game;

        String mapFilePath = "map/testTileMap.tmx";
        this.map = new TmxMapLoader().load(mapFilePath);
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
        this.mapObjects = map.getLayers().get("Collision").getObjects();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 9);

        viewport = new FitViewport(16,9,camera);

        player = new Player();

        player.setX(viewport.getWorldWidth() / 2f - 1 / 2f);
        player.setY(viewport.getWorldHeight() / 2f - 1 / 2f);

        player.getFrontSprite().setPosition(player.getX(), player.getY());
        player.getBackSprite().setPosition(player.getX(), player.getY());
        player.getRightSprite().setPosition(player.getX(), player.getY());
        player.getLeftSprite().setPosition(player.getX(), player.getY());
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

            if (!CollisionChecker.isColliding(player, mapObjects)) {
                player.getActiveSprite().translateY(player.getDeltaY());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setActiveSprite(player.getFrontSprite());
            player.setDeltaY(-player.getSpeed() * delta);
            player.setDeltaX(0);

            if (!CollisionChecker.isColliding(player, mapObjects)) {
                player.getActiveSprite().translateY(player.getDeltaY());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setActiveSprite(player.getLeftSprite());
            player.setDeltaX(-player.getSpeed() * delta);
            player.setDeltaY(0);

            if (!CollisionChecker.isColliding(player, mapObjects)) {
                player.getActiveSprite().translateX(player.getDeltaX());
            }

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setActiveSprite(player.getRightSprite());
            player.setDeltaX(player.getSpeed() * delta);
            player.setDeltaY(0);

            if (!CollisionChecker.isColliding(player, mapObjects)) {
                player.getActiveSprite().translateX(player.getDeltaX());
            }
        }
    }

    private void logic() {}

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        mapRenderer.setView(camera);
        mapRenderer.render();

        float playerCenterX = player.getActiveSprite().getX() + player.getActiveSprite().getWidth() / 2f;
        float playerCenterY = player.getActiveSprite().getY() + player.getActiveSprite().getHeight() / 2f;

        viewport.getCamera().position.set(playerCenterX, playerCenterY, 0);
        viewport.getCamera().update();

        viewport.apply();
        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);

        player.setX(player.getActiveSprite().getX());
        player.setY(player.getActiveSprite().getY());

        game.getBatch().begin();

        player.getFrontSprite().setPosition(player.getX(), player.getY());
        player.getBackSprite().setPosition(player.getX(), player.getY());
        player.getLeftSprite().setPosition(player.getX(), player.getY());
        player.getRightSprite().setPosition(player.getX(), player.getY());

        player.getActiveSprite().draw(game.getBatch());

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
