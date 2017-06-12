package p_09_inventario;

import java.io.Serializable;

public class Cat_lineasarticulos implements Serializable {

    private String cod_lin, cod_gru, nom_lin, abr_lin;

    public Cat_lineasarticulos() {
    }

    public Cat_lineasarticulos(String cod_lin, String cod_gru, String nom_lin, String abr_lin) {
        this.cod_lin = cod_lin;
        this.cod_gru = cod_gru;
        this.nom_lin = nom_lin;
        this.abr_lin = abr_lin;
    }

    public String getCod_lin() {
        return cod_lin;
    }

    public void setCod_lin(String cod_lin) {
        this.cod_lin = cod_lin;
    }

    public String getCod_gru() {
        return cod_gru;
    }

    public void setCod_gru(String cod_gru) {
        this.cod_gru = cod_gru;
    }

    public String getNom_lin() {
        return nom_lin;
    }

    public void setNom_lin(String nom_lin) {
        this.nom_lin = nom_lin;
    }

    public String getAbr_lin() {
        return abr_lin;
    }

    public void setAbr_lin(String abr_lin) {
        this.abr_lin = abr_lin;
    }

}
