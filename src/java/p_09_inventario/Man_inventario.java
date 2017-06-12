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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperRunManager;
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
public class Man_inventario implements Serializable {

    @Inject
    Login cbean;

    private Querys macc;

    //********************** Generales ***********************************
    private List<ListaCorta> centroscosto;
    private List<ListaCorta> Querys;

    private String cod_emp, cod_suc, usogruposlineas, usocodigoauto, usolote, usocosto, ingresarnuevosaldo, usofechaven, index;

    //********************** Artículos **********************************
    private List<ListaCorta> grupos;
    private List<ListaCorta> lineas;
    private List<ListaCorta> marcas;
    private List<ListaCorta> temperaturas;
    private List<ListaCorta> unidades;

    private Cat_articulos catarticulos;
    private List<Cat_articulos> articulos;

    private String art_cod_art, art_cod_emp, art_cod_gru, art_cod_lin, art_cod_ref, art_cod_alt, art_nom_art, art_des_art,
            art_cod_mar, art_img_art, art_cod_uni_med, art_flg_exe, art_flg_per, art_flg_usa_lot, art_flg_imp, art_flg_exp,
            art_cod_tem, art_reg_san, art_flg_pro_com;

    private Double art_pre_sin_iva;

    //********************** Existencias *********************************
    private Cat_lotesVencimiento catlotesvencimiento;
    private List<Cat_lotesVencimiento> lotesvencimiento;

    private List<ListaCorta> paisesbus;
    private List<ListaCorta> bodegasbus;
    private List<ListaCorta> ubicacionesbus;

    private List<ListaCorta> movimientostotal;

    private String fecbus, idbus, movbus, paibus, bodbus, ubibus, probus, lotbus;

    //********************** Almacén **************************************
    private List<ListaCorta> clientesproveedores;
    private List<ListaCorta> areas;
    private List<ListaCorta> tiposdocint;
    private List<ListaCorta> tiposdocext;

    private List<ListaCorta> responsables;
    private List<ListaCorta> bodegas;
    private List<ListaCorta> ubicaciones;
    private List<ListaCorta> lotes;
    private List<ListaCorta> productos;

    private Cat_transacciones cattransacciones;
    private List<Cat_transacciones> encabezado;
    private Cat_transaccionesDetalle cattransaccionesdetalle;
    private List<Cat_transaccionesDetalle> detalles;
    private List<Cat_transaccionesDetalle> historico;

    private List<ListaCortaTransaccionExistencias> hisexi;

    private Cat_existencias catinventario;
    private List<Cat_existencias> inventario;

    private String cod_tra, cod_pai, fec_tra, flg_ing_sal, cod_tip_doc_int, doc_int, cod_tip_doc_ext, doc_ext, cod_cli_pro, det_obs, cod_usu, flg_anu;
    private String cod_res, cod_are, obs_anu;

    private String cod_det, cod_bod, cod_ubi, cod_art, det_lot, fec_ven, nombod, nomubi, nomart, uni_med_con;
    private Double det_can, det_cos, det_can_con;

    private String chklot, chkfecven, titulo, nomprod, unimedori, nombreunidad, nombremarca, existenciareal;
    private String anu_cod_tip_doc_int;

    private String tra_cod_tip_doc_int, tra_cod_bod;

    private String ven_cod_bod;

    private Date mfechatra;

    //********************** Varios ***************************************
    private String boolinout, booleditable;

    private Date mfecha, mfechaven;

    private String editfecvennomart;
    private Date datefecvenedit;

    private int tipoInicio;

    private String mheight, mheight2;

    private static DefaultStreamedContent mimagen, mimagen2;

    private String ubi_cod_rel, ubi_cod_bod, ubi_des_ubi;

    private Errores err;

    public Man_inventario() {
    }

    //****************************************** Inicio ******************************************
    public void iniciarventana() {
        tipoInicio = 0;
        macc = new Querys();
        err = new Errores();

        //******* Imágenes ****************
        mheight = "150";
        mheight2 = "170";
        mimagen = null;
        mimagen2 = null;

        //****** Configuración global ******
        usogruposlineas = cbean.getFlg_uso_gru_lin_art();
        usocodigoauto = cbean.getFlg_aut_cod_art();
        usolote = cbean.getFlg_uso_lot_art();
        usocosto = cbean.getFlg_cos();

        cod_emp = cbean.getCod_emp();
        cod_suc = cbean.getCod_suc_2();
        cod_bod = cbean.getCod_bod();

        //****** Configuración Local ******
        index = "1";

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
        art_pre_sin_iva = 0.0;

        //********** Almacén ************
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        mfecha = Date.from(Instant.now());
        fec_tra = format.format(mfecha);
        //********* Listas **************
        grupos = new ArrayList<>();
        lineas = new ArrayList<>();
        marcas = new ArrayList<>();
        unidades = new ArrayList<>();
        temperaturas = new ArrayList<>();

        catarticulos = new Cat_articulos();
        articulos = new ArrayList<>();

        catinventario = new Cat_existencias();
        inventario = new ArrayList<>();

        clientesproveedores = new ArrayList<>();
        areas = new ArrayList<>();
        tiposdocint = new ArrayList<>();
        tiposdocext = new ArrayList<>();

        responsables = new ArrayList<>();
        bodegas = new ArrayList<>();
        ubicaciones = new ArrayList<>();
        lotes = new ArrayList<>();
        productos = new ArrayList<>();

        Querys = new ArrayList<>();

        cattransacciones = new Cat_transacciones();
        encabezado = new ArrayList<>();
        cattransaccionesdetalle = new Cat_transaccionesDetalle();
        detalles = new ArrayList<>();
        historico = new ArrayList<>();

        catlotesvencimiento = new Cat_lotesVencimiento();

        paisesbus = new ArrayList<>();
        lotesvencimiento = new ArrayList<>();
        bodegasbus = new ArrayList<>();
        ubicacionesbus = new ArrayList<>();

        //******** Llenado de Catálogos ******
        llenarTemperaturas();
        llenarUnidadesMedida();
        llenarMarcas();
        llenarBodegas();
        llenarExistenciasActuales();

    }

    public void cerrarventana() {
        cod_tra = "";
        cod_pai = "";
        fec_tra = "";
        flg_ing_sal = "false";

        doc_ext = "";
        cod_cli_pro = "0";
        det_obs = "";
        cod_usu = "";
        flg_anu = "0";
        cod_res = "";
        doc_int = "";
        cod_are = "";
        obs_anu = "";

        cod_det = "";
        cod_bod = "0";
        cod_ubi = "0";
        cod_art = "0";
        det_lot = "";
        fec_ven = "";
        det_can = 0.0;
        det_cos = 0.0;
        nombod = "";
        nomubi = "";
        nomart = "";
        uni_med_con = "";
        unimedori = "";

        titulo = "";
        boolinout = "false";
        booleditable = "true";
        chkfecven = "false";
        nomprod = "";
        nombremarca = "";
        nombreunidad = "";

        mimagen = null;
        mheight = "260";

        movimientostotal = null;
        responsables = null;
        areas = null;

        catinventario = null;
        inventario = null;

        lotes = null;
        ubicaciones = null;
        bodegas = null;
        articulos = null;
        unidades = null;

        cattransacciones = null;
        encabezado = null;

        cattransaccionesdetalle = null;
        detalles = null;
        historico = null;
        hisexi = null;
        Querys = null;

        paisesbus = null;
        bodegasbus = null;
        ubicacionesbus = null;

        catlotesvencimiento = null;
        lotesvencimiento = null;

        macc = null;

    }

