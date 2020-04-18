package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import java.util.*;

public class AllianceHelper {
    ArrayList<Alliance> allianceList; //Alliance of the current round, not checked yet
    Set<Set<Integer>> allAlliances; //allAlliance pairs
    HashMap<String, Set<Integer>> allianceMap; //territory affected by move/attack

    public void AllianceHelper() {
        this.allianceList = new ArrayList<>();
        this.allAlliances = new HashSet<>();
        this.allianceMap = new HashMap<>();
    }

    public ArrayList<String> breakAlliance(int attacker, int attackee) {
        Set<Integer> inputSet = new HashSet<>();
        inputSet.add(attacker);
        inputSet.add(attackee);
        deleteAlliancePair(inputSet);
        return deleteAllianceTerritory(attacker, attackee);
    }
    public void deleteAlliancePair(Set<Integer> input){
        allAlliances.remove(input);
    }

    public ArrayList<String> deleteAllianceTerritory(int attacker, int attackee) {
        ArrayList<String> res = new ArrayList<>();
        for (Map.Entry<String, Set<Integer>> entry : allianceMap.entrySet()) {
            if (entry.getValue().contains(attacker) || entry.getValue().contains(attackee)) {
                entry.getValue().clear();
                res.add(entry.getKey());
            }
        }
        return res;
    }

    public boolean territoryisAllianced(String territoryname, int id) {
        return allianceMap.get(territoryname).contains(id);
    }
    public void addAlliance(String territoryname, int owner){
        allianceMap.get(territoryname).add(owner);
    }
    public boolean playerisAllianced(int id1, int id2){
        Set<Integer> newSet = new HashSet<>();
        newSet.add(id1);
        newSet.add(id2);
        return allAlliances.contains(newSet);
    }
    public void formNewAlliance(int id1, int id2) {
        Set<Integer> newSet = new HashSet<>();
        newSet.add(id1);
        newSet.add(id2);
        allAlliances.add(newSet);
    }
}
