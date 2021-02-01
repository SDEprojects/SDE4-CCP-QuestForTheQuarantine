package com.codebusters.data;

import com.codebusters.game.Chapter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ChapterBuilderTest {
    ChapterBuilder cb;
    @Before
    public void initializeChaptersTest() {
        cb = new ChapterBuilder();
    }

    @Test
    public void printChapters() {
        Iterator cbIterator = cb.getStory().entrySet().iterator();
        while(cbIterator.hasNext()){
            Map.Entry table = (Map.Entry) cbIterator.next();
            System.out.println(table);
        }
    }

    @Test
    public void verifyTablesExist() {
        assertTrue(cb.getStory().containsKey("Chapters"));
        assertTrue(cb.getStory().containsKey("Paths"));
    }

    @Ignore
    @Test
    public void verifyPathsInFirstEntryPathName() {
        HashMap firstPaths = cb.getStory().get("Paths").get(0);
        assertTrue(firstPaths.get("pathName").equals("Intro"));
    }

    @Ignore
    @Test
    public void builderTest() {
        assertTrue(cb.getChapters().size() == 16);
        Chapter zacatecas = cb.getChapters().get(4);
        assertTrue(zacatecas.getChapterName().equals("Zacatecas"));
        assertTrue(zacatecas.getChapterId().equals("5"));
        ArrayList zPaths = zacatecas.getPaths();
        HashMap path = (HashMap) zPaths.get(0);
        assertTrue(path.get("pathName").equals("Go Around"));
        assertTrue(path.get("chapterId").equals("5"));
        assertTrue(path.get("pathId").equals("7"));
        assertTrue(path.get("actions").equals("skip"));
        assertTrue(path.get("items").equals("water[1], food[1]"));
        assertTrue(path.get("requiredItems") == null);
    }
}