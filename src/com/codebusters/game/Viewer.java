package com.codebusters.game;
/**
 * Viewer.java is where using GUI the game can be experienced and played in a separate window.
 * The GUI is designed to be visually appealing and user friendly.
 * Player using the input field can progress through the game.
 * <p>
 * Author: Aliona (main GUI), Dustin (save/load).
 * Last Edited: 02/09/2021
 */

import com.codebusters.game.scene.HelpScene;
import com.codebusters.game.scene.StoreScene;
import com.codebusters.game.scene.StoryScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/*
 * Main display for the game
 */
public class Viewer implements ActionListener, KeyListener {
    private static final JFrame window = new JFrame();
    private final StoryScene storyScene; //main game scene
    private StoreScene storeScene; //store
    private HelpScene helpScene; //help scene
    private boolean isStoryScene = true; //tracks if story scene is currently displayed in window
    private boolean isHelpScene = false; //tracks if help scene is being displayed
    private boolean isStoreScene = false; //tracks if store scene is being displayed
    private final Game game;

    //Constructor
    public Viewer(Game game) {
        this.game = game;
        window.setSize(880, 690); //size for the frame
        window.setLocationRelativeTo(null); //window pops up in center of screen

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes the window
        window.addKeyListener(this);

        storyScene = new StoryScene();
        //add action listeners
        storyScene.getUserInputField().addActionListener(this);
        storyScene.getUserInputField().addKeyListener(this);
        storyScene.getInputBtn().addActionListener(this);
        storyScene.getHelpBtn().addActionListener(this);
        storyScene.getQuitBtn().addActionListener(this);
        storyScene.getSaveBtn().addActionListener(this);
        storyScene.getLoadBtn().addActionListener(this);

        setHelpScene();
        setStoreScene();

        //once everything is added pack() to make everything fit snugly into frame then set frame to visible
        window.add(storyScene.getMainPanel());
        window.pack();
        window.setVisible(true);
    }


    //*************** METHODS ***************
    /*
     * Updates the game scene to mach the current game state
     */
    public void updateViewer() {
        storyScene.updateDisplay();

        window.revalidate();
        window.repaint();
    }

    /*
     * Manages the transition from the main game scene to the help scene
     */
    public void helpWindowDisplay() {
        if (!isSceneOnWindow("help")) {
            window.add(helpScene.getHelpPanel());
        }
        else {
            helpScene.getHelpPanel().setVisible(true);
        }
        storyScene.getMainPanel().setVisible(false);
        isStoryScene = false;
        isHelpScene = true;
        window.repaint();
    }

    /*
     * Sets all action and key listeners to components on the help scene
     */
    private void setHelpScene() {
        helpScene = new HelpScene();
        //add action listeners to the buttons to display
        helpScene.getEntryBtn().addActionListener(this);
        helpScene.getExitBtn().addActionListener(this);
        helpScene.getGainBtn().addActionListener(this);
        helpScene.getLoseBtn().addActionListener(this);
        helpScene.getUseBtn().addActionListener(this);
        helpScene.getVerbsBtn().addActionListener(this);
        //adding key listener to all buttons, in case they are in focus
        for (Component comp : helpScene.getButtonPanel().getComponents()) {
            if (comp.getName().equals("button")) {
                comp.addKeyListener(this);
            }
        }
        //adding key listener to main help panel and components
        helpScene.getHelpPanel().addKeyListener(this);
        helpScene.getBackgroundImg().addKeyListener(this);
        helpScene.getHelpText().addKeyListener(this);
        helpScene.getHelpPanel().setFocusable(true);
        helpScene.getHelpPanel().requestFocusInWindow();
    }

    /*
     * Manages the transition from the help scene back to the main game scene
     */
    private void exitHelpAndEnterStory() {
        helpScene.getHelpPanel().setVisible(false);
        isHelpScene = false;
        isStoryScene = true;
        storyScene.getMainPanel().setVisible(true);
        storyScene.getUserInputField().requestFocusInWindow();
        window.repaint();
    }

    /*
     * Manages transition from store to main game
     */
    private void exitStoreAndEnterStory() {
        storeScene.getStorePanel().setVisible(false);
        isStoreScene = false;
        isStoryScene = true;
        storyScene.getMainPanel().setVisible(true);
        storyScene.getUserInputField().requestFocusInWindow();
        storyScene.updateDisplay();
        window.repaint();
    }

