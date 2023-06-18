package com.bikkadit.elcetronicstore.service.impl;

import com.bikkadit.elcetronicstore.config.AppConstants;
import com.bikkadit.elcetronicstore.dto.ProductDto;
import com.bikkadit.elcetronicstore.entities.Products;
import com.bikkadit.elcetronicstore.exceptions.ResourceNotFoundException;
import com.bikkadit.elcetronicstore.payloads.PageResponse;
import com.bikkadit.elcetronicstore.repositories.ProductRepository;
import com.bikkadit.elcetronicstore.service.ProductService;
import com.bikkadit.elcetronicstore.utility.PagingHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${product.product.image.path}")
    private String imagePath;

    @Override
    public ProductDto create(ProductDto productDto) {

        log.info("Entering the ProductService to Create the Product : {}", productDto);

        // generate unique id in String format

        String productId = UUID.randomUUID().toString();

        productDto.setProductId(productId);
        Products product = this.modelMapper.map(productDto, Products.class);

        product.setProductImage("Default.png");

        Products savedProducts = this.productRepository.save(product);

        log.info("Returning from ProductService after Creating the Product : {}", productDto);

        return this.modelMapper.map(savedProducts, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {

        log.info("Entering the ProductService to Update the Product : {} ", productDto);

        Products products = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND + " : " + productId));

        products.setTitle(productDto.getTitle());
        products.setDescription(productDto.getDescription());
        products.setBrand(productDto.getBrand());
        products.setPrice(productDto.getPrice());
        products.setDiscountedPrice(productDto.getDiscountedPrice());
        products.setQuantity(productDto.getQuantity());
        products.setProductImage(productDto.getProductImage());
        products.setLive(productDto.isLive());
        products.setStock(productDto.isStock());
        Products updatedProducts = this.productRepository.save(products);

        log.info("Returning from ProductService after Updating the Product : {}", productDto);

        return this.modelMapper.map(updatedProducts, ProductDto.class);
    }

    @Override
    public void delete(String productId) {

        log.info("Entering the ProductService to Deleting the Product : {}", productId);

        Products product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND + " : " + productId));

        //delete product image
        //full path
        String fullPath = imagePath + product.getProductImage();

        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);

        } catch (NoSuchElementException ex) {
            log.error("Category image not found with folder : {} ", ex.getMessage());

        } catch (IOException ex) {
            log.error("Unable to found Category Image : {} ", ex.getMessage());
        }

        log.info("Returning from ProductService after Deleting the Product : {}", productId);

        this.productRepository.delete(product);
    }

    @Override
    public ProductDto getProduct(String productId) {

        log.info("Entering the ProductService to Get Product : {}", productId);

        Products products = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND + " : " + productId));

        log.info("Returning from ProductService after Getting Product : {}", productId);

        return this.modelMapper.map(products, ProductDto.class);
    }

    @Override
    public PageResponse<ProductDto> getAllProduct
            (Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Entering the ProductService to Get All Product : {}", pageNumber);

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Products> page = this.productRepository.findAll(pageable);

        log.info("Returning from ProductService after Getting All Product : {}", pageNumber);

        return PagingHelper.getPageResponse(page, ProductDto.class);
    }

    @Override
    public PageResponse<ProductDto> getAllProductLive
            (Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Entering the ProductService to Get All Product Live : {}", pageNumber);

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Products> page = this.productRepository.findByLiveTrue(pageable);

        log.info("Returning from ProductService after Getting All  Product Live : {}", pageNumber);

        return PagingHelper.getPageResponse(page, ProductDto.class);
    }

    @Override
    public PageResponse<ProductDto> searchByTitle
            (String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Entering the ProductService to search Product by Title : {}", keyword);

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Products> page = this.productRepository.findByTitleContaining(keyword, pageable);

        log.info("Returning from ProductService after searching Product by Title : {}", keyword);

        return PagingHelper.getPageResponse(page, ProductDto.class);
    }

    @Override
    public PageResponse<ProductDto> searchByBrand(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Entering the ProductService to search Product by Brand : {}", keyword);

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Products> page = this.productRepository.findByBrandContaining(keyword, pageable);

        log.info("Returning from ProductService after searching Product by Brand : {}", keyword);

        return PagingHelper.getPageResponse(page, ProductDto.class);
    }
}
