package p_09_inventario;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;

import p_02_transactsql.Querys;
import p_03_autenticar.Login;
import p_06_utilitarios.*;

@Named
@ConversationScoped
public class xxxOLD implements Serializable {

    @Inject
    Login cbean;

    private List<ListaCorta> grupos;
    private List<ListaCorta> lineas;
    private List<ListaCorta> marcas;
    private List<ListaCorta> unidades;
    private List<ListaCorta> temperaturas;

    private List<ListaCorta> bodegas;

    private List<ListaCorta> ubicaciones;
    private List<ListaCorta> lotes;
    private List<ListaCorta> centroscosto;
    private List<ListaCorta> tiposdocint;
    private List<ListaCorta> tiposdocext;
    private List<ListaCorta> clientesproveedores;

    private Cat_articulos catarticulos;
    private List<Cat_articulos> articulos;
    private Cat_existencias catinventario;
    private List<Cat_existencias> inventario;

    private List<ListaCortaTransaccionExistencias> historico;

    private ListaDocumentosInternos listadocumentosinternos;
    private List<ListaDocumentosInternos> documentosint;
    private List<ListaDetalleDocumentosInternos> documentosintdetalle;

    private List<ListaCorta> docintext;

    private String usogruposlineas, usocodigoauto, usolote, usocosto, ingresarnuevosaldo, usofechaven;

    private String art_cod_art, art_cod_emp, art_cod_gru, art_cod_lin, art_cod_ref, art_nom_art, art_des_art,
            art_cod_mar, art_img_art, art_cod_uni_med, art_flg_exe, art_flg_per, art_flg_usa_lot, art_flg_imp, art_flg_exp, art_cod_tem, art_reg_san;

    private String ubi_cod_rel, ubi_cod_bod, ubi_des_ubi;

    private String cod_his, fec_tra, cod_art, cod_emp, cod_suc, cod_bod, des_ubi, det_lot, fec_ven, cod_cen_cos, cod_ord, flg_ent_sal;
    private Double det_can, can_ext, cos_tot, cos_uni, exi_ant_emp, exi_ant_suc, exi_ant_bod, exi_ant_ubi, exi_ant_lot, exi_act_emp, exi_act_suc, exi_act_bod, exi_act_ubi,
            exi_act_lot, cos_pro_emp, cos_pro_suc, cos_pro_cen_cos;
    private String det_obs, cod_tip_doc_int, doc_int, cod_tip_doc_ext, doc_ext, cor_doc_int, cod_cli, cod_pro, cod_usu;
    private String flg_anu, obs_anu;

    private Double exisantubi, exisactubi;
    private String nombrebodega, nombreunimed, unimedori, unimedsal, tituloclienteproveedor, nombrearticulo, index, nombremarca;

    private String bus_tip_doc_int, bus_doc_int, bus_flg_ent_sal, bus_flg_cer_doc, bus_limit, bus_anu_obs, bus_ope_ret, bus_fec_tra;

    private Date mfechatra, mfechaven;

    private static DefaultStreamedContent mimagen, mimagen2;

    private String mwidth, mheight;

    private SimpleDateFormat mformat, mformatsencillo;

    private Querys mquerys;

    private Errores err;

    public xxxOLD() {
    }

    //************* Inicio y cierre de ventana **********
    public void iniciarventana() {
        mformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        mformatsencillo = new SimpleDateFormat("dd/MM/yyyy");
        mquerys = new Querys();
        err = new Errores();

        //********** artículos ***********
        art_cod_art = "";
        art_cod_emp = cbean.getCod_emp();
        art_cod_gru = "0";
        art_cod_lin = "0";
        art_cod_ref = "";
        art_nom_art = "";
        art_des_art = "";
        art_cod_mar = "0";
        art_img_art = "";
        art_cod_tem = "0";
        art_reg_san = "";
        art_cod_uni_med = "0";
        art_flg_exe = "false";
        art_flg_per = "false";
        art_flg_usa_lot = "false";
        art_flg_imp = "false";
        art_flg_exp = "false";

        //******** historial ************
        mfechatra = Date.from(Instant.now());
        mfechaven = null;
        cod_his = "";
        fec_tra = mformat.format(mfechatra);
        cod_art = "";
        cod_emp = cbean.getCod_emp();
        cod_suc = cbean.getCod_suc_2();
        cod_bod = cbean.getCod_bod();
        des_ubi = "";
        det_lot = "";
        fec_ven = "";
        cod_cen_cos = "0";
        cod_ord = "";
        flg_ent_sal = "false";
        det_can = 0.0;
        can_ext = 0.0;
        cos_tot = 0.0;
        cos_uni = 0.0;
        exi_ant_emp = 0.0;
        exi_ant_suc = 0.0;
        exi_ant_bod = 0.0;
        exi_ant_ubi = 0.0;
        exi_ant_lot = 0.0;
        exi_act_emp = 0.0;
        exi_act_suc = 0.0;
        exi_act_bod = 0.0;
        exi_act_ubi = 0.0;
        exi_act_lot = 0.0;
        cos_pro_emp = 0.0;
        cos_pro_suc = 0.0;
        cos_pro_cen_cos = 0.0;
        det_obs = "";
        cod_tip_doc_int = "0";
        doc_int = "";
        cod_tip_doc_ext = "0";
        doc_ext = "";
        cor_doc_int = "";
        cod_cli = "0";
        cod_pro = "0";
        cod_usu = cbean.getCod_usu();
        flg_anu = "0";
        obs_anu = "";

        //********* Búsqueda **************
        bus_tip_doc_int = "";
        bus_doc_int = "";
        bus_flg_ent_sal = "";
        bus_flg_cer_doc = "";

        //******* Imágenes ****************
        mwidth = "170";
        mheight = "150";
        mimagen = null;
        mimagen2 = null;

        //****** Configuración global ******
        usogruposlineas = cbean.getFlg_uso_gru_lin_art();
        usocodigoauto = cbean.getFlg_aut_cod_art();
        usolote = cbean.getFlg_uso_lot_art();
        usocosto = cbean.getFlg_cos();

        //****** Configuración Local ******
        ingresarnuevosaldo = "true";
        index = "1";

        //****** Otros ********************
        exisantubi = 0.0;
        exisactubi = 0.0;
        nombrebodega = "";
        nombreunimed = "";
        unimedori = "0";
        unimedsal = "0";
        nombrearticulo = "Ninguno";
        tituloclienteproveedor = "Proveedor";
        usofechaven = usolote;
        nombremarca = "Marca";

        //******** Listados *****************
        grupos = new ArrayList<>();
        lineas = new ArrayList<>();
        marcas = new ArrayList<>();
        unidades = new ArrayList<>();
        temperaturas = new ArrayList<>();

        bodegas = new ArrayList<>();
        ubicaciones = new ArrayList<>();
        lotes = new ArrayList<>();
        centroscosto = new ArrayList<>();
        tiposdocint = new ArrayList<>();
        tiposdocext = new ArrayList<>();
        clientesproveedores = new ArrayList<>();

        catarticulos = new Cat_articulos();
        articulos = new ArrayList<>();
        catinventario = new Cat_existencias();
        inventario = new ArrayList<>();

        historico = new ArrayList<>();

        listadocumentosinternos = new ListaDocumentosInternos();
        documentosint = new ArrayList<>();
        documentosintdetalle = new ArrayList<>();

        docintext = new ArrayList<>();

        //******** Llenado de Catálogos ******
        llenarTemperaturas();
        llenarUnidadesMedida();
        llenarMarcas();
        llenarBodegas();
        llenarExistenciasActuales();

    }

    public void cerrarventana() {
        mformat = null;
        mformatsencillo = null;

        //********** artículos ***********
        art_cod_art = "";
        art_cod_emp = "";
        art_cod_gru = "0";
        art_cod_lin = "0";
        art_cod_ref = "";
        art_nom_art = "";
        art_des_art = "";
        art_cod_mar = "0";
        art_img_art = "";
        art_cod_uni_med = "0";
        art_cod_tem = "";
        art_reg_san = "";
        art_flg_exe = "false";
        art_flg_per = "false";
        art_flg_usa_lot = "false";
        art_flg_imp = "false";
        art_flg_exp = "false";

        //******** historial ************
        mfechatra = null;
        mfechaven = null;
        cod_his = "";
        fec_tra = "";
        cod_art = "";
        cod_emp = "";
        cod_suc = "";
        cod_bod = "";
        des_ubi = "";
        det_lot = "";
        fec_ven = "";
        cod_cen_cos = "";
        cod_ord = "";
        flg_ent_sal = "";
        det_can = 0.0;
        can_ext = 0.0;
        cos_tot = 0.0;
        cos_uni = 0.0;
        exi_ant_emp = 0.0;
        exi_ant_suc = 0.0;
        exi_ant_bod = 0.0;
        exi_ant_lot = 0.0;
        exi_act_emp = 0.0;
        exi_act_suc = 0.0;
        exi_act_bod = 0.0;
        exi_act_lot = 0.0;
        cos_pro_emp = 0.0;
        cos_pro_suc = 0.0;
        cos_pro_cen_cos = 0.0;
        det_obs = "";
        cod_tip_doc_int = "";
        doc_int = "";
        cod_tip_doc_ext = "";
        doc_ext = "";
        cor_doc_int = "";
        cod_cli = "";
        cod_pro = "";
        cod_usu = "";
        flg_anu = "";
        obs_anu = "";

        //********* Búsqueda **************
        bus_tip_doc_int = "";
        bus_doc_int = "";
        bus_flg_ent_sal = "";
        bus_flg_cer_doc = "";

        //******* Imágenes ****************
        mwidth = "170";
        mheight = "150";
        mimagen = null;
        mimagen2 = null;

        //****** Configuración global ******
        usogruposlineas = "";
        usocodigoauto = "";
        usolote = "";
        usocosto = "";

        //****** Otros ********************
        exisantubi = 0.0;
        exisactubi = 0.0;
        nombrebodega = "";
        nombreunimed = "";
        unimedori = "0";
        unimedsal = "0";
        nombrearticulo = "";
        tituloclienteproveedor = "";
        usofechaven = "";
        nombremarca = "";

        //******** Listados *****************
        grupos = null;
        lineas = null;
        marcas = null;
        unidades = null;
        temperaturas = null;

        bodegas = null;
        ubicaciones = null;
        lotes = null;
        centroscosto = null;
        tiposdocint = null;
        tiposdocext = null;
        clientesproveedores = null;

        catarticulos = null;
        articulos = null;
        catinventario = null;
        inventario = null;

        historico = null;

        listadocumentosinternos = null;
        documentosint = null;
        documentosintdetalle = null;

        docintext = null;

        err = null;
        mquerys = null;

    }

    //********************* Artículos ********************
    public void nuevoarticulo() {
        art_cod_art = "";
        art_cod_emp = cbean.getCod_emp();
        art_cod_gru = "0";
        art_cod_lin = "0";
        art_cod_ref = "";
        art_nom_art = "";
        art_des_art = "";
        art_cod_mar = "0";
        art_img_art = "";
        art_cod_uni_med = "0";
        art_cod_tem = "0";
        art_reg_san = "";
        art_flg_exe = "false";
        art_flg_per = "false";
        art_flg_usa_lot = "false";
        art_flg_imp = "false";
        art_flg_exp = "false";

        //******* Imágenes ****************
        mwidth = "170";
        mimagen = null;
        mimagen2 = null;

        catarticulos = null;
        //articulos.clear();

    }

    public void editararticulo() {
        if ("".equals(art_cod_emp) || "0".equals(art_cod_emp)) {
            addMessage("Editar", "Debe seleccionar una Empresa", 3);
            RequestContext.getCurrentInstance().update("frmInventarioArt");
        } else {
            llenarArticulos();
            RequestContext.getCurrentInstance().update("frmArtEdit");
            RequestContext.getCurrentInstance().execute("PF('wArtEdit').show()");
        }

    }

    public void cerrararticulo() {
        catarticulos = null;
        articulos.clear();
        RequestContext.getCurrentInstance().execute("PF('wvArtEdit').clearFilters()");
        RequestContext.getCurrentInstance().update("frmArtEdit");
    }

