/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.controllers;

import com.optima.javaServer.model.Category;
import com.optima.javaServer.service.interfaces.ICategoryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alex
 */
@RestController
@CrossOrigin( origins = "http://localhost:4200")
@RequestMapping(
        )
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    
    //GET http://localhost:8080/rentacar/category
    
    @GetMapping("/category")
    public List<Category> listCategories() {

        return this.categoryService.listCategories();

    }

    //POST http://localhost:8080/rentacar/admin/category
    @PostMapping("/admin/category")
    public String createCategory(@RequestBody Category category) {

        this.categoryService.createCategory(category);

        return "La categoria ha sido creada correctamente.";

    }

    //PUT http://localhost:8080/rentacar/admin/category
    @PutMapping("/admin/category")
    public ResponseEntity updateCategory(@RequestBody Category category) {

         //Respuesta
        Map entity = new HashMap();
        
        category = this.categoryService.updateCategory(category);

        entity.put("category",category);
        entity.put("successMessage", 
                "La categoria ha sido modificada correctamente.");
        
        return ResponseEntity.ok(entity);
        
    }
    
}
