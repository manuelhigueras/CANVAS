/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db.Tareas.servicios;

import com.db.Tareas.excepciones.TareaException;
import com.db.PoolConexiones;
import com.db.Tareas.domain.Estados;
import com.db.Tareas.domain.RegistroCsv;
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
    
    private static final String INSERT_TAREA = "INSERT INTO TAREAS"
            + "(DESCRIPCION, ESTADO) " + "VALUES (?,?,?)";
   
    //////////////////////////////////////////////////////////////////////////////////////////////////
    
    private static final String DELETE_TAREA = "DELETE FROM TAREAS WHERE DESCRIPCION = ?";
    
    //////////////////////////////////////////////////////////////////////////////////////////////////
    
    private static final String UPDATE_TAREA = "UPDATE TAREAS SET DESCRIPCION = ?, ESTADO = ? "
            + "WHERE (DESCRIPCION LIKE ?) AND (ESTADO LIKE ?)";
    
    //////////////////////////////////////////////////////////////////////////////////////////////////
    
    private static final String UPDATE_TAREA_ARCHIVADA = "UPDATE TAREAS SET ARCHIVADO = ? WHERE ESTADO LIKE ? AND ARCHIVADO = ?";
    //"UPDATE TAREAS SET ARCHIVADO = ? WHERE ID_TAREA = ?"
    private static final String SELECT_TAREA_ARCHIVADA = "SELECT * FROM TAREAS";
    private final String NAME = "Archivador";
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static void main(String[] args) {
        try{
           TareasService gb = new TareasService();
//           List<Tarea> lista = gb.getListaTareasPorEstado("TO DO");
//           System.out.println(lista.toString());
//           gb.altaNuevaTarea(new Tarea("PATATA", "TO DO"));
//            gb.bajaTarea("PATATA");
//            gb.modificaTarea("NUGE NUGET", "IN PROGRESS","APRENDIZ2", "DONE");
           gb.altaArchivadorDone();
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
                    t.setArchivado(rs.getInt("ARCHIVADO"));
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
        try{            
            PreparedStatement ps = con.prepareStatement(INSERT_TAREA);
            ps.setString(1, task.getDescripcion());
            ps.setString(2, task.getEstado());
            ps.setInt(3, 1);
            ps.executeUpdate();
        }
        finally{
            PoolConexiones.liberaConexion(con);
        }
        System.out.println("SIN ERRORES WUAY");
    }

    @Override
    public void bajaTarea(String descripcion) throws TareaException, SQLException {
        Connection con = PoolConexiones.getConexionLibre();
        PreparedStatement ps = con.prepareStatement(DELETE_TAREA);
        ps.setString(1, descripcion);
        ps.executeUpdate();
        con.commit();
        con.setAutoCommit(true);
    }

    @Override
    public void modificaTarea(String Nvdescripcion, String Nvest, String descripcionViejo, String estViejo) throws TareaException, SQLException {
        Connection con = PoolConexiones.getConexionLibre();
        try{
            //UPDATE DE CONSULTA
            //UPDATE TAREAS SET DESCRIPCION = 'Add script SQL de bd2', ESTADO = 'IN PROGRESS' 
            //WHERE (DESCRIPCION LIKE 'Add script SQL de bd') AND (ESTADO LIKE 'DONE');
            //con.setAutoCommit(false); //desactivo la autoconfirmacion
            PreparedStatement pst = con.prepareStatement(UPDATE_TAREA);
            pst.setString(1, Nvdescripcion);
            pst.setString(2, Nvest);
            pst.setString(3, descripcionViejo);
            pst.setString(4, estViejo);
            con.setAutoCommit(true);
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

    @Override
    public void altaArchivadorDone() throws TareaException, SQLException {
        Connection con = PoolConexiones.getConexionLibre();
        String descripcion, estado;
        int archivador, idTarea;
        try{
            PreparedStatement pst = con.prepareStatement(SELECT_TAREA_ARCHIVADA);
            PreparedStatement pst2 = con.prepareStatement(UPDATE_TAREA_ARCHIVADA);
            ResultSet rs = pst.executeQuery();
            RegistroCsv fichero = new RegistroCsv(NAME);
            fichero.existeFile();
            while(rs.next()){
                //"UPDATE TAREAS SET ARCHIVADO = ? WHERE ESTADO LIKE ? AND ARCHIVADO = ?"
                idTarea = rs.getInt("ID_TAREA");
                descripcion = rs.getString("DESCRIPCION");
                estado = rs.getString("ESTADO");
                archivador = rs.getInt("ARCHIVADO");
                if(archivador == 1){
                    fichero.writeFile(idTarea, descripcion, estado, archivador);
                    pst2.setInt(1, 0);
                    pst2.setString(2, "DONE");
                    pst2.setInt(3, 1);
                    con.setAutoCommit(true);
                    pst2.executeUpdate();
                    break;
                }
                //.setIdTarea(rs.getInt("ID_TAREA"));
                //t.setDescripcion(rs.getString("DESCRIPCION"));
                //t.setEstado(rs.getString("ESTADO"));
                //tarea.add(t);
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            PoolConexiones.liberaConexion(con);
        }
    }
    
    

}
