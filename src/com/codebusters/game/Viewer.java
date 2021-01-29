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
    JTextArea storyTextArea;
    Font titleFont = new Font("Times New Roman", Font.BOLD, 36);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 16);
    JTextField userInputField;
    JButton inputBtn = new JButton("Enter");
    String[] inventoryList = {"water", "fire crackers", "matches"};

    public static void main(String[] args) {
        new Viewer();
    }

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

        //user input
        userInputField = new JTextField();
        userInputField.setText("Enter Action:");
        userInputField.setBounds(180,300,110,30);
        userInputField.setBackground(Color.decode("#F5EDDA"));
        userInputField.setForeground(Color.black);
        userInputField.addActionListener(this);
        container.add(userInputField);

        //input button
        inputBtn.setBounds(292,300,80,30);
        inputBtn.addActionListener(this);
        inputBtn.setForeground(Color.white);
        inputBtn.setBackground(Color.darkGray);
        inputBtn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        userInputField.add(inputBtn);
        container.add(inputBtn);

        //text panel for main story
        storyPanel = new JPanel();
        storyPanel.setBounds(180,115,480, 180);
        storyPanel.setBackground(Color.decode("#F5EDDA"));
        container.add(storyPanel); //adding story panel to main container


        //text area of the main story
        storyTextArea = new JTextArea("this is where the story is told. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eleifend, dui tempus porttitor egestas, enim odio sodales turpis, in malesuada lacus erat ac erat. Suspendisse cursus maximus aliquet. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Suspendisse dui felis, tincidunt a justo eget, elementum mattis augue.");
        storyTextArea.setBounds(180,150,480, 180);
        storyTextArea.setBackground(Color.decode("#F5EDDA"));
        storyTextArea.setForeground(Color.black);
        storyTextArea.setFont(normalFont);
        storyTextArea.setLineWrap(true);
        storyPanel.add(storyTextArea);

        //panel for inventory
        inventoryPanel = new JPanel();
        JList<String> list = new JList<>(inventoryList);
        inventoryTitle = new JLabel("Inventory");
        inventoryTitle.setForeground(Color.decode("#e76f51")); //title text color
        inventoryTitle.setFont(normalFont); //title font
        titlePanel.add(titleName);
        inventoryPanel.setBounds(480,300, 180, 100);
        inventoryPanel.setBackground(Color.decode("#F5EDDA"));
        inventoryPanel.setForeground(Color.decode("#e76f51")); //title text color
        inventoryPanel.setFont(normalFont); //font
        list.setBackground(Color.decode("#F5EDDA"));

        inventoryPanel.add(inventoryTitle);
        inventoryPanel.add(list);
        container.add(inventoryPanel);

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==userInputField || e.getSource() == inputBtn) {
            String input = userInputField.getText(); //gets input text and adds to variable input.
            System.out.println(input);
            userInputField.setText("");
        }

    }
}
