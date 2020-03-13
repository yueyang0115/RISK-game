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
        defenceTerritory.setOwner(attackTerritory.getOwner());
        defenceTerritory.setSoldiers(numAttack);
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
    }
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
