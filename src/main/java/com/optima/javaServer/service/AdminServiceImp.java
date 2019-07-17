/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.service;

import com.optima.javaServer.service.interfaces.IAdminService;
import com.optima.javaServer.exceptions.WrongCredentialsException;
import com.optima.javaServer.model.Admin;
import com.optima.javaServer.repository.IAdminRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alex
 */

/**
 * 
 * Lógica de la petición de login de administrador
 */
@Service
public class AdminServiceImp implements IAdminService {

    @Autowired
    private IAdminRepository adminRepository;


    @Override
    public Admin checkAdminCredentials(Admin ad) {

        Optional<Admin> optionalAdmin = this.adminRepository
                .findByAdminNameAndPasswrd(ad.getAdminName(), ad.getPasswrd());

        if (!optionalAdmin.isPresent()) {
            throw new WrongCredentialsException();
        }

        return optionalAdmin.get();

    }

}
