package com.codebusters.game;
/**
 * Viewer.java is where using GUI the game can be experienced and played in a separate window.
 * The GUI is designed to be visually appealing and user friendly.
 * Player using the input field can progress through the game.
 * <p>
 * Author: Aliona (main GUI), Dustin (save/load).
 * Last Edited: 02/09/2021
 */

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

public class Viewer extends JFrame implements ActionListener {
    private static final JFrame window = new JFrame();
    private static final JFrame quitWindow = new JFrame();
    private static Container container;
    private static JPanel storyPanel, inventoryPanel, bottomPanel, bottomRightPanel;
    private static JLabel titleName, inventoryTitle;
    private static final Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
    private static final Font normalFont = new Font("Times New Roman", Font.PLAIN, 16);
    private static JTextField userInputField;
    private static final JButton inputBtn = new JButton("Enter");
    private static final JButton saveBtn = new JButton("Save");
    private static final JButton loadBtn = new JButton("Load");
    private static final JButton quitBtn = new JButton("Quit");
    private static final JButton helpBtn = new JButton("Help");
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

    private Game game;

    //Constructor
    public Viewer(Game game) {
        this.game = game;
        window.setSize(880, 690); //size for the frame
        window.setLocationRelativeTo(null); //window pops up in center of screen
//        window.setLayout(new GridBagLayout());

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes the window

        storyScene = new StoryScene();
        //once everything is added pack() to make everything fit snugly into frame then set frame to visible
        window.getContentPane().add(storyScene.getMainPanel());
//        window.add(storyScene.getMainPanel());
        window.pack();
        window.setVisible(true);
    }

    //*************** METHODS ***************
    public void updateViewer() {
        titleName.setText(GameState.getInstance().getSceneTitle());

        //create StringBuilder for inventory to be displayed as string in the inventory text area.
        StringBuilder inv = new StringBuilder();
        for (Items item : GameState.getInstance().getInventory()) {
            inv.append(item).append("\n");
        }
        //text area for the inventory
        JTextArea inventoryTextArea = new JTextArea(inv.toString());
        inventoryTextArea.setBounds(620, 180, 100, 100);
        inventoryTextArea.setBackground(Color.decode("#EDE5D0"));
        inventoryTextArea.setForeground(Color.black);
        inventoryTextArea.setFont(normalFont);
        inventoryTextArea.setLineWrap(true);
        inventoryTextArea.setWrapStyleWord(true);
        inventoryTextArea.setEditable(false);

        //inventory panel updated
        inventoryPanel.removeAll();
        inventoryPanel.add(inventoryTitle);
        inventoryPanel.add(inventoryTextArea);
        inventoryTextArea.update(inventoryTextArea.getGraphics()); //updates text area

        //text area of the main story
        JTextArea storyTextArea = new JTextArea(GameState.getInstance().getSceneText()); //connects to the story text in the game.
        storyTextArea.setBounds(10, 150, 430, 350);
        storyTextArea.setBackground(Color.decode("#EDE5D0"));
        storyTextArea.setForeground(Color.black);
        storyTextArea.setFont(normalFont);
        storyTextArea.setLineWrap(true);
        storyTextArea.setWrapStyleWord(true); //creates readable text that is separated by word when wrapped.
        storyTextArea.setEditable(false);

        storyPanel.removeAll();
        storyPanel.add(storyTextArea);
        storyTextArea.update(storyTextArea.getGraphics()); //updates text area

        window.revalidate();
        window.repaint();
    }

    public void helpWindowDisplay() {
        JFrame helpWindow = new JFrame(); //initiate help window
        Container helpContainer;
        JLabel helpTitle;

        //set up help window
        helpWindow.setSize(500, 415);
        helpWindow.setLocationRelativeTo(window); //help window will now pop up in front of main game window so user doesn't have to look for it
        helpWindow.setResizable(false);

        //create background image for help window
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
        helpWindow.setContentPane(new JLabel(image));
        helpWindow.setLayout(null); //disables default layout
        helpWindow.setResizable(false); //keep window from being resized
        //help window container
        helpContainer = helpWindow.getContentPane(); //container inside the window with help content

        //help title
        helpTitle = new JLabel("Little Helper");
        helpTitle.setBounds(160, -80, 200, 250);
        helpTitle.setForeground(Color.decode("#e76f51")); //title text color
        helpTitle.setFont(titleFont);
        helpContainer.add(helpTitle);

        //text area for the help window
        JTextArea helpText1 = new JTextArea();
        JTextArea helpText2 = new JTextArea();
        JTextPane imagePane = new JTextPane();

        helpText1.setText("Welcome, I'm your Little Helper! You are in a survival text based game where you take a role of a brave girl named Esperanza." +
                "Your journey is a dangerous one, but with your wits and my guidance I believe you will find your salvation.\n\n" +
                "Pay attention to the story and navigate the game by making your decisions carefully for each choice changes your fate be it for better or worse.\n\n" +
                "Enter only two commands in the text field at a time to progress through the story: 1 verb and 1 noun.");


        // examplesBtn.setPreferredSize(new Dimension(80,30));
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(gainBtn, loseBtn, useBtn, entryBtn, exitBtn, verbsBtn));
        for (JButton button : buttons) {
            button.addActionListener(this);
            button.setForeground(Color.white);
            button.setBackground(Color.darkGray);
            button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.setPreferredSize(new Dimension(100,35));
            button.setOpaque(true);
            button.setBorderPainted(false);
        }

