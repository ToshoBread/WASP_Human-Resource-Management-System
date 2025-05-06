package wasp.DAO;

import java.sql.SQLException;
import java.util.List;

public abstract class DAO<T> {

    public abstract T getById(int id) throws SQLException;

    public abstract List<T> getAll() throws SQLException;

    public abstract void insert(T t) throws SQLException;

    public abstract void update(T t) throws SQLException;

    public abstract void remove(T t) throws SQLException;

}
