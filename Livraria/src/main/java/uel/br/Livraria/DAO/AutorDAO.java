package uel.br.Livraria.DAO;

import java.sql.SQLException;
import uel.br.Livraria.Model.Autor;

public interface AutorDAO extends DAO<Autor>{
   public Autor getByPnome(String Pnome) throws SQLException;
   public Autor getBySnome(String Snome) throws SQLException;
   public Autor getByNacionalidade(String Nacionalidade) throws SQLException;
}
