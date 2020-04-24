package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import java.io.*;
import java.util.*;

public class DoAction {
  private HashMap<Integer, ArrayList<Territory>> myworld;
  private String tempWorldStr;
  private HashMap<Integer, ArrayList<Action>> myActionMap;
  private HashSet<String> invalidPlayer;
  private MyFormatter myformatter;
  private ArrayList<Action> mymoveList;
  private HashMap<Integer, Integer> myResource;
  private HashMap<Integer, Integer> rawResource;
  private AllianceHelper myAllianceHelper;

  public DoAction(HashMap<Integer, ArrayList<Territory>> world,
      HashMap<Integer, ArrayList<Action>> actionsMap, HashMap<Integer, Integer> resource, AllianceHelper ah) {
    init();
    myworld = world;
    myActionMap = actionsMap;
    myformatter = new MyFormatter(myworld.size());
    tempWorldStr = myformatter.MapCompose(myworld).toString();
    myResource = resource;
    copyMap(rawResource, resource);
    myAllianceHelper = ah;
  }

  public DoAction(HashMap<Integer, ArrayList<Territory>> world) {
    init();
    myworld = world;
    myformatter = new MyFormatter(myworld.size());
  }
  private void init() {
    myworld = new HashMap<>();
    myActionMap = new HashMap<>();
    invalidPlayer = new HashSet<>();
    mymoveList = new ArrayList<>();
    myResource = new HashMap<>();
    rawResource = new HashMap<>();
  }
  //TODO: check alliance, if break, split


  // do upgrade action
  public void doUpgradeAction(ArrayList<Upgrade> upgradeList) {
    for (Upgrade CurrentAction : upgradeList) {
      Territory UpgradeTerr = findTerritory(myworld, CurrentAction.getTerritoryName());

      int preLevel = CurrentAction.getPrevLevel();
      int nextLevel = CurrentAction.getNextLevel();
      int CurrPreLevelNum = UpgradeTerr.getSoldierLevel(preLevel);
      int CurrNextLevelNum = UpgradeTerr.getSoldierLevel(nextLevel);

      UpgradeTerr.setSoldierLevel(preLevel, CurrPreLevelNum - CurrentAction.getNumber());
      UpgradeTerr.setSoldierLevel(nextLevel, CurrNextLevelNum + CurrentAction.getNumber());
    }
    tempWorldStr = myformatter.MapCompose(myworld).toString();
  }

  // domove action
  public void doMoveAction(ArrayList<Action> moveList) {
    System.out.println("[DEBUG] moveList.size() is " + moveList.size());
    for (int k = 0; k < moveList.size(); k++) {
      System.out.println("[DEBUG] action's src is " + moveList.get(k).getSrc().getTerritoryName());
      System.out.println("[DEBUG] action's dst is " + moveList.get(k).getDst().getTerritoryName());
      System.out.println(
          "[DEBUG] action's soldoerLevel0 has " + moveList.get(k).getSoldierLevel(0));
    }
    if (moveList.size() == 0) {
      return;
    }

    for (int i = 0; i < moveList.size(); i++) {
      // System.out.println("[DEBUG] i is " + i);
      Action action = moveList.get(i);

      // if player has previous invalid action, ignore all its actions
      if (invalidPlayer.contains(action.getOwner())) {
        System.out.println("[DEBUG] move action invalid");
        continue;
      }

      // check if action is valid
      ServerChecker mychecker = new ServerChecker(myworld, myAllianceHelper);
      ResourceChecker rschecker = new ResourceChecker(myResource, myworld);
      boolean isValid = mychecker.Check(action) && rschecker.CheckResource(action);
      if (!isValid) {
        System.out.println("[DEBUG] move action invalid");
        removePlayer(action);
        moveList.remove(action);
        invalidPlayer.add(action.getOwner());
        myformatter.MapParse(myworld, tempWorldStr); // reset world
        copyMap(myResource, rawResource); // reset resource
        i = -1;
        continue;
      }

      // do move action
      rschecker.reduceCost(myResource, action);
      moveHelper(action);
    }
    // tempWorldStr = myformatter.MapCompose(myworld).toString();
    mymoveList = moveList;
  }

