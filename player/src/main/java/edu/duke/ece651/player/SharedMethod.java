package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;

public class SharedMethod {
    public static String getAllianceInfo(PlayerHelper CurrPlayer){
        ColorID ConvertColor = new ColorID();
        int AllyID = CurrPlayer.getMyAlly();
        String Text = "Empty";
        if(AllyID != -1){
            String AllyName = ConvertColor.getPlayerColor(AllyID);
            String OwnerName = CurrPlayer.getPlayerInfo().getValue();
            Text = OwnerName + " ~ " + AllyName;
        }
        return Text;
    }

    public static void InitTerritoryDetail(HashMap<String, Button> ButtonMap, HashMap<Integer, ArrayList<Territory>> TerrMap){
        for(int i = 0; i < ButtonMap.size(); i++){
            String SearchBase = "A";
            int curr = SearchBase.charAt(0) + i;
            StringBuilder Search = new StringBuilder();
            Search.append((char)curr);
            Button CurrentBtn = ButtonMap.get(Search.toString());
            Tooltip TerrDetail = new Tooltip();
            Territory CurrentClicked =  Show.FindTerritory(TerrMap, Search.toString());
            ShowToolTip(CurrentClicked, TerrDetail);
            CurrentBtn.setTooltip(TerrDetail);
        }
    }

    private static void ShowToolTip(Territory CurrentClicked, Tooltip TerrDetail){
        String ShowLabel = Show.ComposeString(CurrentClicked);
        TerrDetail.setText(ShowLabel);
        TerrDetail.setFont(new Font("Arial", 12));
    }

    public static void InitFigure(PlayerHelper CurrPlayer, ImageView Figure){
        int ID = CurrPlayer.getPlayerInfo().getKey();
        Image player = new Image(SharedMethod.class.getResourceAsStream("/Player_Photo/player" + ID + ".png"));
        Figure.setImage(player);
    }


    public static void InitActionDetail(PlayerHelper CurrPlayer, TreeView<String> Detail){
        TreeItem<String> rootItem = new TreeItem<String> ("Actions List");
        rootItem.setExpanded(true);

        ArrayList<Upgrade> upgrade = CurrPlayer.getUpgradeAction();
        TreeItem<String> UpgradeItem = UpgradeHelper(upgrade, "Upgrade");

        ArrayList<Action> move = CurrPlayer.getMoveAction();
        TreeItem<String> MoveItem = MoveAttackHelper(move, "Move");

        ArrayList<Action> attack = CurrPlayer.getAttackAction();
        TreeItem<String> AttackItem = MoveAttackHelper(attack, "Attack");

        Alliance alliance = CurrPlayer.getAllianceAction();
        TreeItem<String> AllianceItem = AllianceHelper(alliance, "Alliance");

        rootItem.getChildren().addAll(UpgradeItem, MoveItem, AttackItem, AllianceItem);
        Detail.setRoot(rootItem);
    }

    public static TreeItem<String> UpgradeHelper(ArrayList<Upgrade> Actions, String Type){
        TreeItem<String> Items = new TreeItem<> (Type + " Actions");
        for (int i = 0; i < Actions.size(); i++) {
            Upgrade Curr = Actions.get(i);
            StringBuilder Display = new StringBuilder();
            Display.append(Curr.getTerritoryName()).append(": ");
            Display.append("Level ").append(Curr.getPrevLevel()).append(" -> ").append("Level ").append(Curr.getNextLevel());
            Display.append(" *").append(Curr.getNumber());
            String ShowText = Display.toString();
            TreeItem<String> item = new TreeItem<String> (ShowText);
            Items.getChildren().add(item);
        }
        return Items;
    }

    public static TreeItem<String> MoveAttackHelper(ArrayList<Action> Actions, String Type) {
        TreeItem<String> Items = new TreeItem<String> (Type + " Actions");
        for (int i = 0; i < Actions.size(); i++) {
            Action Curr = Actions.get(i);
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
            Items.getChildren().add(item);
        }
        return Items;
    }

    public static TreeItem<String> AllianceHelper(Alliance alliance, String Type){
        TreeItem<String> Items = new TreeItem<> (Type + " Actions");
        if (alliance.getAlly() == -1) { return Items; }
        ColorID PlayerColor = new ColorID();
        String ShowText = new String("Alliance with " + PlayerColor.getPlayerColor(alliance.getAlly()) + " player");
        TreeItem<String> item = new TreeItem<> (ShowText);
        Items.getChildren().add(item);
        return Items;
    }
}
