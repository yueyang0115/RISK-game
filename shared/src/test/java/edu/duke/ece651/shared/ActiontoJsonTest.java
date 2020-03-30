package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.Test;

public class ActiontoJsonTest {
  @Test
  public void test_ActiontoJson() {
    ArrayList<Action> actionList = new ArrayList<>();
    Territory territory_A = new Territory();
    territory_A.setNeighbor("B");
    territory_A.setNeighbor("D");
    territory_A.setOwner("player_0");
    territory_A.setSoldierLevel(0, 3);
    territory_A.setTerritoryName("A");

    Action action_A = new Action();
    action_A.setSrc(territory_A);
    action_A.setDst(territory_A);
    action_A.setSoldierLevel(0, 3);
    action_A.setOwner("player_0");
    action_A.setType("Move");
    actionList.add(action_A);
    actionList.add(action_A);

    ActiontoJson myActiontoJson = new ActiontoJson(actionList, "Move");
    System.out.println(myActiontoJson.getJSON());

    MyFormatter myformatter = new MyFormatter(0);
    System.out.println(myformatter.ActionCompose(actionList, "Attack"));
  }
}
