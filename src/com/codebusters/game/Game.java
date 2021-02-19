package com.codebusters.game;

/**
 * Game.java is the main game engine for Quest for the
 * Quarantine. It contains the startGame() method which
 * runs the game, continuously updating the game state,
 * then the GUI.
 * <p>
 * Authors: Bradley Pratt & Debbie Bitencourt
 * Last Edited: 02/05/2021
 */

import com.codebusters.data.ChapterBuilder;
import com.codebusters.game.util.DisplayError;

import java.util.ArrayList;

public class Game {
    private final Viewer GUI;
    // private final ArrayList<Items> inventory;
    private ArrayList<Chapter> story;
    private Chapter currentChapter;
    public static Player player;
    private MusicPlayer bgMusicPlayer = new MusicPlayer("Hotel California.wav");

    public Game() {
        story = new ArrayList<>();
        ChapterBuilder.getInstance();
        story = ChapterBuilder.getInstance().getChapters();
        currentChapter = story.get(0);
        GUI = new Viewer(this);
        player = new Player();
        bgMusicPlayer.setVolume(-15);
        bgMusicPlayer.startMusic();
        // inventory = new ArrayList<>();
    }

    //*************** METHODS ***************

    /*
     * sets the game state and text parser to the first chapter of the story
     * updates the GUI to reflect the beginning of the game
     * Must be called to begin the game
     */
    public void initialize() {
        updateGameState(currentChapter);
        TextParser.getInstance().setCurrentChapter(currentChapter, player.getInventory());
        GUI.updateViewer();
    }

    /*
     * Takes input from the user
     * updates the state of game to current chapter and inventory
     * passes input to TextParser for validation
     * updates game accordingly
     */
    public void playerAction(String input) {
        updateGameState(currentChapter);
        TextParser.getInstance().setCurrentChapter(currentChapter, player.getInventory());
        TextParser.getInstance().parseInput(input);

        // check if viewer sent valid input to test parser
        if (!TextParser.getInstance().isValidInput()){
            // if not, we need to tell the player to try again
            DisplayError.getInstance().errorPopup(GUI.getWindow(), TextParser.getInstance().getInvalidInputMessage(), "Input Error");
        }
        else {
            // we need to update the inventory and current chapter
            updateInventory();
            String next = TextParser.getInstance().getNextChapter();
            //loop through each chapter to get the next chapter.
            for (Chapter chapt: story){
                if (chapt.getChapterId().equals(next)){
                    currentChapter = chapt;
                    break;
                }
            }
            //keep game state updated with the current Chapter
            updateGameState(currentChapter);
            //update story path when the path matches specified path in the TextParser
            if (TextParser.getInstance().hasPathText()){
                updatePathText();
            }
        }
    }

    /*
     * If game is loaded GameState is updated with that saved games information
     * This method checks if the GameState is tracking a different current chapter than the game class
     * If the chapters are different, currentChapter is set to the chapter GameState reflects,
     * Inventory is also updated based on the GameStates data
     */
    public void loadGame() {
        if (!(GameState.getInstance().getSceneTitle().equals(currentChapter.getChapterName()))) {
            for (Chapter chapter : story) {
                if (chapter.getChapterName().equals(GameState.getInstance().getSceneTitle())) {
                    currentChapter = chapter;
                    player.clearInventory();
                    player.addToInventory(GameState.getInstance().getInventory());
                    player.setMoney(GameState.getInstance().getPlayer().getMoney());
                    break;
                }
            }
        }
    }

    private void updatePathText() {
        // get the current scene text
        String currentText = GameState.getInstance().getSceneText();
        GameState.getInstance().setSceneText(TextParser.getInstance().getPathText() + "\n\n" + currentText);
    }

    private void updateInventory() {
        for (Items item : TextParser.getInstance().getItemsToAdd()) {
            addItemToInventory(item);
        }
        //loop through all items in the TextParser and remove matched item
        for (Items item : TextParser.getInstance().getItemsToRemove()) {
            player.removeItemFromInventory(item);
        }
    }

    private void updateGameState(Chapter currentChapter) {
        //update scene text, title, and inventory in the GameState class.
        GameState.getInstance().setSceneText(currentChapter.getSceneText());
        GameState.getInstance().setSceneTitle(currentChapter.getChapterName());
        GameState.getInstance().getPlayer().setInventory(player.getInventory());
    }

    //***************INVENTORY ACCESSOR METHODS***************
    public boolean findItemInInventory(Items toFind) {
        //loop through items in the inventory and find item by its name.
        for (Items item : player.getInventory()) {
            if (item.getName().equals(toFind.getName())) {
                return true;
            }
        }
        return false;
    }

    private void addItemToInventory(Items toAdd) {
        // if the item is already in the inventory
        if (findItemInInventory(toAdd)) {
            // then we find the item
            for (Items item : player.getInventory()) {
                if (item.getName().equals(toAdd.getName())) {
                    // and just add to its count
                    item.setCount(item.getCount() + toAdd.getCount());
                    break;
                }
            }

            // else we just add the item to the inventory list
        } else {
            player.addToInventory(toAdd);
        }
    }

}
