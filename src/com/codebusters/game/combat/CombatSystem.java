package com.codebusters.game.combat;

import java.util.Map;
import java.util.Random;

/**
 * <p>
 * CombatSystem.java - conflict resolution engine for rpg based game
 * Determines if a user wins or loses a fight in game using the combat method
 * default chance of winning a fight is 50%, weapons increase user percentage to win, highest chance to win is 90%
 * with games strongest weapon
 * </p>
 * <pre>
 * <code>
 *     //to utilize in code to get fight winner
 *     boolean isUserWin = CombatSystem.getInstance().combat("pistol");
 * </code>
 * </pre>
 *
 * @author Cody Cronberger
 * @version 1.0
 */
public class CombatSystem {
    private static CombatSystem instance = null;
    //map of weapons in game and their weighted values
    // TODO: potentially generate from xml?
    private static final Map<String, Integer> GAME_WEAPONS_AND_WEIGHTS = Map.of("machete", 3, "pistol", 4, "knife", 1, "axe", 2);
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

    /**
     * <p>
     * Determines the chance of user winning by adjusting chance based on the weight of the weapon user is utilizing
     * When weapon passed in is not in game or possessed by user, defaults to fist which gives a 50% chance to win
     * Complete random chance to win a fight, weapons increase that chance, highest chance to win is 90% based on
     * games most powerful weapons
     * </p>
     * <pre>
     * <code>
     *     prompter.prompt("What would you like to do?", ["GO", "OPEN", "Look"], "I'm sorry I don't understand those commands");
     * </code>
     * </pre>
     *
     * @param userWeapon weapon the user is utilizing in combat
     * @return boolean if true user won, else user lost
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