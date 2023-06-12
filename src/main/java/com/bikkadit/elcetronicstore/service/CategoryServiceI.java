package com.bikkadit.elcetronicstore.service;

import com.bikkadit.elcetronicstore.dto.CategoryDto;

import java.util.List;

public interface CategoryServiceI {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto,Integer categoryId);

    //get category
    CategoryDto getCategory(Integer categoryId);

    //get all category
    List<CategoryDto> getAllCategory();

    //delete
    void delete(Integer categoryId);

}
