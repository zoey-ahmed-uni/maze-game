package io.maze.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.maze.screens.GameScreen;

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
