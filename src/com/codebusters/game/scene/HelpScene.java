package com.codebusters.game.scene;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class HelpScene implements Scene {
    private JPanel helpPanel;
    private JLabel backgroundImg;
    private JTextArea helpText;
    private static final Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
    private static final Font helperFont = new Font("Times New Roman", Font.ITALIC, 28);
    private static final Font normalFont = new Font("Times New Roman", Font.PLAIN, 16);
    private static final JButton gainBtn = new JButton("Gain");
    private static final JButton loseBtn = new JButton("Lose");
    private static final JButton useBtn = new JButton("Use");
    private static final JButton entryBtn = new JButton("Entry");
    private static final JButton exitBtn = new JButton("Exit");
    private static final JButton verbsBtn = new JButton("Verbs");

    public HelpScene() {
        helpPanel = new JPanel();
        JLabel helpTitle;

        //create background image for help window
        BufferedImage bgImg = null;
        InputStream is = getClass().getClassLoader().getResourceAsStream("helpBgImage.png");
        try {
            bgImg = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert bgImg != null;
        Image helpBgImg = bgImg.getScaledInstance(880, 690, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(helpBgImg);

        backgroundImg = new JLabel(image);
        backgroundImg.setSize(880,690);
        backgroundImg.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //help title
        helpTitle = new JLabel("Little Helper");
        helpTitle.setPreferredSize(new Dimension(480, 46));
        helpTitle.setForeground(Color.decode("#e76f51")); //title text color
        helpTitle.setFont(titleFont); //title font
        helpTitle.setHorizontalAlignment(JLabel.CENTER);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(-200,0,0,0);
        backgroundImg.add(helpTitle, constraints);

        // examplesBtn.setPreferredSize(new Dimension(80,30));
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(gainBtn, loseBtn, useBtn, entryBtn, exitBtn, verbsBtn));
        for (JButton button : buttons) {
//            button.addActionListener(this);
            button.setForeground(Color.white);
            button.setBackground(Color.darkGray);
            button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.setPreferredSize(new Dimension(100,35));
            button.setOpaque(true);
            button.setBorderPainted(false);
        }

        //help text panel
        helpText = new JTextArea();
        helpText.setPreferredSize(new Dimension(600, 250));
        helpText.setText("Welcome, I'm your Little Helper! You are in a survival text based game where you take a role of a brave girl named Esperanza." +
                "Your journey is a dangerous one, but with your wits and my guidance I believe you will find your salvation.\n\n" +
                "Pay attention to the story and navigate the game by making your decisions carefully for each choice changes your fate be it for better or worse.\n\n" +
                "Enter only two commands in the text field at a time to progress through the story: 1 verb and 1 noun.");
        helpText.setFont(normalFont);
        helpText.setBackground(Color.decode("#EDE5D0"));
        helpText.setForeground(Color.black);
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpText.setEditable(false);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(50,0,30,0);
        backgroundImg.add(helpText, constraints);

        JPanel buttonPanel = new JPanel();
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

        backgroundImg.add(buttonPanel, constraints);
        //quit screen helper message
        JTextArea howToResumeGame = new JTextArea("-- Press [ESC] to exit help screen --");
        howToResumeGame.setFont(helperFont);
        howToResumeGame.setBackground(Color.decode("#EDE5D0"));
        howToResumeGame.setForeground(Color.BLUE);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.insets = new Insets(100,0,-100,0);
        backgroundImg.add(howToResumeGame, constraints);
        helpPanel.add(backgroundImg);
    }


    @Override
    public void updateDisplay() {

    }

    @Override
    public JPanel respondToInput(KeyEvent keyPressed) {
        return null;
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
}