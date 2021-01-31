package com.codebusters.game.client;

import com.codebusters.game.Game;

import java.io.IOException;

/**
 * Main class for the Quest for the Quarantine game.
 *
 * Authors: Bradley Pratt, Debbie Bitencourt, Aliona Demerau, Dustin Morris
 * Last Edited: 01/27/2021
 */
import com.codebusters.game.Game;
import java.io.IOException;

public class GameClient {
    public static void main(String[] args) throws IOException {
        Game game1 = new Game();
        game1.startGame();
    }
}
