package edu.duke.ece651.player;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.*;
import javafx.util.Pair;

public class PlayerTest {
  @Test
  public void test_Player() {
/*
    Player TestPlayer = new Player();
    Text T = new Text();
    TestPlayer.addDisplayable(T);
    Pair<Integer, String> playerInfo = new Pair<Integer, String>(0, "Green");
    TestPlayer.setPlayerInfo(playerInfo);
    
    String Input =
        "{'player_0': [{'owner': 'aaa', 'soldiers': 3, 'neighbor': [{'neighbor_0': 'aaa_neigh1'}, {'neighbor_1': 'aaa_neigh2'}], 'territoryName': 'aaaTerr1'}, {'owner': 'aaa', 'soldiers': 4, 'neighbor': [{'neighbor_0': 'aaa_neigh3'}, {'neighbor_1': 'aaa_neigh4'}], 'territoryName': 'aaaTerr2'}],'player_1': [{'owner': 'bbb', 'soldiers': 5, 'neighbor': [{'neighbor_0': 'bbb_neigh1'}, {'neighbor_1': 'bbb_neigh2'}], 'territoryName': 'bbbTerr1'}, {'owner': 'bbb', 'soldiers': 6, 'neighbor': [{'neighbor_0': 'bbb_neigh3'}, {'neighbor_1': 'bbb_neigh4'}], 'territoryName': 'bbbTerr2'}]}";
    MyFormatter Map = new MyFormatter(2);
    HashMap<Integer, ArrayList<Territory>> TerritoryMap = new HashMap<>();
    Map.MapParse(TerritoryMap, Input);
    TestPlayer.setTerritoryMap(TerritoryMap);

    
    TestPlayer.displayMap();

    
    MyFormatter AllAct = new MyFormatter(3);
    String Input1 = "{'player_0':[{'owner':'aaa','dst':{'owner':'aaa','territoryName':'aaaTerr2','soldiers':4,'neighbor':[{'neighbor_0':'aaa_neigh3'}]},'src':{'owner':'aaa','territoryName':'aaaTerr1','soldiers':3,'neighbor':[{'neighbor_0':'aaa_neigh1'},{'neighbor_1':'aaa_neigh2'}]},'soldiers':12, 'type':'Move'},{'owner':'bbb','dst':{'owner':'bbb','territoryName':'bbbTerr2','soldiers':6,'neighbor':[{'neighbor_0':'bbb_neigh3'},{'neighbor_1':'bbb_neigh4'}]},'src':{'owner':'bbb','territoryName':'bbbTerr1','soldiers':5,'neighbor':[{'neighbor_0':'bbb_neigh1'},{'neighbor_1':'bbb_neigh2'}]},'soldiers':20,'type':'Move'}],'player_2':[{'owner':'aaa','dst':{'owner':'aaa','territoryName':'aaaTerr2','soldiers':4,'neighbor':[{'neighbor_0':'aaa_neigh3'},{'neighbor_1':'aaa_neigh4'}]},'src':{'owner':'aaa','territoryName':'aaaTerr1','soldiers':3,'neighbor':[{'neighbor_0':'aaa_neigh1'},{'neighbor_1':'aaa_neigh2'}]},'soldiers':12,'type': 'Attack'},{'owner':'bbb','dst':{'owner':'bbb','territoryName':'bbbTerr2','soldiers':6,'neighbor':[{'neighbor_0':'bbb_neigh3'},{'neighbor_1':'bbb_neigh4'}]},'src':{'owner':'bbb','territoryName':'bbbTerr1','soldiers':5,'neighbor':[{'neighbor_0':'bbb_neigh1'},{'neighbor_1':'bbb_neigh2'}]},'soldiers':20,'type':'Attack'}]}";
    
    HashMap<Integer, ArrayList<Action>> ParsedAction =  new HashMap<>();
    AllAct.AllActionParse(ParsedAction, Input1);

    TestPlayer.setAllAction(ParsedAction);
    TestPlayer.displayAction();*/
  }

}
