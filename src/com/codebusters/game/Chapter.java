package com.codebusters.game;

import java.util.ArrayList;
import java.util.HashMap;

public class Chapter {
    private String chapterId;
    private String chapterName;
    private String sceneText;
    private ArrayList<HashMap> paths = new ArrayList<>();

    public ArrayList<HashMap> getPaths() {
        return paths;
    }

    public void setPaths(HashMap path) {
        if(path == null) return;
        this.paths.add(path);
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        if(chapterId == null) return;
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        if(chapterName == null) return;
        this.chapterName = chapterName;
    }

    public String getSceneText() {
        return this.sceneText;
    }

    public void setSceneText(String sceneText) {
        if(sceneText == null) return;
        this.sceneText = sceneText;
    }

    public String toString() {
        return chapterId + " " + chapterName;
    }
}
