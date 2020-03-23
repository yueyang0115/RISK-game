package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Scanner;

public class Server {
  private int port;
  private int[] playerNum;
  private ServerSocket serverSock;
  private ArrayList<PlayerHandler> list;
  private HashMap<Integer, ArrayList<Territory>> territoryMap;
  private ArrayList<String> status;

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
    this.status = new ArrayList<>();
  }

  public void initGame() {
    PlayerHandler first =
        new PlayerHandler(new Communicator(serverSock), 0, playerNum, territoryMap, status);
    first.start();
    list.add(first);
    try {
      first.join();
    } catch (Exception ex) {
      System.out.println("Exception:" + ex);
    }
    // territoryMap = first.getMap();
    for (int id = 1; id < playerNum[0]; id++) {
      PlayerHandler ph =
          new PlayerHandler(new Communicator(serverSock), id, playerNum, territoryMap, status);
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
  }

  public void startGame() {
    // TODO:new a playerstatus to playerHandler, no need to know size first, first thread updates
    // its value
    ActionHelper ah = new ActionHelper(playerNum[0], territoryMap);
    for (PlayerHandler cur : list) {
      cur.addActionHelper(ah);
    }
    StringBuilder winMsg = new StringBuilder("Game End! Winner is ");
    MyFormatter formatter = new MyFormatter(playerNum[0]);
    Boolean gameEnd = false;
    while (!gameEnd) {
      for (PlayerHandler cur : list) {
        cur.startPlay();
      }
      System.out.println("[DEBUG:Server] Before execute actions:"
          + new MaptoJson(territoryMap).getJSON().toString());
      ah.executeActions();
      System.out.println("[DEBUG:Server] Before execute actions:"
          + new MaptoJson(territoryMap).getJSON().toString());
      // Get action string, send to players later
      String actionstr = ah.getActionString();

      System.out.println("DEBUG: action string is , " + actionstr);
      // MaptoJson myMaptoJson = new MaptoJson(this.territoryMap);

      for (PlayerHandler cur : list) {
        if (cur.checkLose()) {
          cur.sendPlayer(cur.checkAction(), false);
          cur.sendPlayer("Lose Game", false);
        }
      }
      for (int i = 0; i < list.size(); ++i) {
        if (list.get(i).checkWin()) {
          winMsg.append(new ColorID().getPlayerColor(i));
          winMsg.append(" player.");
          gameEnd = true;
        }
      }
      for (int j = 0; j < list.size(); ++j) {
        PlayerHandler cur = list.get(j);
        if (gameEnd) {
          cur.sendPlayer(winMsg.toString(), false);
          // TODO: close the server
        } else {
          if (status.get(j).equals("INGAME")) {
            cur.sendPlayer(cur.checkAction(), false);
          }
          // Send actions of other players to every player
          cur.sendPlayer(actionstr, true);
          // Send map to player
          cur.sendPlayer(formatter.MapCompose(territoryMap).toString(), true);
        }
      }
      ah.reset();
    }
    // end while
  }

  public static void main(String[] args) {
    Server server = new Server(1234);
    System.out.println("========Now connect players!========");
    server.initGame();
    server.startGame();
  }
}
