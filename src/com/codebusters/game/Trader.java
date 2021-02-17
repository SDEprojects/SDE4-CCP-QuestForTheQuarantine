package com.codebusters.game;

import java.util.ArrayList;

public class Trader {
    private Player player;
    private static Trader instance = null;
    private ArrayList<Items> shop = new ArrayList<>();
    private final double exchangeRate = 0.8;
    private final int shopInventoryCount = 999;


    public static Trader getInstance() {
        if (instance == null) {
            instance = new Trader(Game.player);
        }
        return instance;
    }

    private Trader(Player player) {
        this.player = player;
        populateShop();
    }

    private void populateShop() {
        shop.add(new Items("matches", shopInventoryCount, 5));
        shop.add(new Items("water", shopInventoryCount, 15));
        shop.add(new Items("flashlight", shopInventoryCount, 10));
        shop.add(new Items("machete", shopInventoryCount, 50));
        shop.add(new Items("food", shopInventoryCount, 20));
        shop.add(new Items("pistol", shopInventoryCount, 100));
        shop.add(new Items("ammo", shopInventoryCount, 50));
        shop.add(new Items("firecrackers", shopInventoryCount, 25));
        shop.add(new Items("pan", shopInventoryCount, 25));
        shop.add(new Items("medkit", shopInventoryCount, 20));
        shop.add(new Items("knife", shopInventoryCount, 75));
        shop.add(new Items("ax", shopInventoryCount, 75));
    }

    public ArrayList<Items> getShop() {
        return shop;
    }

    public boolean itemPlayerIsBuying(Items itemToBuy) {
        try{
            //Gets price
            int itemValue = 0;
            for (Items items : shop) {
                if (items.equals(itemToBuy)){
                    itemValue = items.getValue();
                }
            }
            //Subtracts money
            subMoney(itemValue);
            //Adds item to player inventory
            player.addToInventory(itemToBuy);
            //Success!
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean itemPlayerIsSelling(Items itemToSell) {
        try{
            //Gets price
            int itemValue = 0;
            for (Items items : shop) {
                if (items.equals(itemToSell)){
                    itemValue = items.getValue();
                }
            }
            //Convert
            int convertedPrice = (int) ((double) itemValue * exchangeRate);
            //Player gets money
            addMoney(convertedPrice);
            //Itemremoved from player inventory
            player.removeItemFromInventory(itemToSell);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addMoney(int amountToAdd) {
        try {
            player.setMoney(player.getMoney()+amountToAdd);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean subMoney(int amountToSub) {
        // Cant be negative money
        if (amountToSub > player.getMoney()) {
            // player doesn't have enough money
            return false;
        }
        else {
            try {
                player.setMoney(player.getMoney() + amountToSub);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
