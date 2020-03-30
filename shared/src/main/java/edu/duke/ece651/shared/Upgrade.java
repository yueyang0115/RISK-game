package edu.duke.ece651.shared;

public class Upgrade {
    private String territoryName;
    private String owner;
    private int prevLevel;
    private int nextLevel;
    private int number;

    public Upgrade() {
    }
    public void setTerritoryName(String n){
        territoryName = n;
    }
    public void setOwner(String Owner) {
        owner = Owner;
    }
    public void setPrevLevel(int p){
        prevLevel = p;
    }
    public void setNextLevel(int n){
        nextLevel = n;
    }
    public void setNumber(int n){
        number = n;
    }
    public String getTerritoryName(){
        return territoryName;
    }
    public String getOwner() {
        return owner;
    }
    public int getPrevLevel(){
        return prevLevel;
    }
    public int getNextLevel(){
        return nextLevel;
    }
    public int getNumber(){
        return number;
    }
}
