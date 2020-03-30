package edu.duke.ece651.shared;

import java.io.*;
import java.util.HashMap;

public class TerritorySize {
    private HashMap<String, Integer> TerrSize;
    public TerritorySize() throws IOException {
        this.TerrSize = new HashMap<>();
        FileInputStream inputStream = new FileInputStream("src/main/resources/SizeMap.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str;
        String[] Split;
        while ((str = bufferedReader.readLine()) != null) {
            Split = str.split("\\s+");
            this.TerrSize.put(Split[0], Integer.parseInt(Split[1]));
        }
    }
    public int getTerritorySize(String Name){
        return this.TerrSize.get(Name);
    }
}
