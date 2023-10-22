package com.intuit.sportseventsregistration.services;

import com.intuit.sportseventsregistration.dto.User;
import com.intuit.sportseventsregistration.exceptions.UserException;
import com.intuit.sportseventsregistration.mapper.UserMapper;
import com.intuit.sportseventsregistration.repository.UserRepository;
import com.intuit.sportseventsregistration.responses.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser_Success() {
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setEmail("newuser@example.com");

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("newuser");
        userResponse.setEmail("newuser@example.com");

        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userMapper.toUserResponse(newUser)).thenReturn(userResponse);
        when(userRepository.save(newUser)).thenReturn(newUser);

        UserResponse result = userService.createUser(newUser);

        assertEquals("newuser", result.getUsername());
        assertEquals("newuser@example.com", result.getEmail());
    }

    @Test
    public void testCreateUser_UserExists() {
        User existingUser = new User();
        existingUser.setUsername("existinguser");
        existingUser.setEmail("existinguser@example.com");

        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(existingUser));

        User newUser = new User();
        newUser.setUsername("existinguser"); // Trying to create a user with an existing username.

        assertThrows(UserException.class, () -> userService.createUser(newUser));
    }
}