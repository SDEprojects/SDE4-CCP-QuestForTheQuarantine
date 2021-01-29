package com.codebusters.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Chapter {
    private List<String> cities = new ArrayList<>();
    private String chapterId;
    private String chapterName;
    private String sceneText;
    private ArrayList<HashMap> paths = new ArrayList<>();

    public String[] getPathItems(HashMap path) {
        return getItems((String) path.get("items"));
    }

    public String[] getRequiredPathItems(HashMap path) {
        return getItems((String) path.get("requiredItems"));
    }

    private String[] getItems(String items) {
       return items.split(",");
    }

    public ArrayList<HashMap> getPaths() {
        return paths;
    }

    public void setPaths(HashMap path) {
        if(path == null) return;
        this.paths.add(path);
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(String cities) {
        if(cities == null) return;
        String[] cityArray = cities.split(",");
        this.cities = Arrays.asList(cityArray);
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
        return chapterId;
    }
}
