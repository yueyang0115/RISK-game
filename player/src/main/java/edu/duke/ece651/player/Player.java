package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.util.*;
import java.net.*;
import java.util.*;

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
    //System.out.println("my id is " + id);
    String color = new ColorID().getPlayerColor(id);
    //System.out.println(color);
    this.playerInfo = new Pair<>(id, color);
    playerNum = Integer.parseInt(receiveString());
    //System.out.println(playerNum);
    receiveMap();
    display();
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
