package com.codebusters.game.scene;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class HelpScene {
    //Main panel everything will be added to
    private final JPanel helpPanel;
    //panel to hold the verb buttons
    private final JPanel buttonPanel;
    //label will hold the background image
    private final JLabel backgroundImg;
    //descriptive text to help user
    private final JTextArea helpText;
    //buttons to display usable game verbs
    private final JButton gainBtn = new JButton("Gain");
    private final JButton loseBtn = new JButton("Lose");
    private final JButton useBtn = new JButton("Use");
    private final JButton entryBtn = new JButton("Entry");
    private final JButton exitBtn = new JButton("Exit");
    private final JButton verbsBtn = new JButton("Verbs");

    public HelpScene() {
        helpPanel = new JPanel();

        //create background image for the backgroundImg label
        BufferedImage bgImg = null;
        InputStream is = getClass().getClassLoader().getResourceAsStream("helpBgImage.png");
        try {
            assert is != null;
            bgImg = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert bgImg != null;
        Image helpBgImg = bgImg.getScaledInstance(880, 690, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(helpBgImg); //create ImageIcon from image to pass to label
        //set image, size, layout
        backgroundImg = new JLabel(image);
        backgroundImg.setSize(880,690);
        //label will act as a panel that will eventually be added to the main help panel
        backgroundImg.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //Setting Help Title
        JLabel helpTitle = new JLabel("Little Helper");
        helpTitle.setPreferredSize(new Dimension(480, 46));
        helpTitle.setForeground(Color.decode("#e76f51")); //title text color
        Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
        helpTitle.setFont(titleFont); //title font
        helpTitle.setHorizontalAlignment(JLabel.CENTER);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(-200,0,0,0);
        //add to the backgroundImg Label
        backgroundImg.add(helpTitle, constraints);

        //Setup help text JTextArea
        helpText = new JTextArea();
        helpText.setPreferredSize(new Dimension(600, 250));
        helpText.setText("Welcome, I'm your Little Helper! You are in a survival text based game where you take a role of a brave girl named Esperanza." +
                "Your journey is a dangerous one, but with your wits and my guidance I believe you will find your salvation.\n\n" +
                "Pay attention to the story and navigate the game by making your decisions carefully for each choice changes your fate be it for better or worse.\n\n" +
                "Enter only two commands in the text field at a time to progress through the story: 1 verb and 1 noun.");
        Font normalFont = new Font("Times New Roman", Font.PLAIN, 16);
        helpText.setFont(normalFont);
        helpText.setBackground(Color.decode("#EDE5D0"));
        helpText.setForeground(Color.black);
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpText.setEditable(false);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(50,0,30,0);
        //add to backgroundImg label
        backgroundImg.add(helpText, constraints);

        //Initialize all verb buttons and store in ArrayList to be used in the button panel
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(gainBtn, loseBtn, useBtn, entryBtn, exitBtn, verbsBtn));
        for (JButton button : buttons) {
            button.setForeground(Color.white);
            button.setBackground(Color.darkGray);
            button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.setPreferredSize(new Dimension(100,35));
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setName("button");
        }

        //Setup button panel, add buttons to panel
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(300,90));
        buttonPanel.setBackground(Color.decode("#EDE5D0"));
        buttonPanel.setLayout(new GridLayout(2,3, 4,4));
        for (JButton button : buttons) {
            button.setOpaque(true);
            button.setBorderPainted(false);
            buttonPanel.add(button);
        }
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.insets = new Insets(0,0,-50,0);
        //add to backgroundImg label
        backgroundImg.add(buttonPanel, constraints);

        //Setup exit screen text to inform user how to quit the helper window
        JTextArea howToResumeGame = new JTextArea("-- Press [ESC] to exit help screen --");
        Font helperFont = new Font("Times New Roman", Font.ITALIC, 28);
        howToResumeGame.setFont(helperFont);
        howToResumeGame.setBackground(Color.decode("#EDE5D0"));
        howToResumeGame.setForeground(Color.BLUE);
        howToResumeGame.setEditable(false);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.insets = new Insets(100,0,-100,0);
        //add to backgroundImg label
        backgroundImg.add(howToResumeGame, constraints);
        //add backgroundImg to the main help panel
        helpPanel.add(backgroundImg);
    }

    //**** GETTERS ****/
    public JPanel getHelpPanel() {
        return helpPanel;
    }

    public JButton getGainBtn() {
        return gainBtn;
    }

    public JButton getLoseBtn() {
        return loseBtn;
    }

    public JButton getUseBtn() {
        return useBtn;
    }

    public JButton getEntryBtn() {
        return entryBtn;
    }

    public JButton getExitBtn() {
        return exitBtn;
    }

    public JButton getVerbsBtn() {
        return verbsBtn;
    }

    public JLabel getBackgroundImg() {
        return backgroundImg;
    }

    public JTextArea getHelpText() {
        return helpText;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }
}