package edu.duke.ece651.shared;

import org.apache.commons.math3.analysis.function.Cos;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CostTest {
    @Test
    public void Test_cost() throws IOException {
        Cost test = new Cost();
        for(int i = 0; i < 7; i++){
            for(int j = i + 1; j < 7; j++){
                System.out.println("From Level " + i + " to Level " + j + " costs " + test.getCosts(i, j));
            }
        }
    }
}
