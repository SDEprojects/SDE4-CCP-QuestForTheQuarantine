package com.codebusters.game;
/**
 * TextParser.java takes the input from the player and checks it against valid
 * commands. If invalid, it sets a bool value which Game.java can then check.
 * Otherwise it updates with the next chapter and inventory changes, which
 * Game.java can then pull.
 * <p>
 * Authors: Bradley Pratt & Debbie Bitencourt
 * Last Edited: 01/31/2021
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class TextParser {
//    private final ArrayList<String> ITEM_VERBS_GAIN;
    private String nextChapter;
    private Chapter currentChapter;
    private boolean validInput;
    private ArrayList<Items> itemsToAdd;
    private ArrayList<Items> itemsToRemove;
    private ArrayList<Items> inventory;
    private static TextParser instance = null;

    private TextParser(){
//        nextChapter = new Chapter();
        currentChapter = new Chapter();
        setValidInput(false);
        itemsToAdd = new ArrayList<>();
        itemsToRemove = new ArrayList<>();
        inventory = new ArrayList<>();
//         ITEM_VERBS_GAIN = new ArrayList<>();
//         ITEM_VERBS_GAIN.add("take");
//         ITEM_VERBS_GAIN.add("grab");
//         ITEM_VERBS_GAIN.add("pickup");
//         ITEM_VERBS_GAIN.add("open");
    }

    public static TextParser getInstance(){
        if (instance == null){
            instance = new TextParser();
        }
        return instance;
    }

    public void parseInput(String input){
        setValidInput(false);   // reset value each time we check

        // check for empty input
        if (!input.equals("")){
            // set delims to remove common typing mistakes
            String delims = " \t,.:;?!\"'";
            StringTokenizer parse = new StringTokenizer(input.toLowerCase(), delims);
            ArrayList<String> command = new ArrayList<>();

            // separate input into individual words
            while (parse.hasMoreTokens()){
                command.add(parse.nextToken());
            }

            // needs to be a two-word input (for now) in the form of verb + noun
            if (command.size() == 2){
                String verb = command.get(0);
                String noun = command.get(1);

                //get current path list
                ArrayList<HashMap> paths = currentChapter.getPaths();
                for (HashMap path: paths){
                    // if we have a valid input
                    if (verb.equals(path.get("verb")) && noun.equals(path.get("noun"))){
                        // first, we check for required items
                        if (!(path.get("requiredItems") == null)){
//                            String[] reqItems = path.get("requiredItems").split(",");
                            ArrayList<String> reqItems = (ArrayList<String>) path.get("requiredItems");

                            // we need to make sure at least one required item is in inventory
                            for (String item: reqItems){
                                checkAgainstInventory(item.toLowerCase());

                                if (isValidInput()){     // no need to keep searching for others, just need one
                                    break;
                                }
                            }

                            // if a required item is found, then proceed, otherwise invalid input
                            if (isValidInput()){
                                logInventoryChanges(path);
                            }
                        // else no required items and valid input: just add the items
                        }else{
                            logInventoryChanges(path);
                        }
                    }
                }
            }
        }
    }

    //***************HELPER METHODS***************
    private void logInventoryChanges(HashMap<String, String> path){
        setValidInput(true);
        nextChapter = path.get("nextId");

        if (!(path.get("gainItems") == null)){
            String[] gainItems = path.get("gainItems").split(",");
            for (String item : gainItems){
                itemsToAdd.add(new Items(item));
            }
        }
        if (!(path.get("loseItems") == null)){
            String[] loseItems = path.get("loseItems").split(",");
            for (String item : loseItems) {
                itemsToRemove.add(new Items(item));
            }
        }
    }

    private void checkAgainstInventory(String item){
        for (Items possession: inventory){
            if (item.equals(possession.getName().toLowerCase())){
                setValidInput(true);
            }
        }
    }

    //***************GETTERS/SETTERS***************
    public String getNextChapter() {
        return nextChapter;
    }

    private void setNextChapter(String nextChapter) {
        this.nextChapter = nextChapter;
    }

    private Chapter getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(Chapter currentChapter, ArrayList<Items> inventory) {
        this.currentChapter = currentChapter;
        this.inventory = inventory;
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
