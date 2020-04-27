package edu.duke.ece651.player;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

public class SharedMethodTest {
    @Test
    public void test_method(){
        PlayerHelper CurrPlayer = new PlayerHelper();
        CurrPlayer.setMyAlly(1);
        Pair<Integer, String> PlayerInfo = new Pair<>(0,"Green");
        CurrPlayer.setPlayerInfo(PlayerInfo);
        System.out.println(SharedMethod.getAllianceInfo(CurrPlayer));
    }

}
