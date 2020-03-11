package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.Test;

public class FomatterTest {
  @Test
  public void test_Formater() {
    String Input =
        "{'player_0': [{'owner': 'aaa', 'soldiers': 3, 'neighbor': [{'neighbor_1': 'aaa_neigh1'}, {'neighbor_2': 'aaa_neigh2'}], 'territoryName': 'aaaTerr1'}, {'owner': 'aaa', 'soldiers': 4, 'neighbor': [{'neighbor_1': 'aaa_neigh3'}, {'neighbor_2': 'aaa_neigh4'}], 'territoryName': 'aaaTerr2'}],'player_1': [{'owner': 'bbb', 'soldiers': 5, 'neighbor': [{'neighbor_1': 'bbb_neigh1'}, {'neighbor_2': 'bbb_neigh2'}], 'territoryName': 'bbbTerr1'}, {'owner': 'bbb', 'soldiers': 6, 'neighbor': [{'neighbor_1': 'bbb_neigh3'}, {'neighbor_2': 'bbb_neigh4'}], 'territoryName': 'bbbTerr2'}]}";
    MyFormatter Map = new MyFormatter(2);
    HashMap<Integer, ArrayList<Territory>> TerritoryMap = new HashMap<>();
    Map.MapParse(TerritoryMap, Input);
  }
}
