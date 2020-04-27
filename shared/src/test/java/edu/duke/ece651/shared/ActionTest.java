package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class ActionTest {
    @Test
    public void test_Action(){
        Action Test = new Action();
        HashMap<Integer, Integer> mySoldiers = new HashMap<>();
        mySoldiers.put(0, 2);
        mySoldiers.put(1, 6);
        Test.setSoldiers(mySoldiers);
    }
}
