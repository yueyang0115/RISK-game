package edu.duke.ece651.shared;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class UpgradetoJson {
    private JSONObject ans;
    private ArrayList<Upgrade> upgradeList;

    public UpgradetoJson(ArrayList<Upgrade> myupgradeList) {
        this.ans = new JSONObject();
        this.upgradeList = new ArrayList<>();
        this.upgradeList = myupgradeList;
        getUpgradeListObj();
    }

    public JSONObject getJSON() {
        return this.ans;
    }

    private void getUpgradeListObj() {
        JSONArray upgradeArray = new JSONArray();
        getUpgradeArray(upgradeArray);
        ans.put("Upgrade", upgradeArray);
    }

    public void getUpgradeArray(JSONArray upgradeArray) {
        for (int i = 0; i < upgradeList.size(); i++) {
            JSONObject upgradeObj = new JSONObject();
            Upgrade upgrade = upgradeList.get(i);
            getUpgradeObj(upgradeObj, upgrade);
            upgradeArray.put(upgradeObj);
        }
    }

    public void getUpgradeObj(JSONObject upgradeObj, Upgrade upgrade) {
        String territoryName = upgrade.getTerritoryName();
        upgradeObj.put("territoryName", territoryName);

        String owner = upgrade.getOwner();
        upgradeObj.put("owner", owner);

        int prevLevel = upgrade.getPrevLevel();
        upgradeObj.put("prevLevel", prevLevel);

        int nextLevel = upgrade.getNextLevel();
        upgradeObj.put("nextLevel", nextLevel);

        int number = upgrade.getNumber();
        upgradeObj.put("number", number);
    }
}
