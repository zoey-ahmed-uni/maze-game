package io.maze.objects;

import java.util.Random;

import io.maze.core.InventoryItem;

public class Locker extends Object {
    private boolean opened;
    private int containedItem; //apple or cookie

    public Locker(String name) {
        super(name, "locker.png");
        this.opened = false;

        Random random = new Random();
        if (random.nextFloat() < 0.7f) { //70% chance for the item
            containedItem = random.nextBoolean() ? InventoryItem.APPLE : InventoryItem.COOKIE;
        } else {
            containedItem = InventoryItem.EMPTY;
        }

    }

    public int open() {
        if (!opened) {
            opened = true;
            return containedItem;
        }
        return InventoryItem.EMPTY;
    }

    public boolean isOpened() {
        return opened;
    }

    public void dispose() {
        super.dispose();
    }
}