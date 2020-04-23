package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import java.util.*;

public class ServerChecker {
  private HashMap<Integer, ArrayList<Territory>> world;
  private Action action;
  private AllianceHelper allianceHelper;

  public ServerChecker(HashMap<Integer, ArrayList<Territory>> myworld, AllianceHelper myah) {
    world = new HashMap<>();
    world = myworld;
    action = new Action();
    allianceHelper = new AllianceHelper();
    allianceHelper = myah;
  }

  public boolean Check(Action myaction) {
    action = myaction;
    // System.out.println("[DEBUG] checkall: srcTerritory is " + action.getSrc().getTerritoryName()
    //    + ", dstTerritory is " + action.getDst().getTerritoryName());
    boolean temp = checkTerritory() && checkOwner() && checkNum() && checkNeighbor();
    if (temp == true) {
      System.out.println("[DEBUG] check servercheck succeed");
    }
    return temp;
  }

  // check if action's srcTerrotory and dstTerritory are in terrirory Map
  private boolean checkTerritory() {
    String srcName = action.getSrc().getTerritoryName();
    String dstName = action.getDst().getTerritoryName();
    DoAction myDoAction = new DoAction(world);
    String findSrcName = myDoAction.findTerritory(world, srcName).getTerritoryName();
    String findDstName = myDoAction.findTerritory(world, dstName).getTerritoryName();
    return srcName.equals(findSrcName) && dstName.equals(findDstName);
  }

  // check if action's owner and scrTerritory's and dstTerritory is legal
  private boolean checkOwner() {
    // System.out.println("[DEBUG] checkTerritory succeed");
    String srcOwner = action.getSrc().getOwner();
    String dstOwner = action.getDst().getOwner();
    String actionOwner = action.getOwner();
    int srcID = Character.getNumericValue(srcOwner.charAt(srcOwner.length() - 1));
    int dstID = Character.getNumericValue(dstOwner.charAt(dstOwner.length() - 1));
    boolean isalliance = allianceHelper.playerisAllianced(srcID,dstID);

    if (action.getType().equals("Move")) {
      //dst can be alliance
      //System.out.println("[DEBUG] move action's src and dst isalliance  = "+ isalliance);
      return (srcOwner.equals(dstOwner) || isalliance) && actionOwner.equals(srcOwner);
    } else {
      //src and dst cannot be alliance, not check here, check and break in doaction
      return (!srcOwner.equals(dstOwner)) && (!isalliance) && actionOwner.equals(srcOwner);
    }
  }

  // check if srcTerritory can provide action enough soldier
  private boolean checkNum() {
    // System.out.println("[DEBUG] checkOwner succeed");
    // Territory srcTerritory = action.getSrc();
    DoAction myDoAction = new DoAction(world);
    Territory srcTerritory = myDoAction.findTerritory(world, action.getSrc().getTerritoryName());
    // System.out.println("[DEBUG] srcTerritory.getSoldiers = " + srcTerritory.getSoliders());
    HashMap<Integer, Integer> srcSoldiers = srcTerritory.getSoldiers();
    HashMap<Integer, Integer> actionSoldiers = action.getSoldiers();
    for (HashMap.Entry<Integer, Integer> entry : srcSoldiers.entrySet()) {
      int soldierLevel = entry.getKey();
      int numSrc = entry.getValue();
      int numCheck = actionSoldiers.get(soldierLevel);
      if (numSrc < numCheck || numCheck < 0) {
        return false;
      }
    }
    return true;
  }

  // check if there is a valid path from srcTerritory to dstTerritory
  private boolean checkNeighbor() {
    // System.out.println("[DEBUG] checkNum succeed");
    HashSet<Territory> visitedSet = new HashSet<>();
    // Territory dstTerritory = action.getDst();
    // Territory srcTerritory = action.getSrc();
    DoAction myDoAction2 = new DoAction(world);
    Territory dstTerritory = myDoAction2.findTerritory(world, action.getDst().getTerritoryName());
    Territory srcTerritory = myDoAction2.findTerritory(world, action.getSrc().getTerritoryName());

    DoAction myDoAction = new DoAction(world);

    Stack<Territory> stack = new Stack<Territory>();
    stack.add(srcTerritory);
    visitedSet.add(srcTerritory);
    while (!stack.isEmpty()) {
      Territory curr = stack.pop();
      if (curr.getTerritoryName().equals(dstTerritory.getTerritoryName())) {
        // System.out.println(
        //    "[DEBUG] checkNeighbor succeed, find dstTerritory " + curr.getTerritoryName());
        return true;
      }

      ArrayList<String> neighborList = curr.getNeighbor();
      for (int i = 0; i < neighborList.size(); i++) {
        String tempName = neighborList.get(i);
        Territory Neighbor = myDoAction.findTerritory(world, tempName);
        if (!visitedSet.contains(Neighbor)) {
          if (Neighbor.getTerritoryName().equals(dstTerritory.getTerritoryName())) {
            // System.out.println(
            //    "[DEBUG] checkNeighbor succeed, find dstTerritory " +
            //    Neighbor.getTerritoryName());
            return true;
          }

          //path neighbor can be alliance
          String srcOwner = srcTerritory.getOwner();
          int srcID = Character.getNumericValue(srcOwner.charAt(srcOwner.length() - 1));
          String neighborOwner = Neighbor.getOwner();
          int neighborID = Character.getNumericValue(neighborOwner.charAt(neighborOwner.length() - 1));
          boolean isAllianced = allianceHelper.playerisAllianced(srcID, neighborID);
          //System.out.println("[DEBUG] Neighbor owner player_"+ neighborID+" and action owner player_"+srcID+" is allianed: "+isAllianced);
          if (Neighbor.getOwner().equals(srcOwner) || isAllianced) {
            stack.push(Neighbor);
            // System.out.println("[DEBUG] check " + curr.getTerritoryName()
            //    + "'s neighbor, put its neighbor " + Neighbor.getTerritoryName() + " in stack");
          }
          visitedSet.add(Neighbor);
        }
      }
      //printStack(stack);
    }
    // System.out.println("[DEBUG] not find dstTerritory");
    return false;
  }

  private void printStack(Stack<Territory> stack) {
    // System.out.print("[DEBUG] Stack contains: ");
    for (Territory item : stack) {
      System.out.print(item.getTerritoryName() + ",");
    }
    System.out.print("\n");
  }
}
