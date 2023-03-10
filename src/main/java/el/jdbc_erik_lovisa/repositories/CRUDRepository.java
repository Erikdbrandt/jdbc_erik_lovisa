package el.jdbc_erik_lovisa.repositories;

import java.util.List;

public interface CRUDRepository<T, U> {
    List<T> findAll();
    T findById(U id);
    int insert(T object);
    int update(T object);
    int delete(T object);
    int deleteById(U id);
}