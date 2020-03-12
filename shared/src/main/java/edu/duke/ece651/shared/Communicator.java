package edu.duke.ece651.shared;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import org.json.*;

public class Communicator {
  //private Socket socket;
  //private OutputStreamWriter out;
  //private BufferedReader in;

  public Socket getCommunicator(int port) {
    try {
      ServerSocket serverSocket= new ServerSocket(port);
      Socket socket = serverSocket.accept();
      return socket;
    } catch (IOException e) {
      System.out.println("Failed to crete Communicator!");
    }
    return null;
  }

  public Socket getCommunicator(String ip, int port) {
    try {
      Socket socket = new Socket(ip, port);
    } catch (IOException e) {
      System.out.println("Failed to crete Communicator!");
    }
  }
  
  public void send(JSONObject json) {
    try {
      this.out = new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8);
      this.out.write(json.toString());
    } catch (IOException e) {
      System.out.println("Failed to send data!");
    }
  }
  
  public String receive() {
    String res = "";
    try {
      this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
      res = this.in.readLine();
    } catch (IOException e) {
      System.out.println("Failed to receive data!");
    }
    return res;
  }

  public void close() {
    try {
      this.socket.close();
      this.in.close();
      this.out.close();
    } catch (IOException e) {
      System.out.println("Failed to close socket!");
    }
  }
  

}
