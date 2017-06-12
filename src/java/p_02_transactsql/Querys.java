package p_02_transactsql;

import p_01_conexion.Conexion;

import java.sql.*;

public class Querys extends Conexion {

    private ResultSet resultado;

    public Querys() {
    }

    public String strQuerySQLvariable(String mQuery) {
        try {
            Conectar();
            getStmt();
            String miSTR = "";
            resultado = stmt.executeQuery(mQuery);
            while (resultado.next()) {
                miSTR = resultado.getString(1);
            }
            Desconectar();
            return miSTR;
        } catch (Exception e) {
            System.out.println("Sql Consulta String Variable Exception:" + e.getMessage() + " Query: " + mQuery);
            Desconectar();
            return null;
        }
    }

    public int intQuerySQLvariable(String mQuery) {
        try {
            Conectar();
            getStmt();
            int miSTR = 0;
            resultado = stmt.executeQuery(mQuery);
            while (resultado.next()) {
                miSTR = resultado.getInt(1);
            }
            Desconectar();
            return miSTR;
        } catch (Exception e) {
            System.out.println("Sql Consulta Integer Variable Exception:" + e.getMessage() + " Query: " + mQuery);
            Desconectar();
            return 0;
        }
    }

    public Double doubleQuerySQLvariable(String mQuery) {
        try {
            Conectar();
            getStmt();
            Double miDBL = 0.0;
            resultado = stmt.executeQuery(mQuery);
            while (resultado.next()) {
                miDBL = resultado.getDouble(1);
            }
            Desconectar();
            return miDBL;
        } catch (Exception e) {
            System.out.println("Sql Consulta Double Variable Exception:" + e.getMessage() + " Query: " + mQuery);
            Desconectar();
            return null;
        }
    }

    public Date dateQuerySQLvariable(String mQuery) {
        try {
            Conectar();
            getStmt();
            Date miDate = null;
            resultado = stmt.executeQuery(mQuery);
            while (resultado.next()) {
                miDate = resultado.getDate(1);
            }
            Desconectar();
            return miDate;
        } catch (Exception e) {
            System.out.println("Sql Consulta Date Variable Exception:" + e.getMessage() + " Query: " + mQuery);
            Desconectar();
            return null;
        }
    }
    
    public Blob blobQuerySQLvariable(String mQuery) {
        try {
            Conectar();
            getStmt();
            Blob miBlob = null;
            resultado = stmt.executeQuery(mQuery);
            while (resultado.next()) {
                miBlob = resultado.getBlob(1);
            }
            Desconectar();
            return miBlob;
        } catch (Exception e) {
            System.err.println("Sql Consulta Blob Variable Exception:" + e.getMessage());
            Desconectar();
            return null;
        }
    }

    public ResultSet querySQLvariable(String mQuery) {
        try {
            getStmt();
            resultado = stmt.executeQuery(mQuery);
            return resultado;
        } catch (Exception e) {
            System.out.println("Sql Consulta ResultSet Variable Exception:" + e.getMessage() + " Query: " + mQuery);
            return null;
        } finally {

        }
    }

    public void dmlSQLvariable(String mQuery) {
        Conectar();
        try {
            getStmt();
            stmt.executeUpdate(mQuery);
        } catch (Exception e) {
            System.out.println("Sql Operación dml Variable Exception:" + e.getMessage() + " Query: " + mQuery);
        }
        Desconectar();
    }

}
