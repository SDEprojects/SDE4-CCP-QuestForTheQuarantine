package com.codebusters.game;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {

    Game game1;

    @Before
    public void setUp() {
        game1 = new Game();
    }

    @Ignore
    @Test
    public void startGame() {
        game1.startGame();
    }

    @Test
    public void verifyCurrentPathTextIsUpdating() {
        String oldSceneText = GameState.getInstance().getSceneText();
        String newSceneText = TextParser.getInstance().getPathText() + "\n\n" + "This is a test";
        TextParser.getInstance().setPathText(newSceneText);
        game1.updatePathText();
        assertEquals(newSceneText + "\n\n" + oldSceneText, GameState.getInstance().getSceneText());
    }


    @Test
    public void addItemToInventoryTest() {
        ArrayList<Items> allInventory = GameState.getInstance().getInventory();
        assertEquals(allInventory.size(), 0);
        Items item = new Items("Water");
        Chapter chapter = new Chapter();
        chapter.setChapterName("Mexico City");
        chapter.setSceneText("You are in Mexico");
        game1.addItemToInventory(item);
        game1.updateGameState(chapter);
        assertEquals(GameState.getInstance().getInventory().size(), 1);
    }

    @Test
    public void findItemInInventoryTest() {
        Items item = new Items("Water");
        Chapter chapter = new Chapter();
        chapter.setChapterName("Mexico City");
        chapter.setSceneText("You are in Mexico");
        game1.addItemToInventory(item);
        game1.updateGameState(chapter);
        assertTrue(game1.findItemInInventory(item));
    }

    @Test
    public void removeItemFromInventoryTest() {
        Items item = new Items("Water");
        Chapter chapter = new Chapter();
        chapter.setChapterName("Mexico City");
        chapter.setSceneText("You are in Mexico");
        game1.addItemToInventory(item);
        game1.updateGameState(chapter);
        assertEquals(GameState.getInstance().getInventory().size(), 1);
        game1.removeItemFromInventory(item);
        assertEquals(GameState.getInstance().getInventory().size(), 0);
    }

    @Test
    public void verifyEndChapterOnDeathOrEnd() {
        Chapter chapter = new Chapter();
        chapter.setChapterName("Death");
        chapter.setSceneText("You are in Mexico");
        assertTrue(game1.isEndChapter(chapter));
        chapter.setChapterName("End");
        assertTrue(game1.isEndChapter(chapter));
        chapter.setChapterName("Debbie");
        assertFalse(game1.isEndChapter(chapter));
    }
}
