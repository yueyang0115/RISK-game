package edu.duke.ece651.player;

import edu.duke.ece651.shared.ColorID;
import edu.duke.ece651.shared.Territory;
import edu.duke.ece651.shared.TerritorySize;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;

public class Show {
    public static void ShowLabel(Territory CurrentClicked, Label Detail){
        String ShowLabel = ComposeString(CurrentClicked);
        Detail.setText(ShowLabel);
        Detail.setFont(new Font("Arial", 20));
    }

    public static Territory FindTerritory(HashMap<Integer, ArrayList<Territory>> World, String TerritoryName) {
        Territory ans = new Territory();
        for (HashMap.Entry<Integer, ArrayList<Territory>> entry : World.entrySet()) {
            ArrayList<Territory> territoryList = entry.getValue();
            for (int j = 0; j < territoryList.size(); j++) {
                Territory Terr = territoryList.get(j);
                if (Terr.getTerritoryName().equals(TerritoryName)) {
                    // System.out.println("[DEBUG] find ans");
                    ans = Terr;
                    return ans;
                }
            }
        }
        return ans;
    }
    public static String ComposeString(Territory Clicked){
        StringBuilder ShowDetail = new StringBuilder();
        int ID = Integer.valueOf(Clicked.getOwner().substring(7));
        ColorID FindName = new ColorID();
        ShowDetail.append("Territory Name: ").append(Clicked.getTerritoryName()).append("\n\n");
        ShowDetail.append("Owner Name: ").append(FindName.getPlayerColor(ID)).append("\n\n");
        int Size = new TerritorySize().getTerritorySize(Clicked.getTerritoryName());
        ShowDetail.append("Territory Size: ").append(Size).append("\n\n");
        ShowDetail.append("Soldiers\n");
        for(int i = 0; i < 7; i++){
            ShowDetail.append("  Level ").append(i).append(": ").append(Clicked.getSoldierLevel(i)).append("\n");
        }
        return ShowDetail.toString();
    }
}
