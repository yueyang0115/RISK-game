package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import java.io.IOException;
import java.util.*;

public class ActionHelper {
  private HashMap<Integer, ArrayList<Action>> playersActions;
  private HashMap<Integer, ArrayList<Territory>> worldMap;
  private ArrayList<Action> moveList;
  private ArrayList<Action> attackList;
  private ArrayList<Upgrade> upgradeList;
  // ArrayList to record if the players have committed the actions
  private ArrayList<Boolean> playerComplete;
  private String actionsStr;
  private int playerNum;

  public ActionHelper(int number, HashMap<Integer, ArrayList<Territory>> m) {
    reset(number, m);
  }
  public void reset() {
    reset(playerNum, worldMap);
  }
  public void reset(int number, HashMap<Integer, ArrayList<Territory>> m) {
    this.playersActions = new HashMap<>();
    this.worldMap = m;
    this.moveList = new ArrayList<>();
    this.attackList = new ArrayList<>();
    this.upgradeList = new ArrayList<>();
    this.playerNum = number;
    this.playerComplete = new ArrayList<>(playerNum);
    for (int i = 0; i < number; ++i) {
      playerComplete.add(false);
    }
  }
  public boolean checkActionValid(int id) {
    return playersActions.containsKey(id);
  }
  public synchronized void addActions(
      int playerId, ArrayList<Action> ml, ArrayList<Action> al, ArrayList<Upgrade> ul) {
    ArrayList<Action> curActions = new ArrayList<>();
    curActions.addAll(ml);
    curActions.addAll(al);
    playersActions.put(playerId, curActions);
    moveList.addAll(ml);
    attackList.addAll(al);
    upgradeList.addAll(ul);
  }
  public synchronized void actionsCompleted(int playerId) {
    playerComplete.set(playerId, true);
  }
  public synchronized void executeActions(HashMap<Integer, Integer> food) {
    // Reference to Piazza post.
    // Wait until all players have committed the actions to execute all the actions
    while (playerComplete.contains(false)) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        System.out.println("Error: wait failed in ActionHelper!");
      }
    }
    DoAction d = new DoAction(worldMap, playersActions, food);
    // First do the move actions, then attack actions, then add 1 to all territory.
    d.doMoveAction(moveList);
    d.doAttackAction(attackList);
    d.doPlusOne();
    worldMap = d.getNewWorld();
    MyFormatter formatter = new MyFormatter(playerNum);
    actionsStr = formatter.AllActionCompose(playersActions).toString();
  }
  public String getActionString() {
    return actionsStr;
  }
}
