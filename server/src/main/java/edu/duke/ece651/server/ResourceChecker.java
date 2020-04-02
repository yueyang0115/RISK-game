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
    } else if (action.getType().equals("Move")) {
      return 0;
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
}
