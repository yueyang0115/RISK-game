package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;
import java.io.*;
import java.util.*;

public class ResourceChecker {
  private HashMap<Integer, Integer> myresource;
  private HashMap<Integer, ArrayList<Territory>> myworld;
  private DoAction myDoAction;

  public ResourceChecker(
      HashMap<Integer, Integer> resource, HashMap<Integer, ArrayList<Territory>> world) {
    myresource = new HashMap<>();
    myresource = resource;
    myworld = new HashMap<>();
    myworld = world;
    myDoAction = new DoAction(myworld);
  }

  public boolean CheckResource(Action action) {
    String playerName = action.getOwner();
    int playerID = Character.getNumericValue(playerName.charAt(playerName.length() - 1));
    int srcResource = myresource.get(playerID);
    int cost = countResource(action);
    return cost <= srcResource;
  }

  private int countResource(Action action) {
    int numSoldiers = myDoAction.countSoldier(action.getSoldiers());
    if (action.getType().equals("Attack")) {
      return numSoldiers;
    } else {
      return countMove(action) * numSoldiers;
    }
  }

  public void reduceCost(HashMap<Integer, Integer> resource, Action action) {
    String playerName = action.getOwner();
    int playerID = Character.getNumericValue(playerName.charAt(playerName.length() - 1));
    int srcResource = myresource.get(playerID);
    int cost = countResource(action);
    resource.put(playerID, srcResource - cost);
  }

  // use Dijkstra’s Algorithm to find the path with samllest total size and count needed resources
  public int countMove(Action action) {
    TerritorySize sizegetter = new TerritorySize();
    PriorityQueue<Territory> pq = new PriorityQueue<>(); // pq.size()?
    HashSet<String> settled = new HashSet<String>();
    String srcName = action.getSrc().getTerritoryName();
    String dstName = action.getDst().getTerritoryName();
    Territory srcTerritory = myDoAction.findTerritory(myworld, srcName);

    srcTerritory.setTotalSize(sizegetter.getTerritorySize(srcName));
    pq.add(srcTerritory);
    System.out.println("[DEBUG] add srcTerritory in pq");
    printPriorityQueue(pq);
    settled.add(srcName);

    while (pq.size() != 0) {
      Territory currTerritory = pq.poll();
      String currName = currTerritory.getTerritoryName();
      int currSize = currTerritory.getTotalSize();
      System.out.println(
          "[DEBUG] Take " + currName + " out of queue, its total Size of path is: " + currSize);
      printPriorityQueue(pq);
      settled.add(currName);

      if (currName.equals(dstName)) {
        System.out.println("[DEBUG] Find dst, total Size of path is: " + currSize);
        return currSize;
      }

      ArrayList<String> neighborList = currTerritory.getNeighbor();
      for (int i = 0; i < neighborList.size(); i++) {
        String neighborName = neighborList.get(i);
        Territory neighborTerritory = myDoAction.findTerritory(myworld, neighborName);
        if (neighborTerritory.getOwner().equals(srcTerritory.getOwner())) {
          System.out.println("[DEBUG] find " + currName + "'s neighbor " + neighborName);
          if (!settled.contains(neighborName)) {
            int edgeSize = sizegetter.getTerritorySize(neighborName);
            int newTotalSize = edgeSize + currSize;
            if ((newTotalSize < neighborTerritory.getTotalSize())) {
              System.out.println("[DEBUG] " + neighborName + "'s totalsize can be updated");
              neighborTerritory.setTotalSize(newTotalSize);
            }

            if (containsTerrotory(pq, neighborTerritory)) {
              System.out.println("[DEBUG] pq already contains " + neighborName + ", move it out");
              pq.remove(neighborTerritory);
              printPriorityQueue(pq);
            }
            pq.add(neighborTerritory);
            System.out.println("[DEBUG] add " + neighborName + " in pq");
            printPriorityQueue(pq);
          }
        }
      }
    }

    return Integer.MAX_VALUE;
  }

  private void printPriorityQueue(PriorityQueue<Territory> pq) {
    System.out.print("[DEBUG] pq contains: ");
    for (Territory t : pq) {
      System.out.print(t.getTerritoryName() + "(" + t.getTotalSize() + "), ");
    }

    System.out.print("\n");
  }

  private boolean containsTerrotory(PriorityQueue<Territory> pq, Territory territory) {
    for (Territory t : pq) {
      if (t.getTerritoryName().equals(territory.getTerritoryName())) {
        return true;
      }
    }
    return false;
  }
}