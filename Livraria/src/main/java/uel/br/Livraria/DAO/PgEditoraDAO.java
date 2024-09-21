package uel.br.Livraria.DAO;

import uel.br.Livraria.Model.Editora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PgEditoraDAO implements EditoraDAO{
   private final Connection connection;

   public PgEditoraDAO(Connection connection) {
      this.connection = connection;
   }

   private static final String CREATE_QUERY =
                                "INSERT INTO livraria.Editora(Nome) VALUES(?);";

   private static final String READ_QUERY =
                                "SELECT Nome FROM livraria.Editora WHERE ID = ?;";

   private static final String UPDATE_QUERY =
                                "UPDATE livraria.Editora SET Nome = ? WHERE ID = ?;";

   private static final String DELETE_QUERY =
                                "DELETE FROM livraria.Editora WHERE ID = ?;";

   private static final String ALL_QUERY =
                                "SELECT ID, Nome FROM livraria.Editora ORDER BY ID;";

   private static final String GET_BY_NOME =
                                "SELECT ID, Nome FROM livraria.Editora " +
                                "WHERE Nome = ?;";


   // ====== BUSCA POR PNOME =====
   public Editora getByNome(String Nome) throws SQLException {
      Editora editora = new Editora();
      try (PreparedStatement statement = connection.prepareStatement(GET_BY_NOME)) {
         statement.setString(1, Nome);
         try (ResultSet result = statement.executeQuery()) {
               if (result.next()) {
                  editora.setID(result.getInt("ID"));
                  editora.setNome(result.getString("Nome"));
               } else {
                  throw new SQLException("Erro ao visualizar: editora não encontrada.");
               }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgEditoraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         if (ex.getMessage().equals("Erro ao visualizar: editora não encontrada.")) {
            throw ex;
         } else {
            throw new SQLException("Erro ao visualizar editora.");
         }
      }
      return editora;
   }


   // ===== CREATE EDITORA =====
   @Override
    public void create(Editora editora) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, editora.getNome());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgEditoraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if(ex.getMessage().contains("pk_Editora")) {
               throw new SQLException("Erro ao inserir editora: ID duplicado.");
           }else if (ex.getMessage().contains("not-null")) {
               throw new SQLException("Erro ao inserir editora: campo obrigatório está em branco.");
           }else {
               throw new SQLException("Erro ao inserir editora.");
           }
        }
    }

   // ===== READ EDITORA =====
   @Override
   public Editora read(Integer ID) throws SQLException {
      Editora editora = new Editora();

      try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
         statement.setInt(1, ID);
         try (ResultSet result = statement.executeQuery()) {
               if (result.next()) {
                  editora.setID(ID);
                  editora.setNome(result.getString("Nome"));
               } else {
                  throw new SQLException("Erro ao visualizar: editora não encontrada.");
               }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgEditoraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         if (ex.getMessage().equals("Erro ao visualizar: editora não encontrada.")) {
            throw ex;
         } else {
            throw new SQLException("Erro ao visualizar editora.");
         }
      }

      return editora;
   }

   // ===== UPDATE EDITORA =====
   @Override
   public void update(Editora editora) throws SQLException {
      String query = UPDATE_QUERY;

      try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setString(1, editora.getNome());
         statement.setInt(2, editora.getID());

         if (statement.executeUpdate() < 1) {
            throw new SQLException("Erro ao editar: editora não encontrada.");
         }
       } catch (SQLException ex) {
         Logger.getLogger(PgEditoraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         if (ex.getMessage().equals("Erro ao editar: editora não encontrada.")) {
             throw ex;
         } else if (ex.getMessage().contains("not-null")) {
             throw new SQLException("Erro ao editar editora: campo obrigatório está em branco.");
         } else {
             throw new SQLException("Erro ao editar editora.");
         }
     }
   }

   // ===== DELETE EDITORA =====
   @Override
   public void delete(Integer ID) throws SQLException {
     try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
         statement.setInt(1, ID);

         if (statement.executeUpdate() < 1) {
             throw new SQLException("Erro ao excluir: editora não encontrada.");
         }
     } catch (SQLException ex) {
         Logger.getLogger(PgEditoraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         if (ex.getMessage().equals("Erro ao excluir: editora não encontrada.")) {
             throw ex;
         } else {
             throw new SQLException("Erro ao excluir editora.");
         }
      }
   }

   // ===== LIST ALL EDITORAS =====
   @Override
   public List<Editora> all() throws SQLException {
      List<Editora> editoraList = new ArrayList<>();

      try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
         ResultSet result = statement.executeQuery()) {
         while (result.next()) {
            Editora editora = new Editora();
            editora.setID(result.getInt("ID"));
            editora.setNome(result.getString("Nome"));

            editoraList.add(editora);
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgEditoraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         throw new SQLException("Erro ao listar editoras.");
      }

      return editoraList;
   }
}