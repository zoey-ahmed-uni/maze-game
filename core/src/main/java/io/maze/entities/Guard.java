package io.maze.entities;

public class Guard extends Entity{
    public Guard(float x, float y) {
        super("guard/front.png",
            "guard/back.png",
            "guard/right.png",
            "guard/left.png"
        );

        this.setX(x);
        this.setY(y);
        this.updateSpritePositions();

        this.speed = 3f;

    }
}
