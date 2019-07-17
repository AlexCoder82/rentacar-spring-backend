/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.controllers;

import com.optima.javaServer.model.Vehicle;
import com.optima.javaServer.service.interfaces.IVehicleService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alex
 */
@RestController
@RequestMapping(path = "/vehicle")
public class VehiclesController {

    @Autowired
    private IVehicleService vehicleService;

    //GET
    @GetMapping(path = "/count/{categoryID}")
    public ResponseEntity countAndGetFirstVehicle(
            @PathVariable("categoryID") Integer categoryID,
            @RequestParam("brand") String brand,
            @RequestParam("model") String model) {

        //Respuesta
        Map entity = new HashMap();

        long totalVehicles = this.vehicleService.countTotalVehicles(
                categoryID,
                brand,
                model);

        //añado a la respuesta el total de vehiculos
        entity.put("totalVehicles", totalVehicles);

        //Si hay vehículos que corresponden a los criterios de busqueda
        //añado los vehiculos de la primera pagina a la respuesta
        if (totalVehicles > 0) {
            Vehicle vehicle = this.vehicleService.getPageVehicle(
                    1,
                    categoryID,
                    brand,
                    model);

            entity.put("pageVehicle", vehicle);
        }

        return ResponseEntity.ok(entity);

    }

    //GET
    @GetMapping(path = "/{page}/{categoryID}")
    public ResponseEntity getVehiclesPerPage(
            @PathVariable("page") int page,
            @PathVariable("categoryID") Integer categoryID,
            @RequestParam("brand") String brand,
            @RequestParam("model") String model) {

        //Respuesta
        Map entity = new HashMap();

        Vehicle vehicle = this.vehicleService.getPageVehicle(
                page,
                categoryID,
                brand,
                model);

        entity.put("pageVehicle", vehicle);

        return ResponseEntity.ok(entity);

    }

}
