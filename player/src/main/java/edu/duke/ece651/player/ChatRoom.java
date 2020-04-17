package edu.duke.ece651.player;

import edu.duke.ece651.shared.Communicator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private class ChatHelper extends Thread {
        public void run() {
            while (true) {
                //receive content
                String str = communicator.receive();
                //add content
                String[] arr = str.split(":");
                if (!arr[0].equals(name)) {
                    ImageView test = new ImageView(new Image(getClass().getResourceAsStream("/Player0/level0.png")));
                    //recontent.getItems().add(test);
                    content.getItems().add(str);
                }
                else {
                    content.getItems().add("Me: " + arr[1]);
                }
            }
        }
    };

    public ChatRoom(String color, Stage W){
        Window = W;
        name = color;
        chatHelper = new ChatHelper();
        communicator = new Communicator("127.0.0.1", 4321); //Different port number just for chat
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
