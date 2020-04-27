package edu.duke.ece651.shared;

import java.util.*;
import org.junit.jupiter.api.Test;

public class UpgradetoJsonTest {
    @Test
    public void test_toJSON() {
        ArrayList<Upgrade> UpgradeList = new ArrayList<>();
        Upgrade upgrade_A = new Upgrade();
        upgrade_A.setTerritoryName("A");
        upgrade_A.setOwner("player_0");
        upgrade_A.setPrevLevel(0);
        upgrade_A.setNextLevel(2);
        upgrade_A.setNumber(3);
        UpgradeList.add(upgrade_A);

        Upgrade upgrade_B = new Upgrade();
        upgrade_B.setTerritoryName("B");
        upgrade_B.setOwner("player_1");
        upgrade_B.setPrevLevel(2);
        upgrade_B.setNextLevel(5);
        upgrade_B.setNumber(6);
        UpgradeList.add(upgrade_B);

        UpgradetoJson myUpgradetoJson = new UpgradetoJson(UpgradeList);
        System.out.println("************* TEST UPGRADE TO JSON ****************");
        System.out.println(myUpgradetoJson.getJSON());
    }
}
