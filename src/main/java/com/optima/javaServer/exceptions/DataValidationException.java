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
public class DataValidationException extends RuntimeException{

    public DataValidationException(String message) {
        super(message);
    }

}
