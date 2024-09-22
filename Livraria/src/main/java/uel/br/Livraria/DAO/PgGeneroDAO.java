package uel.br.Livraria.DAO;

import uel.br.Livraria.Model.Genero;
import uel.br.Livraria.Model.Secao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PgGeneroDAO implements GeneroDAO{
   private final Connection connection;

   private final PgSecaoDAO secaoDAO;

   public PgGeneroDAO(Connection connection) {
      this.connection = connection;
      this.secaoDAO = new PgSecaoDAO(connection);
   }

   private static final String CREATE_QUERY =
                                "INSERT INTO livraria.Genero (Nome, ID_Secao) VALUES (?, ?);";

   private static final String READ_QUERY =
                                "SELECT Nome, ID_Secao FROM livraria.Genero WHERE ID = ?;";

   private static final String UPDATE_QUERY =
                                "UPDATE livraria.Genero SET Nome = ?, ID_Secao = ? WHERE ID = ?;";

   private static final String DELETE_QUERY =
                                "DELETE FROM livraria.Genero WHERE ID = ?;";

   private static final String ALL_QUERY =
                                "SELECT ID, Nome, ID_Secao FROM livraria.Genero ORDER BY ID;";

   private static final String GET_BY_NOME_IDSECAO =
                                "SELECT ID, Nome FROM livraria.Genero " +
                                "WHERE Nome = ? AND ID_SECAO = ?;";       
   
   
   // ====== BUSCA POR Nome e ID_Secao =====
   public Genero getByNomeIDSecao(String Nome, Secao secao) throws SQLException {
      Genero genero = new Genero();
      try (PreparedStatement statement = connection.prepareStatement(GET_BY_NOME_IDSECAO)) {
         statement.setString(1, Nome);
         statement.setInt(2, secao.getID());
         try (ResultSet result = statement.executeQuery()) {
               if (result.next()) {
                  genero.setID(result.getInt("ID"));
                  genero.setNome(result.getString("Nome"));
               } else {
                  throw new SQLException("Erro ao visualizar: gênero não encontrado.");
               }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgGeneroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
         if (ex.getMessage().equals("Erro ao visualizar: gênero não encontrado.")) {
            throw ex;
         } else {
            throw new SQLException("Erro ao visualizar gênero.");
         }
      }
      return genero;
   }
                                
                                
   // ===== CREATE GENERO =====                             
   @Override
   public void create(Genero genero) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, genero.getNome());
            statement.setInt(2, genero.getSecao().getID());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgGeneroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if(ex.getMessage().contains("pk_Genero")) {
               throw new SQLException("Erro ao inserir gênero: ID duplicado.");
           }else if (ex.getMessage().contains("not-null")) {
               throw new SQLException("Erro ao inserir gênero: campo obrigatório está em branco.");
           }else {
               throw new SQLException("Erro ao inserir gênero.");
           }
        }        
    }
   
   // ===== READ GENERO =====
   @Override
   public Genero read(Integer ID) throws SQLException {
      Genero genero = new Genero();

      try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
         statement.setInt(1, ID);
         try (ResultSet result = statement.executeQuery()) {
               if (result.next()) {
                  genero.setID(ID);
                  genero.setNome(result.getString("Nome"));
                  
                  Secao secao = secaoDAO.read(result.getInt("ID_Secao"));
                  genero.setSecao(secao);
               } else {
                  throw new SQLException("Erro ao visualizar: gênero não encontrado.");
               }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgGeneroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
         if (ex.getMessage().equals("Erro ao visualizar: gênero não encontrado.")) {
            throw ex;
         } else {
            throw new SQLException("Erro ao visualizar gênero.");
         }
      }

      return genero;
   } 

   // ===== UPDATE GENERO =====
   @Override
   public void update(Genero genero) throws SQLException {
      String query = UPDATE_QUERY;

      try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setString(1, genero.getNome());
         statement.setInt(2, genero.getSecao().getID());
         statement.setInt(3, genero.getID());

         if (statement.executeUpdate() < 1) {
            throw new SQLException("Erro ao editar: gênero não encontrado.");
         }
       } catch (SQLException ex) {
         Logger.getLogger(PgGeneroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
 
         if (ex.getMessage().equals("Erro ao editar: gênero não encontrado.")) {
             throw ex;
         } else if (ex.getMessage().contains("not-null")) {
             throw new SQLException("Erro ao editar gênero: campo obrigatório está em branco.");
         } else {
             throw new SQLException("Erro ao editar gênero.");
         }
     }        
   }
   
   // ===== DELETE GENERO =====
   @Override
   public void delete(Integer ID) throws SQLException {
     try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
         statement.setInt(1, ID);

         if (statement.executeUpdate() < 1) {
             throw new SQLException("Erro ao excluir: gênero não encontrado.");
         }
     } catch (SQLException ex) {
         Logger.getLogger(PgGeneroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         if (ex.getMessage().equals("Erro ao excluir: gênero não encontrado.")) {
             throw ex;
         } else {
             throw new SQLException("Erro ao excluir gênero.");
         }
      }
   }

   // ===== LIST ALL GENEROS =====
   @Override
   public List<Genero> all() throws SQLException {
      List<Genero> generoList = new ArrayList<>();

      try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
         ResultSet result = statement.executeQuery()) {
         while (result.next()) {
            Genero genero = new Genero();
            genero.setID(result.getInt("ID"));
            genero.setNome(result.getString("Nome"));
            Secao secao = secaoDAO.read(result.getInt("ID_Secao"));
            genero.setSecao(secao);
            generoList.add(genero);
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgGeneroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         throw new SQLException("Erro ao listar gêneros.");
      }

      return generoList;        
   }
}