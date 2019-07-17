/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optima.javaServer.exceptions;

import org.springframework.stereotype.Component;



/**
 * 
 * @author Alex
 */
@Component
public class WrongCredentialsException extends RuntimeException{

    public WrongCredentialsException() {
        super();
    }
 
}
