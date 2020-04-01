package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import org.apache.commons.math3.analysis.function.Cos;

import java.util.*;

public class ServerChecker {
  private HashMap<Integer, ArrayList<Territory>> world;
  private Action action;

  public ServerChecker(HashMap<Integer, ArrayList<Territory>> myworld) {
    world = new HashMap<>();
    world = myworld;
    action = new Action();
  }

  public boolean Check(Action myaction) {
    action = myaction;
    // System.out.println("[DEBUG] checkall: srcTerritory is " + action.getSrc().getTerritoryName()
    //    + ", dstTerritory is " + action.getDst().getTerritoryName());
    boolean temp = checkTerritory() && checkOwner() && checkNum() && checkNeighbor();
    if (temp == true) {
      System.out.println("[DEBUG] check all succeed");
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
    if (action.getType().equals("Move")) {
      return srcOwner.equals(dstOwner) && actionOwner.equals(srcOwner);
    } else {
      return (!srcOwner.equals(dstOwner)) && actionOwner.equals(srcOwner);
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
  private void Dijkstra() {
    boolean[] flag = new boolean[12];
    int[] Distance = new int[12];

    TerritorySize TerrSize = new TerritorySize();

    String Start = this.action.getSrc().getTerritoryName();

    int StartIndex = Integer.valueOf(Start) - Integer.valueOf("A");
    Distance[StartIndex] = 0;
    flag[StartIndex] = true;

    String End = this.action.getDst().getTerritoryName();
    DoAction FindTerr = new DoAction(world);
    Territory StartTerr = FindTerr.findTerritory(world,Start);
    Territory EndTerr = FindTerr.findTerritory(world, End);



  }
  // check if there is a valid path from srcTerritory to dstTerritory
  private boolean checkNeighbor() {
    // System.out.println("[DEBUG] checkNum succeed");
    HashSet<Territory> visitedSet = new HashSet<>();

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
          if (Neighbor.getOwner().equals(srcTerritory.getOwner())) {
            stack.push(Neighbor);
            // System.out.println("[DEBUG] check " + curr.getTerritoryName()
            //    + "'s neighbor, put its neighbor " + Neighbor.getTerritoryName() + " in stack");
          }
          visitedSet.add(Neighbor);
        }
      }
      printStack(stack);
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
