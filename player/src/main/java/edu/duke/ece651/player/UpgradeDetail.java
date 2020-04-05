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

    @FXML
    public void startClick() throws IOException {
        int startInput = (int) startChoose.getValue();
        ObservableList<Integer> endChoices = FXCollections.observableArrayList();
        for (int i = startInput + 1; i < 7; i++) {
            endChoices.add(i);
        }
        endChoose.setItems(endChoices);
    }

    @FXML
    public void endClick() throws IOException {
        int startInput = (int) startChoose.getValue();
        int endInput = (int) endChoose.getValue();
        Cost myCost = new Cost();
        int perCost = myCost.getCosts(startInput, endInput);
        int timesChoose = CurrPlayer.getTechResource() / perCost;
        ObservableList<Integer> timeChoices = FXCollections.observableArrayList();
        for (int i = 1; i < timesChoose; i++) {
            timeChoices.add(i);
        }
        numChoose.setItems(timeChoices);
    }

    @FXML
    public void AddClick() throws IOException {
        int startInput = (int) startChoose.getValue();
        int endInput = (int) endChoose.getValue();
        int numInput = (int) numChoose.getValue();
        Cost myCost = new Cost();
        int totalCost = myCost.getCosts(startInput, endInput) * numInput;
        Upgrade cur = new Upgrade();
        cur.setTerritoryName(curTerritory.getTerritoryName());
        cur.setOwner(curTerritory.getOwner());
        cur.setPrevLevel(startInput);
        cur.setNextLevel(endInput);
        cur.setNumber((int) numChoose.getValue());
        upgradeTable.getItems().add(cur);
        startChoose.setValue(null);
        endChoose.setValue(null);
        numChoose.setValue(null);
        CurrPlayer.setTechResource(CurrPlayer.getTechResource() - totalCost);
        techResource.setText(String.valueOf(CurrPlayer.getTechResource()));
        this.CurrPlayer.setUpgradeAction(cur);
    }

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
