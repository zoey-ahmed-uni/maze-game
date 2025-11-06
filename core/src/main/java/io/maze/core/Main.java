package io.maze.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.maze.screens.MainMenu;

/**
 * The Main class provides a baseline functionality for the game.
 * <p>
 * Pass it into any screen you intend on adding to access the spritebatch.
 */
public class Main extends Game {

    // Update these 2 constants if changing window size in config to avoid graphical issues

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private SpriteBatch batch;

    /**
     * Creates the spritebatch to be used by all screens to improve performance.
     * */
    @Override
    public void create(){
        batch = new SpriteBatch();
        this.setScreen(new MainMenu(this));
    }

    /**
     * Allows screen to update and draw things.
     * <p>
     * Called when the {@link com.badlogic.gdx.ApplicationListener Application}
     * should render itself.
     * */
    @Override
    public void render(){
        super.render();
    }

    /**
     * Gets sprite batch.
     *
     * @return the sprite batch
     */
    public SpriteBatch getBatch(){
        return this.batch;
    }

    /**
     * Sets batch.
     *
     * @param batch
     */
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth(){
        return WIDTH;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight(){
        return HEIGHT;
    }

}
