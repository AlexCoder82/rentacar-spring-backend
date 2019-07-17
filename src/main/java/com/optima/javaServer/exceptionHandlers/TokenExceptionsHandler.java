/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.exceptionHandlers;

import com.optima.javaServer.model.ServerError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Alex
 */
/**
 *
 * Gestión de las excepciones lanzadas en la validaciñon de los tokens
 */
@RestControllerAdvice
public class TokenExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleExpiredJwtException(ExpiredJwtException e) {

        ServerError error = new ServerError(
                new Date(),
                401,
                "Expired Token",
                "La sesión de usuario ha expirado.");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(error);

    }

    @ExceptionHandler({
        MalformedJwtException.class,
        UnsupportedJwtException.class,
        SignatureException.class,
        IllegalArgumentException.class,
        StringIndexOutOfBoundsException.class
    })
    public ResponseEntity handleJwtFormatException(Exception e) {

        ServerError error = new ServerError(
                new Date(),
                403,
                "Forbidden",
                "Debe estar registrado "
                + "y autenticado para acceder a este recurso.");

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(error);

    }

}
