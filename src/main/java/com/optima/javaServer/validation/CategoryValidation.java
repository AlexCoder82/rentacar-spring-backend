/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optima.javaServer.validation;

import com.optima.javaServer.exceptions.DataValidationException;
import com.optima.javaServer.model.Category;
import com.optima.javaServer.validation.interfaces.ICategoryValidation;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alex
 */
@Component
public class CategoryValidation implements ICategoryValidation {

    @Override
    public void validate(Category category) throws DataValidationException {

        if (category.getDescription().length() > 25) {
            throw new DataValidationException("La descripción no puede tener"
                    + " más de 25 caracteres");
        }

    }

    @Override
    public Category format(Category category) {

        StringBuilder description = new StringBuilder(category.getDescription());

        //Borra los posibles espacios al inicio
        while (description.charAt(0) == ' ') {
            description.deleteCharAt(0);
        }

        //Borra los posibles espacios de más entre las palabras y al final     
        for (int i = 1; i < description.length(); i++) {
            if (description.charAt(i - 1) == ' ' && description.charAt(i) == ' ') {
                description.deleteCharAt(i - 1);
                i--;
            }
            if (description.charAt(description.length() - 1) == ' ') {
                description.deleteCharAt(description.length() - 1);
            }
        }

        
        
        //Cambia las primeras letras de cada palablas en mayusculas y las demas
        //en minusculas
        String words[] = description.toString().split("\\ ");
        description = new StringBuilder("");
        for (int i = 0; i < words.length; i++) {
            description.append(Character.toUpperCase(words[i].charAt(0))
                    + words[i].substring(1).toLowerCase());
            //Si no es la ultima palabra añado espacio
            if (i < words.length - 1) {
                description.append(" ");
            }
        }

        category.setDescription(description.toString());

        return category;
        
    }

}
