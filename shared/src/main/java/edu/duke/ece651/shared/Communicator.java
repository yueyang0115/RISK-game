package edu.duke.ece651.shared;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import org.json.*;

public class Communicator {

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
      return socket;
    } catch (IOException e) {
      System.out.println("Failed to crete Communicator!");
    }
    return null;
  }

  public void sendString(Socket socket, String str) {
     try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
      out.println(str);
    } catch (IOException e) {
      System.out.println("Failed to send String!");
    }   
  }
  
  public void sendJSON(Socket socket, JSONObject json) {
    try (OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)) {
      out.write(json.toString());
    } catch (IOException e) {
      System.out.println("Failed to send JSONObject!");
    }
  }
  
  public String receive(Socket socket) {
    String res = "";
    try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
      res = in.readLine();
    } catch (IOException e) {
      System.out.println("Failed to receive data!");
    }
    return res;
  }

  public void close(Socket socket) {
    try {
      socket.close();
    } catch (IOException e) {
      System.out.println("Failed to close socket!");
    }
  }
  

}
