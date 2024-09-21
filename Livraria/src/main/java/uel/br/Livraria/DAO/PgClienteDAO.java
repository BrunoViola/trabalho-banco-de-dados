package uel.br.Livraria.DAO;

import uel.br.Livraria.Model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PgClienteDAO implements ClienteDAO{

    private final Connection connection;

    public PgClienteDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String GET_BY_EMAIL =
            "SELECT CPF, Sexo, Data_nascimento, Pnome, Snome, Cidade, Estado FROM livraria.Cliente " +
                    "WHERE Email = ?";

    private static final String CREATE_QUERY =
            "INSERT INTO livraria.Cliente(CPF, Sexo, Data_nascimento, Email, Pnome, Snome, Cidade, Estado) " + "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String READ_QUERY =
            "SELECT Sexo, Data_nascimento, Email, Pnome, Snome, Cidade, Estado FROM livraria.Cliente " +
                    "WHERE CPF = ?;";

    private static final String UPDATE_QUERY =
            "UPDATE livraria.Cliente " +
                    "SET Sexo = ?, Data_nascimento = ?, Email = ?, Pnome = ?, Snome = ?, Cidade = ?, Estado = ? " +
                    "WHERE CPF = ?;";

    private static final String DELETE_QUERY =
            "DELETE FROM livraria.Cliente WHERE CPF = ?;";

    private static final String ALL_QUERY =
            "SELECT CPF, Sexo, Data_nascimento, Email, Pnome, Snome, Cidade, Estado FROM livraria.Cliente ORDER BY Pnome;";

    // ===== CREATE CLIENTE =====
    @Override
    public Cliente getByEmail(String Email) throws SQLException {
        Cliente cliente = new Cliente();

        try (PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL)) {
            statement.setString(1, Email);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    cliente.setEmail(Email);
                    cliente.setCPF(result.getString("CPF"));
                    cliente.setSexo((result.getString("Sexo")).charAt(0));
                    cliente.setData_nascimento(result.getDate("Data_nascimento"));
                    cliente.setPnome(result.getString("Pnome"));
                    cliente.setSnome(result.getString("Snome"));
                    cliente.setCidade(result.getString("Cidade"));
                    cliente.setEstado(result.getString("Estado"));
                } else {
                    throw new SQLException("Erro ao visualizar: cliente não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: cliente não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar cliente.");
            }
        }

        return cliente;
    }

    // ===== CREATE CLIENTE =====
    @Override
    public void create(Cliente cliente) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, cliente.getCPF());
            statement.setString(2, String.valueOf(cliente.getSexo()));
            statement.setDate(3, cliente.getData_nascimento());
            statement.setString(4, cliente.getEmail());
            statement.setString(5, cliente.getPnome());
            statement.setString(6, cliente.getSnome());
            statement.setString(7, cliente.getCidade());
            statement.setString(8, cliente.getEstado());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if(ex.getMessage().contains("pk_Cliente")) {
                throw new SQLException("Erro ao inserir cliente: ID duplicado.");
            }else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir cliente: pelo menos um campo obrigatório está em branco.");
            }else {
                throw new SQLException("Erro ao inserir cliente.");
            }
        }
    }

    // ===== READ CLIENTE =====
    @Override
    public Cliente read(String id) throws SQLException {
        Cliente cliente = new Cliente();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    cliente.setCPF(id);
                    cliente.setSexo((result.getString("Sexo")).charAt(0));
                    cliente.setData_nascimento(result.getDate("Data_nascimento"));
                    cliente.setEmail(result.getString("Email"));
                    cliente.setPnome(result.getString("Pnome"));
                    cliente.setSnome(result.getString("Snome"));
                    cliente.setCidade(result.getString("Cidade"));
                    cliente.setEstado(result.getString("Estado"));
                } else {
                    throw new SQLException("Erro ao visualizar: cliente não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: cliente não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar cliente.");
            }
        }

        return cliente;
    }

    // ===== UPDATE CLIENTE =====
    @Override
    public void update(Cliente cliente) throws SQLException {
        String query = UPDATE_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, String.valueOf(cliente.getSexo()));
            statement.setDate(2, cliente.getData_nascimento());
            statement.setString(3, cliente.getEmail());
            statement.setString(4, cliente.getPnome());
            statement.setString(5, cliente.getSnome());
            statement.setString(6, cliente.getCidade());
            statement.setString(7, cliente.getEstado());
            statement.setString(8, cliente.getCPF()); // esse CPF entrará no placeholder do WHERE CPF = ?

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: usuário não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: cliente não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar cliente: pelo menos um campo obrigatório está em branco.");
            } else {
                throw new SQLException("Erro ao editar cliente.");
            }
        }
    }

    // ===== DELETE CLIENTE =====
    @Override
    public void delete(String id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, id);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: cliente não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: cliente não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir cliente.");
            }
        }
    }

    // ===== LIST ALL CLIENTES =====
    @Override
    public List<Cliente> all() throws SQLException {
        List<Cliente> clienteList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Cliente cliente = new Cliente();
                cliente.setCPF(result.getString("CPF"));
                cliente.setSexo((result.getString("Sexo")).charAt(0));
                cliente.setData_nascimento(result.getDate("Data_nascimento"));
                cliente.setEmail(result.getString("Email"));
                cliente.setPnome(result.getString("Pnome"));
                cliente.setSnome(result.getString("Snome"));
                cliente.setCidade(result.getString("Cidade"));
                cliente.setEstado(result.getString("Estado"));

                clienteList.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgClienteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar clientes.");
        }

        return clienteList;
    }
}
