package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Lose{
    @FXML private Button buttonYes;
    @FXML private Button buttonNo;

    @FXML
    public void LoseButWatch() throws IOException {
        System.out.println("Lose But Watch");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/Watch.fxml"));
        loaderStart.setControllerFactory(c->{
            return new Watch(this.CurrPlayer);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }

    public void LoseNotWatch() throws IOException {
        System.out.println("Lose Not Watch");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/End.fxml"));
        loaderStart.setControllerFactory(c->{
            return new End(this.CurrPlayer);
        });
        Scene scene = new Scene(loaderStart.load());
        this.Window.setScene(scene);
        this.Window.show();
    }
}