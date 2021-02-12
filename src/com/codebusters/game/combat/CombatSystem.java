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
    private static CombatSystem instance = null;
    //map of weapons in game and their weighted values
    // TODO: potentially generate from xml?
    private static final Map<String, Integer> GAME_WEAPONS_AND_WEIGHTS = Map.of("machete", 3, "pistol", 4, "pocket knife", 1, "axe", 2);
    //maximum number range
    private static final int MAX_DMG_RANGE = 10;
    //minimum number range
    private static final int MIN_DMG_RANGE = 1;
    //user with no weapons has a damage range of 1-5, weapons weight add to that value
    private static int userDmgRange = 5;
    //needed to generate a random number
    private final Random rand;

    private CombatSystem() {
        //add later
        rand = new Random();
    }

    //get instance of CombatSystem
    public static CombatSystem getInstance() {
        if (instance == null) {
            instance = new CombatSystem();
        }
        return instance;
    }
    /*
     * combat method takes the weapon the user is choosing to use in combat
     * and calculates who wins the battle based on random chance
     * If the random winning number falls in the range of the user, user wins and true is returned
     * otherwise user loses and false is returned
     */
    public boolean combat(String userWeapon) {
        //calculate userDmg based on weapon passed in
        int userDmg = calculateUserDmg(userWeapon);

        boolean isUserWin = false;

        //generate the winning number
        int winningNumber = generateBattleRangeNumber();

        //if the winning number is within the users damage range, user wins is true
        if (winningNumber <= userDmg) {
            isUserWin = true;
        }
        return isUserWin;
    }

    /*
     * takes weapon string in and returns the users damage range based of weapon weight
     * If the weapon passed in does not match any in the GAME_WEAPONS_AND_WEIGHTS Map, default damage range is 5
     */
    public int calculateUserDmg(String weapon) {
        //check for null
        if (weapon == null) {
            weapon = "fist";
        }
        //send input param to lowercase to match Map keys
        else {
            weapon = weapon.toLowerCase();
        }

        int userDmg = getUserDmgRange();
        int weaponDmg = 0;

        if (GAME_WEAPONS_AND_WEIGHTS.containsKey(weapon)) {
            weaponDmg = GAME_WEAPONS_AND_WEIGHTS.get(weapon);
        }

        return userDmg + weaponDmg;
    }

    /*
     * generates the winning damage number within the min and max damage range
     */
    private int generateBattleRangeNumber() {
        return rand.nextInt(MAX_DMG_RANGE) + MIN_DMG_RANGE;
    }

    public int getUserDmgRange() {
        return userDmgRange;
    }

    public Map<String, Integer> getGameWeaponsAndWeights() {
        return GAME_WEAPONS_AND_WEIGHTS;
    }
}