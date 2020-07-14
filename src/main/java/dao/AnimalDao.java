package dao;

import models.Animal;
import java.util.List;

public interface AnimalDao {

    // LIST
    List<Animal> getAll();

    // CREATE
    void add(Animal animal);

    // READ
    Animal findById(int id);

    // DELETE
    void deleteById(int id);
    void clearAllAnimals();
}
