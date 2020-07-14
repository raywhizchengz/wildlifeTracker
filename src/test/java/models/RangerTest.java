package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class RangerTest {
    Ranger testRanger(){
        return new Ranger("Tarzan", "jungle");
    }

    @Test
    public void rangerObjectGetsInstantiatedCorrectly() throws Exception{
        Ranger ranger = testRanger();
        assertTrue(ranger instanceof Ranger);
    }

    @Test
    public void rangerGetsRangerName() throws Exception{
        Ranger ranger = testRanger();
        String name = ranger.getRangerName();
        assertEquals("Tarzan", name);
    }

    @Test
    public void rangerGetsRangerLocation() throws Exception{
        Ranger ranger = testRanger();
        String location = ranger.getRangerLocation();
        assertEquals("jungle", location);
    }
}