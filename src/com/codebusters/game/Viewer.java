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
    Border dashed = BorderFactory.createDashedBorder(Color.decode("#0D5B69"), 1.4f, 8.0f, 2.0f, true);
    Border empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    Border compound = new CompoundBorder(empty, dashed);
    String input;
    boolean waitingForInput;

    //Constructor
    public Viewer() {
        waitingForInput = true;
        window.setSize(800,560); //size for the frame
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes the window

        //create background image
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("pageImg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;
        Image bgImg = img.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(bgImg);
        window.setContentPane(new JLabel(imageIcon));
        //window.getContentPane().setBackground(Color.black);
        JLabel background = new JLabel(new ImageIcon(img));
        window.setLayout(null); //disables default layout
        window.setVisible(true); //makes window appear on screen
        container = window.getContentPane(); //container inside the window that contains all the content
        container.add(background);

        //panel for game title
//        titlePanel = new JPanel();
        titleName = new JLabel("The Quest for Quarantine");
        titleName.setBounds(250, 60, 480, 46);
//        titleName.setBackground(Color.decode("#EDE5D0"));
        titleName.setForeground(Color.decode("#e76f51")); //title text color
        titleName.setFont(titleFont); //title font
//        titlePanel.add(titleName);
        container.add(titleName);

        //text panel for main story
        storyPanel = new JPanel();
        storyPanel.setBounds(140, 115, 400, 280);
        storyPanel.setBackground(Color.decode("#EDE5D0"));
        //storyPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        container.add(storyPanel); //adding story panel to main container

        //user input
        userInputField = new JTextField();
        userInputField.setText("");
        userInputField.setBounds(154, 420, 110, 30);
        userInputField.setBackground(Color.decode("#EDE5D0"));
        userInputField.setBorder(BorderFactory.createLoweredBevelBorder());
        userInputField.setForeground(Color.black);
        userInputField.addActionListener(this);
        container.add(userInputField);

        //input button
        inputBtn.setBounds(270, 420, 80, 30);
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

        //save button
        saveBtn.setBounds(360,420,80,30);
        saveBtn.addActionListener(this);
        saveBtn.setForeground(Color.white);
        saveBtn.setBackground(Color.darkGray);
        saveBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        saveBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        container.add(saveBtn);

        //load button
        loadBtn.setBounds(450,420,80,30);
        loadBtn.addActionListener(this);
        loadBtn.setForeground(Color.white);
        loadBtn.setBackground(Color.darkGray);
        loadBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        loadBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        container.add(loadBtn);

        //text panel for main story
        storyPanel = new JPanel();
        storyPanel.setBounds(180,115,480, 200);
        storyPanel.setBackground(Color.decode("#F5EDDA"));
        container.add(storyPanel); //adding story panel to main container

        //panel for inventory
        inventoryPanel = new JPanel();
        inventoryPanel.setBounds(570, 120, 120, 280);
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
        inventoryTextArea.setBounds(160, 420, 100, 100);
        inventoryTextArea.setBackground(Color.decode("#EDE5D0"));
        inventoryTextArea.setForeground(Color.black);
        inventoryTextArea.setFont(normalFont);
        inventoryTextArea.setLineWrap(true);
        inventoryPanel.removeAll();
        inventoryPanel.add(inventoryTitle);
        inventoryPanel.add(inventoryTextArea);
        inventoryTextArea.update(inventoryTextArea.getGraphics()); //updates text area

        //text area of the main story
        storyTextArea = new JTextArea(GameState.getInstance().getSceneText());
        storyTextArea.setBounds(180,150,480, 190);
        storyTextArea.setBackground(Color.decode("#F5EDDA"));
        storyTextArea.setForeground(Color.black);
        storyTextArea.setFont(normalFont);
        storyTextArea.setLineWrap(true);
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



