/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.interceptors;

import com.optima.javaServer.security.interfaces.ITokenUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author Alex
 */
@Component
public class UserAuthorizationInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    private ITokenUtil tokenUtil;

    @Override
    public boolean preHandle(
            HttpServletRequest req,
            HttpServletResponse res,
            Object handler)
            throws Exception {

        if (req.getHeader("Access-Control-Request-Method") != null 
                && "OPTIONS".equals(req.getMethod())) {
            return true;
        }
 
        //Recupero el token del header
        String userToken = req.getHeader("userauthorization").substring(7);
       
        //Compruebo su validez
        this.tokenUtil.validateToken(userToken);

        return true;

    }

}
