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
    Displayable d = new Map(player);
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
