package com.codebusters.game.combat;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CombatSystemTest {
    private List<String> weapons;

    @Before
    public void setUp() {
        weapons = new ArrayList<>();
        CombatSystem.getInstance().getGameWeaponsAndWeights().forEach((key, val) -> weapons.add(key));
    }

    @Test
    public void testCombat_shouldBeNonNull_whenPassedNullValue() {
        assertNotNull(CombatSystem.getInstance().combat(null));
    }

    @Test
    public void  testCalculateUserDmg_userDmgRangeShouldBe5_whenPassedWeaponNotInCombatSystemWeaponAndWeightMap() {
        String notInCombatWeaponMap = "fist";
        int userDmgShouldEqual = 5;

        assertEquals(userDmgShouldEqual, CombatSystem.getInstance().calculateUserDmg(notInCombatWeaponMap));
    }

    @Test
    public void testCalculateUserDmg_userDmgRangeShouldProperlyReflectWeaponUsed_WhenPassedWeaponInCombatSystemWeaponWeightMap() {
        for (String weapon : weapons) {
            int curWeaponWeigth = CombatSystem.getInstance().getGameWeaponsAndWeights().get(weapon);
            int userDmgShouldEqual = curWeaponWeigth + 5;

            int actualUserDmg = CombatSystem.getInstance().calculateUserDmg(weapon);
            assertEquals(userDmgShouldEqual, actualUserDmg);
        }
    }
}