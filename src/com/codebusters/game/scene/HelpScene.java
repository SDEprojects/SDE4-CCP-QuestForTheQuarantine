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
        GridBagConstraints c = new GridBagConstraints(); //needed to specify constraints for components

        //help title
        helpTitle = new JLabel("Little Helper");
        helpTitle.setPreferredSize(new Dimension(480, 46));
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0,110, 30, -100);
        helpTitle.setForeground(Color.decode("#e76f51")); //title text color
        helpTitle.setFont(titleFont); //title font
        backgroundImg.add(helpTitle, c);

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
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.insets = new Insets(0, 140, 0, -80);
        helpText.setBackground(Color.decode("#EDE5D0"));
        backgroundImg.add(helpText, c);

        JPanel buttonPanel = new JPanel();
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(0,-20, 0, -20);
        buttonPanel.setBackground(Color.decode("#EDE5D0"));
        buttonPanel.setLayout(new GridLayout(2,3, 4,4));
        for (JButton button : buttons) {
            button.setOpaque(true);
            button.setBorderPainted(false);
            buttonPanel.add(button);
        }

        backgroundImg.add(buttonPanel, c);
        helpPanel.add(backgroundImg);
    }


    @Override
    public void updateDisplay() {

    }

    @Override
    public Scene respondToInput(KeyEvent keyPressed) {
        return null;
    }

    public JPanel getHelpPanel() {
        return helpPanel;
    }
}