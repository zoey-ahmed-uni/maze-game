package io.maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.maze.core.Main;

/**
 * Main Menu screen
 * has attributes for all the button images as well as its
 * own viewport.
 *
 * as a general rule, every screen should have its own viewport
 */
public class MainMenu implements Screen{

    final Main game;
    private final ExtendViewport viewport;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture settingsButtonActive;
    Texture settingsButtonInactive;

    /**
     * Instantiates a new Main menu.
     *
     * @param game the game is passed in every time we create a new
     *             screen in order to access the spritebatch
     *
     */
    public MainMenu(final Main game){
        this.game = game;
        this.viewport = new ExtendViewport(16,9);
        this.playButtonActive = new Texture("playActive.png");
        this.playButtonInactive = new Texture("playInactive.png");
        this.exitButtonActive = new Texture("exitActive.png");
        this.exitButtonInactive = new Texture("exitInactive.png");
        this.settingsButtonActive = new Texture("settingsActive.png");
        this.settingsButtonInactive = new Texture("settingsInactive.png");
    }

    @Override
    public void show() {

    }
    /**
     * called when the screen renders itself
     * handles the drawing of all the menu buttons
     * */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);

        game.getBatch().begin();

        final float exitButtonWidth = 5f;
        final float exitButtonHeight = 2f;
        final float exitButtonX = viewport.getWorldWidth() / 2f - exitButtonWidth / 2f;
        final float exitButtonY = 1f;


        final float settingsButtonWidth = 5f;
        final float settingsButtonHeight = 2f;
        final float settingsButtonX = viewport.getWorldWidth() / 2f - settingsButtonWidth / 2f;
        final float settingsButtonY = 5f;


        final float playButtonWidth = 5f;
        final float playButtonHeight = 2f;
        final float playButtonX = viewport.getWorldWidth() / 2f - playButtonWidth / 2f;
        final float playButtonY = 9f;

        //Convert world coordinates to screen coordinates
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.getCamera().unproject(touchPos);


        //three boolean values that turn True if the user is hovering over the specified button
        //each one takes the mouse position and compares it to the place on the screen where the
        //button is drawn
        boolean isHoveringPlay = touchPos.x < playButtonX + playButtonWidth &&
            touchPos.x > playButtonX &&
            touchPos.y < playButtonY + playButtonHeight &&
            touchPos.y > playButtonY;

        boolean isHoveringSettings = touchPos.x < settingsButtonX + settingsButtonWidth &&
            touchPos.x > settingsButtonX &&
            touchPos.y < settingsButtonY + settingsButtonHeight &&
            touchPos.y > settingsButtonY;

        boolean isHoveringExit = touchPos.x < exitButtonX + exitButtonWidth &&
            touchPos.x > exitButtonX &&
            touchPos.y < exitButtonY + exitButtonHeight &&
            touchPos.y > exitButtonY;

        //if the user is not hovering play:
        if (!isHoveringPlay){
            //draw the inactive play button
            game.getBatch().draw(playButtonInactive,playButtonX,playButtonY,playButtonWidth,playButtonHeight);
        }
        //otherwise:
        else{
            //draw the active play button
            game.getBatch().draw(playButtonActive,playButtonX,playButtonY,playButtonWidth,playButtonHeight);
        }

        if (!isHoveringSettings){
            game.getBatch().draw(settingsButtonInactive,settingsButtonX,settingsButtonY,settingsButtonWidth,settingsButtonHeight);
        }
        else{
            game.getBatch().draw(settingsButtonActive,settingsButtonX,settingsButtonY,settingsButtonWidth,settingsButtonHeight);
        }

        if (!isHoveringExit){
            game.getBatch().draw(exitButtonInactive,exitButtonX,exitButtonY,exitButtonWidth,exitButtonHeight);
        }
        else{
            game.getBatch().draw(exitButtonActive,exitButtonX,exitButtonY,exitButtonWidth,exitButtonHeight);
        }

        //if the user clicks:
        if (Gdx.input.justTouched()) {
            //on the play button
            if (isHoveringPlay) {
                //change the screen to the game
                game.setScreen(new GameScreen(game));
                dispose();
            }
            //on the settings button
            else if (isHoveringSettings) {
                // go to settings screen
            }
            //on the exit button
            else if (isHoveringExit) {
                //exit the application
                Gdx.app.exit();
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
