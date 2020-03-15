package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.util.*;
import java.net.*;
import java.util.*;
import java.util.Scanner;

public class Player {
  private HashMap<Integer, ArrayList<Territory>> territoryMap;
  private Pair<Integer, String> playerInfo;
  private ArrayList<Action> allAction;
  private Displayable displayer;
  private Communicator communicator;
  private int playerNum;
  
  public Player() {
    this.territoryMap = new HashMap<>();
    this.allAction = new ArrayList<>();
    this.communicator = new Communicator("127.0.0.1", 1234);
    this.playerNum = 0;
  }

  public void init() {
    int id = Integer.parseInt(receiveString());
    if (id == 0) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("=======You're the first player, please enter the number of all players ([2:5])========");
      int playerNum = scanner.nextInt();
      while (playerNum < 2 || playerNum > 5) {
        System.out.println("========Invalid playerNumber, try again ([2:5])========");
        playerNum = scanner.nextInt();
      }
      sendString(String.valueOf(playerNum));
    }
    System.out.println("[DEBUG] my id is " + id);
    String color = new ColorID().getPlayerColor(id);
    this.playerInfo = new Pair<>(id, color);
    playerNum = Integer.parseInt(receiveString());
    receiveMap();
    display();
  }

  public void sendString(String str) {
    communicator.sendString(str);
  }

  public String receiveString() {
    return communicator.receive();
  }

  public void receiveMap() {
    String str = communicator.receive();
    System.out.println(str);
    MyFormatter myformatter = new MyFormatter(playerNum);
    myformatter.MapParse(territoryMap, str);
  }

  public void addDisplayable(Displayable d) {
    this.displayer = d;
  }

  public void display() {
    displayer.show(territoryMap, playerInfo);
  }

  public void close() {
    communicator.close();
  }

  public static void main(String[] args) {
    Player player = new Player();
    Displayable d = new Text();
    player.addDisplayable(d);
    player.init();
    player.close();
  }
  
}
