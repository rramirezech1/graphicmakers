package p_09_inventario;

import java.io.Serializable;

public class Cat_transacciones implements Serializable {

    private String cod_tra, cod_pai, fec_tra, flg_ing_sal, tip_mov, doc_tra, cod_cli_pro,
            det_obs, cod_usu, flg_anu, nompai, nommov, nomclipro, cod_res, cor_mov, cod_are, obs_anu, coddoc, ingsal;

    public Cat_transacciones() {
    }

    public Cat_transacciones(String cod_tra, String cod_pai, String fec_tra, String flg_ing_sal, String tip_mov, String doc_tra, String cod_cli_pro, String det_obs, String cod_usu, String flg_anu, String nompai, String nommov, String nomclipro, String cod_res, String cor_mov, String cod_are, String obs_anu, String coddoc, String ingsal) {
        this.cod_tra = cod_tra;
        this.cod_pai = cod_pai;
        this.fec_tra = fec_tra;
        this.flg_ing_sal = flg_ing_sal;
        this.tip_mov = tip_mov;
        this.doc_tra = doc_tra;
        this.cod_cli_pro = cod_cli_pro;
        this.det_obs = det_obs;
        this.cod_usu = cod_usu;
        this.flg_anu = flg_anu;
        this.nompai = nompai;
        this.nommov = nommov;
        this.nomclipro = nomclipro;
        this.cod_res = cod_res;
        this.cor_mov = cor_mov;
        this.cod_are = cod_are;
        this.obs_anu = obs_anu;
        this.coddoc = coddoc;
        this.ingsal = ingsal;
    }

   
    public String getCod_tra() {
        return cod_tra;
    }

    public void setCod_tra(String cod_tra) {
        this.cod_tra = cod_tra;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public void setFec_tra(String fec_tra) {
        this.fec_tra = fec_tra;
    }

    public String getFlg_ing_sal() {
        return flg_ing_sal;
    }

    public void setFlg_ing_sal(String flg_ing_sal) {
        this.flg_ing_sal = flg_ing_sal;
    }

    public String getTip_mov() {
        return tip_mov;
    }

    public void setTip_mov(String tip_mov) {
        this.tip_mov = tip_mov;
    }

    public String getDoc_tra() {
        return doc_tra;
    }

    public void setDoc_tra(String doc_tra) {
        this.doc_tra = doc_tra;
    }

    public String getCod_cli_pro() {
        return cod_cli_pro;
    }

    public void setCod_cli_pro(String cod_cli_pro) {
        this.cod_cli_pro = cod_cli_pro;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getFlg_anu() {
        return flg_anu;
    }

    public void setFlg_anu(String flg_anu) {
        this.flg_anu = flg_anu;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

    public String getNommov() {
        return nommov;
    }

    public void setNommov(String nommov) {
        this.nommov = nommov;
    }

    public String getNomclipro() {
        return nomclipro;
    }

    public void setNomclipro(String nomclipro) {
        this.nomclipro = nomclipro;
    }

    public String getCod_res() {
        return cod_res;
    }

    public void setCod_res(String cod_res) {
        this.cod_res = cod_res;
    }

    public String getCor_mov() {
        return cor_mov;
    }

    public void setCor_mov(String cor_mov) {
        this.cor_mov = cor_mov;
    }

    public String getCod_are() {
        return cod_are;
    }

    public void setCod_are(String cod_are) {
        this.cod_are = cod_are;
    }

    public String getObs_anu() {
        return obs_anu;
    }

    public void setObs_anu(String obs_anu) {
        this.obs_anu = obs_anu;
    }

    public String getCoddoc() {
        return coddoc;
    }

    public void setCoddoc(String coddoc) {
        this.coddoc = coddoc;
    }

    public String getIngsal() {
        return ingsal;
    }

    public void setIngsal(String ingsal) {
        this.ingsal = ingsal;
    }
    
    

}
