package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
    private boolean Ask;
    private boolean Lose;
    private boolean LoseButWatch;
    //private Stage primaryStage;


    public PlayerHelper() {
        this.territoryMap = new HashMap<>();
        this.MoveAction = new ArrayList<>();
        this.AttackAction = new ArrayList<>();
        this.AllAction = new HashMap<>();
        this.UpgradeAction = new ArrayList<>();
        this.communicator = new Communicator("127.0.0.1", 1234);
        this.playerNum = 0;
        this.TechResource = 200;
        Ask = false;
        Lose = false;
        LoseButWatch = false;
    }


    public void InitValue(){
        //Init player's id, player info, receiving total player number from server
        int id = this.playerInfo.getKey();
        String color = new ColorID().getPlayerColor(id);
        this.playerInfo = new Pair<>(id, color);
        playerNum = Integer.parseInt(receiveString());
        System.out.println("Finish Init values. Total PlayerNumber: " + playerNum);
    }

    public String receiveString(){
        return this.communicator.receive();
    }

    public Communicator getCommunicator(){
        return this.communicator;
    }
    public void setPlayerNum(int total){
        this.playerNum = total;
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
    public void setMoveAction(Action Current){
        MoveAction.add(Current);
    }
    public void setAttackAction(Action Current){
        AttackAction.add(Current);
    }
    public void setUpgradeAction(Upgrade Current){
        UpgradeAction.add(Current);
    }

    public void ReceiveID(){
        System.out.println("Waiting for id");
        int id = Integer.parseInt(receiveString());
        setID(id);
        System.out.println("Received! MY ID is " + id);
    }

    public ArrayList<Action> getMoveAction(){
        return this.MoveAction;
    }
    public ArrayList<Action> getAttackAction(){
        return this.AttackAction;
    }
    public ArrayList<Upgrade> getUpgradeAction(){
        return this.UpgradeAction;
    }
    public int getFoodResource() { return FoodResource; }
    public int getTechResource() { return TechResource; }
    public void setTechResource(int t) { TechResource = t;}

    public void displayMap() {
        displayer.showMap(territoryMap, playerInfo,null);
    }
    public void displayAction() {
        displayer.showAction(AllAction, playerInfo,null);
    }
    public void close() {
        communicator.close();
    }
    public HashMap<Integer, ArrayList<Territory>> getTerritoryMap(){
        return this.territoryMap;
    }
    public Pair<Integer, String> getPlayerInfo(){
        return this.playerInfo;
    }
    public void setTerritoryMap(HashMap<Integer, ArrayList<Territory>> TestMap){
        this.territoryMap = TestMap;
    }
    public void setAllAction(HashMap<Integer, ArrayList<Action>> TestAllAction){
        this.AllAction = TestAllAction;
    }
    public void setPlayerInfo(Pair<Integer, String> TestPlayerInfo){
        this.playerInfo = TestPlayerInfo;
    }
    public void sendString(String str){
        this.communicator.sendString(str);
    }
    public void ReceiveMapANDShow(){
        //for the player that was not end of game or lose to receive map and food resources from server
        String msg = receiveString();
        String FoodStr = receiveString();
        System.out.println("Received Food: " + FoodStr);
        FoodResource = Integer.parseInt(FoodStr);
        MyFormatter myformatter = new MyFormatter(playerNum);
        territoryMap.clear();
        myformatter.MapParse(territoryMap, msg);
    }
    public void setAsk(boolean Ask){
        this.Ask = Ask;
    }
    public void setLose(boolean Ask){
        this.Ask = Ask;
    }
    public void setLoseButWatch(boolean Ask){
        this.Ask = Ask;
    }
    ///map or lose game or game end
    public String ReceiveFromServer(){
        String msg = receiveString();
        return msg;
    }
    public void ContinueReceive(String msg){
        //after check whether last receive is string or map
        //then continue receive food and parse the territory map
        //only receive food if the current player does not lose the game
        if(!LoseButWatch) {
            String FoodStr = receiveString();
            System.out.println("Received Food: " + FoodStr);
            FoodResource = Integer.parseInt(FoodStr);
            MyFormatter myformatter = new MyFormatter(playerNum);
            territoryMap.clear();
            myformatter.MapParse(territoryMap, msg);
        }
    }
    public void AddTechResource(HashMap<Integer, ArrayList<Territory>> TerrMap, Pair<Integer, String> PlayerInfo){
        //at the end of each turn, add their own technology resources
        TerritoryProduce AddResource = new TerritoryProduce();
        ArrayList<Territory> MyTerr = new ArrayList<>();
        MyTerr = TerrMap.get(PlayerInfo.getKey());
        for(int i = 0; i < MyTerr.size(); i++){
            int CurrAdd = AddResource.getTech(MyTerr.get(i).getTerritoryName());
            this.TechResource += CurrAdd;
        }
    }

    public HashMap<Integer, ArrayList<Action>> getAllAction(){
        return this.AllAction;
    }
    public void SendAction(){
        //send the actions separately to the server
        if (!Lose) {
            MyFormatter myformatter = new MyFormatter(playerNum);
            String UpgradeString = myformatter.UpgradeCompose(UpgradeAction).toString();
            System.out.println("Upgrade Actions: " + UpgradeString);
            sendString(UpgradeString);
            String MoveString = myformatter.ActionCompose(MoveAction, "Move").toString();
            System.out.println("Move Actions: " + MoveString);
            sendString(MoveString);
            String AttackString = myformatter.ActionCompose(AttackAction, "Attack").toString();
            System.out.println("Attack Actions: " + AttackString);
            sendString(AttackString);
            ClearActions();
        }
    }
    public void ClearActions(){
        //after each turn, remove the actions' list to add new actions
        this.UpgradeAction.clear();
        this.AttackAction.clear();
        this.MoveAction.clear();
    }

    public String ReceiveActionRes(){
        if (!Lose) {
            //Receive the Current Action Validation Result
            String res = receiveString();
            System.out.println("[DEBUG]Receive Result: " + res);
            return res;
        }
        return null;
    }

    public void ReceiveAllAction(){
        MyFormatter myformatter = new MyFormatter(playerNum);
        //receive all players' actions
        String OtherActions = receiveString();

        System.out.println("Receive All Actions: " + OtherActions);
        if(OtherActions.contains("valid")){
            OtherActions = receiveString();
            System.out.println("Before Game End All actions: " + OtherActions);
        }
        AllAction.clear();
        myformatter.AllActionParse(AllAction, OtherActions);
    }

}
