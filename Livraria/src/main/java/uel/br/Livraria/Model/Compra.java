package uel.br.Livraria.Model;

import java.math.BigDecimal;
import java.sql.Date;

public class Compra {
    private Long Num_Nota_Fiscal;
    private Date Data_Compra;
    private BigDecimal Total;
    private Cliente cliente;

    public Long getNum_Nota_Fiscal() {
        return Num_Nota_Fiscal;
    }

    public void setNum_Nota_Fiscal(Long num_Nota_Fiscal) {
        Num_Nota_Fiscal = num_Nota_Fiscal;
    }

    public Date getData_Compra() {
        return Data_Compra;
    }

    public void setData_Compra(Date data_Compra) {
        Data_Compra = data_Compra;
    }

    public BigDecimal getTotal() {
        return Total;
    }

    public void setTotal(BigDecimal total) {
        Total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
