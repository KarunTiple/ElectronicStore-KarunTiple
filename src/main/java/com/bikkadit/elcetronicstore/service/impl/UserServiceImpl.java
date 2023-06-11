package com.bikkadit.elcetronicstore.service.impl;


import com.bikkadit.elcetronicstore.config.AppConstants;
import com.bikkadit.elcetronicstore.dto.UserDto;
import com.bikkadit.elcetronicstore.entities.User;
import com.bikkadit.elcetronicstore.exceptions.ResourceNotFoundException;
import com.bikkadit.elcetronicstore.payloads.PageResponse;
import com.bikkadit.elcetronicstore.repositories.UserRepository;
import com.bikkadit.elcetronicstore.service.FileService;
import com.bikkadit.elcetronicstore.service.UserServiceI;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserServiceI {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDto createUser(UserDto userDto) {

        log.info("Entering the UserService for creating the User : {}");

        // generate unique id in String format

        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        User user = this.modelMapper.map(userDto, User.class);

        user.setImageName("default.png");

        User savedUser = this.userRepository.save(user);

        log.info("Returning from UserService after creating the User : {}");

        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        log.info("Entering the UserService to update the User with User ID : {} ", userId);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + " : " + userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        User updatedUser = this.userRepository.save(user);

        log.info("Returning from UserService after Updating the User with User ID : {} ", userId);

        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {

        log.info("Entering the UserService to Delete the User with User ID : {} ", userId);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + " : " + userId));


        //delete user profile image
        //full path
        String fullPath = imagePath + user.getImageName();

        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);

        }catch (NoSuchElementException ex) {
            log.error("User image not found with folder : {} ", ex.getMessage());

        }catch (IOException ex){
            log.error("Unable to found User Image : {} ", ex.getMessage());
        }

        //delete user
        log.info("Returning from UserService after Deleting the User with User ID : {} ", userId);

        this.userRepository.delete(user);
    }

    @Override
    public PageResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Entering the UserService to Get All User : {}");

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).ascending()) : (Sort.by(sortBy).descending());
        /*Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }*/
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> page = this.userRepository.findAll(pageable);

        PageResponse<UserDto> response = PagingHelper.getPageResponse(page, UserDto.class);

        log.info("Returning from UserService after Getting All User : {}");

        return response;
    }

    @Override
    public UserDto getUserById(String userId) {

        log.info("Entering the UserService to Get the User with User ID : {} ", userId);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + " : " + userId));

        log.info("Returning from UserService after Getting the User with User ID : {} ", userId);

        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {

        log.info("Entering the UserService to Get the User with Email Id : {} ", email);

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstants.EMAIL_NOT_FOUND + " : " + email));

        log.info("Returning from UserService after Getting the User with Email Id : {} ", email);

        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {

        log.info("Entering the UserService to Search the User with Keyword : {} ", keyword);

        List<User> users = this.userRepository.findByNameContaining(keyword);

        List<UserDto> userDto = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        log.info("Returning from UserService after Searching the User with Keyword : {} ", keyword);

        return userDto;
    }
}