  // do moveAction once
  private void moveHelper(Action action) {
    System.out.println("[DEBUG] move action valid");
    HashMap<Integer, Integer> movedSoldierMap = action.getSoldiers();
    for (HashMap.Entry<Integer, Integer> entry : movedSoldierMap.entrySet()) {
      int soldierLevel = entry.getKey();
      int numReduce = entry.getValue();
      if (numReduce == 0) {
        continue;
      }

      Territory srcTerritory = findTerritory(myworld, action.getSrc().getTerritoryName());
      Territory dstTerritory = findTerritory(myworld, action.getDst().getTerritoryName());
      System.out.println("[DEBUG] " + srcTerritory.getTerritoryName() + " move " + numReduce
          + "level_" + soldierLevel + " soldier to " + dstTerritory.getTerritoryName()
          + ", original_src_level  has " + srcTerritory.getSoldierLevel(soldierLevel)
          + ", original_ dst_level has " + dstTerritory.getSoldierLevel(soldierLevel));

      //TODO: if src dst are alliance, ah.addAlliance(dstName, Moveaction.owner), record where has two player's soldier
      if(ownerisAllianced(srcTerritory,dstTerritory,myAllianceHelper)){
        String srcOwner = srcTerritory.getOwner();
        int srcID = Character.getNumericValue(srcOwner.charAt(srcOwner.length() - 1));
        myAllianceHelper.addAlliance(dstTerritory.getTerritoryName(),srcID);
      }

      srcTerritory.setSoldierLevel(
          soldierLevel, srcTerritory.getSoldierLevel(soldierLevel) - numReduce);
      System.out.println("[DEBUG] after move, srcTerritory's name is "
          + srcTerritory.getTerritoryName() + ", num of soldier in level_" + soldierLevel + " is "
          + srcTerritory.getSoldierLevel(soldierLevel));

      dstTerritory.setSoldierLevel(
          soldierLevel, dstTerritory.getSoldierLevel(soldierLevel) + numReduce);
      System.out.println("[DEBUG] after move, dstTerritory's name is "
          + dstTerritory.getTerritoryName() + ", num of soldier in level_" + soldierLevel + " is "
          + dstTerritory.getSoldierLevel(soldierLevel));
    }
  }

  //check whether two territories' owners are allianced
  private boolean ownerisAllianced(Territory srcTerritory, Territory dstTerritory, AllianceHelper ah){
    String srcOwner = srcTerritory.getOwner();
    int srcID = Character.getNumericValue(srcOwner.charAt(srcOwner.length() - 1));
    String dstOwner = dstTerritory.getOwner();
    int dstID = Character.getNumericValue(dstOwner.charAt(dstOwner.length() - 1));
    boolean isAllianced = ah.playerisAllianced(srcID, dstID);
    return isAllianced;
  }

  //for attack action. remove the soldiers involved in this action out of territory
  private void removeSoldier(Action action) {
    HashMap<Integer, Integer> movedSoldierMap = action.getSoldiers();
    for (HashMap.Entry<Integer, Integer> entry : movedSoldierMap.entrySet()) {
      int soldierLevel = entry.getKey();
      int numAttack = entry.getValue();
      if (numAttack == 0) {
        continue;
      }
      Territory attackTerritory = findTerritory(myworld, action.getSrc().getTerritoryName());
      attackTerritory.setSoldierLevel(
          soldierLevel, attackTerritory.getSoldierLevel(soldierLevel) - numAttack);
    }
  }

  // remove player that contains invalid action from actionsmap
  private void removePlayer(Action action) {
    // System.out.println("[DEBUG] in remove player, action inValid");
    String playerName = action.getOwner();
    int playerID = Character.getNumericValue(playerName.charAt(playerName.length() - 1));
    if (myActionMap.containsKey(playerID)) {
      // System.out.println(
      //    "[DEBUG] before remove invalid player, actionMap.size is " + myActionMap.size());
      myActionMap.remove(playerID);
      // System.out.println(
      //   "[DEBUG] after remove invalid player, actionMap.size is " + myActionMap.size());
    }
  }

  //deep copy hashMap
  private void copyMap(HashMap<Integer, Integer> rscMap, HashMap<Integer, Integer> dstMap) {
    for (HashMap.Entry<Integer, Integer> entry : dstMap.entrySet()) {
      rscMap.put(entry.getKey(), entry.getValue());
    }
  }

