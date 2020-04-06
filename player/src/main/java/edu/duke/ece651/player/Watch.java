package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Watch{
    //------------- Evolution 2 --------------//
    @FXML private Button ButtonA;
    @FXML private Button ButtonB;
    @FXML private Button ButtonC;
    @FXML private Button ButtonD;
    @FXML private Button ButtonE;
    @FXML private Button ButtonF;
    @FXML private Button ButtonG;
    @FXML private Button ButtonH;
    @FXML private Button ButtonI;
    @FXML private Button ButtonJ;
    @FXML private Button ButtonK;
    @FXML private Button ButtonL;
    @FXML private Label Prompt;
    @FXML private Label ActionDetail;
    private HashMap<Integer, ArrayList<Territory>> TerrMap;
    private PlayerHelper CurrPlayer;
    private HashMap<String, Button> ButtonMap;
    //create a button map which can relate the Territory name to the Button
    private void InitButtonMap(){
        ButtonMap = new HashMap<>();
        ButtonMap.put("A", ButtonA);
        ButtonMap.put("B", ButtonB);
        ButtonMap.put("C", ButtonC);
        ButtonMap.put("D", ButtonD);
        ButtonMap.put("E", ButtonE);
        ButtonMap.put("F", ButtonF);
        ButtonMap.put("G", ButtonG);
        ButtonMap.put("H", ButtonH);
        ButtonMap.put("I", ButtonI);
        ButtonMap.put("J", ButtonJ);
        ButtonMap.put("K", ButtonK);
        ButtonMap.put("L", ButtonL);
    }
    private Stage Window;
    public Watch(PlayerHelper CurrPlayer, Stage Window){
        this.TerrMap = new HashMap<>();
        this.TerrMap = CurrPlayer.getTerritoryMap();
        this.CurrPlayer = CurrPlayer;
        this.Window = Window;
    }


    public void initialize() throws IOException {
        System.out.println("++++++++++++++++++Initialize Watch++++++++++++++++++++");
        InitButtonMap();
        new Graph().showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo(), this.ButtonMap);
        showButton();
        ColorID PlayerColor = new ColorID();
        String PlayerName = PlayerColor.getPlayerColor(this.CurrPlayer.getPlayerInfo().getKey());
        this.Prompt.setText("You are " + PlayerName + ".");
        this.Prompt.setFont(new Font("Arial", 28));
        new Graph().showAction(this.CurrPlayer.getAllAction(), this.CurrPlayer.getPlayerInfo(), this.ActionDetail);
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> {
            try {
                WatchGame();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Exception "+ e);
            }
        });
        delay.play();

    }
    public void showButton(){
        //iterate through all the territory map to print out the details of the soldier number in the map
        for (HashMap.Entry<Integer, ArrayList<Territory>> entry : TerrMap.entrySet()){
            ArrayList<Territory> TerrList = entry.getValue();
            for(int i = 0; i < TerrList.size(); i++){
                Territory OneTerr = TerrList.get(i);
                String TerrName = OneTerr.getTerritoryName();
                Button Btn = ButtonMap.get(TerrName);
                HashMap<Integer,Integer> SoldierMap = OneTerr.getSoldiers();
                //construct the string that need to print out
                StringBuilder SoldierDetail = new StringBuilder();
                SoldierDetail.append(TerrName + "\n");
                for(HashMap.Entry<Integer,Integer> CurrentMap : SoldierMap.entrySet()){
                    SoldierDetail.append("Level " + CurrentMap.getKey() + ": " + CurrentMap.getValue() + "\n");
                }
                Btn.setText(SoldierDetail.toString());
                Btn.setFont(new Font(6));
            }
        }
        System.out.println("Already paint color");
    }
    public void WatchGame() throws IOException {
        this.CurrPlayer.ReceiveAllAction();
        String Answer = this.CurrPlayer.ReceiveFromServer();
        System.out.println("Answer" + Answer);
        if(Answer.contains("Game End")){
            new ShowView().ShowEndVIew(Answer,this.CurrPlayer, this.Window);
        }
        else{
            this.CurrPlayer.ContinueReceive(Answer);
            new ShowView().ShowWatchView(this.CurrPlayer,this.Window);
        }
    }

}