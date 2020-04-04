package edu.duke.ece651.server;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.shared.*;
import java.io.*;
import java.util.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class ResourceCheckerTest {
  private HashMap<Integer, Integer> resource;
  private HashMap<Integer, ArrayList<Territory>> myworld;
  private Territory territoryA;
  private Territory territoryB;

  public ResourceCheckerTest() {
    resource = new HashMap<>();
    resource.put(0, 100);
    resource.put(1, 100);
    resource.put(2, 100);
    resource.put(3, 100);
    myworld = new HashMap<>();
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

    JSONObject tempA = new JSONObject(Astr);
    territoryA = formatter.JsonToTerritory(tempA);

    String Bstr =
        "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'E'}], 'territoryName':'B'}";
    JSONObject tempB = new JSONObject(Bstr);
    territoryB = formatter.JsonToTerritory(tempB);
  }

  @Test
  public void test_invalidresource() {
    resource.put(0, 10);

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

    action.setSrc(territoryA); // B
    action.setDst(territoryB); // E
    action.setSoldierLevel(0, 2);
    action.setSoldierLevel(2, 9);
    action.setOwner("player_0");
    action.setType("Attack");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action);

    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);

    DoAction actor = new DoAction(myworld, myactionMap, resource);
    actor.doAttackAction(actionList);

    myworld = actor.getNewWorld();
    assertEquals(myworld.get(0).get(0).getSoldierLevel(0), 3);
    assertEquals(myworld.get(0).get(0).getSoldierLevel(2), 10);
    assertEquals(myworld.get(0).get(0).getSoldierLevel(4), 3);

    assertEquals(myworld.get(1).get(0).getSoldierLevel(0), 0);
    assertEquals(myworld.get(1).get(0).getSoldierLevel(1), 2);
    assertEquals(myworld.get(1).get(0).getSoldierLevel(3), 2);
    assertEquals(myworld.get(1).get(0).getSoldierLevel(5), 2);
  }

  @Test
  public void test_countAttackSource() {
    territoryA.setSoldierLevel(0, 3);
    territoryA.setSoldierLevel(2, 10);
    territoryA.setSoldierLevel(4, 3);
    myworld.get(0).get(0).setSoldierLevel(0, 3);
    myworld.get(0).get(0).setSoldierLevel(2, 10);

    territoryB.setSoldierLevel(0, 0);
    territoryB.setSoldierLevel(1, 2);
    territoryB.setSoldierLevel(3, 2);
    territoryB.setSoldierLevel(5, 2);
    myworld.get(1).get(0).setSoldierLevel(0, 0);
    myworld.get(1).get(0).setSoldierLevel(1, 2);
    myworld.get(1).get(0).setSoldierLevel(3, 2);

    Action action = new Action();

    action.setSrc(territoryA); // B
    action.setDst(territoryB); // E
    action.setSoldierLevel(0, 2);
    action.setSoldierLevel(2, 9);
    action.setOwner("player_0");
    action.setType("Attack");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action);

    HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
    myactionMap.put(0, actionList);

    DoAction actor = new DoAction(myworld, myactionMap, resource);
    actor.doAttackAction(actionList);

    resource = actor.getNewResource();
    int numResource = resource.get(0);
    assertEquals(numResource, 89);
  }

  @Test
  public void test_Dijkstra_MoveCount() {
    WorldInitter myinitter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> Dworld = myinitter.getWorld();
    MyFormatter formatter2 = new MyFormatter(2);
    String B2str =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'L'}], 'territoryName':'B'}";

    JSONObject tempB2 = new JSONObject(B2str);
    Territory territoryB2 = formatter2.JsonToTerritory(tempB2);

    String E2str =
        "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'F'}, {'neighbor_2':'G'}], 'territoryName':'E'}";
    JSONObject tempE2 = new JSONObject(E2str);
    Territory territoryE2 = formatter2.JsonToTerritory(tempE2);

    Action action2 = new Action();

    action2.setSrc(territoryB2); // B
    assertEquals(territoryB2.getTerritoryName(), "B");
    action2.setDst(territoryE2); // E
    action2.setSoldierLevel(0, 2);
    action2.setOwner("player_0");
    action2.setType("Move");
    ArrayList<Action> actionList = new ArrayList<>();
    actionList.add(action2);

    HashMap<Integer, ArrayList<Action>> myactionMap2 = new HashMap<>();
    myactionMap2.put(0, actionList);

    DoAction actor = new DoAction(Dworld, myactionMap2, resource);
    assertEquals(resource.get(0), 100);
    actor.doMoveAction(actionList);

    Dworld = actor.getNewWorld();
    resource = actor.getNewResource();
    int numResource = resource.get(0);
    assertEquals(numResource, 62); // 100-19*2
    assertEquals(Dworld.get(0).get(1).getTerritoryName(), "B");
    assertEquals(Dworld.get(0).get(1).getSoldierLevel(0), 1);
    assertEquals(Dworld.get(0).get(4).getTerritoryName(), "E");
    assertEquals(Dworld.get(0).get(4).getSoldierLevel(0), 5);
  }
}
