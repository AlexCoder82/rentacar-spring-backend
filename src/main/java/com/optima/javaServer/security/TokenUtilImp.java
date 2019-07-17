/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.security;

import com.optima.javaServer.security.interfaces.ITokenUtil;
import com.optima.javaServer.model.Admin;
import com.optima.javaServer.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alex
 */
@Component
public class TokenUtilImp implements ITokenUtil {

    private static final int TOKEN_EXPIRATION_TIME = 900; //15 MINUTOS EN SEGUNDOS

    @Override
    public String generateAdminToken(Admin admin) {

        Map<String, Object> claims = new HashMap<>();
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(now.getTime() + TOKEN_EXPIRATION_TIME * 1000);

        return Jwts.builder().setClaims(claims)
                .setSubject(admin.getAdminName())
                .setSubject(admin.getPasswrd())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, "secretKey").compact();
    }

    @Override
    public String generateUserToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(now.getTime() + TOKEN_EXPIRATION_TIME * 1000);

        return Jwts.builder().setClaims(claims)
                .setSubject(user.getUserName())
                .setSubject(user.getPasswrd())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, "secretKey").compact();

    }

    @Override
    public void validateToken(String bearerToken){

        Jwts.parser()
                .setSigningKey("secretKey")
                .parseClaimsJws(bearerToken);

    }

}
