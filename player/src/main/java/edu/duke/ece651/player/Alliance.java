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
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Alliance {
    //------------- Evolution 2 --------------//
    @FXML private Button DoneBtn;

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

    @FXML private Label Detail;
    @FXML private Label Prompt;

    private Stage Window;
    private PlayerHelper CurrPlayer;
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
    public Alliance(PlayerHelper player, Stage Window){
        this.Window = Window;
        this.CurrPlayer = player;
        this.TerrMap = player.getTerritoryMap();
    }

    public void initialize(){
        InitButtonMap();
        new Graph().showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo(), this.ButtonMap);
        ColorID PlayerColor = new ColorID();
        String PlayerName = PlayerColor.getPlayerColor(this.CurrPlayer.getPlayerInfo().getKey());
        this.Prompt.setText("You are " + PlayerName + " Player, please choose which player to form alliance");
        this.Prompt.setFont(new Font("Arial", 28));
    }

    //if the player click the button, show the detail of each territory in the right side label
    @FXML
    public void BtnA(){
        //System.out.println("Click on A");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "A");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }


    @FXML
    public void BtnB(){
        //System.out.println("Click on B");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "B");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnC(){
        //System.out.println("Click on C");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "C");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnD(){
        //System.out.println("Click on D");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "D");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnE(){
        //System.out.println("Click on E");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "E");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnF(){
        //System.out.println("Click on F");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "F");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnG(){
        //System.out.println("Click on G");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "G");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnH(){
        //System.out.println("Click on H");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "H");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnI(){
        //System.out.println("Click on I");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "I");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnJ(){
        //System.out.println("Click on J");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "J");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnK(){
        //System.out.println("Click on K");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "K");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnL(){
        //System.out.println("Click on L");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "L");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void ChooseDone() throws IOException {
        System.out.println("Click on Done");
        //TODO: add alliance
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Map.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Map(this.CurrPlayer, Window, false);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();

    }
}
