/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optima.javaServer.config;

import com.optima.javaServer.interceptors.AdminAuthorizationInterceptor;
import com.optima.javaServer.interceptors.UserAuthorizationInterceptor;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * @author Alex
 */

@Configuration
public class Config implements WebMvcConfigurer{

    @Autowired
    private AdminAuthorizationInterceptor adminAuthorizationInterceptor;
    
    @Autowired
    private UserAuthorizationInterceptor userAuthorizationInterceptor;

    //Rutas protegidas y reservadas al administrador
    private static final List<String> ADMIN_PATH_PATTERNS = Arrays.asList(
            "/admin/category",
            "/admin/vehicle"
    );
    
    //Rutas protegidas y reservadas al usuario
    private static final List<String> USER_PATH_PATTERNS = Arrays.asList(
            "/user/reservation",
            "/user/reservation/{reservationID}" ,
            "/user/{userID}",
            "/canActivatePage"
            
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            
        registry.addInterceptor(adminAuthorizationInterceptor)
                .addPathPatterns(ADMIN_PATH_PATTERNS);
    
        registry.addInterceptor( userAuthorizationInterceptor)
                .addPathPatterns(USER_PATH_PATTERNS);      
        
    }
    
   
}
