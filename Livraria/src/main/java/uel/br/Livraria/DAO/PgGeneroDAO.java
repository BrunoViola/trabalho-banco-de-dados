package uel.br.Livraria.DAO;

import org.springframework.stereotype.Repository;
import uel.br.Livraria.Model.Editora;
import uel.br.Livraria.Model.Genero;
import uel.br.Livraria.Model.Livro;
import uel.br.Livraria.Model.Secao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class PgGeneroDAO implements GeneroDAO{
   private final Connection connection;
   private final PgSecaoDAO pgSecaoDAO;
   private final PgEditoraDAO pgEditoraDAO;
   private final PgLivroDAO pgLivroDAO;

   public PgGeneroDAO(Connection connection) {
      this.connection = connection;
      this.pgSecaoDAO = new PgSecaoDAO(connection);
      this.pgEditoraDAO = new PgEditoraDAO(connection);
      this.pgLivroDAO = new PgLivroDAO(connection);
   }

   private static final String CREATE_QUERY =
                                "INSERT INTO livraria.Genero (Nome, ID_Secao) VALUES (?, ?) RETURNING ID;";

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

    private static final String LIST_LIVROS_QUERY =
            "SELECT ISBN_Livro FROM livraria.Pertence WHERE (ID_Genero = ?)";

    private static final String DELETE_LIVROS_QUERY =
            "DELETE FROM livraria.Pertence WHERE ID_Genero = ?";

    private static final String CREATE_LIVROS_QUERY =
            "INSERT INTO livraria.Pertence (ID_Genero, ISBN_Livro) VALUES (?, ?)";

    private static final String READ_LIVROS_QUERY =
            "SELECT Titulo, Ano, Preco, Estoque, Descricao, ID_Editora FROM livraria.Livro " +
                    "WHERE ISBN = ?;";

    // ===== LIST LIVROS POR GENERO =====
    private List<Livro> listLivrosByGeneroId(Integer generoId) throws SQLException {
        List<Livro> livrosPertence = new ArrayList<>();

        long livroISBN;
        Livro livro;

        try (PreparedStatement statement = connection.prepareStatement(LIST_LIVROS_QUERY)) {
            statement.setInt(1, generoId);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                livro = new Livro();
                livroISBN = result.getLong("ISBN_Livro");
                livro = readLivro(livroISBN);
                livro.setAutores(pgLivroDAO.listAutoresByLivroISBN(livroISBN));

                livrosPertence.add(livro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgEscritoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao encontrar livros a partir do id do gênero.");
        }

        return livrosPertence;
    }

    // ===== UPDATE LIVROS POR GENERO =====
    private void updateLivrosByGeneroId(Integer generoId, List<Livro> novosLivros) throws SQLException {
        removeLivrosByGeneroId(generoId);

        for (Livro livro : novosLivros) {
            addLivroToGenero(generoId, livro);
        }
    }

    // ===== REMOVE LIVROS POR GENERO =====
    private void removeLivrosByGeneroId(Integer generoId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_LIVROS_QUERY)) {
            statement.setInt(1, generoId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgGeneroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao remover livros do genero.");
        }
    }

    // ===== CREATE LIVROS do GENERO =====
    private void addLivroToGenero(Integer generoId, Livro livro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_LIVROS_QUERY)) {
            statement.setInt(1, generoId);
            statement.setLong(2, livro.getISBN());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgGeneroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao adicionar livro ao genero.");
        }
    }

    // ===== READ LIVRO DO GENERO =====
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
                    livro.setAutores(pgLivroDAO.listAutoresByLivroISBN(ISBN));
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
   
   // ====== BUSCA POR Nome e ID_Secao =====
   public Genero getByNomeIDSecao(String Nome, int secao_id) throws SQLException {
      Genero genero = new Genero();
      try (PreparedStatement statement = connection.prepareStatement(GET_BY_NOME_IDSECAO)) {
         statement.setString(1, Nome);
         statement.setInt(2, secao_id);
         try (ResultSet result = statement.executeQuery()) {
               if (result.next()) {
                  genero.setID(result.getInt("ID"));
                  genero.setNome(result.getString("Nome"));

                  Secao secao = pgSecaoDAO.read(secao_id);
                  genero.setSecao(secao);

                  genero.setLivros(listLivrosByGeneroId(result.getInt("ID")));
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

            try (ResultSet result = statement.executeQuery()){
                if (result.next()) {
                    genero.setID(result.getInt("ID"));
                }
            }
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

       if (genero.getLivros() != null && !genero.getLivros().isEmpty()) {
           for (Livro livro : genero.getLivros()) {
               addLivroToGenero(genero.getID(), livro);
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
                  
                  Secao secao = pgSecaoDAO.read(result.getInt("ID_Secao"));
                  genero.setSecao(secao);

                  genero.setLivros(listLivrosByGeneroId(ID));
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

         updateLivrosByGeneroId(genero.getID(), genero.getLivros());
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
     removeLivrosByGeneroId(ID);
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
            Secao secao = pgSecaoDAO.read(result.getInt("ID_Secao"));
            genero.setSecao(secao);
            genero.setLivros(listLivrosByGeneroId(result.getInt("ID")));

            generoList.add(genero);
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgGeneroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         throw new SQLException("Erro ao listar gêneros.");
      }

      return generoList;        
   }
}