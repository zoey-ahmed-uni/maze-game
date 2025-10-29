package io.maze.entities;

public class Player extends Entity {

    private float spawnX;
    private float spawnY;

    public Player() {
        super("player/front.png",
            "player/back.png",
            "player/right.png",
            "player/left.png"
        );

        spawnX = 0f;
        spawnY = 0f;
    }

    public void setSpawnPoint(float x, float y){
        this.spawnX = x;
        this.spawnY = y;
    }

    public void respawn(){
        this.setX(spawnX);
        this.setY(spawnY);
        updateSpritePositions();
    }

}
