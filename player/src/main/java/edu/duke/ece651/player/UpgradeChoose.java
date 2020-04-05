package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UpgradeChoose implements Displayable {
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
    public UpgradeChoose(PlayerHelper player, Stage Window){
        this.Window = Window;
        this.CurrPlayer = player;
        this.TerrMap = player.getTerritoryMap();
    }

    public void initialize(){
        System.out.println("aaaa========");
        showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo());
    }

    @Override
    public void showMap(HashMap<Integer, ArrayList<Territory>> CurrentMap, Pair<Integer, String> playerInfo) {
        InitButtonMap();
        ColorID PlayerColor = new ColorID();
        String PlayerName = PlayerColor.getPlayerColor(playerInfo.getKey());
        this.Prompt.setText("You are " + PlayerName + " Player.");
        for (HashMap.Entry<Integer, ArrayList<Territory>> entry : CurrentMap.entrySet()){
            //iterate each player color to set the button territory to its color
            String color = PlayerColor.getPlayerColor(entry.getKey());
            String background = color.toLowerCase();
            ArrayList<Territory> TerrList = entry.getValue();
            for(int i = 0; i < TerrList.size(); i++){
                Territory OneTerr = TerrList.get(i);
                String TerrName = OneTerr.getTerritoryName();
                System.out.println("Territory Name: " + TerrName);
                String setBack = "-fx-background-color: " + background + ";";
                System.out.println("Set Style" + setBack);
                Button Btn = this.ButtonMap.get(TerrName);
                Btn.setStyle(setBack);
            }
        }
        System.out.println("Already paint color");
    }

    @Override
    public void showAction(HashMap<Integer, ArrayList<Action>> RecvAction, Pair<Integer, String> playerInfo) {

    }

    @FXML
    public void BtnA(){
        System.out.println("Click on A");

    }

    @FXML
    public void BtnB(){
        System.out.println("Click on B");

    }
    @FXML
    public void BtnC(){
        System.out.println("Click on C");

    }
    @FXML
    public void BtnD(){
        System.out.println("Click on D");

    }
    @FXML
    public void BtnE(){
        System.out.println("Click on E");

    }
    @FXML
    public void BtnF(){
        System.out.println("Click on F");

    }
    @FXML
    public void BtnG(){
        System.out.println("Click on G");

    }
    @FXML
    public void BtnH(){
        System.out.println("Click on H");

    }
    @FXML
    public void BtnI(){
        System.out.println("Click on I");

    }
    @FXML
    public void BtnJ(){
        System.out.println("Click on J");

    }
    @FXML
    public void BtnK(){
        System.out.println("Click on K");

    }
    @FXML
    public void BtnL(){
        System.out.println("Click on L");
//        Territory CurrentClicked =  FindTerritory(this.TerrMap, "L");
//        String ShowLabel = ComposeString(CurrentClicked);
//        this.Detail.setText(ShowLabel);
    }

    public Territory FindTerritory(HashMap<Integer, ArrayList<Territory>> World, String TerritoryName) {
        Territory ans = new Territory();
        for (HashMap.Entry<Integer, ArrayList<Territory>> entry : World.entrySet()) {
            ArrayList<Territory> territoryList = entry.getValue();
            for (int j = 0; j < territoryList.size(); j++) {
                Territory Terr = territoryList.get(j);
                if (Terr.getTerritoryName().equals(TerritoryName)) {
                    // System.out.println("[DEBUG] find ans");
                    ans = Terr;
                    return ans;
                }
            }
        }
        return ans;
    }
}
