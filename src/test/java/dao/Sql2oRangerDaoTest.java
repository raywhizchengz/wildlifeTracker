package dao;

import models.Endangered;
import models.Ranger;
import models.Animal;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oRangerDaoTest {
    private static Sql2oRangerDao rangerDao;
    private static Sql2oAnimalDao animalDao;
    private static Sql2oEndangeredDao endangeredDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/wildlife_tracker_test";
        Sql2o sql2o = new Sql2o(connectionString, "ray", "12345");
//        String connectionString = "jdbc:postgresql://ec2-35-168-54-239.compute-1.amazonaws.com:5432/duvdiicoms0ov";
//        Sql2o sql2o = new Sql2o(connectionString, "subsnlwvrpuxmf", "b32afb160a1086689aba010525ba26005db5d9ab925a97fa9a448111c5f738da");
        rangerDao = new Sql2oRangerDao(sql2o);
        animalDao = new Sql2oAnimalDao(sql2o);
        endangeredDao = new Sql2oEndangeredDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        rangerDao.clearAllRangers();
        animalDao.clearAllAnimals();
        endangeredDao.clearAllEndangered();
        conn.close();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
    }

    public Ranger testRanger(){
        return new Ranger("Tarzan", "jungle");
    }

    @Test
    public void addingARangerSetsId() throws Exception{
        Ranger ranger = testRanger();
        int rangerId = ranger.getId();
        rangerDao.add(ranger);
        assertNotEquals(rangerId, ranger.getId());
    }

    @Test
    public void rangerCanFindRangerById() throws Exception{
        Ranger ranger = testRanger();
        rangerDao.add(ranger);
        Ranger foundRanger = rangerDao.findById(ranger.getId());
        assertEquals(ranger, foundRanger);
    }

    @Test
    public void getAllRangerRetunsAllAddedRangers() throws Exception{
        Ranger ranger = testRanger();
        Ranger anotherRanger = new Ranger("Moli", "riverside");
        rangerDao.add(ranger);
        rangerDao.add(anotherRanger);
        assertEquals(2, rangerDao.getAll().size());
    }

    @Test
    public void ifNoRangerAddedReturnsEmpty() throws Exception{
        assertEquals(0, rangerDao.getAll().size());
    }

    @Test
    public void updateRangersInformation() throws Exception{
        Ranger ranger = testRanger();
        rangerDao.add(ranger);
        rangerDao.update(ranger.getId(), "moli", "grasslands");
        Ranger newRanger = rangerDao.findById(ranger.getId());
        assertNotEquals(ranger.getRangerName(), newRanger.getRangerName());
        assertNotEquals(ranger.getRangerLocation(), newRanger.getRangerLocation());
    }

    @Test
    public void deleteByIdDeletesSingleRanger() throws Exception {
        Ranger ranger = testRanger();
        Ranger anotherRanger = new Ranger("Moli", "riverside");
        rangerDao.add(ranger);
        rangerDao.add(anotherRanger);
        rangerDao.deleteById(ranger.getId());
        assertEquals(1, rangerDao.getAll().size());
    }

    @Test
    public void deleteAllClearsAllRangers() throws Exception{
        Ranger ranger = testRanger();
        Ranger anotherRanger = new Ranger("Moli", "riverside");
        rangerDao.add(ranger);
        rangerDao.add(anotherRanger);
        rangerDao.clearAllRangers();
        assertEquals(0, rangerDao.getAll().size());
    }

    @Test
    public void getAllAnimalsScoutedBySpecificRangerReturnsAnimalCorrectly() throws Exception {
        Ranger ranger = testRanger();
        rangerDao.add(ranger);
        int rangerId = ranger.getId();
        Animal newAnimal = new Animal("Lion", rangerId);
        Animal newAnimal2 = new Animal("Simba", rangerId);
        Animal newAnimal3 = new Animal("Tiger", rangerId);
        animalDao.add(newAnimal);
        animalDao.add(newAnimal2);
        assertEquals(2, rangerDao.getAllAnimalsScoutedByRanger(rangerId).size());
        assertTrue(rangerDao.getAllAnimalsScoutedByRanger(rangerId).contains(newAnimal));
        assertTrue(rangerDao.getAllAnimalsScoutedByRanger(rangerId).contains(newAnimal2));
        assertFalse(rangerDao.getAllAnimalsScoutedByRanger(rangerId).contains(newAnimal3));
    }

//    @Test
//    public void getAllEndangeredAnimalsSightedBySpecificRangerReturnsAnimalCorrectly() throws Exception {
//        Ranger ranger = testRanger();
//        rangerDao.add(ranger);
//        int rangerId = ranger.getId();
//        Endangered testEndangered1 = new Endangered("rhino", "okay",rangerId);
//        Endangered testEndangered2 = new Endangered("gorilla", "okay",rangerId);
//        Endangered testEndangered3 = new Endangered("tiger", "okay",rangerId);
//        endangeredDao.add(testEndangered1);
//        endangeredDao.add(testEndangered2);
//        assertEquals(2, rangerDao.getAllEndangeredSightedByRanger(rangerId).size());
//        assertTrue(rangerDao.getAllEndangeredSightedByRanger(rangerId).contains(testEndangered1));
//        assertTrue(rangerDao.getAllEndangeredSightedByRanger(rangerId).contains(testEndangered2));
//        assertFalse(rangerDao.getAllEndangeredSightedByRanger(rangerId).contains(testEndangered3));
//    }
}