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
public class PgPossuiDAO implements PossuiDAO{
    private final Connection connection;
    private final PgCompraDAO pgCompraDAO;
    private final PgLivroDAO pgLivroDAO;

    public PgPossuiDAO(Connection connection){
        this.connection = connection;
        this.pgCompraDAO = new PgCompraDAO(connection);
        this.pgLivroDAO = new PgLivroDAO(connection);
    }

    private static final String CREATE_QUERY =
            "INSERT INTO livraria.Possui(Num_Nota_Fiscal_Compra, ISBN_Livro, Quantidade, Preco) " + "VALUES(?, ?, ?, ?);";

    private static final String READ_QUERY =
            "SELECT Quantidade, Preco FROM livraria.Possui " +
                    "WHERE (Num_Nota_Fiscal_Compra = ? AND ISBN_Livro = ?);";

    private static final String UPDATE_QUERY =
            "UPDATE livraria.Possui " +
                    "SET Quantidade = ?, Preco = ? " +
                    "WHERE (Num_Nota_Fiscal_Compra = ? AND ISBN_Livro = ?);";

    private static final String DELETE_QUERY =
            "DELETE FROM livraria.Possui WHERE (Num_Nota_Fiscal_Compra = ? AND ISBN_Livro = ?);";

    private static final String ALL_QUERY =
            "SELECT Num_Nota_Fiscal_Compra, ISBN_Livro, Quantidade, Preco FROM livraria.Possui ORDER BY Num_Nota_Fiscal_Compra;";

    // Para satisfazer DAO:
    @Override
    public Possui read(Integer ID) throws SQLException {
        return null;
    }
    @Override
    public void delete(Integer ID) throws SQLException {
    }

    // ===== CREATE POSSUI =====
    @Override
    public void create(Possui possui) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setLong(1, possui.getCompra().getNum_Nota_Fiscal());
            statement.setLong(2, possui.getLivro().getISBN());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgPossuiDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if(ex.getMessage().contains("pk_Possui")) {
                throw new SQLException("Erro ao inserir relacionamento 'possui': (Num_Nota_Fiscal_Compra, ISBN_Livro) duplicado.");
            }else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir relacionamento 'possui': pelo menos um campo obrigatório está em branco.");
            }else {
                throw new SQLException("Erro ao inserir relacionamento 'possui'.");
            }
        }
    }

    // ===== READ POSSUI =====
    @Override
    public Possui read(Long Num_Nota_Fiscal_Compra, long ISBN_Livro) throws SQLException {
        Possui possui = new Possui();

        long notaNum;
        long livroISBN;
        Compra compra;
        Livro livro;
        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setLong(1, Num_Nota_Fiscal_Compra);
            statement.setLong(2, ISBN_Livro);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    notaNum = result.getLong("Num_Nota_Fiscal_Compra");
                    livroISBN = result.getLong("ISBN_Livro");
                    compra = pgCompraDAO.read(notaNum);
                    livro = pgLivroDAO.read(livroISBN);
                    possui.setCompra(compra);
                    possui.setLivro(livro);
                    possui.setQuantidade(result.getInt("Quantidade"));
                    possui.setPreco(result.getBigDecimal("Preco"));
                } else {
                    throw new SQLException("Erro ao visualizar: relacionamento 'possui' não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPossuiDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: relacionamento 'possui' não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar relacionamento 'possui'.");
            }
        }

        return possui;
    }

    // ===== UPDATE POSSUI =====
    @Override
    public void update(Possui possui) throws SQLException {
        String query = UPDATE_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, possui.getQuantidade());
            statement.setBigDecimal(2, possui.getPreco());
            statement.setLong(3, possui.getCompra().getNum_Nota_Fiscal());
            statement.setLong(4, possui.getLivro().getISBN());
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: relacionamento 'possui' não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPossuiDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: relacionamento 'possui' não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar relacionamento 'possui': pelo menos um campo obrigatório está em branco.");
            } else {
                throw new SQLException("Erro ao editar relacionamento 'possui'.");
            }
        }
    }

    // ===== DELETE POSSUI =====
    @Override
    public void delete(Long Num_Nota_Fiscal_Compra, long ISBN_Livro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, Num_Nota_Fiscal_Compra);
            statement.setLong(2, ISBN_Livro);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: relacionamento 'possui' não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPossuiDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: relacionamento 'possui' não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir relacionamento 'possui'.");
            }
        }
    }

    // ===== LIST ALL POSSUIs =====
    @Override
    public List<Possui> all() throws SQLException {
        List<Possui> possuiList = new ArrayList<>();

        long notaNum;
        long livroISBN;
        Compra compra;
        Livro livro;
        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Possui possui = new Possui();
                notaNum = result.getLong("Num_Nota_Fiscal_Compra");
                livroISBN = result.getLong("ISBN_Livro");
                compra = pgCompraDAO.read(notaNum);
                livro = pgLivroDAO.read(livroISBN);
                possui.setCompra(compra);
                possui.setLivro(livro);
                possui.setQuantidade(result.getInt("Quantidade"));
                possui.setPreco(result.getBigDecimal("Preco"));

                possuiList.add(possui);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPossuiDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar relacionamento 'possui'.");
        }

        return possuiList;
    }
}