    /*
     * Creates a popup for the user to confirm they want to quit the game or not
     * If yes is selected game exits otherwise game continues
     */
    private void askToQuit() {
        int userChoice = JOptionPane.showOptionDialog(window, "Are you sure you want to quit?", "Exit Game",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Yes", "No"}, JOptionPane.YES_OPTION);

        if (userChoice == JOptionPane.YES_OPTION) {
            window.dispose();
            System.exit(0);
        }
    }

    /*
     * Creates a popup window that shows the list of available verbs that can be used in game
     * based on specific help button pressed
     */
    private void displayExamples(ArrayList<String> data, String buttonName) {
        //setting title for popup
        String title = buttonName.toLowerCase().equals("verbs") ? "Other Verbs" : buttonName + " Verbs";
        //formatting the list of available verbs
        StringBuilder sb = new StringBuilder();
        for (String verb : data) {
            if (verb.equals(data.get(data.size()-1))) {
                sb.append(verb);
            }
            else {
                sb.append(verb).append(", ");
            }
        }

        JOptionPane.showMessageDialog(window, sb.toString(), title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void enterStore() {
        if (!isSceneOnWindow("store")) {
            window.add(storeScene.getStorePanel());
        }
        else {
            storeScene.getStorePanel().setVisible(true);
        }

        storeScene.updateInventories();
        storyScene.getMainPanel().setVisible(false);
        isStoryScene = false;
        isStoreScene = true;
        window.repaint();
    }

    private void setStoreScene() {
        storeScene = new StoreScene();
        storeScene.getBuyBtn().addActionListener(this);
        storeScene.getSellBtn().addActionListener(this);
        storeScene.getBuyBtn().addKeyListener(this);
        storeScene.getSellBtn().addKeyListener(this);
        storeScene.getStorePanel().addKeyListener(this);
        storeScene.getStoreInventory().addKeyListener(this);
        storeScene.getUserInventory().addKeyListener(this);
        storeScene.getInventories().addKeyListener(this);
        storeScene.getStorePanel().setFocusable(true);
        storeScene.getStorePanel().requestFocusInWindow();
    }

    /*
     * Creates popup for user to input the name of the item they want to purchase from store
     * If that item exists in the store the transaction will be processed through the Trader class
     */
    private void buyFromStore() {
        String itemToBuy = JOptionPane.showInputDialog(window, "What would you like to buy?");
        ArrayList<Items> items = Trader.getInstance().getShop();
        for (Items item : items) {
            if (item.getName().equals(itemToBuy.toLowerCase())) {
               Trader.getInstance().itemPlayerIsBuying(item);
                break;
            }
        }
        storeScene.updateInventories();
    }

    /*
     * Creates a popup for user to input an Item of theirs they would like to sell
     * Validates they input an item that exists then passes the data off to the Trader to process
     * the transaction
     */
    private void sellFromStore() {
        String itemToSell = JOptionPane.showInputDialog(window, "What would you like to sell?");
        ArrayList<Items> userItems = Game.player.getInventory();
        for (Items item : userItems) {
            if (item.getName().equals(itemToSell.toLowerCase())) {
                Trader.getInstance().itemPlayerIsSelling(item);
                break;
            }
        }
        storeScene.updateInventories();
    }

    private boolean isSceneOnWindow(String sceneName) {
        boolean exists = false;

        for (Component component : window.getContentPane().getComponents()) {
            if (component.getName().equals(sceneName)) {
                exists = true;
            }
        }

        return exists;
    }

    //create load and save window and check for file being saved or loaded successfully
    private boolean saveOrLoadGame(String flag) {
        boolean saveOrLoadSuccessful;
        JFileChooser fileChooser;
        Path path = Paths.get("./saved_games");
        if (!Files.exists(path)) {
            File dir = new File("./saved_games");
            dir.mkdirs();
        }
        fileChooser = new JFileChooser("./saved_games");
        fileChooser.setDialogTitle("Specify name of game file to " + flag);
        int userSelection = flag.equals("save") ? fileChooser.showSaveDialog(window) : fileChooser.showOpenDialog(window);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            saveOrLoadSuccessful = flag.equals("save") ? GameState.saveGame(file) : GameState.loadGame(file);
        }else {
            return false;
        }
        return saveOrLoadSuccessful;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == storyScene.getUserInputField() || e.getSource() == storyScene.getInputBtn()) {
            String input = storyScene.getUserInputField().getText();
            //pass user input to be validated and game updated
            game.playerAction(input);

            storyScene.getUserInputField().setText("");
            //update the window to reflect any changes caused by user input
            updateViewer();
        }
        //when saveBtn is pressed pass "save" parameter to saveOrLoadGame method
        else if (e.getSource() == storyScene.getSaveBtn()) {
            saveOrLoadGame("save");
        }
        //when loadBtn is pressed pass "load" parameter to saveOrLoadGame method.
        else if (e.getSource() == storyScene.getLoadBtn()) {
            saveOrLoadGame("load");
            //updates viewer with the GameState from loaded game
            updateViewer();
            //updates the current chapter in the game to track the newly loaded GameState
            game.loadGame();
        }
        //when quitBtn is pressed the GUI window and game closes.
        else if (e.getSource() == storyScene.getQuitBtn()) {
            askToQuit();
        }
        else if (e.getSource() == storyScene.getHelpBtn()) {
            helpWindowDisplay();
        } else if (e.getSource() == helpScene.getGainBtn()) {
            displayExamples(TextParser.getInstance().getITEM_VERBS_GAIN(), helpScene.getGainBtn().getText());
        } else if (e.getSource() == helpScene.getLoseBtn()) {
            displayExamples(TextParser.getInstance().getITEM_VERBS_LOSE(), helpScene.getLoseBtn().getText());
        } else if (e.getSource() == helpScene.getUseBtn()) {
            displayExamples(TextParser.getInstance().getITEM_VERBS_USE(), helpScene.getUseBtn().getText());
        } else if (e.getSource() == helpScene.getEntryBtn()) {
            displayExamples(TextParser.getInstance().getPLACES_VERBS_ENTRY(), helpScene.getEntryBtn().getText());
        } else if (e.getSource() == helpScene.getExitBtn()) {
            displayExamples(TextParser.getInstance().getPLACES_VERBS_EXIT(), helpScene.getExitBtn().getText());
        } else if (e.getSource() == helpScene.getVerbsBtn()) {
            ArrayList<String> data = new ArrayList<>();
            data.addAll(TextParser.getInstance().getVERBS1());
            data.addAll(TextParser.getInstance().getVERBS2());
            displayExamples(data, helpScene.getVerbsBtn().getText());
        }
        else if (e.getSource() == storeScene.getBuyBtn()) {
            buyFromStore();
        }
        else if (e.getSource() == storeScene.getSellBtn()) {
            sellFromStore();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && (!isStoryScene && !isStoreScene)) {
            exitHelpAndEnterStory();
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && (!isStoryScene && !isHelpScene)) {
            exitStoreAndEnterStory();
        }
        else if (e.getKeyCode() == KeyEvent.VK_CONTROL && isStoryScene) {
            enterStore();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}