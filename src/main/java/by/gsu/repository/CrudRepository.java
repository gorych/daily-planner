package by.gsu.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    List<T> findAll();

    Optional<T> findById(int id);

    void add(T entity);

    void update(T entity);

    void delete(T entity);

    void delete(List<T> entities);

}
