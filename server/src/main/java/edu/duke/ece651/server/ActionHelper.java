package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import java.util.*;

public class ActionHelper {
    private HashMap<Integer, ArrayList<Action>> playersActions;
    private HashMap<Integer, ArrayList<Territory>> worldMap;
    private ArrayList<Action> moveList;
    private ArrayList<Action> attackList;
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
        this.playerNum = number;
        this.playerComplete = new ArrayList<>(playerNum);
        for (int i = 0; i < number; ++i) {
            playerComplete.add(false);
        }
    }
    public boolean checkActionValid(int id) {
        return playersActions.containsKey(id);
    }
    public synchronized void addActions(int playerId, ArrayList<Action> ml, ArrayList<Action> al) {
        ArrayList<Action> curActions = new ArrayList<>();
        curActions.addAll(ml);
        curActions.addAll(al);
        playersActions.put(playerId, curActions);
        moveList.addAll(ml);
        attackList.addAll(al);
    }
    public synchronized void actionsCompleted(int playerId) {
        playerComplete.set(playerId, true);
    }
    public synchronized void executeActions() {
        // System.out.println("[DEBUG:ActionHelper] Before execute actions:" + new MaptoJson(worldMap).getJSON().toString());
        while(playerComplete.contains(false)) {
            try {
                this.wait();
            }
            catch (InterruptedException e) {
                System.out.println("Error: wait failed in ActionHelper!");
            }
            
        }
        DoAction d = new DoAction(worldMap, playersActions); //TODO: pass playersActions to contructor
        d.doMoveAction(moveList);
        d.doAttackAction(attackList);
        d.doPlusOne();
        worldMap = d.getNewWorld();
        // System.out.println("[DEBUG:ActionHelper] After execute actions:" + (new MaptoJson(worldMap)).getJSON().toString());
        MyFormatter formatter = new MyFormatter(playerNum); 
        actionsStr = formatter.AllActionCompose(playersActions).toString();      
    }
    public String getActionString() {
        return actionsStr;
    }
}
