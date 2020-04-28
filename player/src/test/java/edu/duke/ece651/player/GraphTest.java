package edu.duke.ece651.player;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GraphTest {
    @Test
    public void Test_Graph(){

        ArrayList<String> Color = new ArrayList<>();
        Color.add("green");
        Color.add("blue");
        Color.add("red");
        Color.add("yellow");
        Color.add("white");
        for(String current: Color){
            Graph StyleTest = new Graph();
            for (int i = 0; i < 12; i++){
                char Name = (char)('A' + i);
                System.out.println(StyleTest.getStyle(current, String.valueOf(Name)));
            }

        }
    }

}
