package dao;

import models.Endangered;
import org.sql2o.*;
import java.util.List;

public class Sql2oEndangeredDao implements EndangeredDao {

    private final Sql2o sql2o;

    public Sql2oEndangeredDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Endangered endangered){
        String sql = "INSERT INTO endAnimals (endangeredName, endangeredHealth, rangerId) VALUES (:endangeredName, :endangeredHealth,:rangerId)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(endangered)
                    .executeUpdate()
                    .getKey();
            endangered.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Endangered> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM endAnimals")
                    .executeAndFetch(Endangered.class);
        }
    }

    @Override
    public Endangered findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM endAnimals WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Endangered.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from endAnimals WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllEndangered() {
        String sql = "DELETE from endAnimals";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}