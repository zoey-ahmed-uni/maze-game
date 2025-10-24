package io.github.eng1team3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * The Main Game
 * pass this into any screen you intend on adding to access
 * the spritebatch
 */
public class Main extends Game {

    //Remember to change these 2 constants if messing with the window size in the config files
    //to avoid graphical issues
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private SpriteBatch batch;

    /**
     * called when application is first created.
     *
     * creates the spritebatch to be used by all screens
     * rather than creating a new one for each screen which
     * will increase GPU load and decrease performance
     * */
    @Override
    public void create(){
        batch = new SpriteBatch();
        this.setScreen(new MainMenu(this));
    }
    /**
     * Called when the application should render itself.
     * allows screen to update and draw things.
     * ApplicationListener already has render implemented
     * so we can just make a super call to let it handle this
     * */
    @Override
    public void render(){
        super.render();
    }

    /**
     * Get sprite batch.
     *
     * @return the sprite batch
     */
    public SpriteBatch getBatch(){
        return this.batch;
    }

    /**
     * Sets batch.
     *
     * @param batch the batch
     */
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    /**
     * Get width int.
     *
     * @return the width
     */
    public int getWidth(){
        return WIDTH;
    }

    /**
     * Get height int.
     *
     * @return the height
     */
    public int getHeight(){
        return HEIGHT;
    }



}
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
