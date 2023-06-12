package com.bikkadit.elcetronicstore.service;

import com.bikkadit.elcetronicstore.dto.CategoryDto;
import com.bikkadit.elcetronicstore.payloads.PageResponse;

import java.util.List;

public interface CategoryServiceI {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto,Integer categoryId);

    //get category
    CategoryDto getCategory(Integer categoryId);

    //get all category
    PageResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //delete
    void delete(Integer categoryId);

}
