package io.maze.entities;

public class Guard extends Entity{
    public Guard() {
        super("guard/front.png",
            "guard/back.png",
            "guard/right.png",
            "guard/left.png"
        );

        this.speed = 3f;

    }
}
