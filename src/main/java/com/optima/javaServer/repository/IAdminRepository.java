/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.repository;

import com.optima.javaServer.model.Admin;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alex
 */

/**
 * Peticiones HQL a la tabla "admins"
 */
@Repository
public interface IAdminRepository extends CrudRepository<Admin, Integer> {

    public Optional<Admin> findByAdminNameAndPasswrd(
            String adminName,
            String passwrd);

    public Optional<Admin> findByAdminName(String adminName);
    
    public Optional<Admin> findByPasswrd(String passwrd);
    
}
