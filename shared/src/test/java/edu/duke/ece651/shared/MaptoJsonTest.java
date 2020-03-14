package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

public class MaptoJsonTest {
  @Test
  public void test_toJSON() {
    HashMap<Integer, ArrayList<Territory>> myterritoryMap =
        new HashMap<Integer, ArrayList<Territory>>();

    ArrayList<Territory> myTerritoryList = new ArrayList<Territory>();
    Territory territory_A = new Territory();
    territory_A.setNeighbor("B");
    territory_A.setNeighbor("D");
    territory_A.setOwner("player_0");
    territory_A.setSoldiers(3);
    territory_A.setTerritoryName("A");
    myTerritoryList.add(territory_A);

    myterritoryMap.put(0, myTerritoryList);
    MaptoJson mytoJSON = new MaptoJson(myterritoryMap);
    System.out.println(mytoJSON.getJSON());

    MyFormatter myformatter = new MyFormatter(0);
    System.out.println(myformatter.MapCompose(myterritoryMap));
  }
}
