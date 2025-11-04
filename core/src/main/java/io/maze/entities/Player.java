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

        this.setX(14.5f);
        this.setY(14f);
        updateSpritePositions();
        this.setSpawnPoint(this.getX(), this.getY());
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
