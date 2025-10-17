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
    private Texture playerLeftTexture;
    private Texture playerRightTexture;

    private Sprite playerFrontSprite;
    private Sprite playerBackSprite;
    private Sprite playerLeftSprite;
    private Sprite playerRightSprite;

    private Sprite activeSprite;

    private float startX;
    private float startY;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(16, 9);

        playerFrontTexture = new Texture("FrontView.png");

        playerFrontSprite = new Sprite(playerFrontTexture);

        playerFrontSprite.setSize(1f, 1f);

        startX = viewport.getWorldWidth() / 2f - playerFrontSprite.getWidth() / 2f;
        startY = viewport.getWorldHeight() / 2f - playerFrontSprite.getHeight() / 2f;

        playerFrontSprite.setPosition(startX, startY);

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
            activeSprite.translateX(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            activeSprite.translateX(-speed * delta); // move the bucket left
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            activeSprite.translateY(speed * delta); // move the bucket left
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            activeSprite.translateY(-speed * delta); // move the bucket left
        }
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float playerWidth = activeSprite.getWidth();
        float playerHeight = activeSprite.getHeight();

        playerFrontSprite.setX(MathUtils.clamp(playerFrontSprite.getX(), 0, worldWidth -  playerWidth));
        playerFrontSprite.setX(MathUtils.clamp(playerFrontSprite.getX(), 0, worldWidth -  playerWidth));
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        playerFrontSprite.draw(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerFrontTexture.dispose();
    }
}
