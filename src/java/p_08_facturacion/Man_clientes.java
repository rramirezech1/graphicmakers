package p_08_facturacion;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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
import p_06_utilitarios.Errores;
import p_06_utilitarios.ListaCorta;

@Named
@ConversationScoped

public class Man_clientes implements Serializable {

    @Inject
    Login cbean;

    private List<ListaCorta> items;

    private Cat_clientes catclientes;
    private List<Cat_clientes> clientes;

    private Cat_tra_clientes cattraclientes;
    private List<Cat_tra_clientes> historia;

    private String cadenabuscar;

    private String cod_cli, cod_pai, cod_est, cod_emp,
            flg_nat_emp, ape_cli, nom_cli,
            per_con,
            tel_con, tel_cel, det_mai, doc_ide, doc_nit,
            det_gir, num_reg,
            fec_nac,
            flg_gra_con, flg_exe,
            det_dir, cod_cue_con,
            cod_des;

    private String cor_tra, fec_tra, cod_usu;
    private Double det_can;
    private String cod_ser, cod_pro, det_obs, flg_fac, not_rec, fec_rec;

    private String nombrecliente, flagservicioproducto, hiscantidad, existencia, hisobservaciones, boolenable, hisnotrec, hisfecrec, boolnotrec;

    private Date dfechanac, dfechahistoria, dfecharecordatorio, dfecharecordatorio2;

    private Querys mquerys;

    private Errores err;

    //************ Configuración Inicial **************************
    public Man_clientes() {
    }

    public void iniciarventana() {
        //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        //det_fec_cie = format.format(mfecha);
        mquerys = new Querys();
        err = new Errores();

        cadenabuscar = "";
        catclientes = new Cat_clientes();
        clientes = new ArrayList<>();

        cod_cli = "";
        cod_pai = cbean.getCod_pai();
        cod_est = "0";
        cod_emp = "0";
        flg_nat_emp = "false";
        ape_cli = "";
        nom_cli = "";
        per_con = "";
        tel_con = "";
        tel_cel = "";
        det_mai = "";
        doc_ide = "";
        doc_nit = "";
        det_gir = "";
        num_reg = "";
        fec_nac = "";
        flg_gra_con = "false";
        flg_exe = "false";
        det_dir = "";
        cod_cue_con = "";
        cod_des = "0";
        dfechanac = null;

        cattraclientes = new Cat_tra_clientes();
        historia = new ArrayList<>();
        items = new ArrayList<>();

    }

    public void cerrarventana() {
        cerrarventanaEdit();
        mquerys = null;
        err = null;

        catclientes = null;
        clientes = null;
        cattraclientes = null;
        historia = null;
        items = null;

    }

    //************* Operaciones menú ********************************
    public void nuevoEdit() {
        cod_cli = "";
        cod_pai = cbean.getCod_pai();
        cod_est = "0";
        cod_emp = "0";
        flg_nat_emp = "false";
        ape_cli = "";
        nom_cli = "";
        per_con = "";
        tel_con = "";
        tel_cel = "";
        det_mai = "";
        doc_ide = "";
        doc_nit = "";
        det_gir = "";
        num_reg = "";
        fec_nac = "";
        flg_gra_con = "false";
        flg_exe = "false";
        det_dir = "";
        cod_cue_con = "";
        cod_des = "0";
        dfechanac = null;

        catclientes = null;
        clientes.clear();

    }

    public void cerrarventanaEdit() {
        cod_cli = "";
        cod_pai = "";
        cod_est = "";
        cod_emp = "";
        flg_nat_emp = "";
        ape_cli = "";
        nom_cli = "";
        per_con = "";
        tel_con = "";
        tel_cel = "";
        det_mai = "";
        doc_ide = "";
        doc_nit = "";
        det_gir = "";
        num_reg = "";
        fec_nac = "";
        flg_gra_con = "";
        flg_exe = "";
        det_dir = "";
        cod_cue_con = "";
        cod_des = "0";
        dfechanac = null;

        catclientes = null;

    }

