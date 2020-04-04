package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;

import java.io.IOException;
import java.util.*;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.*;
import javafx.fxml.*;

public class Player extends Application {
  private Stage Window;
  //private Communicator communicator;

  public void showStartView(PlayerHelper player) throws IOException {
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/StartGame.fxml"));
    loaderStart.setControllerFactory(c->{
      return new StartController(player, this.Window);
    });
    Scene scene = new Scene(loaderStart.load());
    this.Window.setScene(scene);
    this.Window.show();
  }


  @Override
  public void start(Stage primaryStage) throws Exception {
    this.Window = primaryStage;
    PlayerHelper player = new PlayerHelper();
    Displayable d = new Text();
    player.addDisplayable(d);

    ReceiveID(player, player.getCommunicator());
    showStartView(player);
  }



  public void ReceiveID(PlayerHelper player, Communicator communicator){
    System.out.println("Waiting for id");
    int id = Integer.parseInt(receiveString(communicator));
    player.setID(id);
    System.out.println("Received! MY ID is " + id);
  }
  public void SendTotalNumber(int PlayerNum, Communicator communicator){
    sendString(String.valueOf(PlayerNum), communicator);
  }
/*
  public void init(Scanner scanner) {
    
    int id = this.playerInfo.getKey();
    //the first player input the total number of players
    if (id == 0) {
      System.out.println(
          "===You're the first player, please enter the number of all players ([2:5])===");
      int playerNum = scanner.nextInt();
      while (playerNum < 2 || playerNum > 5) {
        System.out.println("========Invalid playerNumber, try again ([2:5])========");
        playerNum = scanner.nextInt();
      }
      sendString(String.valueOf(playerNum));
      //send it to server
    }
    // System.out.println("[DEBUG] my id is " + id);
    String color = new ColorID().getPlayerColor(id);
    this.playerInfo = new Pair<>(id, color);
    playerNum = Integer.parseInt(receiveString());
  }

  public void PlayGame(Scanner scanner) throws IOException {
    String msg;
    boolean Ask = false;
    boolean Lose = false;
    boolean LoseButWatch = false;

    while (true) {
      msg = receiveString();
      //if the msg is game end, end the game
      if (msg.contains("Game End!")) {
        System.out.println(msg);
        break;
      }
      if (msg.contains("Lose Game") && !Ask) {
        Ask = true;
        Lose = true;
        System.out.println("========You lose the game========\n"
                    + "Do you want to still watch the game? Please choose Y/N");
            //wait for player to input their choice
            while (true) {
              String choice = scanner.nextLine().toUpperCase();
              //make sure they only input Y/N
              if (!choice.equals("Y") && !choice.equals("N")) {
                System.out.println("Your Input is invalid.\n"
                        + "Please choose Y/N");
                continue;
              }
              //send the choice to server
              sendString(choice);
              if (choice.equals("Y")) {
                LoseButWatch = true;
            System.out.println("Choose Y");
            break;
          } else {
            return;
          }
        }
      }
      MyFormatter myformatter = new MyFormatter(playerNum);
      if (!LoseButWatch) {
        //if it is lose but watch this turn will not display the current world map
        territoryMap.clear();
        // System.out.println("Received Map = " + msg);
        myformatter.MapParse(territoryMap, msg);
        displayMap();
        //Receive food resource
        FoodResource = Integer.parseInt(receiveString());
        System.out.println("Your food resource: " + FoodResource);
      }
      LoseButWatch = false;
      
      WaitAction(Lose, myformatter);
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


*/
  public void sendString(String str, Communicator communicator) {
    communicator.sendString(str);
  }

  public String receiveString(Communicator communicator) {
    return communicator.receive();
  }



  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    Player player = new Player();



    launch(args);

//    player.init(scanner);
//    player.PlayGame(scanner);
//    //close the socket
//    player.close();
  }
}
