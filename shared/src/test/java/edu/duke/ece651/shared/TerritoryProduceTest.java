package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TerritoryProduceTest {
    @Test
    public void test_produce() throws IOException {
        TerritoryProduce test = new TerritoryProduce();
        System.out.println("Territory A: " + "Food "+ test.getFood("A") + ", Technology " + test.getTech("A"));;
    }
}
