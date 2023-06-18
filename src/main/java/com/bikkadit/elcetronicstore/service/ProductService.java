package com.bikkadit.elcetronicstore.service;

import com.bikkadit.elcetronicstore.dto.ProductDto;
import com.bikkadit.elcetronicstore.payloads.PageResponse;

public interface ProductService {

    //create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto, String productId);

    //delete
    void delete(String productId);

    //get Single
    ProductDto getProduct(String productId);

    //get All
    PageResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get All Live
    PageResponse<ProductDto> getAllProductLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //search Products
    PageResponse<ProductDto> searchByTitle(String keyword,Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PageResponse<ProductDto> searchByBrand(String keyword,Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

}
