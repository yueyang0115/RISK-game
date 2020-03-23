package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.Test;
import org.json.*;

public class FomatterTest {
  @Test
  public void test_Formater() {
    System.out.println("******************TEST MAP************************");
    String Input =
        "{'player_0': [{'owner': 'aaa', 'soldiers': 3, 'neighbor': [{'neighbor_0': 'aaa_neigh1'}, {'neighbor_1': 'aaa_neigh2'}], 'territoryName': 'aaaTerr1'}, {'owner': 'aaa', 'soldiers': 4, 'neighbor': [{'neighbor_0': 'aaa_neigh3'}, {'neighbor_1': 'aaa_neigh4'}], 'territoryName': 'aaaTerr2'}],'player_1': [{'owner': 'bbb', 'soldiers': 5, 'neighbor': [{'neighbor_0': 'bbb_neigh1'}, {'neighbor_1': 'bbb_neigh2'}], 'territoryName': 'bbbTerr1'}, {'owner': 'bbb', 'soldiers': 6, 'neighbor': [{'neighbor_0': 'bbb_neigh3'}, {'neighbor_1': 'bbb_neigh4'}], 'territoryName': 'bbbTerr2'}]}";
    MyFormatter Map = new MyFormatter(2);
    HashMap<Integer, ArrayList<Territory>> TerritoryMap = new HashMap<>();
    Map.MapParse(TerritoryMap, Input);

    System.out.println("================Empty================");
    String EmptyInput =  "{'player_1':[],'player_0':[{'owner':'player_0','territoryName':'A','soldiers':3,'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'}]}]}";
    MyFormatter MapEmpty = new MyFormatter(2);
    HashMap<Integer, ArrayList<Territory>> TerritoryMapEmpty = new HashMap<>();
    MapEmpty.MapParse(TerritoryMapEmpty, EmptyInput);

    System.out.println("================Null================");
    String NullInput =  "{'player_1':[],'player_0':[{'owner':'player_0','territoryName':'A','soldiers':3,'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'D'}]}]}";
    MyFormatter MapNull = new MyFormatter(3);
    HashMap<Integer, ArrayList<Territory>> TerritoryMapNull = new HashMap<>();
    MapNull.MapParse(TerritoryMapNull, NullInput);



    System.out.println("*****************TEST ACTION****************");
    String InputMoveAction = "{'Move': [{'src':{'owner': 'aaa', 'soldiers': 3, 'neighbor': [{'neighbor_0': 'aaa_neigh1'}, {'neighbor_1': 'aaa_neigh2'}], 'territoryName': 'aaaTerr1'}, 'dst': {'owner': 'aaa', 'soldiers': 4, 'neighbor': [{'neighbor_0': 'aaa_neigh3'}, {'neighbor_1': 'aaa_neigh4'}], 'territoryName': 'aaaTerr2'}, 'soldiers': 12,'owner': 'aaa'}, {'src': {'owner': 'bbb', 'soldiers': 5, 'neighbor': [{'neighbor_0': 'bbb_neigh1'}, {'neighbor_1': 'bbb_neigh2'}], 'territoryName': 'bbbTerr1'}, 'dst':{'owner': 'bbb', 'soldiers': 6, 'neighbor': [{'neighbor_0': 'bbb_neigh3'}, {'neighbor_1': 'bbb_neigh4'}], 'territoryName': 'bbbTerr2'}, 'soldiers': 20, 'owner': 'bbb'}]}";
    ArrayList<Action> MoveActionArray = new ArrayList<>();
    Map.ActionParse(MoveActionArray, InputMoveAction);
    System.out.println("[DEBUG]================ Parse Move=============");
    for(Action Temp: MoveActionArray){
      System.out.println("ActionType = " + Temp.getType());
      System.out.println("ActionOwner = " + Temp.getOwner());  
      System.out.println("ActionSoldiers = " + Temp.getSoliders());
      System.out.println("Source TerritoryName = " + Temp.getSrc().getTerritoryName());
      System.out.println("Destination TerritoryName = " + Temp.getDst().getTerritoryName());
    }

    String InputAttackAction = "{'Attack': [{'src':{'owner': 'aaa', 'soldiers': 3, 'neighbor': [{'neighbor_0': 'aaa_neigh1'}, {'neighbor_1': 'aaa_neigh2'}], 'territoryName': 'aaaTerr1'}, 'dst': {'owner': 'aaa', 'soldiers': 4, 'neighbor': [{'neighbor_0': 'aaa_neigh3'}, {'neighbor_1': 'aaa_neigh4'}], 'territoryName': 'aaaTerr2'}, 'soldiers': 12,'owner': 'aaa'}, {'src': {'owner': 'bbb', 'soldiers': 5, 'neighbor': [{'neighbor_0': 'bbb_neigh1'}, {'neighbor_1': 'bbb_neigh2'}], 'territoryName': 'bbbTerr1'}, 'dst':{'owner': 'bbb', 'soldiers': 6, 'neighbor': [{'neighbor_0': 'bbb_neigh3'}, {'neighbor_1': 'bbb_neigh4'}], 'territoryName': 'bbbTerr2'}, 'soldiers': 20, 'owner': 'bbb'}]}";
    ArrayList<Action> AttackActionArray = new ArrayList<>();
    Map.ActionParse(AttackActionArray, InputAttackAction);
    System.out.println("\n\n[DEBUG]================ Parse Attack================");
    for(Action Temp: AttackActionArray){
      System.out.println("ActionType = " + Temp.getType());
      System.out.println("ActionOwner = " + Temp.getOwner());  
      System.out.println("ActionSoldiers = " + Temp.getSoliders());
      System.out.println("Source TerritoryName = " + Temp.getSrc().getTerritoryName());
      System.out.println("Destination TerritoryName = " + Temp.getDst().getTerritoryName());
    }
    MyFormatter AllAct = new MyFormatter(3);
    HashMap<Integer, ArrayList<Action>> AllAction = new HashMap<>();
    ArrayList<Action> ActionList1 = new ArrayList<>();
    for(int i = 0; i < AttackActionArray.size(); i++){
      ActionList1.add(AttackActionArray.get(i));
    }
    AllAction.put(0, ActionList1);
    ArrayList<Action> ActionList2 = new ArrayList<>();
    for(int i = 0; i < MoveActionArray.size(); i++){
      ActionList2.add(MoveActionArray.get(i));
    }
    AllAction.put(2, ActionList2);
    JSONObject All = AllAct.AllActionCompose(AllAction);
    System.out.println("\n================== Compose All Action ===============");
    System.out.println(All.toString());

    System.out.println("\n================== Parse All Action ===============");
    HashMap<Integer, ArrayList<Action>> ParsedAction = new HashMap<>();
    AllAct.AllActionParse(ParsedAction, All.toString());
  }
  
  
}
