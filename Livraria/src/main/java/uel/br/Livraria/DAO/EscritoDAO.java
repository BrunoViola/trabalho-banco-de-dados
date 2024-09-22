package uel.br.Livraria.DAO;

import uel.br.Livraria.Model.Escrito;

import java.sql.SQLException;

public interface EscritoDAO extends DAO<Escrito, Integer>{
    //Escrito read(Integer ID_Autor, Long ISBN_Livro) throws SQLException;
    public void delete(Integer ID_Autor, Long ISBN_Livro) throws SQLException;
}
