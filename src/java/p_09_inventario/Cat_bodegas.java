package p_09_inventario;

import java.io.Serializable;

public class Cat_bodegas implements Serializable {

    private String cod_bod, cod_suc, nom_bod;

    public Cat_bodegas() {
    }

    public Cat_bodegas(String cod_bod, String cod_suc, String nom_bod) {
        this.cod_bod = cod_bod;
        this.cod_suc = cod_suc;
        this.nom_bod = nom_bod;
    }

    public String getCod_bod() {
        return cod_bod;
    }

    public void setCod_bod(String cod_bod) {
        this.cod_bod = cod_bod;
    }

    public String getCod_suc() {
        return cod_suc;
    }

    public void setCod_suc(String cod_suc) {
        this.cod_suc = cod_suc;
    }

    public String getNom_bod() {
        return nom_bod;
    }

    public void setNom_bod(String nom_bod) {
        this.nom_bod = nom_bod;
    }

}
