package uel.br.Livraria.DAO;

import org.springframework.stereotype.Repository;
import uel.br.Livraria.Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class PgLivroDAO implements DAO<Livro, Long>{
    private final Connection connection;
    private final PgEditoraDAO pgEditoraDAO;
    private final PgSecaoDAO pgSecaoDAO;

    public PgLivroDAO(Connection connection) {
        this.connection = connection;
        this.pgEditoraDAO = new PgEditoraDAO(connection);
        this.pgSecaoDAO = new PgSecaoDAO(connection);
    }

    private static final String CREATE_QUERY =
            "INSERT INTO livraria.Livro(ISBN, Titulo, Ano, Preco, Estoque, Descricao, ID_Editora) " + "VALUES(?, ?, ?, ?, ?, ?, ?);";

    private static final String READ_QUERY =
            "SELECT Titulo, Ano, Preco, Estoque, Descricao, ID_Editora FROM livraria.Livro " +
                    "WHERE ISBN = ?;";

    private static final String UPDATE_QUERY =
            "UPDATE livraria.Livro " +
                    "SET Titulo = ?, Ano = ?, Preco = ?, Estoque = ?, Descricao = ?, ID_Editora = ? " +
                    "WHERE ISBN = ?;";

    private static final String DELETE_QUERY =
            "DELETE FROM livraria.Livro WHERE ISBN = ?;";

    private static final String ALL_QUERY =
            "SELECT ISBN, Titulo, Ano, Preco, Estoque, Descricao, ID_Editora FROM livraria.Livro ORDER BY ISBN;";

    private static final String LIST_AUTORES_QUERY =
            "SELECT ID_Autor FROM livraria.Escrito WHERE (ISBN_Livro = ?)";

    private static final String DELETE_AUTORES_QUERY =
            "DELETE FROM livraria.Escrito WHERE ISBN_Livro = ?";

    private static final String CREATE_AUTORES_QUERY =
            "INSERT INTO livraria.Escrito (ID_Autor, ISBN_Livro) VALUES (?, ?)";

    private static final String READ_AUTOR_QUERY =
            "SELECT Pnome, Snome, Nacionalidade FROM livraria.Autor " +
                    "WHERE ID = ?;";

    private static final String LIST_GENEROS_QUERY =
            "SELECT ID_Genero FROM livraria.Pertence WHERE (ISBN_Livro = ?)";

    private static final String DELETE_GENEROS_QUERY =
            "DELETE FROM livraria.Pertence WHERE ISBN_Livro = ?";

    private static final String CREATE_GENEROS_QUERY =
            "INSERT INTO livraria.Pertence (ID_Genero, ISBN_Livro) VALUES (?, ?)";

    private static final String READ_GENERO_QUERY =
            "SELECT Nome, ID_Secao FROM livraria.Genero WHERE ID = ?;";

    // ===== LIST AUTORES POR LIVRO =====
    public List<Autor> listAutoresByLivroISBN(Long livroISBN) throws SQLException {
        List<Autor> autoresEscritos = new ArrayList<>();

        int autorId;
        Autor autor;

        try (PreparedStatement statement = connection.prepareStatement(LIST_AUTORES_QUERY)) {
            statement.setLong(1, livroISBN);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                autor = new Autor();
                autorId = result.getInt("ID_Autor");
                autor = readAutor(autorId);

                autoresEscritos.add(autor);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao encontrar autores a partir do ISBN dos livros.");
        }

        return autoresEscritos;
    }

    // ===== UPDATE AUTORES POR LIVRO =====
    private void updateAutoresByLivroISBN(Long livroISBN, List<Autor> novosAutores) throws SQLException {
        removeAutoresByLivroISBN(livroISBN);

        for (Autor autor : novosAutores) {
            addAutorToLivro(livroISBN, autor);
        }
    }

    // ===== REMOVE AUTORES POR LIVRO =====
    private void removeAutoresByLivroISBN(Long livroISBN) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_AUTORES_QUERY)) {
            statement.setLong(1, livroISBN);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao remover autores do livro.");
        }
    }

    // ===== CREATE LIVROS do AUTOR =====
    private void addAutorToLivro(Long livroISBN, Autor autor) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_AUTORES_QUERY)) {
            statement.setInt(1, autor.getID());
            statement.setLong(2, livroISBN);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao adicionar autor ao livro.");
        }
    }

    // ===== READ AUTOR DO LIVRO =====
    public Autor readAutor(Integer ID) throws SQLException {
        Autor autor = new Autor();

        try (PreparedStatement statement = connection.prepareStatement(READ_AUTOR_QUERY)) {
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
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: autor não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar autor.");
            }
        }
        return autor;
    }

    // ===== LIST GENEROS POR LIVRO =====
    public List<Genero> listGenerosByLivroISBN(Long livroISBN) throws SQLException {
        List<Genero> generosPertence = new ArrayList<>();

        int generoId;
        Genero genero;

        try (PreparedStatement statement = connection.prepareStatement(LIST_GENEROS_QUERY)) {
            statement.setLong(1, livroISBN);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                genero = new Genero();
                generoId = result.getInt("ID_Genero");
                genero = readGeneros(generoId);

                generosPertence.add(genero);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao encontrar generos a partir do ISBN dos livros.");
        }

        return generosPertence;
    }

    // ===== UPDATE GENEROS POR LIVRO =====
    private void updateGenerosByLivroISBN(Long livroISBN, List<Genero> novosGeneros) throws SQLException {
        removeGenerosByLivroISBN(livroISBN);

        for (Genero genero : novosGeneros) {
            addGeneroToLivro(livroISBN, genero);
        }
    }

    // ===== REMOVE GENEROS POR LIVRO =====
    private void removeGenerosByLivroISBN(Long livroISBN) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_GENEROS_QUERY)) {
            statement.setLong(1, livroISBN);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao remover generos do livro.");
        }
    }

    // ===== CREATE GENERO do AUTOR =====
    private void addGeneroToLivro(Long livroISBN, Genero genero) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_GENEROS_QUERY)) {
            statement.setInt(1, genero.getID());
            statement.setLong(2, livroISBN);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao adicionar generos ao livro.");
        }
    }

    // ===== READ GENEROS DO LIVRO =====
    public Genero readGeneros(Integer ID) throws SQLException {
        Genero genero = new Genero();

        try (PreparedStatement statement = connection.prepareStatement(READ_GENERO_QUERY)) {
            statement.setInt(1, ID);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    genero.setID(ID);
                    genero.setNome(result.getString("Nome"));

                    Secao secao = pgSecaoDAO.read(result.getInt("ID_Secao"));
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

    // ===== CREATE LIVRO =====
    @Override
    public void create(Livro livro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setLong(1, livro.getISBN());
            statement.setString(2, livro.getTitulo());
            statement.setInt(3, livro.getAno());
            statement.setBigDecimal(4, livro.getPreco());
            statement.setInt(5, livro.getEstoque());
            statement.setString(6, livro.getDescricao());
            statement.setInt(7, livro.getEditora().getID());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if(ex.getMessage().contains("pk_Livro")) {
                throw new SQLException("Erro ao inserir Livro: ISBN duplicado.");
            }else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir Livro: pelo menos um campo obrigatório está em branco.");
            }else {
                throw new SQLException("Erro ao inserir livro.");
            }
        }

        if (livro.getAutores() != null && !livro.getAutores().isEmpty()) {
            for (Autor autor : livro.getAutores()) {
                addAutorToLivro(livro.getISBN(), autor);
            }
        }

        if (livro.getGeneros() != null && !livro.getGeneros().isEmpty()) {
            for (Genero genero : livro.getGeneros()) {
                addGeneroToLivro(livro.getISBN(), genero);
            }
        }
    }

    // ===== READ LIVRO =====
    @Override
    public Livro read(Long ISBN) throws SQLException {
        Livro livro = new Livro();

        int editoraId;
        Editora editora;
        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
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
                    livro.setAutores(listAutoresByLivroISBN(ISBN));
                    livro.setGeneros(listGenerosByLivroISBN(ISBN));
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

    // ===== UPDATE LIVRO =====
    @Override
    public void update(Livro livro) throws SQLException {
        String query = UPDATE_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, livro.getTitulo());
            statement.setInt(2, livro.getAno());
            statement.setBigDecimal(3, livro.getPreco());
            statement.setInt(4, livro.getEstoque());
            statement.setString(5, livro.getDescricao());
            statement.setInt(6, livro.getEditora().getID());
            statement.setLong(7, livro.getISBN()); // esse ISBN entrará no placeholder do WHERE ISBN = ?

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: livro não encontrado.");
            }

            updateAutoresByLivroISBN(livro.getISBN(), livro.getAutores());
            updateGenerosByLivroISBN(livro.getISBN(), livro.getGeneros());
        } catch (SQLException ex) {
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: livro não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar livro: pelo menos um campo obrigatório está em branco.");
            } else {
                throw new SQLException("Erro ao editar livro.");
            }
        }
    }

    // ===== DELETE LIVRO =====
    @Override
    public void delete(Long ISBN) throws SQLException {
        removeAutoresByLivroISBN(ISBN);
        removeGenerosByLivroISBN(ISBN);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, ISBN);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: livro não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: livro não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir livro.");
            }
        }
    }

    // ===== LIST ALL LIVROS =====
    @Override
    public List<Livro> all() throws SQLException {
        List<Livro> livroList = new ArrayList<>();
        int editoraId;
        Editora editora;

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Livro livro = new Livro();
                livro.setISBN(result.getLong("ISBN"));
                livro.setTitulo(result.getString("Titulo"));
                livro.setAno(result.getInt("Ano"));
                livro.setPreco(result.getBigDecimal("Preco"));
                livro.setEstoque(result.getInt("Estoque"));
                livro.setDescricao(result.getString("Descricao"));
                editoraId = result.getInt("ID_Editora");
                editora = pgEditoraDAO.read(editoraId);
                livro.setEditora(editora);
                livro.setAutores(listAutoresByLivroISBN(result.getLong("ISBN")));
                livro.setGeneros(listGenerosByLivroISBN(result.getLong("ISBN")));

                livroList.add(livro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar livros.");
        }

        return livroList;
    }
}
