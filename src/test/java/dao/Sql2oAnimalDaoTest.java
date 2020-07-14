package dao;

import models.Animal;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Sql2oAnimalDaoTest {
    private static Sql2oAnimalDao animalDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/wildlife_tracker_test";
        Sql2o sql2o = new Sql2o(connectionString, "ray", "12345");
//        String connectionString = "jdbc:postgresql://ec2-35-168-54-239.compute-1.amazonaws.com:5432/duvdiicoms0ov";
//        Sql2o sql2o = new Sql2o(connectionString, "subsnlwvrpuxmf", "b32afb160a1086689aba010525ba26005db5d9ab925a97fa9a448111c5f738da");
        animalDao = new Sql2oAnimalDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        animalDao.clearAllAnimals();
        conn.close();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
    }

    Animal testAnimal(){
        return new Animal("Lion", 1);
    }

    @Test
    public void addingAnAnimalSetsId() throws Exception{
        Animal animal = testAnimal();
        int animalId = animal.getId();
        animalDao.add(animal);
        assertNotEquals(animalId, animal.getId());
    }

    @Test
    public void anAnimalCanBeFoundById() throws Exception{
        Animal animal = testAnimal();
        animalDao.add(animal);
        Animal foundAnimal = animalDao.findById(animal.getId());
        assertEquals(foundAnimal, animalDao.findById(animal.getId()));
    }

    @Test
    public void listOfAllAnimalsCanBeRetrived() throws Exception{
        Animal animal = testAnimal();
        animalDao.add(animal);
        assertEquals(1, animalDao.getAll().size());
    }

    @Test
    public void ifNoAnimalReturnsZero() throws Exception{
        assertEquals(0, animalDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesASingleAnimal() throws Exception {
        Animal animal = testAnimal();
        animalDao.add(animal);
        animalDao.deleteById(animal.getId());
        assertEquals(0, animalDao.getAll().size());
    }

    @Test
    public void clearAllDeletesAllAnimals() throws Exception{
        Animal animal = testAnimal();
        Animal anotherAnimal = new Animal("Zebra", 2);
        animalDao.add(animal);
        animalDao.add(anotherAnimal);
        animalDao.clearAllAnimals();
        assertEquals(0, animalDao.getAll().size());
    }

    @Test
    public void rangerIdIsReturnedCorrectly() throws Exception {
        Animal animal = testAnimal();
        int originalRangerId = animal.getRangerId();
        animalDao.add(animal);
        assertEquals(originalRangerId, animalDao.findById(animal.getId()).getRangerId());
    }
}