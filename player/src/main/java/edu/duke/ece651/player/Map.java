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

public class Map{
    @FXML private Button UpgradeBtn;
    @FXML private Button MoveBtn;
    @FXML private Button AttackBtn;
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
    public Map(PlayerHelper player, Stage Window){
        this.Window = Window;
        this.CurrPlayer = player;
        this.TerrMap = player.getTerritoryMap();
    }

    public void initialize(){
        InitButtonMap();
        new Graph().showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo(), this.ButtonMap);
        ColorID PlayerColor = new ColorID();
        String PlayerName = PlayerColor.getPlayerColor(this.CurrPlayer.getPlayerInfo().getKey());
        this.Prompt.setText("You are " + PlayerName + " Player, please choose action");
        this.Prompt.setFont(new Font("Arial", 28));
    }


    public String ComposeString(Territory Clicked){
        StringBuilder ShowDetail = new StringBuilder();
        int ID = Integer.valueOf(Clicked.getOwner().substring(7));
        ColorID FindName = new ColorID();
        ShowDetail.append("Territory Name: ").append(Clicked.getTerritoryName()).append("\n\n");
        ShowDetail.append("Owner Name: ").append(FindName.getPlayerColor(ID)).append("\n\n");
        int Size = new TerritorySize().getTerritorySize(Clicked.getTerritoryName());
        ShowDetail.append("Territory Size: ").append(Size).append("\n\n");
        ShowDetail.append("Soldiers\n");
        for(int i = 0; i < 7; i++){
            ShowDetail.append("  Level ").append(i).append(": ").append(Clicked.getSoldierLevel(i)).append("\n");
        }
        return ShowDetail.toString();
    }
    public void ShowLabel(Territory CurrentClicked){
        String ShowLabel = ComposeString(CurrentClicked);
        this.Detail.setText(ShowLabel);
        this.Detail.setFont(new Font("Arial", 20));
    }
    @FXML
    public void BtnA(){
        System.out.println("Click on A");
        Territory CurrentClicked =  FindTerritory(this.TerrMap, "A");
        ShowLabel(CurrentClicked);
    }

    @FXML
    public void BtnB(){
        System.out.println("Click on B");
        Territory CurrentClicked =  FindTerritory(this.TerrMap, "B");
        String ShowLabel = ComposeString(CurrentClicked);
        this.Detail.setText(ShowLabel);
    }
    @FXML
    public void BtnC(){
        System.out.println("Click on C");
        Territory CurrentClicked =  FindTerritory(this.TerrMap, "C");
        String ShowLabel = ComposeString(CurrentClicked);
        this.Detail.setText(ShowLabel);
    }
    @FXML
    public void BtnD(){
        System.out.println("Click on D");
        Territory CurrentClicked =  FindTerritory(this.TerrMap, "D");
        String ShowLabel = ComposeString(CurrentClicked);
        this.Detail.setText(ShowLabel);
    }
    @FXML
    public void BtnE(){
        System.out.println("Click on E");
        Territory CurrentClicked =  FindTerritory(this.TerrMap, "E");
        String ShowLabel = ComposeString(CurrentClicked);
        this.Detail.setText(ShowLabel);
    }
    @FXML
    public void BtnF(){
        System.out.println("Click on F");
        Territory CurrentClicked =  FindTerritory(this.TerrMap, "F");
        String ShowLabel = ComposeString(CurrentClicked);
        this.Detail.setText(ShowLabel);
    }
    @FXML
    public void BtnG(){
        System.out.println("Click on G");
        Territory CurrentClicked =  FindTerritory(this.TerrMap, "G");
        String ShowLabel = ComposeString(CurrentClicked);
        this.Detail.setText(ShowLabel);
    }
    @FXML
    public void BtnH(){
        System.out.println("Click on H");
        Territory CurrentClicked =  FindTerritory(this.TerrMap, "H");
        String ShowLabel = ComposeString(CurrentClicked);
        this.Detail.setText(ShowLabel);
    }
    @FXML
    public void BtnI(){
        System.out.println("Click on I");
        Territory CurrentClicked =  FindTerritory(this.TerrMap, "I");
        String ShowLabel = ComposeString(CurrentClicked);
        this.Detail.setText(ShowLabel);
    }
    @FXML
    public void BtnJ(){
        System.out.println("Click on J");
        Territory CurrentClicked =  FindTerritory(this.TerrMap, "J");
        String ShowLabel = ComposeString(CurrentClicked);
        this.Detail.setText(ShowLabel);
    }
    @FXML
    public void BtnK(){
        System.out.println("Click on K");
        Territory CurrentClicked =  FindTerritory(this.TerrMap, "K");
        String ShowLabel = ComposeString(CurrentClicked);
        this.Detail.setText(ShowLabel);
    }
    @FXML
    public void BtnL(){
        System.out.println("Click on L");
        Territory CurrentClicked =  FindTerritory(this.TerrMap, "L");
        String ShowLabel = ComposeString(CurrentClicked);
        this.Detail.setText(ShowLabel);
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
    @FXML
    public void Upgrading() throws IOException {
        System.out.println("Click on Upgrade");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/UpgradeChoose.fxml"));
        loaderStart.setControllerFactory(c->{
            return new UpgradeChoose(this.CurrPlayer, Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    @FXML
    public void ChooseMove() throws IOException {
        System.out.println("Click on Move");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Move_Attack.fxml"));
        loaderStart.setControllerFactory(c->{
            return new MoveORAttack(this.CurrPlayer, "Move");
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    @FXML
    public void ChooseAttack() throws IOException {
        System.out.println("Click on Attack");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Move_Attack.fxml"));
        loaderStart.setControllerFactory(c->{
            return new MoveORAttack(this.CurrPlayer, "Attack");
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    @FXML
    public void ChooseDone() throws IOException {
        System.out.println("Click on Move");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Move_Attack.fxml"));
        loaderStart.setControllerFactory(c->{
            return new MoveORAttack(this.CurrPlayer, "Attack");
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }
}
