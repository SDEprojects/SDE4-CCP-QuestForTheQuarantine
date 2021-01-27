package com.codebusters.game;

import java.util.ArrayList;

public class Game {
    public GameState currentGame;
    public Viewer GUI;
    public TextParser parser;
    public ArrayList<Items> inventory;
    public ArrayList<Chapter> story;

    public Game() {
        currentGame = new GameState();
        GUI = new Viewer();
        parser = new TextParser();
        inventory = new ArrayList<>();
        story = new ArrayList<>();
    }

    public void startGame(){
        boolean endGame = false;
        Chapter currentChapter = story.get(0);

        while (!endGame){
            updateGameState(currentChapter);
            updateViewer();
        }
    }
}
