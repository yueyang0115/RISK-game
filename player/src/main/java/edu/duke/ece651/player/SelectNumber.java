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
    //------------- Evolution 2 --------------//
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
        //if the first player already choose the value, they will click on the OK button
        int TotalNumber = PlayerNumber.getValue();
        CurrPlayer.setPlayerNum(TotalNumber);
        System.out.println("OK Total Number " + TotalNumber);
        Player temp = new Player();
        //send the total number chose by the first player to the server
        temp.SendTotalNumber(TotalNumber, this.CurrPlayer.getCommunicator());
        //inside init value: init some player's information
        this.CurrPlayer.InitValue();
        this.CurrPlayer.ReceiveMapANDShow();
        new ShowView().MainPageView(this.CurrPlayer, this.Window);
    }



}
