package com.bikkadit.elcetronicstore.controllers;

import com.bikkadit.elcetronicstore.config.AppConstants;
import com.bikkadit.elcetronicstore.dto.CategoryDto;
import com.bikkadit.elcetronicstore.payloads.ApiResponse;
import com.bikkadit.elcetronicstore.payloads.ImageResponse;
import com.bikkadit.elcetronicstore.payloads.PageResponse;
import com.bikkadit.elcetronicstore.service.CategoryServiceI;
import com.bikkadit.elcetronicstore.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryServiceI categoryService;

    @Autowired
    private FileService fileService;

    @Value("${category.cover.image.path}")
    private String imageUploadPath;


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
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {

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
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryId) {

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
    public ResponseEntity<PageResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

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
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId) {

        log.info("Entering the CategoryController to Delete Category with ID: {} ", categoryId);

        this.categoryService.delete(categoryId);

        ApiResponse message = ApiResponse
                .builder()
                .message(AppConstants.CATEGORY_DELETED + " : " + categoryId)
                .success(true)
                .status(HttpStatus.OK)
                .build();

        log.info("Returning from CategoryController after Deleting Category with ID: {} ", categoryId);

        return ResponseEntity.ok(message);

    }

//   post image upload

    /**
     * @param coverImage
     * @param categoryId
     * @return
     * @throws IOException
     * @author Karun
     * @apiNote This api is for Uploading the Image for User
     */

    @PatchMapping("/image/upload/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestPart("categoryImage") MultipartFile coverImage,
                                                             @PathVariable String categoryId) throws IOException {

        log.info("Entering the CategoryController to Upload Image in the Category with Category ID: {} ", categoryId);

        String image = this.fileService.uploadFile(coverImage, imageUploadPath);

        CategoryDto category = this.categoryService.getCategory(categoryId);

        category.setCoverImage(image);

        this.categoryService.update(category, categoryId);

        ImageResponse imageResponse = ImageResponse
                .builder()
                .imageName(image)
                .message("Image is uploaded")
                .success(true)
                .status(HttpStatus.CREATED)
                .build();

        log.info("Returning from CategoryController after Uploading Image in the Category with Category ID: {} ", categoryId);

        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);

    }

    // method to serve the files

    /**
     * @param categoryId
     * @param response
     * @throws IOException
     * @author Karun
     * @apiNote This api is for Downloading the Image of User
     */

    @GetMapping(value = "/image/{categoryId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String categoryId, HttpServletResponse response)
            throws IOException {

        log.info("Entering the CategoryController to Serve the Image on the Server : {}", categoryId);

        CategoryDto category = this.categoryService.getCategory(categoryId);
        log.info("Category image name : {} ", category.getCoverImage());

        InputStream resource = this.fileService.getResource(imageUploadPath, category.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

        log.info("Returning from CategoryController after Serving the Image on the Server : {}", categoryId);

    }
}
