package com.codebusters.game;

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

public class Viewer implements ActionListener {
    JFrame window = new JFrame();
    Container container;
    JPanel storyPanel, inventoryPanel;
    JLabel titleName, inventoryTitle;
    JTextArea storyTextArea, inventoryTextArea;
    Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 16);
    JTextField userInputField;
    JButton inputBtn = new JButton("Enter");
    JButton saveBtn = new JButton("Save");
    JButton loadBtn = new JButton("Load");
    JButton quitBtn = new JButton("Quit");
    Border dashed = BorderFactory.createDashedBorder(Color.decode("#0D5B69"), 1.2f, 8.0f, 2.0f, true);
    Border empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    Border compound = new CompoundBorder(empty, dashed);
    String input;
    boolean waitingForInput;

    //Constructor
    public Viewer() {
        waitingForInput = true;
        window.setSize(880,690); //size for the frame
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

//        //user input text area
//        inputTextArea = new JTextArea();
////        inputTextArea.setBounds(160, 420, 150, 30);
////        inputTextArea.setBackground(Color.decode("#EDE5D0"));
////        inputTextArea.setForeground(Color.decode("#2E8B57"));
//        container.add(inputTextArea);

        //quit button
        quitBtn.setBounds(510,520,80,30);
        quitBtn.addActionListener(this);
        quitBtn.setForeground(Color.white);
        quitBtn.setBackground(Color.darkGray);
        quitBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        quitBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        container.add(quitBtn);

        //save button
        saveBtn.setBounds(600,520,80,30);
        saveBtn.addActionListener(this);
        saveBtn.setForeground(Color.white);
        saveBtn.setBackground(Color.darkGray);
        saveBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        saveBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        container.add(saveBtn);

        //load button
        loadBtn.setBounds(690,520,80,30);
        loadBtn.addActionListener(this);
        loadBtn.setForeground(Color.white);
        loadBtn.setBackground(Color.darkGray);
        loadBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        loadBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        container.add(loadBtn);

        //panel for game title
//      titlePanel = new JPanel();
        titleName = new JLabel("The Quest for Quarantine");
        titleName.setBounds(280, 100, 480, 46);
//      titleName.setBackground(Color.decode("#EDE5D0"));
        titleName.setForeground(Color.decode("#e76f51")); //title text color
        titleName.setFont(titleFont); //title font
//      titlePanel.add(titleName);
        container.add(titleName);

        //text panel for main story
        storyPanel = new JPanel();
        storyPanel.setBounds(148,150,430, 300);
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

    public void updateViewer() {
        container.add(titleName);

        StringBuilder inv = new StringBuilder();
        for (Items item : GameState.getInstance().getInventory()) {
            inv.append(item).append("\n");
        }
        inventoryTextArea = new JTextArea(inv.toString());
        inventoryTextArea.setBounds(620, 180, 100, 100);
        inventoryTextArea.setBackground(Color.decode("#EDE5D0"));
        inventoryTextArea.setForeground(Color.black);
        inventoryTextArea.setFont(normalFont);
        inventoryTextArea.setLineWrap(true);
        inventoryTextArea.setWrapStyleWord(true);

        inventoryPanel.removeAll();
        inventoryPanel.add(inventoryTitle);
        inventoryPanel.add(inventoryTextArea);
        inventoryTextArea.update(inventoryTextArea.getGraphics()); //updates text area

        //text area of the main story
        storyTextArea = new JTextArea(GameState.getInstance().getSceneText()); //connects to the story text in the game.
        storyTextArea.setBounds(70,180,400, 280);
        storyTextArea.setBackground(Color.decode("#EDE5D0"));
        storyTextArea.setForeground(Color.black);
        storyTextArea.setFont(normalFont);
        storyTextArea.setLineWrap(true);
        storyTextArea.setWrapStyleWord(true); //creates readable text that is separated by word when wrapped.

        storyPanel.removeAll();
        storyPanel.add(storyTextArea);
        storyTextArea.update(storyTextArea.getGraphics()); //updates text area

        setWaitingForInput(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==userInputField || e.getSource() == inputBtn) {
            input = userInputField.getText();
            //inputTextArea.setText(input); //uncomment to use as feedback to show user's text entered
            TextParser.getInstance().parseInput(input);

            userInputField.setText("");
            setWaitingForInput(false);

        }else if(e.getSource() == saveBtn) {
            saveOrLoadGame("save");
        }else if (e.getSource() == loadBtn) {
            saveOrLoadGame("load");
            updateViewer();
        }else if (e.getSource() == quitBtn){
            window.dispose();
        }

    }

    private boolean saveOrLoadGame(String flag) {
        boolean saveOrLoadSuccessful;
        JFileChooser fileChooser = new JFileChooser();
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
        this.waitingForInput = waitingForInput;
    }
}



