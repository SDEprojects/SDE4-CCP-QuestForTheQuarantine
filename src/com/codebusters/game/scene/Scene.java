package com.codebusters.game.scene;

import java.awt.event.KeyEvent;

public interface Scene {
    public void updateDisplay();

    public Scene respondToInput(KeyEvent keyPressed);
}