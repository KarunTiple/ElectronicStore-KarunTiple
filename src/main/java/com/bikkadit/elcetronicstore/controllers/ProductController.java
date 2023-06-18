package com.bikkadit.elcetronicstore.controllers;

import com.bikkadit.elcetronicstore.config.AppConstants;
import com.bikkadit.elcetronicstore.dto.ProductDto;
import com.bikkadit.elcetronicstore.payloads.ApiResponse;
import com.bikkadit.elcetronicstore.payloads.ImageResponse;
import com.bikkadit.elcetronicstore.payloads.PageResponse;
import com.bikkadit.elcetronicstore.service.FileService;
import com.bikkadit.elcetronicstore.service.ProductService;
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
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.product.image.path}")
    private String imageUploadPath;


//	create

    /**
     * @param productDto
     * @return
     * @author Karun
     * @apiNote This api is for Creating the Product
     */

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {

        log.info("Entering the ProductController to Create Product : {} ", productDto);

        ProductDto createProduct = this.productService.create(productDto);

        log.info("Returning from ProductController after Creating Product : {} ", productDto);

        return new ResponseEntity<ProductDto>(createProduct, HttpStatus.CREATED);
    }

//	update

    /**
     * @param productDto
     * @param productId
     * @return
     * @author Karun
     * @apiNote This api is for Updating the Product
     */

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {

        log.info("Entering the ProductController to Update Product with Id: {} ", productId);

        ProductDto updatedProduct = this.productService.update(productDto, productId);

        log.info("Returning from ProductController after Updating Product with Id : {} ", productId);

        return new ResponseEntity<ProductDto>(updatedProduct, HttpStatus.OK);
    }

//	get

    /**
     * @param productId
     * @return
     * @author Karun
     * @apiNote This api is for Getting the Product
     */

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {

        log.info("Entering the ProductController to Get Product with ID: {} ", productId);

        ProductDto productDto = this.productService.getProduct(productId);

        log.info("Returning from ProductController after Getting Product with ID: {} ", productId);

        return new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);

    }

//  get All

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Karun
     * @apiNote This api is for Getting the Product
     */

    @GetMapping("/")
    public ResponseEntity<PageResponse<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        log.info("Entering the ProductController to Get All Product : {} ", pageNumber);

        PageResponse<ProductDto> products = this.productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);

        log.info("Returning from ProductController after Getting All Product : {} ", pageNumber);

        return ResponseEntity.ok(products);
    }

    //  get All Product Live

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Karun
     * @apiNote This api is for Getting the Product which are Live
     */

    @GetMapping("/live")
    public ResponseEntity<PageResponse<ProductDto>> getAllProductLive(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        log.info("Entering the ProductController to Get All Product : {} ", pageNumber);

        PageResponse<ProductDto> products = this.productService.getAllProductLive(pageNumber, pageSize, sortBy, sortDir);

        log.info("Returning from ProductController after Getting All Product : {} ", pageNumber);

        return ResponseEntity.ok(products);
    }

    /**
     * @param keyword
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Karun
     * @apiNote This api is for Searching the Product
     */

    @GetMapping("/search/{keyword}")
    public ResponseEntity<PageResponse<ProductDto>> searchProducts(
            @PathVariable String keyword,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        log.info("Entering the ProductController to search Product by Title : {}", keyword);

        PageResponse<ProductDto> products = this.productService.searchByTitle(keyword, pageNumber, pageSize, sortBy, sortDir);

        log.info("Returning from ProductController after searching Product by Title : {}", keyword);

        return ResponseEntity.ok(products);
    }


    @GetMapping("/search/brand/{keyword}")
    public ResponseEntity<PageResponse<ProductDto>> searchProductsByBrand(
            @PathVariable String keyword,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        log.info("Entering the ProductController to search Product by Brand : {}", keyword);

        PageResponse<ProductDto> products = this.productService.searchByBrand(keyword, pageNumber, pageSize, sortBy, sortDir);

        log.info("Returning from ProductController after searching Product by Brand : {}", keyword);

        return ResponseEntity.ok(products);
    }

//	delete

    /**
     * @param productId
     * @return
     * @author Karun
     * @apiNote This api is for Deleting the Product
     */

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId) {

        log.info("Entering the ProductController to Delete Product with ID: {} ", productId);

        this.productService.delete(productId);

        ApiResponse message = ApiResponse
                .builder()
                .message(AppConstants.PRODUCT_DELETED + " : " + productId)
                .success(true)
                .status(HttpStatus.OK)
                .build();

        log.info("Returning from ProductController after Deleting Product with ID: {} ", productId);

        return ResponseEntity.ok(message);

    }

    // post image upload

    /**
     * @param productImage
     * @param productId
     * @return
     * @throws IOException
     * @author Karun
     * @apiNote This api is for Uploading the Image for User
     */

    @PatchMapping("/image/upload/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@RequestPart("productImage") MultipartFile productImage,
                                                            @PathVariable String productId) throws IOException {

        log.info("Entering the ProductController to Upload Image in the Product with Product ID: {} ", productId);

        String image = this.fileService.uploadFile(productImage, imageUploadPath);

        ProductDto product = this.productService.getProduct(productId);

        product.setProductImage(image);

        this.productService.update(product, productId);

        ImageResponse imageResponse = ImageResponse
                .builder()
                .imageName(image)
                .message("Image is uploaded")
                .success(true)
                .status(HttpStatus.CREATED)
                .build();

        log.info("Returning from ProductController after Uploading Image in the Product with Product ID: {} ", productId);

        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);

    }

    // method to serve the files

    /**
     * @param productId
     * @param response
     * @throws IOException
     * @author Karun
     * @apiNote This api is for Downloading the Image of User
     */

    @GetMapping(value = "/image/{productId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String productId, HttpServletResponse response)
            throws IOException {

        log.info("Entering the ProductController to Serve the Image on the Server : {}", productId);

        ProductDto product = this.productService.getProduct(productId);
        log.info("Product image name : {} ", product.getProductImage());

        InputStream resource = this.fileService.getResource(imageUploadPath, product.getProductImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

        log.info("Returning from ProductController after Serving the Image on the Server : {}", productId);

    }
}

