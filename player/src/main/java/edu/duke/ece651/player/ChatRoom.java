package edu.duke.ece651.player;

import edu.duke.ece651.shared.Communicator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
                content.getItems().add(str);
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
    }
}
