package com.bikkadit.elcetronicstore.service;

import com.bikkadit.elcetronicstore.dto.CategoryDto;
import com.bikkadit.elcetronicstore.payloads.PageResponse;

public interface CategoryServiceI {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    //get category
    CategoryDto getCategory(String categoryId);

    //get all category
    PageResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //delete
    void delete(String categoryId);

}
