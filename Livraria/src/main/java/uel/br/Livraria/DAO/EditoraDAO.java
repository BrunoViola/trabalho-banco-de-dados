package uel.br.Livraria.DAO;

import java.sql.SQLException;
import uel.br.Livraria.Model.Editora;

public interface EditoraDAO extends DAO<Editora, Integer>{
   public Editora getByNome(String Nome) throws SQLException;
}
