package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;

public class ChatHandler extends Thread {
    private Communicator communicator;
    private ChatServer chatServer;

    public ChatHandler(Communicator c, ChatServer cs) {
        this.communicator = c;
        this.chatServer = cs;
    }
    public void run() {
        while(true) {
            String str = communicator.receive();
            chatServer.sendAll(str);
        }
    }
}
