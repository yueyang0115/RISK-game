package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SoldierTest {
    @Test
    public void test_Soldiers() throws IOException {
        Soldiers Test  = new Soldiers();
        for(int i = 0; i < 7; i++){
            Test.setSoliderNum(i, i * 2 + 1);
            System.out.println("Level " + i + " Bonus: " + Test.getBonus(i));
            System.out.println("Level " + i + " Soldiers: " + Test.getSoldierNum(i));
        }
        System.out.println("Level " + 2 + " to Level " + 6 + " costs: " + Test.getCosts(2, 6));
    }
}
