/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.controllers;

import com.optima.javaServer.model.Category;
import com.optima.javaServer.model.Vehicle;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.optima.javaServer.service.interfaces.ICategoryService;
import com.optima.javaServer.service.interfaces.IReservationService;
import com.optima.javaServer.service.interfaces.IVehicleService;
import java.io.IOException;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Alex
 */
//@CrossOrigin("")
@RestController
@RequestMapping(path = "/admin")
public class AdminController {

     @Autowired
    private ICategoryService categoryService;
     
     @Autowired
    private IVehicleService vehicleService;
     
     @Autowired
    private IReservationService reservationService;

    //POST http://localhost:8080/rentacar/admin/category
    @PostMapping("/category")
    public String createCategory(@RequestBody Category category) {

        this.categoryService.createCategory(category);

        return "La categoria ha sido creada correctamente.";

    }

    //PUT http://localhost:8080/rentacar/admin/category
    @PutMapping("/category")
    public ResponseEntity updateCategory(@RequestBody Category category) {

         //Respuesta
        Map entity = new HashMap();
        
        category = this.categoryService.updateCategory(category);

        entity.put("category",category);
        entity.put("successMessage", 
                "La categoria ha sido modificada correctamente.");
        
        return ResponseEntity.ok(entity);
        
    }
    
    //GET
    @GetMapping(path = "/vehicle")
    public List<Vehicle> getAllVehicles() {

        return this.vehicleService.listAllVehicles();

    }

    //GET
    @GetMapping(path = "/vehicle/{licensePlate}")
    public List<Vehicle> getVehicleByLicensePlate(
            @PathVariable("licensePlate") String licesenPlate) {

        return this.vehicleService
                .getByLicensePlate(licesenPlate);

    }

    //PUT
    @PutMapping(
            path = "/vehicle",
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
            path = "/vehicle",
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
    
    //GET
    @GetMapping(path = "/reservation/count")
    public Map<Integer,Long> countReservationsPerMonth() {

        return this.reservationService
                .countReservationsPerMonth();

    }
    
    @GetMapping(path = "/auth")
    public boolean canActivateAdminRestrictedComponent(){
        
        return true;
       
    }

}
