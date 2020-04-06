package edu.duke.ece651.player;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import javafx.scene.control.Button;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.shared.*;
import javafx.util.Pair;


public class TextTest {
  @Test
  public void test_Text() {
    HashMap<Integer, ArrayList<Territory>> Map = new HashMap<>();
    /*
    Territory TestTerr_a1 = new Territory();
    TestTerr_a1.setOwner("aaa");
    TestTerr_a1.setSoldierLevel(0,3);
    TestTerr_a1.setNeighbor("aaa1_neigh1");
    TestTerr_a1.setNeighbor("aaa1_neigh2");
    TestTerr_a1.setTerritoryName("aaa1_terr1");
    Territory TestTerr_a2 = new Territory();
    TestTerr_a2.setOwner("aaa");
    TestTerr_a2.setSoldierLevel(0, 13);
    TestTerr_a2.setNeighbor("aaa2_neigh1");
    TestTerr_a2.setNeighbor("aaa2_neigh2");
    TestTerr_a2.setTerritoryName("aaa2_terr2");
    ArrayList<Territory> InnerAL1 = new ArrayList<>();
    InnerAL1.add(TestTerr_a1);
    InnerAL1.add(TestTerr_a2);
    Map.put(0, InnerAL1);

    
    Territory TestTerr_b1 = new Territory();
    TestTerr_b1.setOwner("bbb");
    TestTerr_b1.setSoldierLevel(0,5);
    TestTerr_b1.setNeighbor("bbb1_neigh1");
    TestTerr_b1.setNeighbor("bbb1_neigh2");
    TestTerr_b1.setNeighbor("bbb1_neigh3");
    TestTerr_b1.setTerritoryName("bbb1_terr1");
    Territory TestTerr_b2 = new Territory();
    TestTerr_b2.setOwner("bbb");
    TestTerr_b2.setSoldierLevel(0,15);
    TestTerr_b2.setNeighbor("bbb2_neigh1");
    TestTerr_b2.setNeighbor("bbb2_neigh2");
    TestTerr_b2.setNeighbor("bbb2_neigh3");
    TestTerr_b2.setTerritoryName("bbb2_terr2");
    ArrayList<Territory> InnerAL2 = new ArrayList<>();
    InnerAL2.add(TestTerr_b1);
    InnerAL2.add(TestTerr_b2);
    Map.put(1, InnerAL2);
*/
    Pair<Integer, String> playerInfo = new Pair<>(0,"Green");
    Text Test = new Text();
    System.out.println("[DEBUG] TestText.showMap():\n");
    String TestMapStr = "{'player_0':[ {'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'D'}], 'territoryName':'A'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'L'}], 'territoryName':'B'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'B'},{'neighbor_2':'L'},{'neighbor_3':'J'},{'neighbor_4':'F'},{'neighbor_5':'D'}], 'territoryName':'C'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'F'}, {'neighbor_3':'E'}], 'territoryName':'D'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'F'}, {'neighbor_2':'G'}], 'territoryName':'E'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'C'},{'neighbor_2':'J'},{'neighbor_3':'I'},{'neighbor_4':'G'},{'neighbor_5':'E'}], 'territoryName':'F'}], 'player_1':[ {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'E'},{'neighbor_1':'F'},{'neighbor_2':'I'},{'neighbor_3':'H'}], 'territoryName':'G'}, {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'G'},{'neighbor_1':'I'}], 'territoryName':'H'}, {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'F'},{'neighbor_1':'J'},{'neighbor_2':'K'},{'neighbor_3':'H'},{'neighbor_4':'G'}], 'territoryName':'I'}, {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'C'},{'neighbor_1':'L'},{'neighbor_2':'K'},{'neighbor_3':'I'},{'neighbor_4':'F'}], 'territoryName':'J'},{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'I'},{'neighbor_1':'J'},{'neighbor_2':'L'}], 'territoryName':'K'},{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'J'},{'neighbor_3':'K'}], 'territoryName':'L'}] }";


    MyFormatter Change = new MyFormatter(2);
    Change.MapParse(Map, TestMapStr);

    /*HashMap<String, Button> ButtonMap = new HashMap<>();
    for(int i = 0; i < 7; i++){
      Button temp = new Button();
      String Current = "A" + Integer.valueOf(i);
      ButtonMap.put(Current, temp);
    }*/
    Test.showMap(Map, playerInfo, null);


    MyFormatter AllAct = new MyFormatter(2);
    String Input = "{'player_1':[{'owner':'player_1','dst':{'owner':'player_0','territoryName':'B','soldiers':[{'level_0':3},{'level_1':0},{'level_2':0},{'level_3':0},{'level_4':0},{'level_5':0},{'level_6':0}],'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'L'}]},'src':{'owner':'player_1','territoryName':'L','soldiers':[{'level_0':3},{'level_1':0},{'level_2':0},{'level_3':0},{'level_4':0},{'level_5':0},{'level_6':0}],'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'J'},{'neighbor_3':'K'}]},'soldiers':[{'level_0':2},{'level_1':0},{'level_2':0},{'level_3':0},{'level_4':0},{'level_5':0},{'level_6':0}],'type':'Attack'}],'player_0':[{'owner':'player_0','dst':{'owner':'player_0','territoryName':'A','soldiers':[{'level_0':3},{'level_1':0},{'level_2':0},{'level_3':0},{'level_4':0},{'level_5':0},{'level_6':0}],'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'D'}]},'src':{'owner':'player_0','territoryName':'C','soldiers':[{'level_0':3},{'level_1':0},{'level_2':0},{'level_3':0},{'level_4':0},{'level_5':0},{'level_6':0}],'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'B'},{'neighbor_2':'L'},{'neighbor_3':'J'},{'neighbor_4':'F'},{'neighbor_5':'D'}]},'soldiers':[{'level_0':2},{'level_1':0},{'level_2':0},{'level_3':0},{'level_4':0},{'level_5':0},{'level_6':0}],'type':'Move'}]}";
    HashMap<Integer, ArrayList<Action>> ParsedAction =  new HashMap<>();
    AllAct.AllActionParse(ParsedAction, Input);
    System.out.println("[DEBUG] TestText.showAction():\n");
    Test.showAction(ParsedAction, playerInfo, null);

  }

}
