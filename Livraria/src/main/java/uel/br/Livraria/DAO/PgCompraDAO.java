package uel.br.Livraria.DAO;

import org.springframework.stereotype.Repository;
import uel.br.Livraria.Model.Compra;
import uel.br.Livraria.Model.Cliente;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class PgCompraDAO implements CompraDAO{
   private final Connection connection;

   private final PgClienteDAO clienteDAO;

   public PgCompraDAO(Connection connection) {
      this.connection = connection;
      this.clienteDAO = new PgClienteDAO(connection);
   }

   private static final String CREATE_QUERY =
                                "INSERT INTO livraria.Compra (Num_Nota_Fiscal, Data_Compra, Total, CPF_Cliente) VALUES (?, ?, ?, ?);";

   private static final String READ_QUERY =
                                "SELECT Data_Compra, Total, CPF_Cliente FROM livraria.Compra WHERE Num_Nota_Fiscal = ?;";

   private static final String UPDATE_QUERY =
                                "UPDATE livraria.Compra SET Data_Compra = ?, Total = ?, CPF_Cliente = ? WHERE Num_Nota_Fiscal = ?;";

   private static final String DELETE_QUERY =
                                "DELETE FROM livraria.Compra WHERE Num_Nota_Fiscal = ?;";

   private static final String ALL_QUERY =
                                "SELECT Num_Nota_Fiscal, Data_Compra, Total, CPF_Cliente FROM livraria.Compra ORDER BY Num_Nota_Fiscal;";
      
                                
   // ===== CREATE COMPRA =====                             
   @Override
   public void create(Compra compra) throws SQLException {
      try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
          statement.setLong(1, compra.getNum_Nota_Fiscal());
          statement.setDate(2, compra.getData_Compra());
          statement.setBigDecimal(3, compra.getTotal());
          statement.setString(4, compra.getCliente().getCPF());

          statement.executeUpdate();
      } catch (SQLException ex) {
            Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if(ex.getMessage().contains("pk_Compra")) {
               throw new SQLException("Erro ao inserir compra: Número de Nota Fiscal duplicado.");
           }else if (ex.getMessage().contains("not-null")) {
               throw new SQLException("Erro ao inserir compra: campo obrigatório está em branco.");
           }else {
               throw new SQLException("Erro ao inserir compra.");
           }
      }        
   }
   
   // ===== READ COMPRA =====
   @Override
   public Compra read(Long Num_Nota_Fiscal) throws SQLException {
      Compra compra = new Compra();

      try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
         statement.setLong(1, Num_Nota_Fiscal);
         try (ResultSet result = statement.executeQuery()) {
               if (result.next()) {
                  compra.setNum_Nota_Fiscal(Num_Nota_Fiscal);
                  compra.setData_Compra(result.getDate("Data_Compra"));
                  compra.setTotal(result.getBigDecimal("Total"));
                  Cliente cliente = clienteDAO.read(result.getString("CPF_Cliente"));
                  compra.setCliente(cliente);
               } else {
                  throw new SQLException("Erro ao visualizar: compra não encontrada.");
               }
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
         if (ex.getMessage().equals("Erro ao visualizar: compra não encontrada.")) {
            throw ex;
         } else {
            throw new SQLException("Erro ao visualizar compra.");
         }
      }

      return compra;
   } 

   // ===== UPDATE COMPRA =====
   @Override
   public void update(Compra compra) throws SQLException {
      String query = UPDATE_QUERY;

      try (PreparedStatement statement = connection.prepareStatement(query)) {
          statement.setDate(1, compra.getData_Compra());
          statement.setBigDecimal(2, compra.getTotal());
          statement.setString(3, compra.getCliente().getCPF());
          statement.setLong(4, compra.getNum_Nota_Fiscal());

         if (statement.executeUpdate() < 1) {
            throw new SQLException("Erro ao editar: compra não encontrada.");
         }
       } catch (SQLException ex) {
         Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
 
         if (ex.getMessage().equals("Erro ao editar: compra não encontrada.")) {
             throw ex;
         } else if (ex.getMessage().contains("not-null")) {
             throw new SQLException("Erro ao editar compra: campo obrigatório está em branco.");
         } else {
             throw new SQLException("Erro ao editar compra.");
         }
     }        
   }
   
   // ===== DELETE COMPRA =====
   @Override
   public void delete(Long Num_Nota_Fiscal) throws SQLException {
     try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
         statement.setLong(1, Num_Nota_Fiscal);

         if (statement.executeUpdate() < 1) {
             throw new SQLException("Erro ao excluir: compra não encontrada.");
         }
     } catch (SQLException ex) {
         Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         if (ex.getMessage().equals("Erro ao excluir: compra não encontrada.")) {
             throw ex;
         } else {
             throw new SQLException("Erro ao excluir compra.");
         }
      }
   }

   // ===== LIST ALL COMPRAS =====
   @Override
   public List<Compra> all() throws SQLException {
      List<Compra> compralist = new ArrayList<>();

      try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
         ResultSet result = statement.executeQuery()) {
         while (result.next()) {
            Compra compra = new Compra();
            compra.setNum_Nota_Fiscal(result.getLong("Num_Nota_Fiscal"));
            compra.setData_Compra(result.getDate("Data_Compra"));
            compra.setTotal(result.getBigDecimal("Total"));
            Cliente cliente = clienteDAO.read(result.getString("CPF_Cliente"));
            compra.setCliente(cliente);

            compralist.add(compra);
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         throw new SQLException("Erro ao listar compras.");
      }

      return compralist;        
   }
}