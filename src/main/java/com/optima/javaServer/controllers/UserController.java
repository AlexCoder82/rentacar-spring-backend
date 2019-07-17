/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.controllers;

import com.optima.javaServer.model.User;
import com.optima.javaServer.security.interfaces.ITokenUtil;
import com.optima.javaServer.service.interfaces.IUserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping()
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ITokenUtil tokenUtil;

   

    //GET
    @GetMapping(path = "/user/{userID}")
    public User getUserById(@PathVariable Integer userID) {

        return this.userService.getUserByID(userID);

    }

    //POST
    @PostMapping(path = "/user")
    public boolean registerUser(@RequestBody User user) {

        this.userService.registerUser(user);

        return true;

    }

    //PUT
    @PutMapping(path = "/user/{userID}")
    public User updateUser(
            @PathVariable Integer userID,
            @RequestBody User user) {

        user.setUserID(userID);
        user = this.userService.updateUser(user);

        return user;

    }

}
