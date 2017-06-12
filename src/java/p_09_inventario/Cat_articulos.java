package p_09_inventario;

import java.io.Serializable;

public class Cat_articulos implements Serializable {

    private String cod_art, cod_emp, cod_gru, cod_lin, cod_ref, cod_alt, nom_art, des_art, cod_mar, img_art, cod_uni_med,
            cod_tem, reg_san, flg_exe, flg_per, flg_usa_lot, flg_imp, flg_exp, flg_pro_com, pre_sin_iva;

    public Cat_articulos() {
    }

    public Cat_articulos(String cod_art, String cod_emp, String cod_gru, String cod_lin, String cod_ref, String cod_alt, String nom_art, String des_art, String cod_mar, String img_art, String cod_uni_med, String cod_tem, String reg_san, String flg_exe, String flg_per, String flg_usa_lot, String flg_imp, String flg_exp, String flg_pro_com, String pre_sin_iva) {
        this.cod_art = cod_art;
        this.cod_emp = cod_emp;
        this.cod_gru = cod_gru;
        this.cod_lin = cod_lin;
        this.cod_ref = cod_ref;
        this.cod_alt = cod_alt;
        this.nom_art = nom_art;
        this.des_art = des_art;
        this.cod_mar = cod_mar;
        this.img_art = img_art;
        this.cod_uni_med = cod_uni_med;
        this.cod_tem = cod_tem;
        this.reg_san = reg_san;
        this.flg_exe = flg_exe;
        this.flg_per = flg_per;
        this.flg_usa_lot = flg_usa_lot;
        this.flg_imp = flg_imp;
        this.flg_exp = flg_exp;
        this.flg_pro_com = flg_pro_com;
        this.pre_sin_iva = pre_sin_iva;
    }

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public String getCod_emp() {
        return cod_emp;
    }

    public void setCod_emp(String cod_emp) {
        this.cod_emp = cod_emp;
    }

    public String getCod_gru() {
        return cod_gru;
    }

    public void setCod_gru(String cod_gru) {
        this.cod_gru = cod_gru;
    }

    public String getCod_lin() {
        return cod_lin;
    }

    public void setCod_lin(String cod_lin) {
        this.cod_lin = cod_lin;
    }

    public String getCod_ref() {
        return cod_ref;
    }

    public void setCod_ref(String cod_ref) {
        this.cod_ref = cod_ref;
    }

    public String getCod_alt() {
        return cod_alt;
    }

    public void setCod_alt(String cod_alt) {
        this.cod_alt = cod_alt;
    }

    public String getNom_art() {
        return nom_art;
    }

    public void setNom_art(String nom_art) {
        this.nom_art = nom_art;
    }

    public String getDes_art() {
        return des_art;
    }

    public void setDes_art(String des_art) {
        this.des_art = des_art;
    }

    public String getCod_mar() {
        return cod_mar;
    }

    public void setCod_mar(String cod_mar) {
        this.cod_mar = cod_mar;
    }

    public String getImg_art() {
        return img_art;
    }

    public void setImg_art(String img_art) {
        this.img_art = img_art;
    }

    public String getCod_uni_med() {
        return cod_uni_med;
    }

    public void setCod_uni_med(String cod_uni_med) {
        this.cod_uni_med = cod_uni_med;
    }

    public String getCod_tem() {
        return cod_tem;
    }

    public void setCod_tem(String cod_tem) {
        this.cod_tem = cod_tem;
    }

    public String getReg_san() {
        return reg_san;
    }

    public void setReg_san(String reg_san) {
        this.reg_san = reg_san;
    }

    public String getFlg_exe() {
        return flg_exe;
    }

    public void setFlg_exe(String flg_exe) {
        this.flg_exe = flg_exe;
    }

    public String getFlg_per() {
        return flg_per;
    }

    public void setFlg_per(String flg_per) {
        this.flg_per = flg_per;
    }

    public String getFlg_usa_lot() {
        return flg_usa_lot;
    }

    public void setFlg_usa_lot(String flg_usa_lot) {
        this.flg_usa_lot = flg_usa_lot;
    }

    public String getFlg_imp() {
        return flg_imp;
    }

    public void setFlg_imp(String flg_imp) {
        this.flg_imp = flg_imp;
    }

    public String getFlg_exp() {
        return flg_exp;
    }

    public void setFlg_exp(String flg_exp) {
        this.flg_exp = flg_exp;
    }

    public String getFlg_pro_com() {
        return flg_pro_com;
    }

    public void setFlg_pro_com(String flg_pro_com) {
        this.flg_pro_com = flg_pro_com;
    }

    public String getPre_sin_iva() {
        return pre_sin_iva;
    }

    public void setPre_sin_iva(String pre_sin_iva) {
        this.pre_sin_iva = pre_sin_iva;
    }

}
