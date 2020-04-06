package edu.duke.ece651.player;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.ColorID;
import edu.duke.ece651.shared.Territory;
import edu.duke.ece651.shared.TerritorySize;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DoneAction {
    private PlayerHelper CurrPlayer;
    private String ValidationResult;

    @FXML private Label Result;
    @FXML private Label ActionsOrDetail;

    @FXML private Label ActionDetailPrompt;
    private HashMap<String, Button> ButtonMap;
    private HashMap<Integer, ArrayList<Territory> > TerrMap;

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

    @FXML private Label Prompt;

    private Stage Window;

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
    public DoneAction(PlayerHelper CurrPlayer, String Validation, Stage Window){
        this.CurrPlayer = CurrPlayer;
        this.ValidationResult = Validation;
        this.TerrMap = CurrPlayer.getTerritoryMap();
        this.Window = Window;
    }

    public void initialize(){
        InitButtonMap();
        Graph Display = new Graph();
        Display.showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo(), this.ButtonMap);
        this.ActionDetailPrompt.setText("Actions ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        this.Result.setText(this.ValidationResult);
        this.Result.setFont(new Font("Arial", 24));
        Display.showAction(this.CurrPlayer.getAllAction(), this.CurrPlayer.getPlayerInfo(), this.ActionsOrDetail);
        ColorID PlayerColor = new ColorID();
        String PlayerName = PlayerColor.getPlayerColor(this.CurrPlayer.getPlayerInfo().getKey());
        this.Prompt.setText("You are " + PlayerName + " Player, please choose action");
        this.Prompt.setFont(new Font("Arial", 28));
    }


    @FXML
    public void BtnA(){
        //System.out.println("Click on A");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "A");
        this.ActionDetailPrompt.setText("Original Territory Detail ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        Show.ShowLabel(CurrentClicked, this.ActionsOrDetail);
    }

    @FXML
    public void BtnB(){
        //System.out.println("Click on B");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "B");
        this.ActionDetailPrompt.setText("Original Territory Detail ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        Show.ShowLabel(CurrentClicked, this.ActionsOrDetail);
    }
    @FXML
    public void BtnC(){
        //System.out.println("Click on C");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "C");
        this.ActionDetailPrompt.setText("Original Territory Detail ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        Show.ShowLabel(CurrentClicked, this.ActionsOrDetail);
    }
    @FXML
    public void BtnD(){
        //System.out.println("Click on D");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "D");
        this.ActionDetailPrompt.setText("Original Territory Detail ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        Show.ShowLabel(CurrentClicked, this.ActionsOrDetail);
    }
    @FXML
    public void BtnE(){
        //System.out.println("Click on E");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "E");
        this.ActionDetailPrompt.setText("Original Territory Detail ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        Show.ShowLabel(CurrentClicked, this.ActionsOrDetail);
    }
    @FXML
    public void BtnF(){
        //System.out.println("Click on F");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "F");
        this.ActionDetailPrompt.setText("Original Territory Detail ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        Show.ShowLabel(CurrentClicked, this.ActionsOrDetail);
    }
    @FXML
    public void BtnG(){
        //System.out.println("Click on G");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "G");
        this.ActionDetailPrompt.setText("Original Territory Detail ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        Show.ShowLabel(CurrentClicked, this.ActionsOrDetail);
    }
    @FXML
    public void BtnH(){
        //System.out.println("Click on H");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "H");
        this.ActionDetailPrompt.setText("Original Territory Detail ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        Show.ShowLabel(CurrentClicked, this.ActionsOrDetail);
    }
    @FXML
    public void BtnI(){
        //System.out.println("Click on I");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "I");
        this.ActionDetailPrompt.setText("Original Territory Detail ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        Show.ShowLabel(CurrentClicked, this.ActionsOrDetail);
    }
    @FXML
    public void BtnJ(){
        //System.out.println("Click on J");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "J");
        this.ActionDetailPrompt.setText("Original Territory Detail ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        Show.ShowLabel(CurrentClicked, this.ActionsOrDetail);
    }
    @FXML
    public void BtnK(){
        //System.out.println("Click on K");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "K");
        this.ActionDetailPrompt.setText("Original Territory Detail ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        Show.ShowLabel(CurrentClicked, this.ActionsOrDetail);
    }
    @FXML
    public void BtnL(){
        //System.out.println("Click on L");
        Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, "L");
        this.ActionDetailPrompt.setText("Original Territory Detail ");
        this.ActionDetailPrompt.setFont(new Font("Arial", 24));
        Show.ShowLabel(CurrentClicked, this.ActionsOrDetail);
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
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Move_attack.fxml"));
        loaderStart.setControllerFactory(c->{
            return new MoveOrAttack(this.CurrPlayer, "Move", this.Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    @FXML
    public void ChooseAttack() throws IOException {
        System.out.println("Click on Attack");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Move_attack.fxml"));
        loaderStart.setControllerFactory(c->{
            return new MoveOrAttack(this.CurrPlayer, "Attack", this.Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    @FXML
    public void ChooseDone() throws IOException {
        System.out.println("Click on Done");
        this.CurrPlayer.SendAction();
        String Validation = this.CurrPlayer.ReceiveActionRes();
        System.out.println("Validation " + Validation);
        this.CurrPlayer.ReceiveAllAction();
        this.CurrPlayer.AddTechResource(this.CurrPlayer.getTerritoryMap(),this.CurrPlayer.getPlayerInfo());

        //this.CurrPlayer.ReceiveMapANDShow();
        //the answer could be map or lose game and game end
        String Answer = this.CurrPlayer.ReceiveFromServer();
        if(Answer.contains("Game End!")){
            System.out.println("Received Game End");
            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/End.fxml"));
            loaderStart.setControllerFactory(c->{
                return new End(this.CurrPlayer, Answer);
            });
            Scene scene = new Scene(loaderStart.load());
            this.Window.setScene(scene);
            this.Window.show();

        }
        else if(Answer.contains("Lose Game")){
            System.out.println("Received Lose Game");
            this.CurrPlayer.setAsk(true);
            this.CurrPlayer.setLose(true);
            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Lose.fxml"));
            loaderStart.setControllerFactory(c->{
                return new Lose(this.CurrPlayer, this.Window, Validation);
            });
            Scene scene = new Scene(loaderStart.load());
            this.Window.setScene(scene);
            this.Window.show();
        }
        else {
            System.out.println("Normal Received Map");
            this.CurrPlayer.ContinueReceive(Answer);
            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Validation.fxml"));
            loaderStart.setControllerFactory(c -> {
                return new DoneAction(this.CurrPlayer, Validation, this.Window);
            });
            Scene scene = new Scene(loaderStart.load());
            this.Window.setScene(scene);
            this.Window.show();
        }
    }
}
