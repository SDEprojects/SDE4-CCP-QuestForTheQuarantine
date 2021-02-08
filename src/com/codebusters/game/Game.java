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
import java.util.ArrayList;

public class Game {
    private final Viewer GUI;
    private final ArrayList<Items> inventory;
    private ArrayList<Chapter> story;

    public Game() {
        story = new ArrayList<>();
        ChapterBuilder builder = new ChapterBuilder();
        story = builder.getChapters();
        GUI = new Viewer();
        inventory = new ArrayList<>();
    }

    //*************** METHODS ***************
    public void startGame(){
        boolean endGame = false;
        Chapter currentChapter = story.get(0);
        updateGameState(currentChapter);

        while (!endGame){
            TextParser.getInstance().setCurrentChapter(currentChapter, inventory);

            // tell viewer to display now that there is a new gamestate
            GUI.updateViewer();

            //create flag to wait for user's input from GUI
            while (GUI.isWaitingForInput()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }

            // check if viewer sent valid input to test parser
            if (!TextParser.getInstance().isValidInput()){
                // if not, we need to tell the player to try again
                promptAgain(currentChapter);
            }else{
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

                updateGameState(currentChapter);
                //update story path when the path matches specified path in the TextParser
                if (TextParser.getInstance().hasPathText()){
                    updatePathText();
                }

                // last we check if we are now at the end chapter
                if (isEndChapter(currentChapter)){
                    endGame = true;
                }
            }
        }

        // display the end chapter
        updateGameState(currentChapter);
        GUI.updateViewer();
    }

    private void updatePathText() {
        // get the current scene text
        String currentText = GameState.getInstance().getSceneText();
        GameState.getInstance().setSceneText(TextParser.getInstance().getPathText() + "\n\n" + currentText);
    }

    private boolean isEndChapter(Chapter currentChapter) {
        //initiate "end" or "death" outcome
        String chapterName = currentChapter.getChapterName();
        return chapterName.equals("End") || chapterName.equals("Death");
    }

    private void updateInventory() {
        for (Items item: TextParser.getInstance().getItemsToAdd()){
            addItemToInventory(item);
        }
        //loop through all items in the TextParser and remove matched item
        for (Items item: TextParser.getInstance().getItemsToRemove()){
            removeItemFromInventory(item);
        }
    }

    private void promptAgain(Chapter currentChapter) {
        // get the current scene text
        String tryAgain = currentChapter.getSceneText();
        tryAgain += "\n\n";
        tryAgain += TextParser.getInstance().getInvalidInputMessage();
        GameState.getInstance().setSceneText(tryAgain);
    }

    private void updateGameState(Chapter currentChapter) {
        //update scene text, title, and inventory in the GameState class.
        GameState.getInstance().setSceneText(currentChapter.getSceneText());
        GameState.getInstance().setSceneTitle(currentChapter.getChapterName());
        GameState.getInstance().setInventory(inventory);
    }

    //***************INVENTORY ACCESSOR METHODS***************
    public boolean findItemInInventory(Items toFind){
        //loop through items in the inventory and find item by its name.
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
                    break;
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
