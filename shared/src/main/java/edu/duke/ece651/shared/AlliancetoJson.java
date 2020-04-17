package edu.duke.ece651.shared;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class AlliancetoJson {
    private Alliance alliance;
    private JSONObject ans;

    public AlliancetoJson(Alliance a) {
        this.ans = new JSONObject();
        this.alliance = a;
        getAllianceObj();
    }

    public JSONObject getJSON() {
        return this.ans;
    }

    public void getAllianceObj() {
        String owner = alliance.getOwner();
        ans.put("owner", owner);
        String ally = alliance.getAlly();
        ans.put("ally", ally);
    }
}
