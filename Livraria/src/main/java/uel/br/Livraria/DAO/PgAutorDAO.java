package uel.br.Livraria.DAO;

import org.springframework.stereotype.Repository;
import uel.br.Livraria.Model.Autor;
import uel.br.Livraria.Model.Editora;
import uel.br.Livraria.Model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class PgAutorDAO implements DAO<Autor, Integer>{
   private final Connection connection;
   private final PgEditoraDAO pgEditoraDAO;
   private final PgLivroDAO pgLivroDAO;

   public PgAutorDAO(Connection connection) {
      this.connection = connection;
      this.pgEditoraDAO = new PgEditoraDAO(connection);
      this.pgLivroDAO = new PgLivroDAO(connection);
   }

   private static final String CREATE_QUERY =
                                "INSERT INTO livraria.Autor(Pnome, Snome, Nacionalidade) " + "VALUES(?, ?, ?) RETURNING ID;";

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

    private static final String LIST_LIVROS_QUERY =
            "SELECT ISBN_Livro FROM livraria.Escrito WHERE (ID_Autor = ?)";

    private static final String DELETE_LIVROS_QUERY =
            "DELETE FROM livraria.Escrito WHERE ID_Autor = ?";

    private static final String CREATE_LIVROS_QUERY =
            "INSERT INTO livraria.Escrito (ID_Autor, ISBN_Livro) VALUES (?, ?)";

    private static final String READ_LIVROS_QUERY =
            "SELECT Titulo, Ano, Preco, Estoque, Descricao, ID_Editora FROM livraria.Livro " +
                    "WHERE ISBN = ?;";

    // ===== LIST LIVROS POR AUTOR =====
    private List<Livro> listLivrosByAutorId(Integer autorId) throws SQLException {
        List<Livro> livrosEscritos = new ArrayList<>();

        long livroISBN;
        Livro livro;

        try (PreparedStatement statement = connection.prepareStatement(LIST_LIVROS_QUERY)) {
            statement.setInt(1, autorId);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                livro = new Livro();
                livroISBN = result.getLong("ISBN_Livro");
                livro = readLivro(livroISBN);
                livro.setGeneros(pgLivroDAO.listGenerosByLivroISBN(livroISBN));

                livrosEscritos.add(livro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgEscritoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao encontrar livros a partir do id do autor.");
        }

        return livrosEscritos;
    }

    // ===== UPDATE LIVROS POR AUTOR =====
   private void updateLivrosByAutorId(Integer autorId, List<Livro> novosLivros) throws SQLException {
        removeLivrosByAutorId(autorId);

        for (Livro livro : novosLivros) {
            addLivroToAutor(autorId, livro);
        }
   }

   // ===== REMOVE LIVROS POR AUTOR =====
   private void removeLivrosByAutorId(Integer autorId) throws SQLException {
       try (PreparedStatement statement = connection.prepareStatement(DELETE_LIVROS_QUERY)) {
           statement.setInt(1, autorId);
           statement.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(PgAutorDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
           throw new SQLException("Erro ao remover livros do autor.");
       }
   }

   // ===== CREATE LIVROS do AUTOR =====
    private void addLivroToAutor(Integer autorId, Livro livro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_LIVROS_QUERY)) {
            statement.setInt(1, autorId);
            statement.setLong(2, livro.getISBN());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgAutorDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao adicionar livro ao autor.");
        }
    }

    // ===== READ LIVRO DO AUTOR =====
    public Livro readLivro(Long ISBN) throws SQLException {
        Livro livro = new Livro();

        int editoraId;
        Editora editora;
        try (PreparedStatement statement = connection.prepareStatement(READ_LIVROS_QUERY)) {
            statement.setLong(1, ISBN);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    livro.setISBN(ISBN);
                    livro.setTitulo(result.getString("Titulo"));
                    livro.setAno(result.getInt("Ano"));
                    livro.setPreco(result.getBigDecimal("Preco"));
                    livro.setEstoque(result.getInt("Estoque"));
                    livro.setDescricao(result.getString("Descricao"));
                    editoraId = result.getInt("ID_Editora");
                    editora = pgEditoraDAO.read(editoraId);
                    livro.setEditora(editora);
                    livro.setGeneros(pgLivroDAO.listGenerosByLivroISBN(ISBN));
                } else {
                    throw new SQLException("Erro ao visualizar: livro não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: livro não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar livro.");
            }
        }

        return livro;
    }

   // ===== CREATE AUTOR =====
   @Override
    public void create(Autor autor) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, autor.getPnome());
            statement.setString(2, autor.getSnome());
            statement.setString(3, autor.getNacionalidade());

            try (ResultSet result = statement.executeQuery()){
                if (result.next()) {
                    autor.setID(result.getInt("ID"));
                }
            }
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

       if (autor.getLivros() != null && !autor.getLivros().isEmpty()) {
           for (Livro livro : autor.getLivros()) {
               addLivroToAutor(autor.getID(), livro);
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
                  autor.setLivros(listLivrosByAutorId(ID));
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

         updateLivrosByAutorId(autor.getID(), autor.getLivros());
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
     removeLivrosByAutorId(ID);
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
            autor.setNacionalidade(result.getString("Nacionalidade"));
            autor.setLivros(listLivrosByAutorId(result.getInt("ID")));

            autorList.add(autor);
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgAutorDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         throw new SQLException("Erro ao listar autores.");
      }

      return autorList;        
   }
}
