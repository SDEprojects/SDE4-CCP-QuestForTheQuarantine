package com.codebusters.game;
/**
 * GameState.java represents the current gamestate of the ongoing game.
 * It stores the current scene text and inventory, which the GUI can then
 * access to display the interface for the player. It is updated by Game.java.
 * <p>
 * Authors: Bradley Pratt & Debbie Bitencourt
 * Last Edited: 01/29/2021
 */
import java.util.ArrayList;

public class GameState {
    private String sceneText;
    private String sceneTitle;
    private ArrayList<Items> inventory;
    private static GameState instance = null;

    private GameState(){
        setSceneText("Esperanza could not remember the last time she had slept. It had been days, at least. The Violent Ones had started forming gangs in recent weeks, and the herd mentality only exacerbated their aggression. For her own safety, she slept at day and explored at night; the darkness hid her presence well in the vicinity of those who would do her harm.\n" +
                "\n" +
                "In the weeks following El Evento, the days were unsafe. They had learned that lesson the hard way with the death of Mama. She took refuge with her hermano in the night, and for months everything was okay. They even met others who had not succumbed, who were not Violent. They shared stories of past lives, better lives, and hope for salvation. Rumors of a safe zone in the United States, just outside San Diego, persisted among survivors. For a time, they had hope. But slowly, the survivors disappeared.\n" +
                "\n" +
                "And then one night, her brother was gone. She searched for days, tirelessly, to no avail. She found no trace of him. Meanwhile, the Violent Ones banded together, burning houses and rooting out what remained of survivors. It had been two weeks since her hermano had disappeared, and she knew he was dead. Unless she wanted to be the same, she needed to leave.\n" +
                "\n" +
                "So she decided to leave, to seek out the refuge of the quarantine in Los Estados Unidos. But first she needed to gather supplies. She slipped quietly into the night and made her way through the streets…\n");
        setSceneTitle("");
        inventory = new ArrayList<>();
    }

    public static GameState getInstance(){
        if (instance == null){
            instance = new GameState();
        }
        return instance;
    }

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
