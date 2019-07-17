/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optima.javaServer.controllers;

import com.optima.javaServer.model.User;
import com.optima.javaServer.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Alex
 */

@RestController
@RequestMapping(path = "/registration")
public class RegistrationController {

    @Autowired
    private IUserService userService;
    
    //POST
    @PostMapping()
    public boolean registerUser(@RequestBody User user) {

        this.userService.registerUser(user);

        return true;

    }
}
