package uel.br.Livraria.DAO;

import uel.br.Livraria.Model.Grafico;
import java.sql.SQLException;
import java.util.List;

public interface GraficoDAO extends DAO<Grafico, Integer>{
   public List<Grafico> getGrafico1() throws SQLException;
   public List<Grafico> getGrafico2() throws SQLException;
   public List<Grafico> getGrafico3() throws SQLException;
   public List<Grafico> getGrafico4() throws SQLException;
   public List<Grafico> getGrafico5() throws SQLException;
   public List<Grafico> getGrafico6() throws SQLException;
   public List<Grafico> getGrafico7() throws SQLException;
}