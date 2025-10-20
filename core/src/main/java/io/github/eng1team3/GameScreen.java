package io.github.eng1team3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    final Main game;

    private FitViewport viewport;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    private Player player;

    private MapObjects objects;

    public GameScreen(final Main game){
        this.game = game;

        String mapFilePath = "map/testTileMap.tmx";
        map = new TmxMapLoader().load(mapFilePath);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
        objects = map.getLayers().get("Collision").getObjects();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 9);

        viewport = new FitViewport(16,9,camera);


        player = new Player();

        player.x = viewport.getWorldWidth() / 2f - 1 / 2f;
        player.y = viewport.getWorldHeight() / 2f - 1 / 2f;

        player.frontSprite.setPosition(player.x, player.y);
        player.backSprite.setPosition(player.x, player.y);
        player.rightSprite.setPosition(player.x, player.y);
        player.leftSprite.setPosition(player.x, player.y);
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
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight(delta, objects);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft(delta, objects);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveUp(delta, objects);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveDown(delta, objects);
        }
    }

    private void logic() {}

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        mapRenderer.setView(camera);
        mapRenderer.render();

        float playerCenterX = player.activeSprite.getX() + player.activeSprite.getWidth() / 2f;
        float playerCenterY = player.activeSprite.getY() + player.activeSprite.getHeight() / 2f;

        viewport.getCamera().position.set(playerCenterX, playerCenterY, 0);
        viewport.getCamera().update();

        viewport.apply();
        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);

        player.x = player.activeSprite.getX();
        player.y = player.activeSprite.getY();

        game.getBatch().begin();

        player.frontSprite.setPosition(player.x, player.y);
        player.backSprite.setPosition(player.x, player.y);
        player.leftSprite.setPosition(player.x, player.y);
        player.rightSprite.setPosition(player.x, player.y);

        player.activeSprite.draw(game.getBatch());

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
