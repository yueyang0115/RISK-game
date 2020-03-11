package edu.duke.ece651.shared;

public class Action {
  private Territory src;
  private Territory dst;
  private int soldiers;
  private String owner;
  private String type;
  public void setSrc(Territory Src){
    src = Src;
  }
  public void setDst(Territory Dst){
    dst = Dst;
  }
  public void setSoldiers(int Soldiers){
    soldiers = Soldiers;
  }
  public void setOwner(String Owner){
    owner = Owner;
  }
  public void setType(String Type){
    type = Type;
  }
  public String getType(){
    return type;
  }
}
