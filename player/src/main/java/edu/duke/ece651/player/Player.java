package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.util.*;
import java.util.*;
import java.util.Scanner;

public class Player {
  private HashMap<Integer, ArrayList<Territory>> territoryMap;
  private Pair<Integer, String> playerInfo;
  private ArrayList<Action> MoveAction;
  private ArrayList<Action> AttackAction;
  private HashMap<Integer, ArrayList<Action>> AllAction;
  private Displayable displayer;
  private Communicator communicator;
  private int playerNum;
  
  public Player() {
    this.territoryMap = new HashMap<>();
    this.MoveAction = new ArrayList<>();
    this.AttackAction = new ArrayList<>();
    this.AllAction = new HashMap<>();
    this.communicator = new Communicator("127.0.0.1", 1234);
    this.playerNum = 0;
  }

  public void init(Scanner scanner) {
    int id = Integer.parseInt(receiveString());
    if (id == 0) {
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
  }
  
  public void PlayGame(Scanner scanner){
    String msg;
    boolean Ask = false;
    boolean Lose = false;
    OUT:
    while(true){ 
      msg = receiveString();
      if(msg.contains("Game End!")){
        System.out.println(msg);
        break;
      }
      if(msg.contains("Lose Game") && !Ask){
        Ask = true;
        Lose = true;
        System.out.println("========You lose the game========\n" + "Do you want to still watch the game? Please choose Y/N");
        while (true) {
          String choice = scanner.nextLine().toUpperCase();
          if (!choice.equals("Y") && !choice.equals("N")) {
            System.out.println("Your Input is invalid.\n" + "Please choose Y/N");
            continue;
          }
          sendString(choice);
          if(choice.equals("Y")){ 
            continue OUT;
          }
          return;  
        }
      }
      MyFormatter myformatter = new MyFormatter(playerNum);
      myformatter.MapParse(territoryMap, msg);
      displayMap();
      WaitAction(Lose, myformatter); 
    }
  }

  
  public void WaitAction(boolean Lose, MyFormatter myformatter){
    if(!Lose){
      OperateAction PlayerAction = new OperateAction(playerInfo, territoryMap);
      PlayerAction.readAction();
      this.MoveAction = PlayerAction.getMoveActions();
      System.out.println("[DEBUG PLAYER] Size Move Action" + this.MoveAction.size());
      String MoveString = myformatter.ActionCompose(this.MoveAction, "Move").toString();
      sendString(MoveString);
      AttackAction = PlayerAction.getAttackActions();
      String AttackString = myformatter.ActionCompose(AttackAction, "Attack").toString();
      sendString(AttackString);
      System.out.println("Validation result of your actions: " + receiveString());
    }
    String OtherActions = receiveString();
    AllAction.clear();
    myformatter.AllActionParse(AllAction, OtherActions);
    displayAction();
  }
  
  public void sendString(String str) {
    communicator.sendString(str);
  }

  public String receiveString() {
    return communicator.receive();
  }

  public void receiveMap() {
    String str = communicator.receive();
    MyFormatter myformatter = new MyFormatter(playerNum);
    myformatter.MapParse(territoryMap, str);
  }

  public void addDisplayable(Displayable d) {
    this.displayer = d;
  }

  public void displayMap() {
    displayer.showMap(territoryMap, playerInfo);
  }
  public void displayAction(){
    displayer.showAction(AllAction, playerInfo);
  }
  public void close() {
    communicator.close();
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Player player = new Player();
    Displayable d = new Text();
    
    player.addDisplayable(d);
    player.init(scanner);
    player.PlayGame(scanner);
    player.close();
  }
  
}
