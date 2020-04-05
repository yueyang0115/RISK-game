package edu.duke.ece651.player;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class ErrorMsgController {
    @FXML private Button OkBtn;
    @FXML private Label msg;

    private Stage Window;
    public ErrorMsgController(String content, Stage Window){
        this.Window = Window;
        msg.setText(content);
    }
    @FXML
    public void OK() throws IOException {
        Window.close();
    }
}
