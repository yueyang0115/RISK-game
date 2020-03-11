package edu.duke.ece651.shared;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class MaptoJSON {
  private JSONObject ans;
  private HashMap<Integer, ArrayList<Territory>> myterritoryMap;

  public MaptoJSON(HashMap<Integer, ArrayList<Territory>> rhs_territoryMap) {
    this.ans = new JSONObject();
    this.myterritoryMap = new HashMap<>();
    this.myterritoryMap = rhs_territoryMap;
    getPlayerObj();
  }

  public JSONObject getJSON() {
    return this.ans;
  }

  private void getPlayerObj() {
    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : myterritoryMap.entrySet()) {
      int i = entry.getKey();
      StringBuilder playerID = new StringBuilder();
      playerID.append("player_").append(i);

      JSONArray territoryArray = new JSONArray();
      getTerritoryArray(territoryArray, entry.getValue());
      ans.put(playerID.toString(), territoryArray);
    }
  }

  private void getTerritoryArray(JSONArray territoryArray, ArrayList<Territory> TerritoryList) {
    for (int i = 0; i < TerritoryList.size(); i++) {
      JSONObject territoryObj = new JSONObject();
      String owner = TerritoryList.get(i).getOwner();
      territoryObj.put("owner", owner);

      int soldiers = TerritoryList.get(i).getSoilders();
      territoryObj.put("soldiers", soldiers);

      ArrayList<String> neighborList = TerritoryList.get(i).getNeighbor();
      JSONArray neighborArray = new JSONArray();
      getNeighborArray(neighborArray, neighborList);
      territoryObj.put("neighbor", neighborArray);

      String name = TerritoryList.get(i).getTerritoryName();
      territoryObj.put("name", name);

      territoryArray.put(territoryObj);
    }
  }

  private void getNeighborArray(JSONArray neighborArray, ArrayList<String> neighborList) {
    for (int i = 0; i < neighborList.size(); i++) {
      JSONObject neighborObj = new JSONObject();
      StringBuilder neighborID = new StringBuilder();
      neighborID.append("neighbor_").append(i);
      neighborObj.put(neighborID.toString(), neighborList.get(i));
      neighborArray.put(neighborObj);
    }
  }
}
