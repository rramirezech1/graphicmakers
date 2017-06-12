package p_06_utilitarios;

import p_02_transactsql.Querys;

import java.io.Serializable;

public class Errores implements Serializable {

    private String cor_err, obj_err, met_err, des_err, que_err, usu_err;

    public Errores() {
    }

    public void auditarError(String mObjeto, String mMetodo, String mDescripcion, String mQuery, String mUsuario) {
        Querys consulta = new Querys();
        try {

            cor_err = consulta.strQuerySQLvariable("select ifnull(max(cor_err),0) + 1 as cod from aud_tra_tbl_err;");
            consulta.dmlSQLvariable(
                    "insert into aud_tra_tbl_err "
                    + "(cor_err,obj_err,met_err,des_err,que_err,usu_err,fec_err) VALUES ("
                    + cor_err + ",'"
                    + mObjeto + "','"
                    + mMetodo + "','"
                    + mDescripcion + "','"
                    + mQuery + "',"
                    + mUsuario + ","
                    + "now()" + ");"
            );

        } catch (Exception ex) {
            System.out.println("Error en auditarError de Errores." + ex.getMessage());
        } finally {
            consulta = null;
        }

    }

    public String getCor_err() {
        return cor_err;
    }

    public void setCor_err(String cor_err) {
        this.cor_err = cor_err;
    }

    public String getObj_err() {
        return obj_err;
    }

    public void setObj_err(String obj_err) {
        this.obj_err = obj_err;
    }

    public String getMet_err() {
        return met_err;
    }

    public void setMet_err(String met_err) {
        this.met_err = met_err;
    }

    public String getDes_err() {
        return des_err;
    }

    public void setDes_err(String des_err) {
        this.des_err = des_err;
    }

    public String getQue_err() {
        return que_err;
    }

    public void setQue_err(String que_err) {
        this.que_err = que_err;
    }

    public String getUsu_err() {
        return usu_err;
    }

    public void setUsu_err(String usu_err) {
        this.usu_err = usu_err;
    }

}
