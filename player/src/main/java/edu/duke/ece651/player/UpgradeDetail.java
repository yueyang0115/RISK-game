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
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UpgradeDetail {
    @FXML private Label territoryName;
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

    private Stage Window;

    private PlayerHelper CurrPlayer;
    private Territory curTerritory;

    public UpgradeDetail(PlayerHelper player, String name, Stage Window){
        this.Window = Window;
        this.CurrPlayer = player;
        this.curTerritory = FindTerritory(player.getTerritoryMap(), name);
    }

    public void initialize(){
        territoryName.setText(curTerritory.getTerritoryName());
        level0.setText(String.valueOf(curTerritory.getSoldierLevel(0)));
        level1.setText(String.valueOf(curTerritory.getSoldierLevel(1)));
        level2.setText(String.valueOf(curTerritory.getSoldierLevel(2)));
        level3.setText(String.valueOf(curTerritory.getSoldierLevel(3)));
        level4.setText(String.valueOf(curTerritory.getSoldierLevel(4)));
        level5.setText(String.valueOf(curTerritory.getSoldierLevel(5)));
        level6.setText(String.valueOf(curTerritory.getSoldierLevel(6)));
        ObservableList<Integer> LevelChoices = FXCollections.observableArrayList(0,1,2,3,4,5,6);
        startChoose.setItems(LevelChoices);
        endChoose.setItems(LevelChoices);
        ObservableList<Integer> numChoices = FXCollections.observableArrayList();
        for (int i = 1; i < 100; i++) {
            numChoices.add(i);
        }
        numChoose.setItems(numChoices);
        //TODO:initialize table
    }

    @FXML
    public void AddClick() throws IOException {
        //TODO:add check!
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
}
