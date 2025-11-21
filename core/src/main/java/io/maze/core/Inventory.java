package io.maze.core;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<InventoryItem> slots;

    public Inventory(int size) {
        slots = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            slots.add(new InventoryItem()); //empty slots
        }
    }

    public boolean addItem(int itemType) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).isEmpty()) {
                slots.set(i, new InventoryItem(itemType));
                return true;
            }
        }
        return false; //inventory is full
    }

    public int useItem(int slot) {
        //check if slot is valid and has an item in it
        if (slot >= 0 && slot < slots.size() && !slots.get(slot).isEmpty()) {
            //get what type of item is in the current slot
            int itemType = slots.get(slot).getType();
            //replace the current slot with an empty item
            slots.set(slot, new InventoryItem()); //make empty
            //return the itemt ype do the game knows which of the two effects to apply
            return itemType;
        }
        //if the slot was invalid or empty then simply return empty
        return InventoryItem.EMPTY;
    }

    public List<InventoryItem> getSlots() {
        return slots;
    }

    public boolean isFull() {
        for (InventoryItem item : slots) {
            if (item.isEmpty()) return false;
        }
        return true;
    }

    public void dispose() {
        for (InventoryItem item : slots) {
            item.dispose();
        }
    }
}