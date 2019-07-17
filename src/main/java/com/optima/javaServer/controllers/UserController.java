/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.controllers;

import com.optima.javaServer.model.Reservation;
import com.optima.javaServer.model.User;
import com.optima.javaServer.service.interfaces.IReservationService;
import com.optima.javaServer.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alex
 */
@RestController
@RequestMapping( path = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IReservationService reservationService;
    
    //GET
    @GetMapping(path = "/{userID}")
    public User getUserById(@PathVariable Integer userID) {

        return this.userService.getUserByID(userID);

    }

    //PUT
    @PutMapping(path = "/{userID}")
    public User updateUser(
            @PathVariable Integer userID,
            @RequestBody User user) {

        user.setUserID(userID);
        user = this.userService.updateUser(user);

        return user;

    }
    
    //POST
    @PostMapping(path = "/reservation")
    public String createReservation(@RequestBody Reservation reservation) {

        this.reservationService.createReservation(reservation);

        return "La reserva se ha realizado correctamente.";

    }

    //DELETE
    @DeleteMapping(path = "/reservation/{reservationID}")
    public String cancelReservation(
            @PathVariable("reservationID") Integer reservationID) {

        this.reservationService.cancelReservation(reservationID);

        return "La reserva ha sido cancelada correctamente.";

    }
    
    //GET 
    @GetMapping(path = "/auth")
    public boolean canActivateUserRestrictedComponent(){
        
        return true;
       
    }
}
