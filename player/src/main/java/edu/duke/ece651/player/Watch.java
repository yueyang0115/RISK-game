package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
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
    @FXML private Label result;
    @FXML private TreeView<String> ActionDetail;
    private HashMap<Integer, ArrayList<Territory>> TerrMap;
    private PlayerHelper CurrPlayer;
    private HashMap<String, Button> ButtonMap;
    private WatchHelper watchHelper;
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

    private class WatchHelper extends Thread {
        private PlayerHelper CurrPlayer;
        private Stage Window;
        private TreeView<String> ActionDetail;
        private HashMap<String, Button> ButtonMap;
        private HashMap<Integer, ArrayList<Territory>> TerrMap;
        private Label result;
        public WatchHelper(PlayerHelper C, Stage W, TreeView<String> A, HashMap<String, Button> B, HashMap<Integer, ArrayList<Territory>> T, Label res) {
            this.CurrPlayer = C;
            this.Window = W;
            this.ActionDetail = A;
            this.ButtonMap = B;
            this.TerrMap = T;
            this.result = res;
        }
        public void run() {
            while (true) {
                this.CurrPlayer.ReceiveAllAction();
                String Answer = this.CurrPlayer.receiveString();
                System.out.println("Answer" + Answer);
                if(Answer.contains("Game End")) {
                    System.out.println("==============receive game end====================");
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            result.setText(Answer);
                            result.setTextFill(Color.RED);
                        }
                    });
                }
                else {
                    this.CurrPlayer.ContinueReceive(Answer);
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            new Graph().showAction(CurrPlayer.getAllAction(), CurrPlayer.getPlayerInfo(), ActionDetail);
                            new Graph().showMap(CurrPlayer.getTerritoryMap(), CurrPlayer.getPlayerInfo(), ButtonMap);
                            //init tooltip with territory information
                            SharedMethod.InitTerritoryDetail(ButtonMap, TerrMap);
                        }
                    });

                }
            }
        }
    }

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
        //init tooltip with territory information
        SharedMethod.InitTerritoryDetail(this.ButtonMap, this.TerrMap);
        //Show actions of other players
        new Graph().showAction(this.CurrPlayer.getAllAction(), this.CurrPlayer.getPlayerInfo(), this.ActionDetail);
        ColorID PlayerColor = new ColorID();
        String PlayerName = PlayerColor.getPlayerColor(this.CurrPlayer.getPlayerInfo().getKey());
        this.Prompt.setText("Your territories are in " + PlayerName + " color.");
        this.watchHelper = new WatchHelper(CurrPlayer, Window, ActionDetail, ButtonMap, TerrMap, result);
        this.watchHelper.start();
    }

}