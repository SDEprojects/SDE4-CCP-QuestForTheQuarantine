package com.codebusters.game;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private String name;
    private ArrayList<Items> inventory;
    private int money = 0;

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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    // BUSINESS METHODS
    public boolean addToInventory(Items item) {
        try {
            if (inventory.contains(item)) {
                for (Items x : inventory) {
                    if (x.equals(item)){
                        x.setCount(x.getCount() + item.getCount());
                        break;
                    }
                }
            }
            else {
                inventory.add(item);
            }

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
