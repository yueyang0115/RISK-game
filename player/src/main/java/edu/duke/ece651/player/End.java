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

public class End {
    private PlayerHelper CurrPlayer;
    private String EndGame;
    @FXML private Label Prompt;

    public End(PlayerHelper CurrPlayer, String GameEnd){
        this.CurrPlayer = CurrPlayer;
        this.EndGame = GameEnd;
    }

    public void initialize(){
        System.out.println("+++++++++++==============Game End+++++++++++==========");
        this.Prompt.setText(this.EndGame);
        this.Prompt.setFont(new Font("Arial", 24));
    }

}