package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import java.util.*;

public class ServerChecker {
  private HashMap<Integer, ArrayList<Territory>> world;
  private Action action;

  public ServerChecker(HashMap<Integer, ArrayList<Territory>> myworld) {
    world = new HashMap<>();
    world = myworld;
  }

  public boolean Check(Action myaction) {
    action = myaction;
    return checkNeighbor();
  }

  public boolean checkNeighbor() {
    HashSet<Territory> visitedSet = new HashSet<>();
    Territory dstTerritory = action.getDst();
    Territory srcTerritory = action.getSrc();
    System.out.println("[DEBUG] checkNeighbor: srcTerritory is " + srcTerritory.getTerritoryName()
        + ", dstTerritory is " + dstTerritory.getTerritoryName());
    DoAction myDoAction = new DoAction(this.world);

    Stack<Territory> stack = new Stack<Territory>();
    stack.add(srcTerritory);
    visitedSet.add(srcTerritory);
    while (!stack.isEmpty()) {
      Territory curr = stack.pop();
      if (curr.getTerritoryName().equals(dstTerritory.getTerritoryName())) {
        System.out.println("[DEBUG] find dstTerritory " + curr.getTerritoryName());
        return true;
      }

      ArrayList<String> neighborList = curr.getNeighbor();
      for (int i = 0; i < neighborList.size(); i++) {
        String tempName = neighborList.get(i);
        Territory Neighbor = myDoAction.findTerritory(world, tempName);
        if (!visitedSet.contains(Neighbor)) {
          if (Neighbor.getTerritoryName().equals(dstTerritory.getTerritoryName())) {
            System.out.println("[DEBUG] find dstTerritory " + Neighbor.getTerritoryName());
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
