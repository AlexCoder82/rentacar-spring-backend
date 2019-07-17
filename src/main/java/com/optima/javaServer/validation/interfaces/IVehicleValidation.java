/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.validation.interfaces;

import com.optima.javaServer.exceptions.DataValidationException;
import com.optima.javaServer.exceptions.ImageExtensionException;
import com.optima.javaServer.model.Vehicle;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Alex
 */

@Component
public interface IVehicleValidation {
    
    public void validateVehicle(Vehicle vehicle) throws DataValidationException;
    
    public void validateVehicleImageExtension(MultipartFile image)throws ImageExtensionException;
    
    public Vehicle format(Vehicle vehicle);
}
