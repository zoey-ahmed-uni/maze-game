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
 * Tutorial screen
 * has attributes for all the button images as well as its
 * own viewport.
 *
 * Will be shown to the player before they start the game
 *
 * as a general rule, every screen should have its own viewport
 */
public class TutorialScreen implements Screen{

    private final Main game;
    private final MainMenu menu;
    private final BitmapFont font;
    private final ExtendViewport viewport;
    Texture escKey;
    Texture wKey;
    Texture aKey;
    Texture sKey;
    Texture dKey;
    Texture eKey;
    Texture backInactive;
    Texture backActive;
    Texture playButtonActive;
    Texture playButtonInactive;

    /**
     * Instantiates a new Tutorial Screen.
     *
     * @param game the game is passed in every time we create a new
     *             screen in order to access the spritebatch
     * @param menu passes in the Main Menu so that we can go back
     *             to menu if player is not ready to start game yet
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
     * called when the screen renders itself
     * handles the drawing of all the menu's buttons
     * */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.SLATE);

        viewport.apply();
        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);

        game.getBatch().begin();

        final float backButtonWidth = 5f;
        final float backButtonHeight = 2f;
        final float backButtonX = 1f;
        final float backButtonY = 1f;

        final float playButtonWidth = 5f;
        final float playButtonHeight = 2f;
        final float playButtonX = viewport.getWorldWidth() - 6f;
        final float playButtonY = 1f;

        final float wWidth = 1f;
        final float wHeight = 1f;
        final float wButtonX = 2f;
        final float wButtonY = 9f;

        final float aWidth = 1f;
        final float aHeight = 1f;
        final float aButtonX = 1f;
        final float aButtonY = 8f;

        final float sWidth = 1f;
        final float sHeight = 1f;
        final float sButtonX = 2f;
        final float sButtonY = 8f;

        final float dWidth = 1f;
        final float dHeight = 1f;
        final float dButtonX = 3f;
        final float dButtonY = 8f;

        final float eWidth = 1f;
        final float eHeight = 1f;
        final float eButtonX = 2f;
        final float eButtonY = 6f;

        final float escWidth = 1f;
        final float escHeight = 1f;
        final float escButtonX = 2f;
        final float escButtonY = 4f;


        //Convert world coordinates to screen coordinates
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.getCamera().unproject(touchPos);


        //boolean values that turn True if the user is hovering over the specified button
        //takes the mouse position and compares it to the place on the screen where the
        //button is drawn

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

        //if the user is not hovering back:
        if (!isHoveringBack){
            //draw inactive sprite
            game.getBatch().draw(backInactive, backButtonX, backButtonY,backButtonWidth,backButtonHeight);
        }
        //if they are
        else{
            //draw the active sprite
            game.getBatch().draw(backActive, backButtonX, backButtonY,backButtonWidth,backButtonHeight);
        }

        if (!isHoveringPlay){
            game.getBatch().draw(playButtonInactive, playButtonX, playButtonY,playButtonWidth,playButtonHeight);
        }
        else{
            game.getBatch().draw(playButtonActive, playButtonX, playButtonY,playButtonWidth,playButtonHeight);
        }

        //if the user clicks:
        if (Gdx.input.justTouched()) {
            //on the back button
            if (isHoveringBack) {
                //change the screen to the menu
                game.setScreen(menu);
                dispose();
            }
            //on the play button
            else if (isHoveringPlay) {
                //change the screen to the menu
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
        game.getBatch().end();
    }

    /**
     * called whenever the application is resized
     * updates how the viewport scales with screen pixels
     * to keep the aspect ratio consistent
     * */
    @Override
    public void resize(int i, int i1) {
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
