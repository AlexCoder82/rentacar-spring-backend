/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.service.interfaces;


import com.optima.javaServer.model.Vehicle;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Alex
 */

@Service
public interface IVehicleService {
    
    public Vehicle updateVehicle(Vehicle v, MultipartFile vehiclePicture)throws IOException;
    
    public Vehicle createVehicle(Vehicle v, MultipartFile vehiclePicture) throws IOException;
    
    public List<Vehicle> listAllVehicles();
    
    public List<Vehicle> getByLicensePlate(String licensePlate) ;
    
    public long countTotalVehicles(
            Integer categoryID,
            String brand, 
            String model);
    
    public Vehicle getPageVehicle(
            int page,
            Integer categoryID,
            String brand,
            String model);
      
}