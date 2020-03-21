package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;
import java.util.*;
public class PlayerHandler extends Thread {
  private HashMap<Integer, ArrayList<Territory>> territoryMap;
    private Communicator communicator;
    private int id;
    private int[] playerNum;
    private ActionHelper actionHelper;

    public PlayerHandler(Communicator c, int id, int[] p) {
      this.communicator = c;
      this.id = id;
      this.playerNum = p;
    }

    public void addActionHelper(ActionHelper ah) {
      this.actionHelper = ah;
    }

    public void run() {
      communicator.sendString(String.valueOf(id));
      if (id == 0) {
        playerNum[0] = Integer.parseInt(communicator.receive());
        System.out.println("[DEBUG]received playerNum" + playerNum[0]);
      }
      communicator.sendString(String.valueOf(playerNum[0]));
      WorldInitter myworldinitter = new WorldInitter(playerNum[0]);
      this.territoryMap = myworldinitter.getWorld();
      MaptoJson myMaptoJson = new MaptoJson(this.territoryMap);
      communicator.sendJSON(myMaptoJson.getJSON());
      System.out.println(myMaptoJson.getJSON().toString());
    }

    public void startPlay() {
      //TODO:big while loop: not loose/watch after lose      
      //receive actions twice, add to ActionHelper and execute
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
      //
      //Should not be here, should be in the main of server
      // actionHelper.executeActions();
      // //receive action string and send to playe
      // String actionstr = actionHelper.getActionString();
      // System.out.println("DEBUG: action string is , " + actionstr);
      // communicator.sendString(actionstr);
      // //send map to player
      // MaptoJson myMaptoJson = new MaptoJson(this.territoryMap);
      // communicator.sendJSON(myMaptoJson.getJSON());
      // if (!territoryMap.containsKey(id)) {
      //   communicator.sendString("Lose Game");
      //   String ifWatch = communicator.receive();
      // }

    }

    public String checkAction() {
      return actionHelper.checkActionValid(id) ? "valid" : "invalid";
    }

    public void checkLose() {
      if (!territoryMap.containsKey(id)) {
        communicator.sendString("Lose Game");
        String ifWatch = communicator.receive();
        //TODO:have some global variable, 0:ingame, 1:loseandnowatch, 2:loseandwatch
        //
      }
    }

    public void sendPlayer(String input) {
      //TODO:determine if need to send? change previous sendstring
      communicator.sendString(input);
    }

    public HashMap<Integer, ArrayList<Territory>> getMap() {
      return territoryMap;
    }


    // public void run() {
    //   initGame();
    //   // communicator.close();
    // }
}
