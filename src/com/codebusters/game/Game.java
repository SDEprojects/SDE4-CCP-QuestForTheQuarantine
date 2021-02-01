package com.codebusters.game;
/**
 * Game.java is the main game engine for Quest for the
 * Quarantine. It contains the startGame() method which
 * runs the game, continuously updating the game state,
 * then the GUI.
 * <p>
 * Authors: Bradley Pratt & Debbie Bitencourt
 * Last Edited: 01/31/2021
 */
import com.codebusters.data.ChapterBuilder;
import java.util.ArrayList;

public class Game {
    public GameState currentGame;
    public Viewer GUI;
    public TextParser parser;
    public ChapterBuilder builder;
    public ArrayList<Items> inventory;
    public ArrayList<Chapter> story;

    public Game() {
        currentGame = GameState.getInstance();
        GUI = new Viewer();
        parser = TextParser.getInstance();
        inventory = new ArrayList<>();
        story = new ArrayList<>();
        builder = new ChapterBuilder();
        story = builder.getChapters();
    }

    public void startGame(){
        boolean endGame = false;
        Chapter currentChapter = story.get(0);
        updateGameState(currentChapter);

        while (!endGame){
            parser.setCurrentChapter(currentChapter, inventory);

            // tell viewer to display now that there is a new gamestate
            GUI.updateViewer();
            System.out.println("Did you execute?");
            while (GUI.isWaitingForInput()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("How about now?");

            // check if viewer sent valid input to test parser
            if (!parser.isValidInput()){
                // if not, we need to tell the player to try again
                promptAgain();
            }else{
                // we need to update the inventory and current chapter
                updateInventory();
                String next = parser.getNextChapter();
                for (Chapter chapt: story){
                    if (chapt.getChapterId().equals(next)){
                        currentChapter = chapt;
                        break;
                    }
                }

                // update the current gamestate
                updateGameState(currentChapter);

                // last we check if we are now at the end chapter
                if (isEndChapter(currentChapter)){
                    endGame = true;
                }
            }
            System.out.println("Current inventory: "  + currentGame.getInventory());
        }

        // display the end chapter
        updateGameState(currentChapter);
        GUI.updateViewer();
    }

    private boolean isEndChapter(Chapter currentChapter) {
        String chapterName = currentChapter.getChapterName();
        return chapterName.equals("End") || chapterName.equals("Death");
    }

    private void updateInventory() {
        for (Items item: parser.getItemsToAdd()){
            addItemToInventory(item);
        }

        for (Items item: parser.getItemsToRemove()){
            removeItemFromInventory(item);
        }
    }

    private void promptAgain() {
        // get the current scene text
        String tryAgain = currentGame.getSceneText();
        // create message that there was an invalid input
        String invalid = "That is an unrecognized input, please try again. Only two word commands are allowed in the form of verb + noun.";

        // check if current scene already has the message, if so,
        // we don't need to add again
        if (!tryAgain.contains(invalid)){
            tryAgain += "\n\n";
            tryAgain += invalid;
        }
        currentGame.setSceneText(tryAgain);
    }

    private void updateGameState(Chapter currentChapter) {
        currentGame.setSceneText(currentChapter.getSceneText());
        currentGame.setSceneTitle(currentChapter.getChapterName());
        currentGame.setInventory(inventory);
    }

    //***************INVENTORY ACCESSOR METHODS***************
    public boolean findItemInInventory(Items toFind){
        for (Items item: inventory){
            if (item.getName().equals(toFind.getName())){
                return true;
            }
        }
        return false;
    }

    private void addItemToInventory(Items toAdd){
        // if the item is already in the inventory
        if (findItemInInventory(toAdd)){
            // then we find the item
            for (Items item: inventory){
                if (item.getName().equals(toAdd.getName())){
                    // and just add to its count
                    item.setCount(item.getCount() + toAdd.getCount());
                }
            }

        // else we just add the item to the inventory list
        }else{
            inventory.add(toAdd);
        }
    }

    private boolean removeItemFromInventory(Items toLose){
        // find the item in the inventory
        for (Items item: inventory){
            if (item.getName().equals(toLose.getName())){
                // if there is more quantity than the player loses, just update count
                if (item.getCount() > toLose.getCount()){
                    item.setCount(item.getCount() - toLose.getCount());
                // else remove item completely
                }else{
                    inventory.remove(item);
                }
                return true;
            }
        }

        // return false if item not found in inventory
        return false;
    }

}
