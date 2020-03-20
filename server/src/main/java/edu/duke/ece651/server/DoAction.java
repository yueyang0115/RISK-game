package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import java.util.*;

public class DoAction {
  private HashMap<Integer, ArrayList<Territory>> myworld;
  public DoAction(HashMap<Integer, ArrayList<Territory>> world) {
    myworld = new HashMap<>();
    myworld = world;
  }

  public void doMoveAction(ArrayList<Action> moveList) {
    for (int i = 0; i < moveList.size(); i++) {
      Action action = moveList.get(i);
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
  }

  public void doAttackAction(ArrayList<Action> attackList) {
    for (int k = 0; k < attackList.size(); k++) {
      Action action = attackList.get(k);
      int numAttack = action.getSoliders();
      Territory attackTerritory = findTerritory(myworld, action.getSrc().getTerritoryName());
      attackTerritory.setSoldiers(attackTerritory.getSoliders() - numAttack);
    }

    for (int i = 0; i < attackList.size(); i++) {
      Action action = attackList.get(i);
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
  }

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
        + defenceTerritories.size() + " actions");
    /*
    for (int j = 0; j < defenceTerritories.size(); j++) {
      if (defenceTerritories.get(j) == defenceTerritory) {
        defenceTerritories.remove(defenceTerritory);
        System.out.println("[DEBUG] find territory to change owner and erase");
        break;
      }
      }*/
    defenceTerritories.remove(defenceTerritory);
    System.out.println(
        "[DEBUG] after change owner, defence player has " + defenceTerritories.size() + " actions");

    ArrayList<Territory> attackTerritories = myworld.get(ID.get(1));
    System.out.println(
        "[DEBUG] before change owner, attack player has " + attackTerritories.size() + " actions");
    tempTerritory.setOwner(attackTerritory.getOwner());
    tempTerritory.setSoldiers(numAttack);
    attackTerritories.add(tempTerritory);
    System.out.println(
        "[DEBUG] after change owner, attack player has " + attackTerritories.size() + " actions");
  }

  public void doPlusOne() {
    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : myworld.entrySet()) {
      ArrayList<Territory> territoryList = entry.getValue();
      for (int j = 0; j < territoryList.size(); j++) {
        Territory myterritory = territoryList.get(j);
        myterritory.setSoldiers(myterritory.getSoliders() + 1);
      }
    }
  }

  private Territory findTerritory(
      HashMap<Integer, ArrayList<Territory>> myworld, String TerritoryName) {
    Territory ans = new Territory();
    for (HashMap.Entry<Integer, ArrayList<Territory>> entry : myworld.entrySet()) {
      ArrayList<Territory> territoryList = entry.getValue();
      for (int j = 0; j < territoryList.size(); j++) {
        Territory myterritory = territoryList.get(j);
        if (myterritory.getTerritoryName() == TerritoryName) {
          System.out.println("[DEBUG] find ans");
          ans = myterritory;
        }
      }
    }
    return ans;
  }
}
