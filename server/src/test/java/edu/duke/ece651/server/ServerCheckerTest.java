package edu.duke.ece651.server;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.shared.*;
import java.util.*;
import org.junit.jupiter.api.Test;
public class ServerCheckerTest {
  @Test
  public void test_dfs() {
    WorldInitter myworInitter = new WorldInitter(4);
    HashMap<Integer, ArrayList<Territory>> myworld = myworInitter.getWorld();
    Action myaction = new Action();
    Territory srcTerritory = myworld.get(0).get(0); // player_0.A
    Territory dstTerritory = myworld.get(3).get(0); // player_3.F
    myaction.setSrc(srcTerritory);
    myaction.setDst(dstTerritory);
    myaction.setOwner("player_0");
    myaction.setSoldiers(2);
    myaction.setType("Attack");

    ServerChecker mychecker = new ServerChecker(myworld);
    boolean ans = mychecker.Check(myaction);
    assertEquals(ans, true);

    myaction.setDst(srcTerritory);
    boolean ans2 = mychecker.Check(myaction); // src = dst
    assertEquals(ans2, true);

    dstTerritory = myworld.get(2).get(2); // player_2.G
    myaction.setDst(dstTerritory);
    boolean ans3 = mychecker.Check(myaction);
    assertEquals(ans3, false);
  }
}
