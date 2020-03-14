package edu.duke.ece651.shared;
import java.util.*;
import javax.print.attribute.standard.Destination;
import org.json.*;

public class MyFormatter {
  private int NumPlayers;

  public MyFormatter(int num) {
    NumPlayers = num;
  }

  public JSONObject MapCompose(HashMap<Integer, ArrayList<Territory>> territoryMap) {
    MaptoJson myMaptoJson = new MaptoJson();
    return myMaptoJson.getJSON();
  }

  public void MapParse(HashMap<Integer, ArrayList<Territory>> Input, String MapJson) {
    JSONObject InputMap = new JSONObject(MapJson);
    for (int i = 0; i < NumPlayers; i++) {
      JSONArray PlayerTemp = InputMap.getJSONArray("player_" + Integer.toString(i));
      System.out.println("[DEBUG] Player = " + i);
      ArrayList<Territory> InnerTerr = new ArrayList<Territory>();
      for (int j = 0; j < PlayerTemp.length(); j++) {
        JSONObject TerrTemp = PlayerTemp.optJSONObject(j);
        Territory Inner = JsonToTerritory(TerrTemp);
        InnerTerr.add(Inner);
      }
      Input.put(i, InnerTerr);
    }
  }

  private Territory JsonToTerritory(JSONObject TerrTemp) {
    Territory Inner = new Territory();
    String owner = TerrTemp.optString("owner");
    System.out.println("[DEBUG] OwnerName = " + owner);
    Inner.setOwner(owner);
    int SoldierNum = TerrTemp.optInt("soldiers");
    Inner.setSoldiers(SoldierNum);
    System.out.println("[DEBUG] SoldierNum = " + SoldierNum);
    JSONArray NeighborArray = TerrTemp.optJSONArray("neighbor");
    for (int k = 0; k < NeighborArray.length(); k++) {
      JSONObject InnerNeigh = NeighborArray.optJSONObject(k);
      String NeighName = InnerNeigh.optString("neighbor_" + Integer.toString(k));
      // System.out.println("[DEBUG] NeighborName_" + i + " = " + NeighName);
      Inner.setNeighbor(NeighName);
    }
    String TerritoryName = TerrTemp.optString("territoryName");
    System.out.println("[DEBUG] TerritoryName = " + TerritoryName);
    Inner.setTerritoryName(TerritoryName);
    return Inner;
  }

  public JSONObject ActionCompose(ArrayList<Action> actionList) {
    ActiontoJson myActiontoJson = new ActiontoJson(actionList);
    return myActiontoJson.getJSON();
  }

  public void ActionParse(ArrayList<Action> Input, String ActionJson) {
    JSONObject InputAction = new JSONObject(ActionJson);

    String ActionType;
    if (InputAction.has("Move")) {
      ActionType = "Move";
    } else {
      ActionType = "Attack";
    }
    JSONArray ActionArray = InputAction.optJSONArray(ActionType);
    for (int i = 0; i < ActionArray.length(); i++) {
      Action InnerAction = new Action();
      InnerAction.setType(ActionType);
      JSONObject ActionTemp = ActionArray.optJSONObject(i);
      String ActionOwner = ActionTemp.optString("owner");

      InnerAction.setOwner(ActionOwner);
      int ActionSoldierNum = ActionTemp.optInt("soldiers");
      InnerAction.setSoldiers(ActionSoldierNum);
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
}
