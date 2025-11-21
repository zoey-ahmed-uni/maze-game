package io.maze.core;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents an item that can be stored in the player's inventory
 * This is a data class 
 */

public class InventoryItem {
    public static final int EMPTY = 0;
    public static final int APPLE = 1;
    public static final int COOKIE = 2;

    private int type;
    private Texture texture;

    public InventoryItem(int type) {
        this.type = type;

        switch (type) {
            case APPLE:
                texture = new Texture("apple.png");
                break;
            case COOKIE:
                texture = new Texture("cookie.png");
                break;
            default:
                texture = new Texture("emptySlot.png");
                type = EMPTY;
        }
    }

    //Constructor for empty slot
    public InventoryItem(){
        this(EMPTY);
    }

    public int getType() {
        return type;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isEmpty() {
        return type == EMPTY;
    }

    public void dispose() {
        texture.dispose();
    }
}