    public boolean validarguardararticulo() {
        boolean mvalidar = true;

        if ("".equals(art_cod_emp) || "0".equals(art_cod_emp)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar una Empresa en Configuración", 3);
        }
        if ("".equals(art_cod_uni_med) || "0".equals(art_cod_uni_med)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar una Unidad de Medida", 3);
        }

        if ("true".equals(cbean.getFlg_aut_cod_art())) {
            if ("".equals(art_cod_ref)) {
                mvalidar = false;
                addMessage("Validar Datos", "No se ha definido codificación automática de artículos en configuración. Debe ingresar un código de referencia", 3);
            } else if ("".equals(art_cod_art)) {
                if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_art) "
                        + "FROM inv_cat_art "
                        + "where upper(art_cod_ref)='" + art_cod_ref.toUpperCase() + "' "
                        + "and cod_emp=" + art_cod_emp + ";"))) {
                    mvalidar = false;
                    addMessage("Validar Datos", "Este Código de Articulo ya existe", 3);
                }

            } else if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_art) "
                    + "FROM inv_cat_art "
                    + "where upper(art_cod_ref)='" + art_cod_ref.toUpperCase() + "' "
                    + "and cod_emp=" + art_cod_emp + " "
                    + "and cod_art <> " + art_cod_art + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Este Código de Articulo ya existe", 3);
            }
        }

        if ("false".equals(cbean.getFlg_uso_gru_lin_art())) {
            if ("".equals(art_cod_gru) || "0".equals(art_cod_gru)) {
                mvalidar = false;
                addMessage("Validar Datos", "Se ha definido uso de Grupos y Líneas en configuración. Debe Seleccionar un Grupo", 3);
            }
            if ("".equals(art_cod_lin) || "0".equals(art_cod_lin)) {
                mvalidar = false;
                addMessage("Validar Datos", "Se ha definido uso de Grupos y Líneas en configuración. Debe Seleccionar una Línea", 3);
            }
        }

        if ("".equals(art_nom_art)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Ingresar un Nombre de Artículo", 3);
        }

        if (!"".equals(art_cod_art)) {
            if (!art_cod_uni_med.equals(mquerys.strQuerySQLvariable("select cod_uni_med from inv_cat_art where cod_art=" + art_cod_art + ";"))) {
                if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_art) from inv_tra_his_mos where cod_art = " + art_cod_art + ";"))) {
                    mvalidar = false;
                    addMessage("Validar Datos", "Este Artículo ya tiene movimientos de inventario con la unidad de medida anterior y no puede modificarse", 3);
                }
            }
        }
        return mvalidar;

    }

    public void guardararticulo() {
        String mQuery = "";
        if (validarguardararticulo()) {
            try {
                if ("".equals(art_cod_art)) {
                    mQuery = "select ifnull(max(cod_art),0)+1 as codigo from inv_cat_art;";
                    art_cod_art = mquerys.strQuerySQLvariable(mQuery);
                    if ("false".equals(cbean.getFlg_aut_cod_art())) {
                        if ("false".equals(cbean.getFlg_uso_gru_lin_art())) {
                            art_cod_ref = mquerys.strQuerySQLvariable("select concat(lpad('" + art_cod_gru + "',2,'0'),'-',lpad('" + art_cod_lin + "',2,'0'),'-',lpad('" + art_cod_art + "',4,'0')) as cod;");
                        } else {
                            art_cod_ref = mquerys.strQuerySQLvariable("select concat('A',lpad('" + art_cod_art + "',4,'0')) as cod;");
                        }
                    }
                    mQuery = "insert into inv_cat_art (cod_art,cod_emp,cod_gru,cod_lin,cod_ref,nom_art,des_art,"
                            + "cod_mar,img_art, cod_uni_med, cod_tem, reg_san, flg_exe, flg_per, flg_usa_lot,flg_imp,flg_exp,usu_mod,fec_mod,tip_mod) VALUES ("
                            + art_cod_art + "," + art_cod_emp + "," + art_cod_gru + "," + art_cod_lin
                            + ",'" + (art_cod_ref.replace("\\", "\\\\")).replace("'", "\\'") + "'"
                            + ",'" + (art_nom_art.replace("\\", "\\\\")).replace("'", "\\'") + "' "
                            + ",'" + (art_des_art.replace("\\", "\\\\")).replace("'", "\\'") + "' "
                            + "," + art_cod_mar
                            + ",null"
                            + "," + art_cod_uni_med
                            + "," + art_cod_tem
                            + ",'" + art_reg_san + "' "
                            + ",'" + art_flg_exe + "' "
                            + ",'" + art_flg_per + "' "
                            + ",'" + art_flg_usa_lot + "' "
                            + ",'" + art_flg_imp + "' "
                            + ",'" + art_flg_exp + "' "
                            + "," + cbean.getCod_usu() + " "
                            + ",now()"
                            + ",'AGREGAR NUEVO ARTÍCULO');";
                } else {
                    mQuery = "update inv_cat_art set "
                            + "cod_gru= " + art_cod_gru + ","
                            + "cod_lin= " + art_cod_lin + ","
                            + "cod_ref= '" + (art_cod_ref.replace("\\", "\\\\")).replace("'", "\\'") + "',"
                            + "nom_art = '" + (art_nom_art.replace("\\", "\\\\")).replace("'", "\\'") + "',"
                            + "des_art = '" + (art_des_art.replace("\\", "\\\\")).replace("'", "\\'") + "',"
                            + "cod_mar= " + art_cod_mar + ","
                            + "cod_uni_med= " + art_cod_uni_med + ","
                            + "cod_tem= " + art_cod_tem + ","
                            + "reg_san = '" + art_reg_san + "',"
                            + "flg_exe = '" + art_flg_exe + "',"
                            + "flg_per = '" + art_flg_per + "',"
                            + "flg_usa_lot = '" + art_flg_usa_lot + "',"
                            + "flg_imp = '" + art_flg_imp + "',"
                            + "flg_exp = '" + art_flg_exp + "',"
                            + "usu_mod = " + cbean.getCod_usu() + ","
                            + "fec_mod= now(), "
                            + "tip_mod= 'EDICIÓN NORMAL DE ARTÍCULO' "
                            + "where cod_art = " + art_cod_art + ";";
                }
                mquerys.dmlSQLvariable(mQuery);

                // ****************************  Inserta Imagen en tabla ************************************************************
                if (!"".equals(art_img_art)) {
                    try {
                        FileInputStream fis = null;
                        PreparedStatement ps = null;
                        File file = null;
                        try {
                            mquerys.Conectar().setAutoCommit(false);
                            file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(art_img_art));
                            fis = new FileInputStream(file);
                            mQuery = "update inv_cat_art set img_art = ? where cod_art=?;";
                            ps = mquerys.Conectar().prepareStatement(mQuery);
                            ps.setBinaryStream(1, fis, (int) file.length());
                            ps.setString(2, art_cod_art);
                            ps.executeUpdate();
                            mquerys.Conectar().commit();

                        } catch (Exception ex) {

                        } finally {
                            try {
                                ps.close();
                                fis.close();
                                if (file.exists()) {
                                    file.delete();
                                }
                                file = null;
                                mquerys.Desconectar();
                            } catch (Exception ex) {
                                err.auditarError("Man_inventario", "guardararticulo", "Error en cerrar flujos edición imagen de Artículo. " + ex.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
                            }
                        }

                    } catch (Exception e) {
                        err.auditarError("Man_inventario", "guardararticulo", "Error en editar imagen de guardar Artículo. " + (e.getMessage().replace("\\", "\\\\")).replace("'", "\\'"), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
                    }
                }

                addMessage("Guardar", "La Información ha sido almacenada con éxito.", 1);
                if ("false".equals(ingresarnuevosaldo)) {
                    iniciarEntradasSalidas();
                    cod_art = art_cod_art;
                    onSelectArticulo();
                    RequestContext.getCurrentInstance().execute("PF('wEntSalArt').show()");
                    index = "1";
                }
                nuevoarticulo();

            } catch (Exception e) {
                addMessage("Guardar", "Error en el almacenamiento de la información.", 3);
                err.auditarError("Man_inventario", "guardararticulo", "Error en guardar Artículo. " + (e.getMessage().replace("\\", "\\\\")).replace("'", "\\'"), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }

        }
    }

    public boolean validareliminararticulo() {
        boolean mvalidar = true;

        if ("".equals(art_cod_emp) || "0".equals(art_cod_emp) || "".equals(art_cod_art)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un registro en Editar Artículo", 3);
        } else {

            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_art) "
                    + "FROM fac_tra_cli "
                    + "where cod_art=" + art_cod_art + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Este artículo tiene registros relacionados en Transacciones de Cliente y no puede ser eliminado", 3);
            }

            if (!"0".equals(mquerys.strQuerySQLvariable("select count(cod_art) "
                    + "FROM iniv_tra_his_mov "
                    + "where cod_art=" + art_cod_art + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Este artículo tiene registros relacionados en Historial de Movimientos y no puede ser eliminado", 3);
            }
        }
        return mvalidar;

    }

    public void eliminararticulo() {
        String mQuery = "";
        if (validareliminararticulo()) {
            try {
                mQuery = "delete from inv_cat_art_rel_ubi where cod_art=" + art_cod_art + ";";
                mquerys.dmlSQLvariable(mQuery);
                mQuery = "delete from inv_cat_art where cod_art=" + art_cod_art + ";";
                mquerys.dmlSQLvariable(mQuery);
                addMessage("Eliminar Mantenimiento", "Información Eliminada con éxito.", 1);
            } catch (Exception e) {
                addMessage("Eliminar", "Error al momento de Eliminar la información. " + e.getMessage(), 3);
                err.auditarError("Man_inventario", "eliminararticulo", "Error en eliminar artículo. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }
            nuevoarticulo();

        }

    }

    public void ubicacionesarticulo() {
        if ("".equals(art_cod_emp) || "0".equals(art_cod_emp) || "".equals(art_cod_art)) {
            addMessage("Editar", "Debe seleccionar un Artículo en Editar", 3);
        } else {
            ubi_cod_bod = mquerys.strQuerySQLvariable("select cod_bod  FROM inv_cat_art_rel_ubi where cod_suc = " + cod_suc + " and cod_art = " + art_cod_art + " limit 1;");
            if (ubi_cod_bod == null) {
                ubi_cod_bod = "";
            }
            if ("".equals(ubi_cod_bod)) {
                ubi_cod_bod = "0";
            }
            ubi_des_ubi = mquerys.strQuerySQLvariable("select des_ubi  FROM inv_cat_art_rel_ubi where cod_suc = " + cod_suc + " and cod_art = " + art_cod_art + " and cod_bod = " + ubi_cod_bod + ";");
            if (ubi_des_ubi == null) {
                ubi_des_ubi = "";
            }
            RequestContext.getCurrentInstance().update("frmUbicarArt");
            RequestContext.getCurrentInstance().execute("PF('wUbicarArt').show()");
        }

    }

    public void upload(FileUploadEvent event) {
        try {
            Random rnd = new Random();
            String prefijo = String.valueOf(((int) (rnd.nextDouble() * 100)) + ((int) (rnd.nextDouble() * 100)) * ((int) (rnd.nextDouble() * 100)));
            copyFile("art_temp_" + prefijo + ".jpg", event.getFile().getInputstream());

            mimagen = new DefaultStreamedContent(event.getFile().getInputstream(), "image/jpeg");
            mheight = "170";

        } catch (Exception e) {
            addMessage("Cargar Imagen", "El archivo " + event.getFile().getFileName() + " no ha podido ser cargado. " + e.getMessage(), 3);
            err.auditarError("Man_inventario", "upload", "Error en Cargar Imagen Artículo. " + e.getMessage(), "", cbean.getCod_usu());
        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {
            String destination = "";
            File mIMGFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/temp/config.xml"));
            art_img_art = "/resources/images/temp/" + fileName;

            destination = mIMGFile.getPath().replace("config.xml", "");

            //Verifica que no exista otro archivo con el mismo nombre.
            try {
                File mfile = new File(destination + fileName);
                if (mfile.exists()) {
                    mfile.delete();
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName.toLowerCase()));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();

        } catch (IOException e) {
            art_img_art = "";
            addMessage("Cargar Imagen", "Imagen " + fileName + " no ha podido ser copiada. " + e.getMessage(), 3);
            err.auditarError("Man_inventario", "copyFile", "Error en Copiar Archivo Imagen Artículo. " + e.getMessage(), "", cbean.getCod_usu());

        }
    }

    public void recuperarimagen() {
        String mQuery = "";

        mimagen = null;
        try {
            mQuery = "select img_art from inv_cat_art where cod_art=" + art_cod_art + ";";
            Blob miBlob = mquerys.blobQuerySQLvariable(mQuery);
            if (miBlob != null) {
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 170.0);
                img = null;

                data = null;
            } else {
                mQuery = "select img_art from inv_cat_art where cod_art=0;";
                miBlob = mquerys.blobQuerySQLvariable(mQuery);
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 170.0);
                img = null;

                data = null;
            }
        } catch (Exception e) {
            err.auditarError("Man_inventario", "recuperarimagen", "Error en recuperar imagen Artículo. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }

    }

    //******************* Ubicaciones ***********************
    public void cerrarubicaciones() {
        ubi_cod_bod = "0";
        ubi_des_ubi = "";
    }

    public boolean validarubicacion() {
        boolean mvalidar = true;

        if ("".equals(cod_suc) || "0".equals(cod_suc)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar una Sucursal en Configuración", 3);
        }
        if ("".equals(ubi_cod_bod) || "0".equals(ubi_cod_bod)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar una Bodega", 3);
        }
        if ("".equals(ubi_des_ubi)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Ingresar o Seleccionar una Ubicación", 3);
        }

        return mvalidar;

    }

    public void guardarubicacion() {
        String mQuery = "";
        if (validarubicacion()) {
            try {
                if ("0".equals(mquerys.strQuerySQLvariable("select count(cod_art) from inv_cat_art_rel_ubi "
                        + "where cod_suc=" + cod_suc + " and cod_art=" + art_cod_art + " "
                        + "and cod_bod =" + ubi_cod_bod + ";"))) {
                    mQuery = "select ifnull(max(cod_rel),0)+1 as codigo from inv_cat_art_rel_ubi;";
                    ubi_cod_rel = mquerys.strQuerySQLvariable(mQuery);
                    mQuery = "insert into inv_cat_art_rel_ubi (cod_rel,cod_suc,cod_art,cod_bod,des_ubi) VALUES ("
                            + ubi_cod_rel + "," + cod_suc + "," + art_cod_art + "," + ubi_cod_bod
                            + ",'" + (ubi_des_ubi.replace("\\", "\\\\")).replace("'", "\\'") + "');";
                } else {
                    mQuery = "update inv_cat_art_rel_ubi set "
                            + "des_ubi = '" + (ubi_des_ubi.replace("\\", "\\\\")).replace("'", "\\'") + "' "
                            + "where cod_suc = " + cod_suc + " "
                            + "and cod_art =" + art_cod_art + " "
                            + "and cod_bod =" + ubi_cod_bod + ";";
                }

                mquerys.dmlSQLvariable(mQuery);

                addMessage("Guardar", "La Información ha sido almacenada con éxito", 1);

            } catch (Exception e) {
                addMessage("Guardar", "Error en el almacenamiento de la información", 3);
                err.auditarError("Man_inventario", "guardarubicacion", "Error en guardar ubicación. " + (e.getMessage().replace("\\", "\\\\")).replace("'", "\\'"), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }

        }
    }

    //******************* Existencias ***********************
    public void iniciarDetallePorArticulo() {

    }

    public void cerrarDetallePorArticulo() {

    }

    public void iniciarAjusteManual() {

    }

    public void cerrarAjusteManual() {

    }

    public void iniciarEntradasSalidas() {
        boolean mvalidar = true;
        if ("".equals(cod_emp) || "0".equals(cod_emp)) {
            mvalidar = false;
            addMessage("Entradas Salidas", "Debe seleccionar una Empresa en Configuración", 3);
        }
        if ("".equals(cod_suc) || "0".equals(cod_suc)) {
            mvalidar = false;
            addMessage("Entradas Salidas", "Debe seleccionar una Sucursal en Configuración", 3);
        }
        if ("".equals(cod_bod) || "0".equals(cod_bod)) {
            mvalidar = false;
            addMessage("Entradas Salidas", "Debe seleccionar una Bodega", 3);
        }

        if (mvalidar) {

            //******** historial ************
            mfechaven = null;
            cod_his = "";
            cod_art = "0";
            des_ubi = "";
            det_lot = "";
            fec_ven = "";
            cod_cen_cos = "0";
            cod_ord = "";
            flg_ent_sal = "false";
            det_can = 0.0;
            can_ext = 0.0;
            cos_tot = 0.0;
            cos_uni = 0.0;
            exi_ant_emp = 0.0;
            exi_ant_suc = 0.0;
            exi_ant_bod = 0.0;
            exi_ant_ubi = 0.0;
            exi_ant_lot = 0.0;
            exi_act_emp = 0.0;
            exi_act_suc = 0.0;
            exi_act_bod = 0.0;
            exi_act_ubi = 0.0;
            exi_act_lot = 0.0;
            cos_pro_emp = 0.0;
            cos_pro_suc = 0.0;
            cos_pro_cen_cos = 0.0;
            det_obs = "";
            cod_tip_doc_int = "0";
            doc_int = "";
            cod_tip_doc_ext = "0";
            doc_ext = "";
            cor_doc_int = "";
            cod_cli = "0";
            cod_pro = "0";
            cod_usu = cbean.getCod_usu();
            flg_anu = "0";
            obs_anu = "";

            //******* Imágenes ****************
            mheight = "150";
            mimagen2 = null;

            //******** Otros *****************
            exisantubi = 0.0;
            exisactubi = 0.0;
            nombrebodega = mquerys.strQuerySQLvariable("select nom_bod from inv_cat_bod where cod_bod=" + cod_bod + ";");
            nombreunimed = "";
            unimedori = "0";
            unimedsal = "0";
            nombrearticulo = "Ninguno";
            tituloclienteproveedor = "Proveedor";
            usofechaven = "false";
            nombremarca = "Marca";

            //******** Llenar Catálogos ******
            llenarDocumentosInt();
            llenarDocumentosExt();
            //llenarUbicaciones();
            llenarCentrosCosto();
            llenarClientesProveedores();
            llenarArticulos();
            llenarUnidadesMedida();

            RequestContext.getCurrentInstance().update("frmEntSalArt");
            RequestContext.getCurrentInstance().execute("PF('wEntSalArt').show()");
        } else {
            RequestContext.getCurrentInstance().update("frmInventarioArt");
        }

    }

    public void iniciarMaxMin() {

    }

    public void cerrarMaxMin() {

    }

    //**************** Entradas y Salidas *******************
    public void cerrarEntradasSalidas() {
        exisantubi = 0.0;
        exisactubi = 0.0;
        nombrebodega = "";
        nombreunimed = "";
        nombrearticulo = "";
        unimedori = "0";
        unimedsal = "0";
        tituloclienteproveedor = "";
        usofechaven = "";
        llenarExistenciasActuales();
        RequestContext.getCurrentInstance().execute("PF('wBuscarDocInvArt').hide()");
    }

    public void guardarSeguirEntradaSalida() {
        boolean mbool;
        if (validarEntradaSalida()) {
            mbool = guardarMovimiento();
        } else {
            mbool = false;
        }

        if (mbool) {
            addMessage("Guardar", "Información Almacenada Exitosamente", 1);
        } else {
            addMessage("Guardar", "Error al Guardar la Información", 3);
        }
    }

    public void guardarNuevoEntradaSalida() {
        boolean mbool;
        if (validarEntradaSalida()) {
            mbool = guardarMovimiento();
        } else {
            mbool = false;
        }

        if (mbool) {

            //*********** Aumenta correlativo documento interno *****************
            mquerys.dmlSQLvariable("update gen_cat_tip_doc_int set ult_cor = (ult_cor + 1) where cod_tip_doc=" + cod_tip_doc_int + ";");

            //******** historial ************
            mfechaven = null;
            cod_his = "";
            cod_art = "0";
            des_ubi = "";
            det_lot = "";
            fec_ven = "";
            cod_cen_cos = "0";
            cod_ord = "";
            flg_ent_sal = "false";
            det_can = 0.0;
            can_ext = 0.0;
            cos_tot = 0.0;
            cos_uni = 0.0;
            exi_ant_emp = 0.0;
            exi_ant_suc = 0.0;
            exi_ant_bod = 0.0;
            exi_ant_ubi = 0.0;
            exi_ant_lot = 0.0;
            exi_act_emp = 0.0;
            exi_act_suc = 0.0;
            exi_act_bod = 0.0;
            exi_act_ubi = 0.0;
            exi_act_lot = 0.0;
            cos_pro_emp = 0.0;
            cos_pro_suc = 0.0;
            cos_pro_cen_cos = 0.0;
            det_obs = "";
            cod_tip_doc_int = "0";
            doc_int = "";
            cod_tip_doc_ext = "0";
            doc_ext = "";
            cor_doc_int = "";
            cod_cli = "0";
            cod_pro = "0";
            cod_usu = cbean.getCod_usu();
            flg_anu = "0";
            obs_anu = "";

            //******* Imágenes ****************
            mheight = "150";
            mimagen2 = null;

            //******** Otros *****************
            exisantubi = 0.0;
            exisactubi = 0.0;
            nombreunimed = "";
            unimedori = "0";
            unimedsal = "0";
            nombrearticulo = "Ninguno";
            tituloclienteproveedor = "Proveedor";
            usofechaven = "false";

            //******** Llenar Catálogos ******
            llenarDocumentosInt();
            llenarDocumentosExt();
            //llenarUbicaciones();
            llenarCentrosCosto();
            llenarClientesProveedores();
            llenarArticulos();
            llenarUnidadesMedida();

            addMessage("Guardar", "Información Almacenada Exitosamente", 1);
        } else {
            addMessage("Guardar", "No se ha podido Guardar la Información", 3);
        }

    }

    public boolean validarEntradaSalida() {
        boolean mvalidar = true;
        if (des_ubi == null) {
            des_ubi = "";
        }
        if (det_lot == null) {
            det_lot = "";
        }
        if (unimedori == null) {
            unimedori = "0";
        }
        if (unimedsal == null) {
            unimedsal = "0";
        }
        if ("".equals(cod_suc) || "0".equals(cod_suc)) {
            addMessage("Validar Datos", "Debe Seleccionar una Sucursal", 3);
            return false;
        }
        if ("".equals(cod_bod) || "0".equals(cod_bod)) {
            addMessage("Validar Datos", "Debe Seleccionar una Bodega", 3);
            return false;
        }
        if ("".equals(des_ubi.trim())) {
            addMessage("Validar Datos", "Debe Seleccionar una Ubicación", 3);
            return false;
        }
        if ("".equals(cod_tip_doc_int) || "0".equals(art_cod_emp) || "".equals(doc_int)) {
            addMessage("Validar Datos", "Debe Seleccionar una Documento Interno", 3);
            return false;
        }
        if ("".equals(cod_art) || "0".equals(cod_art)) {
            addMessage("Validar Datos", "Debe Seleccionar un Artículo", 3);
            return false;
        }
        if (!"".equals(fec_ven) && "".equals(det_lot)) {
            addMessage("Validar Datos", "No puede Ingresar una Fecha de Vencimiento para un Lote Vacío", 3);
            return false;
        }
        if (det_can <= 0.0) {
            addMessage("Validar Datos", "Debe Ingresar una Cantidad Mayor que Cero", 3);
            return false;
        }
        if ("true".equals(mquerys.strQuerySQLvariable("select flg_usa_lot from inv_cat_art where cod_art=" + cod_art + ";"))) {
            if ("".equals(det_lot)) {
                addMessage("Validar Datos", "Este Artículo ha sido definido para ser controlado por medio de Lotes. Debe Ingresar o Seleccionar un Lote", 3);
                return false;
            }
        }

        if (!"0".equals(mquerys.strQuerySQLvariable("select count(flg_cer_doc) from inv_tra_his_mov where cod_suc=" + cod_suc + " and doc_int='" + doc_int + "' limit 1;"))) {
            if (!"0".equals(mquerys.strQuerySQLvariable("select flg_cer_doc from inv_tra_his_mov where cod_suc=" + cod_suc + " and doc_int='" + doc_int + "' limit 1;"))) {
                addMessage("Validar Datos", "El Documento Interno " + doc_int + " no esta disponible para edición", 3);
                return false;
            }
        }
        // ********************** Valida Fecha de Transacción de Documento *************************************************
        String mFechaVal = mquerys.strQuerySQLvariable("select date_format(fec_tra,'%d/%m/%Y') as fec from inv_tra_his_mov where cod_emp =" + cod_emp + " and cod_tip_doc_int=" + cod_tip_doc_int + " and doc_int='" + doc_int + "' limit 1;");
        if (mFechaVal == null) {
            mFechaVal = "";
        }
        if ("00/00/0000".equals(mFechaVal)) {
            mFechaVal = "";
        }
        if (!"".equals(mFechaVal)) {
            if (!mFechaVal.equals(fec_tra.substring(0, 11).trim())) {
                addMessage("Validar Datos", "El Documento Interno al que se hace referencia posee fecha de Transacción distinta a la ingresada", 3);
                return false;
            }
        }

        //**************** Conversión de Unidades ***************************************************************************
        Double mFactor = 0.0;
        if ("".equals(unimedsal) || "0".equals(unimedsal)) {
            addMessage("Validar Datos", "Debe Seleccionar una Unidad de Medida", 3);
            return false;
        } else if (!unimedori.equals(unimedsal)) {
            try {
                mFactor = mquerys.doubleQuerySQLvariable("select ifnull(can_uni_ini/can_uni_fin,0.0) as factor "
                        + "from gen_cat_uni_med_rel "
                        + "where cod_uni_ini = " + unimedori + " "
                        + "and cod_uni_fin = " + unimedsal + ";");
            } catch (Exception e) {
                mFactor = 0.0;
            }
            if (mFactor == null) {
                mFactor = 0.0;
            }
            if (mFactor == 0.0) {
                try {
                    mFactor = mquerys.doubleQuerySQLvariable("select ifnull(can_uni_fin/can_uni_ini,0.0) as factor "
                            + "from gen_cat_uni_med_rel "
                            + "where cod_uni_fin = " + unimedori + " "
                            + "and cod_uni_ini = " + unimedsal + ";");
                } catch (Exception e) {
                    mFactor = 0.0;
                }
            }
            if (mFactor == null) {
                mFactor = 0.0;
            }
            if (mFactor == 0.0) {
                addMessage("Validar Datos", "No Existe relación Definida Válida entre Unidades de Medida Seleccionadas", 3);
                return false;
            }
        } else {
            mFactor = 1.0;
        }

        //*************************** Verificación de Existencias y Puntos Máximos *************************************************
        Double mExi = 0.0;
        try {
            mExi = Double.valueOf(mquerys.strQuerySQLvariable("select ifnull(exi_act_ubi,0.0) "
                    + "from inv_tra_his_mov "
                    + "where fec_tra <= str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                    + "and cod_bod= " + cod_bod + " "
                    + "and des_ubi='" + des_ubi + "' "
                    + "and cod_art =" + cod_art + " "
                    + "and flg_anu=0 "
                    + "order by fec_tra desc, cod_ord desc limit 1;"));
        } catch (Exception e) {
            mExi = 0.0;
        }
        if ("true".equals(flg_ent_sal)) {
            if (det_can * mFactor > mExi) {
                addMessage("Validar Datos", "La Cantidad Solicitada Sobrepasa las Existencias", 3);
                return false;
            }

        } else {
            Double mMax = 0.0;
            try {
                mMax = Double.valueOf(mquerys.strQuerySQLvariable("select ifnull(pun_max,0.0) "
                        + "from inv_cat_pun_ale "
                        + "where cod_bod = " + cod_bod + " "
                        + "and cod_art = " + cod_art
                        + ";"));
            } catch (Exception e) {
                mMax = 0.0;
            }
            if (mMax > 0.0) {
                if (mExi + det_can * mFactor + can_ext * mFactor > mMax) {
                    addMessage("Validar Datos", "La Cantidad Ingresada Sobrepasa el Máximo permitido en esta Bodega", 3);
                    return false;
                }
            }

        }

        if (mvalidar) {
            det_can = det_can * mFactor;
            can_ext = can_ext * mFactor;
        }

        return mvalidar;
    }

    public boolean guardarMovimiento() {
        boolean mbool = true;
        String mQuery = "";
        ResultSet resvariable;

        try {
            //********************************** Codificación de datos ****************************************
            //código correlativo existencia histórica de artículo
            cod_his = mquerys.strQuerySQLvariable("select ifnull(max(cod_his),0)+1 "
                    + "as codigo from inv_tra_his_mov;");

            //Código correlativo diario existencia histórica de artículo
            cod_ord = mquerys.strQuerySQLvariable("select ifnull(max(cod_ord),0)+1 "
                    + "as cordia from inv_tra_his_mov "
                    + "where "
                    + "cod_art=" + cod_art + " "
                    + "and fec_tra = STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                    + "and cod_suc = " + cod_suc + " "
                    + ";");

            cos_uni = cos_tot / det_can;

            //Costos promedios 
            if ("1".equals(cod_his)) {
                cos_pro_emp = cos_tot / (det_can + can_ext);
                exi_ant_emp = 0.0;
                cos_pro_suc = cos_tot / (det_can + can_ext);
                exi_ant_suc = 0.0;

                exi_ant_bod = 0.0;
                exi_ant_ubi = 0.0;
                exi_ant_lot = 0.0;
            } else {
                //************* Costo Promedio y Existencia anteriores por empresa  **************
                if ("false".equals(flg_ent_sal)) {
                    cos_pro_emp = mquerys.doubleQuerySQLvariable("select (ifnull((exi_act_emp * cos_pro_emp),0) + "
                            + ((det_can + can_ext) * cos_uni) + ")"
                            + "/(IFNULL(exi_act_emp,0)+" + (det_can + can_ext) + ") as Cpromedio "
                            + "from inv_tra_his_mov "
                            + "where "
                            + "cod_art=" + cod_art + " "
                            + "and  fec_tra <= STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and flg_anu = 0 "
                            + "order by fec_tra desc,"
                            + "cod_ord desc "
                            + "limit 1;");

                } else {
                    cos_pro_emp = mquerys.doubleQuerySQLvariable("select ifnull(cos_pro_emp,0) as cospro "
                            + "from inv_tra_his_mov "
                            + "where "
                            + "cod_art=" + cod_art + " "
                            + "and  fec_tra <= STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and flg_anu = 0 "
                            + "order by fec_tra desc,"
                            + "cod_ord desc "
                            + "limit 1;");

                }
                //Existencia Anterior por empresa
                exi_ant_emp = mquerys.doubleQuerySQLvariable("select ifnull(exi_act_emp,0) as exisant "
                        + "from inv_tra_his_mov "
                        + "where "
                        + "cod_art=" + cod_art + " "
                        + "and  fec_tra <= STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                        + "and flg_anu = 0 "
                        + "order by fec_tra desc,"
                        + "cod_ord desc "
                        + "limit 1;");

                //************* Verifica si existen registros históricos de la sucursal actual **************
                if ("0".equals(mquerys.strQuerySQLvariable("select count(cod_his) as codigo from inv_tra_his_mov where cod_suc =" + cod_suc + ";"))) {
                    cos_pro_suc = cos_tot / (det_can + can_ext);
                    exi_ant_suc = 0.0;

                    exi_ant_bod = 0.0;
                    exi_ant_ubi = 0.0;
                    exi_ant_lot = 0.0;
                } else {
                    if ("false".equals(flg_ent_sal)) {
                        cos_pro_suc = mquerys.doubleQuerySQLvariable("select (ifnull((exi_act_suc * cos_pro_suc),0) + "
                                + ((det_can + can_ext) * cos_uni) + ")"
                                + "/(IFNULL(exi_act_suc,0)+" + (det_can + can_ext) + ") as Cpromedio "
                                + "from inv_tra_his_mov "
                                + "where "
                                + "cod_art=" + cod_art + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                                + "and flg_anu = 0 "
                                + "and cod_suc = " + cod_suc + " "
                                + "order by fec_tra desc,"
                                + "cod_ord desc "
                                + "limit 1;");
                    } else {
                        cos_pro_suc = mquerys.doubleQuerySQLvariable("select ifnull(cos_pro_suc,0) as cospro "
                                + "from inv_tra_his_mov "
                                + "where "
                                + "cod_art=" + cod_art + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                                + "and flg_anu = 0 "
                                + "and cod_suc = " + cod_suc + " "
                                + "order by fec_tra desc,"
                                + "cod_ord desc "
                                + "limit 1;");
                    }
                    //Existencia Anterior por Sucursal
                    exi_ant_suc = mquerys.doubleQuerySQLvariable("select ifnull(exi_act_suc,0) as exisant "
                            + "from inv_tra_his_mov "
                            + "where "
                            + "cod_art=" + cod_art + " "
                            + "and  fec_tra <= STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and flg_anu = 0 "
                            + "and cod_suc = " + cod_suc + " "
                            + "order by fec_tra desc,"
                            + "cod_ord desc "
                            + "limit 1;");
                    //Existencia Anterior por Bodega
                    exi_ant_bod = mquerys.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                            + "from inv_tra_his_mov "
                            + "where "
                            + "cod_art=" + cod_art + " "
                            + "and  fec_tra <= STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and flg_anu = 0 "
                            + "and cod_suc = " + cod_suc + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "order by fec_tra desc,"
                            + "cod_ord desc "
                            + "limit 1;");
                    //Existencia Anterior por Ubicación
                    exi_ant_ubi = mquerys.doubleQuerySQLvariable("select ifnull(exi_act_ubi,0) as exisant "
                            + "from inv_tra_his_mov "
                            + "where "
                            + "cod_art=" + cod_art + " "
                            + "and  fec_tra <= STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and flg_anu = 0 "
                            + "and cod_suc = " + cod_suc + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and des_ubi = '" + des_ubi + "' "
                            + "order by fec_tra desc,"
                            + "cod_ord desc "
                            + "limit 1;");
                    //Existencia Anterior por Lote
                    if ("".equals(det_lot)) {
                        exi_ant_lot = 0.0;
                    } else {
                        exi_ant_lot = mquerys.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                                + "from inv_tra_his_mov "
                                + "where "
                                + "cod_art=" + cod_art + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                                + "and flg_anu = 0 "
                                + "and det_lot = '" + det_lot + "' "
                                + "order by fec_tra desc,"
                                + "cod_ord desc "
                                + "limit 1;");
                    }
                }
            }

            //******************* Tratamiento de Existencias **************************************
            if ("false".equals(flg_ent_sal)) {
                exi_act_emp = exi_ant_emp + det_can + can_ext;
                exi_act_suc = exi_ant_suc + det_can + can_ext;
                exi_act_bod = exi_ant_bod + det_can + can_ext;
                exi_act_ubi = exi_ant_ubi + det_can + can_ext;
                exi_act_lot = exi_ant_lot + det_can + can_ext;
            } else {
                exi_act_emp = exi_ant_emp - (det_can + can_ext);
                exi_act_suc = exi_ant_suc - (det_can + can_ext);
                exi_act_bod = exi_ant_bod - (det_can + can_ext);
                exi_act_ubi = exi_ant_ubi - (det_can + can_ext);
                if ("".equals(det_lot)) {
                    exi_act_lot = 0.0;
                } else {
                    exi_act_lot = exi_ant_lot - det_can;
                }

                //*********** Valida que las Existencias no sean menor que Cero ******************
                if (exi_act_emp < 0.0) {
                    exi_act_emp = 0.0;
                }
                if (exi_act_suc < 0.0) {
                    exi_act_suc = 0.0;
                }
                if (exi_act_bod < 0.0) {
                    exi_act_bod = 0.0;
                }
                if (exi_act_ubi < 0.0) {
                    exi_act_ubi = 0.0;
                }
                if (exi_act_lot < 0.0) {
                    exi_act_lot = 0.0;
                }

            }
            //********************** Inserta Registro en transaccional histórica ***************
            cor_doc_int = mquerys.strQuerySQLvariable("select ifnull(max(cor_doc_int),0)+1 as cod from inv_tra_his_mov where cod_emp=" + cod_emp + " and cod_tip_doc_int=" + cod_tip_doc_int + " and doc_int ='" + doc_int + "';");
            if ("".equals(fec_ven)) {
                fec_ven = "null";
            } else {
                fec_ven = "str_to_date('" + fec_ven + "','%d/%m/%Y')";
            }
            if ("false".equals(flg_ent_sal)) {
                cod_cli = "0";
            } else {
                cod_cli = cod_pro;
                cod_pro = "0";
            }

            mQuery = "update inv_tra_his_mov set flg_ult = 0 where cod_art=" + cod_art + " and cod_bod =" + cod_bod + ";";
            mquerys.dmlSQLvariable(mQuery);

            mQuery = "insert into inv_tra_his_mov (cod_his,fec_tra,cod_art,cod_emp,cod_suc,cod_bod,des_ubi,det_lot "
                    + ",fec_ven,cod_cen_cos,cod_ord,flg_ent_sal,det_can,can_ext,cos_tot,cos_uni,exi_ant_emp,exi_ant_suc "
                    + ",exi_ant_bod,exi_ant_ubi,exi_ant_lot,exi_act_emp,exi_act_suc,exi_act_bod,exi_act_ubi,exi_act_lot "
                    + ",cos_pro_emp,cos_pro_suc,cos_pro_cen_cos,det_obs,cod_tip_doc_int,doc_int,cod_tip_doc_ext,doc_ext "
                    + ",cor_doc_int,cod_cli,cod_pro,cod_usu,flg_cer_doc,flg_anu,obs_anu,flg_ult,usu_mod,fec_mod,tip_mod) "
                    + "VALUES (" + cod_his + ",str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y')," + cod_art + ","
                    + cod_emp + "," + cod_suc + "," + cod_bod + ",'" + des_ubi + "','" + det_lot + "'," + fec_ven + ","
                    + cod_cen_cos + "," + cod_ord + ",'" + flg_ent_sal + "'," + det_can + "," + can_ext + "," + cos_tot + ","
                    + cos_uni + "," + exi_ant_emp + "," + exi_ant_suc + "," + exi_ant_bod + "," + exi_ant_ubi + ","
                    + exi_ant_lot + "," + exi_act_emp + "," + exi_act_suc + "," + exi_act_bod + "," + exi_act_ubi + ","
                    + exi_act_lot + "," + cos_pro_emp + "," + cos_pro_suc + "," + cos_pro_cen_cos + ",'" + det_obs + "',"
                    + cod_tip_doc_int + ",'" + doc_int + "'," + cod_tip_doc_ext + ",'" + doc_ext + "'," + cor_doc_int + ","
                    + cod_cli + "," + cod_pro + "," + cod_usu + ",0," + flg_anu + ",'" + obs_anu + "',0,"
                    + cbean.getCod_usu() + ",now(),'INGRESO MOVIMIENTOS DE INVENTARIO');";

            mquerys.dmlSQLvariable(mQuery);

            //***********************************************************************************************************
            //***************************** MODIFICACIÓN DE REGISTROS POSTERIORES ***************************************
            //***********************************************************************************************************
            Double contasiguientes;

            //***********************************************************************************************************
            //******************************************** POR EMPRESA **************************************************
            //***********************************************************************************************************
            // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción por Empresa
            contasiguientes = mquerys.doubleQuerySQLvariable("select count(cod_his) "
                    + "from inv_tra_his_mov where "
                    + " fec_tra = STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                    + "and cod_ord > " + cod_ord + " "
                    + "and cod_art = " + cod_art + " "
                    + "and flg_anu = 0 "
                    + ";");
            contasiguientes = contasiguientes
                    + mquerys.doubleQuerySQLvariable("select count(cod_his) "
                            + "from inv_tra_his_mov where "
                            + " fec_tra > STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and cod_art = " + cod_art + " "
                            + "and flg_anu = 0 "
                            + ";");

            if (contasiguientes > 0) {
                try {
                    historico.clear();
                    mquerys.Conectar();
                    resvariable = mquerys.querySQLvariable("select cod_his, flg_ent_sal, det_can, can_ext,cos_uni from inv_tra_his_mov "
                            + "where "
                            + " fec_tra = STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and cod_ord > " + cod_ord + " "
                            + "and cod_art=" + cod_art + " "
                            + "and flg_anu = 0 "
                            + "order by fec_his asc,"
                            + "ord_dia asc "
                            + "UNION ALL "
                            + "select cod_his, flg_ent_sal, det_can, can_ext,cos_uni from inv_tra_his_mov "
                            + "where "
                            + " fec_tra > STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and cod_art=" + cod_art + " "
                            + "and flg_anu = 0 "
                            + "order by fec_his asc,"
                            + "ord_dia asc"
                            + ";"
                    );
                    while (resvariable.next()) {
                        historico.add(new ListaCortaTransaccionExistencias(
                                resvariable.getString(1),
                                resvariable.getString(2),
                                resvariable.getDouble(3),
                                resvariable.getDouble(4),
                                resvariable.getDouble(5)
                        ));
                    }
                    resvariable.close();
                    mquerys.Desconectar();

                    for (ListaCortaTransaccionExistencias seriehistorica1 : historico) {
                        if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                            exi_ant_emp = exi_act_emp;
                            cos_pro_emp = ((cos_pro_emp * exi_act_emp + (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext()) * seriehistorica1.getCos_uni()) / (exi_act_emp + (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext())));
                            exi_act_emp = exi_act_emp + (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext());

                        } else {
                            exi_act_emp = exi_act_emp - (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext());
                            if (exi_act_emp < 0.0) {
                                exi_act_emp = 0.0;
                            }
                        }
                        mQuery = "update inv_tra_his_mov set "
                                + "cos_pro_emp = " + cos_pro_emp + ","
                                + "exi_ant_emp = " + exi_ant_emp + ","
                                + "exi_act_emp = " + exi_act_emp + " "
                                + "where "
                                + "cod_his = " + seriehistorica1.getCod_his()
                                + ";";
                        mquerys.dmlSQLvariable(mQuery);
                    }

                } catch (Exception e) {
                    err.auditarError("Man_inventario", "guardarEntradaSalida", "Error en Actualización datos posteriores por Empresa. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
                }
            }

            //***********************************************************************************************************
            //******************************************** POR SUCURSAL *************************************************
            //***********************************************************************************************************
            // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción por Sucursal
            contasiguientes = mquerys.doubleQuerySQLvariable("select count(cod_his) "
                    + "from inv_tra_his_mov where "
                    + " fec_tra = STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                    + "and cod_ord > " + cod_ord + " "
                    + "and cod_art = " + cod_art + " "
                    + "and cod_suc = " + cod_suc + " "
                    + "and flg_anu = 0 "
                    + ";");
            contasiguientes = contasiguientes
                    + mquerys.doubleQuerySQLvariable("select count(cod_his) "
                            + "from inv_tra_his_mov where "
                            + " fec_tra > STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and cod_art = " + cod_art + " "
                            + "and cod_suc = " + cod_suc + " "
                            + "and flg_anu = 0 "
                            + ";");

            if (contasiguientes > 0) {
                try {
                    historico.clear();
                    mquerys.Conectar();
                    resvariable = mquerys.querySQLvariable("select cod_his, flg_ent_sal, det_can, can_ext,cos_uni from inv_tra_his_mov "
                            + "where "
                            + " fec_tra = STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and cod_ord > " + cod_ord + " "
                            + "and cod_art=" + cod_art + " "
                            + "and cod_suc = " + cod_suc + " "
                            + "and flg_anu = 0 "
                            + "order by fec_his asc,"
                            + "ord_dia asc "
                            + "UNION ALL "
                            + "select cod_his, flg_ent_sal, det_can, can_ext,cos_uni from inv_tra_his_mov "
                            + "where "
                            + " fec_tra > STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and cod_art=" + cod_art + " "
                            + "and cod_suc = " + cod_suc + " "
                            + "and flg_anu = 0 "
                            + "order by fec_his asc,"
                            + "ord_dia asc"
                            + ";"
                    );
                    while (resvariable.next()) {
                        historico.add(new ListaCortaTransaccionExistencias(
                                resvariable.getString(1),
                                resvariable.getString(2),
                                resvariable.getDouble(3),
                                resvariable.getDouble(4),
                                resvariable.getDouble(5)
                        ));
                    }
                    resvariable.close();
                    mquerys.Desconectar();

                    for (ListaCortaTransaccionExistencias seriehistorica1 : historico) {
                        if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                            exi_ant_suc = exi_act_suc;
                            cos_pro_suc = ((cos_pro_suc * exi_act_suc + (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext()) * seriehistorica1.getCos_uni()) / (exi_act_suc + (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext())));
                            exi_act_suc = exi_act_suc + (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext());

                        } else {
                            exi_act_suc = exi_act_suc - (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext());
                            if (exi_act_suc < 0.0) {
                                exi_act_suc = 0.0;
                            }
                        }
                        mQuery = "update inv_tra_his_mov set "
                                + "cos_pro_suc = " + cos_pro_suc + ","
                                + "exi_ant_suc = " + exi_ant_suc + ","
                                + "exi_act_suc = " + exi_act_suc + " "
                                + "where "
                                + "cod_his = " + seriehistorica1.getCod_his()
                                + ";";
                        mquerys.dmlSQLvariable(mQuery);
                    }

                } catch (Exception e) {
                    err.auditarError("Man_inventario", "guardarEntradaSalida", "Error en Actualización datos posteriores por Sucursal. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
                }
            }

            //***********************************************************************************************************
            //********************************************** POR BODEGA *************************************************
            //***********************************************************************************************************
            // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción por Bodega
            contasiguientes = mquerys.doubleQuerySQLvariable("select count(cod_his) "
                    + "from inv_tra_his_mov where "
                    + " fec_tra = STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                    + "and cod_ord > " + cod_ord + " "
                    + "and cod_art = " + cod_art + " "
                    + "and cod_suc = " + cod_suc + " "
                    + "and cod_bod = " + cod_bod + " "
                    + "and flg_anu = 0 "
                    + ";");
            contasiguientes = contasiguientes
                    + mquerys.doubleQuerySQLvariable("select count(cod_his) "
                            + "from inv_tra_his_mov where "
                            + " fec_tra > STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and cod_art = " + cod_art + " "
                            + "and cod_suc = " + cod_suc + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and flg_anu = 0 "
                            + ";");

            if (contasiguientes > 0) {
                try {
                    historico.clear();
                    mquerys.Conectar();
                    resvariable = mquerys.querySQLvariable("select cod_his, flg_ent_sal, det_can, can_ext,cos_uni from inv_tra_his_mov "
                            + "where "
                            + " fec_tra = STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and cod_ord > " + cod_ord + " "
                            + "and cod_art=" + cod_art + " "
                            + "and cod_suc = " + cod_suc + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and flg_anu = 0 "
                            + "order by fec_his asc,"
                            + "ord_dia asc "
                            + "UNION ALL "
                            + "select cod_his, flg_ent_sal, det_can, can_ext,cos_uni from inv_tra_his_mov "
                            + "where "
                            + " fec_tra > STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and cod_art=" + cod_art + " "
                            + "and cod_suc = " + cod_suc + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and flg_anu = 0 "
                            + "order by fec_his asc,"
                            + "ord_dia asc"
                            + ";"
                    );
                    while (resvariable.next()) {
                        historico.add(new ListaCortaTransaccionExistencias(
                                resvariable.getString(1),
                                resvariable.getString(2),
                                resvariable.getDouble(3),
                                resvariable.getDouble(4),
                                resvariable.getDouble(5)
                        ));
                    }
                    resvariable.close();
                    mquerys.Desconectar();

                    for (ListaCortaTransaccionExistencias seriehistorica1 : historico) {
                        if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                            exi_ant_bod = exi_act_bod;
                            exi_act_bod = exi_act_bod + (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext());

                        } else {
                            exi_act_bod = exi_act_bod - (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext());
                            if (exi_act_bod < 0.0) {
                                exi_act_bod = 0.0;
                            }
                        }
                        mQuery = "update inv_tra_his_mov set "
                                + "exi_ant_bod = " + exi_ant_bod + ","
                                + "exi_act_bod = " + exi_act_bod + " "
                                + "where "
                                + "cod_his = " + seriehistorica1.getCod_his()
                                + ";";
                        mquerys.dmlSQLvariable(mQuery);

                        cod_his = seriehistorica1.getCod_his();
                    }

                } catch (Exception e) {
                    err.auditarError("Man_inventario", "guardarEntradaSalida", "Error en Actualización datos posteriores por Bodega. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
                }
            }

            //********************** Agrega flag de Último registro modificado a la bodega ******************************
            mQuery = "update inv_tra_his_mov set flg_ult = 1 where cod_his=" + cod_his + ";";
            mquerys.dmlSQLvariable(mQuery);

            //***********************************************************************************************************
            //******************************************** POR UBICACIÓN ************************************************
            //***********************************************************************************************************
            // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción por Bodega
            contasiguientes = mquerys.doubleQuerySQLvariable("select count(cod_his) "
                    + "from inv_tra_his_mov where "
                    + " fec_tra = STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                    + "and cod_ord > " + cod_ord + " "
                    + "and cod_art = " + cod_art + " "
                    + "and cod_suc = " + cod_suc + " "
                    + "and cod_bod = " + cod_bod + " "
                    + "and des_ubi ='" + des_ubi + "' "
                    + "and flg_anu = 0 "
                    + ";");
            contasiguientes = contasiguientes
                    + mquerys.doubleQuerySQLvariable("select count(cod_his) "
                            + "from inv_tra_his_mov where "
                            + " fec_tra > STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and cod_art = " + cod_art + " "
                            + "and cod_suc = " + cod_suc + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and des_ubi ='" + des_ubi + "' "
                            + "and flg_anu = 0 "
                            + ";");

            if (contasiguientes > 0) {
                try {
                    historico.clear();
                    mquerys.Conectar();
                    resvariable = mquerys.querySQLvariable("select cod_his, flg_ent_sal, det_can, can_ext,cos_uni from inv_tra_his_mov "
                            + "where "
                            + " fec_tra = STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and cod_ord > " + cod_ord + " "
                            + "and cod_art=" + cod_art + " "
                            + "and cod_suc = " + cod_suc + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and des_ubi ='" + des_ubi + "' "
                            + "and flg_anu = 0 "
                            + "order by fec_his asc,"
                            + "ord_dia asc "
                            + "UNION ALL "
                            + "select cod_his, flg_ent_sal, det_can, can_ext,cos_uni from inv_tra_his_mov "
                            + "where "
                            + " fec_tra > STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                            + "and cod_art=" + cod_art + " "
                            + "and cod_suc = " + cod_suc + " "
                            + "and cod_bod = " + cod_bod + " "
                            + "and des_ubi ='" + des_ubi + "' "
                            + "and flg_anu = 0 "
                            + "order by fec_his asc,"
                            + "ord_dia asc"
                            + ";"
                    );
                    while (resvariable.next()) {
                        historico.add(new ListaCortaTransaccionExistencias(
                                resvariable.getString(1),
                                resvariable.getString(2),
                                resvariable.getDouble(3),
                                resvariable.getDouble(4),
                                resvariable.getDouble(5)
                        ));
                    }
                    resvariable.close();
                    mquerys.Desconectar();

                    for (ListaCortaTransaccionExistencias seriehistorica1 : historico) {
                        if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                            exi_ant_ubi = exi_act_ubi;
                            exi_act_ubi = exi_act_ubi + (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext());

                        } else {
                            exi_act_ubi = exi_act_ubi - (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext());
                            if (exi_act_ubi < 0.0) {
                                exi_act_ubi = 0.0;
                            }
                        }
                        mQuery = "update inv_tra_his_mov set "
                                + "exi_ant_ubi = " + exi_ant_ubi + ","
                                + "exi_act_ubi = " + exi_act_ubi + " "
                                + "where "
                                + "cod_his = " + seriehistorica1.getCod_his()
                                + ";";
                        mquerys.dmlSQLvariable(mQuery);
                    }

                } catch (Exception e) {
                    err.auditarError("Man_inventario", "guardarEntradaSalida", "Error en Actualización datos posteriores por Ubicación. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
                }
            }

            //***********************************************************************************************************
            //*********************************************** POR LOTE **************************************************
            //***********************************************************************************************************
            if (!"".equals(det_lot)) {
                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacción por Sucursal
                contasiguientes = mquerys.doubleQuerySQLvariable("select count(cod_his) "
                        + "from inv_tra_his_mov where "
                        + " fec_tra = STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                        + "and cod_ord > " + cod_ord + " "
                        + "and cod_art = " + cod_art + " "
                        + "and det_lot ='" + det_lot + "' "
                        + "and flg_anu = 0 "
                        + ";");
                contasiguientes = contasiguientes
                        + mquerys.doubleQuerySQLvariable("select count(cod_his) "
                                + "from inv_tra_his_mov where "
                                + " fec_tra > STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                                + "and cod_art = " + cod_art + " "
                                + "and det_lot ='" + det_lot + "' "
                                + "and flg_anu = 0 "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        historico.clear();
                        mquerys.Conectar();
                        resvariable = mquerys.querySQLvariable("select cod_his, flg_ent_sal, det_can, can_ext,cos_uni from inv_tra_his_mov "
                                + "where "
                                + " fec_tra = STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                                + "and cod_ord > " + cod_ord + " "
                                + "and cod_art=" + cod_art + " "
                                + "and det_lot ='" + det_lot + "' "
                                + "and flg_anu = 0 "
                                + "order by fec_his asc,"
                                + "ord_dia asc "
                                + "UNION ALL "
                                + "select cod_his, flg_ent_sal, det_can, can_ext,cos_uni from inv_tra_his_mov "
                                + "where "
                                + " fec_tra > STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                                + "and cod_art=" + cod_art + " "
                                + "and det_lot ='" + det_lot + "' "
                                + "and flg_anu = 0 "
                                + "order by fec_his asc,"
                                + "ord_dia asc"
                                + ";"
                        );
                        while (resvariable.next()) {
                            historico.add(new ListaCortaTransaccionExistencias(
                                    resvariable.getString(1),
                                    resvariable.getString(2),
                                    resvariable.getDouble(3),
                                    resvariable.getDouble(4),
                                    resvariable.getDouble(5)
                            ));
                        }
                        resvariable.close();
                        mquerys.Desconectar();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : historico) {
                            if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                                exi_ant_lot = exi_act_lot;
                                exi_act_lot = exi_act_lot + (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext());

                            } else {
                                exi_act_lot = exi_act_lot - (seriehistorica1.getDet_can() + seriehistorica1.getCan_ext());
                                if (exi_act_lot < 0.0) {
                                    exi_act_lot = 0.0;
                                }
                            }
                            mQuery = "update inv_tra_his_mov set "
                                    + "exi_ant_lot = " + exi_ant_lot + ","
                                    + "exi_act_lot = " + exi_act_lot + " "
                                    + "where "
                                    + "cod_his = " + seriehistorica1.getCod_his()
                                    + ";";
                            mquerys.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        err.auditarError("Man_inventario", "guardarEntradaSalida", "Error en Actualización datos posteriores por Lote. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
                    }
                }
            }

        } catch (Exception e) {
            mbool = false;
            err.auditarError("Man_inventario", "guardarEntradaSalida", "Error en Guardar Entrada Salida. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }

        mfechaven = null;
        cod_his = "";
        det_lot = "";
        fec_ven = "";
        cod_ord = "";
        det_can = 0.0;
        can_ext = 0.0;
        cos_tot = 0.0;
        cos_uni = 0.0;
        exi_ant_emp = 0.0;
        exi_ant_suc = 0.0;
        exi_ant_bod = 0.0;
        exi_ant_ubi = 0.0;
        exi_ant_lot = 0.0;
        exi_act_emp = 0.0;
        exi_act_suc = 0.0;
        exi_act_bod = 0.0;
        exi_act_ubi = 0.0;
        exi_act_lot = 0.0;
        cos_pro_emp = 0.0;
        cos_pro_suc = 0.0;
        cos_pro_cen_cos = 0.0;
        det_obs = "";

        try {
            exisantubi = Double.valueOf(mquerys.strQuerySQLvariable("select ifnull(exi_ant_ubi,0.0) "
                    + "from inv_tra_his_mov "
                    + "where fec_tra <= str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y')"
                    + "and cod_art =" + cod_art + " "
                    + "and cod_bod= " + cod_bod + " "
                    + "and des_ubi='" + des_ubi + "' "
                    + "and flg_anu=0 "
                    + "order by fec_tra desc, cod_ord desc limit 1;"));
        } catch (Exception e) {
            exisantubi = 0.0;
        }
        try {
            exisactubi = Double.valueOf(mquerys.strQuerySQLvariable("select ifnull(exi_act_ubi,0.0) "
                    + "from inv_tra_his_mov "
                    + "where fec_tra <= str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                    + "and cod_bod= " + cod_bod + " "
                    + "and des_ubi='" + des_ubi + "' "
                    + "and cod_art =" + cod_art + " "
                    + "and flg_anu=0 "
                    + "order by fec_tra desc, cod_ord desc limit 1;"));
        } catch (Exception e) {
            exisactubi = 0.0;
        }

        if ("true".equals(cbean.getFlg_uso_lot_art())) {
            usolote = mquerys.strQuerySQLvariable("select flg_usa_lot from inv_cat_art where cod_art=" + cod_art + ";");
            if (!"true".equals(usolote) && !"false".equals(usolote)) {
                usolote = cbean.getFlg_uso_lot_art();
            }
        }
        usofechaven = "false";

        //llenarUbicaciones();
        llenarLotes();

        return mbool;

    }

    public void recuperarimagenEntSal() {
        String mQuery = "";
        mimagen2 = null;
        try {
            mQuery = "select img_art from inv_cat_art where cod_art = " + cod_art + ";";
            Blob miBlob = mquerys.blobQuerySQLvariable(mQuery);
            if (miBlob != null) {
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen2 = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 150.0);
                img = null;

                data = null;
            } else {
                mQuery = "select img_art from inv_cat_art where cod_art = 0;";
                miBlob = mquerys.blobQuerySQLvariable(mQuery);
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen2 = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mheight = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 150.0);
                img = null;

                data = null;
            }

        } catch (Exception e) {
            mheight = "150";
            err.auditarError("Man_inventario", "recuperarimagenEntSal", "Error en recuperar imagen Artículo. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());

        }

    }

    public void iniciarbuscarDocumentos() {
        //********* Búsqueda **************
        bus_tip_doc_int = "";
        bus_doc_int = "";
        bus_flg_ent_sal = "";
        bus_flg_cer_doc = "";
        bus_limit = "25";
        bus_anu_obs = "";
        bus_ope_ret = "0";
        bus_fec_tra = "";

        llenarDocumentosInternos();

    }

    //***************** Buscar Documentos *********************
    public void seguireditandoDocumento() {
        if (!"".equals(bus_doc_int)) {
            switch (bus_flg_cer_doc) {
                case "ABIERTO":
                    //******** historial ************
                    mfechaven = null;
                    cod_his = "";
                    cod_art = "0";
                    des_ubi = "";
                    det_lot = "";
                    fec_ven = "";
                    cod_cen_cos = "0";
                    cod_ord = "";
                    flg_ent_sal = bus_flg_ent_sal;
                    det_can = 0.0;
                    can_ext = 0.0;
                    cos_tot = 0.0;
                    cos_uni = 0.0;
                    exi_ant_emp = 0.0;
                    exi_ant_suc = 0.0;
                    exi_ant_bod = 0.0;
                    exi_ant_ubi = 0.0;
                    exi_ant_lot = 0.0;
                    exi_act_emp = 0.0;
                    exi_act_suc = 0.0;
                    exi_act_bod = 0.0;
                    exi_act_ubi = 0.0;
                    exi_act_lot = 0.0;
                    cos_pro_emp = 0.0;
                    cos_pro_suc = 0.0;
                    cos_pro_cen_cos = 0.0;
                    det_obs = "";
                    cod_tip_doc_int = bus_tip_doc_int;
                    doc_int = bus_doc_int;
                    cod_tip_doc_ext = "0";
                    doc_ext = "";
                    cor_doc_int = "";
                    cod_cli = "0";
                    cod_pro = "0";
                    cod_usu = cbean.getCod_usu();
                    flg_anu = "0";
                    obs_anu = "";

                    //******* Imágenes ****************
                    mheight = "150";
                    mimagen2 = null;

                    //******** Otros *****************
                    exisantubi = 0.0;
                    exisactubi = 0.0;
                    nombrebodega = mquerys.strQuerySQLvariable("select nom_bod from inv_cat_bod where cod_bod=" + cod_bod + ";");
                    nombreunimed = "";
                    unimedori = "0";
                    unimedsal = "0";
                    nombrearticulo = "Ninguno";
                    tituloclienteproveedor = "Proveedor";
                    usofechaven = "false";
                    nombremarca = "Marca";

                    //******** Llenar Catálogos ******
                    llenarDocumentosInt();
                    llenarDocumentosExt();
                    //llenarUbicaciones();
                    llenarCentrosCosto();
                    llenarClientesProveedores();
                    llenarArticulos();
                    llenarUnidadesMedida();

                    RequestContext.getCurrentInstance().execute("PF('wvBuscarDocInvArt').clearFilters()");
                    RequestContext.getCurrentInstance().update("frmBuscarDocInvArt");
                    RequestContext.getCurrentInstance().execute("PF('wBuscarDocInvArt').hide()");

                    RequestContext.getCurrentInstance().update("frmEntSalArt");
                    break;

                case "CERRADO":
                    addMessage("Buscar Documento", "El Documento seleccionado ya ha sido Finalizado y no puede ser editado", 3);
                    break;
                case "ANULADO":
                    addMessage("Buscar Documento", "El Documento seleccionado ha sido ANULADO y no puede ser editado", 3);
                    break;
                case "REVERSIÓN":
                    addMessage("Buscar Documento", "El Documento seleccionado es REVERSIÓN de una Anulación y no puede modificarse", 3);
                    break;
            }

        } else {
            addMessage("Buscar Documento", "Debe Seleccionar un Documento", 3);
        }

    }

    public void finalizarDocumento() {
        if (!"".equals(bus_doc_int)) {
            switch (bus_flg_cer_doc) {
                case "ABIERTO":
                    mquerys.dmlSQLvariable("update inv_tra_his_mov "
                            + "set flg_cer_doc = 1 "
                            + "where cod_emp =" + cod_emp + " "
                            + "and cod_suc=" + cod_suc + " "
                            + "and cod_tip_doc_int =" + bus_tip_doc_int + " "
                            + "and doc_int='" + bus_doc_int + "';");
                    recargarDocumentosInt();
                    addMessage("Buscar Documento", "El Documento ha sido Finalizado", 1);
                    break;
                case "CERRADO":
                    addMessage("Buscar Documento", "El Documento seleccionado ya ha sido Finalizado", 3);
                    break;
                case "ANULADO":
                    addMessage("Buscar Documento", "El Documento seleccionado ha sido ANULADO", 3);
                    break;
                case "REVERSIÓN":
                    addMessage("Buscar Documento", "El Documento seleccionado es REVERSIÓN de una Anulación y no puede modificarse", 3);
                    break;

            }
        } else {
            addMessage("Buscar Documento", "Debe Seleccionar un Documento", 3);
        }
    }

    public void cerrarbuscarDocumentos() {
        bus_anu_obs = "";
        bus_ope_ret = "";
        bus_fec_tra = "";
        RequestContext.getCurrentInstance().execute("PF('wvBuscarDocInvArt').clearFilters()");
        RequestContext.getCurrentInstance().update("frmBuscarDocInvArt");
    }

    public void iniciarAnular() {
        if (!"".equals(bus_doc_int)) {
            switch (bus_flg_cer_doc) {
                case "ABIERTO":
                    bus_anu_obs = "";
                    bus_ope_ret = "0";
                    llenarDocsIntExt();
                    RequestContext.getCurrentInstance().update("frmAnularInvArt");
                    RequestContext.getCurrentInstance().execute("PF('wAnularInvArt').show()");
                    break;
                case "CERRADO":
                    bus_anu_obs = "";
                    bus_ope_ret = "0";
                    llenarDocsIntExt();
                    RequestContext.getCurrentInstance().update("frmAnularInvArt");
                    RequestContext.getCurrentInstance().execute("PF('wAnularInvArt').show()");
                    break;
                case "ANULADO":
                    addMessage("Buscar Documento", "El Documento seleccionado ya ha sido ANULADO", 3);
                    break;
                case "REVERSIÓN":
                    addMessage("Buscar Documento", "El Documento seleccionado es REVERSIÓN de una Anulación y no puede modificarse", 3);
                    break;

            }
        } else {
            addMessage("Buscar Documento", "Debe Seleccionar un Documento", 3);
        }

    }

    public void cerrarAnular() {
        bus_anu_obs = "";
        bus_ope_ret = "0";
    }

    public void anularDocumento() {
        boolean mvalidar = true;
        if (bus_anu_obs == null) {
            bus_anu_obs = "";
        }
        if ("0".equals(bus_ope_ret)) {
            mvalidar = false;
            addMessage("Buscar Documento", "Debe Seleccionar un Tipo de Documento de Reversión", 3);
        }
        if ("".equals(bus_anu_obs)) {
            mvalidar = false;
            addMessage("Buscar Documento", "Debe Ingresar un motivo por el que se anula este Documento", 3);
        }
        if ("".equals(bus_fec_tra)) {
            mvalidar = false;
            addMessage("Buscar Documento", "El Documento Seleccionado no Tiene definida una Fecha de Transacción", 3);
        }
        if (mvalidar) {
            String mNuevoCorrelativo = "";
            while (mvalidar) {
                //************* Genera un Nuevo Correlativo para el tipo de documento elegido ***********************
                mNuevoCorrelativo = mquerys.strQuerySQLvariable("select concat("
                        + "(select pre_cor from gen_cat_tip_doc_int where cod_tip_doc =" + bus_ope_ret + "),"
                        + "(select right(year(str_to_date('" + bus_fec_tra + "','%d/%m/%Y')),2)),"
                        + "'-',"
                        + "LPAD((select ifnull(max(ult_cor),0) + 1 from gen_cat_tip_doc_int where cod_tip_doc =" + bus_ope_ret + "),6,'0')"
                        + ");");
                if (!"0".equals(mquerys.strQuerySQLvariable("select count(doc_int) from inv_tra_his_mov where cod_emp=" + cod_emp + " and doc_int='" + mNuevoCorrelativo + "';"))) {
                    //*********** Aumenta correlativo documento interno *****************
                    mquerys.dmlSQLvariable("update gen_cat_tip_doc_int set ult_cor = (ult_cor + 1) where cod_tip_doc=" + bus_ope_ret + ";");
                } else {
                    mvalidar = false;
                }
            }

            //************ Actualiza Flag de Anulado en los registros del Historial *********************
            mquerys.dmlSQLvariable("update inv_tra_his_mov "
                    + "set flg_cer_doc = 2, "
                    + "obs_anu = '" + bus_anu_obs + ". Se ha revertido la operación con el documento " + mNuevoCorrelativo + "', "
                    + "usu_mod = " + cbean.getCod_usu() + ", "
                    + "fec_mod = now(), "
                    + "tip_mod = 'ANULACIÓN DE DOCUMENTO POR USUARIO' "
                    + "where cod_emp =" + cod_emp + " "
                    + "and cod_suc=" + cod_suc + " "
                    + "and cod_tip_doc_int =" + bus_tip_doc_int + " "
                    + "and doc_int='" + bus_doc_int + "';");

            //****************** Reversión de Operación **************************************************
            for (int i = 0; i < documentosintdetalle.size(); i++) {
                if ("true".equals(bus_flg_ent_sal)) {
                    flg_ent_sal = "false";
                } else {
                    flg_ent_sal = "true";
                }
                fec_tra = bus_fec_tra + " ";
                cod_tip_doc_int = bus_ope_ret;
                doc_int = mNuevoCorrelativo;
                cod_tip_doc_ext = "0";
                doc_ext = "";
                cod_cli = "0";
                cod_pro = "0";
                cod_usu = cbean.getCod_usu();
                flg_anu = "0";
                obs_anu = "";
                det_obs = "";

                cod_art = documentosintdetalle.get(i).getCod_art();
                des_ubi = documentosintdetalle.get(i).getDes_ubi();
                det_lot = documentosintdetalle.get(i).getDet_lot();
                fec_ven = documentosintdetalle.get(i).getFec_ven();
                if (fec_ven == null) {
                    fec_ven = "";
                }
                if ("00/00/0000".equals(fec_ven)) {
                    fec_ven = "";
                }
                cos_tot = Double.valueOf(documentosintdetalle.get(i).getCos_tot());
                det_can = Double.valueOf(documentosintdetalle.get(i).getDet_can());
                can_ext = Double.valueOf(documentosintdetalle.get(i).getCan_ext());

                mvalidar = guardarMovimiento();

            }

            //************ Actualiza Flag de Revertido en los registros del Historial *********************
            mquerys.dmlSQLvariable("update inv_tra_his_mov "
                    + "set flg_cer_doc = 3, "
                    + "usu_mod = " + cbean.getCod_usu() + ", "
                    + "fec_mod = now(), "
                    + "tip_mod = 'DOCUMENTO DE REVERSIÓN GENERADO AUTOMÁTICAMENTE POR ANULACIÓN' "
                    + "where cod_emp =" + cod_emp + " "
                    + "and cod_suc=" + cod_suc + " "
                    + "and cod_tip_doc_int =" + bus_ope_ret + " "
                    + "and doc_int='" + mNuevoCorrelativo + "';");

            //*********** Aumenta correlativo documento interno *****************
            mquerys.dmlSQLvariable("update gen_cat_tip_doc_int set ult_cor = (ult_cor + 1) where cod_tip_doc=" + bus_ope_ret + ";");

            //************* Variable de Búsqueda *******
            llenarDocumentosInternos();
            bus_anu_obs = "";
            bus_ope_ret = "0";
            bus_tip_doc_int = "";
            bus_doc_int = "";
            bus_flg_ent_sal = "";
            bus_flg_cer_doc = "";
            bus_fec_tra = "";

            //******** historial ************
            mfechaven = null;
            cod_his = "";
            cod_art = "0";
            des_ubi = "";
            det_lot = "";
            fec_ven = "";
            cod_cen_cos = "0";
            cod_ord = "";
            flg_ent_sal = "false";
            det_can = 0.0;
            can_ext = 0.0;
            cos_tot = 0.0;
            cos_uni = 0.0;
            exi_ant_emp = 0.0;
            exi_ant_suc = 0.0;
            exi_ant_bod = 0.0;
            exi_ant_ubi = 0.0;
            exi_ant_lot = 0.0;
            exi_act_emp = 0.0;
            exi_act_suc = 0.0;
            exi_act_bod = 0.0;
            exi_act_ubi = 0.0;
            exi_act_lot = 0.0;
            cos_pro_emp = 0.0;
            cos_pro_suc = 0.0;
            cos_pro_cen_cos = 0.0;
            det_obs = "";
            cod_tip_doc_int = "0";
            doc_int = "";
            cod_tip_doc_ext = "0";
            doc_ext = "";
            cor_doc_int = "";
            cod_cli = "0";
            cod_pro = "0";
            cod_usu = cbean.getCod_usu();
            flg_anu = "0";
            obs_anu = "";

            //******* Imágenes ****************
            mheight = "150";
            mimagen2 = null;

            //******** Otros *****************
            exisantubi = 0.0;
            exisactubi = 0.0;
            nombreunimed = "";
            unimedori = "0";
            unimedsal = "0";
            nombrearticulo = "Ninguno";
            tituloclienteproveedor = "Proveedor";
            usofechaven = "false";

            //******** Llenar Catálogos ******
            llenarDocumentosInt();
            llenarDocumentosExt();
            //llenarUbicaciones();
            llenarCentrosCosto();
            llenarClientesProveedores();
            llenarArticulos();
            llenarUnidadesMedida();

            if (mvalidar) {
                addMessage("Anular Documento", "Documento Anulado Exitosamente", 1);
            } else {
                addMessage("Anular Documento", "Errores al Anular el Documento", 3);
            }
            RequestContext.getCurrentInstance().update("frmAnularInvArt");
            RequestContext.getCurrentInstance().execute("PF('wAnularInvArt').hide()");
            RequestContext.getCurrentInstance().update("frmBuscarDocInvArt");
            RequestContext.getCurrentInstance().update("frmEntSalArt");
        } else {
            RequestContext.getCurrentInstance().update("frmAnularInvArt");
        }

    }

    //************ Llenar Catálogos ************************
    public void llenarMarcas() {
        String mQuery = "";
        try {
            marcas.clear();

            mQuery = "select cod_mar, nom_mar "
                    + "from gen_cat_mar order by nom_mar;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                marcas.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarMarcas", "Error en el llenado de Marcas. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarTemperaturas() {
        String mQuery = "";
        try {
            temperaturas.clear();

            mQuery = "select cod_tem, des_tem "
                    + "from inv_cat_art_tem order by cod_tem;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                temperaturas.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarTemperaturas", "Error en el llenado de Temperaturas. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarUnidadesMedida() {
        String mQuery = "";
        try {
            unidades.clear();

            mQuery = "select cod_uni, nom_uni "
                    + "from gen_cat_uni_med order by nom_uni;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                unidades.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarUnidadesMedida", "Error en el llenado de Unidades de Medida. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarDocumentosInt() {
        String mQuery = "";
        try {
            tiposdocint.clear();

            mQuery = "select cod_tip_doc, nom_tip_doc "
                    + "from gen_cat_tip_doc_int "
                    + "where flg_ent_sal ='" + flg_ent_sal + "' "
                    + "order by nom_tip_doc;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                tiposdocint.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarDocumentosInt", "Error en el llenado de Documentos Internos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarDocumentosExt() {
        String mQuery = "";
        try {
            tiposdocext.clear();

            mQuery = "select cod_tip_doc, nom_tip_doc "
                    + "from gen_cat_tip_doc "
                    + "where flg_ent_sal ='" + flg_ent_sal + "' "
                    + "order by nom_tip_doc;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                tiposdocext.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarDocumentosExt", "Error en el llenado de Documentos Externos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarBodegas() {
        String mQuery = "";
        try {
            bodegas.clear();

            mQuery = "select cod_bod, nom_bod "
                    + "from inv_cat_bod "
                    + "where cod_suc = " + cod_suc + " "
                    + "order by nom_bod;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegas.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarBodegas", "Error en el llenado de Bodegas. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarUbicaciones() {
        String mQuery = "";
        try {
            ubi_des_ubi = "";
            ubicaciones.clear();

            mQuery = "select des_ubi from inv_cat_art_rel_ubi "
                    + "where cod_suc=" + cod_suc + " and cod_art=" + art_cod_art + " "
                    + "and cod_bod =" + ubi_cod_bod + ";";

            ubi_des_ubi = mquerys.strQuerySQLvariable(mQuery);
            if (ubi_des_ubi == null) {
                ubi_des_ubi = "";
            }

            /*
            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                ubicaciones.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();
             */
        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarUbicaciones", "Error en el llenado de Ubicaciones. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarCentrosCosto() {
        String mQuery = "";
        try {
            centroscosto.clear();

            mQuery = "select cod_cen_cos, nom_cen_cos "
                    + "from gen_cat_cen_cos order by nom_cen_cos;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                centroscosto.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarCentrosCosto", "Error en el llenado de Centros de Costo. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarClientesProveedores() {
        String mQuery = "";
        try {
            clientesproveedores.clear();
            if ("false".equals(flg_ent_sal)) {
                mQuery = "select cod_pro, concat(nom_pro,' ', ape_pro) as nombre "
                        + "from inv_cat_pro "
                        + "where cod_emp =" + cod_emp + " "
                        + "order by nom_pro;";
            } else {
                mQuery = "select cod_cli, concat(nom_cli,' ', ape_cli) as nombre "
                        + "from fac_cat_cli "
                        + "where cod_emp =" + cod_emp + " "
                        + "order by nom_cli;";
            }

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                clientesproveedores.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarClientesProveedores", "Error en el llenado de Clientes y Proveedores. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarLotes() {
        String mQuery = "";
        try {
            lotes.clear();

            mQuery = "select distinct(det_lot) as lote "
                    + "from inv_tra_his_mov "
                    + "where cod_art = " + cod_art + " "
                    + "and TRIM(det_lot) <> '' "
                    + "order by des_ubi;";

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
            err.auditarError("Man_inventario", "llenarLotes", "Error en el llenado de Lotes. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarArticulos() {
        String mQuery = "";
        try {
            catarticulos = null;
            articulos.clear();

            mQuery = "select cod_art, cod_emp, cod_gru, cod_lin, cod_ref, nom_art, des_art, cod_mar, '' as img_art, "
                    + "cod_uni_med,cod_tem,reg_san, flg_exe, flg_per, flg_usa_lot, flg_imp, flg_exp "
                    + "from inv_cat_art "
                    + "where cod_emp = " + cod_emp + " "
                    + "and cod_art <> 0 "
                    + "order by cod_ref;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                articulos.add(new Cat_articulos(
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
                        resVariable.getString(17),
                        resVariable.getString(17),
                        resVariable.getString(17)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarArticulos", "Error en el llenado de Artículos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarExistenciasActuales() {
        String mQuery = "";
        try {
            catinventario = null;
            inventario.clear();

            mQuery = "select  "
                    + "his.cod_his,  "
                    + "his.fec_tra,  "
                    + "his.cod_art,  "
                    + "his.cod_bod,  "
                    + "his.des_ubi,  "
                    + "his.det_lot,  "
                    + "his.fec_ven,  "
                    + "his.cod_ord,  "
                    + "format(his.exi_ant_bod,2), "
                    + "format(his.exi_act_bod,2), "
                    + "format(his.cos_pro_suc,2),  "
                    + "art.cod_ref,  "
                    + "art.nom_art, "
                    + "uni.nom_uni,  "
                    + "bod.nom_bod "
                    + "from inv_tra_his_mov as his "
                    + "left join inv_cat_art as art on his.cod_emp = art.cod_emp and his.cod_art = art.cod_art "
                    + "left join gen_cat_uni_med as uni on art.cod_uni_med = uni.cod_uni "
                    + "left join inv_cat_bod as bod on his.cod_suc = bod.cod_suc and his.cod_bod = bod.cod_bod "
                    + "where his.fec_tra <= STR_TO_DATE('" + fec_tra.substring(0, 11).trim() + "', '%d/%m/%Y') "
                    + "and his.cod_bod = " + cod_bod + " "
                    + "and his.flg_ult = 1 "
                    + "order by art.cod_ref;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                inventario.add(new Cat_existencias(
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
                        resVariable.getString(15)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarExistencias", "Error en el llenado de Existencias de Inventario. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarExistenciasPorFecha() {

    }

    public void llenarDocumentosInternos() {
        String mQuery = "";
        try {
            listadocumentosinternos = null;
            documentosintdetalle.clear();
            documentosint.clear();

            if (bus_limit == null) {
                bus_limit = "25";
            } else if ("".equals(bus_limit.trim()) || "0".equals(bus_limit)) {
                bus_limit = "25";
            } else {
                try {
                    Integer mprueba = Integer.valueOf(bus_limit);
                    bus_limit = String.valueOf(mprueba);
                } catch (Exception e) {
                    bus_limit = "25";
                }
            }

            mQuery = "select "
                    + "his.flg_ent_sal, "
                    + "case his.flg_ent_sal when 'false' then 'ENTRADA' when 'true' then 'SALIDA' end as flgentsal, "
                    + "his.cod_tip_doc_int, "
                    + "doc.nom_tip_doc, "
                    + "his.doc_int,  "
                    + "date_format(his.fec_tra, '%d/%m/%Y') as fec, "
                    + "case his.flg_cer_doc "
                    + "when 0 then 'ABIERTO' "
                    + "when 1 then 'CERRADO' "
                    + "when 2 then 'ANULADO' "
                    + "when 3 then 'REVERSIÓN' "
                    + "end as flg "
                    + "from inv_tra_his_mov as his "
                    + "left join gen_cat_tip_doc_int as doc on his.cod_tip_doc_int = doc.cod_tip_doc "
                    + "where his.cod_emp = " + cod_emp + " "
                    + "and his.cod_suc = " + cod_suc + " "
                    + "and his.fec_tra <= str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y')  "
                    + "group by his.flg_ent_sal, "
                    + "his.cod_tip_doc_int, "
                    + "doc.nom_tip_doc,  "
                    + "his.doc_int,  "
                    + "his.fec_tra,  "
                    + "his.flg_cer_doc  "
                    + "order by his.fec_tra desc, doc.nom_tip_doc limit " + bus_limit + ";";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                documentosint.add(new ListaDocumentosInternos(
                        resVariable.getString(1),
                        resVariable.getString(2),
                        resVariable.getString(3),
                        resVariable.getString(4),
                        resVariable.getString(5),
                        resVariable.getString(6),
                        resVariable.getString(7)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarDocumentosInternos", "Error en el llenado de Documentos Internos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarDetallesDocumentosInternos() {
        String mQuery = "";
        try {
            documentosintdetalle.clear();

            mQuery = "select "
                    + "his.cod_art, "
                    + "art.cod_ref, "
                    + "art.nom_art, "
                    + "art.cod_uni_med, "
                    + "uni.nom_uni, "
                    + "his.det_can, "
                    + "his.can_ext, "
                    + "his.doc_ext, "
                    + "his.des_ubi, "
                    + "his.cos_tot, "
                    + "his.det_lot, "
                    + "date_format(his.fec_ven,'%d/%m/%Y') as fecv "
                    + "from inv_tra_his_mov as his "
                    + "left join inv_cat_art as art on his.cod_art = art.cod_art "
                    + "left join gen_cat_uni_med as uni on art.cod_uni_med = uni.cod_uni "
                    + "where his.cod_emp = " + cod_emp + " "
                    + "and his.cod_suc = " + cod_suc + " "
                    + "and his.fec_tra <= str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y')  "
                    + "and his.doc_int = '" + bus_doc_int + "' "
                    + "order by his.cor_doc_int;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                documentosintdetalle.add(new ListaDetalleDocumentosInternos(
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
                        resVariable.getString(12)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarDetallesDocumentosInternos", "Error en el llenado de Detalle Documentos Internos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarDocsIntExt() {
        String mQuery = "";
        try {
            docintext.clear();

            mQuery = "select cod_tip_doc, nom_tip_doc "
                    + "from gen_cat_tip_doc_int "
                    + "where flg_ent_sal <> '" + bus_flg_ent_sal + "' "
                    + "order by nom_tip_doc;";

            ResultSet resVariable;
            mquerys.Conectar();

            resVariable = mquerys.querySQLvariable(mQuery);
            while (resVariable.next()) {
                docintext.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            mquerys.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarDocsIntExt", "Error en el llenado de Documentos Internos Externos para Anulación. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }

    }

    //************** Funciones Varias ***********************
    public void onTabChange(TabChangeEvent event) {
        if (!"Artículos".equals(event.getTab().getTitle())) {
            nuevoarticulo();
            RequestContext.getCurrentInstance().update("frmInventarioArt");
        }
    }

    public void onRowSelectEditArt(SelectEvent event) {
        art_cod_art = ((Cat_articulos) event.getObject()).getCod_art();
        art_cod_gru = ((Cat_articulos) event.getObject()).getCod_gru();
        art_cod_lin = ((Cat_articulos) event.getObject()).getCod_lin();
        art_cod_ref = ((Cat_articulos) event.getObject()).getCod_ref();
        art_nom_art = ((Cat_articulos) event.getObject()).getNom_art();
        art_des_art = ((Cat_articulos) event.getObject()).getDes_art();
        art_cod_mar = ((Cat_articulos) event.getObject()).getCod_mar();
        art_img_art = "";
        art_cod_uni_med = ((Cat_articulos) event.getObject()).getCod_uni_med();
        art_cod_tem = ((Cat_articulos) event.getObject()).getCod_tem();
        art_reg_san = ((Cat_articulos) event.getObject()).getReg_san();
        art_flg_exe = ((Cat_articulos) event.getObject()).getFlg_exe();
        art_flg_per = ((Cat_articulos) event.getObject()).getFlg_per();
        art_flg_usa_lot = ((Cat_articulos) event.getObject()).getFlg_usa_lot();
        art_flg_imp = ((Cat_articulos) event.getObject()).getFlg_imp();
        art_flg_exp = ((Cat_articulos) event.getObject()).getFlg_exp();

        recuperarimagen();

        RequestContext.getCurrentInstance().execute("PF('wvArtEdit').clearFilters()");
        RequestContext.getCurrentInstance().execute("PF('wArtEdit').hide()");

    }

    public void onSelectEntSal() {
        if ("false".equals(flg_ent_sal)) {
            tituloclienteproveedor = "Proveedor";
            usocosto = cbean.getFlg_cos();
        } else {
            tituloclienteproveedor = "Cliente";
            usocosto = "true";
        }
        usofechaven = "false";
        doc_int = "";
        doc_ext = "";
        cod_pro = "0";
        cod_cli = "0";
        cos_tot = 0.0;
        cos_uni = 0.0;
        llenarClientesProveedores();
        llenarDocumentosInt();
        llenarDocumentosExt();

    }

    public void onSelectUbicación() {
        try {
            exisantubi = Double.valueOf(mquerys.strQuerySQLvariable("select ifnull(exi_ant_ubi,0.0) "
                    + "from inv_tra_his_mov "
                    + "where fec_tra <= str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y')"
                    + "and cod_art =" + cod_art + " "
                    + "and cod_bod= " + cod_bod + " "
                    + "and des_ubi='" + des_ubi + "' "
                    + "and flg_anu=0 "
                    + "order by fec_tra desc, cod_ord desc limit 1;"));
        } catch (Exception e) {
            exisantubi = 0.0;
        }
        try {
            exisactubi = Double.valueOf(mquerys.strQuerySQLvariable("select ifnull(exi_act_ubi,0.0) "
                    + "from inv_tra_his_mov "
                    + "where fec_tra <= str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                    + "and cod_bod= " + cod_bod + " "
                    + "and des_ubi='" + des_ubi + "' "
                    + "and cod_art =" + cod_art + " "
                    + "and flg_anu=0 "
                    + "order by fec_tra desc, cod_ord desc limit 1;"));
        } catch (Exception e) {
            exisactubi = 0.0;
        }

    }

    public void onSelectDocInt() {
        if (cod_tip_doc_int == null) {
            cod_tip_doc_int = "0";
        }
        if ("0".equals(cod_tip_doc_int)) {
            doc_int = "";
        } else {
            doc_int = mquerys.strQuerySQLvariable("select concat("
                    + "(select pre_cor from gen_cat_tip_doc_int where cod_tip_doc =" + cod_tip_doc_int + "),"
                    + "(select right(year(str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y')),2)),"
                    + "'-',"
                    + "LPAD((select ifnull(max(ult_cor),0) + 1 from gen_cat_tip_doc_int where cod_tip_doc =" + cod_tip_doc_int + "),6,'0')"
                    + ");");
        }
    }

    public void onSelectUltDocInt() {
        if (cod_tip_doc_int == null) {
            cod_tip_doc_int = "0";
        }
        if ("0".equals(cod_tip_doc_int)) {
            doc_int = "";
        } else {
            doc_int = mquerys.strQuerySQLvariable("select concat("
                    + "(select pre_cor from gen_cat_tip_doc_int where cod_tip_doc =" + cod_tip_doc_int + "),"
                    + "(select right(year(str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y')),2)),"
                    + "'-',"
                    + "LPAD((select ifnull(max(doc_int),0) from inv_tra_his_mov where cod_tip_doc_int =" + cod_tip_doc_int + " and year(fec_tra)=year(str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y'))),6,'0')"
                    + ");");
        }
    }

    public void onSelectDocExt() {
        doc_ext = "";
    }

    public void onSelectArticulo() {
        des_ubi = mquerys.strQuerySQLvariable("select des_ubi from inv_cat_art_rel_ubi "
                + "where cod_suc=" + cod_suc + " and cod_art=" + cod_art + " "
                + "and cod_bod =" + cod_bod + ";");
        if (des_ubi == null) {
            des_ubi = "";
        }

        try {
            exisantubi = Double.valueOf(mquerys.strQuerySQLvariable("select ifnull(exi_ant_ubi,0.0) "
                    + "from inv_tra_his_mov "
                    + "where fec_tra <= str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y')"
                    + "and cod_art =" + cod_art + " "
                    + "and cod_bod= " + cod_bod + " "
                    + "and des_ubi='" + des_ubi + "' "
                    + "and flg_anu=0 "
                    + "order by fec_tra desc, cod_ord desc limit 1;"));
        } catch (Exception e) {
            exisantubi = 0.0;
        }
        try {
            exisactubi = Double.valueOf(mquerys.strQuerySQLvariable("select ifnull(exi_act_ubi,0.0) "
                    + "from inv_tra_his_mov "
                    + "where fec_tra <= str_to_date('" + fec_tra.substring(0, 11).trim() + "','%d/%m/%Y') "
                    + "and cod_bod= " + cod_bod + " "
                    + "and des_ubi='" + des_ubi + "' "
                    + "and cod_art =" + cod_art + " "
                    + "and flg_anu=0 "
                    + "order by fec_tra desc, cod_ord desc limit 1;"));
        } catch (Exception e) {
            exisactubi = 0.0;
        }
        nombrearticulo = mquerys.strQuerySQLvariable("select concat(ifnull(cod_ref,'Ninguno'),'-',ifnull(nom_art,'Ninguno')) as art from inv_cat_art where cod_art = " + cod_art + ";");
        nombreunimed = mquerys.strQuerySQLvariable("select ifnull(nom_uni,'NO DEFINIDA') from gen_cat_uni_med where cod_uni = (select cod_uni_med from inv_cat_art where cod_art=" + cod_art + ");");
        unimedori = mquerys.strQuerySQLvariable("select cod_uni_med from inv_cat_art where cod_art=" + cod_art + ";");
        unimedsal = unimedori;
        nombremarca = "Marca: " + mquerys.strQuerySQLvariable("select ifnull(nom_mar,'Sin Marca') as marca from gen_cat_mar where cod_mar=(select cod_uni_med from inv_cat_art where cod_art=" + cod_art + ");");
        if ("true".equals(cbean.getFlg_uso_lot_art())) {
            usolote = mquerys.strQuerySQLvariable("select flg_usa_lot from inv_cat_art where cod_art=" + cod_art + ";");
            if (!"true".equals(usolote) && !"false".equals(usolote)) {
                usolote = cbean.getFlg_uso_lot_art();
            }
        }
        usofechaven = "false";
        det_lot = "";
        fec_ven = "";
        mfechaven = null;
        llenarLotes();
        recuperarimagenEntSal();

    }

    public void onSelectLote() {
        if (det_lot == null) {
            det_lot = "";
        }
        if ("SELECCIONE UNO".equals(det_lot.toUpperCase())) {
            det_lot = det_lot.toUpperCase().replace("SELECCIONE UNO", "");
        }
        usofechaven = "false";
        mfechaven = null;
        fec_ven = "";
        if (!"".equals(det_lot.trim())) {
            if ("true".equals(mquerys.strQuerySQLvariable("select flg_per from inv_cat_art where cod_art=" + cod_art + ";"))) {
                if ("0".equals(mquerys.strQuerySQLvariable("select count(fec_ven) from inv_tra_his_mov where cod_art=" + cod_art + " and det_lot='" + det_lot + "';"))) {
                    if ("true".equals(cbean.getFlg_uso_lot_art())) {
                        usofechaven = "true";
                    }
                } else if ("true".equals(cbean.getFlg_uso_lot_art())) {
                    fec_ven = mquerys.strQuerySQLvariable("select date_format(fec_ven,'%d/%m/%Y') as fec from inv_tra_his_mov where cod_art=" + cod_art + " and det_lot='" + det_lot + "' limit 1;");
                    try {
                        mfechaven = mformatsencillo.parse(fec_ven);
                    } catch (Exception e) {
                        fec_ven = "";
                        mfechaven = null;
                    }
                }
            }
        }
    }

    public void onClickImage() {
        recuperarimagenEntSal();
    }

    public void onSelectExistencia(SelectEvent event) {

    }

    public void dateSelectedFectra(SelectEvent f) {
        Date date = (Date) f.getObject();
        fec_tra = mformat.format(date);
    }

    public void dateSelectedFecVen(SelectEvent f) {
        Date date = (Date) f.getObject();
        fec_ven = mformatsencillo.format(date);
    }

    public void onRowSelectBuscarDocInt(SelectEvent event) {
        bus_tip_doc_int = ((ListaDocumentosInternos) event.getObject()).getCod_tip_doc();
        bus_doc_int = ((ListaDocumentosInternos) event.getObject()).getDoc_int();
        bus_flg_ent_sal = ((ListaDocumentosInternos) event.getObject()).getFlg_ent_sal();
        bus_flg_cer_doc = ((ListaDocumentosInternos) event.getObject()).getFlg_cer_doc();
        bus_fec_tra = ((ListaDocumentosInternos) event.getObject()).getFec_tra();
        if (bus_fec_tra == null) {
            bus_fec_tra = "";
        }
        if ("00/00/0000".equals(bus_fec_tra)) {
            bus_fec_tra = "";
        }

        bus_anu_obs = "";
        if ("ANULADO".equals(bus_flg_cer_doc)) {
            bus_anu_obs = mquerys.strQuerySQLvariable("select obs_anu from inv_tra_his_mov "
                    + "where cod_emp = " + cod_emp + " "
                    + "and cod_tip_doc_int = " + bus_tip_doc_int + " "
                    + "and doc_int = '" + bus_doc_int + "' limit 1;");

            if (bus_anu_obs == null) {
                bus_anu_obs = "";
            }
            if (!"".equals(bus_anu_obs)) {
                RequestContext.getCurrentInstance().update("frmObsAnuInvArt");
                RequestContext.getCurrentInstance().execute("PF('wObsAnuInvArt').show()");
            }
        }

        llenarDetallesDocumentosInternos();
    }

    public void onRowUnselectBuscarDocInt() {
        bus_tip_doc_int = "";
        bus_doc_int = "";
        bus_flg_ent_sal = "";
        bus_flg_cer_doc = "";
        documentosintdetalle.clear();
    }

    public void onFilterBuscarDocInt() {
        bus_tip_doc_int = "";
        bus_doc_int = "";
        bus_flg_ent_sal = "";
        bus_flg_cer_doc = "";
        listadocumentosinternos = null;
        documentosintdetalle.clear();
    }

    public void recargarDocumentosInt() {
        RequestContext.getCurrentInstance().execute("PF('wvBuscarDocInvArt').clearFilters()");
        RequestContext.getCurrentInstance().update("frmBuscarDocInvArt");
        llenarDocumentosInternos();
        bus_tip_doc_int = "";
        bus_doc_int = "";
        bus_flg_ent_sal = "";
        bus_flg_cer_doc = "";
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

    //************** Getter y Setter ***********************
    public List<ListaCorta> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<ListaCorta> grupos) {
        this.grupos = grupos;
    }

    public List<ListaCorta> getLineas() {
        return lineas;
    }

    public void setLineas(List<ListaCorta> lineas) {
        this.lineas = lineas;
    }

    public List<ListaCorta> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<ListaCorta> marcas) {
        this.marcas = marcas;
    }

    public List<ListaCorta> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<ListaCorta> unidades) {
        this.unidades = unidades;
    }

    public List<ListaCorta> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<ListaCorta> bodegas) {
        this.bodegas = bodegas;
    }

    public List<ListaCorta> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<ListaCorta> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public List<ListaCorta> getLotes() {
        return lotes;
    }

    public void setLotes(List<ListaCorta> lotes) {
        this.lotes = lotes;
    }

    public List<ListaCorta> getCentroscosto() {
        return centroscosto;
    }

    public void setCentroscosto(List<ListaCorta> centroscosto) {
        this.centroscosto = centroscosto;
    }

    public List<ListaCorta> getTiposdocint() {
        return tiposdocint;
    }

    public void setTiposdocint(List<ListaCorta> tiposdocint) {
        this.tiposdocint = tiposdocint;
    }

    public List<ListaCorta> getTiposdocext() {
        return tiposdocext;
    }

    public void setTiposdocext(List<ListaCorta> tiposdocext) {
        this.tiposdocext = tiposdocext;
    }

    public List<ListaCorta> getClientesproveedores() {
        return clientesproveedores;
    }

    public void setClientesproveedores(List<ListaCorta> clientesproveedores) {
        this.clientesproveedores = clientesproveedores;
    }

    public Cat_articulos getCatarticulos() {
        return catarticulos;
    }

    public void setCatarticulos(Cat_articulos catarticulos) {
        this.catarticulos = catarticulos;
    }

    public List<Cat_articulos> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Cat_articulos> articulos) {
        this.articulos = articulos;
    }

    public Cat_existencias getCatinventario() {
        return catinventario;
    }

    public void setCatinventario(Cat_existencias catinventario) {
        this.catinventario = catinventario;
    }

    public List<Cat_existencias> getInventario() {
        return inventario;
    }

    public void setInventario(List<Cat_existencias> inventario) {
        this.inventario = inventario;
    }

    public String getArt_cod_art() {
        return art_cod_art;
    }

    public void setArt_cod_art(String art_cod_art) {
        this.art_cod_art = art_cod_art;
    }

    public String getArt_cod_emp() {
        return art_cod_emp;
    }

    public void setArt_cod_emp(String art_cod_emp) {
        this.art_cod_emp = art_cod_emp;
    }

    public String getArt_cod_gru() {
        return art_cod_gru;
    }

    public void setArt_cod_gru(String art_cod_gru) {
        this.art_cod_gru = art_cod_gru;
    }

    public String getArt_cod_lin() {
        return art_cod_lin;
    }

    public void setArt_cod_lin(String art_cod_lin) {
        this.art_cod_lin = art_cod_lin;
    }

    public String getArt_cod_ref() {
        return art_cod_ref;
    }

    public void setArt_cod_ref(String art_cod_ref) {
        this.art_cod_ref = art_cod_ref;
    }

    public String getArt_nom_art() {
        return art_nom_art;
    }

    public void setArt_nom_art(String art_nom_art) {
        this.art_nom_art = art_nom_art;
    }

    public String getArt_des_art() {
        return art_des_art;
    }

    public void setArt_des_art(String art_des_art) {
        this.art_des_art = art_des_art;
    }

    public String getArt_cod_mar() {
        return art_cod_mar;
    }

    public void setArt_cod_mar(String art_cod_mar) {
        this.art_cod_mar = art_cod_mar;
    }

    public String getArt_img_art() {
        return art_img_art;
    }

    public void setArt_img_art(String art_img_art) {
        this.art_img_art = art_img_art;
    }

    public String getArt_cod_uni_med() {
        return art_cod_uni_med;
    }

    public void setArt_cod_uni_med(String art_cod_uni_med) {
        this.art_cod_uni_med = art_cod_uni_med;
    }

    public String getArt_flg_exe() {
        return art_flg_exe;
    }

    public void setArt_flg_exe(String art_flg_exe) {
        this.art_flg_exe = art_flg_exe;
    }

    public String getArt_flg_per() {
        return art_flg_per;
    }

    public void setArt_flg_per(String art_flg_per) {
        this.art_flg_per = art_flg_per;
    }

    public String getArt_flg_usa_lot() {
        return art_flg_usa_lot;
    }

    public void setArt_flg_usa_lot(String art_flg_usa_lot) {
        this.art_flg_usa_lot = art_flg_usa_lot;
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

    public Double getCos_tot() {
        return cos_tot;
    }

    public void setCos_tot(Double cos_tot) {
        this.cos_tot = cos_tot;
    }

    public Double getCos_uni() {
        return cos_uni;
    }

    public void setCos_uni(Double cos_uni) {
        this.cos_uni = cos_uni;
    }

    public Double getExi_ant_emp() {
        return exi_ant_emp;
    }

    public void setExi_ant_emp(Double exi_ant_emp) {
        this.exi_ant_emp = exi_ant_emp;
    }

    public Double getExi_ant_suc() {
        return exi_ant_suc;
    }

    public void setExi_ant_suc(Double exi_ant_suc) {
        this.exi_ant_suc = exi_ant_suc;
    }

    public Double getExi_ant_bod() {
        return exi_ant_bod;
    }

    public void setExi_ant_bod(Double exi_ant_bod) {
        this.exi_ant_bod = exi_ant_bod;
    }

    public Double getExi_ant_lot() {
        return exi_ant_lot;
    }

    public void setExi_ant_lot(Double exi_ant_lot) {
        this.exi_ant_lot = exi_ant_lot;
    }

    public Double getExi_act_emp() {
        return exi_act_emp;
    }

    public void setExi_act_emp(Double exi_act_emp) {
        this.exi_act_emp = exi_act_emp;
    }

    public Double getExi_act_suc() {
        return exi_act_suc;
    }

    public void setExi_act_suc(Double exi_act_suc) {
        this.exi_act_suc = exi_act_suc;
    }

    public Double getExi_act_bod() {
        return exi_act_bod;
    }

    public void setExi_act_bod(Double exi_act_bod) {
        this.exi_act_bod = exi_act_bod;
    }

    public Double getExi_act_lot() {
        return exi_act_lot;
    }

    public void setExi_act_lot(Double exi_act_lot) {
        this.exi_act_lot = exi_act_lot;
    }

    public Double getCos_pro_emp() {
        return cos_pro_emp;
    }

    public void setCos_pro_emp(Double cos_pro_emp) {
        this.cos_pro_emp = cos_pro_emp;
    }

    public Double getCos_pro_suc() {
        return cos_pro_suc;
    }

    public void setCos_pro_suc(Double cos_pro_suc) {
        this.cos_pro_suc = cos_pro_suc;
    }

    public Double getCos_pro_cen_cos() {
        return cos_pro_cen_cos;
    }

    public void setCos_pro_cen_cos(Double cos_pro_cen_cos) {
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

    public Date getMfechatra() {
        return mfechatra;
    }

    public void setMfechatra(Date mfechatra) {
        this.mfechatra = mfechatra;
    }

    public Date getMfechaven() {
        return mfechaven;
    }

    public void setMfechaven(Date mfechaven) {
        this.mfechaven = mfechaven;
    }

    public DefaultStreamedContent getMimagen() {
        return mimagen;
    }

    public void setMimagen(DefaultStreamedContent mimagen) {
        xxxOLD.mimagen = mimagen;
    }

    public DefaultStreamedContent getMimagen2() {
        return mimagen2;
    }

    public void setMimagen2(DefaultStreamedContent mimagen2) {
        xxxOLD.mimagen2 = mimagen2;
    }

    public String getMwidth() {
        return mwidth;
    }

    public void setMwidth(String mwidth) {
        this.mwidth = mwidth;
    }

    public String getMheight() {
        return mheight;
    }

    public void setMheight(String mheight) {
        this.mheight = mheight;
    }

    public String getUsogruposlineas() {
        return usogruposlineas;
    }

    public void setUsogruposlineas(String usogruposlineas) {
        this.usogruposlineas = usogruposlineas;
    }

    public String getUsocodigoauto() {
        return usocodigoauto;
    }

    public void setUsocodigoauto(String usocodigoauto) {
        this.usocodigoauto = usocodigoauto;
    }

    public String getUsolote() {
        return usolote;
    }

    public void setUsolote(String usolote) {
        this.usolote = usolote;
    }

    public String getUsocosto() {
        return usocosto;
    }

    public void setUsocosto(String usocosto) {
        this.usocosto = usocosto;
    }

    public String getIngresarnuevosaldo() {
        return ingresarnuevosaldo;
    }

    public void setIngresarnuevosaldo(String ingresarnuevosaldo) {
        this.ingresarnuevosaldo = ingresarnuevosaldo;
    }

    public String getArt_flg_imp() {
        return art_flg_imp;
    }

    public void setArt_flg_imp(String art_flg_imp) {
        this.art_flg_imp = art_flg_imp;
    }

    public String getArt_flg_exp() {
        return art_flg_exp;
    }

    public void setArt_flg_exp(String art_flg_exp) {
        this.art_flg_exp = art_flg_exp;
    }

    public Double getExi_ant_ubi() {
        return exi_ant_ubi;
    }

    public void setExi_ant_ubi(Double exi_ant_ubi) {
        this.exi_ant_ubi = exi_ant_ubi;
    }

    public Double getExi_act_ubi() {
        return exi_act_ubi;
    }

    public void setExi_act_ubi(Double exi_act_ubi) {
        this.exi_act_ubi = exi_act_ubi;
    }

    public Double getExisantubi() {
        return exisantubi;
    }

    public void setExisantubi(Double exisantubi) {
        this.exisantubi = exisantubi;
    }

    public Double getExisactubi() {
        return exisactubi;
    }

    public void setExisactubi(Double exisactubi) {
        this.exisactubi = exisactubi;
    }

    public String getNombrebodega() {
        return nombrebodega;
    }

    public void setNombrebodega(String nombrebodega) {
        this.nombrebodega = nombrebodega;
    }

    public String getNombreunimed() {
        return nombreunimed;
    }

    public void setNombreunimed(String nombreunimed) {
        this.nombreunimed = nombreunimed;
    }

    public String getUnimedori() {
        return unimedori;
    }

    public void setUnimedori(String unimedori) {
        this.unimedori = unimedori;
    }

    public String getUnimedsal() {
        return unimedsal;
    }

    public void setUnimedsal(String unimedsal) {
        this.unimedsal = unimedsal;
    }

    public String getTituloclienteproveedor() {
        return tituloclienteproveedor;
    }

    public void setTituloclienteproveedor(String tituloclienteproveedor) {
        this.tituloclienteproveedor = tituloclienteproveedor;
    }

    public String getUsofechaven() {
        return usofechaven;
    }

    public void setUsofechaven(String usofechaven) {
        this.usofechaven = usofechaven;
    }

    public String getNombrearticulo() {
        return nombrearticulo;
    }

    public void setNombrearticulo(String nombrearticulo) {
        this.nombrearticulo = nombrearticulo;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getNombremarca() {
        return nombremarca;
    }

    public void setNombremarca(String nombremarca) {
        this.nombremarca = nombremarca;
    }

    public ListaDocumentosInternos getListadocumentosinternos() {
        return listadocumentosinternos;
    }

    public void setListadocumentosinternos(ListaDocumentosInternos listadocumentosinternos) {
        this.listadocumentosinternos = listadocumentosinternos;
    }

    public List<ListaDocumentosInternos> getDocumentosint() {
        return documentosint;
    }

    public void setDocumentosint(List<ListaDocumentosInternos> documentosint) {
        this.documentosint = documentosint;
    }

    public List<ListaDetalleDocumentosInternos> getDocumentosintdetalle() {
        return documentosintdetalle;
    }

    public void setDocumentosintdetalle(List<ListaDetalleDocumentosInternos> documentosintdetalle) {
        this.documentosintdetalle = documentosintdetalle;
    }

    public String getBus_limit() {
        return bus_limit;
    }

    public void setBus_limit(String bus_limit) {
        this.bus_limit = bus_limit;
    }

    public String getBus_anu_obs() {
        return bus_anu_obs;
    }

    public void setBus_anu_obs(String bus_anu_obs) {
        this.bus_anu_obs = bus_anu_obs;
    }

    public String getBus_doc_int() {
        return bus_doc_int;
    }

    public void setBus_doc_int(String bus_doc_int) {
        this.bus_doc_int = bus_doc_int;
    }

    public List<ListaCorta> getDocintext() {
        return docintext;
    }

    public void setDocintext(List<ListaCorta> docintext) {
        this.docintext = docintext;
    }

    public String getBus_ope_ret() {
        return bus_ope_ret;
    }

    public void setBus_ope_ret(String bus_ope_ret) {
        this.bus_ope_ret = bus_ope_ret;
    }

    public List<ListaCorta> getTemperaturas() {
        return temperaturas;
    }

    public void setTemperaturas(List<ListaCorta> temperaturas) {
        this.temperaturas = temperaturas;
    }

    public String getArt_cod_tem() {
        return art_cod_tem;
    }

    public void setArt_cod_tem(String art_cod_tem) {
        this.art_cod_tem = art_cod_tem;
    }

    public String getArt_reg_san() {
        return art_reg_san;
    }

    public void setArt_reg_san(String art_reg_san) {
        this.art_reg_san = art_reg_san;
    }

    public String getUbi_cod_rel() {
        return ubi_cod_rel;
    }

    public void setUbi_cod_rel(String ubi_cod_rel) {
        this.ubi_cod_rel = ubi_cod_rel;
    }

    public String getUbi_cod_bod() {
        return ubi_cod_bod;
    }

    public void setUbi_cod_bod(String ubi_cod_bod) {
        this.ubi_cod_bod = ubi_cod_bod;
    }

    public String getUbi_des_ubi() {
        return ubi_des_ubi;
    }

    public void setUbi_des_ubi(String ubi_des_ubi) {
        this.ubi_des_ubi = ubi_des_ubi;
    }

}
