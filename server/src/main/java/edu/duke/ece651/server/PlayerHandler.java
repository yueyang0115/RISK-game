package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;
import java.util.*;
public class PlayerHandler extends Thread {
  private HashMap<Integer, ArrayList<Territory>> territoryMap;
    private Communicator communicator;
    private int id;
    private int[] playerNum;
    private ActionHelper actionHelper;
    private ArrayList<PlayerStatus> status;

    public PlayerHandler(Communicator c, int id, int[] p, ArrayList<PlayerStatus> s) {
      this.communicator = c;
      this.id = id;
      this.playerNum = p;
      this.status = s;
    }

    public void addActionHelper(ActionHelper ah) {
      this.actionHelper = ah;
    }

    public void run() {
      sendPlayer(String.valueOf(id), false);
      if (id == 0) {
        playerNum[0] = Integer.parseInt(communicator.receive());
        System.out.println("[DEBUG]received playerNum" + playerNum[0]);
        for (int i = 0; i < playerNum[0]; ++i) {
          status.add(PlayerStatus.INGAME);
        }
      }
      sendPlayer(String.valueOf(playerNum[0]), false);
      WorldInitter myworldinitter = new WorldInitter(playerNum[0]);
      this.territoryMap = myworldinitter.getWorld();
      MaptoJson myMaptoJson = new MaptoJson(this.territoryMap);
      sendPlayer(myMaptoJson.getJSON().toString(), false);
      System.out.println(myMaptoJson.getJSON().toString());
    }

    public void startPlay() {    
      //If not lose, receive actions twice, add to ActionHelper
      if (status.get(id) == PlayerStatus.INGAME) {
        ArrayList<Action> moveList = new ArrayList<>();
        ArrayList<Action> attackList = new ArrayList<>();
        MyFormatter myformatter = new MyFormatter(playerNum[0]);
        String str = communicator.receive();
        System.out.println("DEBUG: received moveList, " + str);
        myformatter.ActionParse(moveList, str);
        str = communicator.receive();
        System.out.println("DEBUG: received attackList, " + str);
        myformatter.ActionParse(attackList, str);
        actionHelper.addActions(id, moveList, attackList);
      }
    }

    public String checkAction() {
      return actionHelper.checkActionValid(id) ? "valid" : "invalid";
    }

    public void checkLose() {
      if (!territoryMap.containsKey(id)) {
        sendPlayer("Lose Game", false);
        String ifWatch = communicator.receive();
        if (ifWatch.equals("Y")) {
          status.set(id, PlayerStatus.OUTBUTWATCH);
        } 
        else {
          status.set(id, PlayerStatus.OUTNOWATCH);
        }
      }
    }

    public Boolean checkWin() {
      ArrayList<Integer> inGameList = new ArrayList<>();
      for (int i = 0; i < status.size(); ++i) {
        if (status.get(i) == PlayerStatus.INGAME) {
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
        PlayerStatus s = status.get(id);
        if (s == PlayerStatus.INGAME || s == PlayerStatus.OUTBUTWATCH) {
          communicator.sendString(input);
        }  
      }
      else {
        communicator.sendString(input);
      }
        
    }

    public HashMap<Integer, ArrayList<Territory>> getMap() {
      return territoryMap;
    }
      
}
