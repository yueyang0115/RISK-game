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
    System.out.println("[DEBUG] Own TerritoriesNum: " + ownTerritories.size());
 }
 
  public ArrayList<Action> getMoveActions(){
    return MoveAction;
  }
  
  public ArrayList<Action> getAttackActions(){
    return AttackAction;
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
      System.out.println("[DEBUG] ActionType = " + acttype);
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
      CurrentAction.setOwner(playerInfo.getValue());
      allAction.add(CurrentAction);
    }
    SeparateAction();
  }
  
  public String readActionType(Scanner s){
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
      for (Territory Temp : ownTerritories) {
        if (Temp.getTerritoryName().equals(src)) {
          curAction.setSrc(Temp);
          return true;
        }
      }
    }
    else{
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
     System.out.println("[DEBUG] Dst Territory: " + dst);
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
     int soldiersNum = Integer.valueOf(s.nextLine());
     if(soldiersNum < 0){
       System.out.println("[Invalid] Soldiers Number needs greater than 0!");
       return false;
     }
     curAction.setSoldiers(soldiersNum);
     return true;
  }
  
}
