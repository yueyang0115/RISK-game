package edu.duke.ece651.player;

public class MoveORAttack {
    private PlayerHelper CurrPlayer;
    private String ActionType;

    public MoveORAttack(PlayerHelper player, String Type){
        this.CurrPlayer = player;
        this.ActionType = Type;
    }
}
