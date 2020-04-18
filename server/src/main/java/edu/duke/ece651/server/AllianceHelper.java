package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import java.util.*;

public class AllianceHelper {
    private ArrayList<Alliance> curRoundAlliance; //Alliance of the current round, not checked yet
    private Set<Set<Integer>> allAlliances; //allAlliance pairs
    private HashMap<String, Set<Integer>> allianceMap; //territory affected by move/attack
    private ArrayList<String> checkResults;

    public void AllianceHelper(int playerNum) {
        this.curRoundAlliance = new ArrayList<>();
        this.allAlliances = new HashSet<>();
        this.allianceMap = new HashMap<>();
        this.checkResults = new ArrayList<>(playerNum);
        for (int i = 0; i < playerNum; i++) {
            checkResults.add("No new alliance in this round");
        }
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

    public void addCurrentRoundAlliance(Alliance input) {
        curRoundAlliance.add(input);
    }

    //Check and upgrade all the alliances after doAction
    public void executeCurRound() {
        for (int i = 0; i < curRoundAlliance.size(); i++) {
            Alliance current = curRoundAlliance.get(i);
            int owner = current.getOwner();
            int ally = current.getAlly();
            for (int j = i + 1; j < curRoundAlliance.size(); j++) {
                Alliance search = curRoundAlliance.get(j);
                if (search.getOwner() == ally && search.getAlly() == owner) {
                    formNewAlliance(owner, ally);
                }
            }
        }
    }

    public String getAllianceResult(int id) {
        return checkResults.get(id);
    }
}
