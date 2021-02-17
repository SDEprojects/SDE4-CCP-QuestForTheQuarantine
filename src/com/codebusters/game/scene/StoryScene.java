package com.codebusters.game.scene;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class StoryScene implements Scene {
    private JPanel mainPanel;

    private static JPanel storyPanel, inventoryPanel, bottomPanel, bottomRightPanel;
    private static JLabel titleName, inventoryTitle;
    private static JTextField userInputField;
    private static final JButton inputBtn = new JButton("Enter");
    private static final JButton saveBtn = new JButton("Save");
    private static final JButton loadBtn = new JButton("Load");
    private static final JButton quitBtn = new JButton("Quit");
    private static final JButton helpBtn = new JButton("Help");

    private static final Border dashed = BorderFactory.createDashedBorder(Color.decode("#0D5B69"), 1.2f, 8.0f, 2.0f, true);
    private static final Border empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    private static final Border compound = new CompoundBorder(empty, dashed);

    private static final Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
    private static final Font normalFont = new Font("Times New Roman", Font.PLAIN, 16);

    public StoryScene() {
        //main panel
        mainPanel = new JPanel();
        //create background image
        BufferedImage img = null;
        InputStream is = getClass().getClassLoader().getResourceAsStream("bgImage.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;
        Image bgImg = img.getScaledInstance(880, 690, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(bgImg);
        mainPanel.add(new JLabel(imageIcon));
        mainPanel.setLayout(new GridBagLayout()); //created new GridBagLayout so text moves with screen resize
//        container = window.getContentPane(); //container inside the window that contains all the content
        GridBagConstraints c = new GridBagConstraints(); //needed to specify constraints for components

        //bottom panel to hold user input field and submit buttons
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.decode("#EDE5D0"));
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(0,-20, 0, -20);
        mainPanel.add(bottomPanel, c);


        //user input
        userInputField = new JTextField(10);
        userInputField.setText("");
        userInputField.setPreferredSize(new Dimension(80,30));
        userInputField.setBackground(Color.decode("#EDE5D0"));
        userInputField.setBorder(BorderFactory.createLoweredBevelBorder());
        userInputField.setForeground(Color.black);
//        userInputField.addActionListener(this);
        bottomPanel.add(userInputField);

        //input button
        inputBtn.setPreferredSize(new Dimension(80,30));
//        inputBtn.addActionListener(this);
        inputBtn.setForeground(Color.white);
        inputBtn.setBackground(Color.darkGray);
        inputBtn.setOpaque(true);
        inputBtn.setBorderPainted(false);
        inputBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        inputBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        bottomPanel.add(inputBtn);

        //bottom right panel to hold help, quit, save and load buttons
        bottomRightPanel = new JPanel();
        bottomRightPanel.setBackground(Color.decode("#EDE5D0"));
        c.gridx = 2;
        c.gridy = 2;
        c.insets = new Insets(0,-60,0, 70);
        mainPanel.add(bottomRightPanel, c);


        //help button
        helpBtn.setPreferredSize(new Dimension(80,30));
//        helpBtn.addActionListener(this);
        helpBtn.setForeground(Color.white);
        helpBtn.setBackground(Color.darkGray);
        helpBtn.setOpaque(true);
        helpBtn.setBorderPainted(false);
        helpBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        helpBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        bottomRightPanel.add(helpBtn);

        //quit button
        quitBtn.setPreferredSize(new Dimension(80,30));
//        quitBtn.addActionListener(this);
        quitBtn.setForeground(Color.white);
        quitBtn.setBackground(Color.darkGray);
        quitBtn.setOpaque(true);
        quitBtn.setBorderPainted(false);
        quitBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        quitBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        bottomRightPanel.add(quitBtn);

        //save button
        saveBtn.setPreferredSize(new Dimension(80,30));
//        saveBtn.addActionListener(this);
        saveBtn.setForeground(Color.white);
        saveBtn.setBackground(Color.darkGray);
        saveBtn.setOpaque(true);
        saveBtn.setBorderPainted(false);
        saveBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        saveBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        bottomRightPanel.add(saveBtn);

        //load button
        loadBtn.setPreferredSize(new Dimension(80,30));
//        loadBtn.addActionListener(this);
        loadBtn.setForeground(Color.white);
        loadBtn.setBackground(Color.darkGray);
        loadBtn.setOpaque(true);
        loadBtn.setBorderPainted(false);
        loadBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        loadBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        bottomRightPanel.add(loadBtn);

        //panel for game title
        titleName = new JLabel("The Quest for Quarantine");
        titleName.setPreferredSize(new Dimension(480, 46));
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0,110, 30, -100);
        titleName.setForeground(Color.decode("#e76f51")); //title text color
        titleName.setFont(titleFont); //title font
        mainPanel.add(titleName, c);

        //text panel for main story
        storyPanel = new JPanel();
        storyPanel.setPreferredSize(new Dimension(430, 350));
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(0, 140, 0, -80);
        storyPanel.setBackground(Color.decode("#EDE5D0"));
        mainPanel.add(storyPanel, c); //adding story panel to main container

        //panel for inventory
        inventoryPanel = new JPanel();
        inventoryPanel.setPreferredSize(new Dimension(150, 340));
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 2;
        c.gridwidth = 1;
        c.insets = new Insets(0,30,0,0);
        inventoryPanel.setBackground(Color.decode("#EDE5D0"));
        inventoryPanel.setForeground(Color.decode("#e76f51")); //title text color
        inventoryPanel.setBorder(compound);
        inventoryPanel.setFont(normalFont); //font

        //inventory title
        inventoryTitle = new JLabel("INVENTORY");
        inventoryTitle.setForeground(Color.decode("#635147")); //title text color
        inventoryTitle.setFont(new Font("Arial", Font.BOLD, 13)); //title font
        inventoryPanel.add(inventoryTitle);
        mainPanel.add(inventoryPanel, c);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void display(JPanel scene) {

    }

    @Override
    public Scene respondToInput(KeyEvent keyPressed) {
        return null;
    }
}