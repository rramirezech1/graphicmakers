package p_09_inventario;

import java.io.Serializable;

public class ListaCortaTransaccionExistencias implements Serializable {

    private String cod_his, flg_ent_sal;
    private Double det_can, can_ext, cos_uni;

    public ListaCortaTransaccionExistencias() {
    }

    public ListaCortaTransaccionExistencias(String cod_his, String flg_ent_sal, Double det_can, Double can_ext, Double cos_uni) {
        this.cod_his = cod_his;
        this.flg_ent_sal = flg_ent_sal;
        this.det_can = det_can;
        this.can_ext = can_ext;
        this.cos_uni = cos_uni;
    }

    public String getCod_his() {
        return cod_his;
    }

    public void setCod_his(String cod_his) {
        this.cod_his = cod_his;
    }

    public String getFlg_ent_sal() {
        return flg_ent_sal;
    }

    public void setFlg_ent_sal(String flg_ent_sal) {
        this.flg_ent_sal = flg_ent_sal;
    }

    public Double getDet_can() {
        return det_can;
    }

    public void setDet_can(Double det_can) {
        this.det_can = det_can;
    }

    public Double getCan_ext() {
        return can_ext;
    }

    public void setCan_ext(Double can_ext) {
        this.can_ext = can_ext;
    }

    public Double getCos_uni() {
        return cos_uni;
    }

    public void setCos_uni(Double cos_uni) {
        this.cos_uni = cos_uni;
    }

}
