package com.codebusters.data;

import com.codebusters.game.GameState;
import org.junit.Before;
import org.junit.Test;

public class GameStateLoadAndSaveTest {
    ChapterBuilder cb;
    GameState gs;
    @Before
    public void initializeClasses() {
        cb = new ChapterBuilder();
        gs = GameState.getInstance();
    }

    @Test
    public void saveFileToDisk() {
       // GameState.saveGame();
    }
}
