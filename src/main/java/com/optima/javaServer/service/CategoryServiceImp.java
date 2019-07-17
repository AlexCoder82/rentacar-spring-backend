/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.service;

import com.optima.javaServer.service.interfaces.ICategoryService;
import com.optima.javaServer.exceptions.DataNotFoundException;
import com.optima.javaServer.model.Category;
import com.optima.javaServer.repository.ICategoryRepository;
import com.optima.javaServer.validation.interfaces.ICategoryValidation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alex
 */
/**
 *
 * Lógica de las peticiones relacionadas a las categorias
 */
@Service
public class CategoryServiceImp implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;
    
    @Autowired
    private ICategoryValidation categoryValidation;

    @Override
    public List<Category> listCategories() {

        return this.categoryRepository.findAll();

    }

    @Override
    public void createCategory(Category category) {

        this.categoryValidation.validate(category);
        category = this.categoryValidation.format(category);
        
        this.categoryRepository.save(category);

    }

    @Override
    public Category updateCategory(Category category) {

        this.categoryValidation.validate(category);
        category = this.categoryValidation.format(category);
        
        return this.categoryRepository.save(category);

    }

}
