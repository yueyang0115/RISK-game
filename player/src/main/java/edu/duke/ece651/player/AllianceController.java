package edu.duke.ece651.player;
import edu.duke.ece651.shared.*;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class AllianceController {
    //------------- Evolution 2 --------------//
    @FXML private Button submit;
    @FXML private Button button0;
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private Button button3;
    @FXML private Button button4;

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
    @FXML private Label Choose;
    @FXML private Label error;


    @FXML private ImageView image0;
    @FXML private ImageView image1;
    @FXML private ImageView image2;
    @FXML private ImageView image3;
    @FXML private ImageView image4;

    private Stage Window;
    private PlayerHelper CurrPlayer;
    private HashMap<String, Button> ButtonMap;
    private HashMap<Integer, Button> ChooseBtn;
    private HashMap<Integer, ImageView> ImageViewMap;
    private HashMap<Integer, ArrayList<Territory>> TerrMap;
    private int clickResult;

    public AllianceController(PlayerHelper player, Stage Window){
        this.Window = Window;
        this.CurrPlayer = player;
        this.TerrMap = player.getTerritoryMap();
        this.clickResult = -1;
    }

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
    private void InitialChooseBtn() {
        ChooseBtn = new HashMap<>();
        ChooseBtn.put(0, button0);
        ChooseBtn.put(1, button1);
        ChooseBtn.put(2, button2);
        ChooseBtn.put(3, button3);
        ChooseBtn.put(4, button4);
    }

    private void InitialImageView(){
        ImageViewMap = new HashMap<>();
        ImageViewMap.put(0, image0);
        ImageViewMap.put(1, image1);
        ImageViewMap.put(2, image2);
        ImageViewMap.put(3, image3);
        ImageViewMap.put(4, image4);
    }

    public void initialize(){
        InitialChooseBtn();
        InitButtonMap();
        InitialImageView();
        new Graph().showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo(), this.ButtonMap);
        //init tooltip with territory information
        SharedMethod.InitTerritoryDetail(this.ButtonMap, this.TerrMap);
        ColorID PlayerColor = new ColorID();
        String PlayerName = PlayerColor.getPlayerColor(this.CurrPlayer.getPlayerInfo().getKey());
        this.Prompt.setText("You territories are in " + PlayerName + " color");
        //Get number of player in game
        int playerInGame = 0;
        for (java.util.Map.Entry<Integer, ArrayList<Territory>> entry : CurrPlayer.getTerritoryMap().entrySet()) {
            if (entry.getValue().size() != 0) { playerInGame++; }
        }
        //Check if the player already has an ally
        if (CurrPlayer.getMyAlly() != -1) {
            disableBtns("You already have an ally!");
            return;
        }
        //Check the number of players in game
        if (playerInGame <= 2) {
            disableBtns("Need at least three players currently in game!");
            return;
        }
        for (int i = 0; i < this.ChooseBtn.size(); i++) {
            Button curBtn = ChooseBtn.get(i);
            if (i == CurrPlayer.getID() || i > CurrPlayer.getPlayerNum() -1 || CurrPlayer.getTerritoryMap().get(i).size() == 0) {
                curBtn.setDisable(true);
            }
            else {
                Image Photo = new Image(getClass().getResourceAsStream("/Player_Photo/player" + i + ".png"));
                ImageViewMap.get(i).setImage(Photo);
            }
        }
    }

    private void disableBtns(String text) {
        error.setText(text);
        submit.setText("OK");
        for (int i = 0; i < this.ChooseBtn.size(); i++) {
            Button curBtn = ChooseBtn.get(i);
            curBtn.setDisable(true);
        }
    }

    private void ChooseHelper(int allyID) {
        ColorID PlayerColor = new ColorID();
        String color = PlayerColor.getPlayerColor(allyID);
        this.Choose.setText("You chose the player with " + color + " territories");
    }

    @FXML
    public void Click0() {
        clickResult = 0;
        ChooseHelper(clickResult);
    }
    @FXML
    public void Click1() {
        clickResult = 1;
        ChooseHelper(clickResult);
    }
    @FXML
    public void Click2() {
        clickResult = 2;
        ChooseHelper(clickResult);
    }
    @FXML
    public void Click3() {
        clickResult = 3;
        ChooseHelper(clickResult);
    }
    @FXML
    public void Click4() {
        clickResult = 4;
        ChooseHelper(clickResult);
    }
    @FXML
    public void ClickSubmit() throws IOException {
        System.out.println("Click on Done");
        Alliance a = CurrPlayer.getAllianceAction();
        a.setOwner(CurrPlayer.getID());
        a.setAlly(clickResult);
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Views/Map.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Map(this.CurrPlayer, Window, false);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }
}
