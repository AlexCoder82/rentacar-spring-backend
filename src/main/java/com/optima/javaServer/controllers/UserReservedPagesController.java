/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optima.javaServer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Alex
 */

@RestController
@RequestMapping(path= "/canActivatePage")
public class UserReservedPagesController {

    @GetMapping()
    public boolean canActivatePage(){
        
        return true;
       
    }
}
