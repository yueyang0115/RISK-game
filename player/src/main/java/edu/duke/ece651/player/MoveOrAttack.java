package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MoveOrAttack {
    private PlayerHelper CurrPlayer;
    private String ActionType;
    private HashMap<Integer, ArrayList<Territory>> TerrMap;
    private HashMap<Integer, ComboBox<Integer>> MapLevel;
    private Stage Window;
    @FXML private ComboBox<String> SourceTerr;
    @FXML private ComboBox<String> DstTerr;
    @FXML private Label Resource;
    @FXML private ComboBox<Integer> Level0;
    @FXML private ComboBox<Integer> Level1;
    @FXML private ComboBox<Integer> Level2;
    @FXML private ComboBox<Integer> Level3;
    @FXML private ComboBox<Integer> Level4;
    @FXML private ComboBox<Integer> Level5;
    @FXML private ComboBox<Integer> Level6;

    @FXML private Button OKButton;
    @FXML private Label Detail;
    @FXML private Label Prompt;

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
    private void LevelMap(){
        MapLevel = new HashMap<>();
        MapLevel.put(0,this.Level0);
        MapLevel.put(1,this.Level1);
        MapLevel.put(2,this.Level2);
        MapLevel.put(3,this.Level3);
        MapLevel.put(4,this.Level4);
        MapLevel.put(5,this.Level5);
        MapLevel.put(6,this.Level6);
    }
    public ComboBox<Integer> getComboBox(int level){
        return this.MapLevel.get(level);
    }
    public MoveOrAttack(PlayerHelper player, String Type, Stage Window){
        this.CurrPlayer = player;
        this.ActionType = Type;
        this.Window = Window;

    }
    public void initialize(){
        InitButtonMap();
        new Graph().showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo(), this.ButtonMap);
        ColorID PlayerColor = new ColorID();
        String PlayerName = PlayerColor.getPlayerColor(this.CurrPlayer.getPlayerInfo().getKey());
        this.Prompt.setText("You are " + PlayerName + " Player, please choose action");
        this.Prompt.setFont(new Font("Arial", 28));
        InitTerr();
        this.Resource.setText("Food Resources: " + this.CurrPlayer.getFoodResource());
        this.Resource.setTextFill(Color.web("#ff0000"));
        this.Resource.setFont(new Font("Arial", 20));
    }
    public void InitTerr(){
        int ID = this.CurrPlayer.getPlayerInfo().getKey();
        this.TerrMap = this.CurrPlayer.getTerritoryMap();
        ArrayList<Territory> OwnTerr = this.TerrMap.get(ID);
        ArrayList<String> Src = new ArrayList<>();
        ArrayList<String> MoveDst = new ArrayList<>();
        ArrayList<String> AttackDst = new ArrayList<>();
        for (int i = 0; i < OwnTerr.size(); i++) {
            Src.add(OwnTerr.get(i).getTerritoryName());
            MoveDst.add(OwnTerr.get(i).getTerritoryName());
        }
        ObservableList<String> SourceList = FXCollections.observableArrayList(Src);
        this.SourceTerr.setPromptText("Source");
        this.SourceTerr.setItems(SourceList);

        for (HashMap.Entry<Integer, ArrayList<Territory>> entry : this.TerrMap.entrySet()) {
            if(this.CurrPlayer.getPlayerInfo().getKey() != entry.getKey()) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    AttackDst.add(entry.getValue().get(i).getTerritoryName());
                }
            }
        }
        this.DstTerr.setPromptText("Destination");
        if(this.ActionType.equals("Move")){
            ObservableList<String> MoveDstList = FXCollections.observableArrayList(MoveDst);
            this.DstTerr.setItems(MoveDstList);
        }
        else{
            ObservableList<String> AttackDstList = FXCollections.observableArrayList(AttackDst);
            this.DstTerr.setItems(AttackDstList);
        }
    }

    @FXML
    public void InitSoldiers(){
        //initialize soldier number
        LevelMap();
        System.out.println("MapLevel Size: " + this.MapLevel.size());
        if(this.SourceTerr.getValue() != null){
            System.out.println("[DEBUG] Source is not empty " + this.SourceTerr.getValue());
            Territory Terr = Show.FindTerritory(this.TerrMap, this.SourceTerr.getValue());
            HashMap<Integer, Integer> Soldier = Terr.getSoldiers();
            int level = 0;
            for(HashMap.Entry<Integer, Integer> entry : Soldier.entrySet()){
                System.out.println("Print Level " + level);
                ArrayList<Integer> Num = new ArrayList<>();
                for(int i = 0; i <= 100; i++){
                    Num.add(i);
                }
                ObservableList LevelTemp = FXCollections.observableArrayList(Num);
                getComboBox(level).setPromptText("Level " + level);
                getComboBox(level).setValue(0);
                getComboBox(level).setItems(LevelTemp);
                Num.clear();
                level++;
            }
        }
    }


    private boolean CheckAction(){
        if(this.SourceTerr.getValue() == this.DstTerr.getValue()){
            this.Detail.setText("Invalid Action! Source Name cannot same as Destination Name");
            this.Detail.setFont(new Font("Arial", 24));
            return false;
        }
        if(this.SourceTerr.getValue() == null){
            this.Detail.setText("Source cannot be empty!");
            this.Detail.setFont(new Font("Arial", 24));
            return false;
        }
        if(this.DstTerr.getValue() == null){
            this.Detail.setText("Destination cannot be empty!");
            this.Detail.setFont(new Font("Arial", 24));
            return false;
        }
        return true;
    }
    @FXML
    public void OKBtn() throws IOException {
        System.out.println("Click on OK");
        if(CheckAction()){
            Action Current = new Action();
            Current.setOwner("player_" + this.CurrPlayer.getPlayerInfo().getKey());
            System.out.println("Current Action Owner: " + Current.getOwner());
            Territory Src = Show.FindTerritory(this.TerrMap, this.SourceTerr.getValue());
            Territory Dst = Show.FindTerritory(this.TerrMap, this.DstTerr.getValue());
            Current.setSrc(Src);
            Current.setDst(Dst);
            HashMap<Integer, Integer> Soldiers = new HashMap<>();
            for(int i = 0; i < 7; i++){
                Soldiers.put(i,getComboBox(i).getValue());
            }
            Current.setSoldiers(Soldiers);
            if(this.ActionType.equals("Move")){
                Current.setType("Move");
                this.CurrPlayer.setMoveAction(Current);
            }
            else{
                Current.setType("Attack");
                this.CurrPlayer.setAttackAction(Current);
            }
            MainPageView(this.CurrPlayer);
        }
        else{
            System.out.println("INVALID ACTION");
        }

    }
    public void MainPageView(PlayerHelper player) throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Map.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Map(player, this.Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }
    @FXML
    public void BtnA(){
        System.out.println("Click on A");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "A");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }

    @FXML
    public void BtnB(){
        System.out.println("Click on B");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "B");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnC(){
        System.out.println("Click on C");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "C");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnD(){
        System.out.println("Click on D");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "D");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnE(){
        System.out.println("Click on E");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "E");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnF(){
        System.out.println("Click on F");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "F");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnG(){
        System.out.println("Click on G");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "G");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnH(){
        System.out.println("Click on H");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "H");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnI(){
        System.out.println("Click on I");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "I");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnJ(){
        System.out.println("Click on J");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "J");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnK(){
        System.out.println("Click on K");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "K");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }
    @FXML
    public void BtnL(){
        System.out.println("Click on L");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "L");
        Show.ShowLabel(CurrentClicked, this.Detail);
    }



}
