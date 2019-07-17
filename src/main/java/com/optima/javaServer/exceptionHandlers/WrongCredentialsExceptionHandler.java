/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.exceptionHandlers;

import com.optima.javaServer.exceptions.WrongCredentialsException;
import com.optima.javaServer.model.ServerError;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Alex
 */
/**
 *
 * Gestión de la excepcion lanzada cuando la base de datos no encuentra los
 * credenciales del usuario o del admin
 */
@RestControllerAdvice
public class WrongCredentialsExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ServerError> handleWrongCredentialsException(
            WrongCredentialsException ex) {

        ServerError error = new ServerError(
                new Date(),
                404,
                "Wrong Credentials",
                "Credenciales erróneas.");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

}
