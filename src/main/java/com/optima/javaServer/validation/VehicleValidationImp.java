/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.validation;

import com.optima.javaServer.exceptions.DataValidationException;
import com.optima.javaServer.exceptions.ImageExtensionException;
import com.optima.javaServer.model.Vehicle;
import com.optima.javaServer.validation.interfaces.IVehicleValidation;
import java.util.Iterator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Alex
 */
@Component
public class VehicleValidationImp implements IVehicleValidation {

    @Override
    public void validateVehicle(Vehicle vehicle)
            throws DataValidationException {

        //MATRICULA
        if (!vehicle.getLicensePlate().matches("[0-9]{4}[a-zA-Z]{3}")) {
            throw new DataValidationException("La matrícula no es válida");
        }

        //MARCA
        if (vehicle.getBrand().length() > 50) {
            throw new DataValidationException("La marca no puede tener más de "
                    + " 50 caracteres");
        }

        //MODELO
        if (vehicle.getModel().length() > 50) {
            throw new DataValidationException("El modelo no puede tener más de "
                    + " 50 caracteres");
        }

        //ASIENTOS
        if (vehicle.getSeats() != 2
                && vehicle.getSeats() != 4
                && vehicle.getSeats() != 5) {
            throw new DataValidationException("La cantidad de asientos no es "
                    + "válida");
        }

        //PUERTAS
        if (vehicle.getDoors() != 3
                && vehicle.getDoors() != 4
                && vehicle.getDoors() != 5) {
            throw new DataValidationException("La cantidad de puertas no es "
                    + "válida");
        }

        //TRANSMISSION
        if (!vehicle.getTransmission().toUpperCase().equals("MANUAL")
                && !vehicle.getTransmission().toUpperCase().equals("AUTOMATIC")) {
            throw new DataValidationException("La transmisión no es válida");
        }

        //KILOMETROS
        if (vehicle.getKms() > 999999) {
            throw new DataValidationException("El kilometraje no puede "
                    + "sobrepasar 999 999");
        }
        
    }
    
     @Override
    public void validateVehicleImageExtension(MultipartFile image) throws ImageExtensionException {
        
        String originalFileName = image.getOriginalFilename();
        
        int extensionIndex = originalFileName.lastIndexOf('.');
        
        if(extensionIndex == -1){
            throw new ImageExtensionException();
        }
        
        String extension = originalFileName
                .substring(extensionIndex).toLowerCase();
        
        if(!extension.equals(".jpg") && !extension.equals(".jpeg")
                && !extension.equals(".png")){
            throw new ImageExtensionException();
        }
        
    }


    @Override
    public Vehicle format(Vehicle vehicle) {

        vehicle.setLicensePlate(vehicle.getLicensePlate().toUpperCase());

        StringBuilder brand = new StringBuilder(vehicle.getBrand());
        
        //Borra los posibles espacios al inicio
        while (brand.charAt(0) == ' ') {
            brand.deleteCharAt(0);
        }

        //Borra los posibles espacios de más entre las palabras y al final      
        for (int i = 1; i < brand.length(); i++) {
            if (brand.charAt(i - 1) == ' ' && brand.charAt(i) == ' ') {
                brand.deleteCharAt(i - 1);
                i--;
            }
            if (brand.charAt(brand.length() - 1) == ' ') {
                brand.deleteCharAt(brand.length() - 1);
            }
        }
        
        vehicle.setBrand(brand.toString().toUpperCase());

        
        StringBuilder model = new StringBuilder(vehicle.getModel());

        //Borra los posibles espacios al inicio
        while (model.charAt(0) == ' ') {
            model.deleteCharAt(0);
        }

        //Borra los posibles espacios de más entre las palabras y al final       
        for (int i = 1; i < model.length(); i++) {
            if (model.charAt(i - 1) == ' ' && model.charAt(i) == ' ') {
                model.deleteCharAt(i - 1);
                i--;
            }
            if (model.charAt(model.length() - 1) == ' ') {
                model.deleteCharAt(model.length() - 1);
            }
        }

        vehicle.setModel(model.toString().toUpperCase());

        vehicle.setTransmission(vehicle.getTransmission().toUpperCase());
        return vehicle;

    }

   
}
