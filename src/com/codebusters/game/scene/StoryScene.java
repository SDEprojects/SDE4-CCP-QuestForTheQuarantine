package com.codebusters.game.scene;

import com.codebusters.game.GameState;
import com.codebusters.game.Items;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class StoryScene {
    //main panel to hold all components
    private final JPanel mainPanel;
    //panel to hold story text
    private static JPanel storyPanel;
    //panel to hold the inventory
    private final JPanel inventoryPanel;
    //title label
    private final JLabel titleName;
    //inventory label
    private final JLabel inventoryTitle;
    //user input text field
    private final JTextField userInputField;
    //Buttons
    private final JButton inputBtn = new JButton("Enter");
    private final JButton saveBtn = new JButton("Save");
    private final JButton loadBtn = new JButton("Load");
    private final JButton quitBtn = new JButton("Quit");
    private final JButton helpBtn = new JButton("Help");

    //normal game font
    private final Font normalFont = new Font("Times New Roman", Font.PLAIN, 16);

    /*
     * Sets up the layout and format of the main games story GUI
     * Uses a JLabel backgroundImg to set the background, then adds all components to that label using GridBagLayout
     * Once everything is set on the background the backgroundImg label is added to the mainPanel which can then be used
     * by a JFrame to display the scene
     */
    public StoryScene() {
        //main panel
        mainPanel = new JPanel();

        //create background image and setting image
        BufferedImage img = null;
        InputStream is = getClass().getClassLoader().getResourceAsStream("bgImage.png");
        try {
            assert is != null;
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;
        Image bgImg = img.getScaledInstance(880, 690, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(bgImg);
        //adding background image to JLabel
        JLabel backgroundImg = new JLabel(imageIcon);
        backgroundImg.setSize(880,690);//size for the frame
        backgroundImg.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints(); //needed to specify constraints for components

        //bottom panel to hold user input field and submit buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.decode("#EDE5D0"));
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.insets = new Insets(0,-20, 0, -20);
        backgroundImg.add(bottomPanel, constraints);


        //user input
        userInputField = new JTextField(10);
        userInputField.setText("");
        userInputField.setPreferredSize(new Dimension(80,30));
        userInputField.setBackground(Color.decode("#EDE5D0"));
        userInputField.setBorder(BorderFactory.createLoweredBevelBorder());
        userInputField.setForeground(Color.black);
        bottomPanel.add(userInputField);

        //input button
        inputBtn.setPreferredSize(new Dimension(80,30));
        inputBtn.setForeground(Color.white);
        inputBtn.setBackground(Color.darkGray);
        inputBtn.setOpaque(true);
        inputBtn.setBorderPainted(false);
        inputBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        inputBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        bottomPanel.add(inputBtn);

        //bottom right panel to hold help, quit, save and load buttons
        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setBackground(Color.decode("#EDE5D0"));
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.insets = new Insets(0,-60,0, 70);
        backgroundImg.add(bottomRightPanel, constraints);


        //help button
        helpBtn.setPreferredSize(new Dimension(80,30));
        helpBtn.setForeground(Color.white);
        helpBtn.setBackground(Color.darkGray);
        helpBtn.setOpaque(true);
        helpBtn.setBorderPainted(false);
        helpBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        helpBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        bottomRightPanel.add(helpBtn);

        //quit button
        quitBtn.setPreferredSize(new Dimension(80,30));
        quitBtn.setForeground(Color.white);
        quitBtn.setBackground(Color.darkGray);
        quitBtn.setOpaque(true);
        quitBtn.setBorderPainted(false);
        quitBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        quitBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        bottomRightPanel.add(quitBtn);

        //save button
        saveBtn.setPreferredSize(new Dimension(80,30));
        saveBtn.setForeground(Color.white);
        saveBtn.setBackground(Color.darkGray);
        saveBtn.setOpaque(true);
        saveBtn.setBorderPainted(false);
        saveBtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        saveBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        bottomRightPanel.add(saveBtn);

        //load button
        loadBtn.setPreferredSize(new Dimension(80,30));
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
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(0,110, 30, -100);
        titleName.setForeground(Color.decode("#e76f51")); //title text color
        Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
        titleName.setFont(titleFont); //title font
        backgroundImg.add(titleName, constraints);

        //text panel for main story
        storyPanel = new JPanel();
        storyPanel.setPreferredSize(new Dimension(430, 350));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(0, 140, 0, -80);
        storyPanel.setBackground(Color.decode("#EDE5D0"));
        backgroundImg.add(storyPanel, constraints); //adding story panel to main container

        //panel for inventory
        inventoryPanel = new JPanel();
        inventoryPanel.setPreferredSize(new Dimension(150, 340));
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0,30,0,0);
        inventoryPanel.setBackground(Color.decode("#EDE5D0"));
        inventoryPanel.setForeground(Color.decode("#e76f51")); //title text color
        Border dashed = BorderFactory.createDashedBorder(Color.decode("#0D5B69"), 1.2f, 8.0f, 2.0f, true);
        Border empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
        Border compound = new CompoundBorder(empty, dashed);
        inventoryPanel.setBorder(compound);
        inventoryPanel.setFont(normalFont); //font

        //inventory title
        inventoryTitle = new JLabel("INVENTORY");
        inventoryTitle.setForeground(Color.decode("#635147")); //title text color
        inventoryTitle.setFont(new Font("Arial", Font.BOLD, 13)); //title font
        inventoryPanel.add(inventoryTitle);
        backgroundImg.add(inventoryPanel, constraints);

        //add backgroundImg to main panel
        mainPanel.add(backgroundImg);
    }

    /*
     * Updates components of the main panel with current GameState
     */
    public void updateDisplay() {
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
    }

    //*** GETTERS ****//
    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTextField getUserInputField() {
        return userInputField;
    }

    public JButton getInputBtn() {
        return inputBtn;
    }

    public JButton getHelpBtn() {
        return helpBtn;
    }

    public JButton getSaveBtn() {
        return saveBtn;
    }

    public JButton getLoadBtn() {
        return loadBtn;
    }

    public JButton getQuitBtn() {
        return quitBtn;
    }
}