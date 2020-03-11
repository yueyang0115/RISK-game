package edu.duke.ece651.player;

import java.util.*;
import edu.duke.ece651.shared.*;
import javafx.util.*;
public interface Displayable {
  public void show(HashMap<Integer, ArrayList<Territory>> CurrentMap, Pair<Integer, String> playerInfo);
}
