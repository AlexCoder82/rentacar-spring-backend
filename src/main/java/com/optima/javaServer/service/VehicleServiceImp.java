/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.service;

import com.optima.javaServer.service.interfaces.IVehicleService;
import com.optima.javaServer.model.Vehicle;
import com.optima.javaServer.repository.IVehicleRepository;
import com.optima.javaServer.validation.interfaces.IVehicleValidation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import org.apache.commons.io.FilenameUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Alex
 */
/**
 *
 * Lógica de todas las peticiones relacionadas a los vehículos
 *
 */
@Service
public class VehicleServiceImp implements IVehicleService {

    @Autowired
    private IVehicleRepository vehicleRepository;

    @Autowired
    private IVehicleValidation vehicleValidation;

    private static final String PICTURES_URL
            = "http://localhost:8080/rentacar/uploads/";
    private static final String UPLOADS_URL
            = "src/main/resources/static/uploads/";

    @Override
    public Vehicle updateVehicle(
            Vehicle vehicle,
            MultipartFile vehiclePicture)
            throws IOException {

        this.vehicleValidation.validateVehicle(vehicle);
        

        //Si se cambia de imagen 
        if (vehiclePicture != null) {
            this.vehicleValidation.validateVehicleImageExtension(vehiclePicture);
            String fileName = this.saveVehicleImage(vehiclePicture);
            //Borro la antigua imagen   
            Files.delete(Paths.get(UPLOADS_URL + vehicle.getPictureName()));
            //Nuevo nombre de la imagen
            vehicle.setPictureName(fileName);
        }
        
        vehicle = this.vehicleValidation.format(vehicle);

        return this.vehicleRepository.save(vehicle);

    }

    @Override
    public Vehicle createVehicle(
            Vehicle vehicle,
            MultipartFile vehiclePicture) throws IOException
             {

        this.vehicleValidation.validateVehicle(vehicle);
        this.vehicleValidation.validateVehicleImageExtension(vehiclePicture);
        
        vehicle = this.vehicleValidation.format(vehicle);

        String fileName = this.saveVehicleImage(vehiclePicture);
        vehicle.setPictureName(fileName);

        return this.vehicleRepository.save(vehicle);

    }

    @Override
    public List<Vehicle> listAllVehicles() {

        List<Vehicle> vehicles = this.vehicleRepository.findAll();
        for (Vehicle v : vehicles) {
            System.out.println(v.getPictureURL());
        }
        return vehicles;

    }

    @Override
    public List<Vehicle> getByLicensePlate(String licensePlate) {

        return this.vehicleRepository
                .findByLicensePlateStartsWith(licensePlate);

    }

    @Override
    public long countTotalVehicles(
            Integer categoryID,
            String brand,
            String model) {

        long totalVehicles = 0;

        if (categoryID == 0) {
            totalVehicles = this.vehicleRepository
                    .countByBrandStartsWithAndModelStartsWith(
                            brand,
                            model);
        } else {
            totalVehicles = this.vehicleRepository
                    .countByCategoryCategoryIDAndBrandStartsWithAndModelStartsWith(
                            categoryID,
                            brand,
                            model);
        }

        if (totalVehicles == 0) {

        }

        return totalVehicles;

    }

    @Override
    @Transactional
    public Vehicle getPageVehicle(
            int page,
            Integer categoryID,
            String brand,
            String model) {

        //Creo un objeto pageable para que la base de datos devuelva 
        //tantos vehiculos como "vehiclesPerPage" y ordenados por marca y modelo
        Pageable pageable = PageRequest.of(
                page - 1,
                1,
                Sort.by("brand").ascending().and(Sort.by("model").ascending())
        );

        List<Vehicle> vehicles;

        if (categoryID == 0) {
            vehicles = this.vehicleRepository
                    .findByBrandStartsWithAndModelStartsWith(
                            brand,
                            model,
                            pageable);
        } else {
            vehicles = this.vehicleRepository
                    .findByCategoryCategoryIDAndBrandStartsWithAndModelStartsWith(
                            categoryID,
                            brand,
                            model,
                            pageable);
        }

        return vehicles.get(0);

    }

    //Guarda la imagen del vehiculo en la carpeta publica y retorno el nombre
    private String saveVehicleImage(MultipartFile image) throws IOException {

        //Para asegurarme de que dos imagenes no tengan el mismo nombre,
        //les pongo como nombre el tiempo actual en ms       
        String fileName = String.valueOf(Calendar.getInstance()
                .getTimeInMillis());
        //Añado la extension de la imagen
        String extension = this.getImageExtension(image);
        fileName += extension;
        byte[] bytes = image.getBytes();
        Path path = Paths.get(UPLOADS_URL + fileName);
        Files.write(path, bytes);

        return fileName;

    }

    private String getImageExtension(MultipartFile image) {

        String originalFileName = image.getOriginalFilename();
        int extensionIndex = originalFileName.lastIndexOf('.');
        
        
        return originalFileName.substring(extensionIndex).toLowerCase();

    }

}
