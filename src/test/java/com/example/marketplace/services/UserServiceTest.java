package com.example.marketplace.services;

import com.example.marketplace.models.User;
import com.example.marketplace.models.enums.Role;
import com.example.marketplace.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser_Success() {
        // Arrange
        User user = new User("test@example.com", "password");
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        // Act
        boolean result = userService.createUser(user);

        // Assert
        assertTrue(result);
        assertTrue(user.isActive());
        assertTrue(user.getRoles().contains(Role.ROLE_USER));
        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUser_UserExists() {
        // Arrange
        User existingUser = new User("existing@example.com", "password");
        when(userRepository.findByEmail(any())).thenReturn(existingUser);

        // Act
        boolean result = userService.createUser(existingUser);

        // Assert
        assertFalse(result);
        verify(userRepository, never()).save(existingUser);
    }

    @Test
    void testList() {
        // Arrange
        List<User> userList = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<User> result = userService.list();

        // Assert
        assertEquals(userList, result);
    }

    @Test
    void testBanUser_BanUser() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setActive(true);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        // Act
        userService.banUser(1L);

        // Assert
        assertFalse(user.isActive());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testBanUser_UnbanUser() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setActive(false);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        // Act
        userService.banUser(1L);

        // Assert
        assertTrue(user.isActive());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testBanUser_UserNotFound() {
        // Arrange
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // Act
        userService.banUser(1L);

        // Assert
        // Ensure no interactions with userRepository.save() for a non-existent user
        verify(userRepository, never()).save(any());
    }

    @Test
    void testChangeUserRoles() {
        // Arrange
        User user = new User();
        Map<String, String> form = new HashMap<>();
        form.put("ROLE_ADMIN", "on");
        form.put("ROLE_USER", "on");

        // Act
        userService.changeUserRoles(user, form);

        // Assert
        assertTrue(user.getRoles().contains(Role.ROLE_ADMIN));
        assertTrue(user.getRoles().contains(Role.ROLE_USER));
        verify(userRepository, times(1)).save(user);
    }
}
