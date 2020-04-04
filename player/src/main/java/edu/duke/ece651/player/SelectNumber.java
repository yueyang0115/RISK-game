package edu.duke.ece651.player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class SelectNumber {
    ObservableList<Integer> ChoiceNumber = FXCollections.observableArrayList(2,3,4,5);
    @FXML private ChoiceBox<Integer> PlayerNumber;
    @FXML private Button OKBtn;
    private PlayerHelper CurrPlayer;
    public SelectNumber(PlayerHelper CurrPlayer){
        this.CurrPlayer = CurrPlayer;
    }
    @FXML
    public void initialize(){
        PlayerNumber.setValue(2);
        PlayerNumber.setItems(ChoiceNumber);
    }
    @FXML
    public void OKNumber(){
        int TotalNumber = PlayerNumber.getValue();
        CurrPlayer.setPlayerNum(TotalNumber);
        System.out.println("OK Total Number " + TotalNumber);
        Player temp = new Player();
        temp.SendTotalNumber(TotalNumber, this.CurrPlayer.getCommunicator());
    }
}
