package edu.duke.ece651.player;

import java.util.*;
import edu.duke.ece651.shared.*;
import javafx.util.Pair;

public class OperateAction {
  private ArrayList<Territory> ownTerritories;
  private ArrayList<Territory> wholeTerritories;
  private Pair<Integer, String> playerInfo;
  private ArrayList<Action> allAction;
  private ArrayList<Action> MoveAction;
  private ArrayList<Action> AttackAction;
  private HashMap<String, String> ActionType;
  
  public OperateAction(Pair<Integer, String> PlayerInfo, HashMap<Integer, ArrayList<Territory>> TerritoryMap ){
    allAction = new ArrayList<>();
    MoveAction = new ArrayList<>();
    AttackAction = new ArrayList<>();
    wholeTerritories = new ArrayList<>();
    ActionType = new HashMap<>();
    ActionType.put("M", "Move");
    ActionType.put("A", "Attack");
    playerInfo = PlayerInfo;
    for(HashMap.Entry<Integer, ArrayList<Territory>> entry : TerritoryMap.entrySet()){
      int PlayerID = PlayerInfo.getKey();
      if(entry.getKey() == PlayerID){
        ownTerritories = entry.getValue();
      }
      for(int i = 0; i < entry.getValue().size(); i++){
        wholeTerritories.add(entry.getValue().get(i));
      }
    }
    
 }

  public void SeparateAction(){
    for(Action Current : allAction){
      String CurrType = Current.getType();
      if(CurrType == "Move"){
        MoveAction.add(Current);
      }
      else{
        AttackAction.add(Current);
      }
    }
  }
  public void readAction(){
    Scanner s = new Scanner(System.in);
    while(true){
      Action CurrentAction = new Action();
      String acttype = readActionType(s);
      if(acttype == "D"){
        break;
      }
      if(acttype != "M" && acttype != "A"){
        continue;
      }
      CurrentAction.setType(ActionType.get(acttype));
      //Simple check src, dst, soldiersNum
      if(!readSrc(s, CurrentAction) || !readDst(s, CurrentAction, acttype) || !readNum(s, CurrentAction)){
        continue;
      }
      CurrentAction.setOwner(playerInfo.getValue());
      allAction.add(CurrentAction);
    }
  }
  
  public String readActionType(Scanner s){
     System.out.println("You are the " + playerInfo.getValue() + "player, what would you like to do?");
      System.out.println(" (M)ove");
      System.out.println(" (A)ttack");
      System.out.println(" (D)one");
      return s.nextLine();
  }
  
  public boolean readSrc(Scanner s, Action curAction){
    System.out.println("Please input the source territory: ");
    String src = s.nextLine();
    for(Territory Temp : ownTerritories){
      if(Temp.getTerritoryName() == src){ 
        curAction.setSrc(Temp);
        return true;
      }
    }
    return false;
  }

  public boolean readDst(Scanner s, Action curAction, String ActionType){
     System.out.println("Please input the destination territory: ");
     String dst = s.nextLine();
     if(ActionType == "M"){
       for(Territory Temp : ownTerritories){
         if(Temp.getTerritoryName() == dst){ 
           curAction.setSrc(Temp);
           return true;
         }
       }
     }
     for(Territory Temp : wholeTerritories){
       if(Temp.getTerritoryName() == dst){ 
         curAction.setSrc(Temp);
         return true;
       }
     }
     return false;
  }
  
  public boolean readNum(Scanner s, Action curAction){
     System.out.println("Please input the number of soldiers: ");
     int soldiersNum = Integer.valueOf(s.nextLine());
     if(soldiersNum < 0){
       return false;
     }
     curAction.setSoldiers(soldiersNum);
     return true;
  }
  
}
