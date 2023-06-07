package com.bikkadit.elcetronicstore.service.impl;


import com.bikkadit.elcetronicstore.config.AppConstants;
import com.bikkadit.elcetronicstore.dto.UserDto;
import com.bikkadit.elcetronicstore.entities.User;
import com.bikkadit.elcetronicstore.exceptions.ResourceNotFoundException;
import com.bikkadit.elcetronicstore.repositories.UserRepository;
import com.bikkadit.elcetronicstore.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserServiceI {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDto createUser(UserDto userDto) {

        log.info("Entering the UserService for creating the User : {}");

        // generate unique id in String format

        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        User user = this.modelMapper.map(userDto, User.class);

        User savedUser = this.userRepository.save(user);

        log.info("Returning from UserService after creating the User : {}");

        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUSer(UserDto userDto, String userId) {

        log.info("Entering the UserService to update the User with User ID : {} ", userId);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USERNOTFOUND + " : " + userId));

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

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USERNOTFOUND + " : " + userId));

        log.info("Returning from UserService after Deleting the User with User ID : {} ", userId);

        this.userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUser() {

        log.info("Entering the UserService to Get All User : {}");

        List<User> allUsers = this.userRepository.findAll();

        List<UserDto> userDto = allUsers.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        log.info("Returning from UserService after Getting All User : {}");

        return userDto;
    }

    @Override
    public UserDto getUserById(String userId) {

        log.info("Entering the UserService to Get the User with User ID : {} ", userId);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USERNOTFOUND + " : " + userId));

        log.info("Returning from UserService after Getting the User with User ID : {} ", userId);

        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {

        log.info("Entering the UserService to Get the User with Email Id : {} ", email);

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstants.EMAILNOTFOUND + " : " + email));

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
