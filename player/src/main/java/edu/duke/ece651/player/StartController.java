package edu.duke.ece651.player;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class StartController {
    //------------- Evolution 2 --------------//
    @FXML private Button Start;
    private PlayerHelper CurrPlayer;
    private Pair<Integer, String> playerInfo;
    private Stage Window;
    public StartController(PlayerHelper CurrPlayer, Stage Window){
        this.CurrPlayer = CurrPlayer;
        this.Window = Window;
        System.out.println("[DEBUG] Inside Start Controller Constructor");
    }
    public Button getStartButton(){
        return  this.Start;
    }
    @FXML
    public void StartGame() throws IOException {
        if(CurrPlayer.getID() == 0){
            System.out.println("MY ID 0");
            //if it is the first player, the page will jump to ask the player to choose a total number
            showChooseView(this.CurrPlayer, this.Window);

        }
        else{
            System.out.println("MY ID 1");
            //wait until the first player already choose the total number
            this.CurrPlayer.InitValue();
            this.CurrPlayer.ReceiveMapANDShow();
            //after received information from server, go to main page of the game
            ShowView.MainPageView(this.CurrPlayer, this.Window, true);
        }
    }
    private void showChooseView(PlayerHelper player, Stage Window) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Views/SelectNumber.fxml"));
        loaderStart.setControllerFactory(c->{
            return new SelectNumber(player, Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }
}
