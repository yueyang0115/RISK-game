package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.*;

public class Player extends Application {
  private Stage Window;

  public void showStartView(PlayerHelper player, Stage Window) throws IOException {
    //load the start game page
    FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Views/StartGame.fxml"));
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
    //start the game and receive their own id and store it
    player.ReceiveID();
    //after received, continue to display the start page
    showStartView(player, this.Window);
  }

  public void SendTotalNumber(int PlayerNum, Communicator communicator){
    sendString(String.valueOf(PlayerNum), communicator);
  }

  public void sendString(String str, Communicator communicator) {
    communicator.sendString(str);
  }



  public static void main(String[] args) {
    launch(args);
  }
}
