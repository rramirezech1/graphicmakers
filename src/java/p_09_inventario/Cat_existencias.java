package p_09_inventario;

import java.io.Serializable;

public class Cat_existencias implements Serializable {

    private String cod_his, fec_tra, cod_art, cod_bod, des_ubi, det_lot, fec_ven, cod_ord,
            exi_ant_bod, exi_act_bod, cos_pro_suc, cod_ref, nomart, unimed, nombod;

    public Cat_existencias() {
    }

    public Cat_existencias(String cod_his, String fec_tra, String cod_art, String cod_bod, String des_ubi, String det_lot, String fec_ven, String cod_ord, String exi_ant_bod, String exi_act_bod, String cos_pro_suc, String cod_ref, String nomart, String unimed, String nombod) {
        this.cod_his = cod_his;
        this.fec_tra = fec_tra;
        this.cod_art = cod_art;
        this.cod_bod = cod_bod;
        this.des_ubi = des_ubi;
        this.det_lot = det_lot;
        this.fec_ven = fec_ven;
        this.cod_ord = cod_ord;
        this.exi_ant_bod = exi_ant_bod;
        this.exi_act_bod = exi_act_bod;
        this.cos_pro_suc = cos_pro_suc;
        this.cod_ref = cod_ref;
        this.nomart = nomart;
        this.unimed = unimed;
        this.nombod = nombod;
    }

    public String getCod_his() {
        return cod_his;
    }

    public void setCod_his(String cod_his) {
        this.cod_his = cod_his;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public void setFec_tra(String fec_tra) {
        this.fec_tra = fec_tra;
    }

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public String getCod_bod() {
        return cod_bod;
    }

    public void setCod_bod(String cod_bod) {
        this.cod_bod = cod_bod;
    }

    public String getDes_ubi() {
        return des_ubi;
    }

    public void setDes_ubi(String des_ubi) {
        this.des_ubi = des_ubi;
    }

    public String getDet_lot() {
        return det_lot;
    }

    public void setDet_lot(String det_lot) {
        this.det_lot = det_lot;
    }

    public String getFec_ven() {
        return fec_ven;
    }

    public void setFec_ven(String fec_ven) {
        this.fec_ven = fec_ven;
    }

    public String getCod_ord() {
        return cod_ord;
    }

    public void setCod_ord(String cod_ord) {
        this.cod_ord = cod_ord;
    }

    public String getExi_ant_bod() {
        return exi_ant_bod;
    }

    public void setExi_ant_bod(String exi_ant_bod) {
        this.exi_ant_bod = exi_ant_bod;
    }

    public String getExi_act_bod() {
        return exi_act_bod;
    }

    public void setExi_act_bod(String exi_act_bod) {
        this.exi_act_bod = exi_act_bod;
    }

    public String getCos_pro_suc() {
        return cos_pro_suc;
    }

    public void setCos_pro_suc(String cos_pro_suc) {
        this.cos_pro_suc = cos_pro_suc;
    }

    public String getCod_ref() {
        return cod_ref;
    }

    public void setCod_ref(String cod_ref) {
        this.cod_ref = cod_ref;
    }

    public String getNomart() {
        return nomart;
    }

    public void setNomart(String nomart) {
        this.nomart = nomart;
    }

    public String getUnimed() {
        return unimed;
    }

    public void setUnimed(String unimed) {
        this.unimed = unimed;
    }

    public String getNombod() {
        return nombod;
    }

    public void setNombod(String nombod) {
        this.nombod = nombod;
    }

}
