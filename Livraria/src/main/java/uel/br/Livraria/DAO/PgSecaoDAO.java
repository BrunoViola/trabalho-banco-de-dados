package uel.br.Livraria.DAO;

import uel.br.Livraria.Model.Secao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PgSecaoDAO implements SecaoDAO{
   private final Connection connection;

   public PgSecaoDAO(Connection connection) {
      this.connection = connection;
   }

   private static final String CREATE_QUERY =
                                "INSERT INTO livraria.Secao(Nome) VALUES(?);";

   private static final String READ_QUERY =
                                "SELECT Nome FROM livraria.Secao WHERE ID = ?;";

   private static final String UPDATE_QUERY =
                                "UPDATE livraria.Secao SET Nome = ? WHERE ID = ?;";

   private static final String DELETE_QUERY =
                                "DELETE FROM livraria.Secao WHERE ID = ?;";

   private static final String ALL_QUERY =
                                "SELECT id, Nome FROM livraria.Secao ORDER BY ID;";

   private static final String GET_BY_NOME =
                                "SELECT id, Nome FROM livraria.Secao " +
                                "WHERE Nome = ?;";       
   
   
   // ====== BUSCA POR PNOME =====
   public Secao getByNome(String Nome) throws SQLException {
      Secao secao = new Secao();
      try (PreparedStatement statement = connection.prepareStatement(GET_BY_NOME)) {
         statement.setString(1, Nome);
         try (ResultSet result = statement.executeQuery()) {
               if (result.next()) {
                  secao.setID(result.getInt("ID"));
                  secao.setNome(result.getString("Nome"));
               } else {
                  throw new SQLException("Erro ao visualizar: seção não encontrada.");
               }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgSecaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
         if (ex.getMessage().equals("Erro ao visualizar: seção não encontrada.")) {
            throw ex;
         } else {
            throw new SQLException("Erro ao visualizar seção.");
         }
      }
      return secao;
   }
                                
                                
   // ===== CREATE SECAO =====                             
   @Override
    public void create(Secao secao) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, secao.getNome());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgSecaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if(ex.getMessage().contains("pk_Secao")) {
               throw new SQLException("Erro ao inserir seção: ID duplicado.");
           }else if (ex.getMessage().contains("not-null")) {
               throw new SQLException("Erro ao inserir seção: campo obrigatório está em branco.");
           }else {
               throw new SQLException("Erro ao inserir seção.");
           }
        }        
    }
   
   // ===== READ SECAO =====
   @Override
   public Secao read(Integer ID) throws SQLException {
      Secao secao = new Secao();

      try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
         statement.setInt(1, ID);
         try (ResultSet result = statement.executeQuery()) {
               if (result.next()) {
                  secao.setID(ID);
                  secao.setNome(result.getString("Nome"));
               } else {
                  throw new SQLException("Erro ao visualizar: seção não encontrada.");
               }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgSecaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
         if (ex.getMessage().equals("Erro ao visualizar: seção não encontrada.")) {
            throw ex;
         } else {
            throw new SQLException("Erro ao visualizar seção.");
         }
      }

      return secao;
   } 

   // ===== UPDATE SECAO =====
   @Override
   public void update(Secao secao) throws SQLException {
      String query = UPDATE_QUERY;

      try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setString(1, secao.getNome());
         statement.setInt(2, secao.getID());

         if (statement.executeUpdate() < 1) {
            throw new SQLException("Erro ao editar: seção não encontrada.");
         }
       } catch (SQLException ex) {
         Logger.getLogger(PgSecaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
 
         if (ex.getMessage().equals("Erro ao editar: seção não encontrada.")) {
             throw ex;
         } else if (ex.getMessage().contains("not-null")) {
             throw new SQLException("Erro ao editar seção: campo obrigatório está em branco.");
         } else {
             throw new SQLException("Erro ao editar seção.");
         }
     }        
   }
   
   // ===== DELETE SECAO =====
   @Override
   public void delete(Integer ID) throws SQLException {
     try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
         statement.setInt(1, ID);

         if (statement.executeUpdate() < 1) {
             throw new SQLException("Erro ao excluir: seção não encontrada.");
         }
     } catch (SQLException ex) {
         Logger.getLogger(PgSecaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         if (ex.getMessage().equals("Erro ao excluir: seção não encontrada.")) {
             throw ex;
         } else {
             throw new SQLException("Erro ao excluir seção.");
         }
      }
   }

   // ===== LIST ALL SECOES =====
   @Override
   public List<Secao> all() throws SQLException {
      List<Secao> secaoList = new ArrayList<>();

      try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
         ResultSet result = statement.executeQuery()) {
         while (result.next()) {
            Secao secao = new Secao();
            secao.setID(result.getInt("ID"));
            secao.setNome(result.getString("Nome"));

            secaoList.add(secao);
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgSecaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         throw new SQLException("Erro ao listar seções.");
      }

      return secaoList;        
   }
}