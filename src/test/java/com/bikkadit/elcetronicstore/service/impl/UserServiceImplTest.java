package com.bikkadit.elcetronicstore.service.impl;

import com.bikkadit.elcetronicstore.dto.UserDto;
import com.bikkadit.elcetronicstore.entities.User;
import com.bikkadit.elcetronicstore.repositories.UserRepository;
import com.bikkadit.elcetronicstore.service.UserServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class UserServiceImplTest {


    User user1;
    User user2;
    List<User> users;
    UserDto userDto;
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private ModelMapper modelMapper;
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userDto = UserDto.builder()
                .userId("user1Id")
                .username("Karun Tiple")
                .email("karun@gmail.com")
                .gender("Male")
                .password("xyzA")
                .imageName("Default.png")
                .build();

        user1 = User.builder()
                .userId("user2Id")
                .username("Tanuj Kamble")
                .email("tanuj@gmail.com")
                .gender("Male")
                .password("xyzB")
                .imageName("Default.png")
                .build();

        user2 = User.builder()
                .userId("user3Id")
                .username("Mahesh Sharma")
                .email("mahesh@gmail.com")
                .gender("Male")
                .password("xyzC")
                .imageName("Default.png")
                .build();


        List<User> users = Arrays.asList(user1, user2);
    }


    @Test
    void createUser() {
        //Arrange
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user1);
        //Act
        UserDto userDto = userServiceI.createUser(modelMapper.map(user1, UserDto.class));
        //Assert
        //Assertions.assertNotNull(userDto);
        Assertions.assertEquals("Tanuj Kamble", user1.getUsername());
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getAllUser() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void searchUser() {
    }
}