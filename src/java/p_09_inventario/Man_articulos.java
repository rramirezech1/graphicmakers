package p_09_inventario;

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

@Named
@ConversationScoped
public class Man_articulos implements Serializable {

    @Inject
    Login cbean;

    private Cat_articulos catart;
    private List<Cat_articulos> articulos;

    private String cadenabuscar;

    private String cod_art, cod_ref, nom_art, des_art;
    private Double cos_pro, can_exi, can_reo, ult_exi, pre_uni_sin_iva;

    private Querys mquerys;

    public Man_articulos() {
    }

}
