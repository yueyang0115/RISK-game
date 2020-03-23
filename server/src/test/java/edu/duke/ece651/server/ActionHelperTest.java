package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

public class ActionHelperTest {
  @Test
  public void test_ActionHelper() {
    HashMap<Integer, ArrayList<Territory>> m = new HashMap<>();
    ActionHelper testActionHelper = new ActionHelper(3, m);
    testActionHelper.reset();
    assert(testActionHelper.checkActionValid(3) == false);
    ArrayList<Action> ml = new ArrayList<>();
    ArrayList<Action> al = new ArrayList<>();
    testActionHelper.addActions(1, ml, al);
    testActionHelper.actionsCompleted(0);
  }

}
