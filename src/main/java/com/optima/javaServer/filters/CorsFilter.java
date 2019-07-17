/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Alex
 */

/**
 * Filtra todas las peticiones y configura el CORS 
 */
@Component
public class CorsFilter extends OncePerRequestFilter {

    public static final String ORIGIN_NAME = "Access-Control-Allow-Origin";
    public static final String METHODS_NAME = "Access-Control-Allow-Methods";
    public static final String HEADERS_NAME = "Access-Control-Allow-Headers";
    public static final String MAX_AGE_NAME = "Access-Control-Max-Age";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, 
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        response.addHeader(ORIGIN_NAME, "*");
        response.addHeader(METHODS_NAME, "OPTIONS, GET, POST, PUT, DELETE");
        response.addHeader(HEADERS_NAME, "userauthorization,adminauthorization,Origin,"
                + " X-Requested-With, Content-Type, Accept");
        response.addHeader(MAX_AGE_NAME, "1");
        
        filterChain.doFilter(request, response);
        
    }

}
