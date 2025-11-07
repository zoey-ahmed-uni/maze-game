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
 * Game Over screen
 * has attributes for all the button images as well as its
 * own viewport.
 *
 * Will be displayed to the player when they run out of time in the maze
 *
 * as a general rule, every screen should have its own viewport
 */
public class GameOverScreen implements Screen{

    private final Main game;
    private final BitmapFont font;
    private final ExtendViewport viewport;
    Texture newGameInactive, newGameActive;
    Texture exitInactive, exitActive;

    /**
     * Instantiates a new Game Over Screen.
     *
     * @param game the game is passed in every time we create a new
     *             screen in order to access the spritebatch
     *
     */
    public GameOverScreen(final Main game){
        this.game = game;
        this.font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.15f);
        font.setColor(255, 0, 0, 1);
        this.viewport = new ExtendViewport(16,9);
        this.newGameActive = new Texture("menu/newGameActive.png");
        this.newGameInactive = new Texture("menu/newGameInactive.png");
        this.exitActive = new Texture("menu/exitActive.png");
        this.exitInactive = new Texture("menu/exitInactive.png");
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
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);

        game.getBatch().begin();

        final float exitButtonWidth = 5f;
        final float exitButtonHeight = 2f;
        final float exitButtonX = 1f;
        final float exitButtonY = 1f;

        final float newGameButtonWidth = 5f;
        final float newGameButtonHeight = 2f;
        final float newGameButtonX = viewport.getWorldWidth() - 6f;
        final float newGameButtonY = 1f;


        //Convert world coordinates to screen coordinates
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.getCamera().unproject(touchPos);


        //boolean value that turn True if the user is hovering over the specified button
        //takes the mouse position and compares it to the place on the screen where the
        //button is drawn

        boolean isHoveringExit = touchPos.x < exitButtonX + exitButtonWidth &&
            touchPos.x > exitButtonX &&
            touchPos.y < exitButtonY + exitButtonHeight &&
            touchPos.y > exitButtonY;

        boolean isHoveringNewGame = touchPos.x < newGameButtonX + newGameButtonWidth &&
            touchPos.x > newGameButtonX &&
            touchPos.y < newGameButtonY + newGameButtonHeight &&
            touchPos.y > newGameButtonY;


        //if the user is not hovering exit:
        if (!isHoveringExit){
            //draw inactive sprite
            game.getBatch().draw(exitInactive, exitButtonX, exitButtonY,exitButtonWidth,exitButtonHeight);
        }
        //if they are
        else{
            //draw the active sprite
            game.getBatch().draw(exitActive, exitButtonX, exitButtonY,exitButtonWidth,exitButtonHeight);
        }

        if (!isHoveringNewGame){
            game.getBatch().draw(newGameInactive, newGameButtonX, newGameButtonY,newGameButtonWidth,newGameButtonHeight);
        }
        else{
            game.getBatch().draw(newGameActive, newGameButtonX, newGameButtonY,newGameButtonWidth,newGameButtonHeight);
        }

        font.draw(game.getBatch(), "Game Over!",2f,9f);

        //if the user clicks:
        if (Gdx.input.justTouched()) {
            //on the back exit
            if (isHoveringExit) {
                //change the screen to the menu
                game.setScreen(new MainMenu(game));
                dispose();
            }
            //on the new game button
            else if (isHoveringNewGame) {
                //start a new game
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
