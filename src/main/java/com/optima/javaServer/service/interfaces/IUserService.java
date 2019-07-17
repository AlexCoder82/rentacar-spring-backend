/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.service.interfaces;

import com.optima.javaServer.model.User;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alex
 */

@Service
public interface IUserService {
    
    public User checkUserCredentials(String userName, String passwrd);
    
    public void registerUser(User user);
    
    public User updateUser(User user);
    
    public User getUserByID(Integer userID);
    
}
