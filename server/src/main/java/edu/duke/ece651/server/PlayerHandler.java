package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;

import java.util.*;
public class PlayerHandler extends Thread {
    private HashMap<Integer, ArrayList<Territory>> territoryMap;
    private HashMap<Integer, Integer> food;
    private Communicator communicator;
    private int id;
    private int[] playerNum;
    private ActionHelper actionHelper;
    //ArrayList to record the status of every player" INGAME, OUTBUTWATCH, OUTNOWATCH
    private ArrayList<String> status;

    public PlayerHandler(Communicator c, int id, int[] p, HashMap<Integer,
            ArrayList<Territory>> t, HashMap<Integer, Integer> f, ArrayList<String> s) {
      this.communicator = c;
      this.id = id;
      this.playerNum = p;
      this.territoryMap = t;
      this.food = f;
      this.status = s;
    }

    public void addActionHelper(ActionHelper ah) {
      this.actionHelper = ah;
    }

    public void run() {
      //Send player id to every player

      sendPlayer(String.valueOf(id), false);
      System.out.println("Already send the id " + id);
      //If first player, no to receive the player number
      if (id == 0) {
        playerNum[0] = Integer.parseInt(communicator.receive());
        WorldInitter myworldinitter = new WorldInitter(playerNum[0], territoryMap);
        System.out.println("[DEBUG]received playerNum" + playerNum[0]);
        for (int i = 0; i < playerNum[0]; ++i) {
          status.add("INGAME");
          if (playerNum[0] == 2) { food.put(i, 300); }
          else if (playerNum[0] == 3) { food.put(i, 200); }
          else if (playerNum[0] == 4) { food.put(i, 150); }
          else if (playerNum[0] == 5) { food.put(i, 120); }
        }
      }
      //Send all player number to every player
      sendPlayer(String.valueOf(playerNum[0]), false);
      //Send the map to player
      MaptoJson myMaptoJson = new MaptoJson(territoryMap);
      sendPlayer(myMaptoJson.getJSON().toString(), false);
      System.out.println("[DEBUG] initial map" + myMaptoJson.getJSON().toString());
      //Send the food resource
      sendPlayer(food.get(id).toString(), false);
      System.out.println("[DEBUG] send food resource to player_" + id + ": " + food.get(id));
    }

    public void startPlay() {    
      //If not lose, receive actions twice, add to ActionHelper
      if (status.get(id).equals("INGAME")) {
        ArrayList<Upgrade> upgradeList = new ArrayList<>();
        ArrayList<Action> moveList = new ArrayList<>();
        ArrayList<Action> attackList = new ArrayList<>();
        MyFormatter myformatter = new MyFormatter(playerNum[0]);
        String str = communicator.receive();
        System.out.println("[DEBUG]: received upgradeList, " + str);
        myformatter.UpgradeParse(upgradeList, str);
        str = communicator.receive();
        System.out.println("[DEBUG]: received moveList, " + str);
        myformatter.ActionParse(moveList, str);
        str = communicator.receive();
        System.out.println("[DEBUG]: received attackList, " + str);
        myformatter.ActionParse(attackList, str);
        actionHelper.addActions(id, moveList, attackList, upgradeList);
        //Commit the actions of current player
        actionHelper.actionsCompleted(id);
      }
      else {
        actionHelper.actionsCompleted(id);
        System.out.println("[DEBUG] lose, but also commit actions as empty");
      }
    }

    public String checkAction() {
      return actionHelper.checkActionValid(id) ? "valid" : "invalid";
        //return "valid";
    }

    public void updateLose() {
      String ifWatch = communicator.receive();
      if (ifWatch.equals("Y")) {
        status.set(id, "OUTBUTWATCH");
      } 
      else {
        status.set(id, "OUTNOWATCH");
      }   
    }

    public Boolean checkWin() {
      ArrayList<Integer> inGameList = new ArrayList<>();
      for (int i = 0; i < status.size(); ++i) {
        //Check every player if it wins
        if (territoryMap.get(i).size() != 0) {
          inGameList.add(i);
        }
      }
      if (inGameList.size() == 1 && inGameList.get(0) == id) {
        return true;
      }
      return false;
    }

    public void sendPlayer(String input, Boolean needCheck) {
      if (needCheck) {
        String s = status.get(id);
        if (s.equals("INGAME") || s.equals("OUTBUTWATCH")) {
          communicator.sendString(input);
        }  
      }
      else {
        communicator.sendString(input);
      }
        
    }
      
}
