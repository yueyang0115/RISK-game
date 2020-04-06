package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.Test;

public class ActionTest {
  @Test
  public void test_action() {
    Action myaction = new Action();
    HashMap<Integer, Integer> soldiers = new HashMap<>();
    soldiers.put(0, 0);
    soldiers.put(1, 0);
    soldiers.put(2, 0);
    soldiers.put(3, 0);
    soldiers.put(4, 0);
    soldiers.put(5, 0);
    soldiers.put(6, 0);
    myaction.setSoldiers(soldiers);
    assertEquals(myaction.getSoldiers().get(0), 0);
    myaction.setSoldierLevel(1, 5);
    assertEquals(myaction.getSoldierLevel(1), 5);
  }
}
