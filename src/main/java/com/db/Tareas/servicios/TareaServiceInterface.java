/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db.Tareas.servicios;

import com.db.Tareas.domain.Estados;
import com.db.Tareas.domain.Tarea;
import com.db.Tareas.excepciones.TareaException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author user
 */
public interface TareaServiceInterface {
    public List<Tarea> getListaTareasPorEstado(String status) throws  TareaException, SQLException;
    public void altaNuevaTarea(Tarea tarea) throws TareaException, SQLException; 
    public void bajaTarea(String descripcion) throws TareaException, SQLException;
    public void modificaTarea(String Nvdescripcion, String Nvest, String descripcionOrigen, String estOrigen) throws TareaException, SQLException;  
}
