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
    private Territory territoryJ;
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

        MyFormatter formatter = new MyFormatter(4);
        String Astr =
                "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'D'}], 'territoryName':'A'}";
        territoryA = new Territory();
        JSONObject tempA = new JSONObject(Astr);
        territoryA = formatter.JsonToTerritory(tempA);

        String Bstr =
                "{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'L'}], 'territoryName':'B'}";
        territoryB = new Territory();
        JSONObject tempB = new JSONObject(Bstr);
        territoryB = formatter.JsonToTerritory(tempB);

        String Dstr =
                "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'F'}, {'neighbor_3':'E'}], 'territoryName':'D'}";
        territoryD = new Territory();
        JSONObject tempD = new JSONObject(Dstr);
        territoryD = formatter.JsonToTerritory(tempD);

        String Estr =
                "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'F'}, {'neighbor_2':'G'}], 'territoryName':'E'}";
        territoryE = new Territory();
        JSONObject tempE = new JSONObject(Estr);
        territoryE = formatter.JsonToTerritory(tempE);

        String Fstr =
                "{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'C'},{'neighbor_2':'J'},{'neighbor_3':'I'},{'neighbor_4':'G'},{'neighbor_5':'E'}], 'territoryName':'F'}";
        territoryF = new Territory();
        JSONObject tempF = new JSONObject(Fstr);
        territoryF = formatter.JsonToTerritory(tempF);


        String Jstr =
                "{'owner':'player_3', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'C'},{'neighbor_1':'L'},{'neighbor_2':'K'},{'neighbor_3':'I'},{'neighbor_4':'F'}], 'territoryName':'J'}";
        territoryJ = new Territory();
        JSONObject tempJ = new JSONObject(Jstr);
        territoryJ = formatter.JsonToTerritory(tempJ);
    }

    @Test
    public void test_moveAddAlliance(){
        //test if src dst are alliance, should do ah.addAlliance(dstName, Moveaction.owner)
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
    }

    @Test
    public void test_combineDst(){
        //test: combine action from same player with same same dst
        Action myaction = new Action();
        myaction.setSrc(territoryA); // player_0.A
        myaction.setDst(territoryD); // player_1.D
        myaction.setOwner("player_0");
        myaction.setSoldierLevel(0, 2);
        myaction.setType("Attack"); //pass through alliance

        Action myaction_2 = new Action();
        myaction_2.setSrc(territoryB); // player_0.B
        myaction_2.setDst(territoryD); // player_1.D
        myaction_2.setOwner("player_0");
        myaction_2.setSoldierLevel(0, 1);
        myaction_2.setType("Attack"); //pass through alliance

        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(myaction);
        actionList.add(myaction_2);
        HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
        myactionMap.put(0, actionList);

        DoAction actor = new DoAction(myworld, myactionMap, resource, ah);
        actor.doAttackAction(actionList);
        myworld = actor.getNewWorld();
        assertEquals(myworld.get(0).get(0).getSoldierLevel(0), 1); //A
        assertEquals(myworld.get(0).get(1).getSoldierLevel(0), 2); //B
    }

    @Test
    public void test_allianceAttack(){
        //test: alliance attack same territory, should be combined
        territoryJ.setSoldierLevel(0,0);
        myworld.get(3).get(0).setSoldierLevel(0,0); //J

        Action myaction = new Action();
        myaction.setSrc(territoryA); // player_0.A
        myaction.setDst(territoryJ); // player_3.J
        myaction.setOwner("player_0");
        myaction.setSoldierLevel(0, 1);
        myaction.setType("Attack"); //pass through alliance

        Action myaction_2 = new Action();
        myaction_2.setSrc(territoryD); // player_1.D
        myaction_2.setDst(territoryJ); // player_3.J
        myaction_2.setOwner("player_1");
        myaction_2.setSoldierLevel(0, 2);
        myaction_2.setType("Attack"); //pass through alliance

        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(myaction);
        ArrayList<Action> actionList_2 = new ArrayList<>();
        actionList_2.add(myaction_2);
        HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
        myactionMap.put(0, actionList);
        myactionMap.put(1, actionList_2);
        ArrayList<Action> allActionList = new ArrayList<>();
        allActionList.add(myaction);
        allActionList.add(myaction_2);

        ah.formNewAlliance(0,1);
        assertEquals(territoryJ.getTerritoryName(), "J"); //J
        assertEquals(territoryJ.getSoldierLevel(0), 0); //J
        assertEquals(myaction.getDst().getTerritoryName(), "J"); //J
        assertEquals(myaction.getDst().getSoldierLevel(0), 0); //J
        assertEquals(myworld.get(3).get(0).getTerritoryName(), "J"); //J
        assertEquals(myworld.get(3).get(0).getSoldierLevel(0), 0); //J
        DoAction actor = new DoAction(myworld, myactionMap, resource, ah);
        actor.doAttackAction(allActionList);
        myworld = actor.getNewWorld();
        assertEquals(myworld.get(0).get(0).getSoldierLevel(0), 2); //A
        assertEquals(myworld.get(1).get(0).getSoldierLevel(0), 1); //B
        assertEquals(myworld.get(1).get(3).getOwner(),"player_1"); //J's owner is player_1
        assertEquals(ah.territoryisAllianced("J",0),true); //J has player_0 's soldier
    }

    @Test
    public void test_breakAlliance(){
        //test: player_0 attack alliance player_1, alliance break, soldier get back
        // assume territoryF hold player_0 alliance soldiers, F should return soldiers
        Action myaction = new Action();
        myaction.setSrc(territoryA); // player_0.A
        myaction.setDst(territoryD); // player_1.D
        myaction.setOwner("player_0");
        myaction.setSoldierLevel(0, 1);
        myaction.setType("Attack"); //break alliance

        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(myaction);
        HashMap<Integer, ArrayList<Action>> myactionMap = new HashMap<>();
        myactionMap.put(0, actionList);

        ah.formNewAlliance(0,1);
        ah.addAlliance("E",0);
        DoAction actor = new DoAction(myworld, myactionMap, resource, ah);
        actor.checkAllianceBreak(actionList);
        actor.doAttackAction(actionList);
        myworld = actor.getNewWorld();
        assertEquals(myworld.get(0).get(0).getSoldierLevel(0), 4); //A
        assertEquals(myworld.get(1).get(0).getSoldierLevel(0), 3); //D
        assertEquals(ah.territoryisAllianced("D",0),false); //D doesn't have player_0 's soldier
        assertEquals(ah.playerisAllianced(0,1),false); //player_0 and player_1 don't allianced any more
        assertEquals(ah.territoryisAllianced("E",0),false); //F break alliance
        assertEquals(myworld.get(1).get(1).getSoldierLevel(0), 2); //F return 1/2 soldiers as numReturned

    }
}
