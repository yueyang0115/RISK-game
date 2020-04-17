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
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static javafx.scene.text.FontPosture.ITALIC;
import static javafx.scene.text.FontWeight.BOLD;

public class Map{
    //------------- Evolution 2 --------------//
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

    @FXML private TreeView<String> Detail;
    @FXML private Label Prompt;

    @FXML private ImageView Figure;

    private Stage Window;
    private Boolean firstTime;

    private PlayerHelper CurrPlayer;
    private HashMap<String, Button> ButtonMap;
    private HashMap<Integer, ArrayList<Territory>> TerrMap;
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
    public Map(PlayerHelper player, Stage Window, Boolean first){
        this.Window = Window;
        this.CurrPlayer = player;
        this.TerrMap = player.getTerritoryMap();
        this.firstTime = first;
    }

    private void InitFigure(){
        int ID = this.CurrPlayer.getPlayerInfo().getKey();
        Image player = new Image(getClass().getResourceAsStream("/Player_Photo/player" + ID + ".png"));
        this.Figure.setImage(player);
    }

    public void initialize(){
        InitFigure();

        InitButtonMap();
        new Graph().showMap(this.CurrPlayer.getTerritoryMap(), this.CurrPlayer.getPlayerInfo(), this.ButtonMap);

        InitTerritoryDetail();

        InitActionDetail();

        ColorID PlayerColor = new ColorID();
        String PlayerName = PlayerColor.getPlayerColor(this.CurrPlayer.getPlayerInfo().getKey());
        this.Prompt.setText("Your territories are in " + PlayerName + " Color, please choose an action");
        this.Prompt.setFont(Font.font("Arial", BOLD, ITALIC, 18));
        if (firstTime) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> {
                try {
                    showChat();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Exception "+ e);
                }
            });
            delay.play();
        }
    }
    private void InitActionDetail(){
        ArrayList<Action> move = this.CurrPlayer.getMoveAction();
        TreeItem<String> rootItem = new TreeItem<String> ("Move Actions");
        rootItem.setExpanded(true);
        for (int i = 0; i < move.size(); i++) {
            Action Curr = move.get(i);
            HashMap<Integer, Integer> MoveSoldier = Curr.getSoldiers();
            StringBuilder Display = new StringBuilder();
            Display.append(Curr.getSrc().getTerritoryName()).append(" -> ").append(Curr.getDst().getTerritoryName()).append(": ");
            for(int j = 0; j < 7; j++) {
                int Number = MoveSoldier.get(j);
                if(Number != 0){
                    Display.append("Level ").append(j).append(" *").append(Number);
                }
            }
            String ShowText = Display.toString();
            TreeItem<String> item = new TreeItem<String> (ShowText);
            rootItem.getChildren().add(item);
        }
        this.Detail.setRoot(rootItem);
    }
    private void InitTerritoryDetail(){
        for(int i = 0; i < this.ButtonMap.size(); i++){
            String SearchBase = "A";
            int curr = SearchBase.charAt(0) + i;
            StringBuilder Search = new StringBuilder();
            Search.append((char)curr);
            //System.out.println("[DEBUG] Which Territory: " + Search.toString());
            Button CurrentBtn = this.ButtonMap.get(Search.toString());
            Tooltip TerrDetail = new Tooltip();
            Territory CurrentClicked =  Show.FindTerritory(this.TerrMap, Search.toString());
            ShowToolTip(CurrentClicked, TerrDetail);
            CurrentBtn.setTooltip(TerrDetail);
        }

    }
    public void ShowToolTip(Territory CurrentClicked, Tooltip TerrDetail){
        String ShowLabel = Show.ComposeString(CurrentClicked);
        TerrDetail.setText(ShowLabel);
        TerrDetail.setFont(new Font("Arial", 12));
    }





    //-----------------------------------------------//
    //these three buttons are related to actions
    //and if click on each of them we jump into different page to finish each actions's input information
    @FXML
    public void Upgrading() throws IOException {
        System.out.println("Click on Upgrade");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Views/UpgradeChoose.fxml"));
        loaderStart.setControllerFactory(c->{
            return new UpgradeChoose(this.CurrPlayer, this.Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    @FXML
    public void ChooseMove() throws IOException {
        System.out.println("Click on Move");
        ShowView.ShowMoveAttack(this.CurrPlayer, "Move", this.Window);
    }

    @FXML
    public void ChooseAlliance() throws IOException {
        System.out.println("Click on Alliance");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Views/Alliance.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Alliance(this.CurrPlayer, this.Window);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    @FXML
    public void ChooseAttack() throws IOException {
        System.out.println("Click on Attack");
        ShowView.ShowMoveAttack(this.CurrPlayer, "Attack", this.Window);
    }

    @FXML
    public void ChooseDone() throws IOException {
        //if it choose done, receive this turn's result information
        System.out.println("Click on Done in Map");
        ShowView show = new ShowView();
        this.CurrPlayer.SendAction();
        String Validation = this.CurrPlayer.ReceiveActionRes();
        System.out.println("Validation " + Validation);
        this.CurrPlayer.ReceiveAllAction();

        this.CurrPlayer.AddTechResource(this.CurrPlayer.getTerritoryMap(),this.CurrPlayer.getPlayerInfo());
        //the answer could be map or lose game and game end
        String Answer = this.CurrPlayer.ReceiveFromServer();
        //check whether the received string is game end or lose game or normal map
        //display different page with different received string content
        if(Answer.contains("Game End!")){
            System.out.println("Received Game End");
            ShowView.ShowEndVIew(Answer, this.CurrPlayer, this.Window);
        }
        else if(Answer.contains("Lose Game")){
            System.out.println("Received Lose Game");
            this.CurrPlayer.setAsk(true);
            this.CurrPlayer.setLose(true);
            ShowView.ShowLoseView(Validation, this.CurrPlayer, this.Window);
        }
        else {
            System.out.println("Normal Received Map");
            this.CurrPlayer.ContinueReceive(Answer);
            ShowView.ShowDoneView(Validation, this.CurrPlayer, this.Window);
        }
    }

    public void showChat() throws IOException {
        ColorID cid = new ColorID();
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Views/ChatRoom.fxml"));
        Stage newWindow = new Stage();
        loaderStart.setControllerFactory(c->{
            return new ChatRoom(cid.getPlayerColor(CurrPlayer.getID()), newWindow);
        });
        Scene scene = new Scene(loaderStart.load());
        newWindow.setScene(scene);
        newWindow.show();
    }


}
