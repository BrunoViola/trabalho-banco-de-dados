package uel.br.Livraria.Model;

import java.sql.Date;

public class Cliente {
    private String CPF;
    private char Sexo;
    private Date Data_nascimento;
    private String Email;
    private String Pnome;
    private String Snome;
    private String Cidade;
    private String Estado;

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public char getSexo() {
        return Sexo;
    }

    public void setSexo(char sexo) {
        Sexo = sexo;
    }

    public java.sql.Date getData_nascimento() {
        return (java.sql.Date) Data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        Data_nascimento = data_nascimento;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPnome() {
        return Pnome;
    }

    public void setPnome(String pnome) {
        Pnome = pnome;
    }

    public String getSnome() {
        return Snome;
    }

    public void setSnome(String snome) {
        Snome = snome;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
