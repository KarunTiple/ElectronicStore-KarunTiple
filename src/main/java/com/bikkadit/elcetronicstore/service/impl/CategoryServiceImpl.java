package com.bikkadit.elcetronicstore.service.impl;

import com.bikkadit.elcetronicstore.config.AppConstants;
import com.bikkadit.elcetronicstore.dto.CategoryDto;
import com.bikkadit.elcetronicstore.entities.Category;
import com.bikkadit.elcetronicstore.exceptions.ResourceNotFoundException;
import com.bikkadit.elcetronicstore.payloads.PageResponse;
import com.bikkadit.elcetronicstore.repositories.CategoryRepository;
import com.bikkadit.elcetronicstore.service.CategoryServiceI;
import com.bikkadit.elcetronicstore.utility.PagingHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryServiceI {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        log.info("Entering the CategoryService to Create the Category : {}", categoryDto);

        Category category = this.modelMapper.map(categoryDto, Category.class);

        category.setCoverImage("Default.png");
        Category savedCategory = this.categoryRepository.save(category);

        log.info("Returning from CategoryService after Creating the Category : {}", categoryDto);

        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, Integer categoryId) {

        log.info("Entering the CategoryService to Update the Category : {} ", categoryId);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + " : " + categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepository.save(category);

        log.info("Returning from CategoryService after Updating the Category : {}", categoryId);

        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {


        log.info("Entering the CategoryService to Get Category : {}", categoryId);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + " : " + categoryId));

        log.info("Returning from CategoryService after Getting Category : {}", categoryId);

        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public PageResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Entering the CategoryService to Get All Category : {}", pageNumber, pageSize, sortBy, sortDir);

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).ascending()) : (Sort.by(sortBy).descending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> page = this.categoryRepository.findAll(pageable);

        PageResponse<CategoryDto> pageResponse = PagingHelper.getPageResponse(page, CategoryDto.class);

        log.info("Returning from CategoryService after Getting All Category : {}", pageNumber, pageSize, sortBy, sortDir);

        return pageResponse;
    }

    @Override
    public void delete(Integer categoryId) {

        log.info("Entering the CategoryService to Delete the Category : {}", categoryId);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + " : " + categoryId));

        log.info("Returning from CategoryService after Deleting the Category : {}", categoryId);

        this.categoryRepository.delete(category);

    }
}
