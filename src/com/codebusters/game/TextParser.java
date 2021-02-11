package com.codebusters.game;
/**
 * TextParser.java takes the input from the player and checks it against valid
 * commands. If invalid, it sets a bool value which Game.java can then check.
 * Otherwise it updates with the next chapter and inventory changes, which
 * Game.java can then pull.
 * <p>
 * Authors: Bradley Pratt & Debbie Bitencourt
 * Last Edited: 02/09/2021
 */

import java.util.*;

public class TextParser {


    private static TextParser instance = null;
    private final ArrayList<String> ITEM_VERBS_GAIN = new ArrayList<>(
            Arrays.asList("take", "grab", "pickup", "grasp", "open", "confiscate", "seize", "snatch", "search")
    );
    private final ArrayList<String> ITEM_VERBS_LOSE = new ArrayList<>(
            Arrays.asList("trade", "drink", "eat", "consume", "feed", "imbibe", "swig", "down", "swill", "swallow", "ingest", "devour", "chew")
    );
    private final ArrayList<String> ITEM_VERBS_USE = new ArrayList<>(
            Arrays.asList("use", "light", "threaten", "utilize", "apply", "employ", "ignite", "burn", "intimidate", "bully", "terrorize", "frighten", "scare")
    );
    private final ArrayList<String> PLACES_VERBS_ENTRY = new ArrayList<>(
            Arrays.asList("enter", "search", "explore", "access", "look", "invade", "forage")
    );
    private final ArrayList<String> PLACES_VERBS_EXIT = new ArrayList<>(
            Arrays.asList("leave", "exit", "run", "walk", "escape", "depart", "retreat", "retire")
    );
    private final ArrayList<String> PLACES_NOUNS_1 = new ArrayList<>(
            Arrays.asList("city", "town", "village", "settlement", "around")
    );
    private final ArrayList<String> PLACES_NOUNS_2 = new ArrayList<>(
            Arrays.asList("store", "bodega", "market", "shop")
    );
    private final ArrayList<String> PLACES_NOUNS_3 = new ArrayList<>(
            Arrays.asList("apartment", "flat", "house", "home", "condo", "hut")
    );
    private final ArrayList<String> PLACES_NOUNS_4 = new ArrayList<>(
            Arrays.asList("bedroom", "room", "suite")
    );
    private final ArrayList<String> PLACES_NOUNS_5 = new ArrayList<>(
            Arrays.asList("cabinet", "cabinets")
    );
    private final ArrayList<String> VERBS1 = new ArrayList<>(
            Arrays.asList("go", "skip")
    );
    private final ArrayList<String> VERBS2 = new ArrayList<>(
            Arrays.asList("start", "begin")
    );
    private String nextChapter;
    private String pathText;
    private String invalidInputMessage;
    private Chapter currentChapter;
    private boolean validInput;
    private boolean hasPathText;
    private ArrayList<Items> itemsToAdd;
    private ArrayList<Items> itemsToRemove;
    private ArrayList<Items> inventory;

    private TextParser() {
        currentChapter = new Chapter();
        setValidInput(false);
        setPathText(false);
        itemsToAdd = new ArrayList<>();
        itemsToRemove = new ArrayList<>();
        inventory = new ArrayList<>();
    }

    //make TextParser a Singleton to be used in other Classes.
    public static TextParser getInstance() {
        if (instance == null) {
            instance = new TextParser();
        }
        return instance;
    }

    public void parseInput(String input) {
        setValidInput(false);   // reset value each time we check
        setPathText(false);
        setInvalMessage("");

        // check for empty input
        if (!input.equals("")) {
            // set delims to remove common typing mistakes
            String delims = " \t,.:;?!\"'";
            StringTokenizer parse = new StringTokenizer(input.toLowerCase(), delims);
            ArrayList<String> command = new ArrayList<>();

            // separate input into individual words
            while (parse.hasMoreTokens()) {
                command.add(parse.nextToken());
            }

            // needs to be a two-word input (for now) in the form of verb + noun
            if (command.size() == 2) {
                String verb = command.get(0);
                String noun = command.get(1);

                //get current path list
                ArrayList<HashMap> paths = currentChapter.getPaths();
                for (HashMap path : paths) {
                    String reqNoun = (String) path.get("noun");
                    String reqVerb = (String) path.get("verb");
                    reqNoun = reqNoun.replaceAll(" ", "");
                    reqVerb = reqVerb.replaceAll(" ", "");

                    // if we have a valid input
                    if ((verb.equals(reqVerb) || isSynonym(reqVerb, verb)) && (noun.equals(reqNoun) || isSynonym(reqNoun, noun))) {
                        // first, we check for required items
                        if (!(path.get("requiredItems") == null)) {
                            checkRequiredItems(path);

                            // if a required item is found, then proceed, otherwise invalid input
                            if (isValidInput()) {
                                logInventoryChanges(path);
                            }
                            // else no required items and valid input: just add the items
                        } else {
                            logInventoryChanges(path);
                            setValidInput(true);
                        }

                        if (!(path.get("pathText") == null)) {
                            setPathText((String) path.get("pathText"));
                            setPathText(true);
                        }
                    }
                }
            }
        }
    }

