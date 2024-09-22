package uel.br.Livraria.DAO;

import uel.br.Livraria.Model.Pertence;

import java.sql.SQLException;

public interface PertenceDAO extends DAO<Pertence, Integer>{
   public void delete(Integer ID_Genero, Long ISBN_Livro) throws SQLException;
}