    //************ Clientes *****************************************
    public boolean validardatos() {
        boolean mValidar = true;
        /* if ("".equals(cod_pai) || "0".equals(cod_pai)) {
            mValidar = false;
            addMessage("Validar", "Debe Seleccionar un País", 3);
        }
        if ("".equals(cod_emp) || "0".equals(cod_emp)) {
            mValidar = false;
            addMessage("Validar", "Debe Seleccionar una Empresa", 3);
        }*/
        //Querys consulta = new Querys();

        if ("".equals(cod_cli) || "0".equals(cod_cli)) {
            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_cli) from fac_cat_cli "
                    + "where doc_nit= '" + doc_nit + "';"))) {
                mValidar = false;
                addMessage("Validar", "Este Número de Identificación Tributaria ya Existe", 3);
            }

            if ("false".equals(flg_nat_emp)) {
                if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_cli) from fac_cat_cli "
                        + "where doc_ide= '" + doc_ide + "';"))) {
                    mValidar = false;
                    addMessage("Validar", "Este Documento de Identidad ya Existe", 3);
                }

            } else if ("".equals(num_reg) || "0".equals(num_reg)) {
                mValidar = false;
                addMessage("Validar", "Debe Ingresar un Número de Registro", 3);
            } else if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_cli) from fac_cat_cli "
                    + "where num_reg= '" + num_reg + "';"))) {
                mValidar = false;
                addMessage("Validar", "Este Número de Registro ya Existe", 3);
            }

        } else {
            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_cli) from fac_cat_cli "
                    + "where doc_nit= '" + doc_nit + "' and cod_cli <> " + cod_cli + ";"))) {
                mValidar = false;
                addMessage("Validar", "Este Número de Identificación Tributaria ya Existe", 3);
            }
            if ("false".equals(flg_nat_emp)) {
                if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_cli) from fac_cat_cli "
                        + "where doc_ide= '" + doc_ide + "' and cod_cli <> " + cod_cli + " ;"))) {
                    mValidar = false;
                    addMessage("Validar", "Este Documento de Identidad ya Existe", 3);
                }

            } else if ("".equals(num_reg) || "0".equals(num_reg)) {
                mValidar = false;
                addMessage("Validar", "Debe Ingresar un Número de Registro", 3);
            } else if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_cli) from fac_cat_cli "
                    + "where num_reg= '" + num_reg + "' and cod_cli <> " + cod_cli + ";"))) {
                mValidar = false;
                addMessage("Validar", "Este Número de Registro ya Existe", 3);
            }
        }

        return mValidar;

    }

    public void guardar() {
        String mQuery = "";
        if (validardatos()) {
            try {
                if ("".equals(cod_cli) || "0".equals(cod_cli)) {
                    mQuery = "select ifnull(max(cod_cli),0)+1 as codigo from fac_cat_cli;";
                    cod_cli = mquerys.strQuerySQLvariable(mQuery);
                    mQuery = "insert into fac_cat_cli (cod_cli,cod_pai,cod_est,cod_emp,flg_nat_emp,ape_cli,nom_cli,per_con,"
                            + "tel_con,tel_cel,det_mai,doc_ide,doc_nit,det_gir,num_reg,fec_nac,flg_gra_con,flg_exe,det_dir,"
                            + "cod_cue_con,cod_des,usu_mod,fec_mod) "
                            + "values (" + cod_cli + "," + cod_pai + "," + cod_est + "," + cod_emp + ",'" + flg_nat_emp
                            + "','" + (ape_cli.replace("\\", "\\\\")).replace("'", "\\'")
                            + "','" + (nom_cli.replace("\\", "\\\\")).replace("'", "\\'") + "','" + (per_con.replace("\\", "\\\\")).replace("'", "\\'")
                            + "','" + (tel_con.replace("\\", "\\\\")).replace("'", "\\'") + "','" + (tel_cel.replace("\\", "\\\\")).replace("'", "\\'")
                            + "','" + (det_mai.replace("\\", "\\\\")).replace("'", "\\'") + "','" + (doc_ide.replace("\\", "\\\\")).replace("'", "\\'")
                            + "','" + (doc_nit.replace("\\", "\\\\")).replace("'", "\\'") + "','" + (det_gir.replace("\\", "\\\\")).replace("'", "\\'")
                            + "','" + (num_reg.replace("\\", "\\\\")).replace("'", "\\'")
                            + "',str_to_date('" + fec_nac + "','%d/%m/%Y'),'"
                            + flg_gra_con
                            + "','" + flg_exe + "','"
                            + (det_dir.replace("\\", "\\\\")).replace("'", "\\'")
                            + "','" + cod_cue_con + "'," + cod_des + "," + cbean.getCod_usu() + ",now());";
                } else {
                    mQuery = "update fac_cat_cli SET "
                            + "cod_pai=" + cod_pai
                            + ",cod_est=" + cod_est
                            + ",cod_emp=" + cod_emp
                            + ",flg_nat_emp='" + flg_nat_emp + "'"
                            + ",ape_cli='" + (ape_cli.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + ",nom_cli='" + (nom_cli.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + ",per_con='" + (per_con.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + ",tel_con='" + (tel_con.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + ",tel_cel='" + (tel_cel.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + ",det_mai='" + (det_mai.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + ",doc_ide='" + (doc_ide.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + ",doc_nit='" + (doc_nit.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + ",det_gir='" + (det_gir.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + ",num_reg='" + (num_reg.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + ",fec_nac=str_to_date('" + fec_nac + "','%d/%m/%Y')"
                            + ",flg_gra_con='" + flg_gra_con + "'"
                            + ",flg_exe='" + flg_exe + "'"
                            + ",det_dir='" + (det_dir.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + ",cod_cue_con='" + cod_cue_con + "'"
                            + ",cod_des=" + cod_des + " "
                            + ",usu_mod=" + cbean.getCod_usu() + " "
                            + ",fec_mod = now() "
                            + " WHERE   cod_cli = " + cod_cli + ";";

                }
                mquerys.dmlSQLvariable(mQuery);

                addMessage("Guardar", "Información almacenada con éxito", 1);
            } catch (Exception e) {
                addMessage("Guardar", "Error al guardar la información. " + e.getMessage(), 4);
                err.auditarError("Man_clientes", "guardar", "Error al Guardar Cliente. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }
            nuevoEdit();
        }

    }

    public boolean validareliminar() {
        boolean mValidar = true;

        if ("".equals(cod_cli) || "0".equals(cod_cli) || cod_cli == null) {
            mValidar = false;
            addMessage("Eliminar", "Debe Seleccionar un Registro", 3);
        } else if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_cli) from fac_tra_cli "
                + "where cod_cli= " + cod_cli + ";"))) {
            mValidar = false;
            addMessage("Validar", "Este Cliente no puede ser eliminado porque posee registros en Historial", 3);
        }
        return mValidar;

    }

    public void eliminar() {
        String mQuery = "";
        if (validareliminar()) {
            //Querys consulta = new Querys();

            try {
                mQuery = "delete from fac_cat_cli where cod_cli=" + cod_cli + ";";
                mquerys.dmlSQLvariable(mQuery);
                addMessage("Eliminar", "Información eliminada con éxito", 1);
            } catch (Exception e) {
                addMessage("Eliminar", "Error al eliminar. " + e.getMessage(), 4);
                err.auditarError("Man_clientes", "eliminar", "Error al Eliminar Cliente. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }
            nuevoEdit();
        }

    }

    //************* Historia ****************************************
    public void iniciarventanaHistoria() {
        if ("".equals(cod_cli) || "0".equals(cod_cli)) {
            addMessage("Historia", "Debe Seleccionar un Registro", 3);
            RequestContext.getCurrentInstance().update("frmClientesMain");
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            dfechahistoria = Date.from(Instant.now());

            cor_tra = "";
            fec_tra = format.format(dfechahistoria);
            cod_usu = cbean.getCod_usu();
            det_can = 0.00;
            cod_ser = "0";
            cod_pro = "0";
            det_obs = "";
            flg_fac = "false";
            not_rec = "";
            fec_rec = "";
            dfecharecordatorio = null;
            dfecharecordatorio2 = null;
            boolenable = "true";
            boolnotrec = "false";

            flagservicioproducto = "false";
            hiscantidad = "0";
            existencia = "";
            hisobservaciones = "";
            hisnotrec = "";
            hisfecrec = "";

            llenarHistoria();
            llenarProductoServicio();

            RequestContext.getCurrentInstance().update("frmClientesTransac");
            RequestContext.getCurrentInstance().execute("PF('wClientesTransac').show()");
        }
    }

    public void limpiarventanaHistoria() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dfechahistoria = Date.from(Instant.now());

        cor_tra = "";
        fec_tra = format.format(dfechahistoria);
        cod_usu = cbean.getCod_usu();
        det_can = 0.00;
        cod_ser = "0";
        cod_pro = "0";
        det_obs = "";
        flg_fac = "false";
        not_rec = "";
        fec_rec = "";
        dfecharecordatorio = null;
        dfecharecordatorio2 = null;
        boolenable = "true";
        boolnotrec = "false";

        flagservicioproducto = "false";
        hiscantidad = "0";
        existencia = "";
        hisobservaciones = "";

        llenarHistoria();
        llenarProductoServicio();
    }

    public void cerrarventanaHistoria() {
        cod_cli = "";
        cod_pai = cbean.getCod_pai();
        cod_est = "0";
        cod_emp = "0";
        flg_nat_emp = "false";
        ape_cli = "";
        nom_cli = "";
        per_con = "";
        tel_con = "";
        tel_cel = "";
        det_mai = "";
        doc_ide = "";
        doc_nit = "";
        det_gir = "";
        num_reg = "";
        fec_nac = "";
        flg_gra_con = "false";
        flg_exe = "false";
        det_dir = "";
        cod_cue_con = "";
        cod_des = "0";
        dfechanac = null;
        boolnotrec = "false";

        cor_tra = "";
        fec_tra = "";
        cod_usu = "";
        det_can = 0.00;
        cod_ser = "0";
        cod_pro = "0";
        det_obs = "";
        flg_fac = "false";
        not_rec = "";
        fec_rec = "";
        dfecharecordatorio = null;
        dfecharecordatorio2 = null;
        boolenable = "true";

        flagservicioproducto = "false";
        hiscantidad = "0";
        existencia = "";
        hisobservaciones = "";

        cattraclientes = null;
        historia.clear();
        items.clear();

        catclientes = null;
    }

    public void buscarClientes() {
        String mQuery = "";
        try {
            nuevoEdit();

            if ((cadenabuscar.replace("\\", "\\\\")).replace("'", "\\'").equals("")) {
                mQuery = "select "
                        + "cod_cli, "
                        + "cod_pai, "
                        + "cod_est, "
                        + "cod_emp, "
                        + "flg_nat_emp, "
                        + "ifnull(ape_cli,'') as ape_cli, "
                        + "nom_cli, "
                        + "ifnull(per_con,'') as per_con, "
                        + "tel_con, "
                        + "tel_cel, "
                        + "det_mai, "
                        + "doc_ide, "
                        + "doc_nit, "
                        + "det_gir, "
                        + "num_reg, "
                        + "case ifnull(fec_nac,'') when '' then '' else date_format(fec_nac,'%d/%m/%Y') end as fecnac, "
                        + "flg_gra_con, "
                        + "flg_exe, "
                        + "det_dir, "
                        + "cod_cue_con, "
                        + "cod_des "
                        + "from fac_cat_cli "
                        + "where cod_emp = " + cbean.getCod_emp() + " "
                        + "order by ape_cli,nom_cli,flg_nat_emp,doc_ide,doc_nit"
                        + ";";
            } else {
                mQuery = "select "
                        + "cod_cli, "
                        + "cod_pai, "
                        + "cod_est, "
                        + "cod_emp, "
                        + "flg_nat_emp, "
                        + "ape_cli, "
                        + "nom_cli, "
                        + "per_con, "
                        + "tel_con, "
                        + "tel_cel, "
                        + "det_mai, "
                        + "doc_ide, "
                        + "doc_nit, "
                        + "det_gir, "
                        + "num_reg, "
                        + "case date_format(fec_nac,'%d/%m/%Y') when '00/00/0000' then '' else date_format(fec_nac,'%d/%m/%Y') end as fecnac, "
                        + "flg_gra_con, "
                        + "flg_exe, "
                        + "det_dir, "
                        + "cod_cue_con, "
                        + "cod_des "
                        + "from fac_cat_cli "
                        + "where cod_emp = " + cbean.getCod_emp() + " "
                        + "and (ape_cli like '%" + (cadenabuscar.replace("\\", "\\\\")).replace("'", "\\'") + "%' "
                        + "or nom_cli like '%" + (cadenabuscar.replace("\\", "\\\\")).replace("'", "\\'") + "%' "
                        + "or doc_ide like '%" + (cadenabuscar.replace("\\", "\\\\")).replace("'", "\\'") + "%' "
                        + "or doc_nit like '%" + (cadenabuscar.replace("\\", "\\\\")).replace("'", "\\'") + "%' )"
                        + "order by ape_cli,nom_cli,flg_nat_emp,doc_ide,doc_nit"
                        + ";";
            }

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                clientes.add(new Cat_clientes(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10),
                        resVariable.getString(11),
                        resVariable.getString(12),
                        resVariable.getString(13),
                        resVariable.getString(14),
                        resVariable.getString(15),
                        resVariable.getString(16),
                        resVariable.getString(17),
                        resVariable.getString(18),
                        resVariable.getString(19),
                        resVariable.getString(20),
                        resVariable.getString(21)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_clientes", "buscarClientes", "Error en el llenado de Clientes. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());

        }
    }

    public void llenarHistoria() {
        String mQuery = "";
        try {
            cattraclientes = null;
            historia.clear();

            mQuery = "select "
                    + "tra.cor_tra, "
                    + "tra.cod_cli, "
                    + "date_format(tra.fec_tra,'%d/%m/%Y %H:%i') as fec_tra, "
                    + "tra.cod_usu, "
                    + "tra.det_can, "
                    + "case ifnull(tra.cod_art,'0') when '0' then 0 else 1 end as cod_ser, "
                    + "case ifnull(tra.cod_art,'0') when '0' then tra.cod_ser else tra.cod_art end as cod_art, "
                    + "tra.det_obs, "
                    + "case tra.flg_fac when 'false' then 'No' else 'Sí' end as flg_fac, "
                    + "case ifnull(tra.cod_art,'0') when '0' then concat(ifnull(ser.cod_ref,''),'--',ifnull(ser.nom_ser,'')) else concat(ifnull(art.cod_ref,''),'--',ifnull(cod_alt,''),'--',ifnull(art.nom_art,'')) end as nombreitem, "
                    + "not_rec,"
                    + "case date_format(fec_rec,'%d/%m/%Y') when '00/00/0000' then '' else date_format(fec_rec,'%d/%m/%Y') end as fec_rec, "
                    + "ifnull(cod_suc,0) "
                    + "from fac_tra_cli as tra "
                    + "left join fac_cat_ser as ser on tra.cod_ser = ser.cod_ser "
                    + "left join inv_cat_art as art on tra.cod_art = art.cod_art "
                    + "where tra.cod_cli =" + cod_cli + " "
                    + "order by tra.fec_tra desc "
                    + ";";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                historia.add(new Cat_tra_clientes(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getDouble(5),
                        resVariable.getString(6),
                        resVariable.getString(7),
                        resVariable.getString(8),
                        resVariable.getString(9),
                        resVariable.getString(10),
                        resVariable.getString(11),
                        resVariable.getString(12),
                        resVariable.getString(13)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_clientes", "llenarHistoria", "Error en el llenado de Histórico Cliente. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarProductoServicio() {
        String mQuery = "";
        try {
            cod_ser = "0";
            cod_pro = "0";
            existencia = "";
            items.clear();

            if ("true".equals(flagservicioproducto)) {

                mQuery = "select "
                        + "cod_art, "
                        + "concat(ifnull(cod_ref,''),'--',ifnull(cod_alt,''),'--',ifnull(nom_art,'')) as nomart "
                        + "from inv_cat_art "
                        + "where cod_emp = " + cbean.getCod_emp() + " "
                        + "and cod_art <> 0 "
                        + "order by cod_art "
                        + ";";
            } else {

                mQuery = "select "
                        + "cod_ser, "
                        + "concat(ifnull(cod_ref,''),'--',ifnull(nom_ser,'')) as nomser "
                        + "from fac_cat_ser "
                        + "where cod_emp = " + cbean.getCod_emp() + " "
                        + "order by cod_ser "
                        + ";";
            }

            ResultSet resVariable;

            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                items.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_clientes", "llenarProductoServicio", "Error en el llenado de Catálogo Producto Servicio. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public boolean validarAgregar() {
        boolean mValidar = true;
        if (det_can == 0.0) {
            addMessage("Validar", "La Cantidad debe ser superior a Cero", 3);
            return false;
        }

        if ("false".equals(flagservicioproducto) && "0".equals(cod_pro)) {
            addMessage("Validar", "Debe Seleccionar un Servicio", 3);
            return false;
        }

        if ("true".equals(flagservicioproducto) && "0".equals(cod_pro)) {
            addMessage("Validar", "Debe Seleccionar un Producto", 3);
            return false;
        }

        if ("true".equals(flagservicioproducto) && !"0".equals(cod_pro)) {
            if (mquerys.doubleQuerySQLvariable("select ifnull((select his.exi_act_bod from inv_tra_his as his where his.cod_suc = " + cbean.getCod_suc_2() + " and his.cod_bod = " + cbean.getCod_bod() + " and his.cod_art = " + cod_pro + " and his.fec_tra <= STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "', '%d/%m/%Y') order by his.fec_tra desc, his.ord_dia desc limit 1),0) as existencia;") - det_can < 0.0) {
                addMessage("Validar", "Esta Cantidad sobrepasa la Existencia de este Item", 3);
                return false;
            }

        }

        if ("".equals(fec_rec) || fec_rec == null) {
            if (!"".equals(not_rec)) {
                addMessage("Validar", "Debe ingresar una Fecha de Recordatorio para la Nota Ingresada", 3);
                return false;
            }
        } else if ("".equals(not_rec)) {
            addMessage("Validar", "Debe ingresar una Nota de Recordatorio para la Fecha Ingresada", 3);
            return false;
        }

        return mValidar;
    }

    public void agregarItem() {
        String mQuery = "";
        if (validarAgregar()) {
            try {

                if ("false".equals(flagservicioproducto)) {
                    cod_ser = cod_pro;
                    cod_pro = "0";
                } else {
                    cod_ser = "0";
                    /*
                    mQuery = "update fac_cat_art set "
                            + "ult_exi = can_exi, "
                            + "can_exi = (can_exi - " + det_can + "), "
                            + "fec_mod = now(), "
                            + "usu_mod = " + cbean.getCod_usu() + ", "
                            + "tip_mod = 'AGREGAR ITEM A HISTORIAL' "
                            + "where cod_art = " + cod_pro + " ;";
                    mquerys.dmlSQLvariable(mQuery);
                     */
                }
                String miFecha;
                if (!"".equals(fec_rec) && fec_rec != null) {

                    miFecha = "str_to_date('" + fec_rec + "','%d/%m/%Y')";
                } else {
                    miFecha = "null";
                }

                mQuery = "select ifnull(max(cor_tra),0)+1 as codigo from fac_tra_cli;";
                cor_tra = mquerys.strQuerySQLvariable(mQuery);
                mQuery = "insert into fac_tra_cli (cor_tra,cod_cli,fec_tra,cod_usu,det_can,cod_ser,cod_art,det_obs,flg_fac,not_rec,fec_rec,cod_suc) "
                        + "values (" + cor_tra + "," + cod_cli + ",str_to_date('" + fec_tra + "','%d/%m/%Y %H:%i'),"
                        + cod_usu + "," + det_can + "," + cod_ser + "," + cod_pro
                        + ",'" + (det_obs.replace("\\", "\\\\")).replace("'", "\\'")
                        + "','false','" + not_rec + "'," + miFecha + "," + cbean.getCod_suc_2() + ");";
                mquerys.dmlSQLvariable(mQuery);

                addMessage("Agregar", "Item Agregado con éxito", 1);
            } catch (Exception e) {
                addMessage("Agregar", "Error al Agregar el Item. " + e.getMessage(), 4);
                err.auditarError("Man_clientes", "agregarItem", "Error al Guardar Item. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }
            limpiarventanaHistoria();
        }
    }

    public boolean validarRemover() {
        boolean mValidar = true;
        if (cor_tra == null || "".equals(cor_tra) || "0".equals(cor_tra)) {
            mValidar = false;
            addMessage("Validar", "Debe Seleccionar un Registro a Remover", 3);
        } else if ("true".equals(flg_fac)) {
            mValidar = false;
            addMessage("Validar", "Este Item ya ha sido Facturado y no puede ser removido", 3);
        }

        return mValidar;
    }

    public void eliminarItem() {
        String mQuery = "";
        if (validarRemover()) {
            try {
                //Querys consulta = new Querys();

                /*String miProducto = mquerys.strQuerySQLvariable("select ifnull(cod_art,0) as cod_pro from fac_tra_cli where cor_tra = " + cor_tra + ";");
                
                if (!"0".equals(miProducto)) {
                    mQuery = "update inv_cat_art set "
                            + "ult_exi = can_exi, "
                            + "can_exi = (can_exi + " + hiscantidad + "), "
                            + "fec_mod = now(), "
                            + "usu_mod = " + cbean.getCod_usu() + ", "
                            + "tip_mod = 'ELIMINAR ITEM DE HISTORIAL' "
                            + "where cod_art = " + miProducto + " ;";
                    mquerys.dmlSQLvariable(mQuery);
                }*/
                mQuery = "delete from fac_tra_cli where cor_tra = " + cor_tra + ";";
                mquerys.dmlSQLvariable(mQuery);

                addMessage("Remover", "Item Removido con éxito", 1);
            } catch (Exception e) {
                addMessage("Remover", "Error al Remover el Item. " + e.getMessage(), 4);
                err.auditarError("Man_clientes", "eliminarItem", "Error al Remover Item. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }
            limpiarventanaHistoria();
        }
    }

    //******************* Controles ********************************************
    public void onRowSelectBuscar(SelectEvent event) {
        cod_cli = ((Cat_clientes) event.getObject()).getCod_cli();
        cod_pai = ((Cat_clientes) event.getObject()).getCod_pai();
        cod_est = ((Cat_clientes) event.getObject()).getCod_est();
        cod_emp = ((Cat_clientes) event.getObject()).getCod_emp();
        flg_nat_emp = ((Cat_clientes) event.getObject()).getFlg_nat_emp();
        ape_cli = ((Cat_clientes) event.getObject()).getApe_cli();
        nom_cli = ((Cat_clientes) event.getObject()).getNom_cli();
        per_con = ((Cat_clientes) event.getObject()).getPer_con();
        tel_con = ((Cat_clientes) event.getObject()).getTel_con();
        tel_cel = ((Cat_clientes) event.getObject()).getTel_cel();
        det_mai = ((Cat_clientes) event.getObject()).getDet_mai();
        doc_ide = ((Cat_clientes) event.getObject()).getDoc_ide();
        doc_nit = ((Cat_clientes) event.getObject()).getDoc_nit();
        det_gir = ((Cat_clientes) event.getObject()).getDet_gir();
        num_reg = ((Cat_clientes) event.getObject()).getNum_reg();
        fec_nac = ((Cat_clientes) event.getObject()).getFec_nac();
        flg_gra_con = ((Cat_clientes) event.getObject()).getFlg_gra_con();
        flg_exe = ((Cat_clientes) event.getObject()).getFlg_exe();
        det_dir = ((Cat_clientes) event.getObject()).getDet_dir();
        cod_cue_con = ((Cat_clientes) event.getObject()).getCod_cue_con();
        cod_des = ((Cat_clientes) event.getObject()).getCod_des();

        if ("".equals(fec_nac)) {
            dfechanac = null;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                dfechanac = format.parse(fec_nac);
            } catch (Exception ex) {
                dfechanac = null;
            }
        }

        nombrecliente = nom_cli + " " + ape_cli;
    }

    public void onRowSelectHistoria(SelectEvent event) {
        cor_tra = ((Cat_tra_clientes) event.getObject()).getCor_tra();
        hiscantidad = String.valueOf(((Cat_tra_clientes) event.getObject()).getDet_can());
        hisobservaciones = ((Cat_tra_clientes) event.getObject()).getDet_obs();
        hisnotrec = ((Cat_tra_clientes) event.getObject()).getNot_rec();
        hisfecrec = ((Cat_tra_clientes) event.getObject()).getFec_rec();
        flg_fac = ((Cat_tra_clientes) event.getObject()).getFlg_fac();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if (!"".equals(hisfecrec) && hisfecrec != null) {
            try {
                dfecharecordatorio2 = format.parse(hisfecrec);
            } catch (Exception ex) {
                dfecharecordatorio2 = null;
            }
        }

        if (!"".equals(hisobservaciones) && hisobservaciones != null) {
            RequestContext.getCurrentInstance().update("frmObservacionesCli");
            RequestContext.getCurrentInstance().execute("PF('wObservacionesCli').show()");
        } else if (!"".equals(hisnotrec) && hisnotrec != null) {
            RequestContext.getCurrentInstance().update("frmObservacionesCli");
            RequestContext.getCurrentInstance().execute("PF('wObservacionesCli').show()");
        } else if (!"".equals(hisfecrec) && hisfecrec != null) {
            RequestContext.getCurrentInstance().update("frmObservacionesCli");
            RequestContext.getCurrentInstance().execute("PF('wObservacionesCli').show()");
        }

    }

    public void dateSelectedNacimiento(SelectEvent f) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = (Date) f.getObject();
        fec_nac = format.format(date);
    }

    public void dateSelectedHistoria(SelectEvent f) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = (Date) f.getObject();
        fec_tra = format.format(date);
    }

    public void dateSelectedRecordatorio(SelectEvent f) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = (Date) f.getObject();
        fec_rec = format.format(date);
    }

    public void onselectProducto() {
        if ("true".equals(flagservicioproducto)) {
            existencia = mquerys.strQuerySQLvariable("select ifnull((select his.exi_act_bod from inv_tra_his as his where his.cod_suc = " + cbean.getCod_suc_2() + " and his.cod_bod = " + cbean.getCod_bod() + " and his.cod_art = " + cod_pro + " and his.fec_tra <= STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "', '%d/%m/%Y') order by his.fec_tra desc, his.ord_dia desc limit 1),0) as existencia;");
        } else {
            existencia = "";
        }
    }

    public void onclickNotaRecordatorio() {
        if ("true".equals(boolenable)) {
            boolenable = "false";
        } else {
            boolenable = "true";
        }
        not_rec = "";
        fec_rec = "";
        dfecharecordatorio = null;
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

    //****************** Getters y Setters *************************************
    public List<ListaCorta> getItems() {
        return items;
    }

    public void setItems(List<ListaCorta> items) {
        this.items = items;
    }

    public Cat_clientes getCatclientes() {
        return catclientes;
    }

    public void setCatclientes(Cat_clientes catclientes) {
        this.catclientes = catclientes;
    }

    public List<Cat_clientes> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cat_clientes> clientes) {
        this.clientes = clientes;
    }

    public Cat_tra_clientes getCattraclientes() {
        return cattraclientes;
    }

    public void setCattraclientes(Cat_tra_clientes cattraclientes) {
        this.cattraclientes = cattraclientes;
    }

    public List<Cat_tra_clientes> getHistoria() {
        return historia;
    }

    public void setHistoria(List<Cat_tra_clientes> historia) {
        this.historia = historia;
    }

    public String getCadenabuscar() {
        return cadenabuscar;
    }

    public void setCadenabuscar(String cadenabuscar) {
        this.cadenabuscar = cadenabuscar;
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

    public String getCor_tra() {
        return cor_tra;
    }

    public void setCor_tra(String cor_tra) {
        this.cor_tra = cor_tra;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public void setFec_tra(String fec_tra) {
        this.fec_tra = fec_tra;
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public Double getDet_can() {
        return det_can;
    }

    public void setDet_can(Double det_can) {
        this.det_can = det_can;
    }

    public String getCod_ser() {
        return cod_ser;
    }

    public void setCod_ser(String cod_ser) {
        this.cod_ser = cod_ser;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
    }

    public String getFlg_fac() {
        return flg_fac;
    }

    public void setFlg_fac(String flg_fac) {
        this.flg_fac = flg_fac;
    }

    public Date getDfechanac() {
        return dfechanac;
    }

    public void setDfechanac(Date dfechanac) {
        this.dfechanac = dfechanac;
    }

    public Date getDfechahistoria() {
        return dfechahistoria;
    }

    public void setDfechahistoria(Date dfechahistoria) {
        this.dfechahistoria = dfechahistoria;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getFlagservicioproducto() {
        return flagservicioproducto;
    }

    public void setFlagservicioproducto(String flagservicioproducto) {
        this.flagservicioproducto = flagservicioproducto;
    }

    public String getExistencia() {
        return existencia;
    }

    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    public String getHisobservaciones() {
        return hisobservaciones;
    }

    public void setHisobservaciones(String hisobservaciones) {
        this.hisobservaciones = hisobservaciones;
    }

    public String getNot_rec() {
        return not_rec;
    }

    public void setNot_rec(String not_rec) {
        this.not_rec = not_rec;
    }

    public String getFec_rec() {
        return fec_rec;
    }

    public void setFec_rec(String fec_rec) {
        this.fec_rec = fec_rec;
    }

    public String getBoolenable() {
        return boolenable;
    }

    public void setBoolenable(String boolenable) {
        this.boolenable = boolenable;
    }

    public Date getDfecharecordatorio() {
        return dfecharecordatorio;
    }

    public void setDfecharecordatorio(Date dfecharecordatorio) {
        this.dfecharecordatorio = dfecharecordatorio;
    }

    public String getHisnotrec() {
        return hisnotrec;
    }

    public void setHisnotrec(String hisnotrec) {
        this.hisnotrec = hisnotrec;
    }

    public String getHisfecrec() {
        return hisfecrec;
    }

    public void setHisfecrec(String hisfecrec) {
        this.hisfecrec = hisfecrec;
    }

    public String getBoolnotrec() {
        return boolnotrec;
    }

    public void setBoolnotrec(String boolnotrec) {
        this.boolnotrec = boolnotrec;
    }

    public Date getDfecharecordatorio2() {
        return dfecharecordatorio2;
    }

    public void setDfecharecordatorio2(Date dfecharecordatorio2) {
        this.dfecharecordatorio2 = dfecharecordatorio2;
    }

}
