package uel.br.Livraria.Model;

public class Autor {
    private Integer ID;
    private String Pnome;
    private String Snome;
    private String Nacionalidade;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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

    public String getNacionalidade() {
        return Nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        Nacionalidade = nacionalidade;
    }
}
