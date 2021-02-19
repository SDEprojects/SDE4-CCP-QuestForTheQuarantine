package com.codebusters.game;

import java.awt.*; //used to access the Color, Dimension, and Toolkit class
import javax.swing.*; //use the JWindow and JLabel class
public class Splash {

    public Splash() {
        JWindow window = new JWindow();
        int duration = 50000;
        int width = 450;
        int height = 115;

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;

        window.setBounds(x, y, width, height);
        window.getContentPane().add(new JLabel("This is a Splash Screen"),"Center");
        window.getContentPane().setBackground(Color.CYAN);
        window.setVisible(true);
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
        }
        window.setVisible(false);
    }
}