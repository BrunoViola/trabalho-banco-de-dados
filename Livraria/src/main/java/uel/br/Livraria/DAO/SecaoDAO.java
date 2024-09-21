package uel.br.Livraria.DAO;

import java.sql.SQLException;
import uel.br.Livraria.Model.Secao;

public interface SecaoDAO extends DAO<Secao>{
   public Secao getByNome(String Nome) throws SQLException;
}
