package edu.duke.ece651.shared;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Cost {
    private HashMap<Integer, Integer> cost;
    //key is soldier level
    // value is how much need to cost to upgrade to this level

    public Cost(){//constructor to build a map for the costs of upgrade
        this.cost = new HashMap<>();
        String fileName = "/Cost.txt";
        InputStream input = getClass().getResourceAsStream(fileName);
        Scanner scanner = new Scanner(input);
        String[] Split;
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            Split = str.split("\\s+");
            this.cost.put(Integer.parseInt(Split[0]),Integer.parseInt(Split[1]));
        }
    }

    //get StartLevel and EndLevel, return the total costs of this upgrade
    public int getCosts(int FromLevel, int ToLevel){
        int totalCost = 0;
        for(int i = FromLevel + 1; i <= ToLevel; i++){
            totalCost = totalCost + this.cost.get(i);
        }
        return totalCost;
    }
}
