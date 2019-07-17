/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.exceptionHandlers;

import com.optima.javaServer.model.ServerError;
import java.util.Date;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author Alex
 */

/**
 * Trata las excepciones relacionadas con la base de datos
 */
@RestControllerAdvice
public class DataAccessExceptionHandler {

    //Trata las excepciones lanzadas al violar una restricción 
    //de campo unico 
    @ExceptionHandler
    public ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        ServerError error = null;

        ConstraintViolationException ce
                = (ConstraintViolationException) ex.getCause();

        String constraint = ce.getConstraintName();

        switch (constraint) {
            case "userName":
                error = new ServerError(
                        new Date(),
                        409,
                        "userNameConstraint",
                        "El nombre de usuario ya es utilizado por otra persona");
                break;
            case "passwrd":
                error = new ServerError(
                        new Date(),
                        409,
                        "passwordConstraint",
                        "La contraseña ya es utilizada por otra persona");
                break;

            case "licensePlate":
                error = new ServerError(
                        new Date(),
                        409,
                        "licensePlateConstraint",
                        "Ya existe un vehículo con la misma matrícula");
                break;

            case "description":
                error = new ServerError(
                        new Date(),
                        409,
                        "descriptionConstraint",
                        "Ya existe una categoría con la misma descripción");

                break;
            default:
                break;
        }

        return ResponseEntity
                .status(HttpStatus.CONFLICT).body(error);

    }

    

    //Excepcion lanzada cuando se asigna un foreign key que no existe
    //Ejemplo: asignar a un vehiculo un id de categoria 
    //que no existe en la tabla categoria
    @ExceptionHandler
    public ResponseEntity<ServerError> handleJpaObjectRetrievalFailureException(
            JpaObjectRetrievalFailureException ex) {

        ServerError error = new ServerError(
                new Date(),
                500,
                "Internal Server Error",
                "Error interno relacionado a la base de datos.");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);

    }
    
    //Trata de manera general cualquier excepcion lanzada por la base de datos
    @ExceptionHandler
    public ResponseEntity handleDataAccessException(DataAccessException ex) {

        ServerError error = new ServerError(
                new Date(),
                500,
                "Internal Server Error",
                "Error interno relacionado a la base de datos.");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);

    }

}
