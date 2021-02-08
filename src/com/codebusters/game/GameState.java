package com.codebusters.game;
/**
 * GameState.java represents the current gamestate of the ongoing game.
 * It stores the current scene text and inventory, which the GUI can then
 * access to display the interface for the player. It is updated by Game.java.
 * <p>
 * Authors: Bradley Pratt & Debbie Bitencourt
 * Last Edited: 01/29/2021
 */

import java.io.*;
import java.util.ArrayList;

public class GameState implements Serializable {
    private String sceneText;
    private String sceneTitle;
    private ArrayList<Items> inventory;
    private static GameState instance = null;

    private GameState(){
        setSceneText("");
        setSceneTitle("");
        inventory = new ArrayList<>();
    }

    public GameState(GameState game) {
        instance = game;
    }

    //create method to save the game
    public static boolean saveGame(File fileToSave) {
        try {
            FileOutputStream fileStream = new FileOutputStream(fileToSave.getAbsolutePath());
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(GameState.getInstance());
            objectStream.close();
            return true;
        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean loadGame(File fileToLoad) {
        try {
            FileInputStream fileStream = new FileInputStream(fileToLoad.getAbsolutePath());
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            GameState loadedGame = (GameState) objectStream.readObject();
            new GameState(loadedGame);
            objectStream.close();
            return true;
        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        return false;
    }


    //make GameState as a Singleton to be used in other Classes.
    public static GameState getInstance(){
        if (instance == null){
            instance = new GameState();
        }
        return instance;
    }
    //*************** GETTERS/SETTERS ***************
    public String getSceneText() {
        return sceneText;
    }

    public void setSceneText(String sceneText) {
        this.sceneText = sceneText;
    }

    public String getSceneTitle() {
        return sceneTitle;
    }

    public void setSceneTitle(String sceneTitle) {
        this.sceneTitle = sceneTitle;
    }

    public ArrayList<Items> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Items> inventory) {
        this.inventory = inventory;
    }
}
