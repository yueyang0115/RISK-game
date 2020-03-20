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
      //receive actions twice, add to ActionHelper
      ArrayList<Action> moveList = new ArrayList<>();
      ArrayList<Action> attackList = new ArrayList<>();
      MyFormatter myformatter = new MyFormatter(playerNum);
      String str = communicator.receive();
      myformatter.ActionParse(moveList, str);
      str = communicator.receive();
      myformatter.ActionParse(attackList, str);
      actionHelper.addActions(id, moveList, attackList);
      //receive action string and send to playe
      //send map to player

    }

    public HashMap<Integer, ArrayList<Territory>> getMap() {
      return territoryMap;
    }


    // public void run() {
    //   initGame();
    //   // communicator.close();
    // }
}
