package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

public class AllianceTest {
    @Test
    public void Test_Alliance(){
        Alliance Test = new Alliance();
        Test.setOwner(0);
        Test.setAlly(1);
        assert (0 == Test.getOwner());
        assert (1 == Test.getAlly());
        AlliancetoJson Converter = new AlliancetoJson(Test);
        Converter.getJSON();
        MyFormatter Format = new MyFormatter(2);
        Alliance Parsed = new Alliance();
        // With Alliance Information
        Format.AllianceParse(Parsed, Format.AllianceCompose(Test).toString());
        Alliance Empty = new Alliance();

        Format.AllianceParse(Parsed, Format.AllianceCompose(Empty).toString());
    }
}
