package dao;

import models.Endangered;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Sql2oEndangeredDaoTest {
    private static Sql2oEndangeredDao endangeredDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/wildlife_tracker_test";
        Sql2o sql2o = new Sql2o(connectionString, "ray", "12345");
//        String connectionString = "jdbc:postgresql://ec2-35-168-54-239.compute-1.amazonaws.com:5432/duvdiicoms0ov";
//        Sql2o sql2o = new Sql2o(connectionString, "subsnlwvrpuxmf", "b32afb160a1086689aba010525ba26005db5d9ab925a97fa9a448111c5f738da");
        endangeredDao = new Sql2oEndangeredDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        endangeredDao.clearAllEndangered();
        conn.close();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
    }

    Endangered testEndangered(){
        return new Endangered("tiger","okay", 1);
    }

    @Test
    public void addingAnEndangeredAnimalSetsId() throws Exception{
        Endangered endangered = testEndangered();
        int endangeredId = endangered.getId();
        endangeredDao.add(endangered);
        assertNotEquals(endangeredId, endangered.getId());
    }

    @Test
    public void anAnimalCanBeFoundById() throws Exception{
        Endangered endangered = testEndangered();
        endangeredDao.add(endangered);
        Endangered foundEndangered = endangeredDao.findById(endangered.getId());
        assertEquals(foundEndangered, endangeredDao.findById(endangered.getId()));
    }

    @Test
    public void listOfAllAnimalsCanBeRetrived() throws Exception{
        Endangered endangered = testEndangered();
        endangeredDao.add(endangered);
        assertEquals(1, endangeredDao.getAll().size());
    }

    @Test
    public void ifNoAnimalReturnsZero() throws Exception{
        assertEquals(0, endangeredDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesASingleAnimal() throws Exception {
        Endangered endangered = testEndangered();
        endangeredDao.add(endangered);
        endangeredDao.deleteById(endangered.getId());
        assertEquals(0, endangeredDao.getAll().size());
    }

    @Test
    public void clearAllDeletesAllAnimals() throws Exception{
        Endangered endangered = testEndangered();
        Endangered anotherEndangered = new Endangered("gorilla","okay", 2);
        endangeredDao.add(endangered);
        endangeredDao.add(anotherEndangered);
        endangeredDao.clearAllEndangered();
        assertEquals(0, endangeredDao.getAll().size());
    }

    @Test
    public void rangerIdIsReturnedCorrectly() throws Exception {
        Endangered endangered = testEndangered();
        int originalRangerId = endangered.getRangerId();
        endangeredDao.add(endangered);
        assertEquals(originalRangerId, endangeredDao.findById(endangered.getId()).getRangerId());
    }
}