package edu.duke.ece651.player;

import edu.duke.ece651.shared.*;
import javafx.util.*;

import java.util.*;

public class Player {
  private HashMap<Integer, ArrayList<Territory>> territoryMap;
  private Pair<Integer, String> playerInfo;
  private ArrayList<Action> allAction;
  private Displayable displayer;
  
  public Player() {
    this.territoryMap = new HashMap<>();
    //this.playerInfo = new Pair<>();
    this.allAction = new ArrayList<>();
  }

  public void addDisplayable(Displayable d) {
    this.displayer = d;
  }

  public void display() {
    //displayer.show();
  }
  
}
