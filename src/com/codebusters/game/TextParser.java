package com.codebusters.game;
/**
 * TextParser.java takes the input from the player and checks it against valid
 * commands. If invalid, it sets a bool value which Game.java can then check.
 * Otherwise it updates with the next chapter and inventory changes, which
 * Game.java can then pull.
 * <p>
 * Authors: Bradley Pratt & Debbie Bitencourt
 * Last Edited: 01/28/2021
 */
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TextParser {
//    private final ArrayList<String> ITEM_VERBS_GAIN;
    private Chapter nextChapter;
    private Chapter currentChapter;
    private boolean validInput;
    private ArrayList<Items> itemsToAdd;
    private ArrayList<Items> itemsToRemove;

    public TextParser(){
        nextChapter = new Chapter();
        currentChapter = new Chapter();
        setValidInput(false);
        itemsToAdd = new ArrayList<>();
        itemsToRemove = new ArrayList<>();
//         ITEM_VERBS_GAIN = new ArrayList<>();
//         ITEM_VERBS_GAIN.add("take");
//         ITEM_VERBS_GAIN.add("grab");
//         ITEM_VERBS_GAIN.add("pickup");
//         ITEM_VERBS_GAIN.add("open");
    }

    public void parseInput(String input){
        if (input.equals("")){
            setValidInput(false);
        }else{
            String delims = " \t,.:;?!\"'";
            StringTokenizer parse = new StringTokenizer(input.toLowerCase(), delims);
            ArrayList<String> command = new ArrayList<>();

            while (parse.hasMoreTokens()){
                command.add(parse.nextToken());
            }

            if (command.size() != 2){
                setValidInput(false);
            }else{
                String verb = command.get(0);
                String noun = command.get(1);

                /*
                @TODO: we have the verb and the noun. We need to check it against list of acceptable ones for the current Chapter. Then take the appropriate action.
                 */
            }
        }
    }

    //***************GETTERS/SETTERS***************
    public Chapter getNextChapter() {
        return nextChapter;
    }

    private void setNextChapter(Chapter nextChapter) {
        this.nextChapter = nextChapter;
    }

    private Chapter getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(Chapter currentChapter) {
        this.currentChapter = currentChapter;
    }

    public boolean isValidInput() {
        return validInput;
    }

    private void setValidInput(boolean validInput) {
        this.validInput = validInput;
    }

    public ArrayList<Items> getItemsToAdd() {
        return itemsToAdd;
    }

    private void setItemsToAdd(ArrayList<Items> toAdd) {
        this.itemsToAdd = toAdd;
    }

    public ArrayList<Items> getItemsToRemove() {
        return itemsToRemove;
    }

    private void setItemsToRemove(ArrayList<Items> toRemove) {
        this.itemsToRemove = toRemove;
    }
}
