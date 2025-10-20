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

public class Main extends Game {

    private SpriteBatch batch;

    @Override
    public void create(){
        batch = new SpriteBatch();
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render(){
        super.render();
    }

    public SpriteBatch getBatch(){
        return this.batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }




}
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
