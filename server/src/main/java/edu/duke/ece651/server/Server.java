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
  }

  private static class PlayerHandler extends Thread {
    private HashMap<Integer, ArrayList<Territory>> territoryMap;
    private Communicator communicator;
    private int id;
    private int[] playerNum;

    public PlayerHandler(Communicator c, int id, int[] p) {
      this.communicator = c;
      this.id = id;
      this.playerNum = p;
    }

    public void initGame() {
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

    public void run() {
      initGame();
      communicator.close();
    }
  }

  public void start() {
    PlayerHandler first = new PlayerHandler(new Communicator(serverSock), 0, playerNum);
    first.start();
    ArrayList<PlayerHandler> list = new ArrayList<>(playerNum[0]);
    list.add(first);
    try {
      first.join();
    }
    catch(Exception ex) { 
      System.out.println("Exception:" + ex); 
    } 
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

  public static void main(String[] args) {
    Server server = new Server(1234);
    System.out.println("========Now connect players!========");
    server.start();
  }
}
