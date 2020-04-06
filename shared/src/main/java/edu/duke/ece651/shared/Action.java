package edu.duke.ece651.shared;
import java.util.*;
public class Action {
  private Territory src;
  private Territory dst;
  private HashMap<Integer, Integer> soldiers;
  private String owner;
  private String type;

  public Action() {
    //at initial, all action hold 0 soldier of all soldier level
    soldiers = new HashMap<>();
    soldiers.put(0, 0);
    soldiers.put(1, 0);
    soldiers.put(2, 0);
    soldiers.put(3, 0);
    soldiers.put(4, 0);
    soldiers.put(5, 0);
    soldiers.put(6, 0);
  }
  public void setSrc(Territory Src) {
    src = Src;
  }
  public void setDst(Territory Dst) {
    dst = Dst;
  }

  public void setSoldiers(HashMap<Integer, Integer> mySoldiers) {
    for (HashMap.Entry<Integer, Integer> entry : mySoldiers.entrySet()) {
      soldiers.put(entry.getKey(), entry.getValue());
    }
  }
  public void setSoldierLevel(int level, int num) {
    soldiers.put(level, num);
  }

  public void setOwner(String Owner) {
    owner = Owner;
  }
  public void setType(String Type) {
    type = Type;
  }
  public String getType() {
    return type;
  }
  public Territory getSrc() {
    return src;
  }
  public Territory getDst() {
    return dst;
  }
  public String getOwner() {
    return owner;
  }
  public HashMap<Integer, Integer> getSoldiers() {
    HashMap<Integer, Integer> newMap = new HashMap<>();
    for (HashMap.Entry<Integer, Integer> entry : soldiers.entrySet()) {
      newMap.put(entry.getKey(), entry.getValue());
    }
    return newMap;
  }
  public int getSoldierLevel(int level) {
    return soldiers.get(level);
  }
}
