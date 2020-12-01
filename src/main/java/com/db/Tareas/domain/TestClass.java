/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db.Tareas.domain;

/**
 *
 * @author Manuel
 */
public class TestClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tarea ejemplo = new Tarea(Estados.TODO,"a",1);
        System.out.println(ejemplo.getEstados().getStatus());
    }
    
}
