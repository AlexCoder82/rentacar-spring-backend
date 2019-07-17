package com.optima.javaServer.service;

import com.optima.javaServer.service.interfaces.IReservationService;
import com.optima.javaServer.model.Reservation;
import com.optima.javaServer.model.Vehicle;
import com.optima.javaServer.repository.IReservationRepository;
import com.optima.javaServer.repository.IVehicleRepository;
import java.time.LocalDate;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alex
 */
@Service
public class ReservationServiceImp implements IReservationService {

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IVehicleRepository vehicleRepository;

    @Override
    public void cancelReservation(Integer reservationID) {

        this.reservationRepository.deleteById(reservationID);

    }

    @Override
    public void createReservation(Reservation reservation) {

        this.reservationRepository.save(reservation);

    }

    @Override
    @Transactional
    public Map countAndGetFirstAvailableReservation(LocalDate deliveryDate,
            LocalDate returnDate) {

        //Respuesta
        Map entity = new HashMap();

        long totalAvailableReservations = this.vehicleRepository
                .countAvailableVehicles(deliveryDate, returnDate);

        //añado a la respuesta el total de reservas
        entity.put("totalReservations", totalAvailableReservations);

        //Si hay reservas disponibles añado la reserva 
        //de la primera pagina a la respuesta
        if (totalAvailableReservations > 0) {

            Reservation reservation = this.getPageAvailableReservation(
                    1,
                    deliveryDate,
                    returnDate);

            entity.put("reservation", reservation);
        }

        return entity;

    }

    @Override
    public Map countAndGetFirstAvailableReservationByCategory(
            Integer categoryId,
            LocalDate deliveryDate,
            LocalDate returnDate) {

        //Respuesta
        Map entity = new HashMap();

        long totalAvailableReservations = this.vehicleRepository
                .countAvailableVehiclesByCategory(
                        deliveryDate,
                        returnDate,
                        categoryId);

        //añado a la respuesta el total de reservas
        entity.put("totalReservations", totalAvailableReservations);

        //Si hay reservas disponibles añado la reserva 
        //de la primera pagina a la respuesta
        if (totalAvailableReservations > 0) {

            Reservation reservation = this.getPageAvailableReservationByCategory(
                    1,
                    categoryId,
                    deliveryDate,
                    returnDate);

            entity.put("reservation", reservation);
        }

        return entity;

    }

    @Override
    public Reservation getPageAvailableReservation(
            int page,
            LocalDate deliveryDate,
            LocalDate returnDate) {

        //Creo un objeto pageable para que la base de datos devuelva 
        //un solo registro despues de ordenarlos por marca y modelo
        Pageable pageable = PageRequest.of(
                page - 1,
                1,
                Sort.by("brand").ascending().and(Sort.by("model").ascending())
        );

        List<Vehicle> vehicles = this.vehicleRepository
                .findAvailable(deliveryDate, returnDate, pageable);

        Vehicle vehicle = vehicles.get(0);

        Reservation reservation = new Reservation();
        reservation.setDeliveryDate(deliveryDate);
        reservation.setReturnDate(returnDate);
        reservation.setVehicle(vehicle);
        float totalPrice = this.calculateReservationTotalPrice(reservation, deliveryDate, returnDate);
        reservation.setTotalPrice(totalPrice);

        return reservation;

    }

    @Override
    public Reservation getPageAvailableReservationByCategory(
            int page,
            Integer categoryID,
            LocalDate deliveryDate,
            LocalDate returnDate) {

        //Creo un objeto pageable para que la base de datos devuelva 
        //un solo registro despues de ordenarlos por marca y modelo
        Pageable pageable = PageRequest.of(
                page - 1,
                1,
                Sort.by("brand").ascending().and(Sort.by("model").ascending())
        );

        List<Vehicle> vehicles = this.vehicleRepository
                .findAvailableByCategory(
                        deliveryDate,
                        returnDate,
                        categoryID,
                        pageable);

        Vehicle vehicle = vehicles.get(0);

        Reservation reservation = new Reservation();
        reservation.setDeliveryDate(deliveryDate);
        reservation.setReturnDate(returnDate);
        reservation.setVehicle(vehicle);
        float totalPrice = this.calculateReservationTotalPrice(reservation, deliveryDate, returnDate);
        reservation.setTotalPrice(totalPrice);

        return reservation;

    }

    private long getDifferenceDays(LocalDate d1, LocalDate d2) {

        long diff = d2.getDayOfYear() - d1.getDayOfYear();

        return diff;

    }

    private float calculateReservationTotalPrice(Reservation reservation, LocalDate deliveryDate, LocalDate returnDate) {

        long days = this.getDifferenceDays(deliveryDate, returnDate);
        float pricePerDay = reservation.getVehicle()
                .getCategory().getPricePerDay();

        return days * pricePerDay;

    }

    @Override
    public Map<Integer, Long> countReservationsPerMonth() {

        //Creo un map que asocia 0 reservas a cada mes del año
        Map<Integer, Long> totalReservations = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            totalReservations.put(i, 0L);
        }

        List<Object[]> result
                = this.reservationRepository.countForEachMonth();

        //Recupero los meses y su cantidad asociada de la lista de 
        //objetos devuelta por la base de dato y modifico el map
        for (Object[] ob : result) {
            int key = (int) ob[0];// numero de mes
            long value = (Long) ob[1];//cantidad de reservas de ese mes
            totalReservations.put(key, value);
        }

        return totalReservations;

    }

}
