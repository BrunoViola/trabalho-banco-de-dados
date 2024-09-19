package uel.br.Livraria.Model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class Compra {
    private BigInteger Num_Nota_Fiscal;
    private Date Data_Compra;
    private BigDecimal Total;
    private String CPF_Cliente;

    public BigInteger getNum_Nota_Fiscal() {
        return Num_Nota_Fiscal;
    }

    public void setNum_Nota_Fiscal(BigInteger num_Nota_Fiscal) {
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

    public String getCPF_Cliente() {
        return CPF_Cliente;
    }

    public void setCPF_Cliente(String CPF_Cliente) {
        this.CPF_Cliente = CPF_Cliente;
    }
}
