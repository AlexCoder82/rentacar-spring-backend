package com.optima.javaServer.service;

import com.optima.javaServer.service.interfaces.IUserService;
import com.optima.javaServer.exceptions.DataNotFoundException;
import com.optima.javaServer.exceptions.WrongCredentialsException;
import com.optima.javaServer.model.Admin;
import com.optima.javaServer.model.Reservation;
import com.optima.javaServer.model.User;
import com.optima.javaServer.repository.IAdminRepository;
import com.optima.javaServer.repository.IUserRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alex
 */
/**
 * Servicio que contiene toda la lógica relacionada al usuario
 */
@Service
public class UserServiceImp implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAdminRepository adminRepository;

    @Override
    public User checkUserCredentials(String userName, String passwrd) {

        Optional<User> opcionalUser = this.userRepository
                .findByUserNameAndPasswrd(userName, passwrd);

        //Si no encuentra los credenciales, lanzo una excepción
        if (!opcionalUser.isPresent()) {
            throw new WrongCredentialsException();
        }

        return opcionalUser.get();

    }

    @Override
    public void registerUser(User user) {

        this.checkIfUserAlreadyExistsAsAdmin(user);
        this.userRepository.save(user);

    }

    @Override
    public User updateUser(User user) {

        this.checkIfUserAlreadyExistsAsAdmin(user);
        return this.userRepository.save(user);

    }

    @Override
    public User getUserByID(Integer userID) {

        Optional<User> opcionalUser = this.userRepository.findById(userID);

        //Esta peticion no puede devolver un resultado vacío
        if (!opcionalUser.isPresent()) {
            throw new DataNotFoundException();
        }

        User user = opcionalUser.get();
        for (Reservation r : user.getReservations()) {
            UserServiceImp.setReservationStatus(r);
        }

        return user;

    }

    private static void setReservationStatus(Reservation reservation) {

        if (reservation.getReturnDate().isBefore(LocalDate.now())) {
            reservation.setStatus(Reservation.Status.DONE);
        }

        if (reservation.getDeliveryDate().compareTo(LocalDate.now()) <= 0
                && reservation.getReturnDate().compareTo(LocalDate.now()) >= 0) {
            reservation.setStatus(Reservation.Status.IN_PROGRESS);
        }

        if (reservation.getDeliveryDate().isAfter(LocalDate.now())) {
            reservation.setStatus(Reservation.Status.CANCELABLE);
        }

    }

    //Comprueba si los credenciales del usuario ya son utilizados por
    //un administrador, en tal caso lanzo una excepción 
    private void checkIfUserAlreadyExistsAsAdmin(User user) {

        Optional<Admin> optionalAdmin = this.adminRepository
                .findByAdminName(user.getUserName());

        if (optionalAdmin.isPresent()) {
            ConstraintViolationException cve
                    = new ConstraintViolationException("", null, "userName");
            throw new DataIntegrityViolationException("", cve);
        }

        optionalAdmin = this.adminRepository
                .findByPasswrd(user.getPasswrd());

        if (optionalAdmin.isPresent()) {
            ConstraintViolationException cve
                    = new ConstraintViolationException("", null, "passwrd");
            throw new DataIntegrityViolationException("", cve);
        }

    }

}
