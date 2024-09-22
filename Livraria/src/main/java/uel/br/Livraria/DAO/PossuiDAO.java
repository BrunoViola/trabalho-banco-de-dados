package uel.br.Livraria.DAO;

import uel.br.Livraria.Model.Possui;

import java.sql.SQLException;

public interface PossuiDAO extends DAO<Possui, Integer>{
    public Possui read(Long Num_Nota_Fiscal_Compra, Long ISBN_Livro) throws SQLException;
    public void delete(Long Num_Nota_Fiscal_Compra, Long ISBN_Livro) throws SQLException;
}
