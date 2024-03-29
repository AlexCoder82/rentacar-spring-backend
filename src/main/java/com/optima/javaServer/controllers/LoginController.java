/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optima.javaServer.controllers;

import com.optima.javaServer.service.interfaces.ILoginService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Alex
 */

@RestController
@RequestMapping(path = "/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;
   
    //POST
    @PostMapping()
    public ResponseEntity login(
            @RequestBody Map<String,String> credentials) {
        
        String userName = credentials.get("userName");
        String passwrd = credentials.get("passwrd");

        Map responseEntity = this.loginService.login(userName, passwrd);

        return ResponseEntity.ok(responseEntity);
        
    }
       
}
