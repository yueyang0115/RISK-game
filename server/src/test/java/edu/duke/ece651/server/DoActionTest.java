package edu.duke.ece651.server;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.shared.*;
import java.io.*;
import java.util.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class DoActionTest {
  private HashMap<Integer, Integer> resource;

  public DoActionTest() {
    resource = new HashMap<>();
    resource.put(0, 100);
    resource.put(1, 100);
    resource.put(2, 100);
    resource.put(3, 100);
  }
  @Test
  public void test_moveattack() {
    HashMap<Integer, ArrayList<Territory>> myworld = new HashMap<>();
    StringBuilder fileName = new StringBuilder();
    fileName.append("/old/world2.json");
    InputStream input = getClass().getResourceAsStream(fileName.toString());

    Scanner scanner = new Scanner(input);
    StringBuilder worldInfo = new StringBuilder();
    while (scanner.hasNext()) {
      worldInfo.append(scanner.next());
    }
    MyFormatter tempformatter = new MyFormatter(2);
    tempformatter.MapParse(myworld, worldInfo.toString());

    MyFormatter formatter = new MyFormatter(2);
    String Astr =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'},{'neighbor_2':'E'}], 'territoryName':'A'}";
    Territory territoryA = new Territory();
    JSONObject tempA = new JSONObject(Astr);
    territoryA = formatter.JsonToTerritory(tempA);

    String Dstr =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'E'},{'neighbor_2':'H'}], 'territoryName':'D'}";
    Territory territoryD = new Territory();
    JSONObject tempD = new JSONObject(Dstr);
    territoryD = formatter.JsonToTerritory(tempD);

    String Estr =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'B'},{'neighbor_2':'C'},{'neighbor_3':'D'},{'neighbor_4':'F'},{'neighbor_5':'H'},{'neighbor_6':'J'}], 'territoryName':'E'}";
    Territory territoryE = new Territory();
    JSONObject tempE = new JSONObject(Estr);
    territoryE = formatter.JsonToTerritory(tempE);

    String Fstr =
        "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'C'},{'neighbor_1':'E'},{'neighbor_2':'G'},{'neighbor_3':'K'}], 'territoryName':'F'}";
    Territory territoryF = new Territory();
    JSONObject tempF = new JSONObject(Fstr);
    territoryF = formatter.JsonToTerritory(tempF);
    ArrayList<Action> actionList = new ArrayList<>();

    Action action = new Action();
    action.setSrc(territoryA); // A
    action.setDst(territoryE); // E
    action.setSoldierLevel(0, 3);
    action.setOwner("player_0");
    action.setType("Move");
    actionList.add(action); // invalid action

    Action action2 = new Action();
    action2.setSrc(territoryD); // D
    action2.setDst(territoryE); // E
    action2.setSoldierLevel(0, 3);
    action2.setOwner("player_0");
    action2.setType("Move");
    actionList.add(action2); // invalid action

    Action action3 = new Action();
    action3.setSrc(territoryE); // E
    action3.setDst(territoryF); // F
    action3.setSoldierLevel(0, 3);
    action3.setOwner("player_0");
    action3.setType("Attack");
    actionList.add(action3); // invalid action

    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);

    ArrayList<Action> moveList = new ArrayList<>();
    moveList.add(action);
    moveList.add(action2);

    ArrayList<Action> attackList = new ArrayList<>();
    attackList.add(action3);

    HashMap<Integer, Integer> resource = new HashMap<>();
    resource.put(0, 100);
    resource.put(1, 100);
    resource.put(2, 100);
    resource.put(3, 100);
    DoAction actor = new DoAction(myworld, myactionMap, resource);
    actor.doMoveAction(moveList);
    actor.doAttackAction(attackList);
    myworld = actor.getNewWorld();

    assertEquals(myworld.get(0).get(0).getSoldierLevel(0), 0); // A
    assertEquals(myworld.get(0).get(1).getSoldierLevel(0), 0); // D
    assertEquals(myworld.get(0).get(2).getSoldierLevel(0), 6); // E
    MaptoJson myMaptoJson = new MaptoJson(myworld);
    System.out.println("[DEBUG] outside doactionclass, new world is:" + myMaptoJson.getJSON());
  }

  @Test
  public void test_invalidMoveAction() {
    HashMap<Integer, ArrayList<Territory>> myworld = new HashMap<>();
    StringBuilder fileName = new StringBuilder();
    fileName.append("/old/world2.json");
    InputStream input = getClass().getResourceAsStream(fileName.toString());

    Scanner scanner = new Scanner(input);
    StringBuilder worldInfo = new StringBuilder();
    while (scanner.hasNext()) {
      worldInfo.append(scanner.next());
    }
    MyFormatter tempformatter = new MyFormatter(2);
    tempformatter.MapParse(myworld, worldInfo.toString());

    MyFormatter formatter = new MyFormatter(2);
    String Astr =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'},{'neighbor_2':'E'}], 'territoryName':'A'}";
    Territory territoryA = new Territory();
    JSONObject tempA = new JSONObject(Astr);
    territoryA = formatter.JsonToTerritory(tempA);

    String Bstr =
        "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'E'}], 'territoryName':'B'}";
    Territory territoryB = new Territory();
    JSONObject tempB = new JSONObject(Bstr);
    territoryB = formatter.JsonToTerritory(tempB);

    String Cstr =
        "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'E'},{'neighbor_2':'F'},{'neighbor_3':'G'}], 'territoryName':'C'}";
    Territory territoryC = new Territory();
    JSONObject tempC = new JSONObject(Cstr);
    territoryC = formatter.JsonToTerritory(tempC);

    String Dstr =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'E'},{'neighbor_2':'H'}], 'territoryName':'D'}";
    Territory territoryD = new Territory();
    JSONObject tempD = new JSONObject(Dstr);
    territoryD = formatter.JsonToTerritory(tempD);

    assertEquals(territoryA.getSoldierLevel(0), 3);
    assertEquals(territoryB.getSoldierLevel(0), 3);
    assertEquals(territoryC.getSoldierLevel(0), 3);
    assertEquals(territoryD.getSoldierLevel(0), 3);
    ArrayList<Action> actionList = new ArrayList<>();

    Action action2 = new Action();
    action2.setSrc(territoryA); // A
    action2.setDst(territoryD); // D
    action2.setSoldierLevel(0, 3);
    action2.setOwner("player_0");
    action2.setType("Move");
    actionList.add(action2); // valid action, but is deleted

    Action action = new Action();
    action.setSrc(territoryA); // A
    action.setDst(territoryB); // B
    action.setSoldierLevel(0, 3);
    action.setOwner("player_0");
    action.setType("Move");
    actionList.add(action); // invalid action

    Action action3 = new Action();
    action3.setSrc(territoryB);
    action3.setDst(territoryC);
    action3.setSoldierLevel(0, 3);
    action3.setOwner("player_1");
    action3.setType("Move");
    ArrayList<Action> actionList2 = new ArrayList<>();
    actionList2.add(action3); // valid action

    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);
    myactionMap.put(1, actionList2);

    DoAction actor = new DoAction(myworld, myactionMap, resource);
    ArrayList<Action> allActionList = new ArrayList<>();
    allActionList.add(action2);
    allActionList.add(action);
    allActionList.add(action3);

    actor.doMoveAction(allActionList);
    myworld = actor.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoldierLevel(0), 3);
    assertEquals(myworld.get(1).get(0).getSoldierLevel(0), 0);
    assertEquals(myworld.get(1).get(1).getSoldierLevel(0), 6);
    assertEquals(myworld.get(0).get(1).getSoldierLevel(0), 3);
    assertEquals(myactionMap.containsKey(0), false);
    MaptoJson myMaptoJson = new MaptoJson(myworld);
    System.out.println("[DEBUG] outside doactionclass, new world is:" + myMaptoJson.getJSON());
    MyFormatter formatter2 = new MyFormatter(2);
    System.out.println("[DEBUG] outside doactionclass, new actionmap is:"
        + formatter2.AllActionCompose(myactionMap));
  }

  @Test
  public void test_invalidAttackAction() {
    HashMap<Integer, ArrayList<Territory>> myworld = new HashMap<>();
    StringBuilder fileName = new StringBuilder();
    fileName.append("/old/world2.json");
    InputStream input = getClass().getResourceAsStream(fileName.toString());

    Scanner scanner = new Scanner(input);
    StringBuilder worldInfo = new StringBuilder();
    while (scanner.hasNext()) {
      worldInfo.append(scanner.next());
    }
    MyFormatter tempformatter = new MyFormatter(2);
    tempformatter.MapParse(myworld, worldInfo.toString());

    MyFormatter formatter = new MyFormatter(2);
    String Astr =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'},{'neighbor_2':'E'}], 'territoryName':'A'}";
    Territory territoryA = new Territory();
    JSONObject tempA = new JSONObject(Astr);
    territoryA = formatter.JsonToTerritory(tempA);

    String Bstr =
        "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'E'}], 'territoryName':'B'}";
    Territory territoryB = new Territory();
    JSONObject tempB = new JSONObject(Bstr);
    territoryB = formatter.JsonToTerritory(tempB);

    String Cstr =
        "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'E'},{'neighbor_2':'F'},{'neighbor_3':'G'}], 'territoryName':'C'}";
    Territory territoryC = new Territory();
    JSONObject tempC = new JSONObject(Cstr);
    territoryC = formatter.JsonToTerritory(tempC);

    String Dstr =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'E'},{'neighbor_2':'H'}], 'territoryName':'D'}";
    Territory territoryD = new Territory();
    JSONObject tempD = new JSONObject(Dstr);
    territoryD = formatter.JsonToTerritory(tempD);

    String Estr =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'B'},{'neighbor_2':'C'},{'neighbor_3':'D'},{'neighbor_4':'F'},{'neighbor_5':'H'},{'neighbor_6':'J'}], 'territoryName':'E'}";
    Territory territoryE = new Territory();
    JSONObject tempE = new JSONObject(Estr);
    territoryE = formatter.JsonToTerritory(tempE);

    assertEquals(territoryA.getSoldierLevel(0), 3);
    assertEquals(territoryB.getSoldierLevel(0), 3);
    assertEquals(territoryC.getSoldierLevel(0), 3);
    assertEquals(territoryD.getSoldierLevel(0), 3);
    ArrayList<Action> actionList = new ArrayList<>();

    Action action2 = new Action();
    action2.setSrc(territoryA); // A
    action2.setDst(territoryB); // B
    action2.setSoldierLevel(0, 3);
    action2.setOwner("player_0");
    action2.setType("Attack");
    actionList.add(action2); // valid action, but is deleted

    Action action = new Action();
    action.setSrc(territoryA); // A
    action.setDst(territoryD); // D
    action.setSoldierLevel(0, 3);
    action.setOwner("player_0");
    action.setType("Attack");
    actionList.add(action); // invalid action

    Action action3 = new Action();
    action3.setSrc(territoryB); // B
    action3.setDst(territoryE); // E
    action3.setSoldierLevel(0, 3);
    action3.setOwner("player_1");
    action3.setType("Attack");
    ArrayList<Action> actionList2 = new ArrayList<>();
    actionList2.add(action3); // valid action

    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);
    myactionMap.put(1, actionList2);

    DoAction actor = new DoAction(myworld, myactionMap, resource);
    ArrayList<Action> allActionList = new ArrayList<>();
    allActionList.add(action2);
    allActionList.add(action);
    allActionList.add(action3);

    actor.doAttackAction(allActionList);
    myworld = actor.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoldierLevel(0), 3); // A
    assertEquals(myworld.get(1).get(0).getSoldierLevel(0), 0); // B
    assertEquals(myworld.get(1).get(1).getSoldierLevel(0), 3); // C
    assertEquals(myactionMap.containsKey(0), false);
    MaptoJson myMaptoJson = new MaptoJson(myworld);
    System.out.println("[DEBUG] outside doactionclass, new world is:" + myMaptoJson.getJSON());
    MyFormatter formatter2 = new MyFormatter(2);
    System.out.println("[DEBUG] outside doactionclass, new actionmap is:"
        + formatter2.AllActionCompose(myactionMap));
  }

  @Test
  public void test_DoMoveAction() {
    HashMap<Integer, ArrayList<Territory>> myworld = new HashMap<>();
    StringBuilder fileName = new StringBuilder();
    fileName.append("/old/world2.json");
    InputStream input = getClass().getResourceAsStream(fileName.toString());

    Scanner scanner = new Scanner(input);
    StringBuilder worldInfo = new StringBuilder();
    while (scanner.hasNext()) {
      worldInfo.append(scanner.next());
    }
    MyFormatter tempformatter = new MyFormatter(2);
    tempformatter.MapParse(myworld, worldInfo.toString());

    MyFormatter formatter = new MyFormatter(2);
    String Astr =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'},{'neighbor_2':'E'}], 'territoryName':'A'}";
    Territory territoryA = new Territory();
    JSONObject tempA = new JSONObject(Astr);
    territoryA = formatter.JsonToTerritory(tempA);

    String Bstr =
        "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'E'}], 'territoryName':'B'}";
    Territory territoryB = new Territory();
    JSONObject tempB = new JSONObject(Bstr);
    territoryB = formatter.JsonToTerritory(tempB);

    String Cstr =
        "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'E'},{'neighbor_2':'F'},{'neighbor_3':'G'}], 'territoryName':'C'}";
    Territory territoryC = new Territory();
    JSONObject tempC = new JSONObject(Cstr);
    territoryC = formatter.JsonToTerritory(tempC);

    String Dstr =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'E'},{'neighbor_2':'H'}], 'territoryName':'D'}";
    Territory territoryD = new Territory();
    JSONObject tempD = new JSONObject(Dstr);
    territoryD = formatter.JsonToTerritory(tempD);

    assertEquals(territoryA.getSoldierLevel(0), 3);
    assertEquals(territoryB.getSoldierLevel(0), 3);
    assertEquals(territoryC.getSoldierLevel(0), 3);
    assertEquals(territoryD.getSoldierLevel(0), 3);

    territoryA.setSoldierLevel(2, 2);
    myworld.get(0).get(0).setSoldierLevel(2, 2);
    assertEquals(territoryA.getSoldierLevel(2), 2);
    assertEquals(myworld.get(0).get(0).getSoldierLevel(2), 2);

    Action action = new Action();
    action.setSrc(territoryA);
    action.setDst(territoryD);
    action.setSoldierLevel(0, 3);
    action.setSoldierLevel(2, 1);
    assertEquals(action.getSoldierLevel(0), 3);
    assertEquals(action.getSoldierLevel(2), 1);
    action.setOwner("player_0");
    action.setType("Move");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action);
    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);

    DoAction actor = new DoAction(myworld, myactionMap, resource);
    actor.doMoveAction(actionList);
    myworld = actor.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoldierLevel(0), 0);
    assertEquals(myworld.get(0).get(0).getSoldierLevel(2), 1);
    assertEquals(myworld.get(0).get(1).getSoldierLevel(0), 6);
    assertEquals(myworld.get(0).get(1).getSoldierLevel(2), 1);

    MyFormatter formatter2 = new MyFormatter(2);
    System.out.println("[DEBUG] outside doactionclass, new actionmap is:"
        + formatter2.AllActionCompose(myactionMap));

    Action action2 = new Action();
    action2.setSrc(territoryB);
    action2.setDst(territoryC);
    action2.setSoldierLevel(0, 2);
    action2.setOwner("player_1");
    action2.setType("Move");
    ArrayList<Action> actionList2 = new ArrayList<>();
    actionList2.add(action2);
    HashMap<Integer, ArrayList<Action>> myactionMap2 = new HashMap<>();
    myactionMap2.put(1, actionList2);

    DoAction actor2 = new DoAction(myworld, myactionMap2, resource);
    actor2.doMoveAction(actionList2);
    myworld = actor2.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoldierLevel(0), 0);
    assertEquals(myworld.get(0).get(1).getSoldierLevel(0), 6);
    assertEquals(myworld.get(1).get(0).getSoldierLevel(0), 1);
    assertEquals(myworld.get(1).get(1).getSoldierLevel(0), 5);

    MyFormatter formatter3 = new MyFormatter(2);
    System.out.println("[DEBUG] outside doactionclass, new actionmap is:"
        + formatter3.AllActionCompose(myactionMap));
  }

  @Test
  public void test_DoAttackAtion2() {
    HashMap<Integer, ArrayList<Territory>> myworld = new HashMap<>();
    StringBuilder fileName = new StringBuilder();
    fileName.append("/old/world2.json");
    InputStream input = getClass().getResourceAsStream(fileName.toString());

    Scanner scanner = new Scanner(input);
    StringBuilder worldInfo = new StringBuilder();
    while (scanner.hasNext()) {
      worldInfo.append(scanner.next());
    }
    MyFormatter tempformatter = new MyFormatter(2);
    tempformatter.MapParse(myworld, worldInfo.toString());

    MyFormatter formatter = new MyFormatter(2);

    String Astr =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'},{'neighbor_2':'E'}], 'territoryName':'A'}";
    Territory territoryA = new Territory();
    JSONObject tempA = new JSONObject(Astr);
    territoryA = formatter.JsonToTerritory(tempA);

    String Bstr =
        "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'E'}], 'territoryName':'B'}";
    Territory territoryB = new Territory();
    JSONObject tempB = new JSONObject(Bstr);
    territoryB = formatter.JsonToTerritory(tempB);

    String Cstr =
        "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'E'},{'neighbor_2':'F'},{'neighbor_3':'G'}], 'territoryName':'C'}";
    Territory territoryC = new Territory();
    JSONObject tempC = new JSONObject(Cstr);
    territoryC = formatter.JsonToTerritory(tempC);

    assertEquals(territoryA.getTerritoryName(), "A");
    assertEquals(territoryB.getTerritoryName(), "B");
    assertEquals(territoryA.getSoldierLevel(0), 3);
    assertEquals(territoryB.getSoldierLevel(0), 3);

    Action action = new Action();
    action.setSrc(territoryA); // A
    action.setDst(territoryB); // B
    action.setSoldierLevel(0, 3);
    action.setOwner("player_0");
    action.setType("Attack");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action);

    Action action2 = new Action();
    action2.setSrc(territoryA); // A
    action2.setDst(territoryC); // C
    action2.setSoldierLevel(0, 3);
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
    DoAction actor = new DoAction(myworld, myactionMap, resource);
    actor.doAttackAction(allAction);

    myworld = actor.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoldierLevel(0), 0);

    MyFormatter formatter2 = new MyFormatter(2);
    System.out.println("[DEBUG] outside doactionclass, new actionmap is:"
        + formatter2.AllActionCompose(myactionMap));
  }

  @Test
  public void test_DoAttackAtion() {
    HashMap<Integer, ArrayList<Territory>> myworld = new HashMap<>();
    StringBuilder fileName = new StringBuilder();
    fileName.append("/old/world2.json");
    InputStream input = getClass().getResourceAsStream(fileName.toString());

    Scanner scanner = new Scanner(input);
    StringBuilder worldInfo = new StringBuilder();
    while (scanner.hasNext()) {
      worldInfo.append(scanner.next());
    }
    MyFormatter tempformatter = new MyFormatter(2);
    tempformatter.MapParse(myworld, worldInfo.toString());

    MyFormatter formatter = new MyFormatter(2);
    String Astr =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'},{'neighbor_2':'E'}], 'territoryName':'A'}";
    Territory territoryA = new Territory();
    JSONObject tempA = new JSONObject(Astr);
    territoryA = formatter.JsonToTerritory(tempA);

    String Bstr =
        "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'E'}], 'territoryName':'B'}";
    Territory territoryB = new Territory();
    JSONObject tempB = new JSONObject(Bstr);
    territoryB = formatter.JsonToTerritory(tempB);

    territoryA.setSoldierLevel(0, 3);
    territoryA.setSoldierLevel(2, 10);
    territoryA.setSoldierLevel(4, 3);
    myworld.get(0).get(0).setSoldierLevel(0, 3);
    myworld.get(0).get(0).setSoldierLevel(2, 10);
    myworld.get(0).get(0).setSoldierLevel(4, 3);

    territoryB.setSoldierLevel(0, 0);
    territoryB.setSoldierLevel(1, 2);
    territoryB.setSoldierLevel(3, 2);
    territoryB.setSoldierLevel(5, 2);
    myworld.get(1).get(0).setSoldierLevel(0, 0);
    myworld.get(1).get(0).setSoldierLevel(1, 2);
    myworld.get(1).get(0).setSoldierLevel(3, 2);
    myworld.get(1).get(0).setSoldierLevel(5, 2);

    Action action = new Action();
    assertEquals(territoryA.getTerritoryName(), "A");
    assertEquals(territoryB.getTerritoryName(), "B");
    assertEquals(territoryA.getSoldierLevel(0), 3);
    assertEquals(territoryB.getSoldierLevel(0), 0);

    action.setSrc(territoryA); // B
    action.setDst(territoryB); // E
    action.setSoldierLevel(0, 2);
    action.setSoldierLevel(2, 9);
    action.setSoldierLevel(4, 2);
    action.setOwner("player_0");
    action.setType("Attack");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action);

    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);

    DoAction actor = new DoAction(myworld, myactionMap, resource);
    actor.doAttackAction(actionList);

    myworld = actor.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoldierLevel(0), 1);
    assertEquals(myworld.get(0).get(0).getSoldierLevel(2), 1);
    assertEquals(myworld.get(0).get(0).getSoldierLevel(4), 1);

    MyFormatter formatter2 = new MyFormatter(2);
    System.out.println("[DEBUG] outside doactionclass, new actionmap is:"
        + formatter2.AllActionCompose(myactionMap));
  }

  @Test
  public void test_DoPlusOne() {
    HashMap<Integer, ArrayList<Territory>> myworld = new HashMap<>();
    StringBuilder fileName = new StringBuilder();
    fileName.append("/old/world2.json");
    InputStream input = getClass().getResourceAsStream(fileName.toString());

    Scanner scanner = new Scanner(input);
    StringBuilder worldInfo = new StringBuilder();
    while (scanner.hasNext()) {
      worldInfo.append(scanner.next());
    }
    MyFormatter tempformatter = new MyFormatter(2);
    tempformatter.MapParse(myworld, worldInfo.toString());

    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : myworld.entrySet()) {
      ArrayList<Territory> territoryList = entry.getValue();
      for (int j = 0; j < territoryList.size(); j++) {
        Territory myterritory = territoryList.get(j);
        assertEquals(myterritory.getSoldierLevel(0), 3);
      }
    }

    ArrayList<Action> actionList = new ArrayList<>();
    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);

    DoAction actor = new DoAction(myworld, myactionMap, resource);

    actor.doPlusOne();
    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : myworld.entrySet()) {
      ArrayList<Territory> territoryList = entry.getValue();
      for (int j = 0; j < territoryList.size(); j++) {
        Territory myterritory = territoryList.get(j);
        assertEquals(myterritory.getSoldierLevel(0), 4);
      }
    }
  }
}
