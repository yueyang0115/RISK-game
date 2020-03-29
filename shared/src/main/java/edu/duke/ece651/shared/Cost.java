package edu.duke.ece651.shared;

import java.io.*;
import java.util.HashMap;

public class Cost {
    private HashMap<Integer, Integer> cost;
    //key is soldier level
    // value is how much need to cost to upgrade to this level

    public Cost() throws IOException {//constructor to build a map for the costs of upgrade
        this.cost = new HashMap<>();
        FileInputStream inputStream = new FileInputStream("src/main/resources/Cost.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str;
        String[] Split;
        while ((str = bufferedReader.readLine()) != null) {
            Split = str.split("\\s+");
            this.cost.put(Integer.parseInt(Split[0]), Integer.parseInt(Split[1]));
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
