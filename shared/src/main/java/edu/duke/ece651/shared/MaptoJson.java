package edu.duke.ece651.shared;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class MaptoJson {
  private JSONObject ans;
  private HashMap<Integer, ArrayList<Territory>> myterritoryMap;

  public MaptoJson() {}

  public MaptoJson(HashMap<Integer, ArrayList<Territory>> rhs_territoryMap) {
    this.ans = new JSONObject();
    this.myterritoryMap = new HashMap<>();
    this.myterritoryMap = rhs_territoryMap;
    getPlayerObj();
  }

  // transfer HashMap<Integer, ArrayList<Territory>> to JSONObject
  public JSONObject getJSON() {
    return this.ans;
  }

  public void getPlayerObj() {
    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : myterritoryMap.entrySet()) {
      int i = entry.getKey();
      StringBuilder playerID = new StringBuilder();
      playerID.append("player_").append(i);

      JSONArray territoryArray = new JSONArray();
      getTerritoryArray(territoryArray, entry.getValue());
      ans.put(playerID.toString(), territoryArray);
    }
  }

  public void getTerritoryArray(JSONArray territoryArray, ArrayList<Territory> TerritoryList) {
    for (int i = 0; i < TerritoryList.size(); i++) {
      JSONObject territoryObj = new JSONObject();
      Territory myterritory = TerritoryList.get(i);
      getTerritoryObj(territoryObj, myterritory);
      territoryArray.put(territoryObj);
    }
  }

  public void getTerritoryObj(JSONObject territoryObj, Territory myterritory) {
    String owner = myterritory.getOwner();
    territoryObj.put("owner", owner);

    HashMap<Integer, Integer> soldierMap = myterritory.getSoldiers();
    JSONArray soldierArray = new JSONArray();
    getSoldierArray(soldierArray, soldierMap);
    territoryObj.put("soldiers", soldierArray);

    ArrayList<String> neighborList = myterritory.getNeighbor();
    JSONArray neighborArray = new JSONArray();
    getNeighborArray(neighborArray, neighborList);
    territoryObj.put("neighbor", neighborArray);

    String name = myterritory.getTerritoryName();
    territoryObj.put("territoryName", name);
  }

  public void getSoldierArray(JSONArray soldierArray, HashMap<Integer, Integer> soldierMap) {
    for (HashMap.Entry<Integer, Integer> entry : soldierMap.entrySet()) {
      int i = entry.getKey();
      StringBuilder level = new StringBuilder();
      level.append("level_").append(i);

      JSONObject soldierObj = new JSONObject();
      soldierObj.put(level.toString(), entry.getValue());
      soldierArray.put(soldierObj);
    }
  }

  public void getNeighborArray(JSONArray neighborArray, ArrayList<String> neighborList) {
    for (int i = 0; i < neighborList.size(); i++) {
      JSONObject neighborObj = new JSONObject();
      StringBuilder neighborID = new StringBuilder();
      neighborID.append("neighbor_").append(i);
      neighborObj.put(neighborID.toString(), neighborList.get(i));
      neighborArray.put(neighborObj);
    }
  }
}
