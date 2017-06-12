package p_08_facturacion;

import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import p_02_transactsql.Querys;
import p_03_autenticar.Login;
import p_06_utilitarios.Errores;
import p_06_utilitarios.ListaCorta;
import p_09_inventario.ListaCortaTransaccionExistencias;

@Named
@ConversationScoped
public class Man_Facturacion implements Serializable {

    @Inject
    Login cbean;

    //*************** Facturación *****************************
    private List<ListaCorta> items;
    private List<ListaCorta> unidadmedida;
    private List<ListaCorta> lotes;

    private Cat_prefactura catprefactura;
    private List<Cat_prefactura> prefactura;

    private String pf_cor_pre_fac, pf_cod_ser, pf_cod_art, pf_nomitem;
    private Double pf_det_can, pf_det_can_con;
    private String pf_cod_uni_med, pf_nomunimed;
    private Double pf_pre_sin_iva, pf_iva, pf_pre_con_iva, pf_por_des, pf_totalexe, pf_totalgrav;
    private String pf_cod_cli, pf_cod_tra_cli, pf_lote;

    private Double existencia, det_sumatotal;
    private String det_flg_pro_ser, unimedori, codunimedori, codrefbus;

    //****************** Control de Pago **************************************
    private List<ListaCorta> clientes;
    private List<ListaCorta> cajas;
    private List<ListaCorta> tiposdoc;
    private List<ListaCorta> tipospag;
    private List<ListaCorta> tipostar;
    private List<ListaCorta> bancos;

    private List<ListaCortaTransaccionExistencias> hisexi;

    private String pag_cor_mae, pag_cor_det, pag_cod_cli, pag_cod_caj, pag_cod_tip_doc, pag_num_doc, pag_fec_pag,
            pag_con_pag, pag_tip_pag, pag_tar_tip, pag_tar_num, pag_che_ban, pag_che_num, pag_cod_his;
    private Double pag_por_des, pag_pag_tot, pag_efe_tot, pag_efe_pag_con, pag_efe_cam, pag_tar_tot, pag_che_tot;
    private String boo_efe, boo_tar, boo_che, boo_mix;

    private Date mfechapag;

    //****************** Generales ********************************************
    private Querys mquerys;

    private Errores err;

    public Man_Facturacion() {
    }
    //************ Configuración Inicial **************************

    public void iniciarventana() {

        mquerys = new Querys();
        err = new Errores();

        pf_cor_pre_fac = "";
        pf_cod_ser = "0";
        pf_cod_art = "0";
        pf_nomitem = "";
        pf_det_can = 0.0;
        pf_det_can_con = 0.0;
        pf_cod_uni_med = "0";
        pf_nomunimed = "";
        pf_pre_sin_iva = 0.0;
        pf_iva = 0.0;
        pf_pre_con_iva = 0.0;
        pf_por_des = 0.0;
        pf_totalexe = 0.0;
        pf_totalgrav = 0.0;
        pf_cod_cli = "0";
        pf_cod_tra_cli = "0";
        pf_lote = "";

        existencia = 0.0;
        det_sumatotal = 0.0;
        det_flg_pro_ser = "false";
        unimedori = "";
        codunimedori = "0";
        codrefbus = "";

        items = new ArrayList<>();
        unidadmedida = new ArrayList<>();
        lotes = new ArrayList<>();
        clientes = new ArrayList<>();

        catprefactura = new Cat_prefactura();
        prefactura = new ArrayList<>();

        cajas = new ArrayList<>();
        tiposdoc = new ArrayList<>();
        tipospag = new ArrayList<>();
        tipostar = new ArrayList<>();
        bancos = new ArrayList<>();

        hisexi = new ArrayList<>();

        llenarItems();
        llenarUnidadesMedida();
        llenarClientes();

    }

    public void nuevoitem() {
        pf_cor_pre_fac = "";
        pf_cod_ser = "0";
        pf_cod_art = "0";
        pf_nomitem = "";
        pf_det_can = 0.00;
        pf_det_can_con = 0.00;
        pf_cod_uni_med = "0";
        pf_nomunimed = "";
        pf_pre_sin_iva = 0.0000;
        pf_iva = 0.0000;
        pf_pre_con_iva = 0.0000;
        pf_por_des = 0.00;
        pf_totalexe = 0.0000;
        pf_totalgrav = 0.0000;
        pf_cod_cli = "0";
        pf_cod_tra_cli = "0";
        pf_lote = "";

        existencia = 0.00;
        //det_sumatotal = 0.0;
        det_flg_pro_ser = "false";
        unimedori = "";
        codunimedori = "0";
        codrefbus = "";

        lotes.clear();

        catprefactura = null;
        llenarItems();
    }

    public void cerrarventana() {
        pf_cor_pre_fac = "";
        pf_cod_ser = "";
        pf_cod_art = "";
        pf_nomitem = "";
        pf_det_can = 0.0;
        pf_det_can_con = 0.0;
        pf_cod_uni_med = "";
        pf_nomunimed = "";
        pf_pre_sin_iva = 0.0;
        pf_iva = 0.0;
        pf_pre_con_iva = 0.0;
        pf_por_des = 0.0;
        pf_totalexe = 0.0;
        pf_totalgrav = 0.0;
        pf_cod_cli = "";
        pf_cod_tra_cli = "";
        pf_lote = "";

        existencia = 0.0;
        det_sumatotal = 0.0;
        det_flg_pro_ser = "";
        unimedori = "";
        codunimedori = "";
        codrefbus = "";

        items = null;
        unidadmedida = null;
        lotes = null;
        clientes = null;

        catprefactura = null;
        prefactura = null;

        cajas = null;
        tiposdoc = null;
        tipospag = null;
        tipostar = null;
        bancos = null;

        hisexi = null;

        err = null;

        mquerys = null;

    }

