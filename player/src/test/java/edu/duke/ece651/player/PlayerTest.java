package edu.duke.ece651.player;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.*;
import javafx.util.Pair;

public class PlayerTest {
  @Test
  public void test_Player() throws IOException{

    PlayerHelper player = new PlayerHelper();

    Text T = new Text();
    player.addDisplayable(T);
    Pair<Integer, String> playerInfo = new Pair<Integer, String>(0, "Green");
    player.setPlayerInfo(playerInfo);
    
    String Input  = "{'player_0':[ {'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'D'}], 'territoryName':'A'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'L'}], 'territoryName':'B'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'B'},{'neighbor_2':'L'},{'neighbor_3':'J'},{'neighbor_4':'F'},{'neighbor_5':'D'}], 'territoryName':'C'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'F'}, {'neighbor_3':'E'}], 'territoryName':'D'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'F'}, {'neighbor_2':'G'}], 'territoryName':'E'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'C'},{'neighbor_2':'J'},{'neighbor_3':'I'},{'neighbor_4':'G'},{'neighbor_5':'E'}], 'territoryName':'F'}], 'player_1':[ {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'E'},{'neighbor_1':'F'},{'neighbor_2':'I'},{'neighbor_3':'H'}], 'territoryName':'G'}, {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'G'},{'neighbor_1':'I'}], 'territoryName':'H'}, {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'F'},{'neighbor_1':'J'},{'neighbor_2':'K'},{'neighbor_3':'H'},{'neighbor_4':'G'}], 'territoryName':'I'}, {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'C'},{'neighbor_1':'L'},{'neighbor_2':'K'},{'neighbor_3':'I'},{'neighbor_4':'F'}], 'territoryName':'J'},{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'I'},{'neighbor_1':'J'},{'neighbor_2':'L'}], 'territoryName':'K'},{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'J'},{'neighbor_3':'K'}], 'territoryName':'L'}] }";
    MyFormatter Map = new MyFormatter(2);
    HashMap<Integer, ArrayList<Territory>> TerritoryMap = new HashMap<>();
    Map.MapParse(TerritoryMap, Input);
    player.setTerritoryMap(TerritoryMap);

    
    player.displayMap();

    
    MyFormatter AllAct = new MyFormatter(2);
    String Input1 = "{'player_1':[{'owner':'player_1','dst':{'owner':'player_0','territoryName':'B','soldiers':[{'level_0':3},{'level_1':0},{'level_2':0},{'level_3':0},{'level_4':0},{'level_5':0},{'level_6':0}],'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'L'}]},'src':{'owner':'player_1','territoryName':'L','soldiers':[{'level_0':3},{'level_1':0},{'level_2':0},{'level_3':0},{'level_4':0},{'level_5':0},{'level_6':0}],'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'J'},{'neighbor_3':'K'}]},'soldiers':[{'level_0':2},{'level_1':0},{'level_2':0},{'level_3':0},{'level_4':0},{'level_5':0},{'level_6':0}],'type':'Attack'}],'player_0':[{'owner':'player_0','dst':{'owner':'player_0','territoryName':'A','soldiers':[{'level_0':3},{'level_1':0},{'level_2':0},{'level_3':0},{'level_4':0},{'level_5':0},{'level_6':0}],'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'D'}]},'src':{'owner':'player_0','territoryName':'C','soldiers':[{'level_0':3},{'level_1':0},{'level_2':0},{'level_3':0},{'level_4':0},{'level_5':0},{'level_6':0}],'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'B'},{'neighbor_2':'L'},{'neighbor_3':'J'},{'neighbor_4':'F'},{'neighbor_5':'D'}]},'soldiers':[{'level_0':2},{'level_1':0},{'level_2':0},{'level_3':0},{'level_4':0},{'level_5':0},{'level_6':0}],'type':'Move'}]}";

    HashMap<Integer, ArrayList<Action>> ParsedAction =  new HashMap<>();
    AllAct.AllActionParse(ParsedAction, Input1);

    player.setAllAction(ParsedAction);
    player.displayAction();

    player.getFoodResource();
    player.ClearActions();
    player.getPlayerInfo();
    player.getTerritoryMap();
    player.setLoseButWatch(true);
    player.setLose(true);
    player.setAsk(true);
    player.getAllAction();
    player.getCommunicator();
    player.AddTechResource(TerritoryMap,playerInfo);
    player.setID(0);
    assert(0 == player.getID());
    Action TempAction = new Action();
    player.setMoveAction(TempAction);
    player.setAttackAction(TempAction);
    Upgrade ActionUp = new Upgrade();
    player.setUpgradeAction(ActionUp);
    player.setPlayerNum(3);
    player.setTechResource(100);
    assert(100 == player.getTechResource());

  }

}
