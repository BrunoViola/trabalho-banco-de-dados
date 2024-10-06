package uel.br.Livraria.Model;

import java.math.BigDecimal;

public class Grafico {

   private String nomeAutor;
   private Integer quantidadeLivrosAutores;
   private String faixaEtaria;
   private Integer quantidadePessoasFaixas;
   private char Sexo;
   private BigDecimal gastoMedioSexo;
   private String Localizacao;
   private BigDecimal totalGastoLocalidade;
   private String secaoNome;
   private BigDecimal precoMedioEstoque;
   private BigDecimal precoMedioCompras;
   private String Mes;
   private BigDecimal totalVendasMes;
   private String faixaPreco;
   private BigDecimal unidadesVendidasFaixa;
   
   public String getNomeAutor() {
      return nomeAutor;
   }
   public void setNomeAutor(String nomeAutor) {
      this.nomeAutor = nomeAutor;
   }
   public Integer getQuantidadeLivrosAutores() {
      return quantidadeLivrosAutores;
   }
   public void setQuantidadeLivrosAutores(Integer quantidadeLivrosAutores) {
      this.quantidadeLivrosAutores = quantidadeLivrosAutores;
   }
   public String getFaixaEtaria() {
      return faixaEtaria;
   }
   public void setFaixaEtaria(String faixaEtaria) {
      this.faixaEtaria = faixaEtaria;
   }
   public Integer getQuantidadePessoasFaixas() {
      return quantidadePessoasFaixas;
   }
   public void setQuantidadePessoasFaixas(Integer quantidadePessoasFaixas) {
      this.quantidadePessoasFaixas = quantidadePessoasFaixas;
   }
   public char getSexo() {
      return Sexo;
   }
   public void setSexo(char sexo) {
      Sexo = sexo;
   }
   public BigDecimal getGastoMedioSexo() {
      return gastoMedioSexo;
   }
   public void setGastoMedioSexo(BigDecimal gastoMedioSexo) {
      this.gastoMedioSexo = gastoMedioSexo;
   }
   public String getLocalizacao() {
      return Localizacao;
   }
   public void setLocalizacao(String localizacao) {
      Localizacao = localizacao;
   }
   public BigDecimal getTotalGastoLocalidade() {
      return totalGastoLocalidade;
   }
   public void setTotalGastoLocalidade(BigDecimal totalGastoLocalidade) {
      this.totalGastoLocalidade = totalGastoLocalidade;
   }
   public String getSecaoNome() {
      return secaoNome;
   }
   public void setSecaoNome(String secaoNome) {
      this.secaoNome = secaoNome;
   }
   public BigDecimal getPrecoMedioEstoque() {
      return precoMedioEstoque;
   }
   public void setPrecoMedioEstoque(BigDecimal precoMedioEstoque) {
      this.precoMedioEstoque = precoMedioEstoque;
   }
   public BigDecimal getPrecoMedioCompras() {
      return precoMedioCompras;
   }
   public void setPrecoMedioCompras(BigDecimal precoMedioCompras) {
      this.precoMedioCompras = precoMedioCompras;
   }
   public String getMes() {
      return Mes;
   }
   public void setMes(String mes) {
      Mes = mes;
   }
   public BigDecimal getTotalVendasMes() {
      return totalVendasMes;
   }
   public void setTotalVendasMes(BigDecimal totalVendasMes) {
      this.totalVendasMes = totalVendasMes;
   }
   public String getFaixaPreco() {
      return faixaPreco;
   }
   public void setFaixaPreco(String faixaPreco) {
      this.faixaPreco = faixaPreco;
   }
   public BigDecimal getUnidadesVendidasFaixa() {
      return unidadesVendidasFaixa;
   }
   public void setUnidadesVendidasFaixa(BigDecimal unidadesVendidasFaixa) {
      this.unidadesVendidasFaixa = unidadesVendidasFaixa;
   }
}