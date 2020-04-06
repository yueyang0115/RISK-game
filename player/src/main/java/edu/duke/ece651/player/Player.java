package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;

import java.io.IOException;
import java.util.*;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.*;

public class Player extends Application {
  private Stage Window;

  public void showStartView(PlayerHelper player, Stage Window) throws IOException {
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/StartGame.fxml"));
    loaderStart.setControllerFactory(c->{
      return new StartController(player, Window);
    });
    Scene scene = new Scene(loaderStart.load());
    Window.setScene(scene);
    Window.show();
  }


  @Override
  public void start(Stage primaryStage) throws Exception {
    this.Window = primaryStage;
    PlayerHelper player = new PlayerHelper();
    Displayable d = new Text();
    player.addDisplayable(d);
    ReceiveID(player, player.getCommunicator());
    showStartView(player, this.Window);
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
