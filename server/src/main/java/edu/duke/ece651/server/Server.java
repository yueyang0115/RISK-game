package edu.duke.ece651.server;
import java.util.*;

public class Server {
  //private ArrayList<Action> allMoveActions;
  //private ArrayList<Action> allAttackActions;
  private HashMap<Integer, ArrayList<Territory>> territoryMap;
  private Communicator communicator;

  public Server() {
    //this.allMoveActions = new ArrayList<>();
    //this.allAttackActions = new ArrayList<>();
    this.territoryMap = new HashMap<>();
  }

  public void initGame(int port) {
    this.communicator = new Communicator(port);
    communicator.send("hello");
  }

  public static void main(String[] args) {
    Server server = new Server();
    server.initGame(1234);
  }

  
}
