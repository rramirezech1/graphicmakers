package p_07_generales;

import java.io.Serializable;

public class Cat_sucursales implements Serializable {

    private String cod_suc, cod_emp, cod_est, nom_suc, cod_per_res, det_dir, det_tel, det_tel_2;

    public Cat_sucursales() {
    }

    public Cat_sucursales(String cod_suc, String cod_emp, String cod_est, String nom_suc, String cod_per_res, String det_dir, String det_tel, String det_tel_2) {
        this.cod_suc = cod_suc;
        this.cod_emp = cod_emp;
        this.cod_est = cod_est;
        this.nom_suc = nom_suc;
        this.cod_per_res = cod_per_res;
        this.det_dir = det_dir;
        this.det_tel = det_tel;
        this.det_tel_2 = det_tel_2;
    }

    public String getCod_suc() {
        return cod_suc;
    }

    public void setCod_suc(String cod_suc) {
        this.cod_suc = cod_suc;
    }

    public String getCod_emp() {
        return cod_emp;
    }

    public void setCod_emp(String cod_emp) {
        this.cod_emp = cod_emp;
    }

    public String getCod_est() {
        return cod_est;
    }

    public void setCod_est(String cod_est) {
        this.cod_est = cod_est;
    }

    public String getNom_suc() {
        return nom_suc;
    }

    public void setNom_suc(String nom_suc) {
        this.nom_suc = nom_suc;
    }

    public String getCod_per_res() {
        return cod_per_res;
    }

    public void setCod_per_res(String cod_per_res) {
        this.cod_per_res = cod_per_res;
    }

    public String getDet_dir() {
        return det_dir;
    }

    public void setDet_dir(String det_dir) {
        this.det_dir = det_dir;
    }

    public String getDet_tel() {
        return det_tel;
    }

    public void setDet_tel(String det_tel) {
        this.det_tel = det_tel;
    }

    public String getDet_tel_2() {
        return det_tel_2;
    }

    public void setDet_tel_2(String det_tel_2) {
        this.det_tel_2 = det_tel_2;
    }

}
