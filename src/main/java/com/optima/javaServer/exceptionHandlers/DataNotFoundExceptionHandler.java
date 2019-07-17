package com.optima.javaServer.exceptionHandlers;

import com.optima.javaServer.exceptions.DataNotFoundException;
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
/**
 *
 * Trata las excepciones DataNotFoundException, devuelve un codigo de estado 500
 * y un objeto de error al cliente.
 *
 */
@RestControllerAdvice
public class DataNotFoundExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleDataNotFoundException(DataNotFoundException ex) {

        ServerError error = new ServerError(
                new Date(),
                500,
                "Internal Server Error",
                "No se han encontrado los datos requeridos.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);

    }

}
