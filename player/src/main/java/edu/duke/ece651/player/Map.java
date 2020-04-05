package edu.duke.ece651.player;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.ColorID;
import edu.duke.ece651.shared.Territory;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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

    @FXML private TreeView<String> Detail;



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
    public Map(PlayerHelper player){
        this.Detail = new TreeView<>();
        this.CurrPlayer = player;
        this.TerrMap = player.getTerritoryMap();
    }

    public void initialize(){
        showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo());
    }
    @Override
    public void showMap(HashMap<Integer, ArrayList<Territory>> CurrentMap, Pair<Integer, String> playerInfo) {
        InitButtonMap();
        ColorID PlayerColor = new ColorID();
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
    public void setTree(){

        TreeItem<String> TerrName, Soldier, Neighbor, Owner;
        TerrName = new TreeItem<>();
        Owner = makeBranch("OwnerName", TerrName);
        Soldier = makeBranch("Soldiers", TerrName);
        Neighbor = makeBranch("Neighbors", TerrName);
        ArrayList<TreeItem<String>> Level = new ArrayList<TreeItem<String>>();

        for(int i = 0; i < 7; i++){
            TreeItem<String> TempLevel = Level.get(0);
            TempLevel = makeBranch("Level " + i, Soldier);
        }

        this.Detail = new TreeView<>(TerrName);
    }

    public TreeItem<String> makeBranch(String title, TreeItem<String> parent){
        TreeItem<String> item = new TreeItem<>(title);
        parent.getChildren().add(item);
        return item;//return the branch
    }

    @FXML
    public void BtnA(){
        setTree();
        System.out.println("Click on A");
    }

}
