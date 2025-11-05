package io.maze.entities;

public class Guard extends Entity{
    private final boolean horizontal;

    public Guard(float x, float y, boolean horizontal) {
        super("guard/front.png",
            "guard/back.png",
            "guard/right.png",
            "guard/left.png"
        );

        this.setX(x);
        this.setY(y);
        this.updateSpritePositions();

        this.horizontal = horizontal;

        this.speed = 3f;

    }

    public boolean isMovingHorizontally() {
        return this.horizontal;
    }
}
