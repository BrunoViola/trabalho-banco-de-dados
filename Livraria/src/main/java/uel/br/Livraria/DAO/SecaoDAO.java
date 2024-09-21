package uel.br.Livraria.DAO;

import java.sql.SQLException;
import uel.br.Livraria.Model.Secao;

public interface SecaoDAO extends DAO<Secao, Integer>{
   public Secao getByNome(String Nome) throws SQLException;
}
