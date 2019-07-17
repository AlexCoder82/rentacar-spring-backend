/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.validation.interfaces;

import com.optima.javaServer.exceptions.DataValidationException;
import com.optima.javaServer.model.Category;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alex
 */

@Component
public interface ICategoryValidation {
    
    public void validate(Category category)throws DataValidationException;
    
    public Category format(Category category);
    
}
