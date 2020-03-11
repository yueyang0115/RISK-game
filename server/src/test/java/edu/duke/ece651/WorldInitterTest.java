package edu.duke.ece651;
import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.server.WorldInitter;
import edu.duke.ece651.shared.*;
import java.io.*;
import java.util.*;
import org.junit.jupiter.api.Test;

public class WorldInitterTest {
  @Test
  public void test_world3initter() {
    WorldInitter myworldinitter = new WorldInitter(3);
    HashMap<Integer, ArrayList<Territory>> myworld;
    myworld = myworldinitter.getWorld();
    MaptoJson myMaptoJson = new MaptoJson(myworld);
    System.out.println(myMaptoJson.getJSON());
  }

  @Test
  public void test_world2initter() {
    WorldInitter myworldinitter = new WorldInitter(2);
    HashMap<Integer, ArrayList<Territory>> myworld;
    myworld = myworldinitter.getWorld();
    MaptoJson myMaptoJson = new MaptoJson(myworld);
    System.out.println(myMaptoJson.getJSON());
  }

  @Test
  public void test_world4initter() {
    WorldInitter myworldinitter = new WorldInitter(4);
    HashMap<Integer, ArrayList<Territory>> myworld;
    myworld = myworldinitter.getWorld();
    MaptoJson myMaptoJson = new MaptoJson(myworld);
    System.out.println(myMaptoJson.getJSON());
  }

  @Test
  public void test_world5initter() {
    WorldInitter myworldinitter = new WorldInitter(5);
    HashMap<Integer, ArrayList<Territory>> myworld;
    myworld = myworldinitter.getWorld();
    MaptoJson myMaptoJson = new MaptoJson(myworld);
    System.out.println(myMaptoJson.getJSON());
  }
}
