package edu.duke.ece651.server;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.shared.*;
import java.util.*;
import org.junit.jupiter.api.Test;
public class ServerCheckerTest {
  @Test
  public void test_checkTerritory() {
    WorldInitter myworInitter = new WorldInitter(4);
    HashMap<Integer, ArrayList<Territory>> myworld = myworInitter.getWorld();
    Action myaction = new Action();
    Territory srcTerritory = myworld.get(0).get(0); // player_0.A
    Territory dstTerritory = new Territory();
    dstTerritory.setOwner("player_0");
    dstTerritory.setSoldiers(3);
    dstTerritory.setTerritoryName("Z");
    myaction.setSrc(srcTerritory);
    myaction.setDst(dstTerritory);
    myaction.setOwner("player_0");
    myaction.setSoldiers(2);
    myaction.setType("Move");

    ServerChecker mychecker = new ServerChecker(myworld);
    boolean ans = mychecker.Check(myaction);
    assertEquals(ans, false);
  }

  @Test
  public void test_checkOwner() {
    WorldInitter myworInitter = new WorldInitter(4);
    HashMap<Integer, ArrayList<Territory>> myworld = myworInitter.getWorld();
    Action myaction = new Action();
    Territory srcTerritory = myworld.get(0).get(0); // player_0.A
    Territory dstTerritory = myworld.get(3).get(0); // player_3.F
    myaction.setSrc(srcTerritory);
    myaction.setDst(dstTerritory);
    myaction.setOwner("player_1");
    myaction.setSoldiers(2);
    myaction.setType("Attack");

    ServerChecker mychecker = new ServerChecker(myworld);
    boolean ans = mychecker.Check(myaction); // srcOwner!=actionOwner
    assertEquals(ans, false);

    myaction.setOwner("player_0");
    Territory dstTerritory2 = myworld.get(0).get(1); // player_0.D
    myaction.setDst(dstTerritory2);
    boolean ans2 = mychecker.Check(myaction); // attack srcOwner = dstOwner
    assertEquals(ans2, false);

    myaction.setType("Move");
    boolean ans3 = mychecker.Check(myaction);
    assertEquals(ans3, true);

    myaction.setDst(dstTerritory);
    boolean ans4 = mychecker.Check(myaction); // move srcOwner!=dstOwner
    assertEquals(ans4, false);
  }

  @Test
  public void test_checkNum() {
    WorldInitter myworInitter = new WorldInitter(4);
    HashMap<Integer, ArrayList<Territory>> myworld = myworInitter.getWorld();
    Action myaction = new Action();
    Territory srcTerritory = myworld.get(0).get(0); // player_0.A
    Territory dstTerritory = myworld.get(3).get(0); // player_3.F
    myaction.setSrc(srcTerritory);
    myaction.setDst(dstTerritory);
    myaction.setOwner("player_0");
    myaction.setSoldiers(4);
    myaction.setType("Attack");

    ServerChecker mychecker = new ServerChecker(myworld);
    boolean ans = mychecker.Check(myaction);
    assertEquals(ans, false);

    myaction.setSoldiers(-1);
    boolean ans2 = mychecker.Check(myaction);
    assertEquals(ans2, false);
  }

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
    myaction.setType("Move");
    boolean ans2 = mychecker.Check(myaction); // src = dst
    assertEquals(ans2, true);

    dstTerritory = myworld.get(2).get(2); // player_2.G
    myaction.setDst(dstTerritory);
    myaction.setType("Attack");
    boolean ans3 = mychecker.Check(myaction);
    assertEquals(ans3, false);
  }
}