    //***************** Llenado Catálogos ***********************************
    public void llenarItems() {
        String mQuery = "";
        try {
            pf_cod_ser = "0";
            pf_cod_art = "0";
            existencia = 0.0;
            pf_lote = "";
            codrefbus = "";

            unimedori = "";
            codunimedori = "0";
            pf_cod_uni_med = "0";

            pf_pre_sin_iva = 0.0;
            pf_iva = 0.0;
            pf_pre_con_iva = 0.0;

            items.clear();

            if ("true".equals(det_flg_pro_ser)) {

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
            err.auditarError("Man_Facturacion", "llenarItems", "Error en el llenado de Catálogo Producto Servicio. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarUnidadesMedida() {
        String mQuery = "";
        try {
            unidadmedida.clear();

            mQuery = "select cod_uni, nom_uni "
                    + "from gen_cat_uni_med order by nom_uni;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                unidadmedida.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_Facturacion", "llenarUnidadesMedida", "Error en el llenado de Unidades de Medida. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarLotes() {
        String mQuery = "";
        try {
            lotes.clear();

            pf_lote = mquerys.strQuerySQLvariable("select distinct(det_lot) FROM inv_tra_his where cod_art = " + pf_cod_ser + " and cod_bod = " + cbean.getCod_bod() + " and det_lot <> '' order by det_lot limit 1;");

            mQuery = "select distinct(det_lot) FROM inv_tra_his where cod_art = " + pf_cod_ser + " and cod_bod = " + cbean.getCod_bod() + " and det_lot <> '' order by det_lot;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                lotes.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_Facturacion", "llenarUnidadesMedida", "Error en el llenado de Unidades de Medida. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarClientes() {
        String mQuery = "";
        try {
            clientes.clear();

            mQuery = "select cod_cli,concat(nom_cli,' ',ape_cli)  "
                    + "from fac_cat_cli where cod_emp = " + cbean.getCod_emp() + " order by ape_cli, nom_cli;";

            ResultSet resVariable;

            mquerys.Conectar();
            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                clientes.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_Facturacion", "llenarClientes", "Error en el llenado de Clientes. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarCajas() {
        String mQuery = "";
        try {
            cajas.clear();

            mQuery = "select cod_caj,nom_caj  "
                    + "from fac_cat_caj where cod_suc = " + cbean.getCod_suc_2() + " order by nom_caj;";

            ResultSet resVariable;

            mquerys.Conectar();
            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                cajas.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_Facturacion", "llenarCajas", "Error en el llenado de Cajas. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarTiposDocumentos() {
        String mQuery = "";
        try {
            tiposdoc.clear();

            pag_cod_tip_doc = mquerys.strQuerySQLvariable("select cod_tip_doc,nom_tip_doc  "
                    + "from fac_cat_tip_doc where cod_emp = " + cbean.getCod_emp() + " "
                    + "and cod_tip_doc in (select cod_tip_doc from fac_cat_caj_cnf_doc where cod_caj =" + pag_cod_caj + ") "
                    + "order by cod_tip_doc limit 1;");
            if (pag_cod_tip_doc == null || pag_cod_tip_doc.equals("")) {
                pag_cod_tip_doc = "0";
            }
            pag_num_doc = "";

            mQuery = "select cod_tip_doc,nom_tip_doc  "
                    + "from fac_cat_tip_doc where cod_emp = " + cbean.getCod_emp() + " "
                    + "and cod_tip_doc in (select cod_tip_doc from fac_cat_caj_cnf_doc where cod_caj =" + pag_cod_caj + ") "
                    + "order by cod_tip_doc;";

            ResultSet resVariable;

            mquerys.Conectar();
            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                tiposdoc.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_Facturacion", "llenarTiposDocumentos", "Error en el llenado de Tipos de Documentos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarTiposPagos() {
        String mQuery = "";
        try {
            tipospag.clear();

            pag_tip_pag = mquerys.strQuerySQLvariable("select cod_tip_pag  "
                    + "from fac_cat_tip_pag where cod_emp = " + cbean.getCod_emp() + " and flg_con_cre = '" + pag_con_pag + "' order by cod_tip_pag limit 1;");

            if (pag_tip_pag == null || pag_tip_pag.equals("")) {
                pag_tip_pag = "0";
            }

            mQuery = "select cod_tip_pag,tip_pag  "
                    + "from fac_cat_tip_pag where cod_emp = " + cbean.getCod_emp() + " and flg_con_cre = '" + pag_con_pag + "' order by cod_tip_pag;";

            ResultSet resVariable;

            mquerys.Conectar();
            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                tipospag.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_Facturacion", "llenarTiposPagos", "Error en el llenado de Tipos de Pago. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarTiposTarjeta() {
        String mQuery = "";
        try {
            tipostar.clear();

            mQuery = "select cod_tar, nom_tar  "
                    + "from fac_cat_tar where cod_emp = " + cbean.getCod_emp() + " order by nom_tar;";

            ResultSet resVariable;

            mquerys.Conectar();
            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                tipostar.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_Facturacion", "llenarTiposTarjeta", "Error en el llenado de Tipos de Tarjeta. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarBancos() {
        String mQuery = "";
        try {
            bancos.clear();

            mQuery = "select cod_ban, nom_ban  "
                    + "from gen_cat_ban order by nom_ban;";

            ResultSet resVariable;

            mquerys.Conectar();
            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bancos.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_Facturacion", "llenarBancos", "Error en el llenado de Bancos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    //**************** Acciones Menú *******************************************
    public void iniciarpago() {
        if (!prefactura.isEmpty() && det_sumatotal > 0.0) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            mfechapag = Date.from(Instant.now());

            pag_cor_mae = "";
            pag_cor_det = "";
            //pag_cod_cli = "";
            pag_cod_caj = cbean.getCod_caj();
            pag_cod_tip_doc = "0";
            pag_num_doc = "";
            pag_por_des = 0.00;
            pag_pag_tot = det_sumatotal;
            pag_fec_pag = format.format(mfechapag);
            pag_con_pag = "false";
            pag_tip_pag = "0";

            pag_efe_tot = pag_pag_tot;
            pag_efe_pag_con = 0.0000;
            pag_efe_cam = 0.0000;

            pag_tar_tot = 0.0000;
            pag_tar_tip = "0";
            pag_tar_num = "";

            pag_che_tot = 0.0000;
            pag_che_ban = "0";
            pag_che_num = "";

            boo_efe = "true";
            boo_tar = "true";
            boo_che = "true";
            boo_mix = "true";

            pag_cod_his = "";

            llenarCajas();
            llenarTiposDocumentos();
            llenarTiposPagos();
            llenarTiposTarjeta();
            llenarBancos();

            onSelectDocumento();
            onSelectTipoPago();

            RequestContext.getCurrentInstance().update("frmPagoFac");
            RequestContext.getCurrentInstance().execute("PF('wPagoFac').show()");
        } else {
            addMessage("Pagar", "Debe Agregar al menos un Item a la lista", 3);
        }
    }

    public void cerrarpago() {

    }

    //**************** Acciones Botones ****************************************
    public boolean validarAgregarItem() {
        boolean mValidar = true;
        if (pf_det_can_con == 0.0) {
            addMessage("Validar", "La Cantidad debe ser superior a Cero", 3);
            return false;
        }

        if ("false".equals(det_flg_pro_ser) && "0".equals(pf_cod_ser)) {
            addMessage("Validar", "Debe Seleccionar un Servicio", 3);
            return false;
        }

        if ("true".equals(det_flg_pro_ser) && "0".equals(pf_cod_ser)) {
            addMessage("Validar", "Debe Seleccionar un Producto", 3);
            return false;
        }

        if ("0".equals(cbean.getCod_bod()) || "".equals(cbean.getCod_bod()) || cbean.getCod_bod() == null) {
            addMessage("Validar", "Debe escoger una Bodega", 3);
            return false;
        }

        //**************** Conversión de Unidades ***************************************************************************
        if ("true".equals(det_flg_pro_ser)) {
            Double mFactor = 0.0000;
            if ("0".equals(pf_cod_uni_med)) {
                addMessage("Validar", "Debe Seleccionar una Unidad de Medida", 3);
                return false;
            } else if (!codunimedori.equals(pf_cod_uni_med)) {

                try {
                    mFactor = mquerys.doubleQuerySQLvariable("select ifnull(can_uni_ini/can_uni_fin,0.0) as factor "
                            + "from gen_cat_uni_med_rel "
                            + "where cod_uni_ini = " + codunimedori + " "
                            + "and cod_uni_fin = " + pf_cod_uni_med + ";");
                } catch (Exception e) {
                    mFactor = 0.0000;
                }
                if (mFactor == null) {
                    mFactor = 0.0000;
                }
                if (mFactor == 0.0000) {
                    try {
                        mFactor = mquerys.doubleQuerySQLvariable("select ifnull(can_uni_fin/can_uni_ini,0.0) as factor "
                                + "from gen_cat_uni_med_rel "
                                + "where cod_uni_fin = " + codunimedori + " "
                                + "and cod_uni_ini = " + pf_cod_uni_med + ";");
                    } catch (Exception e) {
                        mFactor = 0.0000;
                    }
                }
                if (mFactor == null) {
                    mFactor = 0.0000;
                }
                if (mFactor == 0.0000) {
                    addMessage("Validar", "No Existe relación Definida Válida entre Unidades de Medida Seleccionadas", 3);
                    return false;
                }

            } else {
                mFactor = 1.0;
            }

            //*************************** Verificación de Existencias y Puntos Máximos *************************************************
            Double mExi = 0.0;

            if (pf_lote == null) {
                pf_lote = "";
            }

            try {
                if ("false".equals(mquerys.strQuerySQLvariable("select flg_usa_lot from inv_cat_art where cod_art =" + pf_cod_ser + ";"))) {
                    mExi = mquerys.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                            + "from inv_tra_his "
                            + "where "
                            + "cod_art=" + pf_cod_ser + " "
                            + "and cod_suc = " + cbean.getCod_suc_2() + " "
                            + "and cod_bod=" + cbean.getCod_bod() + " "
                            + "and  fec_tra <= now() "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");
                } else {
                    mExi = mquerys.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                            + "from inv_tra_his "
                            + "where "
                            + "cod_art=" + pf_cod_ser + " "
                            + "and cod_suc = " + cbean.getCod_suc_2() + " "
                            + "and cod_bod=" + cbean.getCod_bod() + " "
                            + "and det_lot='" + pf_lote + "' "
                            + "and  fec_tra <= now() "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");
                }

            } catch (Exception e) {
                mExi = 0.0;
            }

            Double exigrid = 0.0;
            if (!prefactura.isEmpty()) {
                for (int i = 0; i < prefactura.size(); i++) {
                    if (pf_cod_ser.equals(prefactura.get(i).getCod_art())
                            && pf_lote.equals(prefactura.get(i).getLote())) {
                        exigrid = exigrid + prefactura.get(i).getDet_can();
                    }
                }
            }

            //System.out.println("1: " + pf_det_can_con + " facor: " + mFactor + " grid: " + exigrid + " exi: " + mExi);
            if ((pf_det_can_con * mFactor) + exigrid > mExi) {
                addMessage("Validar Datos", "La Cantidad Solicitada Sobrepasa las Existencias en esta Bodega", 3);
                return false;
            }

            if (mValidar) {
                pf_det_can = pf_det_can_con * mFactor;
            }
        } else {
            pf_det_can = pf_det_can_con;
        }

        return mValidar;
    }

    public void agregarItem() {
        String mQuery = "";
        if (validarAgregarItem()) {
            try {
                int correlativo = 0, insert = 0;
                String nomitem = "", nomuni = "";

                if (det_flg_pro_ser.equals("false")) {
                    pf_cod_art = "0";
                    nomitem = mquerys.strQuerySQLvariable("select concat(ifnull(cod_ref,''),'--',ifnull(nom_ser,'')) as nomitem from fac_cat_ser where cod_ser = " + pf_cod_ser + " and cod_emp = " + cbean.getCod_emp() + ";");
                    nomuni = "Servicio";
                    pf_totalexe = 0.0000;
                    pf_totalgrav = (pf_pre_con_iva * pf_det_can) - ((pf_pre_con_iva * pf_det_can) * pf_por_des / 100.0000);
                } else {
                    pf_cod_ser = "0";
                    nomitem = mquerys.strQuerySQLvariable("select concat(ifnull(cod_ref,''),'--',ifnull(nom_art,'')) as nomitem from inv_cat_art where cod_art = " + pf_cod_art + " and cod_emp = " + cbean.getCod_emp() + ";");
                    nomuni = mquerys.strQuerySQLvariable("select ifnull(nom_uni,'') as nomuni from gen_cat_uni_med where cod_uni = " + pf_cod_uni_med + ";");
                    if ("false".equals(mquerys.strQuerySQLvariable("select flg_exe from inv_cat_art where cod_art = " + pf_cod_art + " and cod_emp = " + cbean.getCod_emp() + ";"))) {
                        pf_totalexe = 0.0000;
                        pf_totalgrav = (pf_pre_con_iva * pf_det_can) - ((pf_pre_con_iva * pf_det_can) * pf_por_des / 100.0000);
                    } else {
                        pf_totalexe = (pf_pre_sin_iva * pf_det_can) - ((pf_pre_sin_iva * pf_det_can) * pf_por_des / 100.0000);
                        pf_totalgrav = 0.0000;
                    }
                }

                for (int i = 0; i < prefactura.size(); i++) {

                    if (det_flg_pro_ser.equals(prefactura.get(i).getFlg_ser_pro())
                            && pf_cod_ser.equals(prefactura.get(i).getCod_ser())
                            && pf_cod_art.equals(prefactura.get(i).getCod_art())
                            && pf_cod_uni_med.equals(prefactura.get(i).getCod_uni_med())
                            && pf_cod_cli.equals(prefactura.get(i).getCod_cli())
                            && pf_por_des.equals(prefactura.get(i).getPor_des())
                            && pf_lote.equals(prefactura.get(i).getLote())) {

                        insert = 1;
                        prefactura.set(i, new Cat_prefactura(
                                prefactura.get(i).getFlg_ser_pro(),
                                prefactura.get(i).getCor_pre_fac(),
                                prefactura.get(i).getCod_ser(),
                                prefactura.get(i).getCod_art(),
                                prefactura.get(i).getNomitem(),
                                prefactura.get(i).getDet_can() + pf_det_can,
                                prefactura.get(i).getDet_can_con() + pf_det_can_con,
                                prefactura.get(i).getCod_uni_med(),
                                prefactura.get(i).getNomunimed(),
                                prefactura.get(i).getPre_sin_iva(),
                                prefactura.get(i).getIva(),
                                prefactura.get(i).getPre_con_iva(),
                                prefactura.get(i).getPor_des(),
                                prefactura.get(i).getTotalexe() + pf_totalexe,
                                prefactura.get(i).getTotalgrav() + pf_totalgrav,
                                prefactura.get(i).getTotal() + pf_totalexe + pf_totalgrav,
                                prefactura.get(i).getCod_cli(),
                                prefactura.get(i).getCod_tra_cli(),
                                prefactura.get(i).getLote()
                        ));
                    }

                    if (Integer.valueOf(prefactura.get(i).getCor_pre_fac()) > correlativo) {
                        correlativo = Integer.valueOf(prefactura.get(i).getCor_pre_fac());
                    }
                }

                if (insert == 0) {

                    prefactura.add(new Cat_prefactura(
                            det_flg_pro_ser,
                            String.valueOf(correlativo + 1),
                            pf_cod_ser,
                            pf_cod_art,
                            nomitem,
                            pf_det_can,
                            pf_det_can_con,
                            pf_cod_uni_med,
                            nomuni,
                            pf_pre_sin_iva,
                            pf_iva,
                            pf_pre_con_iva,
                            pf_por_des,
                            pf_totalexe,
                            pf_totalgrav,
                            pf_totalexe + pf_totalgrav,
                            "0",
                            "0",
                            pf_lote
                    ));
                }
                det_sumatotal = det_sumatotal + pf_totalexe + pf_totalgrav;

            } catch (Exception e) {
                addMessage("Agregar", "Error al Agregar el Item. " + e.getMessage(), 3);
                err.auditarError("Man_Facturacion", "agregarItem", "Error al Guardar Item. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }
            nuevoitem();
        }
    }

    public boolean validarRemover() {
        boolean mValidar = true;
        if (pf_cor_pre_fac == null || "".equals(pf_cor_pre_fac) || "0".equals(pf_cor_pre_fac)) {
            mValidar = false;
            addMessage("Validar", "Debe Seleccionar un Registro a Remover", 3);
        }

        return mValidar;
    }

    public void eliminarItem() {
        String mQuery = "";
        if (validarRemover()) {
            try {
                for (int i = 0; i < prefactura.size(); i++) {
                    if (pf_cor_pre_fac.equals(prefactura.get(i).getCor_pre_fac())) {
                        det_sumatotal = det_sumatotal - prefactura.get(i).getTotalexe() - prefactura.get(i).getTotalgrav();
                        prefactura.remove(i);
                    }
                }
                addMessage("Remover", "Item Removido con éxito", 1);
            } catch (Exception e) {
                addMessage("Remover", "Error al Remover el Item. " + e.getMessage(), 3);
                err.auditarError("Man_Facturacion", "eliminarItem", "Error al Remover Item. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }
            nuevoitem();
        }
    }

    //**************** Facturación *********************************************
    public boolean validarfacturacion() {
        boolean mValidar = true;

        if (pag_por_des == null) {
            pag_por_des = 0.0000;
        }
        if (pag_pag_tot == null) {
            pag_pag_tot = 0.0000;
        }
        if (pag_efe_tot == null) {
            pag_efe_tot = 0.0000;
        }
        if (pag_efe_pag_con == null) {
            pag_efe_pag_con = 0.0000;
        }
        if (pag_efe_cam == null) {
            pag_efe_cam = 0.0000;
        }
        if (pag_tar_tot == null) {
            pag_tar_tot = 0.0000;
        }
        if (pag_che_tot == null) {
            pag_che_tot = 0.0000;
        }

        if (cbean.getCod_emp().equals("0") || cbean.getCod_emp().equals("")) {
            addMessage("Validar", "Debe Seleccionar una Empresa en Configuraciones de Usuario", 3);
            return false;
        }

        if (cbean.getCod_suc_2().equals("0") || cbean.getCod_suc().equals("")) {
            addMessage("Validar", "Debe Seleccionar una Sucursal en Configuraciones de Usuario", 3);
            return false;
        }

        if (pag_cod_cli.equals("0") || pag_cod_cli.equals("")) {
            addMessage("Validar", "Debe Seleccionar un Cliente", 3);
            return false;
        }

        if (pag_cod_caj.equals("0") || pag_cod_caj.equals("")) {
            addMessage("Validar", "Debe Seleccionar un Caja", 3);
            return false;
        }

        if (pag_cod_tip_doc.equals("0") || pag_cod_tip_doc.equals("")) {
            addMessage("Validar", "Debe Seleccionar un Tipo de Documento", 3);
            return false;
        }

        if (pag_num_doc.equals("")) {
            addMessage("Validar", "Verifique la Tabla de Configuración de Documentos", 3);
            return false;
        }

        if (pag_pag_tot <= 0.0) {
            addMessage("Validar", "El Pago Total a Facturar debe ser mayor que Cero", 3);
            return false;
        }

        if (pag_tip_pag.equals("0")) {
            addMessage("Validar", "Debe Seleccionar un Tipo de Pago", 3);
            return false;
        }

        if (pag_con_pag.equals("false")) {
            if (boo_efe.equals("false") && boo_tar.equals("true") && boo_che.equals("true")) {
                if (pag_efe_tot == 0.0000) {
                    addMessage("Validar", "El Pago Efectuado en Efectivo debe ser mayor que Cero", 3);
                    return false;
                }

                if (pag_efe_pag_con == 0.0000) {
                    addMessage("Validar", "El Pago Efectuado en Efectivo debe ser mayor que Cero", 3);
                    return false;
                }

                if (Double.compare(Math.round(pag_pag_tot * 100.0000 / 100.0000) * 1.0000, Math.round(pag_efe_tot * 100.0000 / 100.0000) * 1.0000) != 0) {
                    addMessage("Validar", "El Pago Efectuado en Efectivo debe ser Igual a la Venta Total " + pag_pag_tot, 3);
                    return false;
                }

                if (Double.compare(pag_efe_tot * 1.0000, (pag_efe_pag_con - pag_efe_cam) * 1.0000) != 0) {
                    addMessage("Validar", "El Pago Efectuado en Efectivo debe ser Igual al Saldo Total " + pag_pag_tot, 3);
                    return false;
                }
            }

            if (boo_efe.equals("true") && boo_tar.equals("false") && boo_che.equals("true")) {
                if (pag_tar_tot == 0.0000) {
                    addMessage("Validar", "El Pago Efectuado con Tarjeta debe ser mayor que Cero", 3);
                    return false;
                }

                if (Double.compare(Math.round(pag_pag_tot * 100.0000 / 100.0000) * 1.0000, Math.round(pag_tar_tot * 100.0000 / 100.0000) * 1.0000) != 0) {
                    addMessage("Validar", "El Pago Efectuado con Tarjeta debe ser Igual al Saldo Total " + pag_pag_tot, 3);
                    return false;
                }

                if (pag_tar_tip.equals("0")) {
                    addMessage("Validar", "Debe Seleccionar un Tipo de Tarjeta", 3);
                    return false;
                }

                if (pag_tar_num.equals("")) {
                    addMessage("Validar", "Debe Ingresar el Número de Tarjeta", 3);
                    return false;
                }

            }

            if (boo_efe.equals("true") && boo_tar.equals("true") && boo_che.equals("false")) {
                if (pag_che_tot == 0.0000) {
                    addMessage("Validar", "El Pago Efectuado con Cheque debe ser mayor que Cero", 3);
                    return false;
                }

                if (Double.compare(Math.round(pag_pag_tot * 100.0000 / 100.0000) * 1.0000, Math.round(pag_che_tot * 100.0000 / 100.0000) * 1.0000) != 0) {
                    addMessage("Validar", "El Pago Efectuado con Cheque debe ser Igual al Saldo Total " + pag_pag_tot, 3);
                    return false;
                }

                if (pag_che_ban.equals("0")) {
                    addMessage("Validar", "Debe Seleccionar un Banco", 3);
                    return false;
                }

                if (pag_che_num.equals("")) {
                    addMessage("Validar", "Debe Ingresar el Número de Cheque", 3);
                    return false;
                }
            }

            if (boo_efe.equals("false") && boo_tar.equals("false") && boo_che.equals("false")) {
                if (pag_efe_tot + pag_tar_tot + pag_che_tot == 0.0000) {
                    addMessage("Validar", "El Pago Mixto Efectuado debe ser mayor que Cero", 3);
                    return false;
                }

                if (Double.compare(Math.round(pag_pag_tot * 100.0000 / 100.0000) * 1.0000, Math.round((pag_efe_tot + pag_tar_tot + pag_che_tot) * 100.0000 / 100.0000) * 1.0000) != 0) {
                    addMessage("Validar", "El Pago Mixto Efectuado debe ser Igual al Saldo Total " + pag_pag_tot, 3);
                    return false;
                }

                if (Double.compare(Math.round(pag_efe_tot * 100.0000 / 100.0000), Math.round(pag_pag_tot * 100.0000 / 100.0000)) == 0 || Double.compare(Math.round(pag_tar_tot * 100.0000 / 100.0000), Math.round(pag_pag_tot * 100.0000 / 100.0000)) == 0 || Double.compare(Math.round(pag_che_tot * 100.0000 / 100.0000), Math.round(pag_pag_tot * 100.0000 / 100.0000)) == 0) {
                    addMessage("Validar", "El Pago Mixto Debe Repartirse entre más de un Tipo de Pago", 3);
                    return false;
                }

                if (pag_efe_tot > 0.0) {
                    if (pag_efe_pag_con == 0.0000) {
                        addMessage("Validar", "El Pago Efectuado en Efectivo debe ser mayor que Cero", 3);
                        return false;
                    }

                    if (Double.compare(Math.round(pag_efe_tot * 100.0000 / 100.0000), Math.round((pag_efe_pag_con - pag_efe_cam) * 100.0000 / 100.0000)) == 0) {
                        addMessage("Validar", "El Pago Efectuado en Efectivo debe ser Igual a la Cantidad Total en Efectivo Estipulada " + pag_efe_tot, 3);
                        return false;
                    }
                }

                if (pag_tar_tot > 0.0) {
                    if (pag_tar_tip.equals("0")) {
                        addMessage("Validar", "Debe Seleccionar un Tipo de Tarjeta", 3);
                        return false;
                    }

                    if (pag_tar_num.equals("")) {
                        addMessage("Validar", "Debe Ingresar el Número de Tarjeta", 3);
                        return false;
                    }
                }
                if (pag_che_tot > 0.0) {
                    if (pag_che_ban.equals("0")) {
                        addMessage("Validar", "Debe Seleccionar un Banco", 3);
                        return false;
                    }

                    if (pag_che_num.equals("")) {
                        addMessage("Validar", "Debe Ingresar el Número de Cheque", 3);
                        return false;
                    }
                }
            }

            if (boo_efe.equals("true") && boo_tar.equals("true") && boo_che.equals("true")) {
                addMessage("Validar", "Verifique la Configuración de Tipos de Pago", 3);
                return false;
            }
        } else if (pag_pag_tot * 1.0000 < ((pag_efe_pag_con - pag_efe_cam) + pag_tar_tot + pag_che_tot) * 1.0000) {
            addMessage("Validar", "El Pago de Anticipo no puede ser mayor al Saldo Total " + pag_pag_tot, 3);
            return false;
        }

        return mValidar;
    }

    public boolean guardarmovimiento() {
        String mQuery = "";
        String mhistoria = "";
        ResultSet resVariable;
        try {
            int ord_dia = 0;
            int id_tra = 0;
            Double cos_uni = 0.00, cos_pro = 0.00, exi_ant = 0.0, exi_act = 0.0, exi_act_lot;
            for (int i = 0; i < prefactura.size(); i++) {
                //*************** Tratamiento Histórico de movimientos  *********************************************************************** 
                mQuery = "select ifnull(max(cod_tra),0)+1 as codigo from inv_tra_his;";
                id_tra = Integer.valueOf(mquerys.strQuerySQLvariable(mQuery));

                pag_cod_his = String.valueOf(id_tra);

                mQuery = "select ifnull(max(ord_dia),0)+1 as codigo from inv_tra_his "
                        + "where fec_tra = str_to_date('" + pag_fec_pag + "','%d/%m/%Y') "
                        + "AND cod_suc = " + cbean.getCod_suc_2() + " "
                        + "AND cod_bod = " + cbean.getCod_bod() + " "
                        + "AND cod_art =" + prefactura.get(i).getCod_art() + ";";
                ord_dia = Integer.valueOf(mquerys.strQuerySQLvariable(mQuery));

                cos_uni = 0.00;

                //Costos promedios 
                //************* Costo Promedio y Existencia anteriores  **************
                cos_pro = mquerys.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                        + "from inv_tra_his "
                        + "where "
                        + "cod_art=" + prefactura.get(i).getCod_art() + " "
                        + "and cod_suc = " + cbean.getCod_suc_2() + " "
                        + "and  fec_tra <= STR_TO_DATE('" + pag_fec_pag + "','%d/%m/%Y') "
                        + "order by fec_tra desc,"
                        + "ord_dia desc "
                        + "limit 1;");

                //***********  Existencia Anterior **************************************************
                exi_ant = mquerys.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                        + "from inv_tra_his "
                        + "where "
                        + "cod_art=" + prefactura.get(i).getCod_art() + " "
                        + "and cod_suc = " + cbean.getCod_suc_2() + " "
                        + "and cod_bod=" + cbean.getCod_bod() + " "
                        + "and  fec_tra <= STR_TO_DATE('" + pag_fec_pag + "','%d/%m/%Y') "
                        + "order by fec_tra desc,"
                        + "ord_dia desc "
                        + "limit 1;");

                //********** Existencia Anterior Lote **********************************************
                exi_act_lot = mquerys.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                        + "from inv_tra_his "
                        + "where "
                        + "cod_art=" + prefactura.get(i).getCod_art() + " "
                        + "and cod_suc = " + cbean.getCod_suc_2() + " "
                        + "and cod_bod=" + cbean.getCod_bod() + " "
                        + "and det_lot='" + prefactura.get(i).getLote() + "' "
                        + "and  fec_tra <= STR_TO_DATE('" + pag_fec_pag + "','%d/%m/%Y') "
                        + "order by fec_tra desc,"
                        + "ord_dia desc "
                        + "limit 1;");

                //******************* Tratamiento de Existencias **************************************
                exi_act = exi_ant - prefactura.get(i).getDet_can();
                exi_act_lot = exi_act_lot - prefactura.get(i).getDet_can();
                //*********** Valida que las Existencias no sean menor que Cero ******************
                if (exi_act < 0.0) {
                    exi_act = 0.0;
                }
                if (exi_act_lot < 0.0) {
                    exi_act_lot = 0.0;
                }

                mhistoria = "(" + id_tra + ",'true',str_to_date('" + pag_fec_pag + "','%d/%m/%Y'),"
                        + ord_dia + ",0,0," + cbean.getCod_suc_2() + "," + cbean.getCod_bod() + ",0,"
                        + prefactura.get(i).getCod_art() + "," + prefactura.get(i).getDet_can() + ","
                        + exi_ant + "," + exi_act + "," + cos_uni + "," + cos_pro + ",'"
                        + prefactura.get(i).getLote() + "'," + exi_act_lot + ")";
                mQuery = "insert into inv_tra_his (cod_tra,flg_ing_sal,fec_tra,ord_dia,cod_mov,det_mov,cod_suc,cod_bod,"
                        + "cod_ubi,cod_art,det_can,exi_ant_bod,exi_act_bod,cos_uni,cos_pro,det_lot,exi_act_lot) VALUES "
                        + mhistoria + ";";
                mquerys.dmlSQLvariable(mQuery);

                //***********************************************************************************************************
                //***************************** MODIFICACIÓN DE REGISTROS POSTERIORES ***************************************
                //***********************************************************************************************************
                Double contasiguientes;

                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacci�n 
                contasiguientes = mquerys.doubleQuerySQLvariable("select count(cod_tra) "
                        + "from inv_tra_his where "
                        + "fec_tra = STR_TO_DATE('" + pag_fec_pag + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_suc = " + cbean.getCod_suc_2() + " "
                        + "and cod_bod=" + cbean.getCod_bod() + " "
                        + "and cod_art = " + prefactura.get(i).getCod_art() + " "
                        + ";");
                contasiguientes = contasiguientes
                        + mquerys.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tra_his where "
                                + "fec_tra > STR_TO_DATE('" + pag_fec_pag + "','%d/%m/%Y') "
                                + "and cod_suc = " + cbean.getCod_suc_2() + " "
                                + "and cod_bod=" + cbean.getCod_bod() + " "
                                + "and cod_art = " + prefactura.get(i).getCod_art() + " "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        mquerys.Conectar();
                        resVariable = mquerys.querySQLvariable("(select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tra_his "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + pag_fec_pag + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_suc = " + cbean.getCod_suc_2() + " "
                                + "and cod_bod=" + cbean.getCod_bod() + " "
                                + "and cod_art=" + prefactura.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc) "
                                + "UNION ALL "
                                + "(select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tra_his "
                                + "where "
                                + "fec_tra > STR_TO_DATE('" + pag_fec_pag + "','%d/%m/%Y') "
                                + "and cod_suc = " + cbean.getCod_suc_2() + " "
                                + "and cod_bod=" + cbean.getCod_bod() + " "
                                + "and cod_art=" + prefactura.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc)"
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4),
                                    resVariable.getDouble(5)
                            ));
                        }
                        resVariable.close();
                        mquerys.Desconectar();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexi) {

                            exi_ant = exi_act;
                            exi_act = exi_act - (seriehistorica1.getDet_can());
                            if (exi_act < 0.0) {
                                exi_act = 0.0;
                            }

                            mQuery = "update inv_tra_his set "
                                    + "cos_pro = " + cos_pro + ","
                                    + "exi_ant_bod = " + exi_ant + ","
                                    + "exi_act_bod = " + exi_act + " "
                                    + "where "
                                    + "cod_tra = " + seriehistorica1.getCod_his()
                                    + ";";
                            mquerys.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en Actualización datos posteriores por Bodega. " + e.getMessage() + " Query: " + (mQuery.replace("\\", "\\\\")).replace("'", "\\'"));
                    }
                }

                //***********************************************************************************************************
                //*************************** MODIFICACIÓN DE REGISTROS POSTERIORES POR LOTE ********************************
                //***********************************************************************************************************
                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacci�n 
                contasiguientes = mquerys.doubleQuerySQLvariable("select count(cod_tra) "
                        + "from inv_tra_his where "
                        + "fec_tra = STR_TO_DATE('" + pag_fec_pag + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_suc = " + cbean.getCod_suc_2() + " "
                        + "and cod_bod = " + cbean.getCod_bod() + " "
                        + "and cod_art = " + prefactura.get(i).getCod_art() + " "
                        + "and det_lot ='" + prefactura.get(i).getLote() + "' "
                        + ";");
                contasiguientes = contasiguientes
                        + mquerys.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tra_his where "
                                + "fec_tra > STR_TO_DATE('" + pag_fec_pag + "','%d/%m/%Y') "
                                + "and cod_suc = " + cbean.getCod_suc_2() + " "
                                + "and cod_bod = " + cbean.getCod_bod() + " "
                                + "and cod_art = " + prefactura.get(i).getCod_art() + " "
                                + "and det_lot ='" + prefactura.get(i).getLote() + "' "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        mquerys.Conectar();
                        resVariable = mquerys.querySQLvariable("(select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tra_his "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + pag_fec_pag + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_suc = " + cbean.getCod_suc_2() + " "
                                + "and cod_bod = " + cbean.getCod_bod() + " "
                                + "and cod_art = " + prefactura.get(i).getCod_art() + " "
                                + "and det_lot ='" + prefactura.get(i).getLote() + "' "
                                + "order by fec_tra asc,"
                                + "ord_dia asc) "
                                + "UNION ALL "
                                + "(select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tra_his "
                                + "where "
                                + "fec_tra > STR_TO_DATE('" + pag_fec_pag + "','%d/%m/%Y') "
                                + "and cod_suc = " + cbean.getCod_suc_2() + " "
                                + "and cod_bod = " + cbean.getCod_bod() + " "
                                + "and cod_art = " + prefactura.get(i).getCod_art() + " "
                                + "and det_lot ='" + prefactura.get(i).getLote() + "' "
                                + "order by fec_tra asc,"
                                + "ord_dia asc)"
                                + ";"
                        );
                        while (resVariable.next()) {
                            hisexi.add(new ListaCortaTransaccionExistencias(
                                    resVariable.getString(1),
                                    resVariable.getString(2),
                                    resVariable.getDouble(3),
                                    resVariable.getDouble(4),
                                    resVariable.getDouble(5)
                            ));
                        }
                        resVariable.close();
                        mquerys.Desconectar();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexi) {

                            exi_act_lot = exi_act_lot - (seriehistorica1.getDet_can());
                            if (exi_act_lot < 0.0) {
                                exi_act_lot = 0.0;
                            }

                            mQuery = "update inv_tra_his set "
                                    + "exi_act_lot = " + exi_act_lot + " "
                                    + "where "
                                    + "cod_tra = " + seriehistorica1.getCod_his()
                                    + ";";
                            mquerys.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en Actualización datos posteriores por Lote. " + e.getMessage() + " Query: " + (mQuery.replace("\\", "\\\\")).replace("'", "\\'"));
                    }
                }

                //******************************* Fin Tratamiento Histórico de movimientos *****************************
                mQuery = "select count(cod_art) from inv_tbl_lot_ven "
                        + "WHERE "
                        + "cod_suc = " + cbean.getCod_suc_2() + " "
                        + "AND cod_bod = " + cbean.getCod_bod() + " "
                        + "AND cod_art = " + prefactura.get(i).getCod_art() + " "
                        + "AND num_lot = '" + prefactura.get(i).getLote() + "' ";

                String contador = mquerys.strQuerySQLvariable(mQuery);
                if ("0".equals(contador)) {

                    String msigno;

                    msigno = " - " + prefactura.get(i).getDet_can();

                    mQuery = "update inv_tbl_lot_ven set "
                            + "exi_act = (exi_act " + msigno + ") "
                            + " where "
                            + " cod_suc=" + cbean.getCod_suc_2()
                            + " and cod_bod=" + cbean.getCod_bod()
                            + " and cod_art = " + prefactura.get(i).getCod_art()
                            + " and num_lot ='" + prefactura.get(i).getLote() + "' ";

                    mquerys.dmlSQLvariable(mQuery);

                }
            }

            return true;
        } catch (NumberFormatException e) {
            addMessage("Facturación", "Error al momento de Descargar Inventario." + e.getMessage(), 3);
            err.auditarError("Man_Facturacion", "guardarmovimiento", "Error en Guardar salida de Producto por Facturación. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            return false;
        }

    }

    public void facturar() {
        String mQuery = "";
        String mValores = "";
        int corr = 1;
        Double vIvatotal = 0.0000, vVennosujeta = 0.0000, vVentaExenta = 0.0000, vVentaGravada = 0.0000, vAnticipo = 0.0000, vRetencion = 0.0000;
        if (validarfacturacion()) {
            try {
                if ("".equals(pag_cor_mae)) {

                    mQuery = "select ifnull(max(cod_fac),0)+1 as codigo from fac_fac_mae;";
                    pag_cor_mae = mquerys.strQuerySQLvariable(mQuery);

                    for (int i = 0; i < prefactura.size(); i++) {

                        guardarmovimiento();

                        mValores = mValores + ",(" + pag_cor_mae + "," + corr + "," + pag_cod_his + ",'" + prefactura.get(i).getFlg_ser_pro() + "',"
                                + prefactura.get(i).getCod_art() + "," + prefactura.get(i).getCod_ser() + "," + prefactura.get(i).getDet_can() + ","
                                + prefactura.get(i).getDet_can_con() + "," + prefactura.get(i).getCod_uni_med() + "," + prefactura.get(i).getPre_sin_iva() + ","
                                + prefactura.get(i).getIva() + "," + prefactura.get(i).getTotalexe() + "," + prefactura.get(i).getTotalgrav() + ","
                                + prefactura.get(i).getPor_des() + "," + cbean.getCod_usu() + ",now())";

                        pag_cod_his = "";

                        vIvatotal = vIvatotal + prefactura.get(i).getIva();
                        vVentaExenta = vVentaExenta + prefactura.get(i).getTotalexe();
                        vVentaGravada = vVentaGravada + prefactura.get(i).getTotalgrav();
                        corr = corr + 1;
                    }

                    //pag_num_doc = mquerys.strQuerySQLvariable("select cor_ult from fac_cat_caj_cnf_doc where cod_tip_doc = " + pag_cod_tip_doc + " and cod_caj =" + pag_cod_caj + ";");
                    pag_num_doc = mquerys.strQuerySQLvariable("select "
                            + "concat("
                            + "ifnull(pre_cor,''), "
                            + "case ifnull(cer_izq,0) "
                            + "when 0 then (select cnf1.cor_ult + 1 from fac_cat_caj_cnf_doc as cnf1 where cnf1.cod_tip_doc = mae.cod_tip_doc and cnf1.cod_caj = " + pag_cod_caj + " ) "
                            + "else lpad((select cnf2.cor_ult + 1 from fac_cat_caj_cnf_doc as cnf2 where cnf2.cod_tip_doc = mae.cod_tip_doc and cnf2.cod_caj = " + pag_cod_caj + " ), cer_izq,'0') "
                            + "end ) as cod "
                            + "FROM fac_cat_tip_doc as mae "
                            + "where mae.cod_tip_doc = " + pag_cod_tip_doc + ";");

                    mQuery = "update fac_cat_tip_doc set ult_cor = ult_cor + 1 where cod_tip_doc = " + pag_cod_tip_doc + " and cod_emp =" + cbean.getCod_emp() + ";";
                    mquerys.dmlSQLvariable(mQuery);

                    mQuery = "update fac_cat_caj_cnf_doc set cor_ult = cor_ult + 1 where cod_tip_doc = " + pag_cod_tip_doc + " and cod_caj =" + pag_cod_caj + ";";
                    mquerys.dmlSQLvariable(mQuery);

                    mQuery = "insert into fac_fac_mae ( "
                            + "   cod_fac "
                            + "  ,fec_fac "
                            + "  ,fec_rea_fac "
                            + "  ,cod_suc "
                            + "  ,cod_tip_doc_ext "
                            + "  ,doc_ext "
                            + "  ,cod_cli "
                            + "  ,cod_usu "
                            + "  ,cod_caj "
                            + "  ,ven_tot "
                            + "  ,iva_tot "
                            + "  ,ven_non_suj "
                            + "  ,ven_exe "
                            + "  ,ven_gra "
                            + "  ,des_ven "
                            + "  ,ven_fin "
                            + "  ,con_pag "
                            + "  ,cod_tip_pag "
                            + "  ,det_ant "
                            + "  ,pag_pen "
                            + "  ,pag_efe "
                            + "  ,cod_ban_che "
                            + "  ,num_che "
                            + "  ,pag_che "
                            + "  ,cod_tip_tar "
                            + "  ,num_tar "
                            + "  ,pag_tar "
                            + "  ,det_ret "
                            + "  ,flg_anu "
                            + "  ,obs_anu "
                            + "  ,flg_dev "
                            + "  ,cod_fac_dev "
                            + "  ,usu_mod "
                            + "  ,fec_mod "
                            + "  ,tip_mod "
                            + ") VALUES ("
                            + pag_cor_mae + ","
                            + "str_to_date('" + pag_fec_pag + "','%d/%m/%Y'),"
                            + "now(),"
                            + cbean.getCod_suc_2() + ","
                            + pag_cod_tip_doc + ","
                            + "'" + pag_num_doc + "',"
                            + pag_cod_cli + ","
                            + cbean.getCod_usu() + ","
                            + pag_cod_caj + ","
                            + det_sumatotal + ","
                            + vIvatotal + ","
                            + vVennosujeta + ","
                            + vVentaExenta + ","
                            + vVentaGravada + ","
                            + pag_por_des + ","
                            + pag_pag_tot + ","
                            + pag_con_pag + ","
                            + pag_tip_pag + ","
                            + vAnticipo + ","
                            + (pag_pag_tot - vAnticipo) + ","
                            + pag_efe_tot + ","
                            + pag_che_ban + ","
                            + "'" + pag_che_num + "',"
                            + pag_che_tot + ","
                            + pag_tar_tip + ","
                            + "'" + pag_tar_num + "',"
                            + pag_tar_tot + ","
                            + vRetencion + ","
                            + "0,'',0,0," + cbean.getCod_usu() + ",now() , 'EMISIÓN DE FACTURA')"
                            + ";";
                    mquerys.dmlSQLvariable(mQuery);

                    mQuery = "insert into fac_fac_det (cod_fac,det_fac,cod_his,flg_ser_pro,cod_art,"
                            + "cod_ser,det_can,det_can_con,cod_uni_med,pre_uni,det_iva,ven_exe,"
                            + "ven_grav,des_ven,usu_mod,fec_mod) VALUES " + mValores.substring(1);
                    mquerys.dmlSQLvariable(mQuery);
                    
                    //imprimirFactura();

                }
                nuevoitem();
                det_sumatotal = 0.0000;
                prefactura.clear();
            } catch (Exception e) {

            }
        }
    }

//**************** Controles ***********************************************
    public void dateSelectedFecPag(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        pag_fec_pag = format.format(date);
    }

    public void onRowSelectPrefactura(SelectEvent event) {
        pf_cor_pre_fac = ((Cat_prefactura) event.getObject()).getCor_pre_fac();
    }

    public void onInsertText() {
        lotes.clear();
        if (det_flg_pro_ser.equals("false")) {
            pf_cod_ser = "0";
            pf_cod_art = "0";
            unimedori = "";
            codunimedori = "0";
            pf_cod_uni_med = "0";
            existencia = 0.0;
            pf_pre_sin_iva = 0.0000;
            pf_iva = 0.0000;
            pf_pre_con_iva = 0.0000;
            pf_det_can_con = 0.0;
            if (!codrefbus.equals("")) {
                pf_cod_ser = mquerys.strQuerySQLvariable("select ifnull(cod_ser,'0') as codser from fac_cat_ser where cod_ref = '" + codrefbus + "' and cod_emp = " + cbean.getCod_emp() + ";");
                if (pf_cod_ser == null) {
                    pf_cod_ser = "0";
                }
                if (!pf_cod_ser.equals("0") && !pf_cod_ser.equals("")) {
                    pf_pre_sin_iva = mquerys.doubleQuerySQLvariable("select ifnull(pre_sin_iva,0.0) as presiniva from fac_cat_ser where cod_ref = '" + codrefbus + "' and cod_emp = " + cbean.getCod_emp() + ";");
                    pf_iva = pf_pre_sin_iva * Double.valueOf(cbean.getPor_iva()) / 100.0000;
                    pf_pre_con_iva = pf_pre_sin_iva + pf_iva;
                    pf_det_can_con = 1.0;
                }
            }

        } else {
            pf_cod_ser = "0";
            pf_cod_art = "0";
            unimedori = "";
            codunimedori = "0";
            pf_cod_uni_med = "0";
            existencia = 0.0;
            pf_pre_sin_iva = 0.0000;
            pf_iva = 0.0000;
            pf_pre_con_iva = 0.0000;
            pf_det_can_con = 0.0;
            if (!codrefbus.equals("")) {
                pf_cod_ser = mquerys.strQuerySQLvariable("select ifnull(cod_art,'0') as codart from inv_cat_art where cod_ref = '" + codrefbus + "' and cod_emp = " + cbean.getCod_emp() + ";");
                if (pf_cod_ser == null) {
                    pf_cod_ser = "0";
                }
                pf_cod_art = pf_cod_ser;
                if (!pf_cod_ser.equals("0") && !pf_cod_ser.equals("")) {
                    llenarLotes();
                    codunimedori = mquerys.strQuerySQLvariable("select ifnull(cod_uni_med,0) as codunimed from inv_cat_art where cod_ref = '" + codrefbus + "' and cod_emp = " + cbean.getCod_emp() + ";");
                    pf_cod_uni_med = codunimedori;
                    unimedori = mquerys.strQuerySQLvariable("select ifnull(nom_uni,'') as nomuni from gen_cat_uni_med where cod_uni = " + codunimedori + ";");
                    if ("false".equals(mquerys.strQuerySQLvariable("select flg_usa_lot from inv_cat_art where cod_art =" + pf_cod_ser + ";"))) {
                        existencia = mquerys.doubleQuerySQLvariable("select ifnull((select his.exi_act_bod from inv_tra_his as his where his.cod_suc = " + cbean.getCod_suc_2() + " and his.cod_bod = " + cbean.getCod_bod() + " and his.cod_art = " + pf_cod_art + " and his.fec_tra <= now() order by his.fec_tra desc, his.ord_dia desc limit 1),0) as existencia;");
                        pf_lote = "";
                    } else {
                        existencia = mquerys.doubleQuerySQLvariable("select ifnull((select his.exi_act_lot from inv_tra_his as his where his.cod_suc = " + cbean.getCod_suc_2() + " and his.cod_bod = " + cbean.getCod_bod() + " and his.cod_art = " + pf_cod_art + " and det_lot = '" + pf_lote + "' and his.fec_tra <= now() order by his.fec_tra desc, his.ord_dia desc limit 1),0) as existencia;");
                    }
                    pf_pre_sin_iva = mquerys.doubleQuerySQLvariable("select ifnull(pre_sin_iva,0.0) as presiniva from inv_cat_art where cod_art = " + pf_cod_art + ";");
                    pf_iva = pf_pre_sin_iva * Double.valueOf(cbean.getPor_iva()) / 100.0000;
                    pf_pre_con_iva = pf_pre_sin_iva + pf_iva;
                    pf_det_can_con = 1.0;
                }

            }
        }
        //RequestContext.getCurrentInstance().update("frmFacturacion");

    }

    public void onSelectItem() {
        lotes.clear();
        if (det_flg_pro_ser.equals("false")) {
            pf_cod_art = "0";
            if (pf_cod_ser.equals("0")) {
                codrefbus = "";
                pf_pre_sin_iva = 0.0000;
                pf_iva = 0.0000;
                pf_pre_con_iva = 0.0000;
                pf_det_can_con = 0.0000;
            } else {
                codrefbus = mquerys.strQuerySQLvariable("select ifnull(cod_ref,'') as codref from fac_cat_ser where cod_ser = " + pf_cod_ser + ";");
                pf_pre_sin_iva = mquerys.doubleQuerySQLvariable("select ifnull(pre_sin_iva,0.0) as presiniva from fac_cat_ser where cod_ser = " + pf_cod_ser + ";");
                pf_iva = pf_pre_sin_iva * Double.valueOf(cbean.getPor_iva()) / 100.0000;
                pf_pre_con_iva = pf_pre_sin_iva + pf_iva;
                pf_det_can_con = 1.0;
            }
            unimedori = "";
            codunimedori = "0";
            pf_cod_uni_med = "0";
            existencia = 0.0;

        } else {
            pf_cod_art = pf_cod_ser;
            if (pf_cod_art.equals("0")) {
                codrefbus = "";
                unimedori = "";
                codunimedori = "0";
                pf_cod_uni_med = "0";
                existencia = 0.0;
                pf_pre_sin_iva = 0.0000;
                pf_iva = 0.0000;
                pf_pre_con_iva = 0.0000;
                pf_det_can_con = 0.0000;
            } else {
                llenarLotes();
                codrefbus = mquerys.strQuerySQLvariable("select ifnull(cod_ref,'') as codref from inv_cat_art where cod_art = " + pf_cod_art + ";");
                codunimedori = mquerys.strQuerySQLvariable("select ifnull(cod_uni_med,0) as codunimed from inv_cat_art where cod_art = " + pf_cod_art + ";");
                pf_cod_uni_med = codunimedori;
                unimedori = mquerys.strQuerySQLvariable("select ifnull(nom_uni,'') as nomuni from gen_cat_uni_med where cod_uni = " + codunimedori + ";");
                if ("false".equals(mquerys.strQuerySQLvariable("select flg_usa_lot from inv_cat_art where cod_art =" + pf_cod_ser + ";"))) {
                    existencia = mquerys.doubleQuerySQLvariable("select ifnull((select his.exi_act_bod from inv_tra_his as his where his.cod_suc = " + cbean.getCod_suc_2() + " and his.cod_bod = " + cbean.getCod_bod() + " and his.cod_art = " + pf_cod_art + " and his.fec_tra <= now() order by his.fec_tra desc, his.ord_dia desc limit 1),0) as existencia;");
                    pf_lote = "";
                } else {
                    existencia = mquerys.doubleQuerySQLvariable("select ifnull((select his.exi_act_lot from inv_tra_his as his where his.cod_suc = " + cbean.getCod_suc_2() + " and his.cod_bod = " + cbean.getCod_bod() + " and his.cod_art = " + pf_cod_art + " and det_lot = '" + pf_lote + "' and his.fec_tra <= now() order by his.fec_tra desc, his.ord_dia desc limit 1),0) as existencia;");
                }
                pf_pre_sin_iva = mquerys.doubleQuerySQLvariable("select ifnull(pre_sin_iva,0.0) as presiniva from inv_cat_art where cod_art = " + pf_cod_art + ";");
                pf_iva = pf_pre_sin_iva * Double.valueOf(cbean.getPor_iva()) / 100.0000;
                pf_pre_con_iva = pf_pre_sin_iva + pf_iva;
                pf_det_can_con = 1.0;
            }

        }

    }

    public void onSelectCaja() {
        llenarTiposDocumentos();
        onSelectDocumento();
    }

    public void onSelectDocumento() {
        if (pag_cod_tip_doc != null && !pag_cod_tip_doc.equals("") && !pag_cod_tip_doc.equals("0")) {
            pag_num_doc = mquerys.strQuerySQLvariable("select cor_ult from fac_cat_caj_cnf_doc where cod_tip_doc = " + pag_cod_tip_doc + " and cod_caj =" + pag_cod_caj + ";");
            if (pag_num_doc == null) {
                pag_num_doc = "";
            }
            if (!pag_num_doc.equals("")) {
                if (Double.valueOf(pag_num_doc) >= mquerys.doubleQuerySQLvariable("select cor_ini from fac_cat_caj_cnf_doc where cod_tip_doc = " + pag_cod_tip_doc + " and cod_caj =" + pag_cod_caj + ";")
                        && Double.valueOf(pag_num_doc) < mquerys.doubleQuerySQLvariable("select cor_fin from fac_cat_caj_cnf_doc where cod_tip_doc = " + pag_cod_tip_doc + " and cod_caj =" + pag_cod_caj + ";")) {
                    pag_num_doc = mquerys.strQuerySQLvariable("select "
                            + "concat("
                            + "ifnull(pre_cor,''), "
                            + "case ifnull(cer_izq,0) "
                            + "when 0 then (select cnf1.cor_ult + 1 from fac_cat_caj_cnf_doc as cnf1 where cnf1.cod_tip_doc = mae.cod_tip_doc and cnf1.cod_caj = " + pag_cod_caj + " ) "
                            + "else lpad((select cnf2.cor_ult + 1 from fac_cat_caj_cnf_doc as cnf2 where cnf2.cod_tip_doc = mae.cod_tip_doc and cnf2.cod_caj = " + pag_cod_caj + " ), cer_izq,'0') "
                            + "end ) as cod "
                            + "FROM fac_cat_tip_doc as mae "
                            + "where mae.cod_tip_doc = " + pag_cod_tip_doc + ";");
                } else {
                    pag_num_doc = "";
                }
            }
        } else {
            pag_num_doc = "";
        }
    }

    public void onInsertDescuento() {
        if (det_sumatotal != null && pag_por_des != null) {
            pag_pag_tot = det_sumatotal - (det_sumatotal * pag_por_des / 100.0000);
            if (pag_pag_tot < 0.0) {
                pag_pag_tot = 0.0000;
            }
            onSelectTipoPago();
        }
    }

    public void onClickCondicionPago() {
        llenarTiposPagos();
        onSelectTipoPago();
    }

    public void onSelectTipoPago() {

        boo_efe = mquerys.strQuerySQLvariable("select case flg_efe when 'true' then 'false' when 'false' then 'true' end as flag from fac_cat_tip_pag where cod_tip_pag = " + pag_tip_pag + ";");
        boo_tar = mquerys.strQuerySQLvariable("select case flg_tar when 'true' then 'false' when 'false' then 'true' end as flag from fac_cat_tip_pag where cod_tip_pag = " + pag_tip_pag + ";");
        boo_che = mquerys.strQuerySQLvariable("select case flg_che when 'true' then 'false' when 'false' then 'true' end as flag from fac_cat_tip_pag where cod_tip_pag = " + pag_tip_pag + ";");

        if (boo_efe == null || boo_efe.equals("")) {
            boo_efe = "true";
        }
        if (boo_tar == null || boo_tar.equals("")) {
            boo_tar = "true";
        }
        if (boo_che == null || boo_che.equals("")) {
            boo_che = "true";
        }

        if (boo_efe.equals("false") && boo_tar.equals("true") && boo_che.equals("true")) {
            boo_mix = "true";
            pag_efe_tot = pag_pag_tot;
            pag_efe_pag_con = 0.0000;
            pag_efe_cam = 0.0000;

            pag_tar_tot = 0.0000;
            pag_tar_tip = "0";
            pag_tar_num = "";

            pag_che_tot = 0.0000;
            pag_che_ban = "0";
            pag_che_num = "";
        }

        if (boo_efe.equals("true") && boo_tar.equals("false") && boo_che.equals("true")) {
            boo_mix = "true";
            pag_efe_tot = 0.0000;
            pag_efe_pag_con = 0.0000;
            pag_efe_cam = 0.0000;

            pag_tar_tot = pag_pag_tot;
            pag_tar_tip = "0";
            pag_tar_num = "";

            pag_che_tot = 0.0000;
            pag_che_ban = "0";
            pag_che_num = "";
        }

        if (boo_efe.equals("true") && boo_tar.equals("true") && boo_che.equals("false")) {
            boo_mix = "true";
            pag_efe_tot = 0.0000;
            pag_efe_pag_con = 0.0000;
            pag_efe_cam = 0.0000;

            pag_tar_tot = 0.0000;
            pag_tar_tip = "0";
            pag_tar_num = "";

            pag_che_tot = pag_pag_tot;
            pag_che_ban = "0";
            pag_che_num = "";
        }

        if (boo_efe.equals("false") && boo_tar.equals("false") && boo_che.equals("false")) {
            boo_mix = "false";
            pag_efe_tot = pag_pag_tot;
            pag_efe_pag_con = 0.0000;
            pag_efe_cam = 0.0000;

            pag_tar_tot = 0.0000;
            pag_tar_tip = "0";
            pag_tar_num = "";

            pag_che_tot = 0.0000;
            pag_che_ban = "0";
            pag_che_num = "";
        }

        if (boo_efe.equals("true") && boo_tar.equals("true") && boo_che.equals("true")) {
            boo_mix = "true";
            pag_efe_tot = 0.0000;
            pag_efe_pag_con = 0.0000;
            pag_efe_cam = 0.0000;

            pag_tar_tot = 0.0000;
            pag_tar_tip = "0";
            pag_tar_num = "";

            pag_che_tot = 0.0000;
            pag_che_ban = "0";
            pag_che_num = "";
        }

    }

    public void onInsertPagaCon() {
        if (pag_efe_tot != null && pag_efe_pag_con != null) {
            pag_efe_cam = (pag_efe_tot - pag_efe_pag_con) * -1.0000;
            if (pag_efe_cam < 0.0) {
                pag_efe_cam = 0.0000;
            }
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

    //**************** Tickets *************************************************
    void imprimirFactura() {

        PrinterMatrix printer = new PrinterMatrix();

        Extenso e = new Extenso();

        e.setNumber(101.85);

        //Definir el tamanho del papel para la impresion  aca 25 lineas y 80 columnas
        printer.setOutSize(60, 80);
        //Imprimir * de la 2da linea a 25 en la columna 1;
        // printer.printCharAtLin(2, 25, 1, "*");
        //Imprimir * 1ra linea de la columa de 1 a 80
        printer.printCharAtCol(1, 1, 80, "=");
        //Imprimir Encabezado nombre del La EMpresa
        printer.printTextWrap(1, 2, 30, 80, "FACTURA DE VENTA");
        //printer.printTextWrap(linI, linE, colI, colE, null);
        printer.printTextWrap(2, 3, 1, 22, "Num. Boleta : " + "codigofactura"); //+ txtVentaNumeroFactura.getText());
        printer.printTextWrap(2, 3, 25, 55, "Fecha de Emision: " + "15/15/2015"); //dateFechaVenta.getDate());
        printer.printTextWrap(2, 3, 60, 80, "Hora: 12:22:51");
        printer.printTextWrap(3, 3, 1, 80, "Vendedor.  : " + " Nombre Vendedor"); //+ txtVentaIdVendedor.getText() + " - " + txtVentaNombreVendedor.getText());
        printer.printTextWrap(4, 4, 1, 80, "CLIENTE: " + "Nombre Cliente"); // txtVentaNombreCliente.getText());
        printer.printTextWrap(5, 5, 1, 80, "RUC/CI.: " + "DocDui");//txtVentaRucCliente.getText());
        printer.printTextWrap(6, 6, 1, 80, "DIRECCION: " + "Dirección cliente");
        printer.printCharAtCol(7, 1, 80, "=");
        printer.printTextWrap(7, 8, 1, 80, "Codigo          Descripcion                Cant.      P  P.Unit.      P.Total");
        printer.printCharAtCol(9, 1, 80, "-");
        int filas = 4; //tblVentas.getRowCount();

        for (int i = 0; i < filas; i++) {
            //printer.printTextWrap(9 + i, 10, 1, 80, tblVentas.getValueAt(i, 0).toString() + "|" + tblVentas.getValueAt(i, 1).toString() + "| " + tblVentas.getValueAt(i, 2).toString() + "| " + tblVentas.getValueAt(i, 3).toString() + "|" + tblVentas.getValueAt(i, 4).toString());
            printer.printTextWrap(9 + i, 10, 1, 80, "valor1" + "|" + "valor 2" + "| " + "valor 3" + "| " + "Valor 4" + "|" + "Valor 5");
        }

        if (filas > 15) {
            printer.printCharAtCol(filas + 1, 1, 80, "=");
            printer.printTextWrap(filas + 1, filas + 2, 1, 80, "TOTAL A PAGAR " + "Totalpago.0"); //txtVentaTotal.getText());
            printer.printCharAtCol(filas + 2, 1, 80, "=");
            printer.printTextWrap(filas + 2, filas + 3, 1, 80, "Esta boleta no tiene valor fiscal, solo para uso interno.: + Descripciones........");
        } else {
            printer.printCharAtCol(25, 1, 80, "=");
            printer.printTextWrap(26, 26, 1, 80, "TOTAL A PAGAR " + "Totalpago2.00"); //txtVentaTotal.getText());
            printer.printCharAtCol(27, 1, 80, "=");
            printer.printTextWrap(27, 28, 1, 80, "Esta boleta no tiene valor fiscal, solo para uso interno.: + Descripciones........");

        }
        printer.toFile("impresion11052017.txt");

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("impresion11052017.txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        if (inputStream == null) {
            return;
        }

        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc document = new SimpleDoc(inputStream, docFormat, null);

        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            try {
                printJob.print(document, attributeSet);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.err.println("No existen impresoras instaladas");
        }

        //inputStream.close();
    }

    //**************** Getters y Setters ***************************************
    public List<ListaCorta> getItems() {
        return items;
    }

    public void setItems(List<ListaCorta> items) {
        this.items = items;
    }

    public List<ListaCorta> getUnidadmedida() {
        return unidadmedida;
    }

    public void setUnidadmedida(List<ListaCorta> unidadmedida) {
        this.unidadmedida = unidadmedida;
    }

    public List<ListaCorta> getClientes() {
        return clientes;
    }

    public void setClientes(List<ListaCorta> clientes) {
        this.clientes = clientes;
    }

    public Cat_prefactura getCatprefactura() {
        return catprefactura;
    }

    public void setCatprefactura(Cat_prefactura catprefactura) {
        this.catprefactura = catprefactura;
    }

    public List<Cat_prefactura> getPrefactura() {
        return prefactura;
    }

    public void setPrefactura(List<Cat_prefactura> prefactura) {
        this.prefactura = prefactura;
    }

    public String getPf_cor_pre_fac() {
        return pf_cor_pre_fac;
    }

    public void setPf_cor_pre_fac(String pf_cor_pre_fac) {
        this.pf_cor_pre_fac = pf_cor_pre_fac;
    }

    public String getPf_cod_ser() {
        return pf_cod_ser;
    }

    public void setPf_cod_ser(String pf_cod_ser) {
        this.pf_cod_ser = pf_cod_ser;
    }

    public String getPf_cod_art() {
        return pf_cod_art;
    }

    public void setPf_cod_art(String pf_cod_art) {
        this.pf_cod_art = pf_cod_art;
    }

    public String getPf_nomitem() {
        return pf_nomitem;
    }

    public void setPf_nomitem(String pf_nomitem) {
        this.pf_nomitem = pf_nomitem;
    }

    public Double getPf_det_can() {
        return pf_det_can;
    }

    public void setPf_det_can(Double pf_det_can) {
        this.pf_det_can = pf_det_can;
    }

    public Double getPf_det_can_con() {
        return pf_det_can_con;
    }

    public void setPf_det_can_con(Double pf_det_can_con) {
        this.pf_det_can_con = pf_det_can_con;
    }

    public String getPf_cod_uni_med() {
        return pf_cod_uni_med;
    }

    public void setPf_cod_uni_med(String pf_cod_uni_med) {
        this.pf_cod_uni_med = pf_cod_uni_med;
    }

    public String getPf_nomunimed() {
        return pf_nomunimed;
    }

    public void setPf_nomunimed(String pf_nomunimed) {
        this.pf_nomunimed = pf_nomunimed;
    }

    public Double getPf_pre_sin_iva() {
        return pf_pre_sin_iva;
    }

    public void setPf_pre_sin_iva(Double pf_pre_sin_iva) {
        this.pf_pre_sin_iva = pf_pre_sin_iva;
    }

    public Double getPf_iva() {
        return pf_iva;
    }

    public void setPf_iva(Double pf_iva) {
        this.pf_iva = pf_iva;
    }

    public Double getPf_pre_con_iva() {
        return pf_pre_con_iva;
    }

    public void setPf_pre_con_iva(Double pf_pre_con_iva) {
        this.pf_pre_con_iva = pf_pre_con_iva;
    }

    public Double getPf_por_des() {
        return pf_por_des;
    }

    public void setPf_por_des(Double pf_por_des) {
        this.pf_por_des = pf_por_des;
    }

    public Double getPf_totalexe() {
        return pf_totalexe;
    }

    public void setPf_totalexe(Double pf_totalexe) {
        this.pf_totalexe = pf_totalexe;
    }

    public Double getPf_totalgrav() {
        return pf_totalgrav;
    }

    public void setPf_totalgrav(Double pf_totalgrav) {
        this.pf_totalgrav = pf_totalgrav;
    }

    public String getPf_cod_cli() {
        return pf_cod_cli;
    }

    public void setPf_cod_cli(String pf_cod_cli) {
        this.pf_cod_cli = pf_cod_cli;
    }

    public String getPf_cod_tra_cli() {
        return pf_cod_tra_cli;
    }

    public void setPf_cod_tra_cli(String pf_cod_tra_cli) {
        this.pf_cod_tra_cli = pf_cod_tra_cli;
    }

    public Double getExistencia() {
        return existencia;
    }

    public void setExistencia(Double existencia) {
        this.existencia = existencia;
    }

    public Double getDet_sumatotal() {
        return det_sumatotal;
    }

    public void setDet_sumatotal(Double det_sumatotal) {
        this.det_sumatotal = det_sumatotal;
    }

    public String getDet_flg_pro_ser() {
        return det_flg_pro_ser;
    }

    public void setDet_flg_pro_ser(String det_flg_pro_ser) {
        this.det_flg_pro_ser = det_flg_pro_ser;
    }

    public String getUnimedori() {
        return unimedori;
    }

    public void setUnimedori(String unimedori) {
        this.unimedori = unimedori;
    }

    public String getCodrefbus() {
        return codrefbus;
    }

    public void setCodrefbus(String codrefbus) {
        this.codrefbus = codrefbus;
    }

    public String getCodunimedori() {
        return codunimedori;
    }

    public void setCodunimedori(String codunimedori) {
        this.codunimedori = codunimedori;
    }

    public List<ListaCorta> getCajas() {
        return cajas;
    }

    public void setCajas(List<ListaCorta> cajas) {
        this.cajas = cajas;
    }

    public List<ListaCorta> getTiposdoc() {
        return tiposdoc;
    }

    public void setTiposdoc(List<ListaCorta> tiposdoc) {
        this.tiposdoc = tiposdoc;
    }

    public List<ListaCorta> getTipospag() {
        return tipospag;
    }

    public void setTipospag(List<ListaCorta> tipospag) {
        this.tipospag = tipospag;
    }

    public List<ListaCorta> getTipostar() {
        return tipostar;
    }

    public void setTipostar(List<ListaCorta> tipostar) {
        this.tipostar = tipostar;
    }

    public List<ListaCorta> getBancos() {
        return bancos;
    }

    public void setBancos(List<ListaCorta> bancos) {
        this.bancos = bancos;
    }

    public String getPag_cor_mae() {
        return pag_cor_mae;
    }

    public void setPag_cor_mae(String pag_cor_mae) {
        this.pag_cor_mae = pag_cor_mae;
    }

    public String getPag_cor_det() {
        return pag_cor_det;
    }

    public void setPag_cor_det(String pag_cor_det) {
        this.pag_cor_det = pag_cor_det;
    }

    public String getPag_cod_cli() {
        return pag_cod_cli;
    }

    public void setPag_cod_cli(String pag_cod_cli) {
        this.pag_cod_cli = pag_cod_cli;
    }

    public String getPag_cod_caj() {
        return pag_cod_caj;
    }

    public void setPag_cod_caj(String pag_cod_caj) {
        this.pag_cod_caj = pag_cod_caj;
    }

    public String getPag_cod_tip_doc() {
        return pag_cod_tip_doc;
    }

    public void setPag_cod_tip_doc(String pag_cod_tip_doc) {
        this.pag_cod_tip_doc = pag_cod_tip_doc;
    }

    public String getPag_num_doc() {
        return pag_num_doc;
    }

    public void setPag_num_doc(String pag_num_doc) {
        this.pag_num_doc = pag_num_doc;
    }

    public String getPag_con_pag() {
        return pag_con_pag;
    }

    public void setPag_con_pag(String pag_con_pag) {
        this.pag_con_pag = pag_con_pag;
    }

    public String getPag_tip_pag() {
        return pag_tip_pag;
    }

    public void setPag_tip_pag(String pag_tip_pag) {
        this.pag_tip_pag = pag_tip_pag;
    }

    public String getPag_tar_tip() {
        return pag_tar_tip;
    }

    public void setPag_tar_tip(String pag_tar_tip) {
        this.pag_tar_tip = pag_tar_tip;
    }

    public String getPag_tar_num() {
        return pag_tar_num;
    }

    public void setPag_tar_num(String pag_tar_num) {
        this.pag_tar_num = pag_tar_num;
    }

    public String getPag_che_ban() {
        return pag_che_ban;
    }

    public void setPag_che_ban(String pag_che_ban) {
        this.pag_che_ban = pag_che_ban;
    }

    public String getPag_che_num() {
        return pag_che_num;
    }

    public void setPag_che_num(String pag_che_num) {
        this.pag_che_num = pag_che_num;
    }

    public Double getPag_por_des() {
        return pag_por_des;
    }

    public void setPag_por_des(Double pag_por_des) {
        this.pag_por_des = pag_por_des;
    }

    public Double getPag_pag_tot() {
        return pag_pag_tot;
    }

    public void setPag_pag_tot(Double pag_pag_tot) {
        this.pag_pag_tot = pag_pag_tot;
    }

    public Double getPag_efe_tot() {
        return pag_efe_tot;
    }

    public void setPag_efe_tot(Double pag_efe_tot) {
        this.pag_efe_tot = pag_efe_tot;
    }

    public Double getPag_efe_pag_con() {
        return pag_efe_pag_con;
    }

    public void setPag_efe_pag_con(Double pag_efe_pag_con) {
        this.pag_efe_pag_con = pag_efe_pag_con;
    }

    public Double getPag_efe_cam() {
        return pag_efe_cam;
    }

    public void setPag_efe_cam(Double pag_efe_cam) {
        this.pag_efe_cam = pag_efe_cam;
    }

    public Double getPag_tar_tot() {
        return pag_tar_tot;
    }

    public void setPag_tar_tot(Double pag_tar_tot) {
        this.pag_tar_tot = pag_tar_tot;
    }

    public Double getPag_che_tot() {
        return pag_che_tot;
    }

    public void setPag_che_tot(Double pag_che_tot) {
        this.pag_che_tot = pag_che_tot;
    }

    public String getBoo_efe() {
        return boo_efe;
    }

    public void setBoo_efe(String boo_efe) {
        this.boo_efe = boo_efe;
    }

    public String getBoo_tar() {
        return boo_tar;
    }

    public void setBoo_tar(String boo_tar) {
        this.boo_tar = boo_tar;
    }

    public String getBoo_che() {
        return boo_che;
    }

    public void setBoo_che(String boo_che) {
        this.boo_che = boo_che;
    }

    public Date getMfechapag() {
        return mfechapag;
    }

    public void setMfechapag(Date mfechapag) {
        this.mfechapag = mfechapag;
    }

    public String getBoo_mix() {
        return boo_mix;
    }

    public void setBoo_mix(String boo_mix) {
        this.boo_mix = boo_mix;
    }

    public String getPf_lote() {
        return pf_lote;
    }

    public void setPf_lote(String pf_lote) {
        this.pf_lote = pf_lote;
    }

    public List<ListaCorta> getLotes() {
        return lotes;
    }

    public void setLotes(List<ListaCorta> lotes) {
        this.lotes = lotes;
    }

}
