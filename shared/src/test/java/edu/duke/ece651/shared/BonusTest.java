package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BonusTest {
    @Test
    public void Test_bonus()  {
        Bonus test = new Bonus();
        for(int i = 0; i < 7; i++){
            System.out.println("Level " + i + " Bonus: " + test.getBonus(i));
        }
    }
}
