/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.controllers;

import com.optima.javaServer.model.Category;
import com.optima.javaServer.service.interfaces.ICategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alex
 */
@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    
    //GET http://localhost:8080/rentacar/category   
    @GetMapping()
    public List<Category> listCategories() {

        return this.categoryService.listCategories();

    }

    
    
}
