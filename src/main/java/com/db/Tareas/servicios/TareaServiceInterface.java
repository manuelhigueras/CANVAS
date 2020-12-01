/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db.Tareas.servicios;

import com.db.Tareas.domain.Estados;
import com.db.Tareas.domain.Tarea;
import java.util.List;

/**
 *
 * @author user
 */
public interface TareaServiceInterface {
    public List<Tarea> getListaTareasPorEstado(Estados status);
}
