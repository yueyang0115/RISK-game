package edu.duke.ece651.player;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.ColorID;
import edu.duke.ece651.shared.Territory;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Map implements Displayable{
    @FXML private Button UpgradeBtn;
    @FXML private Button MoveBtn;
    @FXML private Button AttackBtn;
    @FXML private Button DoneBtn;

    @FXML private Button ButtonA = new Button();
    @FXML private Button ButtonB = new Button();
    @FXML private Button ButtonC = new Button();
    @FXML private Button ButtonD = new Button();
    @FXML private Button ButtonE = new Button();
    @FXML private Button ButtonF = new Button();
    @FXML private Button ButtonG = new Button();
    @FXML private Button ButtonH = new Button();
    @FXML private Button ButtonI = new Button();
    @FXML private Button ButtonJ = new Button();
    @FXML private Button ButtonK = new Button();
    @FXML private Button ButtonL = new Button();

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
    public Map(PlayerHelper player){
        InitButtonMap();
        this.CurrPlayer = player;
    }

    public void initialize(){
        this.CurrPlayer.displayMap();
    }
    @Override
    public void showMap(HashMap<Integer, ArrayList<Territory>> CurrentMap, Pair<Integer, String> playerInfo) {
        ColorID PlayerColor = new ColorID();
        for (HashMap.Entry<Integer, ArrayList<Territory>> entry : CurrentMap.entrySet()){
            //iterate each player color to set the button territory to its color
            String color = PlayerColor.getPlayerColor(entry.getKey());
            String background = color.toUpperCase();
            ArrayList<Territory> TerrList = entry.getValue();
            for(int i = 0; i < TerrList.size(); i++){
                Territory OneTerr = TerrList.get(i);
                String TerrName = OneTerr.getTerritoryName();
                System.out.println("Territory Name: " + TerrName);
                String setBack = "-fx-background-color: " + background;
                System.out.println("Set Style" + setBack);
                Button Btn = this.ButtonMap.get(TerrName);
                Btn.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        System.out.println("Already paint color");
    }

    @Override
    public void showAction(HashMap<Integer, ArrayList<Action>> RecvAction, Pair<Integer, String> playerInfo) {

    }

}
