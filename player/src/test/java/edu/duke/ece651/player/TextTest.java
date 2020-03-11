package edu.duke.ece651.player;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.shared.*;
import javafx.util.Pair;


public class TextTest {
  @Test
  public void test_Text() {
    HashMap<Integer, ArrayList<Territory>> Map = new HashMap<>();
    
    Territory TestTerr_a1 = new Territory();
    TestTerr_a1.setOwner("aaa");
    TestTerr_a1.setSoldiers(3);
    TestTerr_a1.setNeighbor("aaa1_neigh1");
    TestTerr_a1.setNeighbor("aaa1_neigh2");
    TestTerr_a1.setTerritoryName("aaa1_terr1");
    Territory TestTerr_a2 = new Territory();
    TestTerr_a2.setOwner("aaa");
    TestTerr_a2.setSoldiers(13);
    TestTerr_a2.setNeighbor("aaa2_neigh1");
    TestTerr_a2.setNeighbor("aaa2_neigh2");
    TestTerr_a2.setTerritoryName("aaa2_terr2");
    ArrayList<Territory> InnerAL1 = new ArrayList<>();
    InnerAL1.add(TestTerr_a1);
    InnerAL1.add(TestTerr_a2);
    Map.put(0, InnerAL1);

    
    Territory TestTerr_b1 = new Territory();
    TestTerr_b1.setOwner("bbb");
    TestTerr_b1.setSoldiers(5);
    TestTerr_b1.setNeighbor("bbb1_neigh1");
    TestTerr_b1.setNeighbor("bbb1_neigh2");
    TestTerr_b1.setNeighbor("bbb1_neigh3");
    TestTerr_b1.setTerritoryName("bbb1_terr1");
    Territory TestTerr_b2 = new Territory();
    TestTerr_b2.setOwner("bbb");
    TestTerr_b2.setSoldiers(15);
    TestTerr_b2.setNeighbor("bbb2_neigh1");
    TestTerr_b2.setNeighbor("bbb2_neigh2");
    TestTerr_b2.setNeighbor("bbb2_neigh3");
    TestTerr_b2.setTerritoryName("bbb2_terr2");
    ArrayList<Territory> InnerAL2 = new ArrayList<>();
    InnerAL2.add(TestTerr_b1);
    InnerAL2.add(TestTerr_b2);
    Map.put(1, InnerAL2);

    Pair<Integer, String> playerInfo = new Pair<>(0,"Green");
    Text Test = new Text();
    System.out.println("[DEBUG] TestText.show():\n");
    Test.show(Map, playerInfo);

  }

}
