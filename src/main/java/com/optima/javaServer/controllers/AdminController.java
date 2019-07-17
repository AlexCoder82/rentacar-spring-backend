/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.controllers;

import com.optima.javaServer.model.Admin;
import com.optima.javaServer.service.interfaces.IAdminService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.optima.javaServer.security.interfaces.ITokenUtil;
import org.springframework.http.MediaType;

/**
 *
 * @author Alex
 */
//@CrossOrigin("")
@RestController
@RequestMapping(
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private ITokenUtil adminTokenUtil;

    //POST
    //http://localhost:8080/rentacar/admin/login
    @PostMapping("/admin/login")
    public ResponseEntity checkAdminCredentials(@RequestBody Admin ad) {

        //Respuesta
        Map entity = new HashMap();

        Admin admin = this.adminService.checkAdminCredentials(ad);
        //Genero un token 
        String adminToken = this.adminTokenUtil.generateAdminToken(admin);

        //Creo una respuesta que contiene el nombre del admin y el token
        entity.put("adminToken", adminToken);
        entity.put("adminName", admin.getAdminName());

        return ResponseEntity.ok(entity);

    }

}
