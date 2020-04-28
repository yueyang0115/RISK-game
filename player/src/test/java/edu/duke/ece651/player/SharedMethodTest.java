package edu.duke.ece651.player;
import edu.duke.ece651.shared.*;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

public class SharedMethodTest {
    @Test
    public void Test_Alliance(){
        PlayerHelper TestPlayer = new PlayerHelper();
        TestPlayer.setMyAlly(1);
        TestPlayer.setID(0);
        Pair<Integer, String> MyInfo = new Pair<>(0,"Green");
        TestPlayer.setPlayerInfo(MyInfo);
        SharedMethod.getAllianceInfo(TestPlayer);
        assert(1 == TestPlayer.getMyAlly());
        TestPlayer.getAllianceAction();
        Alliance Test_alliance = new Alliance();
        Test_alliance.setOwner(0);
        Test_alliance.setAlly(1);
        assert(Test_alliance.getOwner() == 0);
        assert (Test_alliance.getAlly() == 1);
        AlliancetoJson JsonAlliance = new AlliancetoJson(Test_alliance);
        System.out.println("Alliance to Json: " + JsonAlliance.getJSON().toString());

    }
}
