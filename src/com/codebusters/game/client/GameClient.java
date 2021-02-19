package com.codebusters.game.client;
/**
 * Main class for the Quest for the Quarantine game.
 *
 * Authors: Bradley Pratt, Debbie Bitencourt, Aliona Demerau, Dustin Morris
 * Last Edited: 02/02/2021
 */
import com.codebusters.game.Game;
import com.codebusters.game.Splash;

public class GameClient {
    public static void main(String[] args) {
        Splash splash = new Splash();
        Game game1 = new Game();
        game1.initialize();
    }
}
