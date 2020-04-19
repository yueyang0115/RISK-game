package edu.duke.ece651.shared;

public class Alliance {
    private int owner;
    private int ally;

    public Alliance() {
        this.owner = -1;
        this.ally = -1;
    }
    public void setOwner(int Owner) {
        owner = Owner;
    }
    public void setAlly(int Ally){
        ally = Ally;
    }
    public int getOwner() {
        return owner;
    }
    public int getAlly(){
        return ally;
    }
}
