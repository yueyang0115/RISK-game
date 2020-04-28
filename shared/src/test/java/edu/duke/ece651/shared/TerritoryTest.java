package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class TerritoryTest {
    @Test
    public void Test_Territory(){
        Territory Test = new Territory();
        HashMap<Integer, Integer> mySoldiers = new HashMap<>();
        mySoldiers.put(0, 6);
        Test.setSoldiers(mySoldiers);
        Test.getSoldiers();
        assert (6 == Test.getSoldierLevel(0));
        Test.setTotalSize(5);
        assert (5 == Test.getTotalSize());
        Territory Compared = new Territory();
        Compared.setTotalSize(10);
        Test.compareTo(Compared);
        Action Test_Action = new Action();
        Test_Action.setSoldiers(mySoldiers);
        Test_Action.getSoldiers();
        Test_Action.getSoldierLevel(0);
    }
}
