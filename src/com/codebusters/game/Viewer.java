package com.codebusters.game;

/**
 * Viewer.java is where using GUI the game can be experienced and played in a separate window.
 * The GUI is designed to be visually appealing and user friendly.
 * Player using the input field can progress through the game.
 * <p>
 * Author: Aliona (main GUI), Dustin (save/load).
 */

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Viewer implements ActionListener {
    private static final JFrame window = new JFrame();
    private static Container container;
    private static JPanel storyPanel, inventoryPanel;
    private static JLabel titleName, inventoryTitle;
    private static final Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
    private static final Font normalFont = new Font("Times New Roman", Font.PLAIN, 16);
    private static JTextField userInputField;
    private static final JButton inputBtn = new JButton("Enter");
    private static final JButton saveBtn = new JButton("Save");
    private static final JButton loadBtn = new JButton("Load");
    private static final JButton quitBtn = new JButton("Quit");
    private static final JButton helpBtn = new JButton("Help");
    private static final Border dashed = BorderFactory.createDashedBorder(Color.decode("#0D5B69"), 1.2f, 8.0f, 2.0f, true);
    private static final Border empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    private static final Border compound = new CompoundBorder(empty, dashed);
    private static boolean waitingForInput;

    //Constructor
    public Viewer() {
        waitingForInput = true;
        window.setSize(880, 690); //size for the frame
        window.setLocation(350, 80);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes the window

        //create background image
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("bgImage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;
        Image bgImg = img.getScaledInstance(880, 690, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(bgImg);
        window.setContentPane(new JLabel(imageIcon));
        JLabel background = new JLabel(new ImageIcon(img));
        window.setLayout(null); //disables default layout
        window.setVisible(true); //makes window appear on screen
        container = window.getContentPane(); //container inside the window that contains all the content
        container.add(background);


        //user input
        userInputField = new JTextField();
        userInputField.setText("");
        userInputField.setBounds(160, 520, 120, 30);
        userInputField.setBackground(Color.decode("#EDE5D0"));
        userInputField.setBorder(BorderFactory.createLoweredBevelBorder());
        userInputField.setForeground(Color.black);
        userInputField.addActionListener(this);
        container.add(userInputField);

        //input button
        inputBtn.setBounds(290, 520, 80, 30);
        inputBtn.addActionListener(this);
        inputBtn.setForeground(Color.white);
        inputBtn.setBackground(Color.darkGray);
        inputBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        inputBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        userInputField.add(inputBtn);
        container.add(inputBtn);

        //help button
        helpBtn.setBounds(440, 520, 80, 30);
        helpBtn.addActionListener(this);
        helpBtn.setForeground(Color.white);
        helpBtn.setBackground(Color.darkGray);
        helpBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        helpBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        container.add(helpBtn);

        //quit button
        quitBtn.setBounds(525, 520, 80, 30);
        quitBtn.addActionListener(this);
        quitBtn.setForeground(Color.white);
        quitBtn.setBackground(Color.darkGray);
        quitBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        quitBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        container.add(quitBtn);

        //save button
        saveBtn.setBounds(610, 520, 80, 30);
        saveBtn.addActionListener(this);
        saveBtn.setForeground(Color.white);
        saveBtn.setBackground(Color.darkGray);
        saveBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        saveBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        container.add(saveBtn);

        //load button
        loadBtn.setBounds(695, 520, 80, 30);
        loadBtn.addActionListener(this);
        loadBtn.setForeground(Color.white);
        loadBtn.setBackground(Color.darkGray);
        loadBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        loadBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        container.add(loadBtn);

        //panel for game title
        titleName = new JLabel("The Quest for Quarantine");
        titleName.setBounds(280, 100, 480, 46);
        titleName.setForeground(Color.decode("#e76f51")); //title text color
        titleName.setFont(titleFont); //title font
        container.add(titleName);

        //text panel for main story
        storyPanel = new JPanel();
        storyPanel.setBounds(162, 150, 430, 350);
        storyPanel.setBackground(Color.decode("#EDE5D0"));
        container.add(storyPanel); //adding story panel to main container

        //panel for inventory
        inventoryPanel = new JPanel();
        inventoryPanel.setBounds(620, 160, 150, 340);
        inventoryPanel.setBackground(Color.decode("#EDE5D0"));
        inventoryPanel.setForeground(Color.decode("#e76f51")); //title text color
        inventoryPanel.setBorder(compound);
        inventoryPanel.setFont(normalFont); //font
        container.add(inventoryPanel);

        //inventory title
        inventoryTitle = new JLabel("INVENTORY");
        inventoryTitle.setForeground(Color.decode("#635147")); //title text color
        inventoryTitle.setFont(new Font("Arial", Font.BOLD, 13)); //title font
        inventoryPanel.add(inventoryTitle);

    }

    //*************** METHODS ***************
    public void updateViewer() {

        titleName.setText(GameState.getInstance().getSceneTitle());
//        container.add(titleName);

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

        setWaitingForInput(true);
        window.repaint();
        window.revalidate();
    }

    public void helpWindowDisplay() {
        JFrame helpWindow = new JFrame(); //initiate help window
        Container helpContainer;
        JLabel helpTitle;

        //set up help window
        helpWindow.setVisible(true);
        helpWindow.setSize(500, 415);
        helpWindow.setLocation(480, 200);

        //create background image for help window
        BufferedImage bgImg = null;
        try {
            bgImg = ImageIO.read(new File("helpBgImage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert bgImg != null;
        Image helpBgImg = bgImg.getScaledInstance(500, 380, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(helpBgImg);
        helpWindow.setContentPane(new JLabel(image));
        JLabel bg = new JLabel(new ImageIcon(bgImg));
        helpWindow.setLayout(null); //disables default layout
        helpWindow.setVisible(true); //makes window appear on screen

        //help window container
        helpContainer = helpWindow.getContentPane(); //container inside the window with help content
        helpContainer.add(bg);

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

        helpText2.setText("Examples: go around, use firecrackers, enter store, leave city, go farther, search cabinets, grab crate, trade ammo, run away, threaten farmer.");

        helpText1.setBounds(34, 75, 425, 200);
        helpText1.setBackground(Color.decode("#EDE5D0"));
        helpText1.setLineWrap(true);
        helpText1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        helpText1.setWrapStyleWord(true); //creates readable text that is separated by word when wrapped.
        helpText1.setForeground(Color.black);
        helpText1.setEditable(false);

        helpText2.setBounds(34, 310, 425, 40);
        helpText2.setBackground(Color.decode("#EDE5D0"));
        helpText2.setLineWrap(true);
        helpText2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        helpText2.setWrapStyleWord(true); //creates readable text that is separated by word when wrapped.
        helpText2.setForeground(Color.black);
        helpText2.setEditable(false);

        //image inside the help text
        imagePane.insertIcon(new ImageIcon("InputFieldImg.png"));
        imagePane.setBackground(Color.decode("#EDE5D0"));
        imagePane.setBounds(45, 268, 200, 40);

        //add all content to the container
        helpContainer.add(imagePane);
        helpContainer.add(helpText1);
        helpContainer.add(helpText2);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userInputField || e.getSource() == inputBtn) {
            String input = userInputField.getText();
            TextParser.getInstance().parseInput(input);

            userInputField.setText("");
            setWaitingForInput(false);

            //when saveBtn is pressed pass "save" parameter to saveOrLoadGame method
        } else if (e.getSource() == saveBtn) {
            saveOrLoadGame("save");
            //when loadBtn is pressed pass "load" parameter to saveOrLoadGame method.
            //then call updateViewer method to update the game in GUI.
        } else if (e.getSource() == loadBtn) {
            saveOrLoadGame("load");
            updateViewer();
            //when quitBtn is pressed the GUI window and game closes.
        } else if (e.getSource() == quitBtn) {
            window.dispose();
            System.exit(0);
        } else if (e.getSource() == helpBtn) {
            helpWindowDisplay();
        }
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
        int userSelection = fileChooser.showSaveDialog(window);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            saveOrLoadSuccessful = flag.equals("save") ? GameState.saveGame(file) : GameState.loadGame(file);
        }else {
            return false;
        }
        return saveOrLoadSuccessful;
    }

    public boolean isWaitingForInput() {
        return waitingForInput;
    }

    public void setWaitingForInput(boolean waitingForInput) {
        Viewer.waitingForInput = waitingForInput;
    }
}



