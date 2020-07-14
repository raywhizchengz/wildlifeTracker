package dao;

import models.Endangered;
import models.Ranger;
import models.Animal;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

public class Sql2oRangerDao implements RangerDao {

    private final Sql2o sql2o;

    public Sql2oRangerDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Ranger ranger) {
        String sql = "INSERT INTO rangers (rangerName, rangerLocation) VALUES (:rangerName, :rangerLocation)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(ranger)
                    .executeUpdate()
                    .getKey();
            ranger.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Ranger> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM rangers")
                    .executeAndFetch(Ranger.class);
        }
    }

    @Override
    public Ranger findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM rangers WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Ranger.class);
        }
    }

    @Override
    public void update(int id, String newRangerName, String newRangerLocation){
        String sql = "UPDATE rangers SET (rangerName, rangerLocation) = (:rangerName, :rangerLocation) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("rangerName", newRangerName)
                    .addParameter("rangerLocation", newRangerLocation)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from rangers WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllRangers() {
        String sql = "DELETE from rangers";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Animal> getAllAnimalsScoutedByRanger(int rangerId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM sightingAnimals WHERE rangerId = :rangerId")
                    .addParameter("rangerId", rangerId)
                    .executeAndFetch(Animal.class);
        }
    }

    @Override
    public List<Endangered> getAllEndangeredSightedByRanger(int rangerId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM endAnimals WHERE rangerId = :rangerId")
                    .addParameter("rangerId", rangerId)
                    .executeAndFetch(Endangered.class);
        }
    }
}