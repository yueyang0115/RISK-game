package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Watch{

    
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

    private PlayerHelper CurrPlayer;
    private HashMap<String, Button> ButtonMap;

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
        this.CurrPlayer = CurrPlayer;
        this.Window = Window;
    }

    /*public void DisableButton(){
        for(HashMap.Entry<String, Button> entry : ButtonMap.entrySet()){
            entry.getValue().setDisable(true);
        }
    }*/

    public void initialize() throws IOException {
        System.out.println("++++++++++++++++++Initialize Watch++++++++++++++++++++");
        InitButtonMap();
        new Graph().showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo(), this.ButtonMap);
        ColorID PlayerColor = new ColorID();
        String PlayerName = PlayerColor.getPlayerColor(this.CurrPlayer.getPlayerInfo().getKey());
        this.Prompt.setText("You are " + PlayerName + ".");
        this.Prompt.setFont(new Font("Arial", 28));
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
        new Graph().showAction(this.CurrPlayer.getAllAction(), this.CurrPlayer.getPlayerInfo(), this.ActionDetail);

    }

    public void WatchGame() throws IOException {
        this.CurrPlayer.ReceiveAllAction();
        String Answer = this.CurrPlayer.ReceiveFromServer();
        System.out.println("Answer" + Answer);
        if(Answer.contains("Game End")){
            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/End.fxml"));
            loaderStart.setControllerFactory(c->{
                return new End(this.CurrPlayer, Answer);
                //return new Test();
            });
            Scene scene = new Scene(loaderStart.load());
            this.Window.setScene(scene);
        }
        else{
            this.CurrPlayer.ContinueReceive(Answer);
            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Watch.fxml"));
            loaderStart.setControllerFactory(c->{
                return new Watch(this.CurrPlayer, this.Window);

            });
            System.out.println("================Reload Watch Page================");
            Scene scene = new Scene(loaderStart.load());
            this.Window.setScene(scene);
        }
        this.Window.show();
    }

}