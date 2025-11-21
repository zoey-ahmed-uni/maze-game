package io.maze.entities;

/**
 * The Player class represents the character that the player is manipulating.
 */
public class Player extends Entity {

    private float spawnX;
    private float spawnY;

    public Player() {
        super("player/front.png",
            "player/back.png",
            "player/right.png",
            "player/left.png"
        );

        //update scale for the 50x50 map
        this.setX(14.5f);
        this.setY(14f);
        updateSpritePositions();
        this.setSpawnPoint(this.getX(), this.getY());
    }

    /**
     * Sets the spawn position of the character at the start of the game.
     *
     * @param x the starting x coordinate
     * @param y the starting y coordinate
     */
    public void setSpawnPoint(float x, float y){
        this.spawnX = x;
        this.spawnY = y;
    }

    /** Sets the player character position back to its spawn coordinates. */
    public void respawn(){
        this.setX(spawnX);
        this.setY(spawnY);
        updateSpritePositions();
    }

}
