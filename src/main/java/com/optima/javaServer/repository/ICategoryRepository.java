/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.repository;

import com.optima.javaServer.model.Category;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alex
 */

@Repository
public interface ICategoryRepository extends CrudRepository<Category, Integer>{

    @Override
    public List<Category> findAll();

    //Inserta si el id de la categoría es nulo, sino hace un update
    @Override
     public Category save(Category category);
    
}
