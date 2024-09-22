package uel.br.Livraria.DAO;

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

public class PgLivroDAO implements LivroDAO{
    private final Connection connection;
    private final PgEditoraDAO pgEditoraDAO;

    public PgLivroDAO(Connection connection) {
        this.connection = connection;
        this.pgEditoraDAO = new PgEditoraDAO(connection);
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

                livroList.add(livro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLivroDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar livros.");
        }

        return livroList;
    }
}
