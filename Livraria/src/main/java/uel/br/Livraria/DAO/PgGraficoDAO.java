package uel.br.Livraria.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;
import uel.br.Livraria.Model.Grafico;

@Repository
public class PgGraficoDAO implements GraficoDAO{
   private final Connection connection; 
   private static final String GRAFICO_1_QUERY = "SELECT concat(a.Pnome,' ', a.Snome) AS \"Nome Autor\", \n" +
           "      SUM(ps.quantidade) AS Quantidade\n" +
           "FROM livraria.Livro l\n" +
           "JOIN livraria.Possui ps ON ps.ISBN_Livro = l.ISBN\n" +
           "JOIN livraria.Escrito e ON e.ISBN_Livro = l.ISBN\n" +
           "JOIN livraria.Autor a ON e.ID_Autor = a.ID\n" +
           "JOIN livraria.Compra c ON ps.Num_Nota_Fiscal_Compra = c.Num_Nota_Fiscal\n" +
           "WHERE c.Data_Compra >= CURRENT_DATE - 30 AND c.Data_Compra <= CURRENT_DATE\n" +
           "GROUP BY \"Nome Autor\"\n" +
           "ORDER BY Quantidade DESC\n" +
           "LIMIT 5";

   private static final String GRAFICO_2_QUERY = "WITH faixas AS (\r\n" + //
            "    SELECT 'Menos de 14 anos' AS Faixa_Etaria, 0 AS Quantidade\r\n" + //
            "    UNION ALL\r\n" + //
            "    SELECT '14-17 anos', 0\r\n" + //
            "    UNION ALL\r\n" + //
            "    SELECT '18-24 anos', 0\r\n" + //
            "    UNION ALL\r\n" + //
            "    SELECT '25-44 anos', 0\r\n" + //
            "    UNION ALL\r\n" + //
            "    SELECT '45-64 anos', 0\r\n" + //
            "    UNION ALL\r\n" + //
            "    SELECT '65-74 anos', 0\r\n" + //
            "    UNION ALL\r\n" + //
            "    SELECT '75 anos ou mais', 0\r\n" + //
            ")\r\n" + //
            "SELECT \r\n" + //
            "    fxs.Faixa_Etaria,\r\n" + //
            "    COALESCE(COUNT(c.CPF), 0) AS Quantidade\r\n" + //
            "FROM faixas fxs\r\n" + //
            "LEFT JOIN livraria.Cliente c ON \r\n" + //
            "    (CASE \r\n" + //
            "        WHEN fxs.Faixa_Etaria = 'Menos de 14 anos' THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) < 14\r\n" + //
            "        WHEN fxs.Faixa_Etaria = '14-17 anos' THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) BETWEEN 14 AND 17\r\n" + //
            "        WHEN fxs.Faixa_Etaria = '18-24 anos' THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) BETWEEN 18 AND 24\r\n" + //
            "        WHEN fxs.Faixa_Etaria = '25-44 anos' THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) BETWEEN 25 AND 44\r\n" + //
            "        WHEN fxs.Faixa_Etaria = '45-64 anos' THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) BETWEEN 45 AND 64\r\n" + //
            "        WHEN fxs.Faixa_Etaria = '65-74 anos' THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) BETWEEN 65 AND 74\r\n" + //
            "        ELSE EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) >= 75\r\n" + //
            "    END)\r\n" + //
            "GROUP BY fxs.Faixa_Etaria\r\n" + //
            "ORDER BY \r\n" + //
            "\tCASE \r\n" + //
            "\t\tWHEN fxs.Faixa_Etaria LIKE 'Menos%' THEN 0 -- Se a fx começar com 'Menos', 0 será atribuido para que ele seja exibido antes\r\n" + //
            "\t\tELSE 1\r\n" + //
            "\t\tEND,\r\n" + //
            "\t\tfxs.Faixa_Etaria ASC;";

   private static final String GRAFICO_3_QUERY = "SELECT cl.Sexo, AVG(cp.Total) AS Total_Vendas\r\n" + //
            "FROM livraria.Cliente cl\r\n" + //
            "JOIN livraria.Compra cp ON cp.CPF_Cliente = cl.CPF\r\n" + //
            "GROUP BY cl.Sexo\r\n" + //
            "ORDER BY Total_Vendas DESC;"; 
   
