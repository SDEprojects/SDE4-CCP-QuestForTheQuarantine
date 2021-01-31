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
    JTextArea storyTextArea, inputTextArea;
    Font titleFont = new Font("Times New Roman", Font.BOLD, 36);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 16);
    JTextField userInputField;
    JButton inputBtn = new JButton("Enter");
    String[] inventoryList = {"water", "fire crackers", "matches"};
    GameState gameScene = GameState.getInstance();
    TextParser parser = TextParser.getInstance();
    JList list;
    DefaultListModel<Items> model;
    String input;


//
//    public static void main(String[] args) {
//        new Viewer();
//    }

    //Constructor
    public Viewer() {
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
        inventoryTitle = new JLabel("Inventory");
        inventoryTitle.setForeground(Color.decode("#e76f51")); //title text color
        inventoryTitle.setFont(normalFont); //title font
        titlePanel.add(titleName);
        inventoryPanel.setBounds(480,320, 180, 100);
        inventoryPanel.setBackground(Color.decode("#F5EDDA"));
        inventoryPanel.setForeground(Color.decode("#e76f51")); //title text color
        inventoryPanel.setFont(normalFont); //font
        //list.setBackground(Color.decode("#F5EDDA"));
        inventoryPanel.add(inventoryTitle);
        container.add(inventoryPanel);

    }

    public void updateViewer(GameState currentGame) {
        gameScene = currentGame;
//      JList<Items> list = new JList<Items>(currentGame.getInventory());
        model = new DefaultListModel<>();
        list = new JList(model);
        model.addAll(currentGame.getInventory());
        container.add(list);

//        //checks for input in parser
//        input = userInputField.getText();
//        Game.parser.parseInput(input);



        //text area of the main story
        storyTextArea = new JTextArea(currentGame.getSceneText());
        storyTextArea.setBounds(180,150,480, 190);
        storyTextArea.setBackground(Color.decode("#F5EDDA"));
        storyTextArea.setForeground(Color.black);
        storyTextArea.setFont(normalFont);
        storyTextArea.setLineWrap(true);
        storyPanel.add(storyTextArea);
//      storyTextArea.update(storyTextArea.getGraphics()); //updates text area

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==userInputField || e.getSource() == inputBtn) {

//          userInputField.getText(); //gets input text and adds to variable input.
            input = userInputField.getText();
            inputTextArea.setText(input);
            Game.parser.parseInput(input);

            System.out.println(input);
            userInputField.setText("");
        }

    }
}
