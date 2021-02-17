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
import com.codebusters.game.scene.Scene;
import com.codebusters.game.scene.StoryScene;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Viewer implements ActionListener {
    private static final JFrame window = new JFrame();
    private static final JFrame quitWindow = new JFrame();
    private static final Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
    private static final Font normalFont = new Font("Times New Roman", Font.PLAIN, 16);
    private static final JButton gainBtn = new JButton("Gain");
    private static final JButton loseBtn = new JButton("Lose");
    private static final JButton useBtn = new JButton("Use");
    private static final JButton entryBtn = new JButton("Entry");
    private static final JButton exitBtn = new JButton("Exit");
    private static final JButton verbsBtn = new JButton("Verbs");
    private static final JButton yesButton = new JButton("Yes");
    private static final JButton noButton = new JButton("No");
    private static final Border dashed = BorderFactory.createDashedBorder(Color.decode("#0D5B69"), 1.2f, 8.0f, 2.0f, true);
    private static final Border empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    private static final Border compound = new CompoundBorder(empty, dashed);

    private StoryScene storyScene;
    private HelpScene helpScene;
    private Scene currentScene;

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
        currentScene = storyScene;

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
            helpScene = new HelpScene();
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
        } else if (e.getSource() == gainBtn) {
            displayExamples(TextParser.getInstance().getITEM_VERBS_GAIN(), gainBtn.getText());
        } else if (e.getSource() == loseBtn) {
            displayExamples(TextParser.getInstance().getITEM_VERBS_LOSE(), loseBtn.getText());
        } else if (e.getSource() == useBtn) {
            displayExamples(TextParser.getInstance().getITEM_VERBS_USE(), useBtn.getText());
        } else if (e.getSource() == entryBtn) {
            displayExamples(TextParser.getInstance().getPLACES_VERBS_ENTRY(), entryBtn.getText());
        } else if (e.getSource() == exitBtn) {
            displayExamples(TextParser.getInstance().getPLACES_VERBS_EXIT(), exitBtn.getText());
        } else if (e.getSource() == verbsBtn) {
            ArrayList<String> data = new ArrayList<String>();
            data.addAll(TextParser.getInstance().getVERBS1());
            data.addAll(TextParser.getInstance().getVERBS2());
            displayExamples(data, verbsBtn.getText());
        }
    }

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

    private void displayExamples(ArrayList<String> data, String buttonName) {
        JFrame examplesWindow = new JFrame(); //initiate help window
        JLabel helpTitle;
        Container helpContainer;
        examplesWindow.setResizable(false);
        examplesWindow.setVisible(true);
        examplesWindow.setSize(500, 415);
        examplesWindow.setLocationRelativeTo(window);
        examplesWindow.setBackground(Color.decode("#EDE5D0"));

        BufferedImage bgImg = null;
        InputStream is = getClass().getClassLoader().getResourceAsStream("helpBgImage.png");
        try {
            bgImg = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert bgImg != null;
        Image helpBgImg = bgImg.getScaledInstance(500, 380, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(helpBgImg);
        examplesWindow.setContentPane(new JLabel(image));
        JLabel bg = new JLabel(new ImageIcon(bgImg));
        examplesWindow.setLayout(null); //disables default layout
        examplesWindow.setVisible(true); //makes window appear on screen

        //help window container
        helpContainer = examplesWindow.getContentPane(); //container inside the window with help content
        helpContainer.add(bg);

        //help title
        helpTitle = new JLabel(buttonName + " Commands");
        helpTitle.setBounds(130, -80, 400, 250);
        helpTitle.setForeground(Color.decode("#e76f51")); //title text color
        helpTitle.setFont(titleFont);
        helpContainer.add(helpTitle);

        JTextArea commands = new JTextArea();
        commands.setBounds(200, 90, 100, 80);
        commands.setBackground(Color.decode("#EDE5D0"));
        commands.setFont(normalFont);
        data.forEach(x -> commands.append(x.toUpperCase() + "\n"));
        helpContainer.add(commands);
    }

    //Create list of commands window


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

    public static void main(String[] args) {
        Game game = new Game();
    }

}



