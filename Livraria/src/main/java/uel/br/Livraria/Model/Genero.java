package uel.br.Livraria.Model;

import java.util.List;

public class Genero {
    private Integer ID;
    private String Nome;
    private Secao secao;

    private List<Livro> Livros;

    public List<Livro> getLivros() {
        return Livros;
    }

    public void setLivros(List<Livro> livros) {
        Livros = livros;
    }

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

    public Secao getSecao() {
        return secao;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }
}
