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
    JSONObject InputMap = new JSONObject(MapJson);
    for (int i = 0; i < NumPlayers; i++) {
      JSONArray PlayerTemp = InputMap.optJSONArray("player_" + Integer.toString(i));
      System.out.println("[DEBUG] Player = " + i);
      ArrayList<Territory> InnerTerr = new ArrayList<Territory>();
      System.out.println("[DEBUG] ArraySize = " + PlayerTemp.length());
      for (int j = 0; j < PlayerTemp.length(); j++) {
        System.out.println("[DEBUG] ArraySize = " + PlayerTemp.length());
        JSONObject TerrTemp = PlayerTemp.optJSONObject(j);
        Territory Inner = JsonToTerritory(TerrTemp);
        InnerTerr.add(Inner);
      }
      if (InnerTerr.size() != 0) {
        Input.put(i, InnerTerr);
      }
    }
    System.out.println("[DEBUG] HashMap Size = " + Input.size());
  }

  public Territory JsonToTerritory(JSONObject TerrTemp) {
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

  public JSONObject ActionCompose(ArrayList<Action> actionList, String ActionType) {
    ActiontoJson myActiontoJson = new ActiontoJson(actionList, ActionType);
    System.out.println("The returned Action is: " + myActiontoJson.getJSON());
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
    ParseActionArray(Input, ActionArray);
  }

  private void ParseActionArray(ArrayList<Action> Input, JSONArray ActionArray) {
    for (int i = 0; i < ActionArray.length(); i++) {
      Action InnerAction = new Action();
      JSONObject ActionTemp = ActionArray.optJSONObject(i);
      String ActionOwner = ActionTemp.optString("owner");
      InnerAction.setOwner(ActionOwner);
      String ActionType = ActionTemp.optString("type");
      InnerAction.setType(ActionType);
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

  public void AllActionParse(HashMap<Integer, ArrayList<Action>> AllAction, String Input) {
    System.out.println("The Received All Action String is (ready to AllActionParse): " + Input);
    JSONObject InputAction = new JSONObject(Input);

    for (int i = 0; i < NumPlayers; i++) {
      JSONArray PlayerTemp = new JSONArray();
      PlayerTemp = InputAction.optJSONArray("player_" + Integer.toString(i));

      if (PlayerTemp != null) {
        System.out.println("[DEBUG] player_" + Integer.toString(i));
        ArrayList<Action> CurrAction = new ArrayList<>();
        ParseActionArray(CurrAction, PlayerTemp);
        AllAction.put(i, CurrAction);
      }
    }
  }
  public JSONObject AllActionCompose(HashMap<Integer, ArrayList<Action>> AllAction) {
    ActiontoJson myAllAction = new ActiontoJson(AllAction);
    myAllAction.ComposeAllAction();

    return myAllAction.getAllAction();
  }
}
