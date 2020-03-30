package edu.duke.ece651.shared;
import java.util.*;
import org.json.*;

public class MyFormatter {
  private int NumPlayers;

  public MyFormatter(int num) {
    NumPlayers = num;
  }

  public JSONObject MapCompose(HashMap<Integer, ArrayList<Territory>> territoryMap) {
    MaptoJson myMaptoJson = new MaptoJson(territoryMap);
    return myMaptoJson.getJSON();
  }

  public void MapParse(HashMap<Integer, ArrayList<Territory>> Input, String MapJson) {
    // receive the json string Map and parse into a territoryMap
    JSONObject InputMap = new JSONObject(MapJson);
    // search through all player to set their corresponding territory
    for (int i = 0; i < NumPlayers; i++) {
      JSONArray PlayerTemp = new JSONArray();

      PlayerTemp = InputMap.optJSONArray("player_" + Integer.toString(i));

      if (PlayerTemp != null) {
        ArrayList<Territory> InnerTerr = new ArrayList<Territory>();
        // System.out.println("[DEBUG] ArraySize = " + PlayerTemp.length());
        for (int j = 0; j < PlayerTemp.length(); j++) {
          // System.out.println("[DEBUG] ArraySize = " + PlayerTemp.length());
          JSONObject TerrTemp = PlayerTemp.optJSONObject(j);
          Territory Inner = JsonToTerritory(TerrTemp);
          InnerTerr.add(Inner);
        }
        if (InnerTerr.size() != 0) {
          Input.put(i, InnerTerr);
        }
      }
    }
  }

  public Territory JsonToTerritory(JSONObject TerrTemp) {
    // parse One JsonObject which is one territory
    Territory Inner = new Territory();
    String owner = TerrTemp.optString("owner");
    // System.out.println("[DEBUG] OwnerName = " + owner);
    Inner.setOwner(owner);
    JSONArray SoldierArray = TerrTemp.optJSONArray("soldiers");
    for (int i = 0; i < SoldierArray.length(); i++) {
      JSONObject InnerSoldier = SoldierArray.optJSONObject(i);
      int num = InnerSoldier.optInt("level_" + Integer.toString(i));
      Inner.setSoldierLevel(i, num);
    }
    // System.out.println("[DEBUG] SoldierNum = " + SoldierNum);
    // neighbor is stored in JsonArray Format
    JSONArray NeighborArray = TerrTemp.optJSONArray("neighbor");
    for (int k = 0; k < NeighborArray.length(); k++) {
      JSONObject InnerNeigh = NeighborArray.optJSONObject(k);
      String NeighName = InnerNeigh.optString("neighbor_" + Integer.toString(k));
      // System.out.println("[DEBUG] NeighborName_" + i + " = " + NeighName);
      Inner.setNeighbor(NeighName);
    }
    String TerritoryName = TerrTemp.optString("territoryName");
    // System.out.println("[DEBUG] TerritoryName = " + TerritoryName);
    Inner.setTerritoryName(TerritoryName);
    return Inner;
  }

  public JSONObject ActionCompose(ArrayList<Action> actionList, String ActionType) {
    // based on Action type to compose the arraylist action into JsonObject
    ActiontoJson myActiontoJson = new ActiontoJson(actionList, ActionType);
    // System.out.println("The returned Action is: " + myActiontoJson.getJSON());
    return myActiontoJson.getJSON();
  }

  public void ActionParse(ArrayList<Action> Input, String ActionJson) {
    // parse action json string into an arraylist
    JSONObject InputAction = new JSONObject(ActionJson);
    // get the entire jsonArray
    String ActionType;
    if (InputAction.has("Move")) {
      ActionType = "Move";
    } else {
      ActionType = "Attack";
    }
    JSONArray ActionArray = InputAction.optJSONArray(ActionType);
    ParseActionArray(Input, ActionArray);
  }

  private void ParseActionArray(ArrayList<Action> Input, JSONArray ActionArray) {
    // parse each territory in the jsonarray
    for (int i = 0; i < ActionArray.length(); i++) {
      Action InnerAction = new Action();
      JSONObject ActionTemp = ActionArray.optJSONObject(i);
      String ActionOwner = ActionTemp.optString("owner");
      InnerAction.setOwner(ActionOwner);
      String ActionType = ActionTemp.optString("type");
      // System.out.println("Action Type in Parse " + ActionType);
      InnerAction.setType(ActionType);
      JSONArray SoldierArray = ActionTemp.optJSONArray("soldiers");
      for (int j = 0; j < SoldierArray.length(); j++) {
        JSONObject InnerSoldier = SoldierArray.optJSONObject(i);
        int num = InnerSoldier.optInt("level_" + Integer.toString(i));
        InnerAction.setSoldierLevel(i, num);
      }
      setSrcDst(InnerAction, ActionTemp, "src");
      setSrcDst(InnerAction, ActionTemp, "dst");
      Input.add(InnerAction);
    }
  }

  public void setSrcDst(Action InnerAction, JSONObject Inner, String SrcDst) {
    JSONObject InnerTerr = Inner.optJSONObject(SrcDst);
    Territory SrcDstTerr = JsonToTerritory(InnerTerr);
    if (SrcDst.equals("src")) {
      InnerAction.setSrc(SrcDstTerr);
    } else {
      InnerAction.setDst(SrcDstTerr);
    }
  }

  public void AllActionParse(HashMap<Integer, ArrayList<Action>> AllAction, String Input) {
    // get a jsonstring of all players actions and parse that into a hashmap
    // System.out.println("The Received All Action String is (ready to AllActionParse): " + Input);
    JSONObject InputAction = new JSONObject(Input);
    // System.out.println("Input  = " + Input);
    for (int i = 0; i < NumPlayers; i++) {
      JSONArray PlayerTemp = new JSONArray();
      PlayerTemp = InputAction.optJSONArray("player_" + Integer.toString(i));

      if (PlayerTemp != null && PlayerTemp.length() != 0) {
        // System.out.println("[DEBUG] player_" + Integer.toString(i));
        // System.out.println("Current Player_" + i + "isNotEmpty");
        ArrayList<Action> CurrAction = new ArrayList<>();
        ParseActionArray(CurrAction, PlayerTemp);
        AllAction.put(i, CurrAction);
      }
    }
  }
  public JSONObject AllActionCompose(HashMap<Integer, ArrayList<Action>> AllAction) {
    // compose the jsonstring based on the hashMap AllAction
    ActiontoJson myAllAction = new ActiontoJson(AllAction);
    myAllAction.ComposeAllAction();
    return myAllAction.getAllAction();
  }
}