    //***************HELPER METHODS***************
    private void setInvalMessage(String noun) {
        if (!noun.equals("")) {
            String error = "You need to have a " + noun + " in your inventory to perform this action!";
            setInvalidInputMessage(error);
        } else {
            String invalid = "That is an unrecognized input, please try again.";
            setInvalidInputMessage(invalid);
        }
    }

    private boolean isSynonym(String reqWord, String word) {
        return ((ITEM_VERBS_GAIN.contains(reqWord) && ITEM_VERBS_GAIN.contains(word))
                || (ITEM_VERBS_LOSE.contains(reqWord) && ITEM_VERBS_LOSE.contains(word))
                || (ITEM_VERBS_USE.contains(reqWord) && ITEM_VERBS_USE.contains(word))
                || (PLACES_VERBS_ENTRY.contains(reqWord) && PLACES_VERBS_ENTRY.contains(word))
                || (PLACES_VERBS_EXIT.contains(reqWord) && PLACES_VERBS_EXIT.contains(word))
                || (PLACES_NOUNS_1.contains(reqWord) && PLACES_NOUNS_1.contains(word))
                || (PLACES_NOUNS_2.contains(reqWord) && PLACES_NOUNS_2.contains(word))
                || (PLACES_NOUNS_3.contains(reqWord) && PLACES_NOUNS_3.contains(word))
                || (PLACES_NOUNS_4.contains(reqWord) && PLACES_NOUNS_4.contains(word))
                || (PLACES_NOUNS_5.contains(reqWord) && PLACES_NOUNS_5.contains(word))
                || (VERBS1.contains(reqWord) && VERBS1.contains(word))
                || (VERBS2.contains(reqWord) && VERBS2.contains(word))
        );
    }

    private void checkRequiredItems(HashMap<String, String> path) {
        String[] reqItems = path.get("requiredItems").split(",");

        // we need to make sure at least one required item is in inventory
        for (String item : reqItems) {
            checkAgainstInventory(item.toLowerCase());

            if (isValidInput()) { // no need to keep searching for others, just need one
                break;
            } else {
                setInvalMessage(item);
            }
        }
    }

    private void logInventoryChanges(HashMap<String, String> path) {
        setValidInput(true);
        nextChapter = path.get("nextId");
        itemsToAdd.clear();
        itemsToRemove.clear();

        //check if "gainItems" are in the path chosen
        //loop through the items in the list. When found matching item add it to itemsToAdd list.
        if (!(path.get("gainItems") == null)) {
            String[] gainItems = path.get("gainItems").split(",");
            for (String item : gainItems) {
                itemsToAdd.add(new Items(item));
            }
        }
        //check if "loseItems" are in the path chosen
        //loop through the items in the list. When found matching item add it to itemsToRemove list.
        if (!(path.get("loseItems") == null)) {
            String[] loseItems = path.get("loseItems").split(",");
            for (String item : loseItems) {
                itemsToRemove.add(new Items(item));
            }
        }
    }

    //validate if item is inside the inventory list.
    private void checkAgainstInventory(String item) {
        for (Items possession : inventory) {
            if (item.equals(possession.getName().toLowerCase())) {
                setValidInput(true);
                break;
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

    public String getPathText() {
        return pathText;
    }

    private void setPathText(String pathText) {
        this.pathText = pathText;
    }

    private void setPathText(boolean pathText) {
        hasPathText = pathText;
    }

    public String getInvalidInputMessage() {
        return invalidInputMessage;
    }

    private void setInvalidInputMessage(String invalidInputMessage) {
        this.invalidInputMessage = invalidInputMessage;
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

    public boolean hasPathText() {
        return hasPathText;
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

    public ArrayList<String> getITEM_VERBS_GAIN() {
        return ITEM_VERBS_GAIN;
    }

    public ArrayList<String> getITEM_VERBS_LOSE() {
        return ITEM_VERBS_LOSE;
    }

    public ArrayList<String> getITEM_VERBS_USE() {
        return ITEM_VERBS_USE;
    }

    public ArrayList<String> getPLACES_VERBS_ENTRY() {
        return PLACES_VERBS_ENTRY;
    }

    public ArrayList<String> getPLACES_VERBS_EXIT() {
        return PLACES_VERBS_EXIT;
    }

    public ArrayList<String> getVERBS1() {
        return VERBS1;
    }

    public ArrayList<String> getVERBS2() {
        return VERBS2;
    }
}
