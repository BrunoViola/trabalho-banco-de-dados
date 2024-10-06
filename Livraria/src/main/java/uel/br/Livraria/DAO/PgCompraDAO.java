package uel.br.Livraria.DAO;

import org.springframework.stereotype.Repository;
import uel.br.Livraria.Model.Compra;
import uel.br.Livraria.Model.Cliente;
import uel.br.Livraria.Model.Item;
import uel.br.Livraria.Model.Livro;

import java.math.BigDecimal;
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
public class PgCompraDAO implements DAO<Compra, Long>{
   private final Connection connection;
   private final PgClienteDAO clienteDAO;
   private final PgLivroDAO pgLivroDAO;

   public PgCompraDAO(Connection connection) {
      this.connection = connection;
      this.clienteDAO = new PgClienteDAO(connection);
      this.pgLivroDAO = new PgLivroDAO(connection);
   }

   private static final String CREATE_QUERY =
                                "INSERT INTO livraria.Compra (Num_Nota_Fiscal, Data_Compra, Total, CPF_Cliente) VALUES (?, ?, ?, ?);";

   private static final String READ_QUERY =
                                "SELECT Data_Compra, CPF_Cliente FROM livraria.Compra WHERE Num_Nota_Fiscal = ?;";

   private static final String UPDATE_QUERY =
                                "UPDATE livraria.Compra SET Data_Compra = ?, Total = ?, CPF_Cliente = ? WHERE Num_Nota_Fiscal = ?;";

   private static final String DELETE_QUERY =
                                "DELETE FROM livraria.Compra WHERE Num_Nota_Fiscal = ?;";

   private static final String ALL_QUERY =
                                "SELECT Num_Nota_Fiscal, Data_Compra, CPF_Cliente FROM livraria.Compra ORDER BY Num_Nota_Fiscal;";
      
   private static final String LIST_ITENS_QUERY =
           "SELECT ISBN_Livro, Quantidade, Preco FROM livraria.Possui WHERE (Num_Nota_Fiscal_Compra = ?)";

   private static final String DELETE_ITENS_QUERY =
           "DELETE FROM livraria.Possui WHERE Num_Nota_Fiscal_Compra = ?";

   private static final String CREATE_ITENS_QUERY =
           "INSERT INTO livraria.Possui (Num_Nota_Fiscal_Compra, ISBN_Livro, Quantidade, Preco) VALUES (?, ?, ?, ?)";

    // ===== LIST ITENS POR NUM NOTA FISCAL =====
   private List<Item> listItensByNumNota(Long numNota) throws SQLException {
        List<Item> itensPossui = new ArrayList<>();

        long livroISBN;
        Livro livro;
        Item item;

        try (PreparedStatement statement = connection.prepareStatement(LIST_ITENS_QUERY)) {
            statement.setLong(1, numNota);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                item = new Item();
                livroISBN = result.getLong("ISBN_Livro");
                livro = pgLivroDAO.read(livroISBN);
                item.setLivro(livro);
                item.setQuantidade(result.getInt("Quantidade"));
                item.setPreco(result.getBigDecimal("Preco"));

                itensPossui.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao encontrar itens a partir do número da nota fiscal.");
        }

        return itensPossui;
   }

    // ===== UPDATE ITENS POR NUM NOTA FISCAL =====
    private void updateItensByNumNota(Long numNota, List<Item> novosItens) throws SQLException {
        removeItensByNumNota(numNota);

        for (Item item : novosItens) {
            addItenToNumNota(numNota, item);
        }
    }

    // ===== REMOVE ITENS POR NUM NOTA FISCAL =====
    private void removeItensByNumNota(Long numNota) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ITENS_QUERY)) {
            statement.setLong(1, numNota);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao remover itens do numero da nota fiscal.");
        }
    }

    // ===== CREATE ITENS do NUM NOTA FISCAL =====
    private void addItenToNumNota(Long numNota, Item item) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_ITENS_QUERY)) {
            statement.setLong(1, numNota);
            statement.setLong(2, item.getLivro().getISBN());
            statement.setInt(3, item.getQuantidade());
            statement.setBigDecimal(4, item.getPreco());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao adicionar item ao num nota fiscal.");
        }
    }

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

       if (compra.getItens() != null && !compra.getItens().isEmpty()) {
           for (Item item : compra.getItens()) {
               addItenToNumNota(compra.getNum_Nota_Fiscal(), item);
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
                  //compra.setTotal(result.getBigDecimal("Total"));
                  Cliente cliente = clienteDAO.read(result.getString("CPF_Cliente"));
                  compra.setCliente(cliente);
                  compra.setItens(listItensByNumNota(Num_Nota_Fiscal));
                  compra.setTotal(new BigDecimal(0));
                  for (Item item : compra.getItens()) {
                       compra.setTotal((compra.getTotal()).add(item.getPreco()).multiply(new BigDecimal(item.getQuantidade())));
                  }
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
          compra.setTotal(new BigDecimal(0));
          for (Item item : compra.getItens()) {
              compra.setTotal((compra.getTotal()).add(item.getPreco()).multiply(new BigDecimal(item.getQuantidade())));
          }
          statement.setBigDecimal(2, compra.getTotal());
          statement.setString(3, compra.getCliente().getCPF());
          statement.setLong(4, compra.getNum_Nota_Fiscal());

         if (statement.executeUpdate() < 1) {
            throw new SQLException("Erro ao editar: compra não encontrada.");
         }

         updateItensByNumNota(compra.getNum_Nota_Fiscal(), compra.getItens());
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
     removeItensByNumNota(Num_Nota_Fiscal);
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
            //compra.setTotal(result.getBigDecimal("Total"));
            Cliente cliente = clienteDAO.read(result.getString("CPF_Cliente"));
            compra.setCliente(cliente);
            compra.setItens(listItensByNumNota(result.getLong("Num_Nota_Fiscal")));
            compra.setTotal(new BigDecimal(0));
             for (Item item : compra.getItens()) {
                 compra.setTotal((compra.getTotal()).add(item.getPreco()).multiply(new BigDecimal(item.getQuantidade())));
             }

            compralist.add(compra);
         }
      } catch (SQLException ex) {
         Logger.getLogger(PgCompraDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

         throw new SQLException("Erro ao listar compras.");
      }

      return compralist;        
   }
}