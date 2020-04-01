package edu.duke.ece651.shared;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Bonus {
    private HashMap<Integer, Integer> bonus;

    public Bonus(){//construct a map to store all the bonus of each level soldiers
        this.bonus = new HashMap<>();
        String fileName = "/Bonus.txt";
        InputStream input = getClass().getResourceAsStream(fileName);
        Scanner scanner = new Scanner(input);
        String[] Split;
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            Split = str.split("\\s+");
            this.bonus.put(Integer.parseInt(Split[0]),Integer.parseInt(Split[1]));
        }
    }

    public int getBonus(int level){
        return this.bonus.get(level);
    }
}
