package com.codebusters.game;
/**
 * Items class represents the individual items the player can have in their inventory.
 * Each items has a name, an id number for tracking in an internal database, and a count
 * which represents the current stock in the player's inventory.
 * <p>
 * Authors: Bradley Pratt & Debbie Bitencourt
 * Last Edited: 01/27/2021
 */

public class Items {
    private int idNumber;
    private String name;
    private int count;

    public Items(String name, int idNumber){
        setName(name);
        setIdNumber(idNumber);
        setCount(1);
    }

    public Items(String name, int idNumber, int count){
        setName(name);
        setIdNumber(idNumber);
        setCount(count);
    }

    public int getIdNumber() {
        return idNumber;
    }

    private void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
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
}
