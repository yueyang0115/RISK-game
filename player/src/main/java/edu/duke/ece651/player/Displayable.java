package edu.duke.ece651.player;

import java.util.*;
import edu.duke.ece651.shared.*;
import javafx.scene.control.Button;
import javafx.util.*;
public interface Displayable {
  public void showMap(HashMap<Integer, ArrayList<Territory>> CurrentMap, Pair<Integer, String> playerInfo, HashMap<String, Button> ButtonMap);

  public void showAction(HashMap<Integer, ArrayList<Action>> RecvAction, Pair<Integer, String> playerInfo);
}
