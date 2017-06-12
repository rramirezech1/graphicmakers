package p_07_generales;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import p_02_transactsql.Querys;
import p_03_autenticar.Login;
import p_06_utilitarios.*;
import p_09_inventario.Cat_bodegas;

@Named
@ConversationScoped
public class Man_sucursalesbodegas implements Serializable {

    @Inject
    Login cbean;

    private List<ListaCorta> empresas;
    private List<ListaCorta> estados;
    private List<ListaCorta> personal;
    private Cat_sucursales catsucursales;
    private List<Cat_sucursales> sucursales;
    private Cat_bodegas catbodegas;
    private List<Cat_bodegas> bodegas;

    private String suc_cod_suc, suc_cod_emp, suc_cod_est, suc_nom_suc, suc_cod_per_res, suc_det_dir, suc_det_tel, suc_det_tel_2;
    private String bod_cod_bod, bod_cod_suc, bod_nom_bod;

    private Querys mquerys;

    private Errores err;

    public Man_sucursalesbodegas() {
    }

    public void iniciarventana() {
        mquerys = new Querys();
        err = new Errores();

        suc_cod_suc = "";
        suc_cod_emp = cbean.getCod_emp();
        suc_cod_est = "0";
        suc_nom_suc = "";
        suc_cod_per_res = "0";
        suc_det_dir = "";
        suc_det_tel = "";
        suc_det_tel_2 = "";

        bod_cod_bod = "";
        bod_cod_suc = "0";
        bod_nom_bod = "";

        catbodegas = new Cat_bodegas();
        catsucursales = new Cat_sucursales();
        empresas = new ArrayList<>();
        estados = new ArrayList<>();
        personal = new ArrayList<>();
        sucursales = new ArrayList<>();
        bodegas = new ArrayList<>();

        llenarEmpresas();
        llenarEstados();
        llenarPersonal();

    }

    public void cerrarventana() {
        suc_cod_suc = "";
        suc_cod_emp = "";
        suc_cod_est = "0";
        suc_nom_suc = "";
        suc_cod_per_res = "0";
        suc_det_dir = "";
        suc_det_tel = "";
        suc_det_tel_2 = "";

        bod_cod_bod = "";
        bod_cod_suc = "0";
        bod_nom_bod = "";

        catbodegas = null;
        catsucursales = null;
        empresas = null;
        estados = null;
        personal = null;
        sucursales = null;
        bodegas = null;

        mquerys = null;
        err = null;

    }

    //******** Sucursales ****************
    public void nuevaSucursal() {
        suc_cod_suc = "";
        //suc_cod_emp = cbean.getCod_emp();
        suc_cod_est = "0";
        suc_nom_suc = "";
        suc_cod_per_res = "0";
        suc_det_dir = "";
        suc_det_tel = "";
        suc_det_tel_2 = "";

        bod_cod_bod = "";
        bod_cod_suc = "0";
        bod_nom_bod = "";

        catbodegas = null;
        catsucursales = null;
        bodegas.clear();
    }

    public void iniciarventanaEdit() {
        if ("".equals(suc_cod_emp) || "0".equals(suc_cod_emp)) {
            addMessage("Editar", "Debe seleccionar una Empresa", 3);
            RequestContext.getCurrentInstance().update("frmSucBod");
        } else {
            llenarSucursales();
            RequestContext.getCurrentInstance().update("frmSucBodEdit");
            RequestContext.getCurrentInstance().execute("PF('wSucBodEdit').show()");
        }

    }

    public void cerrarventanaEdit() {
        catsucursales = null;
        sucursales.clear();
        RequestContext.getCurrentInstance().execute("PF('wvSucBodEdit').clearFilters()");
        RequestContext.getCurrentInstance().update("frmSucBod");

    }

