package p_03_autenticar;

import p_02_transactsql.Querys;

import java.io.Serializable;
import java.security.MessageDigest;
import java.sql.ResultSet;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import p_06_utilitarios.Errores;

@Named
@ConversationScoped

public class Login implements Serializable {

    private static final long serialVersionUID = -6541718762358561835L;
    @Inject
    private Conversation conversation;
    private String cod_usu, nom_usu, des_pas, det_nom, cod_dep, tip_usu, cod_pai, img_usu, cod_suc, usu_mod, fec_mod,
            nomdep, nompai, nomsuc, nommod,
            cod_emp, cod_suc_2, cod_bod, cod_eje, des_ubi, nomemp, nomsuc2, nombod, nomeje,
            flg_uso_gru_lin_art, flg_aut_cod_art, flg_uso_lot_art, flg_cos, por_iva, cod_caj;

    public Login() {
    }

    @PostConstruct
    public void initialize() {
        conversation.setTimeout(3600000);
        addMessage("Bienvenido", "Introduzca su Usuario y Contraseña", 1);
    }

    public String validausuario() {
        Querys mquerys = new Querys();
        String mQuery = "";

        try {

            //System.out.println("Codigo md5 en Login: " + md5(des_pas));
            String validar = mquerys.strQuerySQLvariable("select count(cod_usu) as usu "
                    + "from gen_cat_usu where nom_usu='" + nom_usu + "' and des_pas='" + md5(des_pas) + "';");

            if ("1".equals(validar)) {
                ResultSet resVariable;
                mquerys.Conectar();
                mQuery = "select "
                        + "usu.cod_usu, "
                        + "usu.nom_usu, "
                        + "usu.des_pas, "
                        + "ifnull(usu.det_nom,''), "
                        + "ifnull(usu.cod_dep,0), "
                        + "ifnull(usu.tip_usu,''), "
                        + "ifnull(usu.cod_pai,0), "
                        + "'' as ima_usu, "
                        + "ifnull(usu.cod_suc,0), "
                        + "ifnull(usu.usu_mod,0), "
                        + "case ifnull(usu.fec_mod,'') when '' then '' else date_format(usu.fec_mod,'%d/%m/%Y %H:%i') end, "
                        + "ifnull(dep.nom_dep,''), "
                        + "ifnull(pai.nom_pai,''), "
                        + "ifnull(suc.nom_suc,''), "
                        + "ifnull(umo.det_nom,''), "
                        + "ifnull(cnf.cod_emp,0), "
                        + "ifnull(cnf.cod_suc,0), "
                        + "ifnull(cnf.cod_bod,0), "
                        + "ifnull(cnf.des_ubi,''), "
                        + "ifnull(cnf.cod_eje,0), "
                        + "ifnull(emp.nom_emp,''), "
                        + "ifnull(suc2.nom_suc,''), "
                        + "ifnull(bod.nom_bod,''), "
                        + "'' as nomeje, "
                        + "ifnull(emp.flg_uso_gru_lin_art,'false'), "
                        + "ifnull(emp.flg_aut_cod_art,'false'), "
                        + "ifnull(emp.flg_uso_lot_art,'false'), "
                        + "ifnull(emp.flg_cos,'false'), "
                        + "emp.por_iva, "
                        + "ifnull(cnf.cod_caj,0) "
                        + "from gen_cat_usu as usu "
                        + "left join gen_cat_dep as dep on usu.cod_dep = dep.cod_dep "
                        + "left join gen_cat_pai as pai on usu.cod_pai = pai.cod_pai "
                        + "left join gen_cat_suc as suc on usu.cod_suc = suc.cod_suc "
                        + "left join gen_cat_usu as umo on usu.usu_mod = umo.cod_usu "
                        + "left join gen_cnf_usu as cnf on usu.cod_usu = cnf.cod_usu "
                        + "left join gen_cat_emp as emp on cnf.cod_emp = emp.cod_emp "
                        + "left join gen_cat_suc as suc2 on cnf.cod_suc = suc2.cod_suc "
                        + "left join inv_cat_bod as bod on cnf.cod_bod = bod.cod_bod "
                        + "where usu.nom_usu ='" + nom_usu + "' and usu.des_pas = '" + md5(des_pas) + "';";
                resVariable = mquerys.querySQLvariable(mQuery);
                while (resVariable.next()) {
                    cod_usu = resVariable.getString(1);
                    nom_usu = resVariable.getString(2);
                    des_pas = resVariable.getString(3);
                    det_nom = resVariable.getString(4);
                    cod_dep = resVariable.getString(5);
                    tip_usu = resVariable.getString(6);
                    cod_pai = resVariable.getString(7);
                    img_usu = resVariable.getString(8);
                    cod_suc = resVariable.getString(9);
                    usu_mod = resVariable.getString(10);
                    fec_mod = resVariable.getString(11);
                    nomdep = resVariable.getString(12);
                    nompai = resVariable.getString(13);
                    nomsuc = resVariable.getString(14);
                    nommod = resVariable.getString(15);
                    cod_emp = resVariable.getString(16);
                    cod_suc_2 = resVariable.getString(17);
                    cod_bod = resVariable.getString(18);
                    des_ubi = resVariable.getString(19);
                    cod_eje = resVariable.getString(20);
                    nomemp = resVariable.getString(21);
                    nomsuc2 = resVariable.getString(22);
                    nombod = resVariable.getString(23);
                    nomeje = "";
                    flg_uso_gru_lin_art = resVariable.getString(25);
                    flg_aut_cod_art = resVariable.getString(26);
                    flg_uso_lot_art = resVariable.getString(27);
                    flg_cos = resVariable.getString(28);
                    por_iva = resVariable.getString(29);
                    cod_caj = resVariable.getString(30);

                }
                resVariable.close();
                mquerys.Desconectar();

                addMessage("Bienvenido", "Validación Satisfactoria", 1);
                return "antsolution?faces-redirect=true";

            } else {
                addMessage("Fallo en Login", "Nombre de Usuario o Clave Incorrecta", 3);
                RequestContext.getCurrentInstance().update("frmLogin");
            }

        } catch (Exception e) {
            Errores err = new Errores();
            err.auditarError("Login", "validausuario", "Error al Validar Usuario. " + e.getMessage(), "", "0");
            err = null;
            return "index?faces-redirect=true";
        }

        mquerys = null;
        return null;
    }

