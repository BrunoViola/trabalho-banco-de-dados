package uel.br.Livraria.Model;

import java.math.BigDecimal;

public class Item {
    Livro livro;
    Integer Quantidade;
    BigDecimal Preco;

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Integer getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        Quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return Preco;
    }

    public void setPreco(BigDecimal preco) {
        Preco = preco;
    }
}
