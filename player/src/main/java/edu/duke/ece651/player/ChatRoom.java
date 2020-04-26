package edu.duke.ece651.player;

import edu.duke.ece651.shared.ColorID;
import edu.duke.ece651.shared.Communicator;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatRoom {
    private Communicator communicator;
    private String name;
    @FXML private Button send;
    @FXML private TextField input;
    @FXML private ListView content;
    private Stage Window;
    private ChatHelper chatHelper;
    private PlayerHelper CurrPlayer;
    private ColorID helper;


    private class ChatHelper extends Thread {
        public void run() {
            while (true) {
                //receive content
                String str = communicator.receive();
                //add content
                String[] arr = str.split(":");
                if (!arr[0].equals(name)) {
                    int ID = helper.getPlayerID(arr[0]);
                    DisplayContent(arr[1], ID, false);
                } else {
                    int ID = CurrPlayer.getPlayerInfo().getKey();
                    DisplayContent(arr[1], ID, true);
                }
            }
        }
    }

    private void DisplayContent(String text,int ID, boolean Mine) {
        HBox Other = new HBox();
        ImageView Photo = new ImageView(new Image(getClass().getResourceAsStream("/Player_Photo/player" + ID + ".png")));
        Photo.setFitHeight(50);
        Photo.setFitWidth(30);
        Label msg = new Label(text);
        msg.setPrefHeight(40);
        if(Mine){
            msg.setStyle("-fx-background-color: lightskyblue;" + "-fx-background-radius: 5, 4;");
            Other.getChildren().addAll(Photo, msg);
            Other.setAlignment(Pos.CENTER_RIGHT);
        }
        else{
            msg.setStyle("-fx-background-color: darkseagreen;" + "-fx-background-radius: 5, 4;");
            Other.getChildren().addAll(Photo, msg);
            Other.setAlignment(Pos.CENTER_LEFT);
        }
        content.getItems().add(Other);
    }

    public ChatRoom(String color, Stage W, PlayerHelper CurrentPlayer){
        this.helper = new ColorID();
        Window = W;
        name = color;
        chatHelper = new ChatHelper();
        communicator = new Communicator("vcm-12475.vm.duke.edu", 4321); //Different port number just for chat
        this.CurrPlayer = CurrentPlayer;
    }
    public void initialize(){
        chatHelper.start();
    }
    @FXML
    public void sendClick() throws IOException {
        communicator.sendString(name + ": " + input.getText());
        input.clear();
    }
}
