package com.bikkadit.elcetronicstore.service;

import com.bikkadit.elcetronicstore.dto.UserDto;
import com.bikkadit.elcetronicstore.payloads.PageResponse;

import java.util.List;

public interface UserServiceI {

    //    create
    UserDto createUser(UserDto userDto);

    //    update
    UserDto updateUser(UserDto userDto, String userId);

    //    delete
    void deleteUser(String userId);

    //    get All user
    PageResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //    get single user by id
    UserDto getUserById(String userId);

    //    get single user by email
    UserDto getUserByEmail(String email);

    //    search user
    List<UserDto> searchUser(String keyword);

}
