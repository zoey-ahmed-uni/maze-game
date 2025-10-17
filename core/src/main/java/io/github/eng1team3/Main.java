package io.github.eng1team3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;

    private Texture playerFrontTexture;
    private Texture playerBackTexture;
    // private Texture playerLeftTexture;
    // private Texture playerRightTexture;

    private Sprite playerFrontSprite;
    private Sprite playerBackSprite;
    // private Sprite playerLeftSprite;
    // private Sprite playerRightSprite;

    private Sprite activeSprite;

    private float playerX;
    private float playerY;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(16, 9);

        playerFrontTexture = new Texture("FrontView.png");
        playerBackTexture = new Texture("BackView.png");
        // playerRightTexture = new Texture("RightView.png");
        // playerLeftTexture = new Texture("LeftView.png");

        playerFrontSprite = new Sprite(playerFrontTexture);
        playerBackSprite = new Sprite(playerBackTexture);
        // playerRightSprite = new Sprite(playerRightTexture);
        // playerLeftSprite= new Sprite(playerLeftTexture);

        playerFrontSprite.setSize(1f, 1f);
        playerBackSprite.setSize(1f, 1f);
        // playerRightSprite.setSize(1f, 1f);
        // playerLeftSprite.setSize(1f, 1f);

        playerX = viewport.getWorldWidth() / 2f - playerFrontSprite.getWidth() / 2f;
        playerY = viewport.getWorldHeight() / 2f - playerFrontSprite.getHeight() / 2f;

        playerFrontSprite.setPosition(playerX, playerY);
        playerBackSprite.setPosition(playerX, playerY);
        // playerRightSprite.setPosition(playerX, playerY);
        // playerLeftSprite.setPosition(playerX, playerY);

        activeSprite = playerFrontSprite;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            // activeSprite = playerRightSprite;
            activeSprite.translateX(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            // activeSprite = playerLeftSprite;
            activeSprite.translateX(-speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            activeSprite = playerBackSprite;
            activeSprite.translateY(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            activeSprite = playerFrontSprite;
            activeSprite.translateY(-speed * delta);
        }
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float playerWidth = activeSprite.getWidth();
        float playerHeight = activeSprite.getHeight();

        activeSprite.setX(MathUtils.clamp(activeSprite.getX(), 0f, worldWidth -  playerWidth));
        activeSprite.setY(MathUtils.clamp(activeSprite.getY(), 0f, worldHeight -  playerHeight));
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

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
        // playerLeftSprite.setPosition(playerX, playerY);
        // playerRightSprite.setPosition(playerX, playerY);

        activeSprite.draw(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerFrontTexture.dispose();
        playerBackTexture.dispose();
        // playerRightTexture.dispose();
        // playerLeftTexture.dispose();
    }
}
