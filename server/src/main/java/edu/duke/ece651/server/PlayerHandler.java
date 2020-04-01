package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;
import java.util.*;
public class PlayerHandler extends Thread {
  private HashMap<Integer, ArrayList<Territory>> territoryMap;
    private Communicator communicator;
    private int id;
    private int[] playerNum;
    private ActionHelper actionHelper;
    //ArrayList to record the status of every player" INGAME, OUTBUTWATCH, OUTNOWATCH
    private ArrayList<String> status;

    public PlayerHandler(Communicator c, int id, int[] p, HashMap<Integer, ArrayList<Territory>> t, ArrayList<String> s) {
      this.communicator = c;
      this.id = id;
      this.playerNum = p;
      this.territoryMap = t;
      this.status = s;
    }

    public void addActionHelper(ActionHelper ah) {
      this.actionHelper = ah;
    }

    public void run() {
      //System.out.println("In server, " + receiveString() + id);
      //Send player id to every player

      sendPlayer(String.valueOf(id), false);
      System.out.println("In server, Already send the id" + id);
      //If first player, no to receive the player number
      if (id == 0) {
        System.out.println(id + ": Waiting for total number");
        playerNum[0] = Integer.parseInt(this.communicator.receive());
        System.out.println("[DEBUG]received playerNum" + playerNum[0]);
        WorldInitter myworldinitter = new WorldInitter(playerNum[0], territoryMap);
        for (int i = 0; i < playerNum[0]; ++i) {
          status.add("INGAME");
        }
      }
      //Send all player number to every player
      sendPlayer(String.valueOf(playerNum[0]), false);
      //Send the map to player
      MaptoJson myMaptoJson = new MaptoJson(territoryMap);
      sendPlayer(myMaptoJson.getJSON().toString(), false);
      System.out.println("[DEBUG] initial map" + myMaptoJson.getJSON().toString());
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
