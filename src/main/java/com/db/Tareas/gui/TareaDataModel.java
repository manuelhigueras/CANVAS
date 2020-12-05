/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db.Tareas.gui;

import com.db.Tareas.domain.Tarea;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author user
 */
public class TareaDataModel extends AbstractTableModel{

    private List<Tarea> tarea;
    private String[] columnas = {"ID_TAREA", "DESCRIPCION", "ESTADO", "ARCHIVADO"};
    
    public TareaDataModel(List<Tarea> lista) {
        this.tarea = lista;
    }

    @Override
    public int getRowCount() {
        return tarea.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tarea t = tarea.get(rowIndex);
        Object dato = null;
        switch(columnIndex){
            case 0:
                dato = t.getIdTarea();
                break;
            case 1:
                dato = t.getDescripcion();
                break;
            case 2:
                dato = t.getEstado();
                break;
            case 3: 
                dato = t.getArchivado();
                break;
        }
        return dato;
    }
    
    @Override
    public String getColumnName(int column){
        return columnas[column];
    } 
    
}
