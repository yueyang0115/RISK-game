package edu.duke.ece651.server;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.shared.*;
import java.util.*;
import org.junit.jupiter.api.Test;
public class ServerCheckerTest {
  private AllianceHelper ah;
  public ServerCheckerTest(){
    ah = new AllianceHelper();
  }

  @Test
  public void test_checkTerritory() {
    WorldInitter myworInitter = new WorldInitter(4);
    HashMap<Integer, ArrayList<Territory>> myworld = myworInitter.getWorld();
    Action myaction = new Action();
    Territory srcTerritory = myworld.get(0).get(0); // player_0.A
    Territory dstTerritory = new Territory();
    dstTerritory.setOwner("player_0");
    dstTerritory.setSoldierLevel(0, 3);
    dstTerritory.setTerritoryName("Z");
    myaction.setSrc(srcTerritory);
    myaction.setDst(dstTerritory);
    myaction.setOwner("player_0");
    myaction.setSoldierLevel(0, 2);
    myaction.setType("Move");

    ServerChecker mychecker = new ServerChecker(myworld,ah);
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
    myaction.setSoldierLevel(0, 2);
    myaction.setType("Attack");

    ServerChecker mychecker = new ServerChecker(myworld,ah);
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
    WorldInitter myworInitter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = myworInitter.getWorld();
    Action myaction = new Action();
    Territory srcTerritory = myworld.get(0).get(0); // player_0.A
    Territory dstTerritory = myworld.get(1).get(0); // player_1.G
    myaction.setSrc(srcTerritory);
    myaction.setDst(dstTerritory);
    myaction.setOwner("player_0");
    myaction.setSoldierLevel(0, 4);
    myaction.setType("Attack");

    ServerChecker mychecker = new ServerChecker(myworld,ah);
    boolean ans = mychecker.Check(myaction);
    assertEquals(ans, false);

    myaction.setSoldierLevel(0, -1);
    boolean ans2 = mychecker.Check(myaction);
    assertEquals(ans2, false);
  }

  @Test
  public void test_dfs() {
    WorldInitter myworInitter = new WorldInitter(4);
    HashMap<Integer, ArrayList<Territory>> myworld = myworInitter.getWorld();
    Action myaction = new Action();
    Territory srcTerritory = myworld.get(0).get(0); // player_0.A
    Territory dstTerritory = myworld.get(1).get(1); // player_1.E
    myaction.setSrc(srcTerritory);
    myaction.setDst(dstTerritory);
    myaction.setOwner("player_0");
    myaction.setSoldierLevel(0, 2);
    myaction.setType("Attack");

    ServerChecker mychecker = new ServerChecker(myworld,ah);
    boolean ans = mychecker.Check(myaction);
    assertEquals(ans, false);

    myaction.setDst(srcTerritory);
    myaction.setType("Move");
    boolean ans2 = mychecker.Check(myaction); // src = dst
    assertEquals(ans2, true);

    dstTerritory = myworld.get(1).get(0); // player_1.D
    myaction.setDst(dstTerritory);
    myaction.setType("Attack");
    boolean ans3 = mychecker.Check(myaction);
    assertEquals(ans3, true);
  }

  @Test
  public void test_alliance(){
    WorldInitter myworInitter = new WorldInitter(4);
    HashMap<Integer, ArrayList<Territory>> myworld = myworInitter.getWorld();
    Action myaction = new Action();
    Territory srcTerritory = myworld.get(0).get(0); // player_0.A
    Territory dstTerritory = myworld.get(1).get(1); // player_1.E
    myaction.setSrc(srcTerritory);
    myaction.setDst(dstTerritory);
    myaction.setOwner("player_0");
    myaction.setSoldierLevel(0, 2);
    myaction.setType("Move"); //pass through alliance

    ah.formNewAlliance(0,1);

    ServerChecker mychecker = new ServerChecker(myworld,ah);
    boolean ans = mychecker.Check(myaction);
    assertEquals(ans, true);  //check move dst can be alliance and path can go through alliance

    myaction.setType("Attack"); //break alliance, check will not pass
    ServerChecker mychecker_2 = new ServerChecker(myworld,ah);
    boolean ans_2 = mychecker_2.Check(myaction);
    assertEquals(ans_2, false); //check attack dst cannot be alliance
  }

}
