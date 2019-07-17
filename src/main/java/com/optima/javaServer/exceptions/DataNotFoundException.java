/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optima.javaServer.exceptions;



/**
 * 
 * @author Alex
 */


/**
 * 
 * Excepción lanzada cuando una peticion select a la base de datos
 * no devuelve ningún registro
 * 
 */
public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException() {
        super();
    }
    
    

    
}
