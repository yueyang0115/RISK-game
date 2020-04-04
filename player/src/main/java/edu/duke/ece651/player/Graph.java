package edu.duke.ece651.player;

import com.sun.tools.javac.Main;
import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.Territory;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph implements Displayable{
    @Override
    public void showMap(HashMap<Integer, ArrayList<Territory>> CurrentMap, Pair<Integer, String> playerInfo, Stage MainWindow) {
//        Group root = new Group();
//
//        Scene scene = new Scene(root, 260, 80);
//        MainWindow.setScene(scene);
//        Group g = new Group();
//        Polygon TerrA = new Polygon();
//        TerrA.getPoints().addAll(new Double[]{0.0, 0.0, 20.0, 10.0, 10.0, 20.0 });
//        g.getChildren().add(TerrA);
//        scene.setRoot(g);
        System.out.println("Inside of showMap");
        //Stage MainWindow = new Stage();

        Label GameName = new Label("(RISC)");
        VBox layout = new VBox();
        layout.getChildren().add(GameName);
        Scene Start = new Scene(layout, 300, 250);
        System.out.println("Already add to Scene");
        MainWindow.setScene(Start);
        System.out.println("Already add to Window");
        //MainWindow.show();

    }

    @Override
    public void showAction(HashMap<Integer, ArrayList<Action>> RecvAction, Pair<Integer, String> playerInfo, Stage MainWindow) {

    }
}
