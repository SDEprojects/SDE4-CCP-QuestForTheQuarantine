package com.codebusters.game;
/**
 * GameState.java represents the current gamestate of the ongoing game.
 * It stores the current scene text and inventory, which the GUI can then
 * access to display the interface for the player. It is updated by Game.java.
 * <p>
 * Authors: Bradley Pratt & Debbie Bitencourt
 * Last Edited: 01/28/2021
 */
import java.util.ArrayList;

public class GameState {
    private String sceneText;
    private ArrayList<Items> inventory;

    public GameState(){
        setSceneText("");
        inventory = new ArrayList<>();
    }

    public String getSceneText() {
        return sceneText;
    }

    public void setSceneText(String sceneText) {
        this.sceneText = sceneText;
    }

    public ArrayList<Items> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Items> inventory) {
        this.inventory = inventory;
    }
}
