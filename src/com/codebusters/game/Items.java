package com.codebusters.game;
/**
 * Items class represents the individual items the player can have in their inventory.
 * Each items has a name, an id number for tracking in an internal database, and a count
 * which represents the current stock in the player's inventory.
 * <p>
 * Authors: Bradley Pratt & Debbie Bitencourt
 * Last Edited: 01/31/2021
 */
import java.io.Serializable; //for saving the game

public class Items implements Serializable {
    private String name;
    private int count;
    private int value;

    public Items(String name){
        setName(name);
        setCount(1);
    }

    public Items(String name, int count){
        setName(name);
        setCount(count);
    }
    public Items(String name, int count, int value){
        setName(name);
        setCount(count);
        setValue(value);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getName() + " x" + getCount();
    }
}
