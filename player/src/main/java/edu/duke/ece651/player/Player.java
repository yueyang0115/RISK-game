package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.*;

public class Player extends Application {
  private HashMap<Integer, ArrayList<Territory>> territoryMap;
  private Pair<Integer, String> playerInfo;
  private ArrayList<Action> MoveAction;
  private ArrayList<Action> AttackAction;
  private HashMap<Integer, ArrayList<Action>> AllAction;
  private ArrayList<Upgrade> UpgradeAction;
  private Displayable displayer;
  private Communicator communicator;
  private int playerNum;
  private int FoodResource;
  private int TechResource;
  private Stage Window;
  private int id;

  public Player() {
    this.territoryMap = new HashMap<>();
    this.MoveAction = new ArrayList<>();
    this.AttackAction = new ArrayList<>();
    this.AllAction = new HashMap<>();

    this.playerNum = 0;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Window = new Stage();
    Window = primaryStage;
    Button BtnStart = new Button("Start Game");
    Label GameName = new Label("Strategic Conquest Game (RISC)");
    GridPane layout = new GridPane();
    layout.setVgap(30);
    layout.setHgap(30);
    layout.setAlignment(Pos.CENTER);
    layout.add(GameName, 0, 0);
    layout.add(BtnStart, 0,2);
    Scene Start = new Scene(layout, 300, 250);
    Window.setScene(Start);

    BtnStart.setOnAction(e-> {
      try {
        Init();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });
    this.communicator = new Communicator("127.0.0.1", 12345);

    Displayable d = new Graph();

    addDisplayable(d);

    Window.show();
  }

  private void Init() throws IOException {
    GridPane layout2 = new GridPane();
    layout2.setVgap(30);
    layout2.setHgap(30);
    layout2.setAlignment(Pos.CENTER);
    Label Choose = new Label("Please choose the player number");
    Button OKNum = new Button("OK");

    //choice box
    ChoiceBox<Integer> TotalPlayerNum = new ChoiceBox<>();
    TotalPlayerNum.getItems().addAll(2,3,4,5);
    //default value
    TotalPlayerNum.setValue(2);

    layout2.add(Choose, 0, 0);
    layout2.add(OKNum, 1,2);
    layout2.add(TotalPlayerNum, 0,2);

    Scene ChooseNum = new Scene(layout2, 300, 300);

    System.out.println("Waiting for id");
    setID(Integer.parseInt(receiveString()));
    System.out.println("Get id" + this.id);
    if(id == 0){
      System.out.println("My ID is 0");
      Window.setScene(ChooseNum);
    }
    else{
      System.out.println("My ID is 1");
      InitValue();

    }
    OKNum.setOnAction(e-> {
      try {
        getChoice(TotalPlayerNum);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });
  }

  private void getChoice(ChoiceBox<Integer> TotalPlayerNum) throws IOException {
    sendString(String.valueOf(TotalPlayerNum.getValue()));
    System.out.println("Clicked OK, Send " + TotalPlayerNum.getValue());
    InitValue();
  }

  public void InitValue() throws IOException {
    String color = new ColorID().getPlayerColor(id);
    this.playerInfo = new Pair<>(id, color);
    this.playerNum = Integer.parseInt(receiveString());
    System.out.println("Received playerNum = " + this.playerNum);
    Scanner scanner = new Scanner(System.in);
    PlayGame(scanner);
  }

  public void PlayGame(Scanner scanner) throws IOException {
    String msg;
    boolean Ask = false;
    boolean Lose = false;
    boolean LoseButWatch = false;

    while (true) {
      System.out.println("Waiting for msg map");
      msg = receiveString();
      //if the msg is game end, end the game
//      if (msg.contains("Game End!")) {
//        System.out.println(msg);
//        break;
//      }
//      if (msg.contains("Lose Game") && !Ask) {
//        Ask = true;
//        Lose = true;
//        System.out.println("========You lose the game========\n"
//                    + "Do you want to still watch the game? Please choose Y/N");
//            //wait for player to input their choice
//            while (true) {
//              String choice = scanner.nextLine().toUpperCase();
//              //make sure they only input Y/N
//              if (!choice.equals("Y") && !choice.equals("N")) {
//                System.out.println("Your Input is invalid.\n"
//                        + "Please choose Y/N");
//                continue;
//              }
//              //send the choice to server
//              sendString(choice);
//              if (choice.equals("Y")) {
//                LoseButWatch = true;
//            System.out.println("Choose Y");
//            break;
//          } else {
//            return;
//          }
//        }
//      }
      MyFormatter myformatter = new MyFormatter(playerNum);
//      if (!LoseButWatch) {
//        //if it is lose but watch this turn will not display the current world map
//        territoryMap.clear();
//        // System.out.println("Received Map = " + msg);
        myformatter.MapParse(territoryMap, msg);
        Stage newWindow = new Stage();
        System.out.println("Finish Parse Map");
        Label GameName = new Label("(RISC)");
        VBox layout = new VBox();
        layout.getChildren().add(GameName);
        Scene Start = new Scene(layout, 300, 250);
        System.out.println("Already add to Scene");
        newWindow.setScene(Start);
        newWindow.show();
        //displayMap();
        System.out.println("Finish show the map");
//      }
//      LoseButWatch = false;
      
//      WaitAction(Lose, myformatter);
    }
  }

  public void WaitAction(boolean Lose, MyFormatter myformatter) throws IOException {
    if (!Lose) {
      //send player input actions to server: move actions and attack actions
      OperateAction PlayerAction = new OperateAction(playerInfo, territoryMap);
      PlayerAction.readAction();

      //Send upgrades first
      UpgradeAction = PlayerAction.getUpgradeActions();
      CheckUpgrade();

      String UpgradeString = myformatter.UpgradeCompose(UpgradeAction).toString();
      sendString(UpgradeString);

      MoveAction = PlayerAction.getMoveActions();
      // System.out.println("[DEBUG PLAYER] Size Move Action" + this.MoveAction.size());
      String MoveString = myformatter.ActionCompose(MoveAction, "Move").toString();
      sendString(MoveString);
      AttackAction = PlayerAction.getAttackActions();
      String AttackString = myformatter.ActionCompose(AttackAction, "Attack").toString();
      sendString(AttackString);
      //receive the result of these actions from server
      System.out.println("Action Validate : " + receiveString());
    }
    //receive all players' actions
    String OtherActions = receiveString();
    // System.out.println(OtherActions);
    if (OtherActions.contains("valid")) {
      //if it is the turn game end and if received validation of the actions
      //receive another time to all actions
      OtherActions = receiveString();
    }
    AllAction.clear();
    myformatter.AllActionParse(AllAction, OtherActions);
    displayAction();
  }

  public void CheckUpgrade() throws IOException {
      Cost Cal = new Cost();
      int TotalCost = 0;
      //calculate the total cost to do the upgrade actions
      for(Upgrade Current: UpgradeAction){
        TotalCost = TotalCost + Current.getNumber() * Cal.getCosts(Current.getPrevLevel(), Current.getNextLevel());
      }
      //if the Actual cost to do the upgrade is already higher than the technology resources, clear UpgradeAction
      if(TotalCost > TechResource){
        UpgradeAction.clear();
        return;
      }
      TechResource -= TotalCost;
  }



  public void sendString(String str) {
    communicator.sendString(str);
  }

  public String receiveString() {
    return communicator.receive();
  }

  public void addDisplayable(Displayable d) {
    this.displayer = d;
  }

  public void displayMap() {
    if(Window == null) {
      System.out.println("[DEBUG] Window is null");
    }
    if(displayer == null) {
      System.out.println("[DEBUG] Displayer is null");
    }
    displayer.showMap(territoryMap, playerInfo, Window);
  }
  public void displayAction() {
    displayer.showAction(AllAction, playerInfo, Window);
  }
  public void close() {
    communicator.close();
  }
  //for testcases
  public void setTerritoryMap(HashMap<Integer, ArrayList<Territory>> TestMap){
    this.territoryMap = TestMap;
  }
  public void setAllAction(HashMap<Integer, ArrayList<Action>> TestAllAction){
    this.AllAction = TestAllAction;
  }
  public void setPlayerInfo(Pair<Integer, String> TestPlayerInfo){
    this.playerInfo = TestPlayerInfo;
  }
  public void setID(int ID){this.id = ID;}
  public static void main(String[] args) throws IOException {
    //Scanner scanner = new Scanner(System.in);
    Player player = new Player();

    launch(args);

    //player.init(scanner);
    //player.PlayGame(scanner);
    //close the socket
    //player.close();
  }
}
