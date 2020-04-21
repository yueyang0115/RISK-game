package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class CommunicatorTest {
  @Test
  public void test_communicator_2() { // server
    try {
      ServerSocket ss = new ServerSocket(12345);
      //ss.accept();
    } catch (IOException e) {
      System.out.println("Failed to crete ServerSocket!");
    }
  }

  @Test
  public void test_Communicator() throws IOException { // player
    Communicator client = new Communicator("127.0.0.1", 12345);
    client.sendString("");
    JSONObject js = new JSONObject(
        "{'classes':[{'name':'Course','fields':[{'name':'numStudents','type':{'e':{'e':'int'}}},{'name':'instructor','type':'Faculty'}]}]}");
    client.sendJSON(js);
    client.close();
  }


}
