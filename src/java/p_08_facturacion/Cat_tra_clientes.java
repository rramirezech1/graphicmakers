package p_08_facturacion;

import java.io.Serializable;

public class Cat_tra_clientes implements Serializable {

    private String cor_tra, cod_cli, fec_tra, cod_usu;
    private Double det_can;
    private String cod_ser, cod_pro, det_obs, flg_fac, nombreitem, not_rec, fec_rec, cod_suc;

    public Cat_tra_clientes() {
    }

    public Cat_tra_clientes(String cor_tra, String cod_cli, String fec_tra, String cod_usu, Double det_can, String cod_ser, String cod_pro, String det_obs, String flg_fac, String nombreitem, String not_rec, String fec_rec, String cod_suc) {
        this.cor_tra = cor_tra;
        this.cod_cli = cod_cli;
        this.fec_tra = fec_tra;
        this.cod_usu = cod_usu;
        this.det_can = det_can;
        this.cod_ser = cod_ser;
        this.cod_pro = cod_pro;
        this.det_obs = det_obs;
        this.flg_fac = flg_fac;
        this.nombreitem = nombreitem;
        this.not_rec = not_rec;
        this.fec_rec = fec_rec;
        this.cod_suc = cod_suc;
    }

    public String getCor_tra() {
        return cor_tra;
    }

    public void setCor_tra(String cor_tra) {
        this.cor_tra = cor_tra;
    }

    public String getCod_cli() {
        return cod_cli;
    }

    public void setCod_cli(String cod_cli) {
        this.cod_cli = cod_cli;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public void setFec_tra(String fec_tra) {
        this.fec_tra = fec_tra;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public Double getDet_can() {
        return det_can;
    }

    public void setDet_can(Double det_can) {
        this.det_can = det_can;
    }

    public String getCod_ser() {
        return cod_ser;
    }

    public void setCod_ser(String cod_ser) {
        this.cod_ser = cod_ser;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getFlg_fac() {
        return flg_fac;
    }

    public void setFlg_fac(String flg_fac) {
        this.flg_fac = flg_fac;
    }

    public String getNombreitem() {
        return nombreitem;
    }

    public void setNombreitem(String nombreitem) {
        this.nombreitem = nombreitem;
    }

    public String getNot_rec() {
        return not_rec;
    }

    public void setNot_rec(String not_rec) {
        this.not_rec = not_rec;
    }

    public String getFec_rec() {
        return fec_rec;
    }

    public void setFec_rec(String fec_rec) {
        this.fec_rec = fec_rec;
    }

    public String getCod_suc() {
        return cod_suc;
    }

    public void setCod_suc(String cod_suc) {
        this.cod_suc = cod_suc;
    }

}
