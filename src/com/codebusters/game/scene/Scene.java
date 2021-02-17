package com.codebusters.game.scene;

import javax.swing.*;
import java.awt.event.KeyEvent;

//TODO: determine what happens on keypress
public interface Scene {
    public void updateDisplay();

    public JPanel respondToInput(KeyEvent keyPressed);
}