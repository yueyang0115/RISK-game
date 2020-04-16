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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class UpgradeDetail {
    //------------- Evolution 2 --------------//
    //The player has chosen a territory to upgrade, now choose the detail
    @FXML private Label territoryName;
    @FXML private Label techResource;

    @FXML private TableView<Upgrade> upgradeTable;
    @FXML private ImageView imageView0;
    @FXML private ImageView imageView1;
    @FXML private ImageView imageView2;
    @FXML private ImageView imageView3;
    @FXML private ImageView imageView4;
    @FXML private ImageView imageView5;
    @FXML private ImageView imageView6;
    @FXML private Label l0;
    @FXML private Label l1;
    @FXML private Label l2;
    @FXML private Label l3;
    @FXML private Label l4;
    @FXML private Label l5;
    @FXML private Label l6;
    @FXML private ImageView PlayerImg;


    private HashMap<Integer, Label> LabelMap;
    private HashMap<Integer, ImageView> ImageViewMap;

    private Stage newWindow;
    //private Upgrade UpgradeAction;
    private Stage Window;

    private PlayerHelper CurrPlayer;
    private Territory curTerritory;
    private String TerrName;
    public UpgradeDetail(PlayerHelper player, String name, Stage Window){
        this.TerrName = name;
        this.Window = Window;
        this.CurrPlayer = player;
        this.curTerritory = Show.FindTerritory(player.getTerritoryMap(), name);

    }

    private void setUpLabel(){
        LabelMap = new HashMap<>();
        LabelMap.put(0,l0);
        LabelMap.put(1,l1);
        LabelMap.put(2,l2);
        LabelMap.put(3,l3);
        LabelMap.put(4,l4);
        LabelMap.put(5,l5);
        LabelMap.put(6,l6);
    }

    private void setUpImageView(){
        ImageViewMap = new HashMap<>();
        ImageViewMap.put(0,imageView0);
        ImageViewMap.put(1,imageView1);
        ImageViewMap.put(2,imageView2);
        ImageViewMap.put(3,imageView3);
        ImageViewMap.put(4,imageView4);
        ImageViewMap.put(5,imageView5);
        ImageViewMap.put(6,imageView6);
    }

    public void initialize(){
        territoryName.setText(curTerritory.getTerritoryName());
        System.out.println(curTerritory.getTerritoryName());
        techResource.setText(String.valueOf(CurrPlayer.getTechResource()));
        System.out.println(CurrPlayer.getTechResource());
        setUpLabel();
        setUpImageView();
        int ID = this.CurrPlayer.getPlayerInfo().getKey();
        Image player = new Image(getClass().getResourceAsStream("/Player_Photo/player" + ID + ".png"));
        PlayerImg.setImage(player);
        setImage(ID);
        InitTableView();
    }

    private void setImage(int ID){
        String fileName = "/Player" + ID + "/Name.txt";
        InputStream input = getClass().getResourceAsStream(fileName);
        Scanner scanner = new Scanner(input);
        int i = 0;
        while (scanner.hasNext()){
            Label Curr = this.LabelMap.get(i);
            String str = scanner.nextLine();
            System.out.println(str);
            Curr.setText("Level " + i + ": " + str);
            Curr.setFont(new Font(13));
            Image img_level = new Image(getClass().getResourceAsStream("/Player" + ID + "/level" + i + ".png"));
            ImageView CurrView = ImageViewMap.get(i);
            CurrView.setImage(img_level);
            i++;
        }
    }

    private int SearchTableView(int FromLevel, int CurrentTotal){
        int alreadyUpgraded = 0;
        ObservableList<Upgrade> allUpgrades;
        allUpgrades = upgradeTable.getItems();
        for (Upgrade cur : allUpgrades) {
            if (cur.getPrevLevel() == FromLevel) {
                alreadyUpgraded += cur.getNumber();
            }
        }
        System.out.println("[DEBUG] IN TableView = " + alreadyUpgraded);
        return CurrentTotal - alreadyUpgraded;
    }

    @FXML
    public void Up0() throws IOException {
        Upgrade Act = new Upgrade();
        Act.setTerritoryName(this.TerrName);
        Act.setOwner(this.curTerritory.getOwner());
        Act.setPrevLevel(0);
        int CurrentTotal = this.curTerritory.getSoldierLevel(0);
        int Remain = SearchTableView(0,CurrentTotal);
        if(Remain > 0) {
            newWindow = new Stage();
            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/UpTo_Num.fxml"));
            loaderStart.setControllerFactory(c -> {
                return new UpTo(this.CurrPlayer, Act, newWindow, this.upgradeTable, Remain, techResource);
            });
            Scene scene = new Scene(loaderStart.load());
            newWindow.setScene(scene);
            newWindow.show();
        }
        else {
            System.out.println("Left 0 can upgrade");
        }
    }
    @FXML
    public void Up1(){

    }
    @FXML
    public void Up2(){

    }
    @FXML
    public void Up3(){

    }
    @FXML
    public void Up4(){

    }
    @FXML
    public void Up5(){

    }
    @FXML
    public void Up6(){

    }
    private void InitTableView(){
        //Initialize the upgradeTable
        //Start Level column
        TableColumn<Upgrade, String> fromColumn = new TableColumn<>("From");
        fromColumn.setMinWidth(100);
        fromColumn.setStyle( "-fx-alignment: CENTER; -fx-background-color: #f0f8ff;");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("prevLevel"));

        //End Level column
        TableColumn<Upgrade, Double> toColumn = new TableColumn<>("To");
        toColumn.setMinWidth(100);
        toColumn.setStyle( "-fx-alignment: CENTER; -fx-background-color: #f0ffff;");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("nextLevel"));

        //Number column
        TableColumn<Upgrade, String> numColumn = new TableColumn<>("Number");
        numColumn.setMinWidth(100);
        numColumn.setStyle( "-fx-alignment: CENTER; -fx-background-color: #fff8dc");
        numColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        upgradeTable.getColumns().addAll(fromColumn, toColumn, numColumn);
    }
    //Initialize the table and ComboBoxes
    /*public void initialize(){

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
*/
    //Click on the start level combobox
    /*@FXML
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
            return new Map(this.CurrPlayer, Window, false);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }*/
}
