package edu.duke.ece651.player;
import edu.duke.ece651.shared.MyFormatter;
import edu.duke.ece651.shared.Territory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerTest extends ApplicationTest{
    private PlayerHelper player;
    private Player TestStart;
    private Stage Window;
    private HashMap<String, Button> ButtonMap;

    private Button ButtonA;
    private Button ButtonB;
    private Button ButtonC;
    private Button ButtonD;
    private Button ButtonE;
    private Button ButtonF;
    private Button ButtonG;
    private Button ButtonH;
    private Button ButtonI;
    private Button ButtonJ;
    private Button ButtonK;
    private Button ButtonL;

    private TreeView<String> Detail;
    private HashMap<Integer, ArrayList<Territory>> TerrMap;


    @Override
    public void start(Stage stage) throws Exception {
        this.player = new PlayerHelper();
        Pair<Integer,String> MyInfo = new Pair<>(0, "Green");
        player.setPlayerInfo(MyInfo);
        player.setID(0);
        TestStart = new Player();
        Window = stage;


    }
    @Test
    public void Test_Button() throws IOException {
        ButtonA = new Button();
        ButtonB = new Button();
        ButtonC = new Button();
        ButtonD = new Button();
        ButtonE = new Button();
        ButtonF = new Button();
        ButtonG = new Button();
        ButtonH = new Button();
        ButtonI = new Button();
        ButtonJ = new Button();
        ButtonK = new Button();
        ButtonL = new Button();
        InitButtonMap();
        TreeView<String> Detail = new TreeView<>();
        Test_InitTerritory();
        SharedMethod.InitTerritoryDetail(this.ButtonMap, this.TerrMap);
    }
    private void Test_InitTerritory(){
        TerrMap = new HashMap<>();
        String TestMapStr = "{'player_0':[ {'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'D'}], 'territoryName':'A'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'L'}], 'territoryName':'B'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'B'},{'neighbor_2':'L'},{'neighbor_3':'J'},{'neighbor_4':'F'},{'neighbor_5':'D'}], 'territoryName':'C'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'A'},{'neighbor_1':'C'},{'neighbor_2':'F'}, {'neighbor_3':'E'}], 'territoryName':'D'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'F'}, {'neighbor_2':'G'}], 'territoryName':'E'},{'owner':'player_0', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'D'},{'neighbor_1':'C'},{'neighbor_2':'J'},{'neighbor_3':'I'},{'neighbor_4':'G'},{'neighbor_5':'E'}], 'territoryName':'F'}], 'player_1':[ {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'E'},{'neighbor_1':'F'},{'neighbor_2':'I'},{'neighbor_3':'H'}], 'territoryName':'G'}, {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'G'},{'neighbor_1':'I'}], 'territoryName':'H'}, {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'F'},{'neighbor_1':'J'},{'neighbor_2':'K'},{'neighbor_3':'H'},{'neighbor_4':'G'}], 'territoryName':'I'}, {'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'C'},{'neighbor_1':'L'},{'neighbor_2':'K'},{'neighbor_3':'I'},{'neighbor_4':'F'}], 'territoryName':'J'},{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'I'},{'neighbor_1':'J'},{'neighbor_2':'L'}], 'territoryName':'K'},{'owner':'player_1', 'soldiers':[{'level_0':'3'},{'level_1':'0'},{'level_2':'0'},{'level_3':'0'},{'level_4':'0'},{'level_5':'0'},{'level_6':'0'}], 'neighbor':[{'neighbor_0':'B'},{'neighbor_1':'C'},{'neighbor_2':'J'},{'neighbor_3':'K'}], 'territoryName':'L'}] }";
        MyFormatter Change = new MyFormatter(2);
        Change.MapParse(TerrMap, TestMapStr);
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
}
