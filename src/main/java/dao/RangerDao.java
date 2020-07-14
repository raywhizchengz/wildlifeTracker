package dao;

import models.Endangered;
import models.Ranger;
import models.Animal;
import java.util.List;

public interface RangerDao {

    //LIST
    List<Ranger> getAll();

    //CREATE
    void add(Ranger ranger);

    //READ
    Ranger findById(int id);

    //UPDATE
    void update(int id, String rangerName, String rangerLocation);

    //DELETE
    void deleteById(int id);
    void clearAllRangers();

    List<Animal> getAllAnimalsScoutedByRanger(int rangerId);
    List<Endangered> getAllEndangeredSightedByRanger(int rangerId);
}