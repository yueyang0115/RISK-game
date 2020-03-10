package edu.duke.ece651.shared;

import java.util.*;

public class Territory {
  private String owner;
  private int soldiers;
  private ArrayList<String> neighbor;
  private String territoryName;
  Territory(){
    neighbor = new ArrayList<String>();
  }
  public void setOwner(String Owner){
    owner = Owner;
  }
  public void setSoldiers(int SoldierNum){
    soldiers = SoldierNum;
  }
  public void setNeighbor(String neigh){
    neighbor.add(neigh);
  }
  public void setTerritoryName(String Name){
    territoryName = Name;
  }
}
