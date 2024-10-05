package uel.br.Livraria.Model;

import java.math.BigDecimal;
import java.util.List;

public class Livro {
    private Long ISBN;
    private String Titulo;
    private Integer Ano;
    private BigDecimal Preco;
    private Integer Estoque;
    private String Descricao;
    private Editora editora;

    private List<Autor> Autores;

    public List<Autor> getAutores() {
        return Autores;
    }

    public void setAutores(List<Autor> autores) {
        Autores = autores;
    }

    public long getISBN() {
        return ISBN;
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public Integer getAno() {
        return Ano;
    }

    public void setAno(Integer ano) {
        Ano = ano;
    }

    public BigDecimal getPreco() {
        return Preco;
    }

    public void setPreco(BigDecimal preco) {
        Preco = preco;
    }

    public Integer getEstoque() {
        return Estoque;
    }

    public void setEstoque(Integer estoque) {
        Estoque = estoque;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }
}
