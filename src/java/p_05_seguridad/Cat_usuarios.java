package p_05_seguridad;

import java.io.Serializable;

public class Cat_usuarios implements Serializable {

    private String cod_usu, nom_usu, des_pas, det_nom, cod_dep, tip_usu, cod_pai, img_usu, cod_suc, usu_mod, fec_mod, nomdep, nompai, nomsuc, nommod;

    public Cat_usuarios() {
    }

    public Cat_usuarios(String cod_usu, String nom_usu, String des_pas, String det_nom, String cod_dep, String tip_usu, String cod_pai, String img_usu, String cod_suc, String usu_mod, String fec_mod, String nomdep, String nompai, String nomsuc, String nommod) {
        this.cod_usu = cod_usu;
        this.nom_usu = nom_usu;
        this.des_pas = des_pas;
        this.det_nom = det_nom;
        this.cod_dep = cod_dep;
        this.tip_usu = tip_usu;
        this.cod_pai = cod_pai;
        this.img_usu = img_usu;
        this.cod_suc = cod_suc;
        this.usu_mod = usu_mod;
        this.fec_mod = fec_mod;
        this.nomdep = nomdep;
        this.nompai = nompai;
        this.nomsuc = nomsuc;
        this.nommod = nommod;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getNom_usu() {
        return nom_usu;
    }

    public void setNom_usu(String nom_usu) {
        this.nom_usu = nom_usu;
    }

    public String getDes_pas() {
        return des_pas;
    }

    public void setDes_pas(String des_pas) {
        this.des_pas = des_pas;
    }

    public String getDet_nom() {
        return det_nom;
    }

    public void setDet_nom(String det_nom) {
        this.det_nom = det_nom;
    }

    public String getCod_dep() {
        return cod_dep;
    }

    public void setCod_dep(String cod_dep) {
        this.cod_dep = cod_dep;
    }

    public String getTip_usu() {
        return tip_usu;
    }

    public void setTip_usu(String tip_usu) {
        this.tip_usu = tip_usu;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getImg_usu() {
        return img_usu;
    }

    public void setImg_usu(String img_usu) {
        this.img_usu = img_usu;
    }

    public String getCod_suc() {
        return cod_suc;
    }

    public void setCod_suc(String cod_suc) {
        this.cod_suc = cod_suc;
    }

    public String getUsu_mod() {
        return usu_mod;
    }

    public void setUsu_mod(String usu_mod) {
        this.usu_mod = usu_mod;
    }

    public String getFec_mod() {
        return fec_mod;
    }

    public void setFec_mod(String fec_mod) {
        this.fec_mod = fec_mod;
    }

    public String getNomdep() {
        return nomdep;
    }

    public void setNomdep(String nomdep) {
        this.nomdep = nomdep;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

    public String getNomsuc() {
        return nomsuc;
    }

    public void setNomsuc(String nomsuc) {
        this.nomsuc = nomsuc;
    }

    public String getNommod() {
        return nommod;
    }

    public void setNommod(String nommod) {
        this.nommod = nommod;
    }
    
    

}
