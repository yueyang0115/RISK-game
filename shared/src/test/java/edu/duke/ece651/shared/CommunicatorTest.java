package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class CommunicatorTest {
  @Test public void test_Communicator() { // player
    /*Communicator client = new Communicator("127.0.0.1", 1234);
    client.sendString("");
    JSONObject js = new JSONObject(
        "{'classes':[{'name':'Course','fields':[{'name':'numStudents','type':{'e':{'e':'int'}}},{'name':'instructor','type':'Faculty'}]}]}");
    client.sendJSON(js);
    client.close();*/
  }

  @Test public void test_communicator_2() { // server
    /*try {
      ServerSocket ss = new ServerSocket(1234);
    } catch (IOException e) {
      System.out.println("Failed to crete ServerSocket!");
    }*/
  }
}
