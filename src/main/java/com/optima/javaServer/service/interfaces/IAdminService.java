/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optima.javaServer.service.interfaces;

import com.optima.javaServer.model.Admin;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Alex
 */

@Service
public interface IAdminService {

    public Admin checkAdminCredentials(Admin admin);
    
}
