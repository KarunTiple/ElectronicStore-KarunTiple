package com.bikkadit.elcetronicstore.controllers;

import com.bikkadit.elcetronicstore.config.AppConstants;
import com.bikkadit.elcetronicstore.dto.CategoryDto;
import com.bikkadit.elcetronicstore.payloads.ApiResponse;
import com.bikkadit.elcetronicstore.payloads.PageResponse;
import com.bikkadit.elcetronicstore.service.CategoryServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryServiceI categoryService;


//	create

    /**
     * @param categoryDto
     * @return
     * @author Karun
     * @apiNote This api is for Creating the Category
     */

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

        log.info("Entering the CategoryController to Create Category : {} ", categoryDto);

        CategoryDto createCategory = this.categoryService.create(categoryDto);

        log.info("Returning from CategoryController after Creating Category : {} ", categoryDto);

        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }

//	update

    /**
     * @param categoryDto
     * @param categoryId
     * @return
     * @author Karun
     * @apiNote This api is for Updating the Category
     */

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId) {

        log.info("Entering the CategoryController to Update Category with Id: {} ", categoryId);

        CategoryDto updatedCategory = this.categoryService.update(categoryDto, categoryId);

        log.info("Returning from CategoryController after Updating Category with Id : {} ", categoryId);

        return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
    }

//	get

    /**
     * @param categoryId
     * @return
     * @author Karun
     * @apiNote This api is for Getting the Category
     */

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {

        log.info("Entering the CategoryController to Get Category with ID: {} ", categoryId);

        CategoryDto categoryDto = this.categoryService.getCategory(categoryId);

        log.info("Returning from CategoryController after Getting Category with ID: {} ", categoryId);

        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);

    }

//  get All

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Karun
     * @apiNote This api is for Getting the Category
     */

    @GetMapping("/")
    public ResponseEntity<PageResponse<CategoryDto>> getAllCategory(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        log.info("Entering the CategoryController to Get All Category : {} ");

        PageResponse<CategoryDto> categories = this.categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);

        log.info("Returning from CategoryController after Getting All Category : {} ");

        return ResponseEntity.ok(categories);
    }

//	delete

    /**
     * @param categoryId
     * @return
     * @author Karun
     * @apiNote This api is for Deleting the Category
     */

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {

        log.info("Entering the CategoryController to Delete Category with ID: {} ", categoryId);

        this.categoryService.delete(categoryId);

        ApiResponse message = ApiResponse.builder().message(AppConstants.CATEGORY_DELETED + " : " + categoryId).success(true).status(HttpStatus.OK).build();

        log.info("Returning from CategoryController after Deleting Category with ID: {} ", categoryId);

        return ResponseEntity.ok(message);

    }
}
