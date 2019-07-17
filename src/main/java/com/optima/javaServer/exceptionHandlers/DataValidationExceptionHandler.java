/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.exceptionHandlers;

import com.optima.javaServer.exceptions.DataValidationException;
import com.optima.javaServer.model.ServerError;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author Alex
 */
@RestControllerAdvice
public class DataValidationExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleDataValidationException(DataValidationException ex) {

        ServerError error = new ServerError(
                new Date(),
                409, 
                "Not Valid Data",
                ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);

    }
    
}
