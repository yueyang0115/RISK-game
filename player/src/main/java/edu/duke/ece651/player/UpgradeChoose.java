package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UpgradeChoose {
    //------------- Evolution 2 --------------//
    //Show the map and ask the player to choose which one to upgrade
    //If the player choose another player's territory, pop-up the error message
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

    //Paint the territory button
    public void initialize(){
        InitButtonMap();
        new Graph().showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo(), this.ButtonMap);
        ColorID PlayerColor = new ColorID();
        String PlayerName = PlayerColor.getPlayerColor(this.CurrPlayer.getPlayerInfo().getKey());
        this.Prompt.setText("You are " + PlayerName + " Player.");
    }

    @FXML
    public void BtnA() throws IOException{
        //System.out.println("Click on A");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "A");
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        checkInput(CurrentClicked, "A");
    }

    @FXML
    public void BtnB() throws IOException{
        //System.out.println("Click on B");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "B");
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        checkInput(CurrentClicked, "B");
    }
    @FXML
    public void BtnC() throws IOException{
        //System.out.println("Click on C");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "C");
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        checkInput(CurrentClicked, "C");
    }
    @FXML
    public void BtnD() throws IOException{
        //System.out.println("Click on D");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "D");
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        checkInput(CurrentClicked, "D");
    }
    @FXML
    public void BtnE() throws IOException{
        //System.out.println("Click on E");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "E");
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        checkInput(CurrentClicked, "E");
    }
    @FXML
    public void BtnF() throws IOException{
        //System.out.println("Click on F");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "F");
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        checkInput(CurrentClicked, "F");
    }
    @FXML
    public void BtnG() throws IOException{
        //System.out.println("Click on G");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "G");
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        checkInput(CurrentClicked, "G");

    }
    @FXML
    public void BtnH() throws IOException{
        //System.out.println("Click on H");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "H");
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        checkInput(CurrentClicked, "H");

    }
    @FXML
    public void BtnI() throws IOException{
        //System.out.println("Click on I");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "I");
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        checkInput(CurrentClicked, "I");

    }
    @FXML
    public void BtnJ() throws IOException{
        //System.out.println("Click on J");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "J");
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        checkInput(CurrentClicked, "J");

    }
    @FXML
    public void BtnK() throws IOException{
        //System.out.println("Click on K");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "K");
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        checkInput(CurrentClicked, "K");
    }
    @FXML
    public void BtnL() throws IOException{
        //System.out.println("Click on L");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "L");
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        checkInput(CurrentClicked, "L");
    }

    //Check if the player choose his own territory
    private void checkInput(Territory CurrentClicked, String territoryName) throws IOException {
        String str = "player_" + CurrPlayer.getPlayerInfo().getKey();
        if (CurrentClicked.getOwner().equals(str)) {
            promptSuccess(territoryName);
        }
        else {
            promptError();
        }
    }

    //If check is successful, go to the UpgradeDetail page
    private void promptSuccess(String territoryName) throws IOException {
        //System.out.println("owner match");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/UpgradeInfo.fxml"));
        loaderStart.setControllerFactory(c->{
            return new UpgradeDetail(this.CurrPlayer, territoryName, Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    //If  check fails, pop-up an error window
    private void promptError() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/ErrorMsg.fxml"));
        Stage newWindow = new Stage();
        loaderStart.setControllerFactory(c->{
            return new ErrorMsgController("Invalid Territory", newWindow);
        });
        Scene scene = new Scene(loaderStart.load());
        newWindow.setScene(scene);
        newWindow.show();
    }
}
