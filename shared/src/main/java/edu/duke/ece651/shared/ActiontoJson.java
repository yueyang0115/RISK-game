package edu.duke.ece651.shared;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class ActiontoJson {
  private JSONObject ans;
  private JSONObject ansAllAction;
  private ArrayList<Action> actionList;
  private HashMap<Integer, ArrayList<Action>> AllAction;
  private String ActionType;
  public ActiontoJson(ArrayList<Action> myactionList, String Type) {
    this.ans = new JSONObject();
    this.actionList = new ArrayList<>();
    this.actionList = myactionList;
    ActionType = Type;
    getActionListObj();
  }
  public ActiontoJson(HashMap<Integer, ArrayList<Action>> Actions) {
    this.ansAllAction = new JSONObject();
    this.AllAction = new HashMap<>();
    this.AllAction = Actions;
  }

  // transfer ArrayList<Action> to string
  public JSONObject getJSON() {
    return this.ans;
  }

  // transfer HashMap<Integer, ArrayList<Action>> to string
  public void ComposeAllAction() {
    for (HashMap.Entry<Integer, ArrayList<Action>> entry : AllAction.entrySet()) {
      JSONArray currActionArray = new JSONArray();
      for (int i = 0; i < entry.getValue().size(); i++) {
        JSONObject actionObj = new JSONObject();
        Action action = entry.getValue().get(i);
        getActionObj(actionObj, action);
        currActionArray.put(actionObj);
      }
      String insertKey = "player_" + entry.getKey();
      ansAllAction.put(insertKey, currActionArray);
    }
  }
  public JSONObject getAllAction() {
    return this.ansAllAction;
  }
  private void getActionListObj() {
    JSONArray actionArray = new JSONArray();
    getActionArray(actionArray);
    ans.put(ActionType, actionArray);
  }

  public void getActionArray(JSONArray actionArray) {
    // System.out.println("[DEBUG] actionList size is: " + actionList.size());
    for (int i = 0; i < actionList.size(); i++) {
      JSONObject actionObj = new JSONObject();
      Action action = actionList.get(i);
      getActionObj(actionObj, action);
      actionArray.put(actionObj);
    }
  }

  public void getActionObj(JSONObject actionObj, Action action) {
    MaptoJson myMaptoJson = new MaptoJson();
    JSONObject srcTerritoryObj = new JSONObject();
    myMaptoJson.getTerritoryObj(srcTerritoryObj, action.getSrc());
    actionObj.put("src", srcTerritoryObj);

    JSONObject dstTerritoryObj = new JSONObject();
    myMaptoJson.getTerritoryObj(dstTerritoryObj, action.getDst());
    actionObj.put("dst", dstTerritoryObj);

    HashMap<Integer, Integer> soldierMap = action.getSoldiers();
    JSONArray soldierArray = new JSONArray();
    myMaptoJson.getSoldierArray(soldierArray, soldierMap);
    actionObj.put("soldiers", soldierArray);

    String owner = action.getOwner();
    actionObj.put("owner", owner);

    String type = action.getType();
    actionObj.put("type", type);
  }
}
