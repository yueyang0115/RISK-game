package edu.duke.ece651.player;

import edu.duke.ece651.shared.Cost;
import edu.duke.ece651.shared.Territory;
import edu.duke.ece651.shared.Upgrade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.math3.analysis.function.Cos;

import java.io.IOException;
import java.util.ArrayList;

public class UpTo {
    private Upgrade Act;
    private Stage myWindow;
    private PlayerHelper CurrentPlayer;
    private TableView<Upgrade> Table;
    private int Remain;
    @FXML private ImageView CurrentLevel;
    @FXML private Label CurrentNumber;
    @FXML private Button Submit;
    @FXML private ComboBox<Integer> Number;
    @FXML private ComboBox<Integer> LevelTo;
    private Label TechResource;
    private int CurrentTech;


    public UpTo(PlayerHelper Curr, Upgrade UpAction, Stage NewWindow, TableView<Upgrade> table, int remain, Label Tech){
        System.out.println("[DEBUG] Remain = " + remain);
        Act = UpAction;
        myWindow = NewWindow;
        this.myWindow.initModality(Modality.APPLICATION_MODAL);
        CurrentPlayer = Curr;
        this.Table = table;
        this.Remain = remain;
        this.TechResource = Tech;
        this.CurrentTech = Integer.parseInt(TechResource.getText());
    }
    public void initialize(){
        int ID = this.CurrentPlayer.getPlayerInfo().getKey();
        int Start = this.Act.getPrevLevel();
        //Show the player's image in the window
        Image Soldier = new Image(getClass().getResourceAsStream("/Player" + ID + "/level" + Start + ".png"));
        CurrentLevel.setImage(Soldier);
        CurrentNumber.setText("* " + this.Remain);
        CurrentNumber.setFont(new Font("Arial", 24 ));
        InitLevelTo(Start);
    }

    private void InitNumber(){
        int ReadyLevelTo = LevelTo.getValue();
        ArrayList<Integer> Options = SetRangeNumber(this.Remain,ReadyLevelTo);
        ObservableList<Integer> NumChoices = FXCollections.observableArrayList(Options);
        Number.setItems(NumChoices);
    }

    private ArrayList<Integer> SetRangeNumber(int RangeEnd, int ReadyLevelTo){
        Cost Calculator = new Cost();
        ArrayList<Integer> starts = new ArrayList<>();
        for (int i = 1; i <= RangeEnd; i++) {
            int ReadyCost = Calculator.getCosts(this.Act.getPrevLevel(),ReadyLevelTo) * i;
            //Calculate how many technology resources will be used for upgrade
            //From StartLevel to the current ComboBox LevelTo
            if(ReadyCost <= this.CurrentTech) {
                starts.add(i);
            }
        }
        return starts;
    }


    //Init the LevelTo ComboBox value, from StartLevel + 1 to HighestLevel 6
    private void InitLevelTo(int StartLevel){
        ArrayList<Integer> OptionsLevel = SetRange(StartLevel + 1,6);
        ObservableList<Integer> LevelChoices = FXCollections.observableArrayList(OptionsLevel);
        LevelTo.setItems(LevelChoices);
    }

    //Called by InitLevelTo, create an ArrayList to add in the ObservableList
    private ArrayList<Integer> SetRange(int RangeBegin, int RangeEnd){
        ArrayList<Integer> starts = new ArrayList<>();
        for (int i = RangeBegin; i <= RangeEnd; i++) {
                starts.add(i);
        }
        return starts;
    }

    //Only when the LevelTo value is determined
    //Init the value of Number ComboBox
    @FXML
    public void EndLevelTo(){
        InitNumber();
    }

    //Record the Upgrade Action Detail
    //Update TechResource Label
    //Update TableView Content
    @FXML
    public void SubmitBtn(){
        System.out.println("Submit");
        int NumValue = this.Number.getValue();
        int LevelEnd = this.LevelTo.getValue();
        this.Act.setNextLevel(LevelEnd);
        this.Act.setNumber(NumValue);
        this.Table.getItems().add(this.Act);
        int OneCost = new Cost().getCosts(this.Act.getPrevLevel(), LevelEnd);
        int TotalCost = OneCost * NumValue;
        int Origin = Integer.parseInt(this.TechResource.getText());
        this.TechResource.setText(String.valueOf(Origin - TotalCost));
        this.myWindow.close();
    }

}
