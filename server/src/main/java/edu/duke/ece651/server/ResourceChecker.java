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
    if (action.getType().equals("Attack")) {
      int numSoldiers = myDoAction.countSoldier(action.getSoldiers());
      return numSoldiers;
    } else {
      return 0;
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
    String srcName = action.getSrc().getTerritoryName();
    String dstName = action.getDst().getTerritoryName();
    TerritorySize sizegetter = new TerritorySize();
    PriorityQueue<Territory> pq = new PriorityQueue<>();
    pq.add(action.getSrc());
    HashSet<String> settled = new HashSet<String>();
    settled.add(srcName);
    HashMap<String, Integer> totalSize = new HashMap<>();
    totalSize.put(srcName, sizegetter.getTerritorySize(srcName));

    while (pq.size() != 0) {
      Territory curr = pq.poll();
      String currName = curr.getTerritoryName();
      int currSize = sizegetter.getTerritorySize(currName);
      settled.add(currName);
      if (currName.equals(dstName)) {
        return totalSize.get(currName);
      }

      ArrayList<String> neighborList = curr.getNeighbor();
      for (int i = 0; i < neighborList.size(); i++) {
        String neighborName = neighborList.get(i);
        Territory Neighbor = myDoAction.findTerritory(myworld, neighborName);

        if (!settled.contains(neighborName)) {
          int edgeSize = totalSize.get(neighborName);
          if ((edgeSize + currSize < sizegetter.getTerritorySize(neighborName))) {
            totalSize.put(neighborName, edgeSize + currSize);
          }
          pq.add(Neighbor);
        }
      }
    }

    return 10000;
  }
}
