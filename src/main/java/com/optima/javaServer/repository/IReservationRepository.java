/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.repository;

import com.optima.javaServer.model.Reservation;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alex
 */

@Repository
public interface IReservationRepository extends  CrudRepository<Reservation, Integer>{

    @Override
    public void deleteById(Integer reservationID);

    @Override
    public Reservation save(Reservation reservation);
    
    @Query("SELECT r from Reservation r "
            + "WHERE (r.deliveryDate >= current_date() "
            + "OR r.returnDate >= current_date()) "
            + "AND r.vehicle.vehicleID = :vehicleID "
            + "ORDER BY r.deliveryDate")
    public List<Reservation> listByVehicleID(@Param("vehicleID") Integer vehicleID);
    
    @Query("SELECT MONTH(r.deliveryDate), COUNT(r) FROM Reservation r "
            + "GROUP BY MONTH(r.deliveryDate)")
    public List<Object[]> countForEachMonth();
    
}
    