    public boolean validarguardarsucursal() {
        boolean mvalidar = true;

        if ("".equals(suc_cod_emp) || "0".equals(suc_cod_emp)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar una Empresa", 3);
        }
        if ("".equals(suc_nom_suc)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre de Sucursal", 3);
        } else if ("".equals(suc_cod_suc)) {
            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_suc) "
                    + "FROM gen_cat_suc "
                    + "where upper(nom_suc)='" + suc_nom_suc.toUpperCase() + "' "
                    + "and cod_emp=" + suc_cod_emp + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Este Nombre de Sucursal ya existe", 3);
            }

        } else if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_suc) "
                + "FROM gen_cat_suc "
                + "where upper(nom_suc)='" + suc_nom_suc.toUpperCase() + "' "
                + "and cod_emp=" + suc_cod_emp + " "
                + "and cod_suc <> " + suc_cod_suc + ";"))) {
            mvalidar = false;
            addMessage("Validar Datos", "Este Nombre de Sucursal ya existe", 3);
        }
        return mvalidar;

    }

    public void guardarsucursal() {
        String mQuery = "";
        if (validarguardarsucursal()) {
            try {
                if ("".equals(suc_cod_suc)) {
                    mQuery = "select ifnull(max(cod_suc),0)+1 as codigo from gen_cat_suc;";
                    suc_cod_suc = mquerys.strQuerySQLvariable(mQuery);
                    mQuery = "insert into gen_cat_suc (cod_suc,cod_emp,cod_est,nom_suc "
                            + ",cod_per_res,det_dir,det_tel,det_tel_2,usu_mod,fec_mod) VALUES ("
                            + suc_cod_suc + "," + suc_cod_emp + "," + suc_cod_est
                            + ",'" + (suc_nom_suc.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + "," + suc_cod_per_res
                            + ",'" + (suc_det_dir.replace("\\", "\\\\")).replace("'", "\\'") + "' "
                            + ",'" + (suc_det_tel.replace("\\", "\\\\")).replace("'", "\\'") + "' "
                            + ",'" + (suc_det_tel_2.replace("\\", "\\\\")).replace("'", "\\'") + "' "
                            + "," + cbean.getCod_usu()
                            + ",now());";
                } else {
                    mQuery = "update gen_cat_suc set "
                            + "cod_est= " + suc_cod_est + ","
                            + "nom_suc= '" + (suc_nom_suc.replace("\\", "\\\\")).replace("'", "\\'") + "',"
                            + "cod_per_res = " + suc_cod_per_res + ","
                            + "det_dir = '" + (suc_det_dir.replace("\\", "\\\\")).replace("'", "\\'") + "',"
                            + "det_tel = '" + (suc_det_tel.replace("\\", "\\\\")).replace("'", "\\'") + "',"
                            + "det_tel_2 = '" + (suc_det_tel_2.replace("\\", "\\\\")).replace("'", "\\'") + "',"
                            + "usu_mod= " + cbean.getCod_usu() + ","
                            + "fec_mod= now() "
                            + "where cod_suc = " + suc_cod_suc + ";";
                }
                mquerys.dmlSQLvariable(mQuery);

                addMessage("Guardar", "La Información ha sido almacenada con éxito.", 1);
                nuevaSucursal();
            } catch (Exception e) {
                addMessage("Guardar", "Error en el almacenamiento de la información.", 3);
                err.auditarError("Man_SucursalesBodegas", "guardarsucursal", "Error en guardar Sucursal. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }

        }
    }

    public boolean validareliminarsucursal() {
        boolean mvalidar = true;

        if ("".equals(suc_cod_emp) || "0".equals(suc_cod_emp) || "".equals(suc_cod_suc)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un registro en Editar Sucursal", 3);
        } else {

            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_suc) "
                    + "FROM fac_cat_caj "
                    + "where cod_suc=" + suc_cod_suc + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Esta sucursal tiene registros relacionados en Cajas y no puede ser eliminada", 3);
            }

            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_suc) "
                    + "FROM fac_tra_cli "
                    + "where cod_suc=" + suc_cod_suc + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Esta sucursal tiene registros relacionados en Transacciones de Cliente y no puede ser eliminada", 3);
            }

            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_suc) "
                    + "FROM gen_cat_usu "
                    + "where cod_suc=" + suc_cod_suc + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Esta sucursal tiene registros relacionados en Usuarios y no puede ser eliminada", 3);
            }

            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_suc) "
                    + "FROM gen_cnf_usu "
                    + "where cod_suc=" + suc_cod_suc + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Esta sucursal tiene registros relacionados en Configuración Personal y no puede ser eliminada", 3);
            }

            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_suc) "
                    + "FROM inv_cat_bod "
                    + "where cod_suc=" + suc_cod_suc + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Esta sucursal tiene registros relacionados en Bodegas y no puede ser eliminada", 3);
            }

            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_suc) "
                    + "FROM inv_tra_his_mov "
                    + "where cod_suc=" + suc_cod_suc + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Esta sucursal tiene registros relacionados en Historial Inventario y no puede ser eliminada", 3);
            }

            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_suc) "
                    + "FROM rhu_cat_per "
                    + "where cod_suc=" + suc_cod_suc + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Esta sucursal tiene registros relacionados en Personal y no puede ser eliminada", 3);
            }
        }
        return mvalidar;

    }

    public void eliminarsucursal() {
        String mQuery = "";
        if (validareliminarsucursal()) {
            try {
                mQuery = "delete from gen_cat_suc where cod_suc=" + suc_cod_suc + ";";
                mquerys.dmlSQLvariable(mQuery);
                addMessage("Eliminar Mantenimiento", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar", "Error al momento de Eliminar la información. " + e.getMessage(), 3);
                err.auditarError("Man_SucursalesBodegas", "eliminarsucursal", "Error en eliminar Sucursal. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }
            nuevaSucursal();

        }

    }

//******* Bodegas ********************
    public boolean validarguardarbodega() {
        boolean mvalidar = true;

        if ("".equals(suc_cod_suc) || "0".equals(suc_cod_suc)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar una Sucursal", 3);
        }
        if ("".equals(bod_nom_bod)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre de Bodega", 3);
        } else if ("".equals(bod_cod_bod)) {
            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_bod) "
                    + "FROM inv_cat_bod "
                    + "where upper(nom_bod)='" + bod_nom_bod.toUpperCase() + "' "
                    + "and cod_suc=" + suc_cod_suc + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Este Nombre de Bodega ya existe", 3);
            }

        } else if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_bod) "
                + "FROM inv_cat_bod "
                + "where upper(nom_bod)='" + bod_nom_bod.toUpperCase() + "' "
                + "and cod_suc=" + suc_cod_suc + " "
                + "and cod_bod <> " + bod_cod_bod + ";"))) {
            mvalidar = false;
            addMessage("Validar Datos", "Este Nombre de Bodega ya existe", 3);
        }
        return mvalidar;

    }

    public void guardarbodega() {
        String mQuery = "";
        if (validarguardarbodega()) {
            try {
                if ("".equals(bod_cod_bod)) {
                    mQuery = "select ifnull(max(cod_bod),0)+1 as codigo from inv_cat_bod;";
                    bod_cod_bod = mquerys.strQuerySQLvariable(mQuery);
                    mQuery = "insert into inv_cat_bod (cod_bod,cod_suc,nom_bod "
                            + ",usu_mod,fec_mod) VALUES ("
                            + bod_cod_bod + "," + suc_cod_suc + ",'" + (bod_nom_bod.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + "," + cbean.getCod_usu()
                            + ",now());";
                } else {
                    mQuery = "update inv_cat_bod set "
                            + "nom_bod= '" + (bod_nom_bod.replace("\\", "\\\\")).replace("'", "\\'") + "',"
                            + "usu_mod= " + cbean.getCod_usu() + ","
                            + "fec_mod= now() "
                            + "where cod_bod = " + bod_cod_bod + ";";
                }
                mquerys.dmlSQLvariable(mQuery);

                addMessage("Guardar", "La Información ha sido almacenada con éxito.", 1);

                bod_cod_bod = "";
                bod_cod_suc = "0";
                bod_nom_bod = "";

                catbodegas = null;

                llenarBodegas();

            } catch (Exception e) {
                addMessage("Guardar", "Error en el almacenamiento de la información.", 3);
                err.auditarError("Man_SucursalesBodegas", "guardarbodega", "Error en guardar Bodega. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }

        }
    }

    public boolean validareliminarbodega() {
        boolean mvalidar = true;

        if ("".equals(bod_cod_bod) || "0".equals(bod_cod_bod)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un registro de la lista", 3);
        } else {

            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_bod) "
                    + "FROM gen_cnf_usu "
                    + "where cod_bod=" + bod_cod_bod + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Esta bodega tiene registros relacionados en Configuración Personal y no puede ser eliminada", 3);
            }
            
            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_bod) "
                    + "FROM inv_cat_art_rel_ubi "
                    + "where cod_bod=" + bod_cod_bod + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Esta bodega tiene registros relacionados en Ubicaciones de Artículo y no puede ser eliminada", 3);
            }

            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_bod) "
                    + "FROM inv_tra_his_mov "
                    + "where cod_bod=" + bod_cod_bod + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Esta bodega tiene registros relacionados en Historial de Transacciones y no puede ser eliminada", 3);
            }
        }

        return mvalidar;

    }

    public void eliminarbodega() {
        String mQuery = "";
        if (validareliminarbodega()) {
            try {
                mQuery = "delete from inv_cat_bod where cod_bod=" + bod_cod_bod + ";";
                mquerys.dmlSQLvariable(mQuery);
                addMessage("Eliminar Mantenimiento", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar", "Error al momento de Eliminar la información. " + e.getMessage(), 3);
                err.auditarError("Man_SucursalesBodegas", "eliminarbodega", "Error en eliminar Bodega. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }
            bod_cod_bod = "";
            bod_cod_suc = "0";
            bod_nom_bod = "";

            catbodegas = null;

            llenarBodegas();

        }

    }
//********* Catálogos ***************

    public void llenarEmpresas() {
        String mQuery = "";
        try {
            empresas.clear();

            mQuery = "select cod_emp, nom_emp "
                    + "from gen_cat_emp order by nom_emp;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                empresas.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_SucursalesBodegas", "llenarEmpresas", "Error en el llenado de Empresas. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarEstados() {
        String mQuery = "";
        try {
            estados.clear();

            mQuery = "select cod_est, nom_est "
                    + "from gen_cat_est "
                    + "where cod_pai = (select cod_pai from gen_cat_emp where cod_emp=" + suc_cod_emp + ") "
                    + "order by nom_est;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                estados.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_SucursalesBodegas", "llenarEstados", "Error en el llenado de Estados. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarPersonal() {
        String mQuery = "";
        try {
            personal.clear();

            mQuery = "select cod_per, concat(det_nom, ' ', det_ape) "
                    + "from rhu_cat_per "
                    + "where cod_emp = " + suc_cod_emp + " "
                    + "order by det_ape;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                personal.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_SucursalesBodegas", "llenarPersonal", "Error en el llenado de Personal. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarSucursales() {
        String mQuery = "";
        try {
            catsucursales = null;
            sucursales.clear();

            mQuery = "select cod_suc, cod_emp, cod_est, nom_suc, cod_per_res, det_dir, det_tel, det_tel_2 "
                    + "from gen_cat_suc "
                    + "where cod_emp = " + suc_cod_emp + " "
                    + "order by nom_suc;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                sucursales.add(new Cat_sucursales(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_SucursalesBodegas", "llenarSucursales", "Error en el llenado de Sucursales. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }

    }

    public void llenarBodegas() {
        String mQuery = "";
        try {
            catbodegas = null;
            bodegas.clear();

            mQuery = "select cod_bod, cod_suc, nom_bod "
                    + "from inv_cat_bod "
                    + "where cod_suc = " + suc_cod_suc + " "
                    + "order by nom_bod;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegas.add(new Cat_bodegas(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_SucursalesBodegas", "llenarBodegas", "Error en el llenado de Bodegas. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }

    }

    //************* funciones varias **********
    public void onRowSelectEdit(SelectEvent event) {
        suc_cod_suc = ((Cat_sucursales) event.getObject()).getCod_suc();
        suc_cod_emp = ((Cat_sucursales) event.getObject()).getCod_emp();
        suc_cod_est = ((Cat_sucursales) event.getObject()).getCod_est();
        suc_nom_suc = ((Cat_sucursales) event.getObject()).getNom_suc();
        suc_cod_per_res = ((Cat_sucursales) event.getObject()).getCod_per_res();
        suc_det_dir = ((Cat_sucursales) event.getObject()).getDet_dir();
        suc_det_tel = ((Cat_sucursales) event.getObject()).getDet_tel();
        suc_det_tel_2 = ((Cat_sucursales) event.getObject()).getDet_tel_2();

        bod_cod_bod = "";
        bod_cod_suc = "0";
        bod_nom_bod = "";

        llenarBodegas();

        RequestContext.getCurrentInstance().execute("PF('wvSucBodEdit').clearFilters()");
        RequestContext.getCurrentInstance().execute("PF('wSucBodEdit').hide()");

    }

    public void onRowSelectBodega(SelectEvent event) {
        bod_cod_bod = ((Cat_bodegas) event.getObject()).getCod_bod();
        bod_cod_suc = ((Cat_bodegas) event.getObject()).getCod_suc();
        bod_nom_bod = ((Cat_bodegas) event.getObject()).getNom_bod();
    }

    public void onRowUnselectBodega() {
        bod_cod_bod = "";
        bod_cod_suc = "0";
        bod_nom_bod = "";

        catbodegas = null;
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

    //************** Getter y Setter *****************
    public List<ListaCorta> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<ListaCorta> empresas) {
        this.empresas = empresas;
    }

    public List<ListaCorta> getEstados() {
        return estados;
    }

    public void setEstados(List<ListaCorta> estados) {
        this.estados = estados;
    }

    public List<ListaCorta> getPersonal() {
        return personal;
    }

    public void setPersonal(List<ListaCorta> personal) {
        this.personal = personal;
    }

    public Cat_sucursales getCatsucursales() {
        return catsucursales;
    }

    public void setCatsucursales(Cat_sucursales catsucursales) {
        this.catsucursales = catsucursales;
    }

    public List<Cat_sucursales> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Cat_sucursales> sucursales) {
        this.sucursales = sucursales;
    }

    public Cat_bodegas getCatbodegas() {
        return catbodegas;
    }

    public void setCatbodegas(Cat_bodegas catbodegas) {
        this.catbodegas = catbodegas;
    }

    public List<Cat_bodegas> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Cat_bodegas> bodegas) {
        this.bodegas = bodegas;
    }

    public String getSuc_cod_suc() {
        return suc_cod_suc;
    }

    public void setSuc_cod_suc(String suc_cod_suc) {
        this.suc_cod_suc = suc_cod_suc;
    }

    public String getSuc_cod_emp() {
        return suc_cod_emp;
    }

    public void setSuc_cod_emp(String suc_cod_emp) {
        this.suc_cod_emp = suc_cod_emp;
    }

    public String getSuc_cod_est() {
        return suc_cod_est;
    }

    public void setSuc_cod_est(String suc_cod_est) {
        this.suc_cod_est = suc_cod_est;
    }

    public String getSuc_nom_suc() {
        return suc_nom_suc;
    }

    public void setSuc_nom_suc(String suc_nom_suc) {
        this.suc_nom_suc = suc_nom_suc;
    }

    public String getSuc_cod_per_res() {
        return suc_cod_per_res;
    }

    public void setSuc_cod_per_res(String suc_cod_per_res) {
        this.suc_cod_per_res = suc_cod_per_res;
    }

    public String getSuc_det_dir() {
        return suc_det_dir;
    }

    public void setSuc_det_dir(String suc_det_dir) {
        this.suc_det_dir = suc_det_dir;
    }

    public String getSuc_det_tel() {
        return suc_det_tel;
    }

    public void setSuc_det_tel(String suc_det_tel) {
        this.suc_det_tel = suc_det_tel;
    }

    public String getSuc_det_tel_2() {
        return suc_det_tel_2;
    }

    public void setSuc_det_tel_2(String suc_det_tel_2) {
        this.suc_det_tel_2 = suc_det_tel_2;
    }

    public String getBod_cod_bod() {
        return bod_cod_bod;
    }

    public void setBod_cod_bod(String bod_cod_bod) {
        this.bod_cod_bod = bod_cod_bod;
    }

    public String getBod_cod_suc() {
        return bod_cod_suc;
    }

    public void setBod_cod_suc(String bod_cod_suc) {
        this.bod_cod_suc = bod_cod_suc;
    }

    public String getBod_nom_bod() {
        return bod_nom_bod;
    }

    public void setBod_nom_bod(String bod_nom_bod) {
        this.bod_nom_bod = bod_nom_bod;
    }

}
