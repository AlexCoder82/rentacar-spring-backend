/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.controllers;

import com.optima.javaServer.model.Reservation;
import com.optima.javaServer.service.interfaces.IReservationService;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alex
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping()
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    //POST
    @PostMapping(path = "/user/reservation")
    public String createReservation(@RequestBody Reservation reservation) {

        this.reservationService.createReservation(reservation);

        return "La reserva se ha realizado correctamente.";

    }

    //DELETE
    @DeleteMapping(path = "/user/reservation/{reservationID}")
    public String cancelReservation(
            @PathVariable("reservationID") Integer reservationID) {

        this.reservationService.cancelReservation(reservationID);

        return "La reserva ha sido cancelada correctamente.";

    }
//GET

    @GetMapping(path = "/reservation/available/count")
    public ResponseEntity countAndGetFirstAvailableReservation(
            @RequestParam("returnDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate returnDate,
            @RequestParam("deliveryDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deliveryDate) {

        //Respuesta
        Map entity = this.reservationService
                .countAndGetFirstAvailableReservation(
                        deliveryDate,
                        returnDate);

        return ResponseEntity.ok(entity);

    }

    @GetMapping(path = "/reservation/available/count/{categoryID}")
    public ResponseEntity countAndGetFirstAvailableReservationByCategory(
            @PathVariable("categoryID") Integer categoryID,
            @RequestParam("returnDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate returnDate,
            @RequestParam("deliveryDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deliveryDate) {

        //Respuesta
        Map entity = this.reservationService
                .countAndGetFirstAvailableReservationByCategory(
                        categoryID,
                        deliveryDate,
                        returnDate);

        return ResponseEntity.ok(entity);

    }

    //GET
    @GetMapping(path = "/reservation/available/{page}")
    public Reservation getPageAvailableVehicle(
            @PathVariable("page") int page,
            @RequestParam("returnDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate returnDate,
            @RequestParam("deliveryDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deliveryDate) {

        return this.reservationService
                .getPageAvailableReservation(
                        page,
                        deliveryDate,
                        returnDate);

    }

    //GET
    @GetMapping(path = "/reservation/available/{page}/{categoryID}")
    public Reservation getPageAvailableVehicleByCategory(
            @PathVariable("page") int page,
            @PathVariable("categoryID") Integer categoryID,
            @RequestParam("returnDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate returnDate,
            @RequestParam("deliveryDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deliveryDate) {

        return this.reservationService
                .getPageAvailableReservationByCategory(
                        page,
                        categoryID,
                        deliveryDate,
                        returnDate);

    }
    
    //GET
    @GetMapping(path = "/reservation/count")
    public Map<Integer,Long> countReservationsPerMonth() {

        return this.reservationService
                .countReservationsPerMonth();

    }

}
