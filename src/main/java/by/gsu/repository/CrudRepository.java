package by.gsu.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    List<T> findAll();

    Optional<T> findById(int id);

    void add(T note);

    void update(T note);

    void delete(T note);

}
