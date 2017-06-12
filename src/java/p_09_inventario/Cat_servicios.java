package p_09_inventario;

import java.io.Serializable;

public class Cat_servicios implements Serializable {

    private String cod_ser, nom_ser, des_ser;

    public Cat_servicios() {
    }

    public Cat_servicios(String cod_ser, String nom_ser, String des_ser) {
        this.cod_ser = cod_ser;
        this.nom_ser = nom_ser;
        this.des_ser = des_ser;
    }

    public String getCod_ser() {
        return cod_ser;
    }

    public void setCod_ser(String cod_ser) {
        this.cod_ser = cod_ser;
    }

    public String getNom_ser() {
        return nom_ser;
    }

    public void setNom_ser(String nom_ser) {
        this.nom_ser = nom_ser;
    }

    public String getDes_ser() {
        return des_ser;
    }

    public void setDes_ser(String des_ser) {
        this.des_ser = des_ser;
    }
    
    

}
