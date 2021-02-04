package com.codebusters.game;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TextParserTest {

    TextParser parser;
    Game game;

    @Before
    public void setUp() {
        parser = TextParser.getInstance();
        game = new Game();
    }

    @Test
    public void emptyStringOnParseInputReturnsUndefined() {
        assertFalse(parser.parseInput(""));
    }

    @Ignore
    @Test
    public void extraCharactersStringOnParseInputReturnsUndefined() {
        parser.parseInput("start game");
        System.out.println(parser.getNextChapter());
        assertTrue(parser.parseInput("take box"));
    }
}
