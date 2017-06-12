package p_09_inventario;

import java.io.Serializable;

public class ListaDetalleDocumentosInternos implements Serializable {

    private String cod_art, cod_ref, nom_art, cod_uni_med, nom_uni_med, det_can, can_ext, doc_ext, des_ubi, cos_tot, det_lot, fec_ven;

    public ListaDetalleDocumentosInternos() {
    }

    public ListaDetalleDocumentosInternos(String cod_art, String cod_ref, String nom_art, String cod_uni_med, String nom_uni_med, String det_can, String can_ext, String doc_ext, String des_ubi, String cos_tot, String det_lot, String fec_ven) {
        this.cod_art = cod_art;
        this.cod_ref = cod_ref;
        this.nom_art = nom_art;
        this.cod_uni_med = cod_uni_med;
        this.nom_uni_med = nom_uni_med;
        this.det_can = det_can;
        this.can_ext = can_ext;
        this.doc_ext = doc_ext;
        this.des_ubi = des_ubi;
        this.cos_tot = cos_tot;
        this.det_lot = det_lot;
        this.fec_ven = fec_ven;
    }

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public String getCod_ref() {
        return cod_ref;
    }

    public void setCod_ref(String cod_ref) {
        this.cod_ref = cod_ref;
    }

    public String getNom_art() {
        return nom_art;
    }

    public void setNom_art(String nom_art) {
        this.nom_art = nom_art;
    }

    public String getCod_uni_med() {
        return cod_uni_med;
    }

    public void setCod_uni_med(String cod_uni_med) {
        this.cod_uni_med = cod_uni_med;
    }

    public String getNom_uni_med() {
        return nom_uni_med;
    }

    public void setNom_uni_med(String nom_uni_med) {
        this.nom_uni_med = nom_uni_med;
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

    public String getDoc_ext() {
        return doc_ext;
    }

    public void setDoc_ext(String doc_ext) {
        this.doc_ext = doc_ext;
    }

    public String getDes_ubi() {
        return des_ubi;
    }

    public void setDes_ubi(String des_ubi) {
        this.des_ubi = des_ubi;
    }

    public String getCos_tot() {
        return cos_tot;
    }

    public void setCos_tot(String cos_tot) {
        this.cos_tot = cos_tot;
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

}
