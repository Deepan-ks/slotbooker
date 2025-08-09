package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.user.UserRegisterRequest;
import com.deepan.slotbooker.dto.user.UserResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.UserMapper;
import com.deepan.slotbooker.model.Role;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testUserRegisterService_Success(){
        //Arrange
        Long id = 1L;

        UserRegisterRequest request = new UserRegisterRequest();
        request.setName("TestUser");
        request.setEmail("test@email.com");

        User user = new User();
        user.setUserId(id);
        user.setUserName("TestUser");
        user.setEmail("test@email.com");

        UserResponse userResponse = new UserResponse();
        userResponse.setName("TestUser");
        userResponse.setId(id);
        userResponse.setEmail("test@email.com");

        when(userRepository.save(user)).thenReturn(user);

        try(MockedStatic<UserMapper> mockedStatic = Mockito.mockStatic(UserMapper.class)){
            mockedStatic.when(() -> UserMapper.createUserEntity(request)).thenReturn(user);
            mockedStatic.when(() -> UserMapper.buildUserResponse(user)).thenReturn(userResponse);

            // Act
            UserResponse response = userService.userRegisterService(request);

            // Assert
            assertNotNull(response);
            assertEquals(user.getUserName(), response.getName());
            verify(userRepository, times(1)).save(user);
        }
    }

    @Test
    void testFindUserById_Success(){
        //Arrange
        Long id = 1L;

        User user = new User();
        user.setUserId(id);
        user.setUserName("TestUser");
        user.setEmail("test@email.com");

        UserResponse userResponse = new UserResponse();
        userResponse.setName("TestUser");
        userResponse.setId(id);
        userResponse.setEmail("test@email.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        try(MockedStatic<UserMapper> mockedStatic = Mockito.mockStatic(UserMapper.class)){
            mockedStatic.when(() -> UserMapper.buildUserResponse(user)).thenReturn(userResponse);

            //Act
            UserResponse response = userService.findUserById(id);

            //Assert
            assertNotNull(response);
            assertEquals(user.getUserId(), response.getId());
            mockedStatic.verify(() -> UserMapper.buildUserResponse(user), times(1));

        }
    }

    @Test
    void testFindUserById_NotFound(){
        //Arrange
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(id));
    }

    @Test
    void testFindAllUsers_Success() {
        // Arrange
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUserName("John");
        user1.setEmail("john@example.com");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setUserName("Smith");
        user2.setEmail("smith@example.com");

        List<User> users = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        // Mock the static mapper
        try (MockedStatic<UserMapper> mockedMapper = mockStatic(UserMapper.class)) {
            mockedMapper.when(() -> UserMapper.buildUserResponse(user1))
                    .thenReturn(new UserResponse(1L, "John","john@example.com", Role.PLAYER.name(), "9090808010"));
            mockedMapper.when(() -> UserMapper.buildUserResponse(user2))
                    .thenReturn(new UserResponse(2L, "Smith", "jane@example.com", Role.OWNER.name(), "9630258741"));

            // Act
            List<UserResponse> result = userService.findAllUsers();

            // Assert
            assertEquals(2, result.size());
            assertEquals("John", result.get(0).getName());
            assertEquals("Smith", result.get(1).getName());

            verify(userRepository).findAll();
            mockedMapper.verify(() -> UserMapper.buildUserResponse(user1));
            mockedMapper.verify(() -> UserMapper.buildUserResponse(user2));
        }
    }

    @Test
    void testFindAllUsers_EmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserResponse> result = userService.findAllUsers();

        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
    }

}
