package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
  private int port;
  private int[] playerNum;
  private ServerSocket serverSock;
  private ArrayList<PlayerHandler> list;
  private HashMap<Integer, ArrayList<Territory>> territoryMap;
  private HashMap<Integer, Integer> food;
  private ArrayList<String> status;
  private ChatServer chatServer;

  public Server(int port) {
    this.port = port;
    this.playerNum = new int[] {0};
    try {
      this.serverSock = new ServerSocket(port);
    } catch (IOException e) {
      System.out.println("Failed to crete ServerSocket!");
    }
    this.list = new ArrayList<>();
    this.territoryMap = new HashMap<>();
    this.food = new HashMap<>();
    this.status = new ArrayList<>();
  }

  public void initGame() {
    // First thread to handle the first player, where it needs to input player number to setup
    PlayerHandler first =
        new PlayerHandler(new Communicator(serverSock), 0, playerNum, territoryMap, food, status);
    first.start();
    list.add(first);
    try {
      first.join();
    } catch (Exception ex) {
      System.out.println("Exception:" + ex);
    }
    //Initialize the chatServer, pass in player number
    this.chatServer = new ChatServer(playerNum[0]);
    // The other threads to handle the rest players
    for (int id = 1; id < playerNum[0]; id++) {
      PlayerHandler ph = new PlayerHandler(
          new Communicator(serverSock), id, playerNum, territoryMap, food, status);
      list.add(ph);
      ph.start();
    }
    for (int id = 1; id < playerNum[0]; id++) {
      PlayerHandler ph = list.get(id);
      try {
        ph.join();
      } catch (Exception ex) {
        System.out.println("Exception:" + ex);
      }
    }
    chatServer.setUp();
  }

  public void startGame() {
    ActionHelper actionh = new ActionHelper(playerNum[0], territoryMap);
    AllianceHelper allianceh = new AllianceHelper();
    for (PlayerHandler cur : list) {
      cur.addActionHelper(actionh);
      cur.addAllianceHelper(allianceh);
    }
    StringBuilder winMsg = new StringBuilder("Game End! Winner is ");
    MyFormatter formatter = new MyFormatter(playerNum[0]);
    Boolean gameEnd = false;
    while (!gameEnd) {
      for (PlayerHandler cur : list) {
        cur.startPlay();
      }
      System.out.println(
          "[DEBUG] Before execute actions:" + new MaptoJson(territoryMap).getJSON().toString());
      actionh.executeActions(food);
      allianceh.executeCurRound();
      System.out.println(
          "[DEBUG] After execute actions:" + new MaptoJson(territoryMap).getJSON().toString());
      // Get action string, send to players later
      String actionstr = actionh.getActionString();
      System.out.println("[DEBUG] action string is, " + actionstr);
      int justLose = -1;
      for (int i = 0; i < list.size(); ++i) {
        // Check all the players if it wein.
        // If so, update the "Game End" message.
        if (list.get(i).checkWin()) {
          System.out.println("[DEBUG] Player" + i + "Win Game!");
          winMsg.append(new ColorID().getPlayerColor(i));
          winMsg.append(" player.");
          gameEnd = true;
        }
      }

      for (int j = 0; j < list.size(); ++j) {
        // Check all the players if it loses.
        PlayerHandler cur = list.get(j);
        System.out.println("[DEBUG] Before Current Status " + j + status.get(j));
        if (status.get(j).equals("INGAME") && territoryMap.get(j).size() == 0 && !gameEnd) {
          cur.sendPlayer(cur.checkAction(), false);
          cur.sendPlayer(actionstr, false);
          cur.sendPlayer("Lose Game", false);
          System.out.println("[DEBUG] Player" + j + "Lose Game!");
          cur.updateLose();
          justLose = j;
        }
        System.out.println("After Current Status " + j + status.get(j));
      }
      System.out.println("[DEBUG] Finish CheckLose for All");
      for (int k = 0; k < list.size(); ++k) {
        // Send the action valiation, all actions and map to every player
        PlayerHandler cur = list.get(k);
        if (gameEnd) {
          cur.sendPlayer(cur.checkAction(), false);
          cur.sendPlayer(actionstr, false);
          cur.sendPlayer(winMsg.toString(), false);
        } else {
          if (status.get(k).equals("INGAME")) {
            System.out.println(
                "[DEBUG] Not lose, send Validation Result" + status.get(k).equals("INGAME"));
            //Send action validation result
            cur.sendPlayer(cur.checkAction(), false);
            //Send alliance checkResult
            String allianceRes = allianceh.getAllianceResult(k);
            cur.sendPlayer(allianceRes, false);
          }
          if (justLose != k) {
            // Send actions of other players to every player
            cur.sendPlayer(actionstr, true);
            System.out.println("[DEBUG] Success Send ActionStr to Player" + k);
            // Send map to player
            cur.sendPlayer(formatter.MapCompose(territoryMap).toString(), true);
            System.out.println("[DEBUG] Success Send Map to Player" + k
                + formatter.MapCompose(territoryMap).toString());
            // Send the food resource
            cur.sendPlayer(food.get(k).toString(), false);
            System.out.println("[DEBUG] send food resource to player_" + k + ": " + food.get(k));
          }
        }
      }
      actionh.reset();
      allianceh.resetCurRound();
    }
    // end while
  }

  public static void main(String[] args) {
    //================================
    // Set port number of server!!
    Server server = new Server(1234);
    //================================

    System.out.println("========Now connect players!========");
    server.initGame();
    server.startGame();
  }
}
