package edu.duke.ece651.player;

import java.util.*;
import edu.duke.ece651.shared.*;
import javafx.stage.Stage;
import javafx.util.*;
public interface Displayable {
  public void showMap(HashMap<Integer, ArrayList<Territory>> CurrentMap, Pair<Integer, String> playerInfo, Stage Window);

  public void showAction(HashMap<Integer, ArrayList<Action>> RecvAction, Pair<Integer, String> playerInfo, Stage Window);
}
