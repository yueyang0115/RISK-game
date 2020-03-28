package edu.duke.ece651.shared;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Soldiers {
  private HashMap<Integer, Integer> CurrSoldiers;
  private HashMap<Integer, Integer> bonus;
  private HashMap<Integer, Integer> cost;

  public Soldiers() throws IOException {
    this.CurrSoldiers = new HashMap<>();
    this.bonus = new HashMap<>();
    this.cost = new HashMap<>();
    FileInputStream inputStream = new FileInputStream("src/main/resources/BonusCost.txt");
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    String str;
    String[] Split;
    while((str = bufferedReader.readLine()) != null) {
      Split = str.split("\\s+");
      this.bonus.put(Integer.parseInt(Split[0]), Integer.parseInt(Split[1]));
      this.cost.put(Integer.parseInt(Split[0]), Integer.parseInt(Split[2]));
    }
    for(int i = 0; i < 7; i++){
      this.CurrSoldiers.put(i, 0);
    }
  }
  public int getCosts(int FromLevel, int ToLevel){
    int totalCost = 0;
    for(int i = FromLevel + 1; i <= ToLevel; i++){
      totalCost = totalCost + this.cost.get(i);
    }
    return totalCost;
  }
  public int getBonus(int level){
    return this.bonus.get(level);
  }
  public int getSoldierNum(int level) {
    return CurrSoldiers.get(level);
  }
  public void setSoliderNum(int level, int Num){
    int current =  this.CurrSoldiers.get(level);
    this.CurrSoldiers.put(level, current + Num);
  }


}
