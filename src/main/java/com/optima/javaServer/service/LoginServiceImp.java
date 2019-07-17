/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.service;

import com.optima.javaServer.exceptions.WrongCredentialsException;
import com.optima.javaServer.model.Admin;
import com.optima.javaServer.model.User;
import com.optima.javaServer.repository.IAdminRepository;
import com.optima.javaServer.repository.IUserRepository;
import com.optima.javaServer.security.interfaces.ITokenUtil;
import com.optima.javaServer.service.interfaces.ILoginService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alex
 */
@Service
public class LoginServiceImp implements ILoginService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAdminRepository adminRepository;

    @Autowired
    private ITokenUtil tokenUtil;

    @Override
    public Map login(String userName, String passwrd) {

        //Respuesta
        Map responseEntity = new HashMap();

        //Compruebo primero si son credenciales de administrador
        Optional<Admin> optionalAdmin = this.adminRepository
                .findByAdminNameAndPasswrd(userName, passwrd);

        //Si es un admin...
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            //Genero un token 
            String adminToken = this.tokenUtil.generateAdminToken(admin);
            //Creo una respuesta que contiene el nombre del admin y el token
            responseEntity.put("adminToken", adminToken);
            responseEntity.put("adminName", admin.getAdminName());
        } else {
            //Compruebo si son credenciales de usuario
            Optional<User> opcionalUser = this.userRepository
                    .findByUserNameAndPasswrd(userName, passwrd);
            //Si no encuentra los credenciales, lanzo una excepción
            if (!opcionalUser.isPresent()) {
                throw new WrongCredentialsException();
            }
            User user = opcionalUser.get();
            //Genero un token 
            String userToken = this.tokenUtil.generateUserToken(user);
            //Creo una respuesta que contiene el nombre del usuario
            //el ID y el token
            responseEntity.put("userFirstName", user.getFirstName());
            responseEntity.put("userID", user.getUserID());
            responseEntity.put("userToken", userToken);
        }
        
        return responseEntity;
        
    }

}
