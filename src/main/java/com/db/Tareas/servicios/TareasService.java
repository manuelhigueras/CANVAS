/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db.Tareas.servicios;

import com.db.Tareas.excepciones.TareaException;
import com.db.PoolConexiones;
import com.db.Tareas.domain.Estados;
import com.db.Tareas.domain.Tarea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class TareasService implements TareaServiceInterface{

    /////////////////////////////////////////////////////////////////////////////////////////////////
    
    private static final String SELECT_ALL_TAREAS_STATUS = "SELECT * FROM TAREAS WHERE ESTADO = ?";
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
    private static final String INSERT_TAREA = "INSERT INTO Tareas"
            + "(ID_TAREA, DESCRIPCION, ESTADO) " + "VALUES (?,?,?)";
    
    private static final String MAX_ID_TAREA = "SELECT MAX(ID_TAREA) FROM TAREAS";
    
    //////////////////////////////////////////////////////////////////////////////////////////////////
    
    private static final String DELETE_TAREA = "DELETE FROM TAREAS WHERE DESCRIPCION = ?";
    
    //////////////////////////////////////////////////////////////////////////////////////////////////
    
    private static final String UPDATE_TAREA = "UPDATE TAREAS SET DESCRIPCION = ?"
            + "  WHERE DESCRIPCION LIKE ?";
    
    //////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static void main(String[] args) {
        try{
           TareasService gb = new TareasService();
//           List<Tarea> lista = gb.getListaTareasPorEstado("TO DO");
//           System.out.println(lista.toString());
           gb.altaNuevaTarea(new Tarea("PATATA", "TO DO",26));
       }
       catch(TareaException ex){
           System.out.println("ERROR EN: " + ex.getMessage());
       }
       catch(SQLException e){
           System.out.println("ERROR EN: " + e.getMessage());
       }
    }
    
    @Override
    public List<Tarea> getListaTareasPorEstado(String status) throws TareaException, SQLException{

            List<Tarea> tarea = new ArrayList<Tarea>();
            Connection con = PoolConexiones.getConexionLibre();
            try{
                PreparedStatement ps = con.prepareStatement(SELECT_ALL_TAREAS_STATUS);
                ps.setString(1, status);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Tarea t = new Tarea();
                    t.setIdTarea(rs.getInt("ID_TAREA"));
                    t.setDescripcion(rs.getString("DESCRIPCION"));
                    t.setEstado(rs.getString("ESTADO"));
                    tarea.add(t);
                }
            }
            finally{
                PoolConexiones.liberaConexion(con);
            }
            return tarea;
    }
    
    @Override
    public void altaNuevaTarea(Tarea task) throws TareaException, SQLException {
        Connection con = PoolConexiones.getConexionLibre();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(MAX_ID_TAREA);
        int nuevoId = 1;
        if(rs.next()){
            nuevoId  = rs.getInt(1) +1;
        }
        PreparedStatement ps = con.prepareStatement(INSERT_TAREA);
        ps.setInt(1, nuevoId);
        ps.setString(2, task.getDescripcion());
        ps.setString(3, task.getEstado());
        ps.executeUpdate();
        PoolConexiones.liberaConexion(con);
        System.out.println("SIN ERRORES WUAY");
    }

    @Override
    public void bajaTarea(String descripcion) throws TareaException, SQLException {
        Connection con = PoolConexiones.getConexionLibre();
        PreparedStatement ps = con.prepareStatement(DELETE_TAREA);
        ps.setString(2, descripcion);
        ps.executeUpdate();
        con.commit();
        con.setAutoCommit(true);
    }

    @Override
    public void modificaTarea(String descripcion) throws TareaException, SQLException {
        Connection con = PoolConexiones.getConexionLibre();
        try{
            //UPDATE DE CONSULTA
            con.setAutoCommit(false); //desactivo la autoconfirmacion
            PreparedStatement pst = con.prepareStatement(UPDATE_TAREA);
            pst.setString(2, descripcion);
            pst.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("... no se pudo hacer la correcion");
            try{
                con.rollback();
            }
            catch(SQLException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
        finally{
            PoolConexiones.liberaConexion(con);
        }
    }
    
    public Estados convertirStringToEstados(String estado) {
        if (estado == null) {
            return null;
        }
        Estados e = null;
        switch (estado) {
            case "TO DO":
                e = Estados.TODO;
                break;
            case "IN PROGRESS":
                e = Estados.INPROGESS;
                break;
            case "DONE":
                e = Estados.DONE;
                break;
        }
        return e;
    }


}
