/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optima.javaServer.exceptionHandlers;

import com.optima.javaServer.model.ServerError;
import java.io.IOException;
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
public class IOExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleIOException(IOException ex) {

        ServerError error = new ServerError(
                new Date(),
                500, 
                "Input Output Error",
                "Error del servidor ");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);

    }
}
