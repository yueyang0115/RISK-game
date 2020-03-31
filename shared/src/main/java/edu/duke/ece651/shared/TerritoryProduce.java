package edu.duke.ece651.shared;

import java.io.*;
import java.util.HashMap;

public class TerritoryProduce {
    private HashMap<String, Integer> FoodProduce;
    private HashMap<String, Integer> TechProduce;
    public TerritoryProduce() throws IOException {
        FoodProduce = new HashMap<>();
        TechProduce = new HashMap<>();
        FileInputStream inputStream = new FileInputStream("src/main/resources/Produce.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str;
        String[] Split;
        while ((str = bufferedReader.readLine()) != null) {
            Split = str.split("\\s+");
            this.FoodProduce.put(Split[0], Integer.parseInt(Split[1]));
            this.TechProduce.put(Split[0], Integer.parseInt(Split[2]));
        }
    }
    public int getFood(String Terr){
        return this.FoodProduce.get(Terr);
    }
    public int getTech(String Terr){
        return this.TechProduce.get(Terr);
    }
}
