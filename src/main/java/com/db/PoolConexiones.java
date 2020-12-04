package com.db;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PoolConexiones {

    private static DriverManager dm;
    private static int numeroMaxConexiones;
    private static final int NUM_MIN_CONEXIONES = 3;
    private static String driverClass;
    private static String url;
    private static String usuario;
    private static String clave;
    
    private static LinkedList<Connection> conexionesLibres;
    private static LinkedList<Connection> conexionesOcupadas;

    static  {
        Properties prop  = null;
        try {
            //leer el fichero de propiedades
            prop = new Properties();
            prop.load(new FileReader("db/db.properties"));
            numeroMaxConexiones = Integer.parseInt(
                    prop.getProperty("numero_max_conexiones", ""+NUM_MIN_CONEXIONES)
                    );
            driverClass = prop.getProperty("driver_class");
            url  = prop.getProperty("url_conexion");
            usuario = prop.getProperty("usuario");
            clave = prop.getProperty("clave");
            
            //cargar driver
            Class.forName(driverClass);
            //iniciar pool
            inicializarPool();
            
        } catch(ClassNotFoundException ef){
            System.out.println("No pudo cargar el driver "+ ef.getMessage());
        }catch (Exception ex) {
            System.out.println("Error leer parametro crear Pool " + ex.getMessage() );
        }
    }//fin static

    private PoolConexiones() {
    } 
    
    public static int getNumeroMaxConexiones() {
        return numeroMaxConexiones;
    }

    private static Connection getConnection() throws SQLException{
        Connection con  = null;
        if(usuario !=null && usuario.length() > 0){
            con = DriverManager.getConnection(url,usuario,clave);
        }else{
           con = DriverManager.getConnection(url);
        }
        return con; 
    }
    
 
    private static void inicializarPool() throws SQLException{
         
        //crear  coleccion conexiones libres
        conexionesLibres = new LinkedList<Connection>();
        //añadir nuevas conexiones
        for(int i = 0 ; i < numeroMaxConexiones; i++){
            Connection con = getConnection();
            conexionesLibres.add(con);
        }
        
        //crear coleccion conexiones ocupadas
        conexionesOcupadas = new LinkedList<Connection>();
 
    }
    
    public static Connection getConexionLibre(){
        Connection con = null;
        if(conexionesLibres.isEmpty() ){
            System.out.println("... No hay conexiones disponibles ");
        }else{
            con = conexionesLibres.getLast();
            conexionesLibres.remove(con);
            conexionesOcupadas.addLast(con);
        }
        
        return con;
    }
    
    public static void liberaConexion(Connection con){
        conexionesOcupadas.remove(con);
        conexionesLibres.addFirst(con);
    }
    
    public static void cerrarPool(){
        
        if(conexionesOcupadas.size() >0 ){
            System.out.println("No se puede cerrar Pool . Hay conexiones ocupadas");
        }else{
            for(Connection c: conexionesLibres){
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PoolConexiones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }//fin cerrar
    
    public static int getTotalConexionesLibres(){
        return conexionesLibres.size();
    }
    
    public static int getTotalConexionesOcupadas(){
        return conexionesOcupadas.size();
    }

} //fin PoolConexiones


/*
     1. Leer de un fichero de propiedades   .properties  los
        datos para crear el pool:
           . driver_class
           . numero_max_conexiones
           . url_conexion
           . usuario
           . clave

*/