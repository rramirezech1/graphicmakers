package p_09_inventario;

import java.io.Serializable;

public class Cat_gruposdearticulos implements Serializable {

    private String cod_gru, cod_emp, nom_gru, abr_gru;

    public Cat_gruposdearticulos() {
    }

    public Cat_gruposdearticulos(String cod_gru, String cod_emp, String nom_gru, String abr_gru) {
        this.cod_gru = cod_gru;
        this.cod_emp = cod_emp;
        this.nom_gru = nom_gru;
        this.abr_gru = abr_gru;
    }

    public String getCod_gru() {
        return cod_gru;
    }

    public void setCod_gru(String cod_gru) {
        this.cod_gru = cod_gru;
    }

    public String getCod_emp() {
        return cod_emp;
    }

    public void setCod_emp(String cod_emp) {
        this.cod_emp = cod_emp;
    }

    public String getNom_gru() {
        return nom_gru;
    }

    public void setNom_gru(String nom_gru) {
        this.nom_gru = nom_gru;
    }

    public String getAbr_gru() {
        return abr_gru;
    }

    public void setAbr_gru(String abr_gru) {
        this.abr_gru = abr_gru;
    }

}
