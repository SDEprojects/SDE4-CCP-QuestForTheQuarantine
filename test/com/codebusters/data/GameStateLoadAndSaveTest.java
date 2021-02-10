package com.codebusters.data;

import com.codebusters.game.GameState;
import org.junit.Before;
import org.junit.Test;

public class GameStateLoadAndSaveTest {
    ChapterBuilder cb;
    GameState gs;

    @Before
    public void initializeClasses() {
        cb = ChapterBuilder.getInstance();
        gs = GameState.getInstance();
    }

    @Test
    public void saveFileToDisk() {

    }
}
