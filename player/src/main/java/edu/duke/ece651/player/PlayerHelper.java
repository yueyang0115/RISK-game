package edu.duke.ece651.player;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.Communicator;
import edu.duke.ece651.shared.Territory;
import edu.duke.ece651.shared.Upgrade;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerHelper {
    private HashMap<Integer, ArrayList<Territory>> territoryMap;
    private Pair<Integer, String> playerInfo;
    private ArrayList<Action> MoveAction;
    private ArrayList<Action> AttackAction;
    private HashMap<Integer, ArrayList<Action>> AllAction;
    private ArrayList<Upgrade> UpgradeAction;
    private Displayable displayer;
    public Communicator communicator;
    private int playerNum;
    private int FoodResource;
    private int TechResource;
    //private Stage primaryStage;

    public PlayerHelper() {
        this.territoryMap = new HashMap<>();
        this.MoveAction = new ArrayList<>();
        this.AttackAction = new ArrayList<>();
        this.AllAction = new HashMap<>();
        this.communicator = new Communicator("127.0.0.1", 1234);
        this.playerNum = 0;
    }
    public Communicator getCommunicator(){
        return this.communicator;
    }
    public void setPlayerNum(int total){
        this.playerNum = total;
    }
    public int getPlayerNum(){
        return this.playerNum;
    }
    public int getID(){
        return this.playerInfo.getKey();
    }
    public void setID(int id){
        this.playerInfo = new Pair<>(id, "Init");
    }
    public void addDisplayable(Displayable d) {
        this.displayer = d;
    }

    public void displayMap() {
        displayer.showMap(territoryMap, playerInfo);
    }
    public void displayAction() {
        displayer.showAction(AllAction, playerInfo);
    }
    public void close() {
        communicator.close();
    }
    //for testcases
    public void setTerritoryMap(HashMap<Integer, ArrayList<Territory>> TestMap){
        this.territoryMap = TestMap;
    }
    public void setAllAction(HashMap<Integer, ArrayList<Action>> TestAllAction){
        this.AllAction = TestAllAction;
    }
    public void setPlayerInfo(Pair<Integer, String> TestPlayerInfo){
        this.playerInfo = TestPlayerInfo;
    }
}
