package uel.br.Livraria.DAO;

import java.sql.SQLException;
import uel.br.Livraria.Model.Genero;
import uel.br.Livraria.Model. Secao;

public interface GeneroDAO extends DAO<Genero, Integer>{
   public Genero getByNomeIDSecao(String Nome, Secao secao) throws SQLException;
}