    private static String md5(String clear) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(clear.getBytes());

        int size = b.length;
        StringBuilder h = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255;
            if (u < 16) {
                h.append("0").append(Integer.toHexString(u));
            } else {
                h.append(Integer.toHexString(u));
            }
        }
        return h.toString();
    }

    public void redireccionarlogin() {
        try {
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("/antsolution/faces/login.xhtml");

        } catch (Exception e) {
            Errores err = new Errores();
            err.auditarError("Login", "validausuario", "Error en redireccionar a login. " + e.getMessage(), "", "0");
            err = null;
        }
    }

    public void redireccionarlogout() {
        try {
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("/antsolution/faces/timeout.xhtml");

        } catch (Exception e) {
            Errores err = new Errores();
            err.auditarError("Login", "validausuario", "Error en redireccionar a logout. " + e.getMessage(), "", "0");
            err = null;
        }
    }

    public void initConversation() {
        if (!FacesContext.getCurrentInstance().isPostback()
                && conversation.isTransient()) {
            conversation.begin();
        }

    }

    public String endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        return "index?faces-redirect=true";

    }

    public void unloadbody() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    public void addMessage(String summary, String detail, int tipo) {
        FacesMessage message = new FacesMessage();
        switch (tipo) {
            case 1:
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
                break;
            case 2:
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail);
                break;
            case 3:
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
                break;
            case 4:
                message = new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail);
                break;
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    //**************** Getter y Setter **************************
    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getNom_usu() {
        return nom_usu;
    }

    public void setNom_usu(String nom_usu) {
        this.nom_usu = nom_usu;
    }

    public String getDes_pas() {
        return des_pas;
    }

    public void setDes_pas(String des_pas) {
        this.des_pas = des_pas;
    }

    public String getDet_nom() {
        return det_nom;
    }

    public void setDet_nom(String det_nom) {
        this.det_nom = det_nom;
    }

    public String getCod_dep() {
        return cod_dep;
    }

    public void setCod_dep(String cod_dep) {
        this.cod_dep = cod_dep;
    }

    public String getTip_usu() {
        return tip_usu;
    }

    public void setTip_usu(String tip_usu) {
        this.tip_usu = tip_usu;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getImg_usu() {
        return img_usu;
    }

    public void setImg_usu(String img_usu) {
        this.img_usu = img_usu;
    }

    public String getCod_suc() {
        return cod_suc;
    }

    public void setCod_suc(String cod_suc) {
        this.cod_suc = cod_suc;
    }

    public String getUsu_mod() {
        return usu_mod;
    }

    public void setUsu_mod(String usu_mod) {
        this.usu_mod = usu_mod;
    }

    public String getFec_mod() {
        return fec_mod;
    }

    public void setFec_mod(String fec_mod) {
        this.fec_mod = fec_mod;
    }

    public String getNomdep() {
        return nomdep;
    }

    public void setNomdep(String nomdep) {
        this.nomdep = nomdep;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

    public String getNomsuc() {
        return nomsuc;
    }

    public void setNomsuc(String nomsuc) {
        this.nomsuc = nomsuc;
    }

    public String getNommod() {
        return nommod;
    }

    public void setNommod(String nommod) {
        this.nommod = nommod;
    }

    public String getCod_emp() {
        return cod_emp;
    }

    public void setCod_emp(String cod_emp) {
        this.cod_emp = cod_emp;
    }

    public String getCod_suc_2() {
        return cod_suc_2;
    }

    public void setCod_suc_2(String cod_suc_2) {
        this.cod_suc_2 = cod_suc_2;
    }

    public String getCod_eje() {
        return cod_eje;
    }

    public void setCod_eje(String cod_eje) {
        this.cod_eje = cod_eje;
    }

    public String getNomemp() {
        return nomemp;
    }

    public void setNomemp(String nomemp) {
        this.nomemp = nomemp;
    }

    public String getNomsuc2() {
        return nomsuc2;
    }

    public void setNomsuc2(String nomsuc2) {
        this.nomsuc2 = nomsuc2;
    }

    public String getNomeje() {
        return nomeje;
    }

    public void setNomeje(String nomeje) {
        this.nomeje = nomeje;
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

    public String getNombod() {
        return nombod;
    }

    public void setNombod(String nombod) {
        this.nombod = nombod;
    }

    public String getFlg_uso_gru_lin_art() {
        return flg_uso_gru_lin_art;
    }

    public void setFlg_uso_gru_lin_art(String flg_uso_gru_lin_art) {
        this.flg_uso_gru_lin_art = flg_uso_gru_lin_art;
    }

    public String getFlg_aut_cod_art() {
        return flg_aut_cod_art;
    }

    public void setFlg_aut_cod_art(String flg_aut_cod_art) {
        this.flg_aut_cod_art = flg_aut_cod_art;
    }

    public String getFlg_uso_lot_art() {
        return flg_uso_lot_art;
    }

    public void setFlg_uso_lot_art(String flg_uso_lot_art) {
        this.flg_uso_lot_art = flg_uso_lot_art;
    }

    public String getFlg_cos() {
        return flg_cos;
    }

    public void setFlg_cos(String flg_cos) {
        this.flg_cos = flg_cos;
    }

    public String getPor_iva() {
        return por_iva;
    }

    public void setPor_iva(String por_iva) {
        this.por_iva = por_iva;
    }

    public String getCod_caj() {
        return cod_caj;
    }

    public void setCod_caj(String cod_caj) {
        this.cod_caj = cod_caj;
    }
    
    

}
