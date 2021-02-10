package com.codebusters.game;

import com.codebusters.data.ChapterBuilder;
import org.junit.Before;
import org.junit.Test;
import com.codebusters.game.Chapter;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TextParserTest {

    Chapter currentChapter;
    private ArrayList<Chapter> story;
    private ArrayList<Items> inventory;

    @Before
    public void setUp() {
        currentChapter = new Chapter();
        ChapterBuilder builder = new ChapterBuilder();
        story = builder.getChapters();
        currentChapter = story.get(3);
        inventory = new ArrayList<>();
        inventory.add(new Items("machete"));
        TextParser.getInstance().setCurrentChapter(currentChapter, inventory);
    }

    @Test
    public void testParseInput_emptyString_returnsFalse() {
        TextParser.getInstance().parseInput("");
        assertFalse(TextParser.getInstance().isValidInput());
    }

    @Test
    public void testParseInput_invalidInput_returnsFalse() {
        TextParser.getInstance().parseInput("hello");
        assertFalse(TextParser.getInstance().isValidInput());
        TextParser.getInstance().parseInput("exit city");
        assertFalse(TextParser.getInstance().isValidInput());
        TextParser.getInstance().parseInput("skip city");
        assertFalse(TextParser.getInstance().isValidInput());
        TextParser.getInstance().parseInput("enter the city");
        assertFalse(TextParser.getInstance().isValidInput());
        TextParser.getInstance().parseInput("city enter");
        assertFalse(TextParser.getInstance().isValidInput());
    }

    @Test
    public void testParseInput_validInput_returnsTrue() {
        TextParser.getInstance().parseInput("enter city");
        assertTrue(TextParser.getInstance().isValidInput());
        TextParser.getInstance().parseInput("access city");
        assertTrue(TextParser.getInstance().isValidInput());
        TextParser.getInstance().parseInput("skip city");
        assertFalse(TextParser.getInstance().isValidInput());
        inventory.add(new Items("water"));
        TextParser.getInstance().parseInput("skip city");
        assertTrue(TextParser.getInstance().isValidInput());
        TextParser.getInstance().parseInput("EnTeR CiTy");
        assertTrue(TextParser.getInstance().isValidInput());
        TextParser.getInstance().parseInput("EnTeR...??;:CiTy");
        assertTrue(TextParser.getInstance().isValidInput());
    }

    @Test
    public void testGetNextChapter_validInput_returns4() {
        assertNotEquals(TextParser.getInstance().getNextChapter(), "4");
        inventory.add(new Items("water"));
        TextParser.getInstance().parseInput("skip city");
        assertEquals(TextParser.getInstance().getNextChapter(), "4");
    }

    @Test
    public void testGetNextChapter_invalidInput_returnsNot4() {
        assertNotEquals(TextParser.getInstance().getNextChapter(), "4");
        TextParser.getInstance().parseInput("enter the city");
        assertNotEquals(TextParser.getInstance().getNextChapter(), "4");
    }

}
