package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class DoActionAllianceTest {
    private HashMap<Integer, Integer> resource;
    private HashMap<Integer, ArrayList<Territory>> myworld;
    private Territory territoryA;
    private Territory territoryB;
    private Territory territoryC;
    private Territory territoryD;
    private Territory territoryE;
    private Territory territoryF;
    private AllianceHelper ah;

    public DoActionAllianceTest(){
        ah = new AllianceHelper();
        resource = new HashMap<>();
        resource.put(0, 100);
        resource.put(1, 100);
        resource.put(2, 100);
        resource.put(3, 100);

        myworld = new HashMap<>();
        WorldInitter myworInitter = new WorldInitter(4);
        myworld = myworInitter.getWorld();

        MyFormatter formatter = new MyFormatter(2);
        String Astr =
                "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'D'}], 'territoryName':'A'}";
        territoryA = new Territory();
        JSONObject tempA = new JSONObject(Astr);
        territoryA = formatter.JsonToTerritory(tempA);

        String Estr =
                "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'F'}, {'neighbor_2':'G'}], 'territoryName':'E'}";
        territoryE = new Territory();
        JSONObject tempE = new JSONObject(Estr);
        territoryE = formatter.JsonToTerritory(tempE);
    }

    @Test
    public void test_moveAddAlliance(){
        Action myaction = new Action();
        myaction.setSrc(territoryA); // player_0.A
        myaction.setDst(territoryE); // player_1.E
        myaction.setOwner("player_0");
        myaction.setSoldierLevel(0, 2);
        myaction.setType("Move"); //pass through alliance

        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(myaction);
        HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
        myactionMap.put(0, actionList);

        ah.formNewAlliance(0,1);
        DoAction actor = new DoAction(myworld, myactionMap, resource, ah);
        actor.doMoveAction(actionList);
        myworld = actor.getNewWorld();
        assertEquals(myworld.get(0).get(0).getSoldierLevel(0), 1); //A
        assertEquals(myworld.get(1).get(1).getSoldierLevel(0), 5); //e
        assertEquals(ah.territoryisAllianced("E",0),true);
        //test if src dst are alliance, should do ah.addAlliance(dstName, Moveaction.owner)
    }
}
