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
        reset(playerNum, m);
    }
    public void reset(int number, HashMap<Integer, ArrayList<Territory>> m) {
        this.playersActions = new HashMap<>();
        this.worldMap = m;
        this.moveList = new ArrayList<>();
        this.attackList = new ArrayList<>();
        this.playerNum = number;
        this.playerComplete = new ArrayList<>(playerNum);
        for (int i = 0; i < playerNum; ++i) {
            playerComplete.add(false);
        }
    }
    public synchronized void addActions(int playerId, ArrayList<Action> ml, ArrayList<Action> al) {
        ArrayList<Action> curActions = new ArrayList<>();
        curActions.addAll(ml);
        curActions.addAll(al);
        playersActions.put(playerId, curActions);
        moveList.addAll(ml);
        attackList.addAll(al);
    }
    public synchronized void ordersCompleted(int playerId) {
        playerComplete.set(playerId, true);
    }
    public synchronized void executeActions() {
        while(playerComplete.contains(false)) {
            this.wait();
        }
        DoAction d = new DoAction(worldMap);
        d.doMoveAction(moveList);
        d.doAttackAction(attackList);
        d.doPlusOne();
        MyFormatter formatter = new MyFormatter(playerNum[0]); 
        actionsStr = formatter.AllActionCompose(playersActions).toString();
        reset(playerNum, worldMap);
        
    }
    public String getActionString() {
        return actionsStr;
    }
}
