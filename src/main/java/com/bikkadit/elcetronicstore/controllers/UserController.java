package com.bikkadit.elcetronicstore.controllers;

import com.bikkadit.elcetronicstore.config.AppConstants;
import com.bikkadit.elcetronicstore.dto.UserDto;
import com.bikkadit.elcetronicstore.payloads.ApiResponse;
import com.bikkadit.elcetronicstore.payloads.ImageResponse;
import com.bikkadit.elcetronicstore.payloads.PageResponse;
import com.bikkadit.elcetronicstore.service.FileService;
import com.bikkadit.elcetronicstore.service.UserServiceI;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceI userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    //Create

    /**
     * @param userDto
     * @return
     * @author Karun
     * @apiNote This is the api for saving the User
     */
    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        log.info("Entering the UserController to Create User : {} ");

        UserDto user = this.userService.createUser(userDto);

        log.info("Returning from UserController after Creating User : {} ");

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //Update

    /**
     * @param userDto
     * @param userId
     * @return
     * @author Karun
     * @apiNote This is the api for updating the User
     */
    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {

        log.info("Entering the UserController to Update User with User ID : {} ", userId);

        UserDto updatedUser = this.userService.updateUser(userDto, userId);

        log.info("Returning from UserController after Updating User with User ID : {} ", userId);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //@Delete

    /**
     * @param userId
     * @return
     * @author Karun
     * @apiNote This is the api for deleting the User
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {

        log.info("Entering the UserController to Delete User with User ID : {} ", userId);

        this.userService.deleteUser(userId);
        ApiResponse message = ApiResponse.builder().message(AppConstants.USER_DELETED + " : " + userId).success(true).status(HttpStatus.OK).build();

        log.info("Returning from UserController after Deleting User with User ID : {} ", userId);

        return ResponseEntity.ok(message);
    }

    //Get All User

    /**
     * @return
     * @author Karun
     * @apiNote This api is for Getting all the User
     */
    @GetMapping("/user")
    public ResponseEntity<PageResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        log.info("Entering the UserController to Get All User : {} ");

        return new ResponseEntity<>(this.userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);

    }

    //Get Single User

    /**
     * @param userId
     * @return
     * @author Karun
     * @apiNote This api is for Getting the Single User
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {

        log.info("Entering the UserController to Get Single User : {} ", userId);

        return new ResponseEntity<>(this.userService.getUserById(userId), HttpStatus.OK);
    }

    //Get By Email

    /**
     * @param email
     * @return
     * @author Karun
     * @apiNote This api is for Getting the User by Email
     */
    @GetMapping("/user/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {

        log.info("Entering the UserController to Get Single User by Email : {} ", email);

        return new ResponseEntity<>(this.userService.getUserByEmail(email), HttpStatus.OK);
    }

    //Search User

    /**
     * @param keyword
     * @return
     * @author Karun
     * @apiNote This api is for Searching the User by Keyword
     */
    @GetMapping("/user/keyword/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {

        log.info("Entering the UserController to search User : {} ", keyword);

        return new ResponseEntity<>(this.userService.searchUser(keyword), HttpStatus.OK);
    }

    // post image upload

    /**
     * @param image
     * @param userId
     * @return
     * @throws IOException
     * @author Karun
     * @apiNote This api is for Uploading the Image for User
     */

    @PostMapping("/user/image/upload/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestPart("userImage") MultipartFile image,
                                                         @PathVariable String userId) throws IOException {

        log.info("Entering the UserController to Upload Image in the User with User ID: {} ", userId);

        UserDto user = this.userService.getUserById(userId);
        String imageName = this.fileService.uploadFile(image, imageUploadPath);

        user.setImageName(imageName);
        UserDto userDto = this.userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse
                .builder()
                .imageName(imageName)
                .success(true)
                .status(HttpStatus.CREATED)
                .build();

        log.info("Returning from UserController after Uploading Image in the User with User ID: {} ", userId);

        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);

    }

    // method to serve the files

    /**
     * @author Karun
     * @param userId
     * @param response
     * @throws IOException
     * @apiNote This api is for Downloading the Image of User
     */

    @GetMapping(value = "/user/image/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String userId, HttpServletResponse response)
            throws IOException {

        log.info("Entering the UserController to Serve the Image on the Server : {}");

        UserDto user = this.userService.getUserById(userId);
        log.info("User image name : {} ", user.getImageName());

        InputStream resource = this.fileService.getResource(imageUploadPath, user.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

        log.info("Returning from UserController after Serving the Image on the Server : {}");

    }


}