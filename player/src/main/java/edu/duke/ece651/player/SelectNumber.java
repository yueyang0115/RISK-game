package edu.duke.ece651.player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.HashMap;

public class SelectNumber {
    //------------- Evolution 2 --------------//

    @FXML private ImageView player0;
    @FXML private ImageView player1;
    @FXML private ImageView player2;
    @FXML private ImageView player3;
    @FXML private ImageView player4;
    private HashMap<Integer, ImageView> ImageViewMap;
    @FXML private ComboBox<Integer> PlayerNumber;
    @FXML private Button OKBtn;
    private PlayerHelper CurrPlayer;
    private Stage Window;


    public SelectNumber(PlayerHelper CurrPlayer, Stage Window){
        this.CurrPlayer = CurrPlayer;
        this.Window = Window;
    }
    @FXML
    public void initialize(){
        setUpImageView();
        PlayerNumber.setValue(2);
        ObservableList<Integer> ChoiceNumber = FXCollections.observableArrayList(2,3,4,5);
        PlayerNumber.setItems(ChoiceNumber);
    }

    @FXML
    public void DisplayPhoto(){
        int number = this.PlayerNumber.getValue();
        for(int i = 0; i < 5; i++){
            if(i < number){
                Image Photo = new Image(getClass().getResourceAsStream("/Player_Photo/player" + i + ".png"));
                ImageViewMap.get(i).setImage(Photo);
            }
            else{
                Image Locked = new Image(getClass().getResourceAsStream("/Player_Photo/player" + i + "_lock.png"));
                ImageViewMap.get(i).setImage(Locked);
            }
        }
    }
    private void setUpImageView(){
        ImageViewMap = new HashMap<>();
        ImageViewMap.put(0,player0);
        ImageViewMap.put(1,player1);
        ImageViewMap.put(2,player2);
        ImageViewMap.put(3,player3);
        ImageViewMap.put(4,player4);
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
        ShowView.MainPageView(this.CurrPlayer, this.Window, true);
    }



}
