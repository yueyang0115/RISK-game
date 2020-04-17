package edu.duke.ece651.shared;

public class Alliance {
    private String owner;
    private String ally;

    public Alliance() {
        this.owner = "";
        this.ally = "";
    }
    public void setOwner(String Owner) {
        owner = Owner;
    }
    public void setAlly(String Ally){
        ally = Ally;
    }
    public String getOwner() {
        return owner;
    }
    public String getAlly(){
        return ally;
    }
}
