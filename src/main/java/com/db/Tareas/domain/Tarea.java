/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db.Tareas.domain;

import java.util.Objects;

/**
 *
 * @author user
 */
public class Tarea {
//    private Estados status;
    private String descripcion, estado;
    private int idTarea, archivado;

    public Tarea(){
        
    }

//    public Tarea(String descripcion, String estado, int idTarea) {
//        this.descripcion = descripcion;
//        this.estado = estado;
//        this.idTarea = idTarea;
//    }

    public Tarea(String descripcion, String estado) {
        this.descripcion = descripcion;
        this.estado = estado;
    }
    
    public Tarea(String descripcion, String estado, int archivado) {
        this.descripcion = descripcion;
        this.estado = estado;
        this.archivado = archivado;
    }
    
//    public Tarea(Estados status, String descripcion, int idTarea) {
//        this.status = status;
//        this.descripcion = descripcion;
//        this.idTarea = idTarea;
//    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getArchivado() {
        return archivado;
    }

    public void setArchivado(int archivado) {
        this.archivado = archivado;
    }    

//    public Estados getEstados() {
//        return status;
//    }
//
//    public void setEstados(Estados status) {
//        this.status = status;
//    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.estado);
        hash = 61 * hash + Objects.hashCode(this.descripcion);
        hash = 61 * hash + this.idTarea;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tarea other = (Tarea) obj;
        if (this.idTarea != other.idTarea) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tarea{" + "Estado=" + estado + ", descripcion=" + descripcion + ", idTarea=" + idTarea + '}';
    }
      
}
