/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.controllers;

import com.optima.javaServer.model.Vehicle;
import com.optima.javaServer.service.interfaces.IVehicleService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Alex
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping()
public class VehiclesController {

    @Autowired
    private IVehicleService vehicleService;

    //GET
    @GetMapping(path = "/vehicle/count/{categoryID}")
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
    @GetMapping(path = "/vehicle/{page}/{categoryID}")
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

    //GET
    @GetMapping(path = "admin/vehicle")
    public List<Vehicle> getAllVehicles() {

        return this.vehicleService.listAllVehicles();

    }

    //GET
    @GetMapping(path = "/admin/vehicle/{licensePlate}")
    public List<Vehicle> getVehicleByLicensePlate(
            @PathVariable("licensePlate") String licesenPlate) {

        return this.vehicleService
                .getByLicensePlate(licesenPlate);

    }

    //PUT
    @PutMapping(
            path = "/admin/vehicle",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateVehicle(
            @RequestPart(value = "vehicle") Vehicle v,
            @RequestPart(value = "picture", required = false) MultipartFile vehiclePicture)
            throws IOException {

        //Respuesta
        Map entity = new HashMap();

        //System.err.println(v.getPictureURL().substring(39));
        Vehicle vehicle = this.vehicleService.updateVehicle(v, vehiclePicture);

        //Retorno la nueva imagen para actualizar la vista en el cliente
        entity.put("vehicle", vehicle);
        entity.put(
                "successMessage",
                "El vehículo ha sido actualizado correctamente.");

        return ResponseEntity.ok(entity);

    }

    //POST
    @PostMapping(
            path = "/admin/vehicle",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createVehicle(
            @RequestPart("vehicle") Vehicle v,
            @RequestPart("picture") MultipartFile vehiclePicture
            )
            throws IOException {

        System.out.println(v.getCategory().getCategoryID());
        this.vehicleService.createVehicle(v, vehiclePicture);

        return "El vehículo ha sido creado correctamente.";

    }

}
