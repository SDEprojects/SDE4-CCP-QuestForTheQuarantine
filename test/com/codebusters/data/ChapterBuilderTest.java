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
        cb = ChapterBuilder.getInstance();
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


    @Test
    public void builderTest() {
        assertTrue(cb.getChapters().size() == 15);
        Chapter leon = cb.getChapters().get(4);
        assertTrue(leon.getChapterName().equals("Leon"));
        assertTrue(leon.getChapterId().equals("3"));
        ArrayList zPaths = leon.getPaths();
        HashMap path = (HashMap) zPaths.get(0);
        assertTrue(path.get("pathName").equals("Enter Apartment"));
        assertTrue(path.get("chapterId").equals("3"));
        assertTrue(path.get("pathId").equals("8"));
        assertTrue(path.get("pathText") != null && path.get("pathText").toString().length() > 10);
        assertTrue(path.get("nextId").equals("3A"));
        assertTrue(path.get("verb").equals("enter"));
        assertTrue(path.get("gainItems") == null);
        assertTrue(path.get("noun").equals("apartment"));
        assertTrue(path.get("loseItems") == null);
        assertTrue(path.get("requiredItems") == null);
    }
}