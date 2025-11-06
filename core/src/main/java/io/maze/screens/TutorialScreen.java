package io.maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.maze.core.Main;
/**
 * Contains attributes for all the button images as well as its
 * own viewport.
 * 
 * As a general rule, every screen should have its own viewport
 */
public class TutorialScreen implements Screen{

    private final Main game;
    private final MainMenu menu;
    private final BitmapFont font;
    private final ExtendViewport viewport;
    
    Texture escKey, wKey, aKey, sKey, dKey, eKey;
    Texture backInactive, backActive, playButtonActive, playButtonInactive;

    /* The game is passed in every time we create a new screen,
     * in order to access the spritebatch.
     */
    public TutorialScreen(final Main game, final MainMenu menu){
        this.game = game;
        this.menu = menu;
        this.font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.05f);
        this.viewport = new ExtendViewport(16,9);
        this.escKey = new Texture("menu/escKey.png");
        this.wKey = new Texture("menu/wKey.png");
        this.aKey = new Texture("menu/aKey.png");
        this.sKey = new Texture("menu/sKey.png");
        this.dKey = new Texture("menu/dKey.png");
        this.eKey = new Texture("menu/eKey.png");
        this.backActive = new Texture("menu/backActive.png");
        this.backInactive = new Texture("menu/backInactive.png");
        this.playButtonActive = new Texture("menu/playActive.png");
        this.playButtonInactive = new Texture("menu/playInactive.png");
    }

    @Override
    public void show() {

    }
    /**
     * Called when the screen renders itself, handles the drawing of all the menu buttons.
     * 
     * @param delta the delta time
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.SLATE);

        viewport.apply();
        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);

        game.getBatch().begin();

        // Setting the heights, widths and positions of the various buttons
        final float backButtonWidth = 5f, backButtonHeight = 2f, backButtonX = 1f, backButtonY = 1f;

        final float playButtonWidth = 5f, playButtonHeight = 2f;
        final float playButtonX = viewport.getWorldWidth() - 6f;
        final float playButtonY = 1f;

    
        final float wWidth = 1f, wHeight = 1f, wButtonX = 2f, wButtonY = 9f;
        final float aWidth = 1f, aHeight = 1f, aButtonX = 1f, aButtonY = 8f;
        final float sWidth = 1f, sHeight = 1f, sButtonX = 2f, sButtonY = 8f;
        final float dWidth = 1f, dHeight = 1f, dButtonX = 3f, dButtonY = 8f;
        final float eWidth = 1f, eHeight = 1f, eButtonX = 2f, eButtonY = 6f;
        final float escWidth = 1f, escHeight = 1f, escButtonX = 2f, escButtonY = 4f;


        // Convert world coordinates to screen coordinates
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.getCamera().unproject(touchPos);

        // TODO: Seperate this logic into a helper function 'isHovering(Button)'
        boolean isHoveringBack = touchPos.x < backButtonX + backButtonWidth &&
            touchPos.x > backButtonX &&
            touchPos.y < backButtonY + backButtonHeight &&
            touchPos.y > backButtonY;

        boolean isHoveringPlay = touchPos.x < playButtonX + playButtonWidth &&
            touchPos.x > playButtonX &&
            touchPos.y < playButtonY + playButtonHeight &&
            touchPos.y > playButtonY;

        game.getBatch().draw(wKey,wButtonX,wButtonY,wWidth,wHeight);
        game.getBatch().draw(aKey,aButtonX,aButtonY,aWidth,aHeight);
        game.getBatch().draw(sKey,sButtonX,sButtonY,sWidth,sHeight);
        game.getBatch().draw(dKey,dButtonX,dButtonY,dWidth,dHeight);


        font.draw(game.getBatch(), "use WASD to move",5f,9f);
        font.draw(game.getBatch(), "press E to interact",5f,6.75f);
        font.draw(game.getBatch(), "press ESC to pause",5f,4.75f);


        game.getBatch().draw(eKey,eButtonX,eButtonY,eWidth,eHeight);
        game.getBatch().draw(escKey,escButtonX,escButtonY,escWidth,escHeight);

        // If the user is not hovering back:
        if (!isHoveringBack){
            // Draw inactive sprite
            game.getBatch().draw(backInactive, backButtonX, backButtonY,backButtonWidth,backButtonHeight);
        }
        else{
            // Draw the active sprite
            game.getBatch().draw(backActive, backButtonX, backButtonY,backButtonWidth,backButtonHeight);
        }

        if (!isHoveringPlay){
            game.getBatch().draw(playButtonInactive, playButtonX, playButtonY,playButtonWidth,playButtonHeight);
        }
        else{
            game.getBatch().draw(playButtonActive, playButtonX, playButtonY,playButtonWidth,playButtonHeight);
        }

        // On click
        if (Gdx.input.justTouched()) {
            // On the back button
            if (isHoveringBack) {
                // Change the screen to the menu
                game.setScreen(menu);
                dispose();
            }

            else if (isHoveringPlay) {
                //change the screen to the menu
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
        game.getBatch().end();
    }

    /**
     * Called whenever the application is resized
     * <p>
     * Updates how the viewport scales with screen pixels to keep the aspect ratio consistent.
     * 
     * @param w width
     * @param h height
     */
    @Override
    public void resize(int w, int h) {
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //set the camera centrally
        viewport.getCamera().position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        viewport.getCamera().update();
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
    public void dispose() {

    }
}
