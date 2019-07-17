/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.service.interfaces;

import com.optima.javaServer.model.Category;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alex
 */

@Service
public interface ICategoryService {
       
    public List<Category> listCategories();
    
    public void createCategory(Category category);
    
    public Category updateCategory(Category category);
    
}
