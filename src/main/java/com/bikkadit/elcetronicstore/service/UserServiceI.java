package com.bikkadit.elcetronicstore.service;

import com.bikkadit.elcetronicstore.dto.UserDto;

import java.util.List;

public interface UserServiceI {

    //    create
    UserDto createUser(UserDto userDto);

    //    update
    UserDto updateUSer(UserDto userDto, String userId);

    //    delete
    void deleteUser(String userId);

    //    get All user
    List<UserDto> getAllUser();

    //    get single user by id
    UserDto getUserById(String userId);

    //    get single user by email
    UserDto getUserByEmail(String email);

    //    search user
    List<UserDto> searchUser(String keyword);

}
