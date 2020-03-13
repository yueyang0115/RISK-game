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
  private int playerNum;
  private ServerSocket serverSock;

  public Server(int port, int playerNum) {
    // this.allMoveActions = new ArrayList<>();
    // this.allAttackActions = new ArrayList<>();
    this.port = port;
    this.playerNum = playerNum;
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
    private int playerNum;

    public PlayerHandler(Communicator c, int id, int p) {
      this.communicator = c;
      this.id = id;
      this.playerNum = p;
    }

    public void initGame() {
      communicator.sendString(String.valueOf(id));
      communicator.sendString(String.valueOf(playerNum));
      WorldInitter myworldinitter = new WorldInitter(playerNum);
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
    ArrayList<PlayerHandler> list = new ArrayList<>(playerNum);
    for (int id = 0; id < playerNum; id++) {
      PlayerHandler ph = new PlayerHandler(new Communicator(serverSock), id, playerNum); 
      list.add(ph);
      ph.start();
    }
    for (PlayerHandler ph : list) {   
      try {
        ph.join();
      }
      catch(Exception ex) { 
        System.out.println("Exception:" + ex); 
      }     
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please enter the number of players ([2:5])");
    int playerNum = scanner.nextInt();
    while (playerNum < 2 || playerNum > 5) {
      System.out.println("Invalid playerNumber, try again ([2:5])");
      playerNum = scanner.nextInt();
    }
    Server server = new Server(1234, playerNum);
    server.start();
  }
}
