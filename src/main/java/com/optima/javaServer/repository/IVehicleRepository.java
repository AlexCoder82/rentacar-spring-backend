/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.repository;

import com.optima.javaServer.model.Reservation;
import com.optima.javaServer.model.Vehicle;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alex
 */
@Repository
public interface IVehicleRepository extends CrudRepository<Vehicle, Integer> {

    //Inserta si el id del vehículo es nulo, sino hace un update
    @Override
    public Vehicle save(Vehicle v);

    @Override
    public List<Vehicle> findAll();

    public List<Vehicle> findByLicensePlateStartsWith(String licensePlate);

    public long countByBrandStartsWithAndModelStartsWith(
            String brand,
            String model);

    public long countByCategoryCategoryIDAndBrandStartsWithAndModelStartsWith(
            Integer categoryID,
            String brand,
            String model);

    public List<Vehicle> findByCategoryCategoryIDAndBrandStartsWithAndModelStartsWith(
            Integer categoryID,
            String brand,
            String model,
            Pageable pageable);
   

    
    public List<Vehicle> findByBrandStartsWithAndModelStartsWith(
            String brand,
            String model,
            Pageable pageable);

    @Query("SELECT COUNT(*) FROM Vehicle v "
            + "LEFT JOIN Reservation r ON v.vehicleID = r.vehicle.vehicleID "
            + "AND r.returnDate >= :deliverydate "
            + "AND r.deliveryDate <= :returndate "
            + "WHERE r.reservationID IS NULL ")
    public long countAvailableVehicles(
            @Param("deliverydate") LocalDate deliveryDate,
            @Param("returndate") LocalDate returnDate);

    @Query("SELECT COUNT(v)  FROM Vehicle v "
            + "LEFT JOIN Reservation r ON v.vehicleID = r.vehicle.vehicleID "
            + "AND r.returnDate >= :deliverydate "
            + "AND r.deliveryDate <= :returndate "
            + "WHERE v.category.categoryID = :categoryid "
            + "AND r.reservationID IS NULL ")
    public long countAvailableVehiclesByCategory(   
            @Param("deliverydate") LocalDate deliveryDate,
            @Param("returndate") LocalDate returnDate,
            @Param("categoryid")Integer categoryID);
    
    @Query("SELECT v FROM Vehicle v "
            + "LEFT JOIN Reservation r ON v.vehicleID = r.vehicle.vehicleID "
            + "AND r.returnDate >= :deliverydate "
            + "AND r.deliveryDate <= :returndate "
            + "WHERE r.reservationID IS NULL "
            + "GROUP BY v.vehicleID")
    public List<Vehicle> findAvailable(
            @Param("deliverydate") LocalDate deliveryDate,
            @Param("returndate") LocalDate returnDate,
            Pageable pageable);

    @Query("SELECT v FROM Vehicle v "
            + "LEFT JOIN Reservation r ON v.vehicleID = r.vehicle.vehicleID "
            + "AND r.returnDate >= :deliverydate "
            + "AND r.deliveryDate <= :returndate "
            + "WHERE v.category.categoryID = :categoryid "
            + "AND r.reservationID IS NULL "
            + "GROUP BY v.vehicleID")
    public List<Vehicle> findAvailableByCategory(
            @Param("deliverydate") LocalDate deliveryDate,
            @Param("returndate") LocalDate returnDate,
            @Param("categoryid") Integer categoryID,
            Pageable pageable);

}