  //do attack action
  public void doAttackAction(ArrayList<Action> attackList) {
    System.out.println("[DEBUG] attackList.size() is " + attackList.size());
    for (int k = 0; k < attackList.size(); k++) {
      System.out.println(
          "[DEBUG] action's src is " + attackList.get(k).getSrc().getTerritoryName());
      System.out.println(
          "[DEBUG] action's dst is " + attackList.get(k).getDst().getTerritoryName());
    }
    if (attackList.size() == 0) {
      return;
    }

    for (int k = 0; k < attackList.size(); k++) {
      Action action = attackList.get(k);
      System.out.println(
          "+++++++++++++++++ [Before] Current Attack Action Number Level 0 ++++++++++++++ \n"
          + action.getSoldierLevel(0));
      // if player has previous invalid action, ignore all its actions
      if (invalidPlayer.contains(action.getOwner())) {
        System.out.println("[DEBUG] attack action invalid");
        continue;
      }

      // based on moveActions result, check if attackaction is valid
      ServerChecker mychecker = new ServerChecker(myworld, myAllianceHelper);
      ResourceChecker rschecker = new ResourceChecker(myResource, myworld);
      boolean isValid = mychecker.Check(action) && rschecker.CheckResource(action);
      if (!isValid) {
        System.out.println("[DEBUG] attack action invalid");
        removePlayer(action);
        attackList.remove(action);
        invalidPlayer.add(action.getOwner());
        myformatter.MapParse(myworld, tempWorldStr); // reset world
        copyMap(myResource, rawResource); // reset resource
        k = -1;
        continue;
      }
      // if valid, remove soldiers from attackTerritory
      rschecker.reduceCost(myResource, action);
      removeSoldier(action);
    }

    // reset map back to after upgrade performed status, do move actions first
    myformatter.MapParse(myworld, tempWorldStr); // reset world
    copyMap(myResource, rawResource); // reset resource
    doMoveAction(mymoveList);

    // perform attack actions for all
    // TODO: first move soldiers out of srcTerritory and reduceCost
    for (int k = 0; k < attackList.size(); k++) {
      Action action = attackList.get(k);
      if (invalidPlayer.contains(action.getOwner())) {
        //System.out.println("[DEBUG] attack action invalid");
        continue;
      }
      removeSoldier(action);

      ResourceChecker rschecker = new ResourceChecker(myResource, myworld);
      rschecker.reduceCost(myResource, action);
    }

    //TODO: then combine action from same player and has same dst
    for (int i = 0; i < attackList.size(); i++) {
      Action action = attackList.get(i);
      if (invalidPlayer.contains(action.getOwner())) {
        //System.out.println("[DEBUG] attack action invalid");
        continue;
      }
      for(int j = i + 1; j< attackList.size(); j++){
        Action nextAction = attackList.get(j);
        boolean sameOwner = nextAction.getOwner().equals(action.getOwner());
        boolean sameDst = nextAction.getDst().getTerritoryName().equals(action.getDst().getTerritoryName());
        if( sameOwner && sameDst){
          HashMap<Integer, Integer> toSoldiers = action.getSoldiers();
          HashMap<Integer, Integer> fromSoldiers = nextAction.getSoldiers();
          combineSoldier(toSoldiers,fromSoldiers);
          action.setSoldiers(toSoldiers);
          attackList.remove(j);
        }
      }
    }

    //go through dstMap, combine actions with same dst

    //perform real attack action
    for (int i = 0; i < attackList.size(); i++) {
      Action action = attackList.get(i);
      if (invalidPlayer.contains(action.getOwner())) {
        //System.out.println("[DEBUG] attack action invalid");
        continue;
      }
//      ResourceChecker rschecker = new ResourceChecker(myResource, myworld);
//      rschecker.reduceCost(myResource, action);

      //TODO: find if has alliance, combine, bool attackHelper()
      attackHelper(action);
      //TODO: if win, ah.addAlliance(name,  贡献小的.playerid)
      System.out.println(
          "+++++++++++++++++ [After] Current Attack Action Number Level 0 ++++++++++++++ \n"
          + action.getSoldierLevel(0));
    }
    invalidPlayer.clear();
  }

  private void combineSoldier(HashMap<Integer, Integer> toSoldiers, HashMap<Integer, Integer> fromSoldiers){
      System.out.println("[DEBUG] combine the soldiers in two actions");
      for (HashMap.Entry<Integer, Integer> entry : toSoldiers.entrySet()) {
        int soldierLevel = entry.getKey();
        int numTo = toSoldiers.get(soldierLevel);
        int numFrom = fromSoldiers.get(soldierLevel);

        System.out.println("[DEBUG] add " +  numFrom
                + " level_" + soldierLevel + " soldier to original " + numTo +" soldier");
        toSoldiers.replace(
                soldierLevel, numTo + numFrom);
        System.out.println("[DEBUG] after add, toAction get  "
                + (numTo+numFrom) + " soldier in level_" + soldierLevel);
      }
  }

