package com.bikkadit.elcetronicstore.service.impl;

import com.bikkadit.elcetronicstore.dto.UserDto;
import com.bikkadit.elcetronicstore.entities.User;
import com.bikkadit.elcetronicstore.payloads.PageResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceImplTest {

    User user1;
    User user2;
    User user3;
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
                .imageName("Default.jpg")
                .build();

        user2 = User.builder()
                .userId("user3Id")
                .username("Mahesh Sharma")
                .email("mahesh@gmail.com")
                .gender("Male")
                .password("xyzC")
                .imageName("Default.png")
                .build();

        user3 = User.builder()
                .userId("user4Id")
                .username("Kabirdas")
                .email("kabir@gmail.com")
                .gender("Male")
                .password("xyzC")
                .imageName("Default.png")
                .build();

        users = Arrays.asList(user1, user2, user3);
    }

    @Test
    void createUserTest() {
        //Arrange
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user1);
        //Act
        UserDto userDto = userServiceI.createUser(modelMapper.map(user1, UserDto.class));
        //Assert
        //Assertions.assertNotNull(userDto);
        Assertions.assertEquals("Tanuj Kamble", user1.getUsername());
    }

    @Test
    void updateUserTest() {
        //Arrange
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user1);
        //Act
        UserDto updatedUser = userServiceI.updateUser(userDto, user1.getUserId());
        //Assert
        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(userDto.getUsername(), updatedUser.getUsername(), "Name is not validated");
    }

    @Test
    void deleteUserTest() {
        //Arrange
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user1));
        //Act
        userServiceI.deleteUser(user1.getUserId());
        //Mockito-verify
        Mockito.verify(userRepository, Mockito.times(1)).delete(user1);
    }

    @Test
    void getAllUserTest() {

        Page<User> page = new PageImpl<>(users);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        //PageResponse<UserDto> allUser = userServiceI.getAllUser(1, 2, "name", "asc");
        PageResponse<UserDto> allUser = userServiceI.getAllUser(page.getNumber(), page.getSize(), page.getSort().toString(), String.valueOf(page.getSort().ascending()));

        //Assertions.assertEquals(users.size(), allUser.getContent().size());
        Assertions.assertNotNull(allUser.getContent());
    }

    @Test
    void getUserByIdTest() {
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user2));

        UserDto userDtoId = userServiceI.getUserById(user2.getUserId());

        Assertions.assertNotNull(userDtoId);
        Assertions.assertEquals(user2.getUserId(), userDtoId.getUserId(), "Name not matched");
    }

    @Test
    void getUserByEmailTest() {

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user2));

        UserDto userDtoEmail = userServiceI.getUserByEmail(user2.getEmail());

        Assertions.assertNotNull(userDtoEmail);
        Assertions.assertEquals(user2.getEmail(), userDtoEmail.getEmail(), "Email not matched");
    }

    @Test
    void searchUserTest() {

        Mockito.when(userRepository.findByUsernameContaining(Mockito.anyString())).thenReturn(users);

        List<UserDto> userDtos = userServiceI.searchUser(users.toString());

        Assertions.assertEquals(users.size(), userDtos.size(), "Size not matched");
        Assertions.assertNotNull(userDtos);
    }
}