package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ColorIDTest {
  @Test
  public void test_ColorID() {
    ColorID c = new ColorID();
    System.out.println(c.getPlayerColor(0));
  }

}
