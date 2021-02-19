package com.codebusters.game.util;

import javax.swing.*;

public class DisplayError {
    private static DisplayError instance = null;

    private DisplayError() {

    }

    public static DisplayError getInstance() {
        if (instance == null) {
            instance = new DisplayError();
        }
        return instance;
    }

    public void errorPopup(JFrame window, String message, String title) {
        JOptionPane.showMessageDialog(window, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void errorPopup(JFrame window, String message) {
        JOptionPane.showMessageDialog(window, message);
    }
}