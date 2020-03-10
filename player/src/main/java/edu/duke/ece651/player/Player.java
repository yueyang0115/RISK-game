package edu.duke.ece651.player;

import java.util.*;

public class Player {
  private HashMap<Integer, ArrayList<Territory>> territoryMap;
  private pair<Integer, String> playerInfo;
  private ArrayList<Action> allAction;
  private Displayable displayer;

  public Player() {
    this.territoryMap = new HashMap<>();
    this.playerInfp = new pair<>();
    this.allAction = new ArrayList<>();
  }

  public void addDisplayable(Displayable d) {
    this.displayer = d;
  }

  public void display() {
    displayer.show();
  }

}