        // helpText2.setText("Examples: go around, use firecrackers, enter store, leave city, go farther, search cabinets, grab crate, trade ammo, run away, threaten farmer.");

        helpText1.setBounds(34, 75, 425, 200);
        helpText1.setBackground(Color.decode("#EDE5D0"));
        helpText1.setLineWrap(true);
        helpText1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        helpText1.setWrapStyleWord(true); //creates readable text that is separated by word when wrapped.
        helpText1.setForeground(Color.black);
        helpText1.setEditable(false);

        // helpText2.setBounds(34, 310, 425, 40);
        // helpText2.setBackground(Color.decode("#EDE5D0"));
        // helpText2.setLineWrap(true);
        // helpText2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        // helpText2.setWrapStyleWord(true); //creates readable text that is separated by word when wrapped.
        // helpText2.setForeground(Color.black);
        // helpText2.setEditable(false);

        //image inside the help text
        imagePane.insertIcon(new ImageIcon(getClass().getClassLoader().getResource("inputFieldImg.png")));
        imagePane.setBackground(Color.decode("#EDE5D0"));
        imagePane.setBounds(45, 268, 200, 40);
        imagePane.setEditable(false);

        //add all content to the container

        // helpContainer.add(imagePane);
        helpContainer.add(helpText1);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(45, 270, 400, 80);
        buttonPanel.setBackground(Color.decode("#EDE5D0"));
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        helpContainer.add(buttonPanel);


        //ensure everything fits snugly in JFrame and set visible
        helpWindow.pack();
        helpWindow.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userInputField || e.getSource() == inputBtn) {
            String input = userInputField.getText();
            //pass user input to be validated and game updated
            game.playerAction(input);

            userInputField.setText("");
            //update the window to reflect any changes caused by user input
            updateViewer();
        }
        //when saveBtn is pressed pass "save" parameter to saveOrLoadGame method
        else if (e.getSource() == saveBtn) {
            saveOrLoadGame("save");
        }
        //when loadBtn is pressed pass "load" parameter to saveOrLoadGame method.
        else if (e.getSource() == loadBtn) {
            saveOrLoadGame("load");
            //updates viewer with the GameState from loaded game
            updateViewer();
            //updates the current chapter in the game to track the newly loaded GameState
            game.loadGame();
        }
        //when quitBtn is pressed the GUI window and game closes.
        else if (e.getSource() == quitBtn) {
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
        else if (e.getSource() == helpBtn) {
            helpWindowDisplay();
        } else if (e.getSource() == gainBtn) {
            gainBtn.setOpaque(true);
            gainBtn.setBorderPainted(false);
            displayExamples(TextParser.getInstance().getITEM_VERBS_GAIN(), gainBtn.getText());
        } else if (e.getSource() == loseBtn) {
            loseBtn.setOpaque(true);
            loseBtn.setBorderPainted(false);
            displayExamples(TextParser.getInstance().getITEM_VERBS_LOSE(), loseBtn.getText());
        } else if (e.getSource() == useBtn) {
            useBtn.setOpaque(true);
            useBtn.setBorderPainted(false);
            displayExamples(TextParser.getInstance().getITEM_VERBS_USE(), useBtn.getText());
        } else if (e.getSource() == entryBtn) {
            entryBtn.setOpaque(true);
            entryBtn.setBorderPainted(false);
            displayExamples(TextParser.getInstance().getPLACES_VERBS_ENTRY(), entryBtn.getText());
        } else if (e.getSource() == exitBtn) {
            exitBtn.setOpaque(true);
            exitBtn.setBorderPainted(false);
            displayExamples(TextParser.getInstance().getPLACES_VERBS_EXIT(), exitBtn.getText());
        } else if (e.getSource() == verbsBtn) {
            verbsBtn.setOpaque(true);
            verbsBtn.setBorderPainted(false);
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



