package edu.duke.ece651.player;

import edu.duke.ece651.shared.Cost;
import edu.duke.ece651.shared.Territory;
import edu.duke.ece651.shared.Upgrade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
    @FXML private ChoiceBox<Integer> Number;
    @FXML private ChoiceBox<Integer> LevelTo;
    private Label TechResource;


    public UpTo(PlayerHelper Curr, Upgrade UpAction, Stage NewWindow, TableView<Upgrade> table, int remain, Label Tech){
        System.out.println("[DEBUG] Remain = " + remain);
        Act = UpAction;
        myWindow = NewWindow;
        this.myWindow.initModality(Modality.APPLICATION_MODAL);
        CurrentPlayer = Curr;
        this.Table = table;
        this.Remain = remain;
        this.TechResource = Tech;
    }
    public void initialize(){
        int ID = this.CurrentPlayer.getPlayerInfo().getKey();
        int Start = this.Act.getPrevLevel();
        Image Soldier = new Image(getClass().getResourceAsStream("/Player" + ID + "/level" + Start + ".png"));
        CurrentLevel.setImage(Soldier);
        CurrentNumber.setText("* " + this.Remain);
        CurrentNumber.setFont(new Font("Arial", 24 ));
        InitNumber();
        InitLevelTo(Start);
    }

    private void InitNumber(){
        ArrayList<Integer> Options = SetRangeNumber(1, this.Remain);
        ObservableList<Integer> NumChoices = FXCollections.observableArrayList(Options);
        Number.setItems(NumChoices);
    }
    private void InitLevelTo(int StartLevel){
        ArrayList<Integer> OptionsLevel = SetRangeNumber(StartLevel + 1,6);
        ObservableList<Integer> LevelChoices = FXCollections.observableArrayList(OptionsLevel);
        LevelTo.setItems(LevelChoices);
    }

    private ArrayList<Integer> SetRangeNumber(int RangeBegin, int RangeEnd){
        ArrayList<Integer> starts = new ArrayList<>();
        for (int i = RangeBegin; i <= RangeEnd; i++) {
                starts.add(i);
        }
        return starts;
    }

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
