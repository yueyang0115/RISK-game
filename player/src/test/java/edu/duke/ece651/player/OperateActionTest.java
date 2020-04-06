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
    String Input = "{'player_0':[ {'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'D'}], 'territoryName':'A'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'L'}], 'territoryName':'B'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'B'},{'neighbor_2':'L'},{'neighbor_3':'J'},{'neighbor_4':'F'},{'neighbor_5':'D'}], 'territoryName':'C'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'F'}, {'neighbor_3':'E'}], 'territoryName':'D'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'F'}, {'neighbor_2':'G'}], 'territoryName':'E'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'C'},{'neighbor_2':'J'},{'neighbor_3':'I'},{'neighbor_4':'G'},{'neighbor_5':'E'}], 'territoryName':'F'}], 'player_1':[ {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'E'},{'neighbor_1':'F'},{'neighbor_2':'I'},{'neighbor_3':'H'}], 'territoryName':'G'}, {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'G'},{'neighbor_1':'I'}], 'territoryName':'H'}, {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'F'},{'neighbor_1':'J'},{'neighbor_2':'K'},{'neighbor_3':'H'},{'neighbor_4':'G'}], 'territoryName':'I'}, {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'C'},{'neighbor_1':'L'},{'neighbor_2':'K'},{'neighbor_3':'I'},{'neighbor_4':'F'}], 'territoryName':'J'},{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'I'},{'neighbor_1':'J'},{'neighbor_2':'L'}], 'territoryName':'K'},{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'J'},{'neighbor_3':'K'}], 'territoryName':'L'}] }";
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
      System.out.println("Number of Soldiers = " + Temp.getSoldierLevel(0));
      System.out.print("\n");
    }
    System.out.print("\n");
    System.out.println("[DEBUG] AttackAction:");
    for(Action Temp: readTest1.getAttackActions()){
      System.out.println("[DEBUG] OwnerName = " + Temp.getOwner());
      System.out.println("ActionType = " + Temp.getType());
      System.out.println("Source = " + Temp.getSrc().getTerritoryName());
      System.out.println("Destination = " + Temp.getDst().getTerritoryName() );
      System.out.println("Number of Soliders = " + Temp.getSoldierLevel(0));
      System.out.print("\n");
    }
  }

}
