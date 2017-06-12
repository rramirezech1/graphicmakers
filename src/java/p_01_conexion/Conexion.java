package p_01_conexion;

import java.sql.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean
@RequestScoped
public class Conexion {

    protected Connection con;
    protected Statement stmt;
    private String serverName = "localhost";
    private String portNumber = "3306";
    public static String databaseName = "antsolution"; //"harolda1_distasoft001";
    private String url = "jdbc:mysql://localhost:3306/" + databaseName;
    private String userName = "root"; //"root"; "harolda1_root";
    private String password = ""; 
    private String errString = "";

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
 
    public Conexion() {
    }
    
    private String getConnectionUrl(){
        return url;
        
    }
    
    public Connection Conectar(){
        con = null;
        try{
            Class.forName("org.gjt.mm.mysql.Driver");
            con=DriverManager.getConnection(getConnectionUrl(), userName, password);
            stmt = con.createStatement();
           
        }catch (ClassNotFoundException | SQLException e){            
            errString = "Error mientras se conectaba a la BD. " + e.getMessage();
            System.out.println(errString);
            return null;
            
        }
        return con;
    }
    
    public void Desconectar(){
        try{
            stmt.close();
            con.close();
         
        }catch(Exception e){
            errString="Error mientras se cerraba la Conexión " + e.getMessage();
            System.out.println(errString);
        }
    }
    
    public Statement getStmt(){
        return this.stmt;
        
    }
    
    
}
