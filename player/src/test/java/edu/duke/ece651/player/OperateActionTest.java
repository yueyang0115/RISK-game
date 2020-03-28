package edu.duke.ece651.player;

import static org.junit.jupiter.api.Assertions.*;
import edu.duke.ece651.shared.*;
import javafx.util.Pair;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import org.junit.jupiter.api.Test;

public class OperateActionTest {
  @Test
  public void test_OperateAction() throws FileNotFoundException{
    String Input = "{'player_0':[ {'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'}], 'territoryName':'A'}, {'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'D'},{'neighbor_3':'E'},{'neighbor_4':'F'}], 'territoryName':'B'}, {'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'F'}], 'territoryName':'C'}, {'owner':'player_0', 'soldiers':3, 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'B'},{'neighbor_2':'E'},{'neighbor_3':'G'}], 'territoryName':'D'}],'player_1':[ {'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'},{'neighbor_2':'F'},{'neighbor_3':'G'},{'neighbor_4':'H'}], 'territoryName':'E'}, {'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'E'},{'neighbor_3':'H'}], 'territoryName':'F'}, {'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'E'}], 'territoryName':'G'}, {'owner':'player_1', 'soldiers':3, 'neighbor':[{'neighbor_0':'E'},{'neighbor_1':'F'}], 'territoryName':'H'}]}";   
    MyFormatter Map = new MyFormatter(2);
    HashMap<Integer, ArrayList<Territory>> TerritoryMap = new HashMap<>();
    Map.MapParse(TerritoryMap, Input);
    Pair<Integer, String> playerInfo = new Pair<Integer, String>(0, "Green");
    OperateAction readTest1 = new OperateAction(playerInfo, TerritoryMap);
    
    System.setIn(new FileInputStream("src/test/resources/TestReadAction1.txt"));
    readTest1.readAction();

    

    System.out.println("[DEBUG] MoveAction:");
    for(Action Temp: readTest1.getMoveActions()){
      System.out.println("[DEBUG] OwnerName = " + Temp.getOwner());
      System.out.println("ActionType = " + Temp.getType());
      System.out.println("Source = " + Temp.getSrc().getTerritoryName());
      System.out.println("Destination = " + Temp.getDst().getTerritoryName());
      System.out.println("Number of Soliders = " + Temp.getSoliders());
      System.out.print("\n");
    }
    System.out.print("\n");
    System.out.println("[DEBUG] AttackAction:");
    for(Action Temp: readTest1.getAttackActions()){
      System.out.println("[DEBUG] OwnerName = " + Temp.getOwner());
      System.out.println("ActionType = " + Temp.getType());
      System.out.println("Source = " + Temp.getSrc().getTerritoryName());
      System.out.println("Destination = " + Temp.getDst().getTerritoryName() );
      System.out.println("Number of Soliders = " + Temp.getSoliders());
      System.out.print("\n");
    }

  }

}
