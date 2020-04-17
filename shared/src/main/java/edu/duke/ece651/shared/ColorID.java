package edu.duke.ece651.shared;

import java.util.*;
public class ColorID {
  HashMap<Integer, String> Colorid;
  HashMap<String, Integer> IDColor;
  public ColorID(){
    Colorid = new HashMap<>();
    Colorid.put(0, "Green");
    Colorid.put(1, "Blue");
    Colorid.put(2, "Red");
    Colorid.put(3, "Yellow");
    Colorid.put(4, "White");

    IDColor = new HashMap<>();
    IDColor.put("Green", 0);
    IDColor.put("Blue", 1);
    IDColor.put("Red", 2);
    IDColor.put("Yellow", 3);
    IDColor.put("White", 4);
  }

  public String getPlayerColor(int ID){
    return Colorid.get(ID);
  }
  public int getPlayerID(String color){
    return this.IDColor.get(color);
  }
}
