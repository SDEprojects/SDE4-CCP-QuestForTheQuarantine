package com.codebusters.game;

import java.awt.*; //used to access the Color, Dimension, and Toolkit class
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*; //use the JWindow and JLabel class
public class Splash {

    private JProgressBar progressBar;
    private Task task;
    private JWindow window;

    public Splash() {
        displaySplash();
    }

    private class Task extends Thread {
        public Task(){
        }
        public void run(){
            for(int i =0; i<= 100; i+=2){
                final int progress = i;

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        progressBar.setValue(progress);
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
        }
    }





    public void displaySplash() {
        window = new JWindow();

        UIManager.put("ProgressBar.border",
                BorderFactory.createLineBorder(Color.black, 2));
        UIManager.put("ProgressBar.selectionBackground",Color.black);
        UIManager.put("ProgressBar.selectionForeground",Color.white);
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setBounds(185,575,450,25);
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.ORANGE);
        int duration = 5000;
        int width = 820;
        int height = 820;

        BufferedImage img = null;
        InputStream is = getClass().getClassLoader().getResourceAsStream("Quest-For-Quarantine.png");
        try {
            assert is != null;
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;
        Image bgImg = img.getScaledInstance(820, 820, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(bgImg);
        //adding background image to JLabel
        JLabel backgroundImg = new JLabel(imageIcon);
        backgroundImg.setSize(820,820);
        backgroundImg.add(progressBar);


        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;

        window.setBounds(x, y, width, height);
        window.add(backgroundImg);
        JPanel jpanel = new JPanel();
        task = new Task();
        task.start();
        //jpanel.add(progressBar);
        window.add(jpanel);
        window.setVisible(true);
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
        }

        window.setVisible(false);
    }
}