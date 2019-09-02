package com.snakehunter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class DiceTest {

    private Dice dice;

    @Before
    public void setUp() throws Exception {
        dice = new Dice();
    }

    @Test
    public void testRollRange() throws Exception {
        // run a few times to make sure that number doesn't exceed the maximum/minimum.
        for (int i = 0; i < 50; i++) {
            int num = dice.roll();
            if (num < Dice.DICE_NUM_MIN || num > Dice.DICE_NUM_MAX) {
                throw new Exception();
            }
        }
    }
}