package p_09_inventario;

import java.io.Serializable;

public class ListaDocumentosInternos implements Serializable {

    private String flg_ent_sal, nomentsal, cod_tip_doc, nomtipdoc, doc_int, fec_tra, flg_cer_doc;

    public ListaDocumentosInternos() {
    }

    public ListaDocumentosInternos(String flg_ent_sal, String nomentsal, String cod_tip_doc, String nomtipdoc, String doc_int, String fec_tra, String flg_cer_doc) {
        this.flg_ent_sal = flg_ent_sal;
        this.nomentsal = nomentsal;
        this.cod_tip_doc = cod_tip_doc;
        this.nomtipdoc = nomtipdoc;
        this.doc_int = doc_int;
        this.fec_tra = fec_tra;
        this.flg_cer_doc = flg_cer_doc;
    }

    public String getFlg_ent_sal() {
        return flg_ent_sal;
    }

    public void setFlg_ent_sal(String flg_ent_sal) {
        this.flg_ent_sal = flg_ent_sal;
    }

    public String getNomentsal() {
        return nomentsal;
    }

    public void setNomentsal(String nomentsal) {
        this.nomentsal = nomentsal;
    }

    public String getCod_tip_doc() {
        return cod_tip_doc;
    }

    public void setCod_tip_doc(String cod_tip_doc) {
        this.cod_tip_doc = cod_tip_doc;
    }

    public String getNomtipdoc() {
        return nomtipdoc;
    }

    public void setNomtipdoc(String nomtipdoc) {
        this.nomtipdoc = nomtipdoc;
    }

    public String getDoc_int() {
        return doc_int;
    }

    public void setDoc_int(String doc_int) {
        this.doc_int = doc_int;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public void setFec_tra(String fec_tra) {
        this.fec_tra = fec_tra;
    }

    public String getFlg_cer_doc() {
        return flg_cer_doc;
    }

    public void setFlg_cer_doc(String flg_cer_doc) {
        this.flg_cer_doc = flg_cer_doc;
    }

}
