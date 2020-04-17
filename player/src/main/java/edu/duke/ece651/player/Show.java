package edu.duke.ece651.player;

import edu.duke.ece651.shared.ColorID;
import edu.duke.ece651.shared.Territory;
import edu.duke.ece651.shared.TerritorySize;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;

public class Show {
    //------------- Evolution 2 --------------//
    public static void ShowLabel(Territory CurrentClicked, Label Detail){
        String ShowLabel = ComposeString(CurrentClicked);
        Detail.setText(ShowLabel);
        Detail.setFont(new Font("Arial", 20));
    }



    public static Territory FindTerritory(HashMap<Integer, ArrayList<Territory>> World, String TerritoryName) {
        //get the territory name and find the territory in the territory map and return it
        Territory ans = new Territory();
        for (HashMap.Entry<Integer, ArrayList<Territory>> entry : World.entrySet()) {
            ArrayList<Territory> territoryList = entry.getValue();
            for (int j = 0; j < territoryList.size(); j++) {
                Territory Terr = territoryList.get(j);
                //if find the territory, return it
                if (Terr.getTerritoryName().equals(TerritoryName)) {
                    ans = Terr;
                    return ans;
                }
            }
        }
        return ans;
    }
    public static String ComposeString(Territory Clicked){
        //compose up the territory detail to string
        //ready for display it on the right side label of each page
        StringBuilder ShowDetail = new StringBuilder();
        int ID = Integer.valueOf(Clicked.getOwner().substring(7));
        ColorID FindName = new ColorID();
        ShowDetail.append("Territory Name: ").append(Clicked.getTerritoryName()).append("\n\n");
        ShowDetail.append("Owner Name: ").append(FindName.getPlayerColor(ID)).append("\n\n");
        int Size = new TerritorySize().getTerritorySize(Clicked.getTerritoryName());
        ShowDetail.append("Territory Size: ").append(Size).append("\n\n");
        ShowDetail.append("Soldiers\n");
        //iterate through the soldier to add the soldiers' detail
        for(int i = 0; i < 7; i++){
            ShowDetail.append("  Level ").append(i).append(": ").append(Clicked.getSoldierLevel(i)).append("\n");
        }
        return ShowDetail.toString();
    }
}