   private static final String GRAFICO_4_QUERY = "SELECT CONCAT(cl.Cidade, ' - ', cl.Estado) AS Localizacao, SUM(cp.Total) AS Total_Gasto\r\n" + //
            "FROM livraria.Cliente cl\r\n" + //
            "JOIN livraria.Compra cp ON cp.CPF_Cliente = cl.CPF\r\n" + //
            "WHERE cp.Data_Compra BETWEEN '2023-09-29' AND '2024-10-06'\r\n" + //
            "GROUP BY Localizacao\r\n" + //
            "HAVING SUM(cp.Total)>90\r\n" + //
            "ORDER BY Total_Gasto DESC " + //
            "LIMIT 5;" ;
   
   private static final String GRAFICO_5_QUERY = "SELECT s.Nome, AVG(l.Preco) AS Preco_Medio_Estoque, COALESCE(AVG(psi.Preco),0) as Preco_Medio_Compras\r\n" + //
            "FROM livraria.Livro l\r\n" + //
            "JOIN livraria.Pertence p ON p.ISBN_Livro = l.ISBN\r\n" + //
            "JOIN livraria.Genero g ON g.ID = p.ID_genero\r\n" + //
            "JOIN livraria.Secao s ON g.ID_Secao = s.ID\r\n" + //
            "LEFT JOIN livraria.Possui psi ON psi.ISBN_Livro = l.ISBN\r\n" + //
            "GROUP BY s.Nome\r\n" + //
            "ORDER BY Preco_Medio_Estoque DESC;";
   
   //Vendas por Preço Médio
   private static final String GRAFICO_6_QUERY = "WITH faixas AS (\r\n" + //
            "    SELECT '0 - 9.99' AS Faixa_Preco, 0 AS Unidades_Vendidas\r\n" + //
            "    UNION ALL\r\n" + //
            "    SELECT '10 - 29.99', 0\r\n" + //
            "    UNION ALL\r\n" + //
            "    SELECT '30 - 49.99', 0\r\n" + //
            "    UNION ALL\r\n" + //
            "    SELECT '50 - 99.99', 0\r\n" + //
            "    UNION ALL\r\n" + //
            "    SELECT 'acima de 100', 0\r\n" + //
            ")\r\n" + //
            "SELECT\r\n" + //
            "\tfxs.Faixa_Preco,\r\n" + //
            "\tCOALESCE(SUM(ps.Quantidade), 0) AS Unidades_Vendidas\r\n" + //
            "FROM faixas fxs\r\n" + //
            "LEFT JOIN livraria.Livro l ON\r\n" + //
            "    (CASE \r\n" + //
            "        WHEN fxs.Faixa_Preco = '0 - 9.99' THEN l.Preco <= 9.99\r\n" + //
            "        WHEN fxs.Faixa_Preco ='10 - 29.99' THEN l.Preco BETWEEN 10 AND 29.99\r\n" + //
            "        WHEN fxs.Faixa_Preco ='30 - 49.99' THEN l.Preco BETWEEN 30 AND 49.99\r\n" + //
            "        WHEN fxs.Faixa_Preco ='50 - 99.99' THEN l.Preco BETWEEN 50 AND 99.99\r\n" + //
            "        ELSE l.preco >= 100\r\n" + //
            "    END)\r\n" + //
            "LEFT JOIN livraria.Possui ps ON ps.ISBN_Livro = l.ISBN\r\n" + //
            "GROUP BY Faixa_Preco\r\n" + //
            "ORDER BY Faixa_Preco;";

   //Vendas por mês
   private static final String GRAFICO_7_QUERY =
            "SELECT TO_CHAR(c.data_compra, 'Month') AS mes,\n" +
                "    SUM(p.Preco * p.Quantidade) AS Total_Vendas\n" +
                "FROM livraria.Compra c\n" +
                "JOIN livraria.Possui p ON c.Num_Nota_Fiscal = p.Num_Nota_Fiscal_Compra\n" +
                "GROUP BY c.data_compra\n" +
                "ORDER BY EXTRACT ('Month' From c.data_compra) ASC\n" +
                "LIMIT 6;";

   public PgGraficoDAO(Connection connection) {
      this.connection = connection;
   }

