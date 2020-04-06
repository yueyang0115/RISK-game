package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Lose{
    @FXML private Button buttonYes;
    @FXML private Button buttonNo;

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
    @FXML private Label Detail;
    private String Result;
    private PlayerHelper CurrPlayer;
    private Stage Window;
    private HashMap<String, Button> ButtonMap;
    private HashMap<Integer, ArrayList<Territory>> TerrMap;
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

    public Lose(PlayerHelper CurrPlayer, Stage Window, String Validation){
        this.CurrPlayer = CurrPlayer;
        this.Window = Window;
        this.Result = Validation;
    }
    public void initialize(){
        InitButtonMap();
        new Graph().showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo(), this.ButtonMap);

        new Graph().showAction(this.CurrPlayer.getAllAction(), this.CurrPlayer.getPlayerInfo(), this.Detail);
        System.out.println("Print out All Actions");

        ColorID PlayerColor = new ColorID();
        String PlayerName = PlayerColor.getPlayerColor(this.CurrPlayer.getPlayerInfo().getKey());
        this.Prompt.setText("You are " + PlayerName + ".");
        this.Prompt.setFont(new Font("Arial", 28));
    }
    @FXML
    public void LoseButWatch() throws IOException {
        System.out.println("Lose But Watch");
        this.CurrPlayer.setLoseButWatch(true);
        //send Y to server to continue watch the game
        this.CurrPlayer.sendString("Y");
        System.out.println("Already Send the Watch result to server");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Watch.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Watch(this.CurrPlayer, this.Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }
    public void LoseNotWatch() throws IOException {
        //send N to server to not watch
        this.CurrPlayer.sendString("N");
        System.out.println("Lose Not Watch");
        this.Window.close();
    }
}