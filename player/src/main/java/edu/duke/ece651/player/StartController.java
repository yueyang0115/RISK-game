package edu.duke.ece651.player;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class StartController {
    @FXML private Button Start;
    private PlayerHelper CurrPlayer;
    private Pair<Integer, String> playerInfo;
    private Stage Window;
    public StartController(PlayerHelper CurrPlayer, Stage Window){
        this.CurrPlayer = CurrPlayer;
        this.Window = Window;
    }
    @FXML
    public void StartGame() throws IOException {
        if(CurrPlayer.getID() == 0){
            System.out.println("MY ID 0");
            showChooseView(this.CurrPlayer, this.Window);
        }
        else{
            System.out.println("MY ID 1");
            this.CurrPlayer.InitValue();
            this.CurrPlayer.ReceiveMapANDShow();
            MainPageView(this.CurrPlayer);
        }
    }
    public void showChooseView(PlayerHelper player, Stage Window) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/SelectNumber.fxml"));
        loaderStart.setControllerFactory(c->{
            return new SelectNumber(player, Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    public void MainPageView(PlayerHelper player) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Map.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Map(player);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }
}
