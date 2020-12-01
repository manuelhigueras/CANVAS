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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class TareasService implements TareaServiceInterface{

    private static final String SELECT_ALL_TAREAS_STATUS = "SELECT * FROM TAREAS WHERE ESTADO = ?";
    
//    public static void main(String[] args) {
//        
//    }
    
    @Override
    public List<Tarea> getListaTareasPorEstado(Estados status) throws TareaException, SQLException {
        List<Tarea> tarea = new ArrayList<Tarea>();
        Connection con = PoolConexiones.getConexionLibre();
        try{
            PreparedStatement ps = con.prepareStatement(SELECT_ALL_TAREAS_STATUS);
            ps.setString(3, "ESTADO");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Tarea t = new Tarea();
                c.setIdCliente(rs.getInt("ID_CLIENTE"));
                c.setNombre(rs.getString("NOMBRE"));
                c.setApellido(rs.getString("APELLIDOS"));
                c.setFechaNacimiento(rs.getDate("FECHA_NACIMIENTO"));
                c.setIdBanco(rs.getInt("ID_BANCO"));
                clientes.add(c);
        }
        finally{
            PoolConexiones.liberaConexion(con);
        }
        return tarea;
    }
    
//    @Override
//    public List<Cliente> getClientesPorIdBanco(int idBanco) throws BankException, SQLException {
//        List<Cliente> clientes = new ArrayList<Cliente>();
//        Connection con = PoolConexiones.getConexionLibre();
//        try{
//            PreparedStatement ps = con.prepareStatement(SELECT_ALL_CLIENTES_BANCO);
//            ps.setInt(1, idBanco);
//            ResultSet rs = ps.executeQuery();
//            while(rs.next()){
//                Cliente c = new Cliente();
//                c.setIdCliente(rs.getInt("ID_CLIENTE"));
//                c.setNombre(rs.getString("NOMBRE"));
//                c.setApellido(rs.getString("APELLIDOS"));
//                c.setFechaNacimiento(rs.getDate("FECHA_NACIMIENTO"));
//                c.setIdBanco(rs.getInt("ID_BANCO"));
//                clientes.add(c);
//            }
//            System.out.println("GUARDADO");
//        }
//        finally{
//            PoolConexiones.liberaConexion(con);
//        }
//        return clientes;
//    }
    
}
