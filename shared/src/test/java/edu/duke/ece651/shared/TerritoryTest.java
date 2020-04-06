package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.Test;

public class TerritoryTest {
  @Test
  public void test_territory() {
    Territory t = new Territory();

    HashMap<Integer, Integer> soldiers = new HashMap<>();
    soldiers.put(0, 0);
    soldiers.put(1, 0);
    soldiers.put(2, 0);
    soldiers.put(3, 0);
    soldiers.put(4, 0);
    soldiers.put(5, 0);
    soldiers.put(6, 0);
    t.setSoldiers(soldiers);
    assertEquals(soldiers.get(0), t.getSoldiers().get(0));
    t.setSoldierLevel(0, 1);
    assertEquals(t.getSoldierLevel(0), 1);
    t.setNeighbor("A");
    assertEquals(t.getNeighbor().get(0), "A");
    t.setTerritoryName("B");
    assertEquals(t.getTerritoryName(), "B");
    t.setTotalSize(10);
    assertEquals(t.getTotalSize(), 10);
    Territory t2 = new Territory();
    t2.setTotalSize(10);
    assertEquals(t.compareTo(t2), 0);
  }
}
