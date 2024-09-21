package uel.br.Livraria.DAO;

import uel.br.Livraria.Model.Cliente;

import java.sql.SQLException;

public interface ClienteDAO extends DAO<Cliente, String>{

    public Cliente getByEmail(String Email) throws SQLException;
}
