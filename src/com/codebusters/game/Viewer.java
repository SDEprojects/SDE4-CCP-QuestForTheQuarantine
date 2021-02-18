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
import com.codebusters.game.scene.StoryScene;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Viewer implements ActionListener, KeyListener {
    private static final JFrame window = new JFrame();
    private static final JFrame quitWindow = new JFrame();
    private static final Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
    private static final Font normalFont = new Font("Times New Roman", Font.PLAIN, 16);
    private static final JButton yesButton = new JButton("Yes");
    private static final JButton noButton = new JButton("No");
    private static final Border dashed = BorderFactory.createDashedBorder(Color.decode("#0D5B69"), 1.2f, 8.0f, 2.0f, true);
    private static final Border empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    private static final Border compound = new CompoundBorder(empty, dashed);

    private StoryScene storyScene;
    private HelpScene helpScene;

    private Game game;

    //Constructor
    public Viewer(Game game) {
        this.game = game;
        window.setSize(880, 690); //size for the frame
        window.setLocationRelativeTo(null); //window pops up in center of screen

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes the window

        storyScene = new StoryScene();
        //add action listeners
        storyScene.getUserInputField().addActionListener(this);
        storyScene.getInputBtn().addActionListener(this);
        storyScene.getHelpBtn().addActionListener(this);
        storyScene.getQuitBtn().addActionListener(this);
        storyScene.getSaveBtn().addActionListener(this);
        storyScene.getLoadBtn().addActionListener(this);

        //once everything is added pack() to make everything fit snugly into frame then set frame to visible
        window.add(storyScene.getMainPanel());
        window.pack();
        window.setVisible(true);
    }

    //*************** METHODS ***************
    public void updateViewer() {
        storyScene.updateDisplay();

        window.revalidate();
        window.repaint();
    }

    public void helpWindowDisplay() {
        if (helpScene == null) {
            setHelpScene();
        }
        storyScene.getMainPanel().setVisible(false);
        window.add(helpScene.getHelpPanel());
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
        else if (e.getSource() == yesButton) {
            window.dispose();
            quitWindow.dispose();
            System.exit(0);
        }
        else if (e.getSource() == noButton) {
            quitWindow.dispose();
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
    }

    private void setHelpScene() {
        helpScene = new HelpScene();
        helpScene.getEntryBtn().addActionListener(this);
        helpScene.getExitBtn().addActionListener(this);
        helpScene.getGainBtn().addActionListener(this);
        helpScene.getLoseBtn().addActionListener(this);
        helpScene.getUseBtn().addActionListener(this);
        helpScene.getVerbsBtn().addActionListener(this);
    }

    //TODO: needs to become a JDialogue popup
    private void askToQuit() {
        JLabel quitTitle;
        Container quitContainer;
        quitWindow.setResizable(false);
        quitWindow.setVisible(true);
        quitWindow.setSize(500, 200);
        quitWindow.setLocationRelativeTo(window);
        quitWindow.setBackground(Color.decode("#EDE5D0"));
        BufferedImage bgImg = null;
        InputStream is = getClass().getClassLoader().getResourceAsStream("helpBgImage.png");
        try {
            bgImg = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert bgImg != null;
        Image quitBgImg = bgImg.getScaledInstance(500, 200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(quitBgImg);
        quitWindow.setContentPane(new JLabel(image));
        JLabel bg = new JLabel(new ImageIcon(bgImg));
        quitWindow.setLayout(null); //disables default layout
        quitWindow.setVisible(true); //makes window appear on screen

        quitTitle = new JLabel("Are you sure you want to quit?");
        quitTitle.setBounds(55, 0, 400, 100);
        quitTitle.setForeground(Color.decode("#e76f51")); //title text color
        Font quitFontTitle = titleFont.deriveFont(30F);
        quitTitle.setFont(quitFontTitle);
        quitContainer = quitWindow.getContentPane();
        quitContainer.add(quitTitle);

        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(yesButton, noButton));
        for (JButton button : buttons) {
            button.addActionListener(this);
            button.setForeground(Color.white);
            button.setBackground(Color.darkGray);
            button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.setPreferredSize(new Dimension(100, 35));
            button.setOpaque(true);
            button.setBorderPainted(false);
        }
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(45, 90, 400, 80);
        buttonPanel.setBackground(Color.decode("#EDE5D0"));
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        quitContainer.add(buttonPanel);

        quitWindow.pack();
        quitWindow.setVisible(true);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}



