package edu.duke.ece651.player;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.ColorID;
import edu.duke.ece651.shared.Territory;
import javafx.scene.control.Button;
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
                System.out.println("Territory Name: " + TerrName);
                String setBack = "-fx-background-color: " + background + ";";
                System.out.println("Set Style" + setBack);
                Button Btn = ButtonMap.get(TerrName);
                Btn.setStyle(setBack);
            }
        }
        System.out.println("Already paint color");
    }
    @Override
    public void showAction(HashMap<Integer, ArrayList<Action>> RecvAction, Pair<Integer, String> playerInfo) {

    }

}
