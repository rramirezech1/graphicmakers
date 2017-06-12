package p_08_facturacion;

import java.io.Serializable;

public class Cat_clientes implements Serializable {

    private String cod_cli, cod_pai, cod_est, cod_emp, flg_nat_emp, ape_cli, nom_cli, per_con, tel_con, tel_cel, det_mai, doc_ide, doc_nit,
            det_gir, num_reg, fec_nac, flg_gra_con, flg_exe, det_dir, cod_cue_con, cod_des;

    public Cat_clientes() {
    }

    public Cat_clientes(String cod_cli, String cod_pai, String cod_est, String cod_emp, String flg_nat_emp, String ape_cli, String nom_cli, String per_con, String tel_con, String tel_cel, String det_mai, String doc_ide, String doc_nit, String det_gir, String num_reg, String fec_nac, String flg_gra_con, String flg_exe, String det_dir, String cod_cue_con, String cod_des) {
        this.cod_cli = cod_cli;
        this.cod_pai = cod_pai;
        this.cod_est = cod_est;
        this.cod_emp = cod_emp;
        this.flg_nat_emp = flg_nat_emp;
        this.ape_cli = ape_cli;
        this.nom_cli = nom_cli;
        this.per_con = per_con;
        this.tel_con = tel_con;
        this.tel_cel = tel_cel;
        this.det_mai = det_mai;
        this.doc_ide = doc_ide;
        this.doc_nit = doc_nit;
        this.det_gir = det_gir;
        this.num_reg = num_reg;
        this.fec_nac = fec_nac;
        this.flg_gra_con = flg_gra_con;
        this.flg_exe = flg_exe;
        this.det_dir = det_dir;
        this.cod_cue_con = cod_cue_con;
        this.cod_des = cod_des;
    }

    public String getCod_cli() {
        return cod_cli;
    }

    public void setCod_cli(String cod_cli) {
        this.cod_cli = cod_cli;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getCod_est() {
        return cod_est;
    }

    public void setCod_est(String cod_est) {
        this.cod_est = cod_est;
    }

    public String getCod_emp() {
        return cod_emp;
    }

    public void setCod_emp(String cod_emp) {
        this.cod_emp = cod_emp;
    }

    public String getFlg_nat_emp() {
        return flg_nat_emp;
    }

    public void setFlg_nat_emp(String flg_nat_emp) {
        this.flg_nat_emp = flg_nat_emp;
    }

    public String getApe_cli() {
        return ape_cli;
    }

    public void setApe_cli(String ape_cli) {
        this.ape_cli = ape_cli;
    }

    public String getNom_cli() {
        return nom_cli;
    }

    public void setNom_cli(String nom_cli) {
        this.nom_cli = nom_cli;
    }

    public String getPer_con() {
        return per_con;
    }

    public void setPer_con(String per_con) {
        this.per_con = per_con;
    }

    public String getTel_con() {
        return tel_con;
    }

    public void setTel_con(String tel_con) {
        this.tel_con = tel_con;
    }

    public String getTel_cel() {
        return tel_cel;
    }

    public void setTel_cel(String tel_cel) {
        this.tel_cel = tel_cel;
    }

    public String getDet_mai() {
        return det_mai;
    }

    public void setDet_mai(String det_mai) {
        this.det_mai = det_mai;
    }

    public String getDoc_ide() {
        return doc_ide;
    }

    public void setDoc_ide(String doc_ide) {
        this.doc_ide = doc_ide;
    }

    public String getDoc_nit() {
        return doc_nit;
    }

    public void setDoc_nit(String doc_nit) {
        this.doc_nit = doc_nit;
    }

    public String getDet_gir() {
        return det_gir;
    }

    public void setDet_gir(String det_gir) {
        this.det_gir = det_gir;
    }

    public String getNum_reg() {
        return num_reg;
    }

    public void setNum_reg(String num_reg) {
        this.num_reg = num_reg;
    }

    public String getFec_nac() {
        return fec_nac;
    }

    public void setFec_nac(String fec_nac) {
        this.fec_nac = fec_nac;
    }

    public String getFlg_gra_con() {
        return flg_gra_con;
    }

    public void setFlg_gra_con(String flg_gra_con) {
        this.flg_gra_con = flg_gra_con;
    }

    public String getFlg_exe() {
        return flg_exe;
    }

    public void setFlg_exe(String flg_exe) {
        this.flg_exe = flg_exe;
    }

    public String getDet_dir() {
        return det_dir;
    }

    public void setDet_dir(String det_dir) {
        this.det_dir = det_dir;
    }

    public String getCod_cue_con() {
        return cod_cue_con;
    }

    public void setCod_cue_con(String cod_cue_con) {
        this.cod_cue_con = cod_cue_con;
    }

    public String getCod_des() {
        return cod_des;
    }

    public void setCod_des(String cod_des) {
        this.cod_des = cod_des;
    }
    
    

}
