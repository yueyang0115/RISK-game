package edu.duke.ece651.player;

import java.util.*;
import edu.duke.ece651.shared.*;
import javafx.util.Pair;
import org.apache.commons.math3.analysis.function.Add;

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
    //stored territorymap by players own territory and whole territories
    for(HashMap.Entry<Integer, ArrayList<Territory>> entry : TerritoryMap.entrySet()){
      int PlayerID = PlayerInfo.getKey();
      if(entry.getKey() == PlayerID){
        ownTerritories = entry.getValue();
      }
      for(int i = 0; i < entry.getValue().size(); i++){
        wholeTerritories.add(entry.getValue().get(i));
      }
    }
    //System.out.println("[DEBUG] Own TerritoriesNum: " + ownTerritories.size());
 }
 
  public ArrayList<Action> getMoveActions(){
    return MoveAction;
  }
  
  public ArrayList<Action> getAttackActions(){
      return AttackAction;
  }
  
  public void SeparateAction(){
    //stored move and attack action separately
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
    //wait for player to input their action
    Scanner s = new Scanner(System.in);
    while(true){
      Action CurrentAction = new Action();
      String acttype = readActionType(s);
      //System.out.println("[DEBUG] ActionType = " + acttype);
      if(acttype.equals("D")){
        System.out.println("Finished Input Actions");
        break;
      }
      if(!acttype.equals("M") && !acttype.equals("A") ){
        System.out.println("[Invalid] ActionType");
        continue;
      }
      CurrentAction.setType(ActionType.get(acttype));
      //Simple check src, dst, soldiersNum
      if(!readSrc(s, CurrentAction, acttype) || !readDst(s, CurrentAction, acttype) || !readNum(s, CurrentAction)){
        continue;
      }
      String OwnerName  = "player_" + playerInfo.getKey();
      CurrentAction.setOwner(OwnerName);
      //store the input action into list
      allAction.add(CurrentAction);
    }
    
    SeparateAction();
  }
  
  public String readActionType(Scanner s){
    //print prompt + read Action Type
     System.out.println("You are the " + playerInfo.getValue() + " player, what would you like to do?");
      System.out.println(" (M)ove");
      System.out.println(" (A)ttack");
      System.out.println(" (D)one");
      return s.nextLine();
  }
  
  public boolean readSrc(Scanner s, Action curAction,String ActionType){
    System.out.println("Please input the source territory: ");
    String src = s.nextLine();
    if (ActionType.equals("M")) {
      //check if that src for move action is valid
      for (Territory Temp : ownTerritories) {
        if (Temp.getTerritoryName().equals(src)) {
          curAction.setSrc(Temp);
          return true;
        }
      }
    }
    else{
      //for attack actions
      for(Territory WholeTemp: wholeTerritories){
        if (WholeTemp.getTerritoryName().equals(src)) {
          curAction.setSrc(WholeTemp);
          return true;
        }
      }
    }
    System.out.println("[Invalid] The source territory is invalid!");
    return false;
  }

  public boolean readDst(Scanner s, Action curAction, String ActionType){
     System.out.println("Please input the destination territory: ");
     String dst = s.nextLine();
     //System.out.println("[DEBUG] Dst Territory: " + dst);
     if(ActionType.equals("M")){
       for(Territory Temp : ownTerritories){
         if(Temp.getTerritoryName().equals(dst)){  
           curAction.setDst(Temp);
           return true;
         }
       }
     }
     else {
       for (Territory Temp : wholeTerritories) {
         if (Temp.getTerritoryName().equals(dst) && !ownTerritories.contains(Temp)) {
           curAction.setDst(Temp);
           return true;
         }
       }
     }
     System.out.println("[Invalid] The destination territory is invalid!");
     return false;
  }
  
  public boolean readNum(Scanner s, Action curAction){
     System.out.println("Please input the number of soldiers: ");
     String Num = s.nextLine();
     //make sure the player only input numbers
     for (int i = 0; i < Num.length(); i++){
       if(!Character.isDigit(Num.charAt(i))){
         System.out.println("[Invalid] Soldiers Number needs to be integer!");
         return false; 
       }
     }
     int soldiersNum = Integer.valueOf(Num);
     curAction.setSoldiers(soldiersNum);
     return true;
  }
  
}
