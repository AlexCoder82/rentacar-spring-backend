/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.security.interfaces;

import com.optima.javaServer.model.Admin;
import com.optima.javaServer.model.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alex
 */

@Component
public interface ITokenUtil {
    
    public String generateAdminToken(Admin admin);
    
    public String generateUserToken(User user);
    
    public void validateToken(String privateKey) throws IllegalArgumentException;

}
