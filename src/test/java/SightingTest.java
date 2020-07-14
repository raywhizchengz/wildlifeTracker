import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SightingTest{

    @Rule
    public DatabaseRule database = new DatabaseRule();

    private Sighting testSighting;

    @Before
    public void setUp() {
        Timestamp now = new Timestamp(new Date().getTime());
        testSighting = new Sighting(1, "Zone A", "Kamau");
    }

    @After
    public void tearDown(){
        Sighting.clearAll();
    }

    @Test
    public void Sighting_instantiatesCorrectly() {
        assertTrue(testSighting instanceof Sighting);
    }

    @Test
    public void getId_sightingInstantiatesWithId(){
        testSighting.save();
        assertTrue(testSighting.getId() > 0);
    }

    @Test
    public void getAnimalId_sightingInstantiatesWithAnimalId(){
        testSighting.save();
        assertEquals(1, testSighting.getAnimalId());
    }

    @Test
    public void getLocation_sightingInstantiatesWithLocation(){
        testSighting.save();
        assertEquals("Zone A", testSighting.getLocation());
    }

    @Test
    public void getRangerName_sightingInstantiatesWithRangerName(){
        testSighting.save();
        assertEquals("Kamau", testSighting.getRangerName());
    }
    @Test
    public void equals_returnsTrueIfAllPropertiesAreTheSame() {
        Timestamp rightNow = new Timestamp(new Date().getTime());
        Sighting anotherSighting = new Sighting(1, "Zone A", "Kamau");
        assertTrue(testSighting.equals(anotherSighting));
    }

    @Test
    public void save_assignsIdToObject() {
        testSighting.save();
        Sighting savedSighting = Sighting.all().get(0);
        assertEquals(testSighting.getId(), savedSighting.getId());
    }

    @Test
    public void save_insertsObjectIntoDatabase() {
        testSighting.save();
        assertTrue(Sighting.all().get(0).equals(testSighting));
    }







}