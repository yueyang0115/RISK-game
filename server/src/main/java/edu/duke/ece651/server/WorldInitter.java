package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;
import java.io.*;
import java.util.*;

public class WorldInitter {
  private HashMap<Integer, ArrayList<Territory>> myworld;

  public WorldInitter(int playerNum) {
    myworld = new HashMap<>();
    createWorld(playerNum);
  }

  public WorldInitter(int playerNum, HashMap<Integer, ArrayList<Territory>> w) {
    myworld = w;
    createWorld(playerNum);
  }

  public HashMap<Integer, ArrayList<Territory>> getWorld() {
    return myworld;
  }

  private void createWorld(int playerNum) {
    StringBuilder fileName = new StringBuilder();
    fileName.append("/world").append(playerNum).append(".json");
    InputStream input = getClass().getResourceAsStream(fileName.toString());

    Scanner scanner = new Scanner(input);
    StringBuilder worldInfo = new StringBuilder();
    while (scanner.hasNext()) {
      worldInfo.append(scanner.next());
    }

    MyFormatter myformatter = new MyFormatter(playerNum);
    myformatter.MapParse(myworld, worldInfo.toString());
  }
}
