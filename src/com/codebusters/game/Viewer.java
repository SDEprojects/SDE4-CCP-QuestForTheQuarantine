package com.codebusters.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Viewer implements ActionListener{
    JFrame window;
    Container container;
    JPanel titlePanel, storyPanel, inventoryPanel;
    JLabel titleName, inventoryTitle;
    JTextArea storyTextArea, inputTextArea, inventoryTextArea;
    Font titleFont = new Font("Times New Roman", Font.BOLD, 36);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 16);
    JTextField userInputField;
    JButton inputBtn = new JButton("Enter");
    GameState gameScene = GameState.getInstance();
    TextParser parser = TextParser.getInstance();

    String input;
    boolean waitingForInput;

    //Constructor
    public Viewer() {
        waitingForInput = true;

        window = new JFrame(); //initiates the frame
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

        //panel for title
        titlePanel = new JPanel();
        titlePanel.setBounds(180,50, 480, 60);
        titlePanel.setBackground(Color.decode("#F5EDDA"));
        titleName = new JLabel("The Quest for Quarantine");
        titleName.setForeground(Color.decode("#e76f51")); //title text color
        titleName.setFont(titleFont); //title font
        titlePanel.add(titleName);
        container.add(titlePanel);

        //user input text area
        inputTextArea = new JTextArea();
        inputTextArea.setBounds(180, 380, 150, 30);
        inputTextArea.setBackground(Color.decode("#F5EDDA"));
        inputTextArea.setForeground(Color.decode("#2E8B57"));
        container.add(inputTextArea);

        //user input
        userInputField = new JTextField();
        userInputField.setText("");
        userInputField.setBounds(180,340,110,30);
        userInputField.setBackground(Color.decode("#F5EDDA"));
        userInputField.setForeground(Color.black);
        userInputField.addActionListener(this);
        container.add(userInputField);

        //input button
        inputBtn.setBounds(292,340,80,30);
        inputBtn.addActionListener(this);
        inputBtn.setForeground(Color.white);
        inputBtn.setBackground(Color.darkGray);
        inputBtn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        userInputField.add(inputBtn);
        container.add(inputBtn);

        //text panel for main story
        storyPanel = new JPanel();
        storyPanel.setBounds(180,115,480, 200);
        storyPanel.setBackground(Color.decode("#F5EDDA"));
        container.add(storyPanel); //adding story panel to main container

        //panel for inventory
        inventoryPanel = new JPanel();
        inventoryPanel.setBounds(480,320, 180, 100);
        inventoryPanel.setBackground(Color.decode("#F5EDDA"));
        inventoryPanel.setForeground(Color.decode("#e76f51")); //title text color
        inventoryPanel.setFont(normalFont); //font
        container.add(inventoryPanel);

    }

    public void updateViewer() {
        inventoryTitle = new JLabel("Inventory");
        inventoryTitle.setForeground(Color.decode("#e76f51")); //title text color
        inventoryTitle.setFont(normalFont); //title font
        titlePanel.add(titleName);

        String inv = "";
        for (Items item: gameScene.getInventory()){
            inv += item + "\n";
        }
        inventoryTextArea = new JTextArea(inv);
        inventoryTextArea.setBounds(480,320,180, 100);
        inventoryTextArea.setBackground(Color.decode("#F5EDDA"));
        inventoryTextArea.setForeground(Color.black);
        inventoryTextArea.setFont(normalFont);
        inventoryTextArea.setLineWrap(true);
        inventoryPanel.removeAll();
        inventoryPanel.add(inventoryTitle);
        inventoryPanel.add(inventoryTextArea);
        inventoryTextArea.update(inventoryTextArea.getGraphics()); //updates text area

        //text area of the main story
        storyTextArea = new JTextArea(gameScene.getSceneText());
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
            inputTextArea.setText(input);
            parser.parseInput(input);

            userInputField.setText("");
            setWaitingForInput(false);
        }

    }

    public boolean isWaitingForInput() {
        return waitingForInput;
    }

    public void setWaitingForInput(boolean waitingForInput) {
        this.waitingForInput = waitingForInput;
    }
}
