package edu.duke.ece651.player;
import edu.duke.ece651.shared.*;
import java.util.*;
import javafx.util.*;
public class Text implements Displayable{
  public void show(HashMap<Integer, ArrayList<Territory>> CurrentMap, Pair<Integer, String> playerInfo){
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
    System.out.println("You are the " + playerInfo.getValue() + "player, what would you like to do?");
    System.out.println(" (M)ove");
    System.out.println(" (A)ttack");
    System.out.println(" (D)one");
  }
}
