package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TerritorySizeTest {
    @Test
    public void test_size() throws IOException {
        TerritorySize test = new TerritorySize();
        test.getTerritorySize("A");
    }
}
