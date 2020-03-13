package edu.duke.ece651.server;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.shared.*;
import java.util.*;
import org.junit.jupiter.api.Test;

public class DoActionTest {
  @Test
  public void test_DoMoveAction() {
    WorldInitter initter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = initter.getWorld();
    Action action = new Action();
    Territory territoryA = myworld.get(0).get(0);
    Territory territoryB = myworld.get(0).get(1);
    assertEquals(territoryA.getSoliders(), 3);
    assertEquals(territoryB.getSoliders(), 3);

    action.setDst(territoryA);
    action.setSrc(territoryB);
    action.setSoldiers(3);
    action.setOwner("player_0");
    action.setType("Move");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action);

    DoAction actor = new DoAction(myworld);
    actor.doMoveAction(actionList);
    assertEquals(territoryA.getSoliders(), 6);
    assertEquals(territoryB.getSoliders(), 0);
  }

  @Test
  public void test_DoAttackAtion2() {
    WorldInitter initter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = initter.getWorld();
    Action action = new Action();
    Territory territoryA = myworld.get(0).get(1); // player_0.B
    Territory territoryB = myworld.get(1).get(0); // player_1.E
    assertEquals(territoryA.getTerritoryName(), "B");
    assertEquals(territoryB.getTerritoryName(), "E");
    assertEquals(territoryA.getSoliders(), 3);
    assertEquals(territoryB.getSoliders(), 3);

    action.setSrc(territoryA); // B
    action.setDst(territoryB); // E
    action.setSoldiers(3);
    action.setOwner("player_0");
    action.setType("Attack");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action);

    DoAction actor = new DoAction(myworld);
    actor.doAttackAction(actionList);

    assertEquals(territoryA.getSoliders(), 0); // b
  }

  @Test
  public void test_DoAttackAtion() {
    WorldInitter initter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = initter.getWorld();
    Action action = new Action();
    Territory territoryA = myworld.get(0).get(1); // player_0.B
    Territory territoryB = myworld.get(1).get(0); // player_1.E
    assertEquals(territoryA.getTerritoryName(), "B");
    assertEquals(territoryB.getTerritoryName(), "E");
    assertEquals(territoryA.getSoliders(), 3);
    assertEquals(territoryB.getSoliders(), 3);

    action.setSrc(territoryA); // B
    action.setDst(territoryB); // E
    action.setSoldiers(2);
    action.setOwner("player_0");
    action.setType("Attack");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action);

    DoAction actor = new DoAction(myworld);
    actor.doAttackAction(actionList);

    assertEquals(territoryA.getSoliders(), 1); // b
  }

  @Test
  public void test_DoPlusOne() {
    WorldInitter initter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = initter.getWorld();
    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : myworld.entrySet()) {
      ArrayList<Territory> territoryList = entry.getValue();
      for (int j = 0; j < territoryList.size(); j++) {
        Territory myterritory = territoryList.get(j);
        assertEquals(myterritory.getSoliders(), 3);
      }
    }
    DoAction actor = new DoAction(myworld);
    actor.doPlusOne();
    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : myworld.entrySet()) {
      ArrayList<Territory> territoryList = entry.getValue();
      for (int j = 0; j < territoryList.size(); j++) {
        Territory myterritory = territoryList.get(j);
        assertEquals(myterritory.getSoliders(), 4);
      }
    }
  }
}
