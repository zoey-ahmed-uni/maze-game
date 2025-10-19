package io.github.eng1team3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    private Texture playerFrontTexture;
    private Texture playerBackTexture;
    private Texture playerLeftTexture;
    private Texture playerRightTexture;

    private Sprite playerFrontSprite;
    private Sprite playerBackSprite;
    private Sprite playerLeftSprite;
    private Sprite playerRightSprite;

    private Sprite activeSprite;

    private float playerX;
    private float playerY;

    private MapObjects objects;

    @Override
    public void create() {
        String mapFilePath = "map/testTileMap.tmx";
        map = new TmxMapLoader().load(mapFilePath);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 9);

        batch = new SpriteBatch();
        viewport = new FitViewport(16, 9, camera);

        playerFrontTexture = new Texture("FrontView.png");
        playerBackTexture = new Texture("BackView.png");
        playerRightTexture = new Texture("RightView.png");
        playerLeftTexture = new Texture("LeftView.png");

        playerFrontSprite = new Sprite(playerFrontTexture);
        playerBackSprite = new Sprite(playerBackTexture);
        playerRightSprite = new Sprite(playerRightTexture);
        playerLeftSprite= new Sprite(playerLeftTexture);

        playerFrontSprite.setSize(1f, 1f);
        playerBackSprite.setSize(1f, 1f);
        playerRightSprite.setSize(1f, 1f);
        playerLeftSprite.setSize(1f, 1f);

        playerX = viewport.getWorldWidth() / 2f - playerFrontSprite.getWidth() / 2f;
        playerY = viewport.getWorldHeight() / 2f - playerFrontSprite.getHeight() / 2f;

        playerFrontSprite.setPosition(playerX, playerY);
        playerBackSprite.setPosition(playerX, playerY);
        playerRightSprite.setPosition(playerX, playerY);
        playerLeftSprite.setPosition(playerX, playerY);

        activeSprite = playerFrontSprite;

        objects = map.getLayers().get("Collision").getObjects();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();
        float deltaX = 0;
        float deltaY = 0;
        Sprite newActiveSprite = activeSprite;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newActiveSprite = playerRightSprite;
            deltaX = speed * delta;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newActiveSprite = playerLeftSprite;
            deltaX = -speed * delta;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            newActiveSprite = playerBackSprite;
            deltaY = speed * delta;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newActiveSprite = playerFrontSprite;
            deltaY = -speed * delta;
        }

        if (deltaX != 0 || deltaY != 0) {
            Rectangle futureHitBox = new Rectangle(
                activeSprite.getX() + deltaX,
                activeSprite.getY() + deltaY,
                1f,
                1f
            );

            activeSprite = newActiveSprite;
            if (!checkCollision(futureHitBox)) {
                activeSprite.translateX(deltaX);
                activeSprite.translateY(deltaY);
                camera.translate(deltaX, deltaY);
            }
        }
    }

    private boolean checkCollision(Rectangle hitBox) {
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();

            Rectangle scaledRectangle = new Rectangle(
                rectangle.x / 16f,
                rectangle.y / 16f,
                rectangle.width / 16f,
                rectangle.height / 16f
            );

            if (Intersector.overlaps(scaledRectangle, hitBox)) {
                return true;
            }
        }
        return false;
    }

    private void logic() {}

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        mapRenderer.setView(camera);
        mapRenderer.render();

        float playerCenterX = activeSprite.getX() + activeSprite.getWidth() / 2f;
        float playerCenterY = activeSprite.getY() + activeSprite.getHeight() / 2f;

        viewport.getCamera().position.set(playerCenterX, playerCenterY, 0);
        viewport.getCamera().update();

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        playerX = activeSprite.getX();
        playerY = activeSprite.getY();

        batch.begin();

        playerFrontSprite.setPosition(playerX, playerY);
        playerBackSprite.setPosition(playerX, playerY);
        playerLeftSprite.setPosition(playerX, playerY);
        playerRightSprite.setPosition(playerX, playerY);

        activeSprite.draw(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerFrontTexture.dispose();
        playerBackTexture.dispose();
        playerRightTexture.dispose();
        playerLeftTexture.dispose();

        map.dispose();
        mapRenderer.dispose();
    }
}
