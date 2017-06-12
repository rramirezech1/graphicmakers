package p_08_facturacion;

import java.io.Serializable;

public class Cat_prefactura implements Serializable {

    private String flg_ser_pro, cor_pre_fac, cod_ser, cod_art, nomitem;
    private Double det_can, det_can_con;
    private String cod_uni_med, nomunimed;
    private Double pre_sin_iva, iva, pre_con_iva, por_des, totalexe, totalgrav, total;
    private String cod_cli, cod_tra_cli, lote;

    public Cat_prefactura() {
    }

    public Cat_prefactura(String flg_ser_pro, String cor_pre_fac, String cod_ser, String cod_art, String nomitem, Double det_can, Double det_can_con, String cod_uni_med, String nomunimed, Double pre_sin_iva, Double iva, Double pre_con_iva, Double por_des, Double totalexe, Double totalgrav, Double total, String cod_cli, String cod_tra_cli, String lote) {
        this.flg_ser_pro = flg_ser_pro;
        this.cor_pre_fac = cor_pre_fac;
        this.cod_ser = cod_ser;
        this.cod_art = cod_art;
        this.nomitem = nomitem;
        this.det_can = det_can;
        this.det_can_con = det_can_con;
        this.cod_uni_med = cod_uni_med;
        this.nomunimed = nomunimed;
        this.pre_sin_iva = pre_sin_iva;
        this.iva = iva;
        this.pre_con_iva = pre_con_iva;
        this.por_des = por_des;
        this.totalexe = totalexe;
        this.totalgrav = totalgrav;
        this.total = total;
        this.cod_cli = cod_cli;
        this.cod_tra_cli = cod_tra_cli;
        this.lote = lote;
    }

    public String getFlg_ser_pro() {
        return flg_ser_pro;
    }

    public void setFlg_ser_pro(String flg_ser_pro) {
        this.flg_ser_pro = flg_ser_pro;
    }

    public String getCor_pre_fac() {
        return cor_pre_fac;
    }

    public void setCor_pre_fac(String cor_pre_fac) {
        this.cor_pre_fac = cor_pre_fac;
    }

    public String getCod_ser() {
        return cod_ser;
    }

    public void setCod_ser(String cod_ser) {
        this.cod_ser = cod_ser;
    }

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public String getNomitem() {
        return nomitem;
    }

    public void setNomitem(String nomitem) {
        this.nomitem = nomitem;
    }

    public Double getDet_can() {
        return det_can;
    }

    public void setDet_can(Double det_can) {
        this.det_can = det_can;
    }

    public Double getDet_can_con() {
        return det_can_con;
    }

    public void setDet_can_con(Double det_can_con) {
        this.det_can_con = det_can_con;
    }

    public String getCod_uni_med() {
        return cod_uni_med;
    }

    public void setCod_uni_med(String cod_uni_med) {
        this.cod_uni_med = cod_uni_med;
    }

    public String getNomunimed() {
        return nomunimed;
    }

    public void setNomunimed(String nomunimed) {
        this.nomunimed = nomunimed;
    }

    public Double getPre_sin_iva() {
        return pre_sin_iva;
    }

    public void setPre_sin_iva(Double pre_sin_iva) {
        this.pre_sin_iva = pre_sin_iva;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getPre_con_iva() {
        return pre_con_iva;
    }

    public void setPre_con_iva(Double pre_con_iva) {
        this.pre_con_iva = pre_con_iva;
    }

    public Double getPor_des() {
        return por_des;
    }

    public void setPor_des(Double por_des) {
        this.por_des = por_des;
    }

    public Double getTotalexe() {
        return totalexe;
    }

    public void setTotalexe(Double totalexe) {
        this.totalexe = totalexe;
    }

    public Double getTotalgrav() {
        return totalgrav;
    }

    public void setTotalgrav(Double totalgrav) {
        this.totalgrav = totalgrav;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCod_cli() {
        return cod_cli;
    }

    public void setCod_cli(String cod_cli) {
        this.cod_cli = cod_cli;
    }

    public String getCod_tra_cli() {
        return cod_tra_cli;
    }

    public void setCod_tra_cli(String cod_tra_cli) {
        this.cod_tra_cli = cod_tra_cli;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

}
