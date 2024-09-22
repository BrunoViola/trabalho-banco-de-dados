package uel.br.Livraria.DAO;

import org.springframework.stereotype.Repository;
import uel.br.Livraria.Model.Autor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class PgAutorDAO implements AutorDAO{
   private final Connection connection;
   public PgAutorDAO(Connection connection) {
      this.connection = connection;
   }

   private static final String CREATE_QUERY =
                                "INSERT INTO livraria.Autor(Pnome, Snome, Nacionalidade) " + "VALUES(?, ?, ?);";

   private static final String READ_QUERY =
                                "SELECT Pnome, Snome, Nacionalidade FROM livraria.Autor " +
                                "WHERE ID = ?;";

   private static final String UPDATE_QUERY =
                                "UPDATE livraria.Autor " +
                                "SET Pnome = ?, Snome = ?, Nacionalidade = ? " +
                                "WHERE ID = ?;";

   private static final String DELETE_QUERY =
                                "DELETE FROM livraria.Autor WHERE ID = ?;";

   private static final String ALL_QUERY =
                                "SELECT ID, Pnome, Snome, Nacionalidade FROM livraria.Autor ORDER BY ID;";

   // ===== CREATE AUTOR =====                             
   @Override
    public void create(Autor autor) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, autor.getPnome());
            statement.setString(2, autor.getSnome());
            statement.setString(3, autor.getNacionalidade());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgAutorDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if(ex.getMessage().contains("pk_Autor")) {
               throw new SQLException("Erro ao inserir autor: ID duplicado.");
           }else if (ex.getMessage().contains("not-null")) {
               throw new SQLException("Erro ao inserir autor: pelo menos um campo obrigatório está em branco.");
           }else {
               throw new SQLException("Erro ao inserir autor.");
           }
        }        
    }
   
   // ===== READ AUTOR =====
   @Override
   public Autor read(Integer ID) throws SQLException {
      Autor autor = new Autor();

      try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
         statement.setInt(1, ID);
         try (ResultSet result = statement.executeQuery()) {
               if (result.next()) {
                  autor.setID(ID);
                  autor.setPnome(result.getString("Pnome"));
                  autor.setSnome(result.getString("Snome"));
                  autor.setNacionalidade(result.getString("Nacionalidade"));
               } else {
                  throw new SQLException("Erro ao visualizar: autor não encontrado.");
               }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgAutorDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
         if (ex.getMessage().equals("Erro ao visualizar: autor não encontrado.")) {
            throw ex;
         } else {
            throw new SQLException("Erro ao visualizar autor.");
         }
      }

      return autor;
   } 

   // ===== UPDATE AUTOR =====
   @Override
   public void update(Autor autor) throws SQLException {
      String query = UPDATE_QUERY;

      try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setString(1, autor.getPnome());
         statement.setString(2, autor.getSnome());
         statement.setString(3, autor.getNacionalidade());
         statement.setInt(4, autor.getID()); // esse ID entrará no placeholder do WHERE ID = ?

         if (statement.executeUpdate() < 1) {
            throw new SQLException("Erro ao editar: autor não encontrado.");
         }
       } catch (SQLException ex) {
         Logger.getLogger(PgAutorDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
 
         if (ex.getMessage().equals("Erro ao editar: autor não encontrado.")) {
             throw ex;
         } else if (ex.getMessage().contains("not-null")) {
             throw new SQLException("Erro ao editar autor: pelo menos um campo obrigatório está em branco.");
         } else {
             throw new SQLException("Erro ao editar autor.");
         }
     }        
   }
   
   // ===== DELETE AUTOR =====
   @Override
   public void delete(Integer ID) throws SQLException {
     try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
         statement.setInt(1, ID);

         if (statement.executeUpdate() < 1) {
             throw new SQLException("Erro ao excluir: autor não encontrado.");
         }
     } catch (SQLException ex) {
         Logger.getLogger(PgAutorDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         if (ex.getMessage().equals("Erro ao excluir: autor não encontrado.")) {
             throw ex;
         } else {
             throw new SQLException("Erro ao excluir autor.");
         }
      }
   }

   // ===== LIST ALL AUTORES =====
   @Override
   public List<Autor> all() throws SQLException {
      List<Autor> autorList = new ArrayList<>();

      try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
         ResultSet result = statement.executeQuery()) {
         while (result.next()) {
            Autor autor = new Autor();
            autor.setID(result.getInt("ID"));
            autor.setPnome(result.getString("Pnome"));
            autor.setSnome(result.getString("Snome"));

            autorList.add(autor);
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgAutorDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         throw new SQLException("Erro ao listar autores.");
      }

      return autorList;        
   }
}
