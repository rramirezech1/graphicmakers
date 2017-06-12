package p_09_inventario;

import java.io.Serializable;

public class Cat_inventario implements Serializable {

    private String cod_his, fec_tra, cod_art, cod_emp, cod_suc, cod_bod, des_ubi, det_lot, fec_ven, cod_cen_cos, cod_ord, flg_ent_sal,
            det_can, can_ext, cos_tot, cos_uni, exi_ant_emp, exi_ant_suc, exi_ant_bod, exi_ant_lot, exi_act_emp, exi_act_suc, exi_act_bod,
            exi_act_lot, cos_pro_emp, cos_pro_suc, cos_pro_cen_cos, det_obs, cod_tip_doc_int, doc_int, cod_tip_doc_ext, doc_ext, cor_doc_int, cod_cli, cod_pro, cod_usu, cod_caj,
            pre_ven_uni_sin_iva, pre_ven_tot, iva_tot, ven_non_suj, ven_exe, cod_tip_pag, det_ant, pag_efe, cod_ban_che, num_che, pag_che,
            cod_ban_tar, num_tar, pag_tar, flg_anu, obs_anu;

    public Cat_inventario() {
    }

    public Cat_inventario(String cod_his, String fec_tra, String cod_art, String cod_emp, String cod_suc, String cod_bod, String des_ubi, String det_lot, String fec_ven, String cod_cen_cos, String cod_ord, String flg_ent_sal, String det_can, String can_ext, String cos_tot, String cos_uni, String exi_ant_emp, String exi_ant_suc, String exi_ant_bod, String exi_ant_lot, String exi_act_emp, String exi_act_suc, String exi_act_bod, String exi_act_lot, String cos_pro_emp, String cos_pro_suc, String cos_pro_cen_cos, String det_obs, String cod_tip_doc_int, String doc_int, String cod_tip_doc_ext, String doc_ext, String cor_doc_int, String cod_cli, String cod_pro, String cod_usu, String cod_caj, String pre_ven_uni_sin_iva, String pre_ven_tot, String iva_tot, String ven_non_suj, String ven_exe, String cod_tip_pag, String det_ant, String pag_efe, String cod_ban_che, String num_che, String pag_che, String cod_ban_tar, String num_tar, String pag_tar, String flg_anu, String obs_anu) {
        this.cod_his = cod_his;
        this.fec_tra = fec_tra;
        this.cod_art = cod_art;
        this.cod_emp = cod_emp;
        this.cod_suc = cod_suc;
        this.cod_bod = cod_bod;
        this.des_ubi = des_ubi;
        this.det_lot = det_lot;
        this.fec_ven = fec_ven;
        this.cod_cen_cos = cod_cen_cos;
        this.cod_ord = cod_ord;
        this.flg_ent_sal = flg_ent_sal;
        this.det_can = det_can;
        this.can_ext = can_ext;
        this.cos_tot = cos_tot;
        this.cos_uni = cos_uni;
        this.exi_ant_emp = exi_ant_emp;
        this.exi_ant_suc = exi_ant_suc;
        this.exi_ant_bod = exi_ant_bod;
        this.exi_ant_lot = exi_ant_lot;
        this.exi_act_emp = exi_act_emp;
        this.exi_act_suc = exi_act_suc;
        this.exi_act_bod = exi_act_bod;
        this.exi_act_lot = exi_act_lot;
        this.cos_pro_emp = cos_pro_emp;
        this.cos_pro_suc = cos_pro_suc;
        this.cos_pro_cen_cos = cos_pro_cen_cos;
        this.det_obs = det_obs;
        this.cod_tip_doc_int = cod_tip_doc_int;
        this.doc_int = doc_int;
        this.cod_tip_doc_ext = cod_tip_doc_ext;
        this.doc_ext = doc_ext;
        this.cor_doc_int = cor_doc_int;
        this.cod_cli = cod_cli;
        this.cod_pro = cod_pro;
        this.cod_usu = cod_usu;
        this.cod_caj = cod_caj;
        this.pre_ven_uni_sin_iva = pre_ven_uni_sin_iva;
        this.pre_ven_tot = pre_ven_tot;
        this.iva_tot = iva_tot;
        this.ven_non_suj = ven_non_suj;
        this.ven_exe = ven_exe;
        this.cod_tip_pag = cod_tip_pag;
        this.det_ant = det_ant;
        this.pag_efe = pag_efe;
        this.cod_ban_che = cod_ban_che;
        this.num_che = num_che;
        this.pag_che = pag_che;
        this.cod_ban_tar = cod_ban_tar;
        this.num_tar = num_tar;
        this.pag_tar = pag_tar;
        this.flg_anu = flg_anu;
        this.obs_anu = obs_anu;
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

    public String getCod_emp() {
        return cod_emp;
    }

    public void setCod_emp(String cod_emp) {
        this.cod_emp = cod_emp;
    }

    public String getCod_suc() {
        return cod_suc;
    }

    public void setCod_suc(String cod_suc) {
        this.cod_suc = cod_suc;
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

    public String getCod_cen_cos() {
        return cod_cen_cos;
    }

    public void setCod_cen_cos(String cod_cen_cos) {
        this.cod_cen_cos = cod_cen_cos;
    }

    public String getCod_ord() {
        return cod_ord;
    }

    public void setCod_ord(String cod_ord) {
        this.cod_ord = cod_ord;
    }

    public String getFlg_ent_sal() {
        return flg_ent_sal;
    }

    public void setFlg_ent_sal(String flg_ent_sal) {
        this.flg_ent_sal = flg_ent_sal;
    }

    public String getDet_can() {
        return det_can;
    }

    public void setDet_can(String det_can) {
        this.det_can = det_can;
    }

    public String getCan_ext() {
        return can_ext;
    }

    public void setCan_ext(String can_ext) {
        this.can_ext = can_ext;
    }

    public String getCos_tot() {
        return cos_tot;
    }

    public void setCos_tot(String cos_tot) {
        this.cos_tot = cos_tot;
    }

    public String getCos_uni() {
        return cos_uni;
    }

    public void setCos_uni(String cos_uni) {
        this.cos_uni = cos_uni;
    }

    public String getExi_ant_emp() {
        return exi_ant_emp;
    }

    public void setExi_ant_emp(String exi_ant_emp) {
        this.exi_ant_emp = exi_ant_emp;
    }

    public String getExi_ant_suc() {
        return exi_ant_suc;
    }

    public void setExi_ant_suc(String exi_ant_suc) {
        this.exi_ant_suc = exi_ant_suc;
    }

    public String getExi_ant_bod() {
        return exi_ant_bod;
    }

    public void setExi_ant_bod(String exi_ant_bod) {
        this.exi_ant_bod = exi_ant_bod;
    }

    public String getExi_ant_lot() {
        return exi_ant_lot;
    }

    public void setExi_ant_lot(String exi_ant_lot) {
        this.exi_ant_lot = exi_ant_lot;
    }

    public String getExi_act_emp() {
        return exi_act_emp;
    }

    public void setExi_act_emp(String exi_act_emp) {
        this.exi_act_emp = exi_act_emp;
    }

    public String getExi_act_suc() {
        return exi_act_suc;
    }

    public void setExi_act_suc(String exi_act_suc) {
        this.exi_act_suc = exi_act_suc;
    }

    public String getExi_act_bod() {
        return exi_act_bod;
    }

    public void setExi_act_bod(String exi_act_bod) {
        this.exi_act_bod = exi_act_bod;
    }

    public String getExi_act_lot() {
        return exi_act_lot;
    }

    public void setExi_act_lot(String exi_act_lot) {
        this.exi_act_lot = exi_act_lot;
    }

    public String getCos_pro_emp() {
        return cos_pro_emp;
    }

    public void setCos_pro_emp(String cos_pro_emp) {
        this.cos_pro_emp = cos_pro_emp;
    }

    public String getCos_pro_suc() {
        return cos_pro_suc;
    }

    public void setCos_pro_suc(String cos_pro_suc) {
        this.cos_pro_suc = cos_pro_suc;
    }

    public String getCos_pro_cen_cos() {
        return cos_pro_cen_cos;
    }

    public void setCos_pro_cen_cos(String cos_pro_cen_cos) {
        this.cos_pro_cen_cos = cos_pro_cen_cos;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getCod_tip_doc_int() {
        return cod_tip_doc_int;
    }

    public void setCod_tip_doc_int(String cod_tip_doc_int) {
        this.cod_tip_doc_int = cod_tip_doc_int;
    }

    public String getDoc_int() {
        return doc_int;
    }

    public void setDoc_int(String doc_int) {
        this.doc_int = doc_int;
    }

    public String getCod_tip_doc_ext() {
        return cod_tip_doc_ext;
    }

    public void setCod_tip_doc_ext(String cod_tip_doc_ext) {
        this.cod_tip_doc_ext = cod_tip_doc_ext;
    }

    public String getDoc_ext() {
        return doc_ext;
    }

    public void setDoc_ext(String doc_ext) {
        this.doc_ext = doc_ext;
    }

    public String getCor_doc_int() {
        return cor_doc_int;
    }

    public void setCor_doc_int(String cor_doc_int) {
        this.cor_doc_int = cor_doc_int;
    }

    public String getCod_cli() {
        return cod_cli;
    }

    public void setCod_cli(String cod_cli) {
        this.cod_cli = cod_cli;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getCod_caj() {
        return cod_caj;
    }

    public void setCod_caj(String cod_caj) {
        this.cod_caj = cod_caj;
    }

    public String getPre_ven_uni_sin_iva() {
        return pre_ven_uni_sin_iva;
    }

    public void setPre_ven_uni_sin_iva(String pre_ven_uni_sin_iva) {
        this.pre_ven_uni_sin_iva = pre_ven_uni_sin_iva;
    }

    public String getPre_ven_tot() {
        return pre_ven_tot;
    }

    public void setPre_ven_tot(String pre_ven_tot) {
        this.pre_ven_tot = pre_ven_tot;
    }

    public String getIva_tot() {
        return iva_tot;
    }

    public void setIva_tot(String iva_tot) {
        this.iva_tot = iva_tot;
    }

    public String getVen_non_suj() {
        return ven_non_suj;
    }

    public void setVen_non_suj(String ven_non_suj) {
        this.ven_non_suj = ven_non_suj;
    }

    public String getVen_exe() {
        return ven_exe;
    }

    public void setVen_exe(String ven_exe) {
        this.ven_exe = ven_exe;
    }

    public String getCod_tip_pag() {
        return cod_tip_pag;
    }

    public void setCod_tip_pag(String cod_tip_pag) {
        this.cod_tip_pag = cod_tip_pag;
    }

    public String getDet_ant() {
        return det_ant;
    }

    public void setDet_ant(String det_ant) {
        this.det_ant = det_ant;
    }

    public String getPag_efe() {
        return pag_efe;
    }

    public void setPag_efe(String pag_efe) {
        this.pag_efe = pag_efe;
    }

    public String getCod_ban_che() {
        return cod_ban_che;
    }

    public void setCod_ban_che(String cod_ban_che) {
        this.cod_ban_che = cod_ban_che;
    }

    public String getNum_che() {
        return num_che;
    }

    public void setNum_che(String num_che) {
        this.num_che = num_che;
    }

    public String getPag_che() {
        return pag_che;
    }

    public void setPag_che(String pag_che) {
        this.pag_che = pag_che;
    }

    public String getCod_ban_tar() {
        return cod_ban_tar;
    }

    public void setCod_ban_tar(String cod_ban_tar) {
        this.cod_ban_tar = cod_ban_tar;
    }

    public String getNum_tar() {
        return num_tar;
    }

    public void setNum_tar(String num_tar) {
        this.num_tar = num_tar;
    }

    public String getPag_tar() {
        return pag_tar;
    }

    public void setPag_tar(String pag_tar) {
        this.pag_tar = pag_tar;
    }

    public String getFlg_anu() {
        return flg_anu;
    }

    public void setFlg_anu(String flg_anu) {
        this.flg_anu = flg_anu;
    }

    public String getObs_anu() {
        return obs_anu;
    }

    public void setObs_anu(String obs_anu) {
        this.obs_anu = obs_anu;
    }

}
