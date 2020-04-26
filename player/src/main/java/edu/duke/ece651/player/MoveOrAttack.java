package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MoveOrAttack {
    //------------- Evolution 2 --------------//
    private PlayerHelper CurrPlayer;
    private String ActionType;
    private HashMap<Integer, ArrayList<Territory>> TerrMap;
    private HashMap<Integer, ComboBox<Integer>> MapLevel;
    private Stage Window;

    @FXML private ImageView Photo;

    @FXML private Label Food;
    @FXML private Label Tech;
    @FXML private Label AllianceInfo;

    @FXML private ComboBox<String> SourceTerr;
    @FXML private ComboBox<String> DstTerr;
    @FXML private ComboBox<Integer> Level0;
    @FXML private ComboBox<Integer> Level1;
    @FXML private ComboBox<Integer> Level2;
    @FXML private ComboBox<Integer> Level3;
    @FXML private ComboBox<Integer> Level4;
    @FXML private ComboBox<Integer> Level5;
    @FXML private ComboBox<Integer> Level6;

    @FXML private Button OKButton;
    @FXML private TreeView<String> DetailActions;

    @FXML private Label Detail;
    @FXML private Label PromptColor;
    @FXML private Label PromptAction;
    @FXML private Label EnteredPrompt;

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
        LevelMap();
        this.CurrPlayer = player;
        this.ActionType = Type;
        this.Window = Window;
        this.TerrMap = this.CurrPlayer.getTerritoryMap();

    }
    public void initialize(){
        //initialize the button's shape and color
        InitButtonMap();
        new Graph().showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo(), this.ButtonMap);
        SharedMethod.InitTerritoryDetail(this.ButtonMap, this.TerrMap);
        SharedMethod.InitFigure(this.CurrPlayer, this.Photo);
        InitInfo();
        InitPrompt();
        InitSrcDst();
        SharedMethod.InitActionDetail(this.CurrPlayer, this.DetailActions);
    }


    private void InitInfo(){
        Food.setText(String.valueOf(CurrPlayer.getFoodResource()));
        Tech.setText(String.valueOf(CurrPlayer.getTechResource()));
        this.AllianceInfo.setText(SharedMethod.getAllianceInfo(this.CurrPlayer));
    }

    private void InitPrompt(){
        this.PromptAction.setText("please choose " + this.ActionType + " action details");
        this.PromptColor.setText("Your territories is in " + this.CurrPlayer.getPlayerInfo().getValue() + " Color");
        this.EnteredPrompt.setText("Entered " + this.ActionType + " Actions");
    }

    public void InitSrcDst(){
        //for different action type: MOVE OR ATTACK
        //search through all the territory map to set the comboBox's value
        int ID = this.CurrPlayer.getPlayerInfo().getKey();

        ArrayList<Territory> OwnTerr = this.TerrMap.get(ID);
        ArrayList<String> Src = new ArrayList<>();
        ArrayList<String> MoveDst = new ArrayList<>();
        ArrayList<String> AttackDst = new ArrayList<>();
        //initialize the source territory and destination territory
        for (int i = 0; i < OwnTerr.size(); i++) {
            Src.add(OwnTerr.get(i).getTerritoryName());
            MoveDst.add(OwnTerr.get(i).getTerritoryName());
        }

        int MyAllyID = this.CurrPlayer.getMyAlly();
        ArrayList<Territory> MyAllyTerr = this.TerrMap.get(MyAllyID);
        if(MyAllyID != -1){
            for(int i = 0; i < MyAllyTerr.size(); i++){
                MoveDst.add(MyAllyTerr.get(i).getTerritoryName());
            }
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
                    //give each combo box 0~100 choices and validate it in the server side
                    Num.add(i);
                }
                ObservableList LevelTemp = FXCollections.observableArrayList(Num);
                getComboBox(level).setPromptText("Level " + level);
                getComboBox(level).setItems(LevelTemp);
                Num.clear();
                level++;
            }
        }
    }


    private boolean CheckAction(){
        //make sure the source is not the empty
        //the destination is not empty
        //the source is not the same as the destination
        if(this.SourceTerr.getValue() == this.DstTerr.getValue()){
            this.Detail.setText("Invalid Action!\nSource Name cannot same as Destination Name");
            return false;
        }
        if(this.SourceTerr.getValue() == null){
            this.Detail.setText("Invalid Action!\nSource cannot be empty!");
            return false;
        }
        if(this.DstTerr.getValue() == null){
            this.Detail.setText("Invalid Action!\nDestination cannot be empty!");
            return false;
        }
        boolean NumberCheck = false;
        for(int i = 0; i < 7; i++){
            if(this.MapLevel.get(i).getValue() != null){
                NumberCheck = true;
                break;
            }
        }
        if(!NumberCheck){
            this.Detail.setText("Invalid Action!\nAt Least One Level has to be Non-Zero");
            return false;
        }
        return true;
    }
    @FXML
    public void OKBtn() throws IOException {
        System.out.println("Click on OK");
        //if it click on the OK
        //check whether the value is valid
        if(CheckAction()){
            //if it is valid, add the current action into its action list which is stored in the PlayerHelper class
            Action Current = new Action();
            Current.setOwner("player_" + this.CurrPlayer.getPlayerInfo().getKey());
            System.out.println("Current Action Owner: " + Current.getOwner());
            Territory Src = Show.FindTerritory(this.TerrMap, this.SourceTerr.getValue());
            Territory Dst = Show.FindTerritory(this.TerrMap, this.DstTerr.getValue());
            Current.setSrc(Src);
            Current.setDst(Dst);
            HashMap<Integer, Integer> Soldiers = new HashMap<>();
            for(int i = 0; i < 7; i++){
                if(getComboBox(i).getValue() == null) {
                    Soldiers.put(i, 0);
                }
                else{
                    Soldiers.put(i, getComboBox(i).getValue());
                }
            }
            Current.setSoldiers(Soldiers);
            //MOVE OR ATTACK
            if(this.ActionType.equals("Move")){
                Current.setType("Move");
                this.CurrPlayer.setMoveAction(Current);
            }
            else{
                Current.setType("Attack");
                this.CurrPlayer.setAttackAction(Current);
            }
            ShowView.MainPageView(this.CurrPlayer, this.Window, false);
        }
        else{
            //if it is not a valid action, OK will not work
            System.out.println("INVALID ACTION");
        }

    }

}
