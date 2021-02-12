package com.codebusters.game.combat;

import java.util.Map;
import java.util.Random;

/*
 * CombatSystem sets up how fights between user and enemies work in game
 * With no weapons users will have a 50% chance of winning a fight,
 * weapons have a value that increases the chances based on a weighted value of the weapon used
 * example: fist - 5 so 5 in 10 chance of winning, pistol has weight of 4 - 5+4 = 9 -> 9 in 10 chance 90%
 * current version only allows one chance, user either wins or loses
 */
public class CombatSystem {
    private static CombatSystem instance;
    //map of weapons in game and their weighted values
    // TODO: potentially generate from xml?
    private static final Map<String, Integer> GAME_WEAPONS_AND_WEIGHTS = Map.of("machete", 3, "pistol", 4, "pocket knife", 1, "axe", 2);
    //user with no weapons has a damage range of 1-5, weapons weight add to that value
    private static int userDmgRange = 5;
    //needed to generate a random number
    private final Random rand;

    private CombatSystem() {
        //add later
        rand = new Random();
    }

    /*
     * combat method
     */
    public static boolean combat(String userWeapon) {
        return false;
    }

    /*
     * getter for CombatSystem singleton
     */
    public CombatSystem getInstance() {
        if (instance == null) {
            instance = new CombatSystem();
        }
        return instance;
    }

    public void setUserDmgRange(int range) {
        userDmgRange = range;
    }

    public int getUserDmgRange() {
        return userDmgRange;
    }
}