package edu.duke.ece651.player;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static javafx.scene.text.FontPosture.*;
import static javafx.scene.text.FontWeight.BOLD;

public class ErrorMsgController {
    //------------- Evolution 2 --------------//
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
    public void OK(){
        Window.close();
    }

}
