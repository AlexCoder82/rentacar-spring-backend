package com.optima.javaServer.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.optima.javaServer.security.interfaces.ITokenUtil;

/**
 *
 * @author Alex
 */


/**
 *
 * Intercepta todas las peticiones protegidas y reservadas a la administración
 * para comprobar la validez del token
 */
@Component
public class AdminAuthorizationInterceptor extends HandlerInterceptorAdapter {

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
        System.out.println(req.getPart("vehicle").getSize());
        //Recupero el token del header
        String adminToken = req.getHeader("adminAuthorization").substring(7);
        //Compruebo su validez
        this.tokenUtil.validateToken(adminToken);

        return super.preHandle(req, res, handler); 

    }

}
