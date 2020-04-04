package edu.duke.ece651.shared;

import java.io.*;
import java.util.*;

public class TerritoryProduce {
  private HashMap<String, Integer> FoodProduce;
  private HashMap<String, Integer> TechProduce;

  public TerritoryProduce() {
    FoodProduce = new HashMap<>();
    TechProduce = new HashMap<>();
    String fileName = "/Produce.txt";
    InputStream input = getClass().getResourceAsStream(fileName);
    Scanner scanner = new Scanner(input);
    String[] Split;
    while (scanner.hasNext()) {
      String str = scanner.nextLine();
      Split = str.split("\\s+");
      this.FoodProduce.put(Split[0], Integer.parseInt(Split[1]));
      this.TechProduce.put(Split[0], Integer.parseInt(Split[2]));
    }
  }
  public int getFood(String Terr) {
    return this.FoodProduce.get(Terr);
  }

  public int getTech(String Terr) {
    return this.TechProduce.get(Terr);
  }
}
