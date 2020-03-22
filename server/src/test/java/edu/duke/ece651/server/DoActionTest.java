package edu.duke.ece651.server;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.shared.*;
import java.util.*;
import org.junit.jupiter.api.Test;

public class DoActionTest {
  /*
  @Test
  public void test_invalidAction() {
    WorldInitter initter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = initter.getWorld();
    Territory territoryA = myworld.get(0).get(0); // A
    Territory territoryB = myworld.get(1).get(0); // B
    assertEquals(territoryA.getSoliders(), 3);
    assertEquals(territoryB.getSoliders(), 3);
    ArrayList<Action> actionList = new ArrayList<>();

    Action action2 = new Action();
    Territory territoryD = myworld.get(0).get(1);
    action2.setSrc(territoryA); // A
    action2.setDst(territoryD); // D
    action2.setSoldiers(3);
    action2.setOwner("player_0");
    action2.setType("Move");
    actionList.add(action2); // valid action, but is deleted

    Action action = new Action();
    action.setSrc(territoryA); // A
    action.setDst(territoryB); // B
    action.setSoldiers(3);
    action.setOwner("player_0");
    action.setType("Move");
    actionList.add(action); // invalid action

    Territory territoryC = myworld.get(1).get(1); // C
    Action action3 = new Action();
    action3.setSrc(territoryB);
    action3.setDst(territoryC);
    action3.setSoldiers(3);
    action3.setOwner("player_1");
    action3.setType("Move");
    ArrayList<Action> actionList2 = new ArrayList<>();
    actionList2.add(action3); // invalid action

    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);
    myactionMap.put(1, actionList2);

    DoAction actor = new DoAction(myworld, myactionMap);
    ArrayList<Action> allActionList = new ArrayList<>();
    allActionList.add(action2);
    allActionList.add(action);
    allActionList.add(action3);

    actor.doMoveAction(allActionList);
    myworld = actor.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoliders(), 3);
    assertEquals(myworld.get(1).get(0).getSoliders(), 0);
    assertEquals(myworld.get(1).get(1).getSoliders(), 6);
    assertEquals(myworld.get(0).get(1).getSoliders(), 3);
    assertEquals(myactionMap.containsKey(0), false);
  }

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
    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);

    DoAction actor = new DoAction(myworld, myactionMap);
    actor.doMoveAction(actionList);
    myworld = actor.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoliders(), 6);
    assertEquals(myworld.get(0).get(1).getSoliders(), 0);

    Action action2 = new Action();
    Territory territoryC = myworld.get(1).get(0);
    Territory territoryD = myworld.get(1).get(1);
    assertEquals(territoryC.getSoliders(), 3);
    assertEquals(territoryD.getSoliders(), 3);

    action2.setDst(territoryC);
    action2.setSrc(territoryD);
    action2.setSoldiers(2);
    action2.setOwner("player_1");
    action2.setType("Move");
    ArrayList<Action> actionList2 = new ArrayList<>();
    actionList2.add(action2);
    HashMap<Integer, ArrayList<Action>> myactionMap2 = new HashMap<>();
    myactionMap2.put(1, actionList2);

    DoAction actor2 = new DoAction(myworld, myactionMap2);
    actor2.doMoveAction(actionList2);
    myworld = actor2.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoliders(), 6);
    assertEquals(myworld.get(0).get(1).getSoliders(), 0);
    assertEquals(myworld.get(1).get(0).getSoliders(), 5);
    assertEquals(myworld.get(1).get(1).getSoliders(), 1);
  }

  @Test
  public void test_DoAttackAtion2() {
    WorldInitter initter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = initter.getWorld();
    Action action = new Action();
    Territory territoryA = myworld.get(0).get(0); // player_0.A
    Territory territoryB = myworld.get(1).get(0); // player_1.B
    assertEquals(territoryA.getTerritoryName(), "A");
    assertEquals(territoryB.getTerritoryName(), "B");
    assertEquals(territoryA.getSoliders(), 3);
    assertEquals(territoryB.getSoliders(), 3);

    action.setSrc(territoryA); // A
    action.setDst(territoryB); // B
    action.setSoldiers(3);
    action.setOwner("player_0");
    action.setType("Attack");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action);

    Action action2 = new Action();
    Territory territoryC = myworld.get(1).get(1);
    action2.setSrc(territoryA); // A
    action2.setDst(territoryC); // C
    action2.setSoldiers(3);
    action2.setOwner("player_1");
    action2.setType("Attack"); // invalid action.owner!=src.owner
    ArrayList<Action> actionList2 = new ArrayList<>();
    actionList2.add(action2);
    // actionList2.add(action2);

    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);
    myactionMap.put(1, actionList2);

    ArrayList<Action> allAction = new ArrayList<>();
    allAction.add(action);
    allAction.add(action2);
    // allAction.add(action2);
    DoAction actor = new DoAction(myworld, myactionMap);
    actor.doAttackAction(allAction);

    myworld = actor.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoliders(), 3);
  }

  @Test
  public void test_DoAttackAtion() {
    WorldInitter initter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = initter.getWorld();
    Action action = new Action();
    Territory territoryA = myworld.get(0).get(0); // player_0.A
    Territory territoryB = myworld.get(1).get(0); // player_1.B
    assertEquals(territoryA.getTerritoryName(), "A");
    assertEquals(territoryB.getTerritoryName(), "B");
    assertEquals(territoryA.getSoliders(), 3);
    assertEquals(territoryB.getSoliders(), 3);

    action.setSrc(territoryA); // B
    action.setDst(territoryB); // E
    action.setSoldiers(2);
    action.setOwner("player_0");
    action.setType("Attack");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action);

    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);

    DoAction actor = new DoAction(myworld, myactionMap);
    actor.doAttackAction(actionList);

    myworld = actor.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoliders(), 1);
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

    ArrayList<Action> actionList = new ArrayList<>();
    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);

    DoAction actor = new DoAction(myworld, myactionMap);

    actor.doPlusOne();
    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : myworld.entrySet()) {
      ArrayList<Territory> territoryList = entry.getValue();
      for (int j = 0; j < territoryList.size(); j++) {
        Territory myterritory = territoryList.get(j);
        assertEquals(myterritory.getSoliders(), 4);
      }
    }
    }*/
}
