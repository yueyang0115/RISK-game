package edu.duke.ece651.player;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class ErrorMsgController {
    @FXML private Button OKBtn;
    @FXML private Label labelmsg;
    private String message;

    private Stage Window;
    public ErrorMsgController(String content, Stage W){
        Window = W;
        Window.initModality(Modality.APPLICATION_MODAL);
        message = content;
        labelmsg = new Label(content);
    }
    public void initialize(){
        labelmsg.setText(message);
    }
    @FXML
    public void OK() throws IOException {
        Window.close();
    }

}
