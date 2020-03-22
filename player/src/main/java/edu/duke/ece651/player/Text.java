package edu.duke.ece651.player;
import edu.duke.ece651.shared.*;
import java.util.*;
import javafx.util.*;
public class Text implements Displayable{
  public void showMap(HashMap<Integer, ArrayList<Territory>> CurrentMap, Pair<Integer, String> playerInfo){
    ColorID PlayerColor = new ColorID();
    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : CurrentMap.entrySet()){
      String color = PlayerColor.getPlayerColor(entry.getKey());
      System.out.println(color + " Player:");
      System.out.println("---------------");
      ArrayList<Territory> TerrList = entry.getValue();
      for(int i = 0; i < TerrList.size(); i++){
        Territory OneTerr = TerrList.get(i);
        int Soldiers = OneTerr.getSoliders();
        String TerrName = OneTerr.getTerritoryName();
        ArrayList<String> Neigh = OneTerr.getNeighbor();
        String NeighName = "";
        for (int k = 0; k < Neigh.size(); k++){
          NeighName += Neigh.get(k);
          if(k != Neigh.size() - 1){
            NeighName += ", ";
          }
        }
        System.out.println(" " + Soldiers + " units in " + TerrName + " (next to: " + NeighName + ")");
      }
      System.out.print("\n");
    }
  }
  public void showAction(HashMap<Integer, ArrayList<Action>> RecvAction, Pair<Integer, String> playerInfo){
    ColorID PlayerColor = new ColorID();
     for (HashMap.Entry<Integer, ArrayList<Action>> entry : RecvAction.entrySet()){
      String color = PlayerColor.getPlayerColor(entry.getKey());
      System.out.println(color + " Player:");
      System.out.println("---------------");
      ArrayList<Action> ActionList = entry.getValue();
      for(int i = 0; i < ActionList.size(); i++){
        Action OneAction = ActionList.get(i);
        int ActSoldiers = OneAction.getSoliders();
        String ActionType = OneAction.getType();
        String Source = OneAction.getSrc().getTerritoryName();
        String Destination = OneAction.getDst().getTerritoryName();
        System.out.println(" " + ActionType + ": " + ActSoldiers + " units from " + Source + " to " + Destination);
      }
      System.out.print("\n");
    }
  }
}