   @Override
   public List<Grafico> getGrafico1() throws SQLException {
      List<Grafico> ResultJSON = new ArrayList<>();
      try (PreparedStatement statement = connection.prepareStatement(GRAFICO_1_QUERY)) {
         try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
               Grafico grafico = new Grafico();
               grafico.setNomeAutor(result.getString("Nome Autor"));
               grafico.setQuantidadeLivrosAutores(result.getInt("Quantidade"));
               ResultJSON.add(grafico);
            }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgGraficoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
  
         throw new SQLException("Erro dados");
      }     
      return ResultJSON;
   }

   @Override
   public List<Grafico> getGrafico2() throws SQLException {
      List<Grafico> ResultJSON = new ArrayList<>();
      try (PreparedStatement statement = connection.prepareStatement(GRAFICO_2_QUERY)) {
         try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
               Grafico grafico = new Grafico();
               grafico.setFaixaEtaria(result.getString("Faixa_Etaria"));
               grafico.setQuantidadePessoasFaixas(result.getInt("Quantidade"));
               ResultJSON.add(grafico);
            }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgGraficoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
  
         throw new SQLException("Erro dados");
      }     
      return ResultJSON;
   }

   @Override
   public List<Grafico> getGrafico3() throws SQLException {
      List<Grafico> ResultJSON = new ArrayList<>();
      try (PreparedStatement statement = connection.prepareStatement(GRAFICO_3_QUERY)) {
         try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
               Grafico grafico = new Grafico();
               grafico.setSexo(result.getString("Sexo").charAt(0));
               grafico.setGastoMedioSexo(result.getBigDecimal("Total_Vendas"));
               ResultJSON.add(grafico);
            }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgGraficoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
  
         throw new SQLException("Erro dados");
      }     
      return ResultJSON;
   }

   @Override
   public List<Grafico> getGrafico4() throws SQLException {
      List<Grafico> ResultJSON = new ArrayList<>();
      try (PreparedStatement statement = connection.prepareStatement(GRAFICO_4_QUERY)) {
         try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
               Grafico grafico = new Grafico();
               grafico.setLocalizacao(result.getString("Localizacao"));
               grafico.setTotalGastoLocalidade(result.getBigDecimal("Total_Gasto"));
               ResultJSON.add(grafico);
            }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgGraficoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
  
         throw new SQLException("Erro dados");
      }     
      return ResultJSON;
   }

   @Override
   public List<Grafico> getGrafico5() throws SQLException {
      List<Grafico> ResultJSON = new ArrayList<>();
      try (PreparedStatement statement = connection.prepareStatement(GRAFICO_5_QUERY)) {
         try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
               Grafico grafico = new Grafico();
               grafico.setSecaoNome(result.getString("Nome"));
               grafico.setPrecoMedioEstoque(result.getBigDecimal("Preco_Medio_Estoque"));
               grafico.setPrecoMedioCompras(result.getBigDecimal("Preco_Medio_Compras"));
               ResultJSON.add(grafico);
            }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgGraficoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
  
         throw new SQLException("Erro dados");
      }     
      return ResultJSON;
   }

   @Override
   public List<Grafico> getGrafico6() throws SQLException {
      List<Grafico> ResultJSON = new ArrayList<>();
      try (PreparedStatement statement = connection.prepareStatement(GRAFICO_6_QUERY)) {
         try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
               Grafico grafico = new Grafico();
               grafico.setFaixaPreco(result.getString("Faixa_Preco"));
               grafico.setUnidadesVendidasFaixa(result.getBigDecimal("Unidades_Vendidas"));
               ResultJSON.add(grafico);
            }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgGraficoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
  
         throw new SQLException("Erro dados");
      }     
      return ResultJSON;
   }

   @Override
   public List<Grafico> getGrafico7() throws SQLException {
      List<Grafico> ResultJSON = new ArrayList<>();
      try (PreparedStatement statement = connection.prepareStatement(GRAFICO_7_QUERY)) {
         try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
               Grafico grafico = new Grafico();
               grafico.setMes(result.getString("Mes"));
               grafico.setTotalVendasMes(result.getBigDecimal("Total_Vendas"));
               ResultJSON.add(grafico);
            }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgGraficoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         throw new SQLException("Erro dados");
      }
      return ResultJSON;
   }

   @Override
   public void create(Grafico t) throws SQLException {
     throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public Grafico read(Integer id) throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void update(Grafico t) throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void delete(Integer id) throws SQLException {
      throw new UnsupportedOperationException("Not supported yet."); 
   }

   @Override
   public List<Grafico> all() throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }           
}
