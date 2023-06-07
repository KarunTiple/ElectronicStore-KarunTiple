package com.bikkadit.elcetronicstore.controllers;

import com.bikkadit.elcetronicstore.config.AppConstants;
import com.bikkadit.elcetronicstore.dto.UserDto;
import com.bikkadit.elcetronicstore.payloads.ApiResponse;
import com.bikkadit.elcetronicstore.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceI userService;

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

        UserDto updatedUser = this.userService.updateUSer(userDto, userId);

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
        ApiResponse message = ApiResponse.builder().message(AppConstants.USERDELETED + " : " + userId).success(true).status(HttpStatus.OK).build();

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
    public ResponseEntity<List<UserDto>> getAllUser() {

        log.info("Entering the UserController to Get All User : {} ");

        return new ResponseEntity<>(this.userService.getAllUser(), HttpStatus.OK);

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
}