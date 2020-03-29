package edu.duke.ece651.shared;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Bonus {
    private HashMap<Integer, Integer> bonus;

    public Bonus() throws IOException {//construct a map to store all the bonus of each level soldiers
        this.bonus = new HashMap<>();
        FileInputStream inputStream = new FileInputStream("src/main/resources/Bonus.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str;
        String[] Split;
        while((str = bufferedReader.readLine()) != null) {
            Split = str.split("\\s+");
            this.bonus.put(Integer.parseInt(Split[0]), Integer.parseInt(Split[1]));
        }
    }

    public int getBonus(int level){
        return this.bonus.get(level);
    }
}
