package Main.Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T> {

    T getObject(int Id) throws SQLException;

    void create(T obj) throws SQLException;

    List<T> getAll() throws SQLException;

    void update(T obj) throws SQLException;

    void delete(int id) throws SQLException;

}
