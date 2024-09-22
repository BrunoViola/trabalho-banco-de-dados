package uel.br.Livraria.DAO;

import org.springframework.stereotype.Repository;
import uel.br.Livraria.Model.Pertence;
import uel.br.Livraria.Model.Genero;
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
public class PgPertenceDAO implements PertenceDAO{
    private final Connection connection;
    private final PgGeneroDAO pgGeneroDAO;
    private final PgLivroDAO pgLivroDAO;

    public PgPertenceDAO(Connection connection) {
        this.connection = connection;
        this.pgGeneroDAO = new PgGeneroDAO(connection);
        this.pgLivroDAO = new PgLivroDAO(connection);
    }

    private static final String CREATE_QUERY =
            "INSERT INTO livraria.Pertence(ID_Genero, ISBN_Livro) VALUES(?, ?);";

    private static final String DELETE_QUERY =
            "DELETE FROM livraria.Pertence WHERE (ID_Genero = ? AND ISBN_Livro = ?);";

    private static final String ALL_QUERY =
            "SELECT ID_Genero, ISBN_Livro FROM livraria.Pertence ORDER BY ID_Genero;";

    // ===== CREATE PERTENCE =====
    @Override
    public void create(Pertence pertence) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setInt(1, pertence.getGenero().getID());
            statement.setLong(2, pertence.getLivro().getISBN());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgPertenceDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if(ex.getMessage().contains("pk_Pertence")) {
                throw new SQLException("Erro ao inserir relacionamento 'pertence': (ID_Genero, ISBN_Livro) duplicado.");
            }else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir relacionamento 'pertence': pelo menos um campo obrigatório está em branco.");
            }else {
                throw new SQLException("Erro ao inserir relacionamento 'pertence'.");
            }
        }
    }

    // Para satisfazer DAO:
    @Override
    public Pertence read(Integer ID) throws SQLException {
        return null;
    }
    @Override
    public void update(Pertence pertence) throws SQLException {
    }
    @Override
    public void delete(Integer ID) throws SQLException {
    }

    // ===== DELETE PERTENCE =====
    @Override
    public void delete(Integer ID_Genero, Long ISBN_Livro) throws SQLException{
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, ID_Genero);
            statement.setLong(2, ISBN_Livro);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: relacionamento 'pertence' não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPertenceDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: relacionamento 'pertence' não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir relacionamento 'pertence'.");
            }
        }
    }

    // ===== LIST ALL PERTENCEs =====
    @Override
    public List<Pertence> all() throws SQLException {
        List<Pertence> pertenceList = new ArrayList<>();

        int generoId;
        long livroISBN;
        Genero genero;
        Livro livro;
        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Pertence pertence = new Pertence();
                generoId = result.getInt("ID_Genero");
                livroISBN = result.getLong("ISBN_Livro");
                genero = pgGeneroDAO.read(generoId);
                livro = pgLivroDAO.read(livroISBN);
                pertence.setGenero(genero);
                pertence.setLivro(livro);

                pertenceList.add(pertence);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPertenceDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar relacionamento 'pertence'.");
        }

        return pertenceList;
    }
}
