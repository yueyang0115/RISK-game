package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer {
    private ServerSocket chatServerSock;
    private ArrayList<ChatHandler> chatHandlers;
    private ArrayList<Communicator> communicators;
    private int playerNum;

    public ChatServer (int Num) {
        try {
            this.chatServerSock = new ServerSocket(4321);
        } catch (IOException e) {
            System.out.println("Failed to crete chatServerSocket!");
        }
        this.chatHandlers = new ArrayList<>(Num);
        this.communicators = new ArrayList<>(Num);
        this.playerNum = Num;

    }
    public void setUp() {
        for (int i = 0; i < playerNum; i++) {
            Communicator curCommunicator = new Communicator(chatServerSock);
            communicators.add(curCommunicator);
            ChatHandler curHandler = new ChatHandler(curCommunicator, this);
            chatHandlers.add(curHandler);
            curHandler.start();
        }
    }
    public synchronized void sendAll(String str) {
        for (Communicator cur : communicators) {
            cur.sendString(str);
        }
    }
}
