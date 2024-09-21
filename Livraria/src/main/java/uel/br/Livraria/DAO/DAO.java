package uel.br.Livraria.DAO;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T, Tchave> {

    public void create(T t) throws SQLException;
    public T read(Tchave id) throws SQLException;
    public void update(T t) throws SQLException;
    public void delete(Tchave id) throws SQLException;
    public List<T> all() throws SQLException;

}
