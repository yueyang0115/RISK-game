package edu.duke.ece651.player;


import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.Territory;
import edu.duke.ece651.shared.Upgrade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SoldierChoose {

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

    private PlayerHelper CurrPlayer;
    private HashMap<Integer, ArrayList<Territory>> TerrMap;
    private Stage Window;
    private Upgrade UpgradeAction;

    public SoldierChoose(PlayerHelper current, String TerrName, Stage Window){
        this.CurrPlayer = current;
        UpgradeAction = new Upgrade();
        UpgradeAction.setTerritoryName(TerrName);
        this.Window = Window;
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
        setUpLabel();
        setUpImageView();
        int ID = this.CurrPlayer.getPlayerInfo().getKey();
        Image player = new Image(getClass().getResourceAsStream("/Player_Photo/player" + ID + ".png"));
        PlayerImg.setImage(player);
        setImage(ID);
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
    @FXML
    private void Upgrade0(){
        System.out.println("Click upgrade from 0");



    }
    private void Upgrade1(){

    }
    private void Upgrade2(){

    }
    private void Upgrade3(){

    }
    private void Upgrade4(){

    }
    private void Upgrade5(){

    }
    private void Upgrade6(){

    }


}