    //******************************** Artículos ******************************
    public void nuevoarticulo() {
        art_cod_art = "";
        art_cod_emp = cbean.getCod_emp();
        art_cod_gru = "0";
        art_cod_lin = "0";
        art_cod_ref = "";
        art_cod_alt = "";
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
        art_flg_pro_com = "false";
        art_pre_sin_iva = 0.0;

        //******* Imágenes ****************
        mheight = "150";
        mimagen = null;
        mimagen2 = null;

        catarticulos = null;

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
                if (!"0".equals(macc.strQuerySQLvariable("select count(cod_art) "
                        + "FROM inv_cat_art "
                        + "where upper(art_cod_ref)='" + art_cod_ref.toUpperCase() + "' "
                        + "and cod_emp=" + art_cod_emp + ";"))) {
                    mvalidar = false;
                    addMessage("Validar Datos", "Este Código de Articulo ya existe", 3);
                }

            } else if (!"0".equals(macc.strQuerySQLvariable("select count(cod_art) "
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
            if (!art_cod_uni_med.equals(macc.strQuerySQLvariable("select cod_uni_med from inv_cat_art where cod_art=" + art_cod_art + ";"))) {
                if (!"0".equals(macc.strQuerySQLvariable("select count(cod_art) from inv_tra_his_mos where cod_art = " + art_cod_art + ";"))) {
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
                    art_cod_art = macc.strQuerySQLvariable(mQuery);
                    if ("false".equals(cbean.getFlg_aut_cod_art())) {
                        if ("false".equals(cbean.getFlg_uso_gru_lin_art())) {
                            art_cod_ref = macc.strQuerySQLvariable("select concat(lpad('" + art_cod_gru + "',2,'0'),'-',lpad('" + art_cod_lin + "',2,'0'),'-',lpad('" + art_cod_art + "',4,'0')) as cod;");
                        } else {
                            art_cod_ref = macc.strQuerySQLvariable("select concat('A',lpad('" + art_cod_art + "',4,'0')) as cod;");
                        }
                    }
                    mQuery = "insert into inv_cat_art (cod_art,cod_emp,cod_gru,cod_lin,cod_ref, cod_alt,nom_art,des_art,"
                            + "cod_mar,img_art, cod_uni_med, cod_tem, reg_san, flg_exe, flg_per, flg_usa_lot,flg_imp,flg_exp,"
                            + "flg_pro_com,pre_sin_iva,usu_mod,fec_mod,tip_mod) VALUES ("
                            + art_cod_art + "," + art_cod_emp + "," + art_cod_gru + "," + art_cod_lin
                            + ",'" + (art_cod_ref.replace("\\", "\\\\")).replace("'", "\\'") + "' "
                            + ",'" + (art_cod_alt.replace("\\", "\\\\")).replace("'", "\\'") + "' "
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
                            + ",'" + art_flg_pro_com + "' "
                            + "," + art_pre_sin_iva + " "
                            + "," + cbean.getCod_usu() + " "
                            + ",now()"
                            + ",'AGREGAR NUEVO ARTÍCULO');";
                } else {
                    mQuery = "update inv_cat_art set "
                            + "cod_gru= " + art_cod_gru + ","
                            + "cod_lin= " + art_cod_lin + ","
                            + "cod_ref= '" + (art_cod_ref.replace("\\", "\\\\")).replace("'", "\\'") + "',"
                            + "cod_alt= '" + (art_cod_alt.replace("\\", "\\\\")).replace("'", "\\'") + "',"
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
                            + "flg_pro_com = '" + art_flg_pro_com + "',"
                            + "pre_sin_iva=" + art_pre_sin_iva + ", "
                            + "usu_mod = " + cbean.getCod_usu() + ","
                            + "fec_mod= now(), "
                            + "tip_mod= 'EDICIÓN NORMAL DE ARTÍCULO' "
                            + "where cod_art = " + art_cod_art + ";";
                }
                macc.dmlSQLvariable(mQuery);

                // ****************************  Inserta Imagen en tabla ************************************************************
                if (!"".equals(art_img_art)) {
                    try {
                        FileInputStream fis = null;
                        PreparedStatement ps = null;
                        File file = null;
                        try {
                            macc.Conectar().setAutoCommit(false);
                            file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(art_img_art));
                            fis = new FileInputStream(file);
                            mQuery = "update inv_cat_art set img_art = ? where cod_art=?;";
                            ps = macc.Conectar().prepareStatement(mQuery);
                            ps.setBinaryStream(1, fis, (int) file.length());
                            ps.setString(2, art_cod_art);
                            ps.executeUpdate();
                            macc.Conectar().commit();

                        } catch (Exception ex) {

                        } finally {
                            try {
                                ps.close();
                                fis.close();
                                if (file.exists()) {
                                    file.delete();
                                }
                                file = null;
                                macc.Desconectar();
                            } catch (Exception ex) {
                                err.auditarError("Man_inventario", "guardararticulo", "Error en cerrar flujos edición imagen de Artículo. " + ex.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
                            }
                        }

                    } catch (Exception e) {
                        err.auditarError("Man_inventario", "guardararticulo", "Error en editar imagen de guardar Artículo. " + (e.getMessage().replace("\\", "\\\\")).replace("'", "\\'"), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
                    }
                }

                addMessage("Guardar", "La Información ha sido almacenada con éxito.", 1);
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

            if (!"0".equals(macc.strQuerySQLvariable("select count(cod_art) "
                    + "FROM fac_tra_cli "
                    + "where cod_art=" + art_cod_art + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Este artículo tiene registros relacionados en Transacciones de Cliente y no puede ser eliminado", 3);
            }

            if (!"0".equals(macc.strQuerySQLvariable("select count(cod_art) "
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
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from inv_cat_art where cod_art=" + art_cod_art + ";";
                macc.dmlSQLvariable(mQuery);
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
            ubi_cod_bod = macc.strQuerySQLvariable("select cod_bod  FROM inv_cat_art_rel_ubi where cod_suc = " + cod_suc + " and cod_art = " + art_cod_art + " limit 1;");
            if (ubi_cod_bod == null) {
                ubi_cod_bod = "";
            }
            if ("".equals(ubi_cod_bod)) {
                ubi_cod_bod = "0";
            }
            ubi_des_ubi = macc.strQuerySQLvariable("select des_ubi  FROM inv_cat_art_rel_ubi where cod_suc = " + cod_suc + " and cod_art = " + art_cod_art + " and cod_bod = " + ubi_cod_bod + ";");
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
            mheight = "150";

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
            Blob miBlob = macc.blobQuerySQLvariable(mQuery);
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
                miBlob = macc.blobQuerySQLvariable(mQuery);
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

    public void llenarMarcas() {
        String mQuery = "";
        try {
            marcas.clear();

            mQuery = "select cod_mar, nom_mar "
                    + "from gen_cat_mar order by nom_mar;";

            ResultSet resVariable;
            macc.Conectar();

            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                marcas.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

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
            macc.Conectar();

            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                temperaturas.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarTemperaturas", "Error en el llenado de Temperaturas. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarArticulos() {
        String mQuery = "";
        try {
            catarticulos = null;
            articulos.clear();

            mQuery = "select cod_art, cod_emp, cod_gru, cod_lin, cod_ref, cod_alt, nom_art, des_art, cod_mar, '' as img_art, "
                    + "cod_uni_med,cod_tem,reg_san, flg_exe, flg_per, flg_usa_lot, flg_imp, flg_exp, flg_pro_com, pre_sin_iva "
                    + "from inv_cat_art "
                    + "where cod_emp = " + cod_emp + " "
                    + "and cod_art <> 0 "
                    + "order by cod_ref;";

            ResultSet resVariable;
            macc.Conectar();

            resVariable = macc.querySQLvariable(mQuery);
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
                        resVariable.getString(18),
                        resVariable.getString(19),
                        resVariable.getString(20)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarArticulos", "Error en el llenado de Artículos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

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
        art_cod_alt = ((Cat_articulos) event.getObject()).getCod_alt();
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
        art_flg_pro_com = ((Cat_articulos) event.getObject()).getFlg_pro_com();
        art_pre_sin_iva = Double.valueOf(((Cat_articulos) event.getObject()).getPre_sin_iva());

        recuperarimagen();

        RequestContext.getCurrentInstance().execute("PF('wvArtEdit').clearFilters()");
        RequestContext.getCurrentInstance().execute("PF('wArtEdit').hide()");

    }

    //********************** Existencias ***************************************
    //********************** Entradas y Salidas ********************************
    public void nuevoentradasalida() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        mfecha = Date.from(Instant.now());
        cod_tra = "";
        cod_pai = cbean.getCod_pai();
        fec_tra = format.format(mfecha);
        flg_ing_sal = "false";
        cod_tip_doc_int = "0";
        doc_int = "";
        cod_tip_doc_ext = "0";
        doc_ext = "";
        cod_cli_pro = "0";
        det_obs = "";
        cod_usu = cbean.getCod_usu();
        flg_anu = "0";
        cod_res = "0";

        cod_are = "0";
        obs_anu = "";

        cod_det = "";
        cod_bod = "0";
        cod_ubi = "0";
        cod_art = "0";
        det_lot = "";
        fec_ven = "";
        det_can = 0.0;
        det_cos = 0.0;
        nombod = "";
        nomubi = "";
        nomart = "";
        uni_med_con = "0";
        unimedori = "0";

        titulo = "Proveedor";
        chkfecven = "false";
        nomprod = "";
        nombremarca = "";
        nombreunidad = "";
        existenciareal = "0";

        usofechaven = "false";

        mimagen2 = null;
        mheight2 = "170";

        cattransaccionesdetalle = null;
        detalles.clear();

        llenarDocumentosInt();
        llenarDocumentosExt();
        llenarClientesProveedores();
        llenarAreas();
        llenarResponsables();
        llenarProductos();

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

        if (mvalidar) {

            nuevoentradasalida();

            llenarUnidades();

            llenarBodegas();
            llenarProductos();

            RequestContext.getCurrentInstance().update("frmInventarioPro");
            RequestContext.getCurrentInstance().execute("PF('wInventarioPro').show()");
        } else {
            RequestContext.getCurrentInstance().update("frmInventarioArt");
        }

    }

    public void cerrarEntradasSalidas() {
        mfecha = null;
        cod_tra = "";
        cod_pai = "";
        fec_tra = "";
        flg_ing_sal = "false";
        cod_tip_doc_int = "";
        doc_int = "";
        cod_tip_doc_ext = "";
        doc_ext = "";
        cod_cli_pro = "";
        det_obs = "";
        cod_usu = "";
        flg_anu = "";
        cod_res = "";

        cod_are = "";
        obs_anu = "";

        cod_det = "";
        cod_bod = "";
        cod_ubi = "";
        cod_art = "";
        det_lot = "";
        fec_ven = "";
        det_can = 0.0;
        det_cos = 0.0;
        nombod = "";
        nomubi = "";
        nomart = "";
        uni_med_con = "";
        unimedori = "";

        titulo = "";
        chkfecven = "";
        nomprod = "";
        nombremarca = "";
        nombreunidad = "";
        existenciareal = "0";

        usofechaven = "";

        mimagen2 = null;
        mheight2 = "170";

        cattransaccionesdetalle = null;
        detalles.clear();

        /*
        tiposdocint = null;
        tiposdocext = null;
        areas = null;
        responsables = null;
        ubicaciones = null;
        lotes = null;
        productos = null;
        clientesproveedores = null;
         */
    }

    //*****************Llenado de catálogos ************************************
    public void llenarClientesProveedores() {
        String mQuery = "";
        try {

            clientesproveedores.clear();

            if ("false".equals(flg_ing_sal)) {
                mQuery = "select cod_pro, concat(nom_pro,' ',ape_pro) "
                        + "from inv_cat_pro where cod_emp = " + cod_emp + " order by ape_pro, nom_pro;";
                titulo = "Proveedor";
            } else {
                mQuery = "select cod_cli,concat(nom_cli,' ',ape_cli)  "
                        + "from fac_cat_cli where cod_emp = " + cod_emp + " order by ape_cli, nom_cli;";
                titulo = "Cliente";
            }

            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                clientesproveedores.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarProveedores", "Error en el llenado de Proveedores. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarDocumentosInt() {
        String mQuery = "";
        try {
            tiposdocint.clear();

            mQuery = "select cod_tip_doc, nom_tip_doc "
                    + "from gen_cat_tip_doc_int "
                    + "where flg_ent_sal ='" + flg_ing_sal + "' "
                    + "order by nom_tip_doc;";

            ResultSet resVariable;
            macc.Conectar();

            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                tiposdocint.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

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
                    + "where flg_ent_sal ='" + flg_ing_sal + "' "
                    + "order by nom_tip_doc;";

            ResultSet resVariable;
            macc.Conectar();

            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                tiposdocext.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarDocumentosExt", "Error en el llenado de Documentos Externos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarAreas() {
        String mQuery = "";
        try {
            areas.clear();

            mQuery = "select cod_are, nom_are "
                    + "from fac_cat_are_cli order by nom_are;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                areas.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarAreas", "Error en el llenado de Áreas. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarResponsables() {
        String mQuery = "";
        try {
            responsables.clear();
            if ("true".equals(flg_ing_sal)) {
                mQuery = "select cod_per,concat(det_nom, ' ' , det_ape) as nombre "
                        + "from rhu_cat_per "
                        + "where cod_emp = " + cod_emp + " "
                        + "order by det_ape;";
                ResultSet resVariable;

                macc.Conectar();
                resVariable = macc.querySQLvariable(mQuery);
                while (resVariable.next()) {
                    responsables.add(new ListaCorta(
                            resVariable.getString(1),
                            resVariable.getString(2)
                    ));
                }
                resVariable.close();
                macc.Desconectar();
            }

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarResponsables", "Error en el llenado de Responsables. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarUnidades() {
        String mQuery = "";
        unidades.clear();
        try {

            mQuery = "select cod_uni, nom_uni "
                    + "from gen_cat_uni_med "
                    + "order by nom_uni;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                unidades.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarUnidades", "Error en el llenado de Unidades de Medida. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
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
            macc.Conectar();

            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegas.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarBodegas", "Error en el llenado de Bodegas. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }

    }

    public void llenarUbicaciones() {
        String mQuery = "";
        try {

            ubicaciones.clear();

            mQuery = "select ubi.cod_ubi,ubi.nom_ubi "
                    + "from inv_cat_ubi as ubi "
                    + "left join inv_cat_bod as bod on bod.cod_bod = ubi.cod_bod "
                    + "where bod.cod_suc = " + cod_suc + " "
                    + "and ubi.cod_bod=" + cod_bod + " "
                    + "order by ubi.cod_bod,ubi.cod_ubi;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                ubicaciones.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

            lotes.clear();
            det_lot = "";
            mfechaven = null;
            chkfecven = "false";
        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarUbicaciones", "Error en el llenado de Ubicaciones. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarProductos() {
        String mQuery = "";
        try {
            lotes.clear();
            productos.clear();

            mQuery = "select art.cod_art, concat(ifnull(art.cod_ref,''), '--', ifnull(art.cod_alt,''), '--', art.nom_art,'--',ifnull(mar.nom_mar,'')) as nomart "
                    + "from inv_cat_art as art "
                    + "left join gen_cat_mar as mar on art.cod_mar = mar.cod_mar "
                    + "where art.cod_emp = " + cod_emp + " "
                    + "and art.cod_art <> 0 "
                    + "order by art.cod_ref;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                productos.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarProductos", "Error en el llenado de Productos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarLotes() {
        String mQuery = "";
        try {

            lotes.clear();

            mQuery = "select distinct(det_lot) FROM inv_tra_his where cod_art = " + cod_art + " and cod_bod = " + cod_bod + " order by det_lot;";

            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                lotes.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(1)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarLotes", "Error en el llenado de Lotes. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarExistenciasActuales() {
        String mQuery = "";
        try {
            catinventario = null;
            inventario.clear();

            mQuery = "select '' as cod_his, '' as fec_tra, t.cod_art,t.cod_bod, t.nomubi, t.detlot, t.fecven, '' as cod_ord, 0 as exi_ant_bod, t.existencia, 0 as cos_pro_suc,'' as cod_ref, t.nomart, t.nomemb,t.nombod   "
                    + "from (  "
                    + "select   "
                    + "mae.cod_suc,  "
                    + "det.cod_bod,  "
                    + "0 as cod_ubi,  "
                    + "art.cod_art,  "
                    + "art.cod_uni_med,  "
                    + "'TODOS' as detlot,  "
                    + "'' as fecven,  "
                    + "ifnull((select his.exi_act_bod from inv_tra_his as his where his.cod_suc = mae.cod_suc and his.cod_bod = det.cod_bod and his.cod_art = det.cod_art and his.fec_tra <= STR_TO_DATE('" + fec_tra + "', '%d/%m/%Y') order by his.fec_tra desc, his.ord_dia desc limit 1),0) as existencia,  "
                    + "ifnull(pai.nom_suc,'') as nompai,  "
                    + "ifnull(bod.nom_bod,'') as nombod,  "
                    + "ifnull(ubi.nom_ubi,'') as nomubi,  "
                    + "concat(art.cod_ref,'--',ifnull(art.cod_alt,''),'--',art.nom_art) as nomart,  "
                    + "ifnull(emb.nom_uni,'') as nomemb   "
                    + "from inv_cat_art as art  "
                    + "left join gen_cat_uni_med as emb on art.cod_uni_med = emb.cod_uni  "
                    + "left join inv_tbl_tra_det as det on art.cod_art = det.cod_art   "
                    + "left join inv_tbl_tra as mae on det.cod_tra = mae.cod_tra  "
                    + "left join gen_cat_suc as pai on mae.cod_suc = pai.cod_suc  "
                    + "left join inv_cat_bod as bod on mae.cod_suc = bod.cod_suc and det.cod_bod = bod.cod_bod  "
                    + "left join inv_cat_ubi as ubi on bod.cod_bod = ubi.cod_bod and det.cod_ubi = ubi.cod_ubi  "
                    + "where mae.cod_suc =  " + cod_suc + "   "
                    + "and mae.flg_ing_sal = 'false'  "
                    + "group by mae.cod_suc,  "
                    + "det.cod_bod,  "
                    + "art.cod_art,  "
                    + "art.cod_uni_med,  "
                    + "pai.nom_suc,  "
                    + "bod.nom_bod,  "
                    + "ubi.nom_ubi,  "
                    + "art.cod_ref,  "
                    + "art.cod_alt,  "
                    + "art.nom_art,  "
                    + "emb.nom_uni ) as t  "
                    + "where t.existencia > 0 "
                    + "and t.cod_bod = " + cod_bod + " "
                    + "order by t.nomart;  ";

            ResultSet resVariable;
            macc.Conectar();

            resVariable = macc.querySQLvariable(mQuery);
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
            macc.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarExistenciasActuales", "Error en el llenado de Existencias de Inventario. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    //************************* Detalle **************************************
    public boolean validardetalle() {
        boolean mvalidar = true;

        if (fec_ven.equals("00/00/0000")) {
            fec_ven = "";
        }
        if (fec_ven == null) {
            fec_ven = "";
        }
        if (det_lot == null) {
            det_lot = "";
        }
        if ("0".equals(cod_pai)) {
            addMessage("Validar Datos", "Debe Seleccionar un País", 3);
            return false;
        }
        if ("0".equals(cod_art)) {
            addMessage("Validar Datos", "Debe Seleccionar un Producto", 3);
            return false;
        }
        if ("0".equals(cod_bod)) {
            addMessage("Validar Datos", "Debe escoger una Bodega", 3);
            return false;
        }
        if (det_can <= 0.0) {
            addMessage("Validar Datos", "Debe Ingresar una Cantidad mayor que Cero", 3);
            return false;
        }

        //**************** Conversión de Unidades ***************************************************************************
        Double mFactor = 0.0;
        if ("0".equals(uni_med_con)) {
            addMessage("Validar Datos", "Debe Seleccionar una Unidad de Medida", 3);
            return false;
        } else if (!unimedori.equals(uni_med_con)) {
           
            try {
                mFactor = macc.doubleQuerySQLvariable("select ifnull(can_uni_ini/can_uni_fin,0.0) as factor "
                        + "from gen_cat_uni_med_rel "
                        + "where cod_uni_ini = " + unimedori + " "
                        + "and cod_uni_fin = " + uni_med_con + ";");
            } catch (Exception e) {
                mFactor = 0.0;
            }
            if (mFactor == null) {
                mFactor = 0.0;
            }
            if (mFactor == 0.0) {
                try {
                    mFactor = macc.doubleQuerySQLvariable("select ifnull(can_uni_fin/can_uni_ini,0.0) as factor "
                            + "from gen_cat_uni_med_rel "
                            + "where cod_uni_fin = " + unimedori + " "
                            + "and cod_uni_ini = " + uni_med_con + ";");
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
            mExi = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                    + "from inv_tra_his "
                    + "where "
                    + "cod_art=" + cod_art + " "
                    + "and cod_suc = " + cod_suc + " "
                    + "and cod_bod=" + cod_bod + " "
                    + "and det_lot='" + det_lot + "' "
                    + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                    + "order by fec_tra desc,"
                    + "ord_dia desc "
                    + "limit 1;");
        } catch (Exception e) {
            mExi = 0.0;
        }
       
        Double exigrid = 0.0;
        if (!detalles.isEmpty()) {
            for (int i = 0; i < detalles.size(); i++) {
                if (cod_bod.equals(detalles.get(i).getCod_bod())
                        && cod_art.equals(detalles.get(i).getCod_art())
                        && det_lot.equals(detalles.get(i).getDet_lot())) {
                    exigrid = exigrid + Double.valueOf(detalles.get(i).getDet_can());
                }
            }
        }

        if ("true".equals(flg_ing_sal)) {

            if ((det_can * mFactor) + exigrid > mExi) {
                if ("".equals(det_lot)) {
                    addMessage("Validar Datos", "La Cantidad Solicitada Sobrepasa las Existencias en esta Bodega", 3);
                } else {
                    addMessage("Validar Datos", "La Cantidad Solicitada del Lote " + det_lot + " Sobrepasa las Existencias en esta Bodega", 3);
                }
                return false;
            }

        } else {
            Double mMax = 0.0;
           
            try {
                mMax = Double.valueOf(macc.strQuerySQLvariable("select ifnull(pun_max,0.0) "
                        + "from inv_cat_pun_ale "
                        + "where cod_bod = " + cod_bod + " "
                        + "and cod_art = " + cod_art
                        + ";"));
            } catch (Exception e) {
                mMax = 0.0;
            }
           
            if (mMax > 0.0) {
                if (mExi + det_can * mFactor + exigrid > mMax) {
                    addMessage("Validar Datos", "La Cantidad Ingresada Sobrepasa el Máximo permitido en esta Bodega", 3);
                    return false;
                }
            }

        }

        if (mvalidar) {
            det_can_con = det_can;
            det_can = det_can * mFactor;
        }

        return mvalidar;
    }

    public void agregardetalle() {
        try {
            if (validardetalle()) {
               
                int correlativo = 0, insert = 0;
                Double mcantidad;

                for (int i = 0; i < detalles.size(); i++) {

                    if (cod_art.equals(detalles.get(i).getCod_art())
                            && cod_bod.equals(detalles.get(i).getCod_bod())
                            && cod_ubi.equals(detalles.get(i).getCod_ubi())
                            && det_lot.equals(detalles.get(i).getDet_lot())
                            && fec_ven.equals(detalles.get(i).getFec_ven())
                            && uni_med_con.equals(detalles.get(i).getUni_med_con())) {

                        if ("false".equals(boolinout)) {
                            mcantidad = Double.valueOf(detalles.get(i).getDet_can()) + det_can;
                        } else {
                            mcantidad = Double.valueOf(detalles.get(i).getDet_can()) + det_can;
                        }

                        insert = 1;
                        detalles.set(i, new Cat_transaccionesDetalle(
                                detalles.get(i).getCod_tra(),
                                detalles.get(i).getCod_det(),
                                detalles.get(i).getCod_bod(),
                                detalles.get(i).getCod_ubi(),
                                detalles.get(i).getCod_art(),
                                detalles.get(i).getDet_lot(),
                                detalles.get(i).getFec_ven(),
                                String.valueOf(mcantidad),
                                detalles.get(i).getNombod(),
                                detalles.get(i).getNomubi(),
                                detalles.get(i).getNomart(),
                                detalles.get(i).getDet_can_con(),
                                detalles.get(i).getUni_med_con(),
                                detalles.get(i).getDet_cos(),
                                detalles.get(i).getCodrefart(),
                                detalles.get(i).getNomunimed()
                        ));
                    }

                    if (Integer.valueOf(detalles.get(i).getCod_det()) > correlativo) {
                        correlativo = Integer.valueOf(detalles.get(i).getCod_det());
                    }
                }

                if (insert == 0) {
                    detalles.add(new Cat_transaccionesDetalle(
                            "",
                            String.valueOf(correlativo + 1),
                            cod_bod,
                            cod_ubi,
                            cod_art,
                            det_lot.trim(),
                            fec_ven,
                            String.valueOf(det_can),
                            macc.strQuerySQLvariable("select nom_bod from inv_cat_bod where cod_suc = " + cod_suc + " and cod_bod =" + cod_bod + ";"),
                            macc.strQuerySQLvariable("select nom_ubi from inv_cat_ubi where cod_ubi = " + cod_ubi + " and cod_bod =" + cod_bod + ";"),
                            macc.strQuerySQLvariable("select nom_art from inv_cat_art where cod_art = " + cod_art + ";"),
                            String.valueOf(det_can_con),
                            uni_med_con,
                            String.valueOf(det_cos),
                            macc.strQuerySQLvariable("select cod_ref from inv_cat_art where cod_art = " + cod_art + ";"),
                            macc.strQuerySQLvariable("select nom_uni from gen_cat_uni_med where cod_uni =" + uni_med_con + ";")
                    ));
                }
              
                cod_art = "0";
                cod_bod = "0";
                cod_ubi = "0";
                det_can = 0.0;
                det_cos = 0.0;
                uni_med_con = "0";
                nombreunidad = "";
                unimedori = "0";
                det_lot = "";
                chkfecven = "false";
                fec_ven = "";
                mfechaven = null;
                cattransaccionesdetalle = null;
            }
        } catch (Exception e) {
            System.out.println("Error en Agregar Detalle ManInventarioProductos." + e.getMessage());
        }
    }

    public void eliminardetalle() {
        if ("".equals(cod_det)) {
            addMessage("Eliminar Detalles", "Debe Seleccionar un detalle para remover.", 3);
        } else {
            for (int i = 0; i < detalles.size(); i++) {
                if (cod_det.equals(detalles.get(i).getCod_det())) {
                    detalles.remove(i);
                }
            }
            cod_art = "0";
            cod_bod = "0";
            cod_ubi = "0";
            det_can = 0.0;
            det_cos = 0.0;
            uni_med_con = "0";
            nombreunidad = "";
            unimedori = "0";
            det_lot = "";
            cattransaccionesdetalle = null;
        }
    }

    //************************ Operaciones Transaccionales *********************
    public boolean validardatos() {
        boolean mvalidar = true;
        if ("0".equals(cod_suc) || "".equals(cod_suc)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar una Sucursal", 3);
        }
        if ("0".equals(doc_int)) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un Tipo de Operación", 3);
        }
        if (detalles.isEmpty()) {
            mvalidar = false;
            addMessage("Validar Datos", "Debe Agregar al menos un Detalle", 3);
        }

        if (!"".equals(cod_tra)) {
            macc.Conectar();
            if ("1".equals(macc.strQuerySQLvariable("select flg_anu from inv_tbl_transacciones where cod_tra = " + cod_tra + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Este Documento ya ha sido Anulado", 3);
            }

            if ("2".equals(macc.strQuerySQLvariable("select flg_anu from inv_tbl_transacciones where cod_tra = " + cod_tra + ";"))) {
                mvalidar = false;
                addMessage("Validar Datos", "Este Documento es Reversi�n de Anulaci�n y no puede modificarse", 3);
            }
            macc.Desconectar();
        }
        return mvalidar;
    }

    public void guardar() {
        if (validardatos()) {
            if (guardarmovimiento()) {
                addMessage("Guardar Movimiento de Inventario", "Información almacenada con Éxito.", 1);
                nuevoentradasalida();
            }

        }
    }

    public boolean guardarmovimiento() {
        String mQuery = "";
        String mValores = "", mhistoria = "", embalaje;
        ResultSet resVariable;
        try {
            if ("".equals(cod_tra)) {
                mQuery = "select ifnull(max(cod_tra),0)+1 as codigo from inv_tbl_tra;";
                cod_tra = macc.strQuerySQLvariable(mQuery);

                doc_int = macc.strQuerySQLvariable("select ifnull(max(doc_int),0) + 1 as cod from inv_tbl_tra "
                        + "where cod_tip_doc_int=" + cod_tip_doc_int + " and cod_suc=" + cod_suc + " and year(fec_tra) = year(str_to_date('" + fec_tra + "','%d/%m/%Y'));");

                mQuery = "insert into inv_tbl_tra (cod_tra,cod_suc,fec_tra,flg_ing_sal,cod_tip_doc_int,cod_tip_doc_ext,doc_ext,"
                        + "cod_cli_pro,det_obs,cod_usu,flg_anu,cod_res,doc_int,cod_are,obs_anu,usu_edi,fec_edi) "
                        + "values (" + cod_tra + "," + cod_suc + ",str_to_date('" + fec_tra + "','%d/%m/%Y'),'" + flg_ing_sal
                        + "'," + cod_tip_doc_int + "," + cod_tip_doc_ext + ",'" + doc_ext + "'," + cod_cli_pro + ",'" + det_obs + "',"
                        + cod_usu + ",0," + cod_res + "," + doc_int + "," + cod_are + ",'" + obs_anu + "'," + cod_usu + ",now());";
                macc.dmlSQLvariable(mQuery);
            } else {
                historico.clear();
                String flganterior = macc.strQuerySQLvariable("select flg_ing_sal from inv_tbl_tra where cod_tra=" + cod_tra + ";");
                String codsuc = macc.strQuerySQLvariable("select cod_suc from inv_tbl_tra where cod_tra=" + cod_tra + ";");

                mQuery = "update inv_tbl_tra set "
                        + "cod_suc=" + cod_suc + ", "
                        + "cod_tip_doc_ext=" + cod_tip_doc_ext + ", "
                        + "doc_ext='" + doc_ext + "',"
                        + "cod_cli_pro=" + cod_cli_pro + ","
                        + "det_obs='" + det_obs + "', "
                        + "cod_usu=" + cod_usu + ", "
                        + "cod_res=" + cod_res + ", "
                        + "cod_are=" + cod_are + ", "
                        + "obs_anu='" + obs_anu + "', "
                        + "usu_edi=" + cod_usu + ", "
                        + "fec_edi=now() "
                        + "where cod_tra=" + cod_tra + ";";
                macc.dmlSQLvariable(mQuery);

                //************************** Reorganizar Existencias antes de borrar *********************************************************
                mQuery = "select cod_tra, cod_det, cod_bod, cod_ubi, cod_art, det_lot, date_format(fec_ven,'%d/%m/%Y'), det_can,'','','',det_can_con, uni_med_con,det_cos,'','' "
                        + "FROM inv_tbl_tra_det where cod_tra = " + cod_tra + ";";
                macc.Conectar();
                resVariable = macc.querySQLvariable(mQuery);
                while (resVariable.next()) {
                    historico.add(new Cat_transaccionesDetalle(
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
                            resVariable.getString(16)
                    ));
                }
                resVariable.close();
                macc.Desconectar();

                String modcan;
                if (!historico.isEmpty()) {
                    for (int i = 0; i < historico.size(); i++) {
                        if ("true".equals(flganterior)) {
                            modcan = " + " + historico.get(i).getDet_can();
                        } else {
                            modcan = " - " + historico.get(i).getDet_can();
                        }

                        mQuery = "update inv_tbl_lot_ven SET "
                                + "exi_act = (exi_act " + modcan + ") "
                                + "WHERE "
                                + "cod_suc = " + codsuc + " "
                                + "AND cod_bod = " + historico.get(i).getCod_bod() + " "
                                + "AND cod_art = " + historico.get(i).getCod_art() + " "
                                + "AND num_lot = '" + historico.get(i).getDet_lot() + "' ";
                        macc.dmlSQLvariable(mQuery);

                    }
                }
                //************************* Fin Reorganizar Histórico ************************************************************************

                mQuery = "delete from inv_tbl_tra_det where cod_tra=" + cod_tra + ";";
                macc.dmlSQLvariable(mQuery);
                mQuery = "delete from inv_tra_his where cod_mov =" + cod_tra + " and flg_ing_sal='" + flg_ing_sal + "';";
                macc.dmlSQLvariable(mQuery);
            }

            int mCorrela = 1, ord_dia = 0;
            int id_tra = 0;
            Double cos_uni = 0.0, cos_pro = 0.0, exi_ant = 0.0, exi_act = 0.0, exi_act_lot;
            for (int i = 0; i < detalles.size(); i++) {

                mValores = mValores + ",(" + cod_tra + "," + mCorrela + "," + detalles.get(i).getCod_bod() + "," + detalles.get(i).getCod_ubi() + ","
                        + detalles.get(i).getCod_art() + ",'" + detalles.get(i).getDet_lot() + "',str_to_date('" + detalles.get(i).getFec_ven() + "','%d/%m/%Y'),"
                        + detalles.get(i).getDet_can() + "," + detalles.get(i).getDet_cos() + "," + detalles.get(i).getDet_can_con() + "," + detalles.get(i).getUni_med_con() + ")";

                //*************** Tratamiento Histórico de movimientos  *********************************************************************** 
                mQuery = "select ifnull(max(cod_tra),0)+1 as codigo from inv_tra_his;";
                id_tra = Integer.valueOf(macc.strQuerySQLvariable(mQuery));

                mQuery = "select ifnull(max(ord_dia),0)+1 as codigo from inv_tra_his "
                        + "where fec_tra = str_to_date('" + fec_tra + "','%d/%m/%Y') "
                        + "AND cod_suc = " + cod_suc + " "
                        + "AND cod_bod = " + detalles.get(i).getCod_bod() + " "
                        + "AND cod_art =" + detalles.get(i).getCod_art() + ";";
                ord_dia = Integer.valueOf(macc.strQuerySQLvariable(mQuery));

                cos_uni = Double.valueOf(detalles.get(i).getDet_cos()) / Double.valueOf(detalles.get(i).getDet_can());

                //Costos promedios 
                if (macc.doubleQuerySQLvariable("select count(cod_art) as conta "
                        + "from inv_tra_his "
                        + "where "
                        + "cod_art=" + detalles.get(i).getCod_art() + " "
                        + "and cod_suc = " + cod_suc + " "
                        + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y');") < 1) {
                    cos_pro = cos_uni;
                    exi_ant = 0.0;
                    exi_act_lot = 0.0;

                } else {
                    //************* Costo Promedio y Existencia anteriores  **************
                    if ("false".equals(flg_ing_sal)) {
                        cos_pro = macc.doubleQuerySQLvariable("select (ifnull((exi_act_bod * cos_pro),0) + "
                                + (Double.valueOf(detalles.get(i).getDet_can()) * cos_uni) + ")"
                                + "/(IFNULL(exi_act_bod,0)+" + detalles.get(i).getDet_can() + ") as Cpromedio "
                                + "from inv_tra_his "
                                + "where "
                                + "cod_art= " + detalles.get(i).getCod_art() + " "
                                + "and cod_suc = " + cod_suc + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                    } else {
                        cos_pro = macc.doubleQuerySQLvariable("select ifnull(cos_pro,0) as cospro "
                                + "from inv_tra_his "
                                + "where "
                                + "cod_art=" + detalles.get(i).getCod_art() + " "
                                + "and cod_suc = " + cod_suc + " "
                                + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "order by fec_tra desc,"
                                + "ord_dia desc "
                                + "limit 1;");

                    }
                    //***********  Existencia Anterior **************************************************
                    exi_ant = macc.doubleQuerySQLvariable("select ifnull(exi_act_bod,0) as exisant "
                            + "from inv_tra_his "
                            + "where "
                            + "cod_art=" + detalles.get(i).getCod_art() + " "
                            + "and cod_suc = " + cod_suc + " "
                            + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                            + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                    //********** Existencia Anterior Lote **********************************************
                    exi_act_lot = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                            + "from inv_tra_his "
                            + "where "
                            + "cod_art=" + detalles.get(i).getCod_art() + " "
                            + "and cod_suc = " + cod_suc + " "
                            + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                            + "and det_lot='" + detalles.get(i).getDet_lot().trim() + "' "
                            + "and  fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                            + "order by fec_tra desc,"
                            + "ord_dia desc "
                            + "limit 1;");

                }

                //******************* Tratamiento de Existencias **************************************
                if ("false".equals(flg_ing_sal)) {
                    exi_act = exi_ant + Double.valueOf(detalles.get(i).getDet_can());
                    exi_act_lot = exi_act_lot + Double.valueOf(detalles.get(i).getDet_can());
                } else {
                    exi_act = exi_ant - Double.valueOf(detalles.get(i).getDet_can());
                    exi_act_lot = exi_act_lot - Double.valueOf(detalles.get(i).getDet_can());
                    //*********** Valida que las Existencias no sean menor que Cero ******************
                    if (exi_act < 0.0) {
                        exi_act = 0.0;
                    }
                    if (exi_act_lot < 0.0) {
                        exi_act_lot = 0.0;
                    }
                }

                mhistoria = "(" + id_tra + ",'" + flg_ing_sal + "',str_to_date('" + fec_tra + "','%d/%m/%Y'),"
                        + ord_dia + "," + cod_tra + "," + mCorrela
                        + "," + cod_suc + "," + detalles.get(i).getCod_bod() + ","
                        + detalles.get(i).getCod_ubi() + "," + detalles.get(i).getCod_art() + ","
                        + detalles.get(i).getDet_can() + "," + exi_ant + "," + exi_act + "," + cos_uni + ","
                        + cos_pro + ",'" + detalles.get(i).getDet_lot().trim() + "'," + exi_act_lot + ")";
                mQuery = "insert into inv_tra_his (cod_tra,flg_ing_sal,fec_tra,ord_dia,cod_mov,det_mov,cod_suc,cod_bod,"
                        + "cod_ubi,cod_art,det_can,exi_ant_bod,exi_act_bod,cos_uni,cos_pro,det_lot,exi_act_lot) VALUES "
                        + mhistoria + ";";
                macc.dmlSQLvariable(mQuery);

                //***********************************************************************************************************
                //***************************** MODIFICACIÓN DE REGISTROS POSTERIORES ***************************************
                //***********************************************************************************************************
                Double contasiguientes;

                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacci�n 
                contasiguientes = macc.doubleQuerySQLvariable("select count(cod_tra) "
                        + "from inv_tra_his where "
                        + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_suc = " + cod_suc + " "
                        + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                        + "and cod_art = " + detalles.get(i).getCod_art() + " "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tra_his where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_suc = " + cod_suc + " "
                                + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        macc.Conectar();
                        resVariable = macc.querySQLvariable("(select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tra_his "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_suc = " + cod_suc + " "
                                + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                + "and cod_art=" + detalles.get(i).getCod_art() + " "
                                + "order by fec_tra asc,"
                                + "ord_dia asc) "
                                + "UNION ALL "
                                + "(select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tra_his "
                                + "where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_suc = " + cod_suc + " "
                                + "and cod_bod=" + detalles.get(i).getCod_bod() + " "
                                + "and cod_art=" + detalles.get(i).getCod_art() + " "
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
                        macc.Desconectar();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexi) {
                            if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                                exi_ant = exi_act;
                                cos_pro = ((cos_pro * exi_act + (seriehistorica1.getDet_can()) * seriehistorica1.getCos_uni()) / (exi_act + (seriehistorica1.getDet_can())));
                                exi_act = exi_act + (seriehistorica1.getDet_can());
                            } else {
                                exi_ant = exi_act;
                                exi_act = exi_act - (seriehistorica1.getDet_can());
                                if (exi_act < 0.0) {
                                    exi_act = 0.0;
                                }
                            }
                            mQuery = "update inv_tra_his set "
                                    + "cos_pro = " + cos_pro + ","
                                    + "exi_ant_bod = " + exi_ant + ","
                                    + "exi_act_bod = " + exi_act + " "
                                    + "where "
                                    + "cod_tra = " + seriehistorica1.getCod_his()
                                    + ";";
                            macc.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en Actualización datos posteriores por Bodega. " + e.getMessage() + " Query: " + (mQuery.replace("\\", "\\\\")).replace("'", "\\'"));
                    }
                }

                //***********************************************************************************************************
                //*************************** MODIFICACIÓN DE REGISTROS POSTERIORES POR LOTE ********************************
                //***********************************************************************************************************
                // Verifica si hay registros posteriores y si los hay actualiza a partir de la fecha de Transacci�n 
                contasiguientes = macc.doubleQuerySQLvariable("select count(cod_tra) "
                        + "from inv_tra_his where "
                        + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                        + "and ord_dia > " + ord_dia + " "
                        + "and cod_suc = " + cod_suc + " "
                        + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                        + "and cod_art = " + detalles.get(i).getCod_art() + " "
                        + "and det_lot ='" + detalles.get(i).getDet_lot() + "' "
                        + ";");
                contasiguientes = contasiguientes
                        + macc.doubleQuerySQLvariable("select count(cod_tra) "
                                + "from inv_tra_his where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_suc = " + cod_suc + " "
                                + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and det_lot ='" + detalles.get(i).getDet_lot() + "' "
                                + ";");

                if (contasiguientes > 0) {
                    try {
                        hisexi.clear();
                        macc.Conectar();
                        resVariable = macc.querySQLvariable("(select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tra_his "
                                + "where "
                                + "fec_tra = STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and ord_dia > " + ord_dia + " "
                                + "and cod_suc = " + cod_suc + " "
                                + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and det_lot ='" + detalles.get(i).getDet_lot() + "' "
                                + "order by fec_tra asc,"
                                + "ord_dia asc) "
                                + "UNION ALL "
                                + "(select cod_tra, flg_ing_sal, det_can, cos_uni from inv_tra_his "
                                + "where "
                                + "fec_tra > STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                + "and cod_suc = " + cod_suc + " "
                                + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                                + "and cod_art = " + detalles.get(i).getCod_art() + " "
                                + "and det_lot ='" + detalles.get(i).getDet_lot() + "' "
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
                        macc.Desconectar();

                        for (ListaCortaTransaccionExistencias seriehistorica1 : hisexi) {
                            if ("false".equals(seriehistorica1.getFlg_ent_sal())) {
                                exi_act_lot = exi_act_lot + (seriehistorica1.getDet_can());

                            } else {
                                exi_act_lot = exi_act_lot - (seriehistorica1.getDet_can());
                                if (exi_act_lot < 0.0) {
                                    exi_act_lot = 0.0;
                                }
                            }
                            mQuery = "update inv_tra_his set "
                                    + "exi_act_lot = " + exi_act_lot + " "
                                    + "where "
                                    + "cod_tra = " + seriehistorica1.getCod_his()
                                    + ";";
                            macc.dmlSQLvariable(mQuery);
                        }

                    } catch (Exception e) {
                        System.out.println("Error en Actualización datos posteriores por Lote. " + e.getMessage() + " Query: " + (mQuery.replace("\\", "\\\\")).replace("'", "\\'"));
                    }
                }

                //******************************* Fin Tratamiento Histórico de movimientos *****************************
                embalaje = macc.strQuerySQLvariable("select cod_uni_med from inv_cat_art where cod_art =" + detalles.get(i).getCod_art());

                mQuery = "select count(cod_art) from inv_tbl_lot_ven "
                        + "WHERE "
                        + "cod_suc = " + cod_suc + " "
                        + "AND cod_bod = " + detalles.get(i).getCod_bod() + " "
                        + "AND cod_art = " + detalles.get(i).getCod_art() + " "
                        + "AND num_lot = '" + detalles.get(i).getDet_lot() + "' ";

                String contador = macc.strQuerySQLvariable(mQuery);
                if ("0".equals(contador)) {
                    if ("false".equals(flg_ing_sal)) {
                        mQuery = "select ifnull(max(cod_inv),0)+1 as codigo from inv_tbl_lot_ven;";
                        String cod_inv = macc.strQuerySQLvariable(mQuery);
                        mQuery = "insert into inv_tbl_lot_ven (cod_inv,cod_suc,cod_bod,cod_ubi,cod_art,cod_uni_med,num_lot,fec_ven,exi_act) "
                                + "values (" + cod_inv
                                + "," + cod_suc
                                + "," + detalles.get(i).getCod_bod()
                                + "," + detalles.get(i).getCod_ubi()
                                + "," + detalles.get(i).getCod_art()
                                + "," + embalaje
                                + ",'" + detalles.get(i).getDet_lot()
                                + "'," + "str_to_date('" + detalles.get(i).getFec_ven() + "','%d/%m/%Y')"
                                + "," + detalles.get(i).getDet_can() + ");";
                    }

                } else {
                    String msigno;
                    if ("true".equals(flg_ing_sal)) {
                        msigno = " - " + detalles.get(i).getDet_can();
                    } else {
                        msigno = " + " + detalles.get(i).getDet_can();
                    }
                    mQuery = "update inv_tbl_lot_ven set "
                            + "exi_act = (exi_act " + msigno + ") "
                            + " where "
                            + " cod_suc=" + cod_suc
                            + " and cod_bod=" + detalles.get(i).getCod_bod()
                            + " and cod_art = " + detalles.get(i).getCod_art()
                            + " and num_lot ='" + detalles.get(i).getDet_lot() + "' ";

                }
                macc.dmlSQLvariable(mQuery);

                mCorrela = mCorrela + 1;

            }
            mQuery = "insert into inv_tbl_tra_det (cod_tra,cod_det,cod_bod,cod_ubi,cod_art,det_lot,fec_ven,det_can,det_cos,det_can_con,uni_med_con) VALUES " + mValores.substring(1) + ";";
            macc.dmlSQLvariable(mQuery);

            return true;

        } catch (SQLException | NumberFormatException e) {
            addMessage("Guardar Movimiento de Inventario", "Error al momento de Guardar la Información." + e.getMessage(), 3);
            err.auditarError("Man_inventario", "guardarmovimiento", "Error en Guardar Movimiento de Inventario Productos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            return false;
        }

    }

    public void eliminar() {
        String mQuery = "";
        int conta = 0;
        ResultSet resVariable;
        boolean mValidar = true;

        if ("".equals(cod_tra)) {
            mValidar = false;
            addMessage("Anular de Inventario Productos", "Debe elegir un Registro.", 3);
        } else {
            try {
                Querys.clear();
                macc.Conectar();
                mQuery = "select cod_bod,cod_ubi,cod_art from inv_tbl_tra_det where cod_tra = " + cod_tra + ";";
                resVariable = macc.querySQLvariable(mQuery);
                while (resVariable.next()) {
                    Querys.add(new ListaCorta(
                            "",
                            "select cod_mov from inv_tra_his where cod_suc = " + cod_suc
                            + " and cod_bod=" + resVariable.getString(1)
                            + " and cod_ubi=" + resVariable.getString(2)
                            + " and cod_art = " + resVariable.getString(3)
                            + " order by fec_tra desc, ord_dia desc limit 1;"
                    ));
                }
                resVariable.close();
                if (!Querys.isEmpty()) {
                    for (int i = 0; i < Querys.size(); i++) {
                        if (!cod_tra.equals(macc.strQuerySQLvariable(Querys.get(i).getDescripcion()))) {
                            conta = conta + 1;
                        }
                    }
                }

                if (conta > 0) {
                    mValidar = false;
                    addMessage("Anular de Inventario Productos", "Existen Movimientos posteriores que imposibilitan Anular el actual.", 3);
                }

                macc.Desconectar();
            } catch (Exception e) {
                mValidar = false;
                err.auditarError("Man_inventario", "eliminar", "Error en Validar Anular Movimiento de Inventario. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }
        }

        if (mValidar) {
            try {

                String modcan;
                macc.Conectar();
                for (int i = 0; i < detalles.size(); i++) {

                    if ("true".equals(flg_ing_sal)) {
                        modcan = " + " + detalles.get(i).getDet_can();
                    } else {
                        modcan = " - " + detalles.get(i).getDet_can();
                    }

                    mQuery = "update inv_tbl_lot_ven SET "
                            + "exi_act = (exi_act " + modcan + ") "
                            + "WHERE "
                            + "cod_suc = " + cod_suc + " "
                            + "AND cod_bod = " + detalles.get(i).getCod_bod() + " "
                            + "AND cod_ubi = " + detalles.get(i).getCod_ubi() + " "
                            + "AND cod_art = " + detalles.get(i).getCod_art() + " "
                            + "AND num_lot = '" + detalles.get(i).getDet_lot() + "' "
                            + "AND fec_ven = str_to_date('" + detalles.get(i).getFec_ven() + "','%d/%m/%Y');";
                    macc.dmlSQLvariable(mQuery);
                }
                mQuery = "delete from inv_tra_his where cod_mov = " + cod_tra + "  and flg_ing_sal = '" + flg_ing_sal + "';";
                macc.dmlSQLvariable(mQuery);
                mQuery = "update inv_tbl_tra set flg_anu = 1 where cod_tra = " + cod_tra + ";";
                macc.dmlSQLvariable(mQuery);
                addMessage("Anular Movimiento de Inventario", "Información Anulada con éxito.", 1);
                macc.Desconectar();
                cattransacciones = null;
                encabezado.clear();
                RequestContext.getCurrentInstance().update("frmBuscarIng");
            } catch (Exception e) {
                addMessage("Anular de Inventario Productos", "Error al momento de Anular la información. " + e.getMessage(), 3);
                err.auditarError("Man_inventario", "eliminar", "Error en Anular Movimiento de Inventario. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }
        }
        nuevoentradasalida();
    }

    public void iniciaranular() {
        String mQuery = "";
        boolean mValidar = true;
        anu_cod_tip_doc_int = "0";

        if ("".equals(cod_tra)) {
            mValidar = false;
            addMessage("Anular de Inventario Productos", "Debe elegir un Registro.", 3);
        } else {

            mQuery = "select flg_anu from inv_tbl_tra where cod_tra = " + cod_tra + ";";

            if ("1".equals(macc.strQuerySQLvariable(mQuery))) {
                mValidar = false;
                addMessage("Anular de Inventario Productos", "Este Documento ya ha sido Anulado", 3);
            }

            if ("2".equals(macc.strQuerySQLvariable(mQuery))) {
                mValidar = false;
                addMessage("Anular de Inventario Productos", "Este Documento es Reversión de Anulación y no puede modificarse", 3);
            }
        }

        if (mValidar) {
            try {
                mQuery = "select  "
                        + "date_format(enc.fec_tra,'%d/%m/%Y') as fectra,"
                        + "enc.flg_ing_sal, "
                        + "enc.cod_tip_doc_int, "
                        + "enc.doc_ext, "
                        + "enc.cod_cli_pro, "
                        + "enc.det_obs, "
                        + "ifnull(enc.cod_res,''),"
                        + "ifnull(enc.cod_are,'') "
                        + "FROM inv_tbl_tra as enc "
                        + "where enc.cod_suc = " + cod_suc + " and enc.cod_tra = " + cod_tra + ";";
                macc.Conectar();
                ResultSet resVariable;
                resVariable = macc.querySQLvariable(mQuery);
                while (resVariable.next()) {
                    fec_tra = resVariable.getString(1);
                    flg_ing_sal = resVariable.getString(2);
                    cod_tip_doc_int = resVariable.getString(3);
                    doc_ext = resVariable.getString(4);
                    cod_cli_pro = resVariable.getString(5);
                    det_obs = resVariable.getString(6);
                    cod_res = resVariable.getString(7);
                    cod_are = resVariable.getString(8);

                }
                resVariable.close();
                macc.Desconectar();

                llenarDetalleMovimiento();
                if ("false".equals(flg_ing_sal)) {
                    flg_ing_sal = "true";
                } else {
                    flg_ing_sal = "false";
                }
                llenarDocumentosInt();
                RequestContext.getCurrentInstance().update("frmAnularInvArt");
                RequestContext.getCurrentInstance().execute("PF('wAnularInvArt').show()");
            } catch (Exception e) {
                addMessage("Anular de Inventario Productos", "Error al momento Inicializar la Anulación. " + e.getMessage(), 3);
                err.auditarError("Man_inventario", "iniciaranular", "Error al Iniciar Anulado de Inventario de Productos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
                nuevoentradasalida();
            }

        }

    }

    public void anular() {
        String mQuery;
        boolean mValidar = true;

        if ("".equals(obs_anu)) {
            mValidar = false;
            addMessage("Anular Movimiento de Inventario", "Debe ingresar un comentario sobre la anulaci�n del documento", 3);
        }

        if ("0".equals(anu_cod_tip_doc_int)) {
            mValidar = false;
            addMessage("Anular Movimiento de Inventario", "Debe seleccionar un tipo de documento de Reversión", 3);
        }

        if (mValidar) {
            String mCorMov = macc.strQuerySQLvariable("select ifnull(max(doc_int),0) + 1 as cod from inv_tbl_tra "
                    + "where cod_tip_doc_int=" + anu_cod_tip_doc_int + " and cod_suc =" + cod_suc + " "
                    + "and year(fec_tra) = year(str_to_date('" + fec_tra + "','%d/%m/%Y'));");

            String mDocu = macc.strQuerySQLvariable("select "
                    + "case ifnull(pre_cor,'') when '' then concat(right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2),'-') "
                    + "else concat(pre_cor,right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2),'-') end as abr "
                    + "from gen_cat_tip_doc_int where cod_tip_doc = " + anu_cod_tip_doc_int + ";")
                    + macc.strQuerySQLvariable("select LPAD('" + mCorMov + "', 6, '0');");

            obs_anu = obs_anu + ". Se generó automáticamente el Documento de Reversión: "
                    + mDocu;
            cod_tip_doc_int = anu_cod_tip_doc_int;
            cod_cli_pro = "0";
            cod_res = "0";
            cod_are = "0";
            String mdfechatra = fec_tra;
            String mflagingsal = flg_ing_sal;
            mQuery = "update inv_tbl_tra set flg_anu = 1, obs_anu='" + obs_anu + "' where cod_tra=" + cod_tra + ";";
            macc.dmlSQLvariable(mQuery);

            cod_tra = "";
            obs_anu = "";

            if (guardarmovimiento()) {
                mQuery = "select cod_tra from inv_tbl_tra "
                        + "where fec_tra = str_to_date('" + mdfechatra + "','%d/%m/%Y') "
                        + "and flg_ing_sal='" + mflagingsal + "' "
                        + "and cod_tip_doc_int = " + anu_cod_tip_doc_int + " "
                        + "and doc_int = " + mCorMov + ";";
                cod_tra = macc.strQuerySQLvariable(mQuery);
                mQuery = "update inv_tbl_tra set flg_anu = 2 where cod_tra=" + cod_tra + ";";
                cod_tra = "";
                macc.dmlSQLvariable(mQuery);

                RequestContext.getCurrentInstance().update("frmInventarioPro");
                RequestContext.getCurrentInstance().execute("PF('wAnularInvArt').hide()");
                addMessage("Anular Movimiento de Inventario", "Información Anulada con éxito", 1);
            } else {
                addMessage("Anular Movimiento de Inventario", "Error al momento de anular el Documento", 3);
            }
            cattransacciones = null;
            encabezado.clear();
            RequestContext.getCurrentInstance().update("frmBuscarIng");
            anu_cod_tip_doc_int = "0";
            nuevoentradasalida();
        }

    }

    public void cerraranular() {
        nuevoentradasalida();
    }

    public void iniciartraslado() {
        boolean mValidar = true;

        if ("false".equals(flg_ing_sal)) {
            mValidar = false;
            addMessage("Traslado Productos", "Debe seleccionar SALIDA como tipo de movimiento", 3);
        }
        if (!"".equals(cod_tra)) {
            mValidar = false;
            addMessage("Traslado Productos", "No se puede realizar traslados en una modificación. Debe generar un nuevo Documento", 3);
        }

        if (mValidar && validardatos()) {
            try {
                tra_cod_tip_doc_int = "0";
                tra_cod_bod = "0";
                flg_ing_sal = "false";
                llenarDocumentosInt();
                RequestContext.getCurrentInstance().update("frmTrasladoInvArt");
                RequestContext.getCurrentInstance().execute("PF('wTrasladoInvArt').show()");
            } catch (Exception e) {
                addMessage("Traslado Productos", "Error al momento Inicializar Traslado. " + e.getMessage(), 3);
                err.auditarError("Man_inventario", "iniciartraslado", "Error al iniciar Traslado Inventario Producto. " + e.getMessage(), "No existe Query en este método", cbean.getCod_usu());
                nuevoentradasalida();
            }

        }

    }

    public void trasladar() {
        boolean mValidar = true;

        if ("0".equals(tra_cod_tip_doc_int)) {
            mValidar = false;
            addMessage("Traslado Productos", "Debe Seleccionar un Documento de Ingreso para el Traslado", 3);
        }

        if ("0".equals(tra_cod_bod)) {
            mValidar = false;
            addMessage("Traslado Productos", "Debe seleccionar una Bodega Destino", 3);
        } else {
            for (int i = 0; i < detalles.size(); i++) {
                if (tra_cod_bod.equals(detalles.get(i).getCod_bod())) {
                    mValidar = false;
                }
            }
            if (!mValidar) {
                addMessage("Traslado Productos", "Existen productos relacionados con la Bodega destino en la Lista de Detalle", 3);
            }

            for (int i = 0; i < detalles.size(); i++) {
                //*************************** Verificaci�n de Puntos Máximos *************************************************
                Double mExi = 0.0;
                try {
                    mExi = macc.doubleQuerySQLvariable("select ifnull(exi_act_lot,0) as exisant "
                            + "from inv_tra_his "
                            + "where "
                            + "cod_art= " + detalles.get(i).getCod_art() + " "
                            + "and cod_suc = " + cod_suc + " "
                            + "and cod_bod = " + detalles.get(i).getCod_bod() + " "
                            + "and det_lot ='" + detalles.get(i).getDet_lot().trim() + "' "
                            + "and fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                            + "order by fec_tra desc, "
                            + "ord_dia desc "
                            + "limit 1;");
                } catch (Exception e) {
                    mExi = 0.0;
                }
                Double exigrid = 0.0;
                if (!detalles.isEmpty()) {
                    for (int j = 0; j < detalles.size(); j++) {
                        if (cod_bod.equals(detalles.get(j).getCod_bod())
                                && cod_art.equals(detalles.get(j).getCod_art())
                                && det_lot.equals(detalles.get(j).getDet_lot())) {
                            exigrid = exigrid + Double.valueOf(detalles.get(j).getDet_can());
                        }
                    }
                }

                Double mMax = 0.0;
                try {
                    mMax = Double.valueOf(macc.strQuerySQLvariable("select ifnull(pun_max,0.0) "
                            + "from inv_cat_pun_ale "
                            + "where cod_bod = " + tra_cod_bod + " "
                            + "and cod_art = " + detalles.get(i).getCod_art()
                            + ";"));
                } catch (Exception e) {
                    mMax = 0.0;
                }
                if (mMax > 0.0) {
                    if ((mExi + Double.valueOf(detalles.get(i).getDet_can()) + exigrid) > mMax) {
                        addMessage("Traslado Productos", "La Cantidad Ingresada de '" + detalles.get(i).getNomart() + "' Sobrepasa el Máximo permitido en Bodega Destino", 3);
                        mValidar = false;
                    }
                }
            }
        }

        if (mValidar) {
            flg_ing_sal = "true";
            if (guardarmovimiento()) {
                flg_ing_sal = "false";
                cod_tip_doc_int = tra_cod_tip_doc_int;
                cod_tra = "";
                macc.Conectar();
                for (int i = 0; i < detalles.size(); i++) {
                    detalles.set(i, new Cat_transaccionesDetalle(
                            detalles.get(i).getCod_tra(),
                            detalles.get(i).getCod_det(),
                            tra_cod_bod,
                            detalles.get(i).getCod_ubi(),
                            detalles.get(i).getCod_art(),
                            detalles.get(i).getDet_lot(),
                            detalles.get(i).getFec_ven(),
                            detalles.get(i).getDet_can(),
                            detalles.get(i).getNombod(),
                            detalles.get(i).getNomubi(),
                            detalles.get(i).getNomart(),
                            detalles.get(i).getDet_can_con(),
                            detalles.get(i).getUni_med_con(),
                            macc.strQuerySQLvariable("select (ifnull(cos_pro,0) * " + detalles.get(i).getDet_can() + ") as cospro "
                                    + "from inv_tra_his "
                                    + "where "
                                    + "cod_art = " + detalles.get(i).getCod_art() + " "
                                    + "and cod_suc = " + cod_suc + " "
                                    + "and fec_tra <= STR_TO_DATE('" + fec_tra + "','%d/%m/%Y') "
                                    + "order by fec_tra desc, "
                                    + "ord_dia desc "
                                    + "limit 1;"),
                            detalles.get(i).getCodrefart(),
                            detalles.get(i).getNomunimed()
                    ));
                }
                macc.Desconectar();

                if (guardarmovimiento()) {
                    addMessage("Traslado Productos", "Traslado realizado con éxito", 1);
                } else {
                    addMessage("Traslado Productos", "Error al momento de guardar Entrada Traslado", 3);
                }
                RequestContext.getCurrentInstance().update("frmInventarioPro");
                RequestContext.getCurrentInstance().execute("PF('wTrasladoInvArt').hide()");
                nuevoentradasalida();
            } else {
                addMessage("Traslado Productos", "Error al momento de guardar Salida Traslado", 3);
            }
        }
    }

    public void cerrartraslado() {
        nuevoentradasalida();
    }

    public void iniciarvencidos() {
        ven_cod_bod = "0";
    }

    public void llenarVencidos() {
        String mQuery = "";
        if ("0".equals(ven_cod_bod)) {
            addMessage("Cargar Vencidos", "Debe Seleccionar una Bodega Origen", 3);
        } else {
            try {
                flg_ing_sal = "true";
                inout();
                cattransaccionesdetalle = null;
                detalles.clear();
                mQuery = "select 0 as tra, 0 as dettra,"
                        + "t.cod_bod, t.cod_ubi, t.cod_art, t.detlot, date_format(t.fecven,'%d/%m/%Y'), "
                        + "t.detcan, t.nombod, t.nomubi, t.nomart,t.detcancon,t.cod_uni_med,t.costo,t.cod_ref, t.nomemb "
                        + "from ( "
                        + "select "
                        + "det.cod_bod, "
                        + "0 as cod_ubi, "
                        + "art.cod_art, "
                        + "ifnull(det.det_lot,'') as detlot, "
                        + "det.fec_ven as fecven, "
                        + "ifnull((select his.exi_act_lot from inv_tra_his as his where his.cod_suc = mae.cod_suc and his.cod_bod = det.cod_bod and his.cod_art = det.cod_art and his.det_lot = det.det_lot and his.fec_tra <= STR_TO_DATE('" + fec_tra + "', '%d/%m/%Y') order by his.fec_tra desc, his.ord_dia desc limit 1),0) as detcan, "
                        + "ifnull(bod.nom_bod,'') as nombod, "
                        + "ifnull(ubi.nom_ubi,'') as nomubi, "
                        + "concat(art.cod_ref,'--',ifnull(art.cod_alt,''),'--',art.nom_art) as nomart, "
                        + "ifnull((select his.exi_act_lot from inv_tra_his as his where his.cod_suc = mae.cod_suc and his.cod_bod = det.cod_bod and his.cod_art = det.cod_art and his.det_lot = det.det_lot and his.fec_tra <= STR_TO_DATE('" + fec_tra + "', '%d/%m/%Y') order by his.fec_tra desc, his.ord_dia desc limit 1),0) as detcancon, "
                        + "art.cod_uni_med, "
                        + "0.0 as costo, "
                        + "art.cod_ref, "
                        + "ifnull(emb.nom_uni,'') as nomemb "
                        + "from inv_cat_art as art "
                        + "left join gen_cat_uni_med as emb on art.cod_uni_med = emb.cod_uni "
                        + "left join inv_tbl_tra_det as det on art.cod_art = det.cod_art "
                        + "left join inv_tbl_tra as mae on det.cod_tra = mae.cod_tra "
                        + "left join inv_cat_bod as bod on det.cod_bod = bod.cod_bod "
                        + "left join inv_cat_ubi as ubi on bod.cod_bod = ubi.cod_bod and det.cod_ubi = ubi.cod_ubi "
                        + "where art.cod_emp = " + cod_emp + " "
                        + "and mae.flg_ing_sal = 'false' "
                        + "group by "
                        + "det.cod_bod, "
                        + "art.cod_art, "
                        + "art.cod_uni_med, "
                        + "det.det_lot, "
                        + "det.fec_ven, "
                        + "bod.nom_bod, "
                        + "ubi.nom_ubi, "
                        + "art.cod_ref, "
                        + "art.nom_art, "
                        + "emb.nom_uni ) as t "
                        + "where t.detcan > 0 "
                        + "and date_format(t.fecven,'%d/%m/%Y') <> '00/00/0000' "
                        + "and t.fecven < str_to_date('" + fec_tra + "','%d/%m/%Y') "
                        + "and t.cod_bod = " + ven_cod_bod + " "
                        + "order by t.cod_art;";

                ResultSet resVariable;
                macc.Conectar();
                resVariable = macc.querySQLvariable(mQuery);
                int mCorrela = 1;
                while (resVariable.next()) {
                    detalles.add(new Cat_transaccionesDetalle(
                            resVariable.getString(1),
                            String.valueOf(mCorrela),
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
                            resVariable.getString(16)
                    ));
                    mCorrela = mCorrela + 1;
                }
                resVariable.close();
                macc.Desconectar();

                RequestContext.getCurrentInstance().update("frmInventarioPro");
                RequestContext.getCurrentInstance().execute("PF('wCargarVencidos').hide()");
            } catch (Exception e) {
                err.auditarError("Man_inventario", "llenarVencidos", "Error en el llenado de Vencidos. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
            }
        }
    }

    public void cerrarvencidos() {
        ven_cod_bod = "0";
        nuevoentradasalida();
    }

    //********************* Llenar catálogos Generales *************************
    public void llenarMovimientosTotal() {
        String mQuery = "";
        try {

            movimientostotal.clear();

            mQuery = "select id_mov, nom_mov "
                    + "from inv_cat_mov "
                    + "where cod_pai =" + cod_pai + " "
                    + "order by id_mov;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                movimientostotal.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Movimientos Total en ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUnidadesMedida() {
        String mQuery = "";
        try {
            unidades.clear();

            mQuery = "select cod_uni, nom_uni "
                    + "from gen_cat_uni_med order by nom_uni;";

            ResultSet resVariable;
            macc.Conectar();

            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                unidades.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarUnidadesMedida", "Error en el llenado de Unidades de Medida. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    //************************ Funcionalidad de Controles **********************
    public void iniciarimagen() {
        String mQuery = "";
        macc.Conectar();
        try {
            Blob miBlob = null;
            if ("0".equals(cod_art) || "".equals(cod_art)) {
                mQuery = "select img_art from inv_cat_art where cod_art=0;";
                miBlob = macc.blobQuerySQLvariable(mQuery);
                byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                InputStream is = new ByteArrayInputStream(data);

                mimagen2 = new DefaultStreamedContent(is, "image/jpeg");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                mheight2 = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 270.0);
                img = null;
                data = null;
                nomprod = "NINGUNO";
                nombremarca = "SIN MARCA";
            } else {
                mQuery = "select img_art from inv_cat_art where cod_art=" + cod_art + ";";
                miBlob = macc.blobQuerySQLvariable(mQuery);
                if (miBlob != null) {
                    byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                    InputStream is = new ByteArrayInputStream(data);
                    mimagen2 = new DefaultStreamedContent(is, "image/jpeg");
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                    mheight2 = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 270.0);
                    img = null;
                    data = null;

                } else {
                    mQuery = "select img_art from inv_cat_art where cod_art=0;";
                    miBlob = macc.blobQuerySQLvariable(mQuery);
                    byte[] data = miBlob.getBytes(1, (int) miBlob.length());
                    InputStream is = new ByteArrayInputStream(data);
                    mimagen2 = new DefaultStreamedContent(is, "image/jpeg");
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                    mheight2 = String.valueOf(Double.valueOf(img.getHeight()) / Double.valueOf(img.getWidth()) * 270.0);
                    img = null;
                    data = null;

                }
                nomprod = macc.strQuerySQLvariable("Select concat(ifnull(cod_ref,''), '--', ifnull(cod_alt,''), '--', nom_art) as nomart from inv_cat_art where cod_art=" + cod_art + ";");
                nombremarca = macc.strQuerySQLvariable("select nom_mar from gen_cat_mar where cod_mar = (select cod_mar from inv_cat_art where cod_art =" + cod_art + ");");

            }

        } catch (Exception e) {
            mheight2 = "170";
            err.auditarError("Man_inventario", "iniciarimagen", "Error en recuperar imagen Artículo. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
        macc.Desconectar();
    }

    public void onCloseImg() {
        nomprod = "";
        mimagen = null;
        mheight = "260";

    }

    public void onSelectDocInt() {
        if (cod_tip_doc_int == null) {
            cod_tip_doc_int = "0";
        }
        if ("0".equals(cod_tip_doc_int)) {
            doc_int = "";
        } else {
            doc_int = macc.strQuerySQLvariable("select concat("
                    + "(select pre_cor from gen_cat_tip_doc_int where cod_tip_doc =" + cod_tip_doc_int + "),"
                    + "(select right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2)),"
                    + "'-',"
                    + "LPAD((select ifnull(max(doc_int),0) + 1 from inv_tbl_tra where cod_tip_doc_int =" + cod_tip_doc_int + " and cod_suc=" + cod_suc + "),6,'0')"
                    + ");");

        }
    }

    public void seleccionarproducto() {
        llenarBodegas();
        cod_bod = "0";
        unimedori = macc.strQuerySQLvariable("select cod_uni_med from inv_cat_art where cod_art =" + cod_art + ";");
        if (unimedori == null) {
            unimedori = "0";
        }
        uni_med_con = unimedori;
        nombreunidad = macc.strQuerySQLvariable("select nom_uni from gen_cat_uni_med where cod_uni =(select cod_uni_med from inv_cat_art where cod_art = " + cod_art + ") ;");
        if ("true".equals(cbean.getFlg_uso_lot_art())) {
            usolote = macc.strQuerySQLvariable("select flg_usa_lot from inv_cat_art where cod_art=" + cod_art + ";");
            if (!"true".equals(usolote) && !"false".equals(usolote)) {
                usolote = cbean.getFlg_uso_lot_art();
            }
        }
        usofechaven = "false";
        det_lot = "";
        fec_ven = "";
        mfechaven = null;
        existenciareal = "0";
        llenarLotes();
        //recuperarimagenEntSal();
    }

    public void seleccionarbodega() {
        llenarUbicaciones();
        llenarLotes();
        det_lot = "";
        mfechaven = null;
        chkfecven = "false";
        cod_ubi = "0";
        det_lot = "";

        existenciareal = macc.strQuerySQLvariable("select ifnull(exi_act_bod,0) "
                + "from inv_tra_his "
                + "where cod_bod =" + cod_bod + " "
                + "and cod_art =" + cod_art + " "
                + "and fec_tra <= str_to_date('" + fec_tra + "','%d/%m/%Y') "
                + "order by fec_tra desc, ord_dia desc limit 1;");
        if (existenciareal == null) {
            existenciareal = "0";
        }
        if (existenciareal.equals("")) {
            existenciareal = "0";
        }

    }

    public void seleccionarUbicaciones() {

    }

    public void inout() {
        cod_cli_pro = "0";
        cod_are = "0";
        cod_res = "0";
        cod_tip_doc_ext = "0";
        doc_ext = "";
        cod_tip_doc_int = "0";
        doc_int = "";
        det_obs = "";
        obs_anu = "";

        cod_art = "0";
        unimedori = "0";
        nombreunidad = "";

        cod_bod = "0";
        existenciareal = "0";
        cod_ubi = "0";
        det_lot = "";
        mfechaven = null;
        fec_ven = "";
        det_can = 0.0;
        uni_med_con = "0";
        det_cos = 0.0;

        cod_det = "";

        usofechaven = "false";

        detalles.clear();
        lotes.clear();

        llenarDocumentosInt();
        llenarDocumentosExt();
        llenarClientesProveedores();
        llenarAreas();
        llenarResponsables();

        if ("false".equals(flg_ing_sal)) {
            titulo = "Proveedor";
            usocosto = cbean.getFlg_cos();
        } else {
            titulo = "Cliente";
            usocosto = "true";
        }

    }

    public void onselectlote() {
        if (det_lot == null) {
            det_lot = "";
        }
        if ("SELECCIONE UNO".equals(det_lot.toUpperCase())) {
            det_lot = det_lot.toUpperCase().replace("SELECCIONE UNO", "");
        }
        usofechaven = "false";
        mfechaven = null;
        fec_ven = "";
        if (!"".equals(det_lot)) {
            if ("true".equals(macc.strQuerySQLvariable("select flg_per from inv_cat_art where cod_art=" + cod_art + ";"))) {
                if ("0".equals(macc.strQuerySQLvariable("select count(fec_ven) from inv_tbl_lot_ven where cod_art=" + cod_art + " and num_lot='" + det_lot + "';"))) {
                    if ("true".equals(cbean.getFlg_uso_lot_art())) {
                        usofechaven = "true";
                    }
                } else if ("true".equals(cbean.getFlg_uso_lot_art())) {
                    fec_ven = macc.strQuerySQLvariable("select date_format(fec_ven,'%d/%m/%Y') as fec from inv_tbl_lot_ven where cod_art=" + cod_art + " and num_lot='" + det_lot + "' limit 1;");
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        mfechaven = format.parse(fec_ven);
                    } catch (Exception e) {
                        fec_ven = "";
                        mfechaven = null;
                    }
                }
            }
        }
    }

    public void onRowSelectDetalleAlmacen(SelectEvent event) {
        cod_det = ((Cat_transaccionesDetalle) event.getObject()).getCod_det();
        /*
        cod_bod = ((CatTransaccionesDetalle) event.getObject()).getCod_bod();
        cod_ubi = ((CatTransaccionesDetalle) event.getObject()).getCod_ubi();
        cod_art = ((CatTransaccionesDetalle) event.getObject()).getCod_art();
        det_lot = ((CatTransaccionesDetalle) event.getObject()).getDet_lot();
        fec_ven = ((CatTransaccionesDetalle) event.getObject()).getFec_ven();
        det_can = Double.valueOf(((CatTransaccionesDetalle) event.getObject()).getDet_can());
        nombod = ((CatTransaccionesDetalle) event.getObject()).getNombod();
        nomubi = ((CatTransaccionesDetalle) event.getObject()).getNomubi();
        nomart = ((CatTransaccionesDetalle) event.getObject()).getNomart();
        if ("00/00/0000".equals(fec_ven)) {
            fec_ven = "";
        }
         */
    }

    public void dateSelectedFectra(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_tra = format.format(date);
    }

    public void dateSelectedAction(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_tra = format.format(date);
    }

    public void dateVenSelectedAction(SelectEvent f) {
        Date date = (Date) f.getObject();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        fec_ven = format.format(date);
    }

    // *************************** Consulta Existencias ****************************
    public void iniciarventanaexistencias(int tipoini) {
        if (tipoini == 1) {
            iniciarventana();
            tipoInicio = 1;
        }
        paibus = cod_suc;
        bodbus = "0";
        ubibus = "0";
        probus = "0";
        chklot = "false";
        lotbus = "";
        lotesvencimiento.clear();
        bodegasbus.clear();
        ubicacionesbus.clear();
        llenarPaisesbus();
        llenarBodegasbus();
        catlotesvencimiento = null;
        RequestContext.getCurrentInstance().execute("PF('wDTExistenciasLot').clearFilters()");
        RequestContext.getCurrentInstance().update("frmExistenciasLot");

    }

    public void cerrarventanaexistencias() {

        paibus = "0";
        bodbus = "0";
        ubibus = "0";
        probus = "0";
        chklot = "false";
        lotbus = "";

        if (tipoInicio == 1) {
            cerrarventana();
        }

        RequestContext.getCurrentInstance().execute("PF('wDTExistenciasLot').clearFilters()");
        RequestContext.getCurrentInstance().update("frmExistenciasLot");
    }

    public void llenarPaisesbus() {
        try {

            paisesbus.clear();

            String mQuery = "select cod_suc, nom_suc "
                    + "from gen_cat_suc where cod_emp = " + cod_emp + " order by nom_suc;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                paisesbus.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Paises Búsqueda en ManInventarioProductos. " + e.getMessage());
        }
    }

    public void llenarBodegasbus() {
        String mQuery = "";
        try {

            bodegasbus.clear();

            mQuery = "select cod_bod, nom_bod "
                    + "from inv_cat_bod "
                    + "where cod_suc=" + paibus + " "
                    + "order by nom_bod;";
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                bodegasbus.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();
            ubicacionesbus.clear();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Bodegas Búsqueda en ManTblPiezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void llenarUbicacionesbus() {
        String mQuery = "";
        try {

            ubicacionesbus.clear();

            mQuery = "select ubi.cod_ubi,ubi.nom_ubi "
                    + "from inv_cat_ubi as ubi "
                    + "left join inv_cat_bod as bod on bod.cod_bod = ubi.cod_bod "
                    + "where bod.cod_suc = " + paibus + " "
                    + "and ubi.cod_bod=" + bodbus + " "
                    + "order by ubi.cod_bod,ubi.nom_ubi;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                ubicacionesbus.add(new ListaCorta(
                        resVariable.getString(1),
                        resVariable.getString(2)
                ));
            }
            resVariable.close();
            macc.Desconectar();

        } catch (Exception e) {
            System.out.println("Error en el llenado de Ubicaciones Búsqueda en ManTblPiezas. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    public void onchklotchange() {
        lotbus = "";
    }

    public void consultarexistencias() {
        lotbus = lotbus.trim().replace("'", "");
        llenarExistenciasLotes();
    }

    public void llenarExistenciasLotes() {
        String mQuery = "", mWhere = "";
        try {

            catlotesvencimiento = null;
            lotesvencimiento.clear();

            if (!"0".equals(paibus)) {
                mWhere = mWhere + " and t.cod_suc = " + paibus + " ";
            }
            if (!"0".equals(bodbus)) {
                mWhere = mWhere + " and t.cod_bod = " + bodbus + " ";
            }
            if (!"0".equals(ubibus)) {
                mWhere = mWhere + " and t.cod_ubi = " + ubibus + " ";
            }
            if (!"0".equals(probus)) {
                mWhere = mWhere + " and t.cod_art = " + probus + " ";
            }

            if ("false".equals(chklot)) {
                mQuery = "select  t.cod_suc, t.cod_bod, t.cod_ubi, t.cod_art, t.cod_uni_med, t.detlot, t.fecven, t.existencia, t.nompai,t.nombod, t.nomubi, t.nomart, t.nomemb from ( "
                        + "select  "
                        + "mae.cod_suc, "
                        + "det.cod_bod, "
                        + "0 as cod_ubi, "
                        + "art.cod_art, "
                        + "art.cod_uni_med, "
                        + "'TODOS' as detlot, "
                        + "'' as fecven, "
                        + "ifnull((select his.exi_act_bod from inv_tra_his as his where his.cod_suc = mae.cod_suc and his.cod_bod = det.cod_bod and his.cod_art = det.cod_art and his.fec_tra <= STR_TO_DATE('" + fec_tra + "', '%d/%m/%Y') order by his.fec_tra desc, his.ord_dia desc limit 1),0) as existencia, "
                        + "ifnull(pai.nom_suc,'') as nompai, "
                        + "ifnull(bod.nom_bod,'') as nombod, "
                        + "ifnull(ubi.nom_ubi,'') as nomubi, "
                        + "concat(art.cod_ref,'--',ifnull(art.cod_alt,''),'--',art.nom_art) as nomart, "
                        + "ifnull(emb.nom_uni,'') as nomemb  "
                        + "from inv_cat_art as art "
                        + "left join gen_cat_uni_med as emb on art.cod_uni_med = emb.cod_uni "
                        + "left join inv_tbl_tra_det as det on art.cod_art = det.cod_art  "
                        + "left join inv_tbl_tra as mae on det.cod_tra = mae.cod_tra "
                        + "left join gen_cat_suc as pai on mae.cod_suc = pai.cod_suc "
                        + "left join inv_cat_bod as bod on mae.cod_suc = bod.cod_suc and det.cod_bod = bod.cod_bod "
                        + "left join inv_cat_ubi as ubi on bod.cod_bod = ubi.cod_bod and det.cod_ubi = ubi.cod_ubi "
                        + "where mae.cod_suc = " + paibus + " "
                        + "and mae.flg_ing_sal = 'false' "
                        + "group by mae.cod_suc, "
                        + "det.cod_bod, "
                        + "art.cod_art, "
                        + "art.cod_uni_med, "
                        + "pai.nom_suc, "
                        + "bod.nom_bod, "
                        + "ubi.nom_ubi, "
                        + "art.cod_ref, "
                        + "art.cod_alt, "
                        + "art.nom_art, "
                        + "emb.nom_uni ) as t "
                        + "where t.existencia > 0 "
                        + mWhere
                        + "order by t.cod_art; ";
            } else {
                if (!"".equals(lotbus)) {
                    mWhere = mWhere + " and t.detlot='" + lotbus + "' ";
                }
                mQuery = "select  t.cod_suc, t.cod_bod, t.cod_ubi, t.cod_art, t.cod_uni_med, t.detlot, t.fecven, t.existencia, t.nompai,t.nombod, t.nomubi, t.nomart, t.nomemb from ( "
                        + "select  "
                        + "mae.cod_suc, "
                        + "det.cod_bod, "
                        + "0 as cod_ubi, "
                        + "art.cod_art, "
                        + "art.cod_uni_med, "
                        + "ifnull(det.det_lot,'') as detlot, "
                        + "case ifnull(date_format(det.fec_ven,'%d/%m/%Y'),'00/00/0000') when '00/00/0000' then '' else date_format(det.fec_ven,'%d/%m/%Y') end as fecven, "
                        + "ifnull((select his.exi_act_lot from inv_tra_his as his where his.cod_suc = mae.cod_suc and his.cod_bod = det.cod_bod and his.cod_art = det.cod_art and his.det_lot = det.det_lot and his.fec_tra <= STR_TO_DATE('" + fec_tra + "', '%d/%m/%Y') order by his.fec_tra desc, his.ord_dia desc limit 1),0) as existencia, "
                        + "ifnull(pai.nom_suc,'') as nompai, "
                        + "ifnull(bod.nom_bod,'') as nombod, "
                        + "ifnull(ubi.nom_ubi,'') as nomubi, "
                        + "concat(art.cod_ref,'--',ifnull(art.cod_alt,''),'--',art.nom_art) as nomart, "
                        + "ifnull(emb.nom_uni,'') as nomemb  "
                        + "from inv_cat_art as art "
                        + "left join gen_cat_uni_med as emb on art.cod_uni_med = emb.cod_uni "
                        + "left join inv_tbl_tra_det as det on art.cod_art = det.cod_art  "
                        + "left join inv_tbl_tra as mae on det.cod_tra = mae.cod_tra "
                        + "left join gen_cat_suc as pai on mae.cod_suc = pai.cod_suc "
                        + "left join inv_cat_bod as bod on mae.cod_suc = bod.cod_suc and det.cod_bod = bod.cod_bod "
                        + "left join inv_cat_ubi as ubi on bod.cod_bod = ubi.cod_bod and det.cod_ubi = ubi.cod_ubi "
                        + "where mae.cod_suc = " + paibus + " "
                        + "and mae.flg_ing_sal = 'false' "
                        + "group by mae.cod_suc, "
                        + "det.cod_bod, "
                        + "art.cod_art, "
                        + "art.cod_uni_med, "
                        + "det.det_lot, "
                        + "det.fec_ven, "
                        + "pai.nom_suc, "
                        + "bod.nom_bod, "
                        + "ubi.nom_ubi, "
                        + "art.cod_ref, "
                        + "art.cod_alt, "
                        + "art.nom_art, "
                        + "emb.nom_uni ) as t "
                        + "where t.existencia > 0 "
                        + mWhere
                        + "order by t.cod_art;";
            }
            ResultSet resVariable;

            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                lotesvencimiento.add(new Cat_lotesVencimiento(
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
                        resVariable.getString(13)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en el llenado de Lotes y Vencimiento en ManInventarioProductos. " + e.getMessage() + " Query: " + mQuery);
        }
    }

    //************************************ Buscar Movimiento ********************************
    public void cerrarbuscar() {
        encabezado.clear();
    }

    public void llenarEncabezado() {
        String mQuery = "";
        try {
            cattransacciones = null;
            encabezado.clear();

            mQuery = "select  "
                    + "enc.cod_tra, "
                    + "enc.cod_suc, "
                    + "date_format(enc.fec_tra,'%d/%m/%Y') as fectra,"
                    + "enc.flg_ing_sal, "
                    + "enc.cod_tip_doc_int, "
                    + "enc.doc_ext, "
                    + "enc.cod_cli_pro, "
                    + "enc.det_obs, "
                    + "enc.cod_usu, "
                    + "enc.flg_anu,"
                    + "suc.nom_suc,"
                    + "mov.nom_tip_doc,"
                    + "case enc.flg_ing_sal "
                    + "when 'true' then ifnull((select concat(cli.nom_cli,' ',ifnull(cli.ape_cli,'')) as nomcli from fac_cat_cli as cli where cli.cod_cli = enc.cod_cli_pro),'') "
                    + "when 'false' then ifnull((select concat(pro.nom_pro,' ',ifnull(pro.ape_pro,'')) as nompro from inv_cat_pro as pro where pro.cod_pro = enc.cod_cli_pro),'') "
                    + "end as nomclipro, "
                    + "ifnull(enc.cod_res,''),"
                    + "ifnull(enc.doc_int,''),"
                    + "ifnull(enc.cod_are,''),"
                    + "enc.obs_anu, "
                    + "(concat((select case ifnull(pre_cor,'') when '' then right(year(enc.fec_tra),2) else concat(pre_cor,right(year(enc.fec_tra),2),'-') end as abr from gen_cat_tip_doc_int where cod_tip_doc=enc.cod_tip_doc_int),(select LPAD(enc.doc_int, 6, '0')) )) as coddoc, "
                    + "case enc.flg_ing_sal "
                    + "when 'true' then 'SALIDA' "
                    + "when 'false' then 'INGRESO' "
                    + "end as ingsal "
                    + "FROM inv_tbl_tra as enc "
                    + "left join gen_cat_suc as suc on enc.cod_suc = suc.cod_suc "
                    + "left join gen_cat_tip_doc_int as mov on enc.cod_tip_doc_int = mov.cod_tip_doc "
                    + "where enc.flg_anu=0 and enc.cod_suc = " + cod_suc + " "
                    + "order by enc.fec_tra desc, enc.flg_ing_sal, enc.cod_tip_doc_int;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                encabezado.add(new Cat_transacciones(
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
                        resVariable.getString(19)
                ));
            }
            resVariable.close();
            macc.Desconectar();
            RequestContext.getCurrentInstance().execute("PF('wDTIngBuscar').clearFilters()");
            RequestContext.getCurrentInstance().update("frmBuscarIng");
        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarEncabezado", "Error en el llenado de Encabezado para Modificar. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void llenarDetalleMovimiento() {
        String mQuery = "";
        try {
            cattransaccionesdetalle = null;
            detalles.clear();
            mQuery = "select "
                    + "det.cod_tra, "
                    + "det.cod_det, "
                    + "det.cod_bod, "
                    + "det.cod_ubi, "
                    + "det.cod_art, "
                    + "det.det_lot, "
                    + "if(date_format(det.fec_ven,'%d/%m/%Y')='00/00/0000','',date_format(det.fec_ven,'%d/%m/%Y')) as fecven,"
                    + "det.det_can,"
                    + "bod.nom_bod,"
                    + "ubi.nom_ubi,"
                    + "art.nom_art,"
                    + "det.det_can_con, "
                    + "det.uni_med_con, "
                    + "det.det_cos, "
                    + "art.cod_ref, "
                    + "emb.nom_uni "
                    + "FROM inv_tbl_tra_det as det "
                    + "left join inv_tbl_tra as enc on det.cod_tra = enc.cod_tra "
                    + "left join inv_cat_bod as bod on det.cod_bod = bod.cod_bod and enc.cod_suc = bod.cod_suc "
                    + "left join inv_cat_ubi as ubi on det.cod_bod = ubi.cod_bod and det.cod_ubi = ubi.cod_ubi "
                    + "left join inv_cat_art as art on det.cod_art = art.cod_art "
                    + "left join gen_cat_uni_med as emb on det.uni_med_con = emb.cod_uni "
                    + "where det.cod_tra = " + cod_tra + " "
                    + "order by det.cod_det;";
            ResultSet resVariable;
            macc.Conectar();
            resVariable = macc.querySQLvariable(mQuery);
            while (resVariable.next()) {
                detalles.add(new Cat_transaccionesDetalle(
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
                        resVariable.getString(16)
                ));
            }
            resVariable.close();
            macc.Desconectar();
        } catch (Exception e) {
            err.auditarError("Man_inventario", "llenarDetalleMovimiento", "Error en el llenado de Detalle para Modificar. " + e.getMessage(), (mQuery.replace("\\", "\\\\")).replace("'", "\\'"), cbean.getCod_usu());
        }
    }

    public void onRowSelectBuscar(SelectEvent event) {
        cod_tra = ((Cat_transacciones) event.getObject()).getCod_tra();
        fec_tra = ((Cat_transacciones) event.getObject()).getFec_tra();
        flg_ing_sal = ((Cat_transacciones) event.getObject()).getFlg_ing_sal();
        cod_tip_doc_int = ((Cat_transacciones) event.getObject()).getTip_mov();
        doc_ext = ((Cat_transacciones) event.getObject()).getDoc_tra();
        cod_cli_pro = ((Cat_transacciones) event.getObject()).getCod_cli_pro();
        det_obs = ((Cat_transacciones) event.getObject()).getDet_obs();
        cod_res = ((Cat_transacciones) event.getObject()).getCod_res();
        cod_are = ((Cat_transacciones) event.getObject()).getCod_are();
        obs_anu = ((Cat_transacciones) event.getObject()).getObs_anu();

        doc_int = macc.strQuerySQLvariable("select case ifnull(pre_cor,'') when '' then right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2) else concat(pre_cor,right(year(str_to_date('" + fec_tra + "','%d/%m/%Y')),2),'-') end as abr from gen_cat_tip_doc_int where cod_tip_doc=" + cod_tip_doc_int + ";")
                + macc.strQuerySQLvariable("select LPAD('" + ((Cat_transacciones) event.getObject()).getCor_mov() + "', 6, '0');");

        if ("true".equals(flg_ing_sal)) {
            titulo = "Cliente";
        } else {
            titulo = "Proveedor";
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            mfecha = format.parse(fec_tra);
        } catch (Exception ex) {

        }
        boolinout = flg_ing_sal;
        chkfecven = "false";
        cattransaccionesdetalle = null;
        detalles.clear();
        llenarDetalleMovimiento();
        llenarDocumentosInt();
        llenarClientesProveedores();

        RequestContext.getCurrentInstance().execute("PF('wDTIngBuscar').clearFilters()");
        RequestContext.getCurrentInstance().execute("PF('wBuscarIng').hide()");
        RequestContext.getCurrentInstance().update("frmBuscarIng");
    }

    //*************************** Cambiar Fecha Vencimiento a Lote  ********************************
    public void iniciareditfechaven() {
        Boolean mValidar = true;
        if (det_lot == null) {
            det_lot = "";
        }
        if ("".equals(cod_suc) || "0".equals(cod_suc)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Seleccionar una Sucursal", 3);
        }
        if ("".equals(cod_art) || "0".equals(cod_art)) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Seleccionar un Producto", 3);
        }
        if ("".equals(det_lot.trim())) {
            mValidar = false;
            addMessage("Validar Datos", "Debe Seleccionar o Ingresar un Número de Lote", 3);
        }
        if (mValidar) {
            editfecvennomart = macc.strQuerySQLvariable("select concat(cod_ref,' - ',nom_art) as art from inv_cat_art where cod_art=" + cod_art + ";");
            datefecvenedit = mfechaven;

            RequestContext.getCurrentInstance().update("frmEditFecVen");
            RequestContext.getCurrentInstance().execute("PF('wEditFecVen').show()");

        }
    }

    public void cerrareditfechaven() {
        editfecvennomart = "";
        datefecvenedit = null;
    }

    public void guardareditfechaven() {
        Boolean mValidar = true;

        if (datefecvenedit == null) {
            mValidar = false;
            addMessage("Modificar Fecha Vencimiento", "Debe Ingresar una Fecha de Vencimiento", 3);
        }

        if (mValidar) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                macc.Conectar();
                macc.dmlSQLvariable("update inv_tbl_tra_det as det "
                        + "left join inv_tbl_tra as mae on det.cod_tra = mae.cod_tra "
                        + "set fec_ven=str_to_date('" + format.format(datefecvenedit) + "','%d/%m/%Y') "
                        + "where mae.cod_suc=" + cod_suc + " "
                        + "and det.cod_art=" + cod_art + " "
                        + "and det.det_lot ='" + det_lot + "';"
                );
                macc.dmlSQLvariable("update inv_tbl_lot_ven "
                        + "set fec_ven=str_to_date('" + format.format(datefecvenedit) + "','%d/%m/%Y') "
                        + "where cod_suc=" + cod_suc + " "
                        + "and cod_art=" + cod_art + " "
                        + "and num_lot ='" + det_lot + "';"
                );
                macc.Desconectar();
                addMessage("Modificar Fecha Vencimiento", "Información almacenada con éxito", 1);
            } catch (Exception e) {
                addMessage("Modificar Fecha Vencimiento", "Error al guardar la información", 3);
                err.auditarError("Man_inventario", "guardareditfechaven", "Error en Guardar Modificación Fec Ven de Inventario Productos. " + e.getMessage(), "No Existe Query en este método", cbean.getCod_usu());
            }
        }

    }

    //****************************** IMPRIMIR ********************************************
    private String nombrereporte; //, nombreexportar;
    private Map<String, Object> parametros;

    public void imprimir() {
        try {
            parametros = new HashMap<>();
            parametros.put("cod_tra", cod_tra);
            parametros.put("cod_pai", cod_pai);
            nombrereporte = "/reportes/MovimientoAlmacen.jasper";
            verPDF();
            parametros = null;
        } catch (Exception e) {
            System.out.println("Error en imprimir InvAlmacenProductos." + e.getMessage());
        }

    }

    public void verPDF() {
        try {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(nombrereporte));
            byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), parametros, macc.Conectar());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();

            FacesContext.getCurrentInstance().responseComplete();
            macc.Desconectar();
        } catch (Exception e) {
            System.out.println("Error en verPDF en InvAlmacenProductos." + e.getMessage());
        }
    }

    //*********************************** Mensajer�a ************************************
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

    //****************** GETTER Y SETTERS **********************************************
    public List<ListaCorta> getCentroscosto() {
        return centroscosto;
    }

    public void setCentroscosto(List<ListaCorta> centroscosto) {
        this.centroscosto = centroscosto;
    }

    public String getCod_emp() {
        return cod_emp;
    }

    public void setCod_emp(String cod_emp) {
        this.cod_emp = cod_emp;
    }

    public String getUsogruposlineas() {
        return usogruposlineas;
    }

    public void setUsogruposlineas(String usogruposlineas) {
        this.usogruposlineas = usogruposlineas;
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

    public String getUsofechaven() {
        return usofechaven;
    }

    public void setUsofechaven(String usofechaven) {
        this.usofechaven = usofechaven;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

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

    public List<ListaCorta> getTemperaturas() {
        return temperaturas;
    }

    public void setTemperaturas(List<ListaCorta> temperaturas) {
        this.temperaturas = temperaturas;
    }

    public List<ListaCorta> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<ListaCorta> unidades) {
        this.unidades = unidades;
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

    public String getArt_cod_alt() {
        return art_cod_alt;
    }

    public void setArt_cod_alt(String art_cod_alt) {
        this.art_cod_alt = art_cod_alt;
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

    public String getArt_flg_pro_com() {
        return art_flg_pro_com;
    }

    public void setArt_flg_pro_com(String art_flg_pro_com) {
        this.art_flg_pro_com = art_flg_pro_com;
    }

    public Cat_lotesVencimiento getCatlotesvencimiento() {
        return catlotesvencimiento;
    }

    public void setCatlotesvencimiento(Cat_lotesVencimiento catlotesvencimiento) {
        this.catlotesvencimiento = catlotesvencimiento;
    }

    public List<ListaCorta> getBodegasbus() {
        return bodegasbus;
    }

    public void setBodegasbus(List<ListaCorta> bodegasbus) {
        this.bodegasbus = bodegasbus;
    }

    public String getBodbus() {
        return bodbus;
    }

    public void setBodbus(String bodbus) {
        this.bodbus = bodbus;
    }

    public List<ListaCorta> getClientesproveedores() {
        return clientesproveedores;
    }

    public void setClientesproveedores(List<ListaCorta> clientesproveedores) {
        this.clientesproveedores = clientesproveedores;
    }

    public List<ListaCorta> getAreas() {
        return areas;
    }

    public void setAreas(List<ListaCorta> areas) {
        this.areas = areas;
    }

    public List<ListaCorta> getEspecialistas() {
        return responsables;
    }

    public void setEspecialistas(List<ListaCorta> responsables) {
        this.responsables = responsables;
    }

    public List<ListaCorta> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<ListaCorta> bodegas) {
        this.bodegas = bodegas;
    }

    public Cat_transacciones getCattransacciones() {
        return cattransacciones;
    }

    public void setCattransacciones(Cat_transacciones cattransacciones) {
        this.cattransacciones = cattransacciones;
    }

    public List<Cat_transacciones> getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(List<Cat_transacciones> encabezado) {
        this.encabezado = encabezado;
    }

    public Cat_transaccionesDetalle getCattransaccionesdetalle() {
        return cattransaccionesdetalle;
    }

    public void setCattransaccionesdetalle(Cat_transaccionesDetalle cattransaccionesdetalle) {
        this.cattransaccionesdetalle = cattransaccionesdetalle;
    }

    public List<Cat_transaccionesDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Cat_transaccionesDetalle> detalles) {
        this.detalles = detalles;
    }

    public Cat_existencias getCatinventario() {
        return catinventario;
    }

    public void setCatinventario(Cat_existencias catinventario) {
        this.catinventario = catinventario;
    }

    public String getNomart() {
        return nomart;
    }

    public void setNomart(String nomart) {
        this.nomart = nomart;
    }

    public String getAnu_cod_tip_doc_int() {
        return anu_cod_tip_doc_int;
    }

    public void setAnu_cod_tip_doc_int(String anu_cod_tip_doc_int) {
        this.anu_cod_tip_doc_int = anu_cod_tip_doc_int;
    }

    public String getBooleditable() {
        return booleditable;
    }

    public void setBooleditable(String booleditable) {
        this.booleditable = booleditable;
    }

    public String getMheight2() {
        return mheight2;
    }

    public void setMheight2(String mheight2) {
        this.mheight2 = mheight2;
    }

    public String getMheight() {
        return mheight;
    }

    public void setMheight(String mheight) {
        this.mheight = mheight;
    }

    public String getUsocodigoauto() {
        return usocodigoauto;
    }

    public void setUsocodigoauto(String usocodigoauto) {
        this.usocodigoauto = usocodigoauto;
    }

    public DefaultStreamedContent getMimagen() {
        return mimagen;
    }

    public void setMimagen(DefaultStreamedContent mimagen) {
        Man_inventario.mimagen = mimagen;
    }

    public DefaultStreamedContent getMimagen2() {
        return mimagen2;
    }

    public void setMimagen2(DefaultStreamedContent mimagen2) {
        Man_inventario.mimagen2 = mimagen2;
    }

    public Date getMfechatra() {
        return mfechatra;
    }

    public void setMfechatra(Date mfechatra) {
        this.mfechatra = mfechatra;
    }

    public String getCod_bod() {
        return cod_bod;
    }

    public void setCod_bod(String cod_bod) {
        this.cod_bod = cod_bod;
    }

    public List<Cat_existencias> getInventario() {
        return inventario;
    }

    public void setInventario(List<Cat_existencias> inventario) {
        this.inventario = inventario;
    }

    public String getUbibus() {
        return ubibus;
    }

    public void setUbibus(String ubibus) {
        this.ubibus = ubibus;
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

    public Querys getMacc() {
        return macc;
    }

    public void setMacc(Querys macc) {
        this.macc = macc;
    }

    public List<ListaCorta> getQuerys() {
        return Querys;
    }

    public void setQuerys(List<ListaCorta> Querys) {
        this.Querys = Querys;
    }

    public String getCod_suc() {
        return cod_suc;
    }

    public void setCod_suc(String cod_suc) {
        this.cod_suc = cod_suc;
    }

    public String getIngresarnuevosaldo() {
        return ingresarnuevosaldo;
    }

    public void setIngresarnuevosaldo(String ingresarnuevosaldo) {
        this.ingresarnuevosaldo = ingresarnuevosaldo;
    }

    public List<Cat_lotesVencimiento> getLotesvencimiento() {
        return lotesvencimiento;
    }

    public void setLotesvencimiento(List<Cat_lotesVencimiento> lotesvencimiento) {
        this.lotesvencimiento = lotesvencimiento;
    }

    public List<ListaCorta> getPaisesbus() {
        return paisesbus;
    }

    public void setPaisesbus(List<ListaCorta> paisesbus) {
        this.paisesbus = paisesbus;
    }

    public List<ListaCorta> getUbicacionesbus() {
        return ubicacionesbus;
    }

    public void setUbicacionesbus(List<ListaCorta> ubicacionesbus) {
        this.ubicacionesbus = ubicacionesbus;
    }

    public List<ListaCorta> getMovimientostotal() {
        return movimientostotal;
    }

    public void setMovimientostotal(List<ListaCorta> movimientostotal) {
        this.movimientostotal = movimientostotal;
    }

    public String getFecbus() {
        return fecbus;
    }

    public void setFecbus(String fecbus) {
        this.fecbus = fecbus;
    }

    public String getIdbus() {
        return idbus;
    }

    public void setIdbus(String idbus) {
        this.idbus = idbus;
    }

    public String getMovbus() {
        return movbus;
    }

    public void setMovbus(String movbus) {
        this.movbus = movbus;
    }

    public String getPaibus() {
        return paibus;
    }

    public void setPaibus(String paibus) {
        this.paibus = paibus;
    }

    public String getProbus() {
        return probus;
    }

    public void setProbus(String probus) {
        this.probus = probus;
    }

    public String getLotbus() {
        return lotbus;
    }

    public void setLotbus(String lotbus) {
        this.lotbus = lotbus;
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

    public List<ListaCorta> getProductos() {
        return productos;
    }

    public void setProductos(List<ListaCorta> productos) {
        this.productos = productos;
    }

    public List<Cat_transaccionesDetalle> getHistorico() {
        return historico;
    }

    public void setHistorico(List<Cat_transaccionesDetalle> historico) {
        this.historico = historico;
    }

    public List<ListaCortaTransaccionExistencias> getHisexi() {
        return hisexi;
    }

    public void setHisexi(List<ListaCortaTransaccionExistencias> hisexi) {
        this.hisexi = hisexi;
    }

    public String getCod_tra() {
        return cod_tra;
    }

    public void setCod_tra(String cod_tra) {
        this.cod_tra = cod_tra;
    }

    public String getCod_pai() {
        return cod_pai;
    }

    public void setCod_pai(String cod_pai) {
        this.cod_pai = cod_pai;
    }

    public String getFec_tra() {
        return fec_tra;
    }

    public void setFec_tra(String fec_tra) {
        this.fec_tra = fec_tra;
    }

    public String getFlg_ing_sal() {
        return flg_ing_sal;
    }

    public void setFlg_ing_sal(String flg_ing_sal) {
        this.flg_ing_sal = flg_ing_sal;
    }

    public String getTip_mov() {
        return cod_tip_doc_int;
    }

    public void setTip_mov(String cod_tip_doc_int) {
        this.cod_tip_doc_int = cod_tip_doc_int;
    }

    public String getDoc_tra() {
        return doc_ext;
    }

    public void setDoc_tra(String doc_ext) {
        this.doc_ext = doc_ext;
    }

    public String getCod_cli_pro() {
        return cod_cli_pro;
    }

    public void setCod_cli_pro(String cod_cli_pro) {
        this.cod_cli_pro = cod_cli_pro;
    }

    public String getDet_obs() {
        return det_obs;
    }

    public void setDet_obs(String det_obs) {
        this.det_obs = det_obs;
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

    public List<ListaCorta> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<ListaCorta> responsables) {
        this.responsables = responsables;
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

    public String getCod_res() {
        return cod_res;
    }

    public void setCod_res(String cod_res) {
        this.cod_res = cod_res;
    }

    public String getCod_are() {
        return cod_are;
    }

    public void setCod_are(String cod_are) {
        this.cod_are = cod_are;
    }

    public String getCod_det() {
        return cod_det;
    }

    public void setCod_det(String cod_det) {
        this.cod_det = cod_det;
    }

    public String getCod_ubi() {
        return cod_ubi;
    }

    public void setCod_ubi(String cod_ubi) {
        this.cod_ubi = cod_ubi;
    }

    public String getCod_art() {
        return cod_art;
    }

    public void setCod_art(String cod_art) {
        this.cod_art = cod_art;
    }

    public String getDet_lot() {
        return det_lot;
    }

    public void setDet_lot(String det_lot) {
        this.det_lot = det_lot;
    }

    public String getUni_med_con() {
        return uni_med_con;
    }

    public void setUni_med_con(String uni_med_con) {
        this.uni_med_con = uni_med_con;
    }

    public Double getDet_can() {
        return det_can;
    }

    public void setDet_can(Double det_can) {
        this.det_can = det_can;
    }

    public Double getDet_cos() {
        return det_cos;
    }

    public void setDet_cos(Double det_cos) {
        this.det_cos = det_cos;
    }

    public Double getDet_can_con() {
        return det_can_con;
    }

    public void setDet_can_con(Double det_can_con) {
        this.det_can_con = det_can_con;
    }

    public String getUnimedori() {
        return unimedori;
    }

    public void setUnimedori(String unimedori) {
        this.unimedori = unimedori;
    }

    public String getTra_cod_tip_doc_int() {
        return tra_cod_tip_doc_int;
    }

    public void setTra_cod_tip_doc_int(String tra_cod_tip_doc_int) {
        this.tra_cod_tip_doc_int = tra_cod_tip_doc_int;
    }

    public String getTra_cod_bod() {
        return tra_cod_bod;
    }

    public void setTra_cod_bod(String tra_cod_bod) {
        this.tra_cod_bod = tra_cod_bod;
    }

    public String getVen_cod_bod() {
        return ven_cod_bod;
    }

    public void setVen_cod_bod(String ven_cod_bod) {
        this.ven_cod_bod = ven_cod_bod;
    }

    public Date getMfecha() {
        return mfecha;
    }

    public void setMfecha(Date mfecha) {
        this.mfecha = mfecha;
    }

    public Date getMfechaven() {
        return mfechaven;
    }

    public void setMfechaven(Date mfechaven) {
        this.mfechaven = mfechaven;
    }

    public Date getDatefecvenedit() {
        return datefecvenedit;
    }

    public void setDatefecvenedit(Date datefecvenedit) {
        this.datefecvenedit = datefecvenedit;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreunidad() {
        return nombreunidad;
    }

    public void setNombreunidad(String nombreunidad) {
        this.nombreunidad = nombreunidad;
    }

    public String getNomprod() {
        return nomprod;
    }

    public void setNomprod(String nomprod) {
        this.nomprod = nomprod;
    }

    public String getExistenciareal() {
        return existenciareal;
    }

    public void setExistenciareal(String existenciareal) {
        this.existenciareal = existenciareal;
    }

    public String getNombremarca() {
        return nombremarca;
    }

    public void setNombremarca(String nombremarca) {
        this.nombremarca = nombremarca;
    }

    public String getEditfecvennomart() {
        return editfecvennomart;
    }

    public void setEditfecvennomart(String editfecvennomart) {
        this.editfecvennomart = editfecvennomart;
    }

    public String getObs_anu() {
        return obs_anu;
    }

    public void setObs_anu(String obs_anu) {
        this.obs_anu = obs_anu;
    }

    public String getChklot() {
        return chklot;
    }

    public void setChklot(String chklot) {
        this.chklot = chklot;
    }

    public Double getArt_pre_sin_iva() {
        return art_pre_sin_iva;
    }

    public void setArt_pre_sin_iva(Double art_pre_sin_iva) {
        this.art_pre_sin_iva = art_pre_sin_iva;
    }

}
