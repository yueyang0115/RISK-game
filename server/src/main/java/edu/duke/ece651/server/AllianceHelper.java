package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;

import java.util.*;

public class AllianceHelper {
    private ArrayList<Alliance> curRoundAlliance; //Alliance of the current round, not checked yet
    private Set<Set<Integer>> allAlliances; //allAlliance pairs
    private HashMap<String, Set<Integer>> allianceMap; //territory affected by move/attack
    private Set<Integer> successID;
    private Set<Integer> noNewID;
    private Set<Integer> breakID;

    public AllianceHelper(int playerNum) {
        this.curRoundAlliance = new ArrayList<>();
        this.allAlliances = new HashSet<>();
        this.allianceMap = new HashMap<>();
        this.successID = new HashSet<>();
        this.noNewID = new HashSet<>();
        this.breakID = new HashSet<>();
    }

    public ArrayList<String> breakAlliance(int attacker, int attackee) {
        Set<Integer> inputSet = new HashSet<>();
        inputSet.add(attacker);
        inputSet.add(attackee);
        breakID.add(attacker);
        breakID.add(attackee);
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
            if (owner == -1 || ally == -1) {
                noNewID.add(i);
                continue;
            }
            for (int j = i + 1; j < curRoundAlliance.size(); j++) {
                Alliance search = curRoundAlliance.get(j);
                if (search.getOwner() == ally && search.getAlly() == owner) {
                    formNewAlliance(owner, ally);
                    successID.add(i);
                    successID.add(j);
                    break;
                }
            }
        }
    }

    public String getAllianceResult(int id) {
        if (successID.contains(id)) {
            return "Successfully formed alliance!";
        }
        else if (noNewID.contains(id)) {
            return "No new alliance in this round.";
        }
        else if (breakID.contains(id)) {
            return "Your alliance is broken!";
        }
        return "Failed to form alliance!";
    }

    public void resetCurRound() {
        this.curRoundAlliance = new ArrayList<>();
        this.successID = new HashSet<>();
        this.noNewID = new HashSet<>();
        this.breakID = new HashSet<>();
    }
}
