package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;
import java.util.*;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Server {
  
  // private ArrayList<Action> allMoveActions;
  // private ArrayList<Action> allAttackActions;

  private int port;
  private int[] playerNum;
  private ServerSocket serverSock;
  private ArrayList<PlayerHandler> list;
  private HashMap<Integer, ArrayList<Territory>> territoryMap;

  public Server(int port) {
    // this.allMoveActions = new ArrayList<>();
    // this.allAttackActions = new ArrayList<>();
    this.port = port;
    this.playerNum = new int[]{0};
    try {
      this.serverSock = new ServerSocket(port);
    } catch (IOException e) {
      System.out.println("Failed to crete ServerSocket!");
    }
    this.list = new ArrayList<>();
    this.territoryMap = new HashMap<>();  
  }


  public void initGame() {
    PlayerHandler first = new PlayerHandler(new Communicator(serverSock), 0, playerNum);
    first.start();
    list.add(first);
    try {
      first.join();
    } 
    catch(Exception ex) { 
      System.out.println("Exception:" + ex); 
    } 
    territoryMap = first.getMap();
    for (int id = 1; id < playerNum[0]; id++) {
      PlayerHandler ph = new PlayerHandler(new Communicator(serverSock), id, playerNum); 
      list.add(ph);
      ph.start();
    }
    for (int id = 1; id < playerNum[0]; id++) { 
      PlayerHandler ph = list.get(id);  
      try {
        ph.join();
      }
      catch(Exception ex) { 
        System.out.println("Exception:" + ex); 
      }     
    }
  }

  public void startGame() {
    //TODO:new a playerstatus to playerHandler, no need to know size first, first thread updates its value
    ActionHelper ah = new ActionHelper(playerNum[0], territoryMap);
    for (PlayerHandler cur : list) {
      cur.addActionHelper(ah);
    }
    //TODO:while game not end, at leat one player "in game"
    for (PlayerHandler cur : list) {
      cur.startPlay();
    }
    // for (PlayerHandler cur : list) {
    //   try {
    //     cur.join();
    //   }
    //   catch(Exception ex) { 
    //     System.out.println("Exception:" + ex); 
    //   }    
    // }
    ah.executeActions();
    //receive action string and send to player
    String actionstr = ah.getActionString();
    System.out.println("DEBUG: action string is , " + actionstr);
    MaptoJson myMaptoJson = new MaptoJson(this.territoryMap);
    for (PlayerHandler cur : list) {
      cur.sendPlayer(cur.checkAction());
      cur.sendPlayer(actionstr);
      //send map to player
      cur.sendPlayer(myMaptoJson.getJSON().toString());
      cur.checkLose();
      //TODO:add check win, only me "ingame"
    }
    //end while   
  }

  public static void main(String[] args) {
    Server server = new Server(1234);
    System.out.println("========Now connect players!========");
    server.initGame(); 
    server.startGame();  
  }
}
