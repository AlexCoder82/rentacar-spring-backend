/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.service.interfaces;

import com.optima.javaServer.model.Reservation;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alex
 */
@Service
public interface IReservationService {

    public void cancelReservation(Integer reservationID);

    public void createReservation(Reservation reservation);

    public Map countAndGetFirstAvailableReservation(LocalDate deliveryDate,
            LocalDate returnDate);

    public Map countAndGetFirstAvailableReservationByCategory(
            Integer categoryId,
            LocalDate deliveryDate,
            LocalDate returnDate);

    public Reservation getPageAvailableReservation(
            int page,
            LocalDate deliveryDate,
            LocalDate returnDate);

    public Reservation getPageAvailableReservationByCategory(
            int page,
            Integer categoryId,
            LocalDate deliveryDate,
            LocalDate returnDate);

    public Map<Integer,Long> countReservationsPerMonth();
    
}
