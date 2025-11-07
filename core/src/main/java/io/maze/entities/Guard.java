package io.maze.entities;

/** The Guard class represents a negative non-playable character within the game. */
public class Guard extends Entity{
    private final boolean horizontal;

    public Guard(float x, float y, boolean horizontal, float speed) {
        super("guard/front.png",
            "guard/back.png",
            "guard/right.png",
            "guard/left.png"
        );

        this.setX(x);
        this.setY(y);
        this.updateSpritePositions();

        this.horizontal = horizontal;

        this.speed = speed;

    }

    /** {@return true if guard is moving horizontally} */
    public boolean isMovingHorizontally() {
        return this.horizontal;
    }
}
