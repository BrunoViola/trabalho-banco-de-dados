package uel.br.Livraria.DAO;

import java.sql.Connection;

public class PgDAOFactory extends DAOFactory{

    public PgDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AutorDAO getAutorDAO() {
        return new PgAutorDAO(this.connection);
    }

    @Override
    public ClienteDAO getClienteDAO() {
        return new PgClienteDAO(this.connection);
    }

    @Override
    public CompraDAO getCompraDAO() {
        return new PgCompraDAO(this.connection);
    }

    @Override
    public EditoraDAO getEditoraDAO() {
        return new PgEditoraDAO(this.connection);
    }

    @Override
    public GeneroDAO getGeneroDAO() {
        return new PgGeneroDAO(this.connection);
    }

    @Override
    public LivroDAO getLivroDAO() {
        return new PgLivroDAO(this.connection);
    }

    @Override
    public SecaoDAO getSecaoDAO() {
        return new PgSecaoDAO(this.connection);
    }
}
