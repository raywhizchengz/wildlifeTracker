package dao;

import models.Endangered;
import java.util.List;

public interface EndangeredDao {

    // LIST
    List<Endangered> getAll();

    // CREATE
    void add(Endangered endangered);

    // READ
    Endangered findById(int id);

    // DELETE
    void deleteById(int id);
    void clearAllEndangered();
}