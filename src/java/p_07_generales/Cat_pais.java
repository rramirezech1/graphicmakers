package p_07_generales;

import java.io.Serializable;

public class Cat_pais implements Serializable {

    private String cod_pai, nom_pai, abr_pai;

    public Cat_pais() {
    }

    public Cat_pais(String cod_pai, String nom_pai, String abr_pai) {
        this.cod_pai = cod_pai;
        this.nom_pai = nom_pai;
        this.abr_pai = abr_pai;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getNom_pai() {
        return nom_pai;
    }

    public void setNom_pai(String nom_pai) {
        this.nom_pai = nom_pai;
    }

    public String getAbr_pai() {
        return abr_pai;
    }

    public void setAbr_pai(String abr_pai) {
        this.abr_pai = abr_pai;
    }

}