  // do atatckAction once
  private void attackHelper(Action action) {
    System.out.println("[DEBUG] attack action valid");
    HashMap<Integer, Integer> attackSoldierMap = action.getSoldiers();
    Territory attackTerritory = findTerritory(myworld, action.getSrc().getTerritoryName());
    Territory defenceTerritory = findTerritory(myworld, action.getDst().getTerritoryName());
    HashMap<Integer, Integer> defenceSoldierMap = defenceTerritory.getSoldiers();

    // DEBUG
    for (HashMap.Entry<Integer, Integer> entry : attackSoldierMap.entrySet()) {
      int soldierLevel = entry.getKey();
      int numAttack = entry.getValue();
      if (numAttack == 0) {
        continue;
      }
      System.out.println("[DEBUG] " + attackTerritory.getTerritoryName() + " attack "
          + defenceTerritory.getTerritoryName() + ", attack_level_" + soldierLevel + " has "
          + attackSoldierMap.get(soldierLevel) + ", defence_level_" + soldierLevel + " has "
          + defenceSoldierMap.get(soldierLevel));
    }

    Random rand = new Random();
    int attackLevel;
    int defenceLevel;
    int i = 0;

    //use bonus + generated random number to attack
    Bonus bonus = new Bonus();
    while (countSoldier(defenceSoldierMap) != 0 && countSoldier(attackSoldierMap) != 0) {
      if (i % 2 == 0) {
        attackLevel = findMaxSoldier(attackSoldierMap);
        defenceLevel = findMinSoldier(defenceSoldierMap);
      } else {
        attackLevel = findMinSoldier(attackSoldierMap);
        defenceLevel = findMaxSoldier(defenceSoldierMap);
      }
      int randAttack = rand.nextInt(20) + 0;
      int randDefence = rand.nextInt(20) + 0;
      int attackBonus = bonus.getBonus(attackLevel);
      int defenceBonus = bonus.getBonus(defenceLevel);
      if (randAttack + attackBonus < randDefence + defenceBonus) {
        System.out.println("[DEBUG] randDefence is bigger");
        attackSoldierMap.put(attackLevel, attackSoldierMap.get(attackLevel) - 1);
      } else if (randDefence + defenceBonus < randAttack + attackBonus) {
        System.out.println("[DEBUG] randAttack is bigger");
        defenceSoldierMap.put(defenceLevel, defenceSoldierMap.get(defenceLevel) - 1);
      }
      i++;
    }

    if (countSoldier(defenceSoldierMap) == 0) {
      System.out.println("[DEBUG] attack success");
      changeOwner(defenceTerritory, attackTerritory, attackSoldierMap);
    } else { // numAttack = 0
      System.out.println("[DEBUG] defence success");
      defenceTerritory.setSoldiers(defenceSoldierMap);
    }

    // DEBUG
    System.out.println("[DEBUG] after attack, attackTerritory's name is "
        + attackTerritory.getTerritoryName() + ", owner is " + attackTerritory.getOwner());
    System.out.println("[DEBUG] after attack, defenceTerritory's name is "
        + defenceTerritory.getTerritoryName() + ", owner is " + defenceTerritory.getOwner());
    for (HashMap.Entry<Integer, Integer> entry : attackTerritory.getSoldiers().entrySet()) {
      int soldierLevel = entry.getKey();
      int numAttack = entry.getValue();
      if (numAttack == 0) {
        continue;
      }
      System.out.println("[DEBUG] after attack, attack_level_" + soldierLevel + " has "
          + attackTerritory.getSoldierLevel(soldierLevel));
    }

    //DEBUG
    for (HashMap.Entry<Integer, Integer> entry : defenceTerritory.getSoldiers().entrySet()) {
      int soldierLevel = entry.getKey();
      int numAttack = entry.getValue();
      if (numAttack == 0) {
        continue;
      }
      System.out.println("[DEBUG] after attack, defence_level_" + soldierLevel + " has "
          + defenceTerritory.getSoldierLevel(soldierLevel));
    }

    MaptoJson myMaptoJson = new MaptoJson(myworld);
    System.out.println("[DEBUG] after attack, new worldmap is " + myMaptoJson.getJSON());
  }

