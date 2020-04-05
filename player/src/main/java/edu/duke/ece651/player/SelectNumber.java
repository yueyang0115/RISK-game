package edu.duke.ece651.player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectNumber {
    ObservableList<Integer> ChoiceNumber = FXCollections.observableArrayList(2,3,4,5);
    @FXML private ChoiceBox<Integer> PlayerNumber;
    @FXML private Button OKBtn;
    private PlayerHelper CurrPlayer;
    private Stage Window;
    public SelectNumber(PlayerHelper CurrPlayer, Stage Window){
        this.CurrPlayer = CurrPlayer;
        this.Window = Window;
    }
    @FXML
    public void initialize(){
        PlayerNumber.setValue(2);
        PlayerNumber.setItems(ChoiceNumber);
    }
    @FXML
    public void OKNumber() throws IOException {
        int TotalNumber = PlayerNumber.getValue();
        CurrPlayer.setPlayerNum(TotalNumber);
        System.out.println("OK Total Number " + TotalNumber);
        Player temp = new Player();
        temp.SendTotalNumber(TotalNumber, this.CurrPlayer.getCommunicator());
        this.CurrPlayer.InitValue();
        this.CurrPlayer.ReceiveMapANDShow();
        MainPageView(this.CurrPlayer);
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
