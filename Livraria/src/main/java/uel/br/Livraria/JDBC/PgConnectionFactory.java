package uel.br.Livraria.JDBC;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PgConnectionFactory extends ConnectionFactory{
    private String Host;
    private String Port;
    private String Name;
    private String User;
    private String Password;

    public PgConnectionFactory(){
    }

    public void getProperties() throws IOException {
        Properties properties = new Properties();

        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream(propertiesPath);
            properties.load(input);
            Host = properties.getProperty("host");
            Port = properties.getProperty("port");
            Name = properties.getProperty("name");
            User = properties.getProperty("user");
            Password = properties.getProperty("password");
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
            throw  new IOException("Erro ao obter informações do banco de dados.");
        }
    }

    @Override
    public Connection getConnection() throws IOException, SQLException, ClassNotFoundException {
        Connection connetion = null;

        try {
            Class.forName("org.postgresql.Driver");
            getProperties();
            String url = "jdbc:postgresql://" + Host + ":" + Port + "/" + Name;
            connetion = DriverManager.getConnection(url, User, Password);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            throw new ClassNotFoundException("Erro de conexão ao banco de dados.");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro de conexão ao banco de dados.");
        }
        return connetion;
    }
}
