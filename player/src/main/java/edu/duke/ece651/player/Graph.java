package edu.duke.ece651.player;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.ColorID;
import edu.duke.ece651.shared.Territory;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph implements Displayable{
    @Override
    public void showMap(HashMap<Integer, ArrayList<Territory>> CurrentMap, Pair<Integer, String> playerInfo, HashMap<String, Button> ButtonMap) {
        ColorID PlayerColor = new ColorID();
        for (HashMap.Entry<Integer, ArrayList<Territory>> entry : CurrentMap.entrySet()){
            //iterate each player color to set the button territory to its color
            String color = PlayerColor.getPlayerColor(entry.getKey());
            String background = color.toLowerCase();
            ArrayList<Territory> TerrList = entry.getValue();
            for(int i = 0; i < TerrList.size(); i++){
                Territory OneTerr = TerrList.get(i);
                String TerrName = OneTerr.getTerritoryName();
                //System.out.println("Territory Name: " + TerrName);
                String styleStr = getStyle(background, TerrName);
                //System.out.println("Set Style" + styleStr);
                Button Btn = ButtonMap.get(TerrName);
                Btn.setStyle(styleStr);
                Btn.setCursor(Cursor.HAND);
            }
        }
        System.out.println("Already paint color");
    }
    @Override
    public void showAction(HashMap<Integer, ArrayList<Action>> RecvAction, Pair<Integer, String> playerInfo, Label ShowLabel) {
        ColorID PlayerColor = new ColorID();
        StringBuilder Text = new StringBuilder();
        for (HashMap.Entry<Integer, ArrayList<Action>> entry : RecvAction.entrySet()){
            String color = PlayerColor.getPlayerColor(entry.getKey());
            Text.append(color + " Player:\n");
            Text.append("---------------\n");
            ArrayList<Action> ActionList = entry.getValue();
            for(int i = 0; i < ActionList.size(); i++){
                Action OneAction = ActionList.get(i);
                String ActionType = OneAction.getType();
                String Source = OneAction.getSrc().getTerritoryName();
                String Destination = OneAction.getDst().getTerritoryName();
                HashMap<Integer,Integer> soldierMap = OneAction.getSoldiers();
                for(int j = 0 ; j <soldierMap.size();j++) {
                    int ActSoldiers = OneAction.getSoldierLevel(j);
                    if (ActSoldiers != 0) {
                        Text.append("  " + ActionType + ":  " + ActSoldiers + " units of level " + i +" from " + Source + " to " + Destination + "\n");
                    }
                }
            }
        }
        ShowLabel.setText(Text.toString());
    }

    public String getStyle(String color, String territoryName) {
        StringBuilder sb = new StringBuilder();
        sb.append("-fx-background-color: " + color + ";");
        if (territoryName.equals("A")) { sb.append("-fx-shape: \"M 400 150 Q 450 400 350 400 Q 200 400 50 400 Q 50 200 250 200 Q 350 200 400 150\""); }
        else if (territoryName.equals("B")) { sb.append("-fx-shape: \"M 400 300 Q 450 300 600 300 L 600 100 L 400 100 Q 350 200 400 300\""); }
        else if (territoryName.equals("C")) { sb.append("-fx-shape: \"M 350 300 C 425 300 475 300 550 300 Q 650 200 600 50 L 350 50 Q 350 200 350 300\""); }
        else if (territoryName.equals("D")) { sb.append("-fx-shape: \"M 350 300 C 425 300 475 300 600 300 Q 650 200 600 100 Q 450 50 350 150 Q 350 200 350 300\""); }
        else if (territoryName.equals("E")) { sb.append("-fx-shape: \"M 400 200 Q 450 300 550 300 Q 650 300 750 250 Q 750 0 450 50 Q 450 100 400 200\""); }
        else if (territoryName.equals("F")) { sb.append("-fx-shape: \"M 350 350 C 450 350 450 350 550 350 Q 600 200 600 100 Q 450 0 350 100 Q 350 200 350 350\""); }
        else if (territoryName.equals("G")) { sb.append("-fx-shape: \"M 350 350 C 450 350 450 350 600 350 Q 650 250 600 150 Q 500 150 350 200 Q 350 200 350 350\""); }
        else if (territoryName.equals("H")) { sb.append("-fx-shape: \"M 550 350 Q 600 400 700 400 Q 800 250 750 100 L 550 100 Q 500 200 550 350\""); }
        else if (territoryName.equals("I")) { sb.append("-fx-shape: \"M 350 450 C 500 400 550 425 700 400 Q 650 250 700 100 L 350 100 Q 300 250 350 450\""); }
        else if (territoryName.equals("J")) { sb.append("-fx-shape: \"M 350 400 Q 500 450 700 400 Q 800 300 800 200 C 450 200 500 200 350 200 Q 300 250 350 400\""); }
        else if (territoryName.equals("K")) { sb.append("-fx-shape: \"M 350 350 C 500 350 550 450 700 400 Q 800 300 700 150 C 450 200 500 200 350 200 Q 300 250 350 350\""); }
        else if (territoryName.equals("L")) { sb.append("-fx-shape: \"M 350 450 C 450 500 450 450 550 450 Q 500 300 600 200 C 450 200 500 200 350 200 C 400 300 200 350 350 450\""); }
        sb.append(";");
        sb.append("-fx-border-color: black;");
        return sb.toString();
    }

}
