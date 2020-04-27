package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

public class AllianceHelperTest {
    private AllianceHelper ah;
    public AllianceHelperTest(){
        ah = new AllianceHelper();
    }
    @Test
    public void test_playerisAllianced() {
        AllianceHelper ah2 = new AllianceHelper();
        ah = ah2;
        int ID1 = 0;
        int ID2 = 0;
        boolean isallianced = ah.playerisAllianced(ID1,ID2);
        assertEquals(isallianced,false);
    }

    @Test
    public void test_oneRound() {
        //Success
        Alliance a1 = new Alliance();
        a1.setOwner(0);
        a1.setOwner(1);
        Alliance a2 = new Alliance();
        a1.setOwner(1);
        a1.setOwner(0);
        ah.addCurrentRoundAlliance(a1);
        ah.addCurrentRoundAlliance(a2);
        //Fail
        Alliance a3 = new Alliance();
        a1.setOwner(2);
        a1.setOwner(3);
        ah.addCurrentRoundAlliance(a3);
        ah.executeCurRound();
        String s1 = ah.getAllianceResult(0);
        String s2 = ah.getAllianceResult(1);
        String s3 = ah.getAllianceResult(2);
        String s4 = ah.getAllianceResult(3);
        ah.resetCurRound();
    }
}
