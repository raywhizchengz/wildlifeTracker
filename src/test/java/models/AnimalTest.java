package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalTest {
    Animal testAnimal(){
        return new Animal("Lion", 1);
    }

    @Test
    public void newAnimalGetsInstantiatedCorrectly_true() throws Exception{
        Animal animal = testAnimal();
        assertEquals(true, animal instanceof Animal);
    }

    @Test
    public void  newAnimalgetsName() throws Exception{
        Animal animal = testAnimal();
        String name = testAnimal().getAnimalName();
        assertEquals("Lion", name);
    }
}