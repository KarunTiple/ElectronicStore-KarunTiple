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

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryServiceI {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${category.cover.image.path}")
    private String imagePath;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        log.info("Entering the CategoryService to Create the Category : {}", categoryDto);

        // generate unique id in String format

        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        Category category = this.modelMapper.map(categoryDto, Category.class);

        category.setCoverImage("Default.png");
        Category savedCategory = this.categoryRepository.save(category);

        log.info("Returning from CategoryService after Creating the Category : {}", categoryDto);

        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {

        log.info("Entering the CategoryService to Update the Category : {} ", categoryId);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + " : " + categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = this.categoryRepository.save(category);

        log.info("Returning from CategoryService after Updating the Category : {}", categoryId);

        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(String categoryId) {


        log.info("Entering the CategoryService to Get Category : {}", categoryId);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + " : " + categoryId));

        log.info("Returning from CategoryService after Getting Category : {}", categoryId);

        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public PageResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Entering the CategoryService to Get All Category : {}");

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).ascending()) : (Sort.by(sortBy).descending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> page = this.categoryRepository.findAll(pageable);

        PageResponse<CategoryDto> pageResponse = PagingHelper.getPageResponse(page, CategoryDto.class);

        log.info("Returning from CategoryService after Getting All Category : {}", pageNumber, pageSize, sortBy, sortDir);

        return pageResponse;
    }

    @Override
    public void delete(String categoryId) {

        log.info("Entering the CategoryService to Delete the Category : {}", categoryId);

        Category category = this.categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + " : " + categoryId));

        //delete category cover image
        //full path
        String fullPath = imagePath + category.getCoverImage();

        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);

        } catch (NoSuchElementException ex) {
            log.error("Category image not found with folder : {} ", ex.getMessage());

        } catch (IOException ex) {
            log.error("Unable to found Category Image : {} ", ex.getMessage());
        }

        log.info("Returning from CategoryService after Deleting the Category : {}", categoryId);

        this.categoryRepository.delete(category);

    }
}
