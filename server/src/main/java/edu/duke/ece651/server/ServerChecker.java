package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import java.util.*;

public class ServerChecker {
  private HashMap<Integer, ArrayList<Territory>> world;
  private Action action;
  private DoAction myDoAction;

  public ServerChecker(HashMap<Integer, ArrayList<Territory>> myworld) {
    world = new HashMap<>();
    world = myworld;
    myDoAction = new DoAction(myworld);
  }

  public boolean Check(Action myaction) {
    action = myaction;
    System.out.println("[DEBUG] checkall: srcTerritory is " + action.getSrc().getTerritoryName()
        + ", dstTerritory is " + action.getDst().getTerritoryName());
    boolean temp = checkTerritory() && checkOwner() && checkNum() && checkNeighbor();
    if (temp == true) {
      System.out.println("[DEBUG] check all succeed");
    }
    return temp;
  }

  private boolean checkTerritory() {
    String srcName = action.getSrc().getTerritoryName();
    String dstName = action.getDst().getTerritoryName();
    String findSrcName = myDoAction.findTerritory(world, srcName).getTerritoryName();
    String findDstName = myDoAction.findTerritory(world, dstName).getTerritoryName();
    return srcName.equals(findSrcName) && dstName.equals(findDstName);
  }

  private boolean checkOwner() {
    System.out.println("[DEBUG] checkTerritory succeed");
    String srcOwner = action.getSrc().getOwner();
    String dstOwner = action.getDst().getOwner();
    String actionOwner = action.getOwner();
    if (action.getType().equals("Move")) {
      return srcOwner.equals(dstOwner) && actionOwner.equals(srcOwner);
    } else {
      return (!srcOwner.equals(dstOwner)) && actionOwner.equals(srcOwner);
    }
  }

  private boolean checkNum() {
    System.out.println("[DEBUG] checkOwner succeed");
    Territory srcTerritory = action.getSrc();
    return ((srcTerritory.getSoliders() >= action.getSoliders()) && (action.getSoliders() >= 0));
  }

  private boolean checkNeighbor() {
    System.out.println("[DEBUG] checkNum succeed");
    HashSet<Territory> visitedSet = new HashSet<>();
    Territory dstTerritory = action.getDst();
    Territory srcTerritory = action.getSrc();

    Stack<Territory> stack = new Stack<Territory>();
    stack.add(srcTerritory);
    visitedSet.add(srcTerritory);
    while (!stack.isEmpty()) {
      Territory curr = stack.pop();
      if (curr.getTerritoryName().equals(dstTerritory.getTerritoryName())) {
        System.out.println(
            "[DEBUG] checkNeighbor succeed, find dstTerritory " + curr.getTerritoryName());
        return true;
      }

      ArrayList<String> neighborList = curr.getNeighbor();
      for (int i = 0; i < neighborList.size(); i++) {
        String tempName = neighborList.get(i);
        Territory Neighbor = myDoAction.findTerritory(world, tempName);
        if (!visitedSet.contains(Neighbor)) {
          if (Neighbor.getTerritoryName().equals(dstTerritory.getTerritoryName())) {
            System.out.println(
                "[DEBUG] checkNeighbor succeed, find dstTerritory " + Neighbor.getTerritoryName());
            return true;
          }
          if (Neighbor.getOwner().equals(srcTerritory.getOwner())) {
            stack.push(Neighbor);
            System.out.println("[DEBUG] check " + curr.getTerritoryName()
                + "'s neighbor, put its neighbor " + Neighbor.getTerritoryName() + " in stack");
          }
          visitedSet.add(Neighbor);
        }
      }
      printStack(stack);
    }
    System.out.println("[DEBUG] not find dstTerritory");
    return false;
  }

  private void printStack(Stack<Territory> stack) {
    System.out.print("[DEBUG] Stack contains: ");
    for (Territory item : stack) {
      System.out.print(item.getTerritoryName() + ",");
    }
    System.out.print("\n");
  }
}
