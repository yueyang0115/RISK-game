package edu.duke.ece651.shared;

import java.io.*;
import java.util.*;

public class TerritorySize {
  private HashMap<String, Integer> TerrSize;

  public TerritorySize() {
    this.TerrSize = new HashMap<>();
    String fileName = "/SizeMap.txt";
    InputStream input = getClass().getResourceAsStream(fileName);
    Scanner scanner = new Scanner(input);
    String[] Split;
    while (scanner.hasNext()) {
      String str = scanner.nextLine();
      Split = str.split("\\s+");
      this.TerrSize.put(Split[0], Integer.parseInt(Split[1]));
    }
  }
  public int getTerritorySize(String Name) {
    return this.TerrSize.get(Name);
  }
}
