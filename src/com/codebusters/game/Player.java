package com.codebusters.game;

import java.security.PublicKey;
import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Items> inventory;

    public Player() {
        inventory = new ArrayList<>();
    }

    // GETTERS/SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Items> getInventory() {
        return inventory;
    }
    public void setInventory(ArrayList<Items> inventory) {
        this.inventory = inventory;
    }

    // BUSINESS METHODS
    public boolean addToInventory(Items item) {
        try {
            inventory.add(item);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean addToInventory(ArrayList<Items> itemsList) {
        try {
            inventory.addAll(itemsList);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean clearInventory(){
        try {
            inventory.clear();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean removeItemFromInventory(Items toLose) {
        // find the item in the inventory
        for (Items item : inventory) {
            if (item.getName().equals(toLose.getName())) {
                // if there is more quantity than the player loses, just update count
                if (item.getCount() > toLose.getCount()) {
                    item.setCount(item.getCount() - toLose.getCount());
                    // else remove item completely
                } else {
                    inventory.remove(item);
                }
                return true;
            }
        }
        return false;
    }
}
