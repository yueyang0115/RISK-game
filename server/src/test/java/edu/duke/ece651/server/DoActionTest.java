package edu.duke.ece651.server;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.shared.*;
import java.util.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class DoActionTest {
  @Test
  public void test_invalidMoveAction() {
    WorldInitter initter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = initter.getWorld();

    MyFormatter formatter = new MyFormatter(2);
    String Astr =
        "{'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'},{'neighbor_2':'E'}], 'territoryName':'A'}";
    Territory territoryA = new Territory();
    JSONObject tempA = new JSONObject(Astr);
    territoryA = formatter.JsonToTerritory(tempA);

    String Bstr =
        "{'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'E'}], 'territoryName':'B'}";
    Territory territoryB = new Territory();
    JSONObject tempB = new JSONObject(Bstr);
    territoryB = formatter.JsonToTerritory(tempB);

    String Cstr =
        "{'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'E'},{'neighbor_2':'F'},{'neighbor_3':'G'}], 'territoryName':'C'}";
    Territory territoryC = new Territory();
    JSONObject tempC = new JSONObject(Cstr);
    territoryC = formatter.JsonToTerritory(tempC);

    String Dstr =
        "{'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'E'},{'neighbor_2':'H'}], 'territoryName':'D'}";
    Territory territoryD = new Territory();
    JSONObject tempD = new JSONObject(Dstr);
    territoryD = formatter.JsonToTerritory(tempD);

    assertEquals(territoryA.getSoliders(), 3);
    assertEquals(territoryB.getSoliders(), 3);
    assertEquals(territoryC.getSoliders(), 3);
    assertEquals(territoryD.getSoliders(), 3);
    ArrayList<Action> actionList = new ArrayList<>();

    Action action2 = new Action();
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

    Action action3 = new Action();
    action3.setSrc(territoryB);
    action3.setDst(territoryC);
    action3.setSoldiers(3);
    action3.setOwner("player_1");
    action3.setType("Move");
    ArrayList<Action> actionList2 = new ArrayList<>();
    actionList2.add(action3); // valid action

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
    MaptoJson myMaptoJson = new MaptoJson(myworld);
    System.out.println("[DEBUG] outside doactionclass, new world is:" + myMaptoJson.getJSON());
  }

  @Test
  public void test_invalidAttackAction() {
    WorldInitter initter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = initter.getWorld();

    MyFormatter formatter = new MyFormatter(2);
    String Astr =
        "{'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'},{'neighbor_2':'E'}], 'territoryName':'A'}";
    Territory territoryA = new Territory();
    JSONObject tempA = new JSONObject(Astr);
    territoryA = formatter.JsonToTerritory(tempA);

    String Bstr =
        "{'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'E'}], 'territoryName':'B'}";
    Territory territoryB = new Territory();
    JSONObject tempB = new JSONObject(Bstr);
    territoryB = formatter.JsonToTerritory(tempB);

    String Cstr =
        "{'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'E'},{'neighbor_2':'F'},{'neighbor_3':'G'}], 'territoryName':'C'}";
    Territory territoryC = new Territory();
    JSONObject tempC = new JSONObject(Cstr);
    territoryC = formatter.JsonToTerritory(tempC);

    String Dstr =
        "{'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'E'},{'neighbor_2':'H'}], 'territoryName':'D'}";
    Territory territoryD = new Territory();
    JSONObject tempD = new JSONObject(Dstr);
    territoryD = formatter.JsonToTerritory(tempD);

    String Estr =
        "{'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'B'},{'neighbor_2':'C'},{'neighbor_3':'D'},{'neighbor_4':'F'},{'neighbor_5':'H'},{'neighbor_6':'J'}], 'territoryName':'E'}";
    Territory territoryE = new Territory();
    JSONObject tempE = new JSONObject(Estr);
    territoryE = formatter.JsonToTerritory(tempE);

    assertEquals(territoryA.getSoliders(), 3);
    assertEquals(territoryB.getSoliders(), 3);
    assertEquals(territoryC.getSoliders(), 3);
    assertEquals(territoryD.getSoliders(), 3);
    ArrayList<Action> actionList = new ArrayList<>();

    Action action2 = new Action();
    action2.setSrc(territoryA); // A
    action2.setDst(territoryB); // B
    action2.setSoldiers(3);
    action2.setOwner("player_0");
    action2.setType("Attack");
    actionList.add(action2); // valid action, but is deleted

    Action action = new Action();
    action.setSrc(territoryA); // A
    action.setDst(territoryD); // D
    action.setSoldiers(3);
    action.setOwner("player_0");
    action.setType("Attack");
    actionList.add(action); // invalid action

    Action action3 = new Action();
    action3.setSrc(territoryB); // B
    action3.setDst(territoryE); // E
    action3.setSoldiers(3);
    action3.setOwner("player_1");
    action3.setType("Attack");
    ArrayList<Action> actionList2 = new ArrayList<>();
    actionList2.add(action3); // valid action

    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);
    myactionMap.put(1, actionList2);

    DoAction actor = new DoAction(myworld, myactionMap);
    ArrayList<Action> allActionList = new ArrayList<>();
    allActionList.add(action2);
    allActionList.add(action);
    allActionList.add(action3);

    actor.doAttackAction(allActionList);
    myworld = actor.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoliders(), 3); // A
    assertEquals(myworld.get(1).get(0).getSoliders(), 0); // B
    assertEquals(myworld.get(1).get(1).getSoliders(), 3); // C
    assertEquals(myactionMap.containsKey(0), false);
    MaptoJson myMaptoJson = new MaptoJson(myworld);
    System.out.println("[DEBUG] outside doactionclass, new world is:" + myMaptoJson.getJSON());
  }

  @Test
  public void test_DoMoveAction() {
    WorldInitter initter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = initter.getWorld();

    MyFormatter formatter = new MyFormatter(2);
    String Astr =
        "{'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'},{'neighbor_2':'E'}], 'territoryName':'A'}";
    Territory territoryA = new Territory();
    JSONObject tempA = new JSONObject(Astr);
    territoryA = formatter.JsonToTerritory(tempA);

    String Bstr =
        "{'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'E'}], 'territoryName':'B'}";
    Territory territoryB = new Territory();
    JSONObject tempB = new JSONObject(Bstr);
    territoryB = formatter.JsonToTerritory(tempB);

    String Cstr =
        "{'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'E'},{'neighbor_2':'F'},{'neighbor_3':'G'}], 'territoryName':'C'}";
    Territory territoryC = new Territory();
    JSONObject tempC = new JSONObject(Cstr);
    territoryC = formatter.JsonToTerritory(tempC);

    String Dstr =
        "{'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'E'},{'neighbor_2':'H'}], 'territoryName':'D'}";
    Territory territoryD = new Territory();
    JSONObject tempD = new JSONObject(Dstr);
    territoryD = formatter.JsonToTerritory(tempD);

    assertEquals(territoryA.getSoliders(), 3);
    assertEquals(territoryB.getSoliders(), 3);
    assertEquals(territoryC.getSoliders(), 3);
    assertEquals(territoryD.getSoliders(), 3);

    Action action = new Action();
    action.setSrc(territoryA);
    action.setDst(territoryD);
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
    assertEquals(myworld.get(0).get(0).getSoliders(), 0);
    assertEquals(myworld.get(0).get(1).getSoliders(), 6);

    Action action2 = new Action();
    action2.setSrc(territoryB);
    action2.setDst(territoryC);
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
    assertEquals(myworld.get(0).get(0).getSoliders(), 0);
    assertEquals(myworld.get(0).get(1).getSoliders(), 6);
    assertEquals(myworld.get(1).get(0).getSoliders(), 1);
    assertEquals(myworld.get(1).get(1).getSoliders(), 5);
  }

  @Test
  public void test_DoAttackAtion2() {
    WorldInitter initter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = initter.getWorld();
    MyFormatter formatter = new MyFormatter(2);

    String Astr =
        "{'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'},{'neighbor_2':'E'}], 'territoryName':'A'}";
    Territory territoryA = new Territory();
    JSONObject tempA = new JSONObject(Astr);
    territoryA = formatter.JsonToTerritory(tempA);

    String Bstr =
        "{'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'E'}], 'territoryName':'B'}";
    Territory territoryB = new Territory();
    JSONObject tempB = new JSONObject(Bstr);
    territoryB = formatter.JsonToTerritory(tempB);

    String Cstr =
        "{'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'E'},{'neighbor_2':'F'},{'neighbor_3':'G'}], 'territoryName':'C'}";
    Territory territoryC = new Territory();
    JSONObject tempC = new JSONObject(Cstr);
    territoryC = formatter.JsonToTerritory(tempC);

    assertEquals(territoryA.getTerritoryName(), "A");
    assertEquals(territoryB.getTerritoryName(), "B");
    assertEquals(territoryA.getSoliders(), 3);
    assertEquals(territoryB.getSoliders(), 3);

    Action action = new Action();
    action.setSrc(territoryA); // A
    action.setDst(territoryB); // B
    action.setSoldiers(3);
    action.setOwner("player_0");
    action.setType("Attack");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action);

    Action action2 = new Action();
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
    assertEquals(myworld.get(0).get(0).getSoliders(), 0);
  }

  @Test
  public void test_DoAttackAtion() {
    WorldInitter initter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld = initter.getWorld();

    MyFormatter formatter = new MyFormatter(2);
    String Astr =
        "{'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'},{'neighbor_2':'E'}], 'territoryName':'A'}";
    Territory territoryA = new Territory();
    JSONObject tempA = new JSONObject(Astr);
    territoryA = formatter.JsonToTerritory(tempA);

    String Bstr =
        "{'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'E'}], 'territoryName':'B'}";
    Territory territoryB = new Territory();
    JSONObject tempB = new JSONObject(Bstr);
    territoryB = formatter.JsonToTerritory(tempB);

    Action action = new Action();
    assertEquals(territoryA.getTerritoryName(), "A");
    assertEquals(territoryB.getTerritoryName(), "B");
    assertEquals(territoryA.getSoliders(), 3);
    assertEquals(territoryB.getSoliders(), 3);

    action.setSrc(territoryA); // B
    action.setDst(territoryB); // E
    action.setSoldiers(3);
    action.setOwner("player_0");
    action.setType("Attack");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action);

    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);

    DoAction actor = new DoAction(myworld, myactionMap);
    actor.doAttackAction(actionList);

    myworld = actor.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoliders(), 0);
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
  }
}
