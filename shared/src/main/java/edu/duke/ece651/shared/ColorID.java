package edu.duke.ece651.shared;

import java.util.*;
public class ColorID {
  HashMap<Integer, String> Colorid;
  public ColorID(){
    Colorid = new HashMap<>();
    Colorid.put(0, "Green");
    Colorid.put(1, "Blue");
    Colorid.put(2, "Red");
    Colorid.put(3, "Yellow");
    Colorid.put(4, "White");
  }

  public String getPlayerColor(int ID){
    return Colorid.get(ID);
  }

}
