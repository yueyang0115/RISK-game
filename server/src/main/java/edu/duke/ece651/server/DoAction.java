package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import java.util.*;

public class DoAction {
  private HashMap<Integer, ArrayList<Territory>> myworld;
  private HashMap<Integer, ArrayList<Action>> myActionMap;
  private HashSet<String> invalidPlayer;
  private MyFormatter myformatter;
  private String tempWorldStr;
  private ArrayList<Action> mymoveList;

  public DoAction(HashMap<Integer, ArrayList<Territory>> world,
      HashMap<Integer, ArrayList<Action>> actionsMap) {
    init();
    myworld = world;
    myActionMap = actionsMap;
    myformatter = new MyFormatter(myworld.size());
    tempWorldStr = myformatter.MapCompose(myworld).toString();
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
  }

  // remove player that contains invalid action from actionsmap
  private void removePlayer(Action action) {
    // System.out.println("[DEBUG] action inValid");
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

  // domove action
  public void doMoveAction(ArrayList<Action> moveList) {
    for (int i = 0; i < moveList.size(); i++) {
      // System.out.println("[DEBUG] i is " + i);
      System.out.print("[DEBUG] invalidSet contains: ");
      for (String str : invalidPlayer) {
        System.out.print(str + ",");
      }
      System.out.print("\n");
      Action action = moveList.get(i);

      // if player has previous invalid action, ignore all its actions
      if (invalidPlayer.contains(action.getOwner())) {
        continue;
      }

      // check if action is valid
      ServerChecker mychecker = new ServerChecker(myworld);
      boolean isValid = mychecker.Check(action);
      if (!isValid) {
        removePlayer(action);
        moveList.remove(action);
        invalidPlayer.add(action.getOwner());
        myformatter.MapParse(myworld, tempWorldStr); // reset world
        i = -1;
        continue;
      }

      int numReduce = action.getSoliders();
      Territory srcTerritory = findTerritory(myworld, action.getSrc().getTerritoryName());
      Territory dstTerritory = findTerritory(myworld, action.getDst().getTerritoryName());
      System.out.println("[DEBUG] " + srcTerritory.getTerritoryName() + " move " + numReduce
          + " to " + dstTerritory.getTerritoryName() + ", original_src  has "
          + srcTerritory.getSoliders() + ", original_ dst has " + dstTerritory.getSoliders());

      srcTerritory.setSoldiers(srcTerritory.getSoliders() - numReduce);
      System.out.println("[DEBUG] after move, srcTerritory's name is "
          + srcTerritory.getTerritoryName() + ", num of soldier is " + srcTerritory.getSoliders());

      dstTerritory.setSoldiers(dstTerritory.getSoliders() + numReduce);
      System.out.println("[DEBUG] after move, dstTerritory's name is "
          + dstTerritory.getTerritoryName() + ", num of soldier is " + dstTerritory.getSoliders());
    }
    // tempWorldStr = myformatter.MapCompose(myworld).toString();
    mymoveList = moveList;
  }

  public void doAttackAction(ArrayList<Action> attackList) {
    for (int k = 0; k < attackList.size(); k++) {
      Action action = attackList.get(k);

      // if player has previous invalid action, ignore all its actions
      if (invalidPlayer.contains(action.getOwner())) {
        continue;
      }

      // based on moveActions result, check if attackaction is valid
      ServerChecker mychecker = new ServerChecker(myworld);
      boolean isValid = mychecker.Check(action);
      if (!isValid) {
        removePlayer(action);
        attackList.remove(action);
        invalidPlayer.add(action.getOwner());
        myformatter.MapParse(myworld, tempWorldStr); // reset world
        k = -1;
        continue;
      }

      // if valid, remove soldiers from srcTerritory
      int numAttack = action.getSoliders();
      Territory attackTerritory = findTerritory(myworld, action.getSrc().getTerritoryName());
      attackTerritory.setSoldiers(attackTerritory.getSoliders() - numAttack);
    }

    // reset map back to no action performed status, do move actions first
    myformatter.MapParse(myworld, tempWorldStr);
    doMoveAction(mymoveList);

    // then do attack actions, ignore all invalid action

    // move soldiers out of srcTerritory
    for (int k = 0; k < attackList.size(); k++) {
      Action action = attackList.get(k);

      if (invalidPlayer.contains(action.getOwner())) {
        continue;
      }

      int numAttack = action.getSoliders();
      Territory attackTerritory = findTerritory(myworld, action.getSrc().getTerritoryName());
      attackTerritory.setSoldiers(attackTerritory.getSoliders() - numAttack);
    }

    // perform attack actions
    for (int i = 0; i < attackList.size(); i++) {
      Action action = attackList.get(i);

      if (invalidPlayer.contains(action.getOwner())) {
        continue;
      }

      int numAttack = action.getSoliders();
      Territory attackTerritory = findTerritory(myworld, action.getSrc().getTerritoryName());
      Territory defenceTerritory = findTerritory(myworld, action.getDst().getTerritoryName());
      int numDefence = defenceTerritory.getSoliders();
      System.out.println("[DEBUG] " + attackTerritory.getTerritoryName() + " attack "
          + defenceTerritory.getTerritoryName() + ", attack has " + numAttack + ", defence has "
          + numDefence);

      Random rand = new Random();
      while (numAttack != 0 && numDefence != 0) {
        int randAttack = rand.nextInt(20) + 0;
        int randDefence = rand.nextInt(20) + 0;
        if (randAttack < randDefence) {
          System.out.println("[DEBUG] randDefence is bigger");
          numAttack -= 1;
        } else if (randDefence < randAttack) {
          System.out.println("[DEBUG] randAttack is bigger");
          numDefence -= 1;
        }
      }

      if (numDefence == 0) {
        System.out.println("[DEBUG] attack success");
        changeOwner(defenceTerritory, attackTerritory, numAttack);
      } else { // numAttack = 0
        System.out.println("[DEBUG] defence success");
        defenceTerritory.setSoldiers(numDefence);
      }

      System.out.println("[DEBUG] after attack, attackTerritory's name is "
          + attackTerritory.getTerritoryName() + ", owner is " + attackTerritory.getOwner()
          + ", num of soldier is " + attackTerritory.getSoliders());
      System.out.println("[DEBUG] after attack, defenceTerritory's name is "
          + defenceTerritory.getTerritoryName() + ", owner is " + defenceTerritory.getOwner()
          + ", num of soldier is " + defenceTerritory.getSoliders());
      MaptoJson myMaptoJson = new MaptoJson(myworld);
      System.out.println("[DEBUG] after attack, new worldmap is " + myMaptoJson.getJSON());
    }

    invalidPlayer.clear();
  }





  // if defence lose, move specfic territory from defence player to attack player in actionsmap
  private void changeOwner(Territory defenceTerritory, Territory attackTerritory, int numAttack) {
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
    /*
    for (int j = 0; j < defenceTerritories.size(); j++) {
      if (defenceTerritories.get(j) == defenceTerritory) {
        defenceTerritories.remove(defenceTerritory);
        System.out.println("[DEBUG] find territory to change owner and erase");
        break;
      }
      }*/
    defenceTerritories.remove(defenceTerritory);
    System.out.println("[DEBUG] after change owner, defence player has " + defenceTerritories.size()
        + " territories");

    ArrayList<Territory> attackTerritories = myworld.get(ID.get(1));
    System.out.println("[DEBUG] before change owner, attack player has " + attackTerritories.size()
        + " territories");
    tempTerritory.setOwner(attackTerritory.getOwner());
    tempTerritory.setSoldiers(numAttack);
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
        myterritory.setSoldiers(myterritory.getSoliders() + 1);
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

  public HashMap<Integer, ArrayList<Territory>> getNewWorld() {
    return this.myworld;
  }
}
