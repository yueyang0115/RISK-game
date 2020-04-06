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
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class UpgradeDetail {
    //------------- Evolution 2 --------------//
    //The player has chosen a territory to upgrade, now choose the detail
    @FXML private Label territoryName;
    @FXML private Label techResource;
    @FXML private Label level0;
    @FXML private Label level1;
    @FXML private Label level2;
    @FXML private Label level3;
    @FXML private Label level4;
    @FXML private Label level5;
    @FXML private Label level6;
    @FXML private Button addBtn;
    @FXML private Button OKBtn;
    @FXML private ComboBox startChoose;
    @FXML private ComboBox endChoose;
    @FXML private ComboBox numChoose;
    @FXML private TableView<Upgrade> upgradeTable;


    private Stage Window;

    private PlayerHelper CurrPlayer;
    private Territory curTerritory;

    public UpgradeDetail(PlayerHelper player, String name, Stage Window){
        this.Window = Window;
        this.CurrPlayer = player;
        this.curTerritory = Show.FindTerritory(player.getTerritoryMap(), name);
    }

    //Initialize the table and ComboBoxes
    public void initialize(){
        territoryName.setText(curTerritory.getTerritoryName());
        techResource.setText(String.valueOf(CurrPlayer.getTechResource()));
        level0.setText(String.valueOf(curTerritory.getSoldierLevel(0)));
        level1.setText(String.valueOf(curTerritory.getSoldierLevel(1)));
        level2.setText(String.valueOf(curTerritory.getSoldierLevel(2)));
        level3.setText(String.valueOf(curTerritory.getSoldierLevel(3)));
        level4.setText(String.valueOf(curTerritory.getSoldierLevel(4)));
        level5.setText(String.valueOf(curTerritory.getSoldierLevel(5)));
        level6.setText(String.valueOf(curTerritory.getSoldierLevel(6)));
        ArrayList<Integer> starts = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (curTerritory.getSoldierLevel(i) != 0) {
                starts.add(i);
            }
        }
        ObservableList<Integer> StartChoices = FXCollections.observableArrayList(starts);
        startChoose.setItems(StartChoices);
        endChoose.setItems(FXCollections.observableArrayList());
        numChoose.setItems(FXCollections.observableArrayList());

        //Initialize the upgradeTable

        //Start Level column
        TableColumn<Upgrade, String> fromColumn = new TableColumn<>("Upgrade From");
        fromColumn.setMinWidth(200);
        fromColumn.setStyle( "-fx-alignment: CENTER;");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("prevLevel"));

        //End Level column
        TableColumn<Upgrade, Double> toColumn = new TableColumn<>("Upgrade To");
        toColumn.setMinWidth(200);
        toColumn.setStyle( "-fx-alignment: CENTER;");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("nextLevel"));

        //Number column
        TableColumn<Upgrade, String> numColumn = new TableColumn<>("Number");
        numColumn.setMinWidth(200);
        numColumn.setStyle( "-fx-alignment: CENTER;");
        numColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        upgradeTable.getColumns().addAll(fromColumn, toColumn, numColumn);
    }

    //Click on the start level combobox
    @FXML
    public void startClick() throws IOException {
        int startInput = (int) startChoose.getValue();
        ObservableList<Integer> endChoices = FXCollections.observableArrayList();
        //Set the values of the end level combobox
        for (int i = startInput + 1; i < 7; i++) {
            endChoices.add(i);
        }
        endChoose.setItems(endChoices);
    }

    //Click on the end level combobox
    @FXML
    public void endClick() throws IOException {
        int startInput = (int) startChoose.getValue();
        int endInput = (int) endChoose.getValue();
        Cost myCost = new Cost();
        int perCost = myCost.getCosts(startInput, endInput);
        //How many soldier can upgrade based on the resource
        int timesChoose = CurrPlayer.getTechResource() / perCost;
        //How many soldier can upgrade based on the original soldier number
        int alreadyUpgraded = 0;
        ObservableList<Upgrade> allUpgrades;
        allUpgrades = upgradeTable.getItems();
        for (Upgrade cur : allUpgrades) {
            if (cur.getPrevLevel() == startInput) {
                alreadyUpgraded += cur.getNumber();
            }
        }
        //Choose the min value of the possible number
        timesChoose = Math.min(timesChoose, curTerritory.getSoldierLevel(startInput) - alreadyUpgraded);
        ObservableList<Integer> timeChoices = FXCollections.observableArrayList();
        //Set the values of the number combobox
        for (int i = 1; i < timesChoose + 1; i++) {
            timeChoices.add(i);
        }
        numChoose.setItems(timeChoices);
    }

    //Click on the add button
    @FXML
    public void AddClick() throws IOException {
        int startInput = (int) startChoose.getValue();
        int endInput = (int) endChoose.getValue();
        int numInput = (int) numChoose.getValue();
        Cost myCost = new Cost();
        int totalCost = myCost.getCosts(startInput, endInput) * numInput;
        //Create the according upgrade
        Upgrade cur = new Upgrade();
        cur.setTerritoryName(curTerritory.getTerritoryName());
        cur.setOwner(curTerritory.getOwner());
        cur.setPrevLevel(startInput);
        cur.setNextLevel(endInput);
        cur.setNumber((int) numChoose.getValue());
        //Add the upgrade to the tableView
        upgradeTable.getItems().add(cur);
        startChoose.setValue(null);
        endChoose.setValue(null);
        numChoose.setValue(null);
        //Decrease the resource
        CurrPlayer.setTechResource(CurrPlayer.getTechResource() - totalCost);
        techResource.setText(String.valueOf(CurrPlayer.getTechResource()));
        //Add the current upgrade
        this.CurrPlayer.setUpgradeAction(cur);
    }

    //Click on the ok (back) button
    @FXML
    public void OKClick() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Map.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Map(this.CurrPlayer, Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }
}
