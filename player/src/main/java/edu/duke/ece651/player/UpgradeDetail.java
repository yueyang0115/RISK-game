package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    private void ShowToNum(PlayerHelper CurrPlayer, Upgrade Act, TableView<Upgrade> upTable, int Remain, Label tech) throws IOException {
        newWindow = new Stage();
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Views/UpTo_Num.fxml"));
        loaderStart.setControllerFactory(c -> {
            return new UpTo(CurrPlayer, Act, newWindow, upTable, Remain, tech);
        });
        Scene scene = new Scene(loaderStart.load());
        newWindow.setScene(scene);
        newWindow.show();
    }


    private int SearchUpgraded(Territory Current, int StartLevel){
        ArrayList<Upgrade> UpgradedList = this.CurrPlayer.getUpgradeAction();
        String TerrName = Current.getTerritoryName();
        int AlreadyEntered = 0;
        for(int i = 0; i < UpgradedList.size(); i++){
            Upgrade TempAction = UpgradedList.get(i);
            if(TempAction.getTerritoryName().equals(TerrName) && TempAction.getPrevLevel() == StartLevel){
                AlreadyEntered += TempAction.getNumber();
            }
        }
        return AlreadyEntered;
    }

    public void UpFromBtn(Territory curTerritory, int StartLevel, PlayerHelper CurrPlayer, TableView<Upgrade> upgradeTable, Label techResource) throws IOException {
        Upgrade Act = new Upgrade();
        Act.setTerritoryName(curTerritory.getTerritoryName());
        Act.setOwner(curTerritory.getOwner());
        Act.setPrevLevel(StartLevel);
        int CurrentTotal = curTerritory.getSoldierLevel(StartLevel);
        int Remain = SearchTableView(StartLevel, CurrentTotal);
        if(Remain > 0) {
            if((Remain - SearchUpgraded(curTerritory, StartLevel)) > 0){
                ShowToNum(CurrPlayer, Act, upgradeTable, Remain, techResource);
                return;
            }
            ShowView.promptError("Already Upgraded All The Units in This Level");
        }
        else {
            ShowView.promptError("Empty to Upgrade");
        }
    }
    @FXML
    public void Up0() throws IOException {
        UpFromBtn(curTerritory, 0, CurrPlayer, upgradeTable, techResource);
    }

    @FXML
    public void Up1() throws IOException {
        UpFromBtn(curTerritory, 1, CurrPlayer, upgradeTable, techResource);
    }
    @FXML
    public void Up2() throws IOException {
        UpFromBtn(curTerritory, 2, CurrPlayer, upgradeTable, techResource);
    }
    @FXML
    public void Up3() throws IOException {
        UpFromBtn(curTerritory, 3, CurrPlayer, upgradeTable, techResource);
    }
    @FXML
    public void Up4() throws IOException {
        UpFromBtn(curTerritory, 4, CurrPlayer, upgradeTable, techResource);
    }
    @FXML
    public void Up5() throws IOException {
        UpFromBtn(curTerritory, 5, CurrPlayer, upgradeTable, techResource);
    }
    @FXML
    public void Up6() throws IOException {
        ShowView.promptError("Highest Level. No More Upgrade");
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

    @FXML
    public void SubmitUpgrade() throws IOException {
        //Traverse the current TableView Value
        //Extract each upgrade Action
        //Add them into Player's UpgradeActionList
        ObservableList<Upgrade> UpgradeList= upgradeTable.getItems();
        for(Upgrade SingleUp: UpgradeList){
            this.CurrPlayer.setUpgradeAction(SingleUp);
        }
        CurrPlayer.setTechResource(Integer.parseInt(techResource.getText()));
        //Return to MainPage for further actions
        ShowView.MainPageView(this.CurrPlayer, this.Window, false);
    }
}