  //count the total number of soldiers in soldieraMap
  public int countSoldier(HashMap<Integer, Integer> soldierMap) {
    int size = soldierMap.size();
    int count = 0;
    for (int i = 0; i < size; i++) {
      count += soldierMap.get(i);
    }
    return count;
  }

  //find the soldiers that has the highest level
  private int findMaxSoldier(HashMap<Integer, Integer> soldierMap) {
    int size = soldierMap.size();
    for (int i = size - 1; i >= 0; i--) {
      if (soldierMap.get(i) != 0) {
        System.out.println("find max soldierLevel is :" + i);
        return i;
      }
    }
    return -1;
  }

  //find the soldier that has the lowest level
  private int findMinSoldier(HashMap<Integer, Integer> soldierMap) {
    int size = soldierMap.size();
    for (int i = 0; i < size; i++) {
      System.out.println("find min soldierLevel is :" + i);
      if (soldierMap.get(i) != 0) {
        return i;
      }
    }
    return -1;
  }

  // if defence lose, move specfic territory from defence player to attack player in actionsmap
  private void changeOwner(
      Territory defenceTerritory, Territory attackTerritory, HashMap<Integer, Integer> soldierMap) {
    ArrayList<Integer> ID = new ArrayList<>();
    ArrayList<String> playerName = new ArrayList<>();
    playerName.add(defenceTerritory.getOwner());
    playerName.add(attackTerritory.getOwner());
    for (int i = 0; i < 2; i++) {
      String tempName = playerName.get(i);
      int temp = Character.getNumericValue(tempName.charAt(tempName.length() - 1));
      ID.add(temp);
    }
    System.out.println("[DEBUG] defence platerID is " + ID.get(0));
    System.out.println("[DEBUG] attack platerID is " + ID.get(1));
    Territory tempTerritory = new Territory();
    tempTerritory = defenceTerritory;

    ArrayList<Territory> defenceTerritories = myworld.get(ID.get(0));
    System.out.println("[DEBUG] before change owner, defence player has "
        + defenceTerritories.size() + " territories");

    defenceTerritories.remove(defenceTerritory);
    System.out.println("[DEBUG] after change owner, defence player has " + defenceTerritories.size()
        + " territories");

    ArrayList<Territory> attackTerritories = myworld.get(ID.get(1));
    System.out.println("[DEBUG] before change owner, attack player has " + attackTerritories.size()
        + " territories");
    tempTerritory.setOwner(attackTerritory.getOwner());
    tempTerritory.setSoldiers(soldierMap);
    attackTerritories.add(tempTerritory);
    System.out.println("[DEBUG] after change owner, attack player has " + attackTerritories.size()
        + " territories");
  }

  // add one soldier to all territory
  public void doPlusOne() {
    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : myworld.entrySet()) {
      ArrayList<Territory> territoryList = entry.getValue();
      for (int j = 0; j < territoryList.size(); j++) {
        Territory myterritory = territoryList.get(j);
        myterritory.setSoldierLevel(0, myterritory.getSoldierLevel(0) + 1);
      }
    }
  }

  // find specific territory in worldmap
  public Territory findTerritory(
      HashMap<Integer, ArrayList<Territory>> myworld, String TerritoryName) {
    Territory ans = new Territory();
    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : myworld.entrySet()) {
      ArrayList<Territory> territoryList = entry.getValue();
      for (int j = 0; j < territoryList.size(); j++) {
        Territory myterritory = territoryList.get(j);
        if (myterritory.getTerritoryName().equals(TerritoryName)) {
          // System.out.println("[DEBUG] find ans");
          ans = myterritory;
          return ans;
        }
      }
    }
    return ans;
  }

  //return worldMap with territory details changed
  public HashMap<Integer, ArrayList<Territory>> getNewWorld() {
    return this.myworld;
  }

  //return resourceMap that contains all players food resource which has been added with the newly produced food
  public HashMap<Integer, Integer> getNewResource() {
    TerritoryProduce tp = new TerritoryProduce();
    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : myworld.entrySet()) {
      for (Territory cur : entry.getValue()) {
        int newFood = tp.getFood(cur.getTerritoryName());
        int playerid = entry.getKey();
        myResource.put(playerid, myResource.get(playerid) + newFood);
      }
    }
    return this.myResource;
  }
}
