package uel.br.Livraria.DAO;

import uel.br.Livraria.Model.Autor;
import uel.br.Livraria.Model.Escrito;
import uel.br.Livraria.Model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PgEscritoDAO implements  EscritoDAO{
    private final Connection connection;
    private final PgAutorDAO pgAutorDAO;
    private final PgLivroDAO pgLivroDAO;

    public PgEscritoDAO(Connection connection) {
        this.connection = connection;
        this.pgAutorDAO = new PgAutorDAO(connection);
        this.pgLivroDAO = new PgLivroDAO(connection);
    }

    private static final String CREATE_QUERY =
            "INSERT INTO livraria.Escrito(ID_Autor, ISBN_Livro) " + "VALUES(?, ?);";

    private static final String DELETE_QUERY =
            "DELETE FROM livraria.Escrito WHERE (ID_Autor = ? AND ISBN_Livro = ?)";

    private static final String ALL_QUERY =
            "SELECT ID_Autor, ISBN_Livro FROM livraria.Escrito ORDER BY ID_Autor;";

    // ===== CREATE ESCRITO =====
    @Override
    public void create(Escrito escrito) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setInt(1, escrito.getAutor().getID());
            statement.setLong(2, escrito.getLivro().getISBN());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgEscritoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if(ex.getMessage().contains("pk_Escrito")) {
                throw new SQLException("Erro ao inserir relacionamento 'escrito': (ID_Autor, ISBN_Livro) duplicado.");
            }else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir relacionamento 'escrito': pelo menos um campo obrigatório está em branco.");
            }else {
                throw new SQLException("Erro ao inserir relacionamento 'escrito'.");
            }
        }
    }

    // Para satisfazer DAO:
    @Override
    public Escrito read(Integer ID) throws SQLException {
        return null;
    }
    @Override
    public void update(Escrito escrito) throws SQLException {
    }
    @Override
    public void delete(Integer ID) throws SQLException {
    }

    // ===== DELETE ESCRITO =====
    @Override
    public void delete(Integer ID_Autor, Long ISBN_Livro) throws SQLException{
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, ID_Autor);
            statement.setLong(2, ISBN_Livro);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: relacionamento 'escrito' não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgEscritoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: relacionamento 'escrito' não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir relacionamento 'escrito'.");
            }
        }
    }

    // ===== LIST ALL ESCRITOs =====
    @Override
    public List<Escrito> all() throws SQLException {
        List<Escrito> escritoList = new ArrayList<>();

        int autorId;
        long livroISBN;
        Autor autor;
        Livro livro;
        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Escrito escrito = new Escrito();
                autorId = result.getInt("ID_Autor");
                livroISBN = result.getLong("ISBN_Livro");
                autor = pgAutorDAO.read(autorId);
                livro = pgLivroDAO.read(livroISBN);
                escrito.setAutor(autor);
                escrito.setLivro(livro);

                escritoList.add(escrito);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgEscritoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar relacionamento 'escrito'.");
        }

        return escritoList;
    }
}
