package com.codebusters.game.scene;

import javax.swing.*;
import java.awt.event.KeyEvent;

public interface Scene {
    public void display(JPanel scene);

    public Scene respondToInput(KeyEvent keyPressed);
}