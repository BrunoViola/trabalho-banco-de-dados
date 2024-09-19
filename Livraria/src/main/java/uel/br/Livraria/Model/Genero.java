package uel.br.Livraria.Model;

public class Genero {
    private Integer ID;
    private String Nome;
    private Integer ID_Secao;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Integer getID_Secao() {
        return ID_Secao;
    }

    public void setID_Secao(Integer ID_Secao) {
        this.ID_Secao = ID_Secao;
    }
}
