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

}
