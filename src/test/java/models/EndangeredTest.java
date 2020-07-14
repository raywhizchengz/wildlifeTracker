package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class EndangeredTest {
    Endangered testExtinct(){
        return new Endangered("tiger", "okay", 1);
    }

    @Test
    public void extinctAnimalInstantiatesCorrectly_true(){
        Endangered newEndangered = testExtinct();
        assertEquals(true, newEndangered instanceof Endangered);
    }

    @Test
    public void extinctAnimalGetsNameCorectly_tiger(){
        Endangered newEndangered = testExtinct();
        assertEquals("tiger", newEndangered.getEndangeredName());
    }

    @Test
    public void extinctAnimalGetsHealthCorectly_okay(){
        Endangered newEndangered = testExtinct();
        assertEquals("okay", newEndangered.getEndangeredHealth());
    }

    @Test
    public void extinctAnimalGetsRangerId_1(){
        Endangered newEndangered = testExtinct();
        assertEquals(1, newEndangered.getRangerId());
    }

    @Test
    public void endangeredAnimalSettersWorkCorrectly() throws Exception{
        Endangered newEndangered = testExtinct();
        newEndangered.setEndangeredName("rhino");
        newEndangered.setEndangeredHealth("healthy");
        newEndangered.setRangerId(2);
        assertEquals("rhino", newEndangered.getEndangeredName());
        assertEquals("healthy", newEndangered.getEndangeredHealth());
        assertEquals(2, newEndangered.getRangerId());
    }
}