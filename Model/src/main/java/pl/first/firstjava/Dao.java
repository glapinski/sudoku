package pl.first.firstjava;

import java.sql.SQLException;
import pl.first.firstjava.exception.DaoException;


public interface Dao<T> {
    T read() throws DaoException;

    void write(T obj) throws SQLException, DaoException;
}
