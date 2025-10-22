package io.maze.entities;

public class EvilNPC extends Entity{
    public EvilNPC() {
        super("evilnpc/front.png",
            "evilnpc/back.png",
            "evilnpc/right.png",
            "evilnpc/left.png"
        );

        this.speed = 3f;
       
    }
}
