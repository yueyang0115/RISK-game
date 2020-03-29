package edu.duke.ece651.shared;

import java.util.*;

public class Territory {
  private String owner;
  //TODO: change its type into HashMap<Integer, Integer>
  private int soldiers;
  private ArrayList<String> neighbor;
  private String territoryName;
  public Territory(){
    neighbor = new ArrayList<String>();
  }
  public String getOwner(){
    return owner;
  }

  //TODO: update get and set functions
  public int getSoliders(){
    return soldiers;
  }
  public void setSoldiers(int SoldierNum){
    soldiers = SoldierNum;
  }

  public ArrayList<String> getNeighbor(){
    return neighbor;
  }
  public String getTerritoryName(){
    return territoryName;
  }
  public void setOwner(String Owner){
    owner = Owner;
  }

  public void setNeighbor(String neigh){
    neighbor.add(neigh);
  }
  public void setTerritoryName(String Name){
    territoryName = Name;
  }
}
