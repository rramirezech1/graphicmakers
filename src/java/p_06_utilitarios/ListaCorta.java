package p_06_utilitarios;

import java.io.Serializable;

public class ListaCorta implements Serializable {

    private String codigo, descripcion;

    public ListaCorta() {
    }

    public ListaCorta(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
