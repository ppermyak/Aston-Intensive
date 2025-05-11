package ru.aston.userservice.service;

import org.junit.jupiter.api.Test;
import ru.aston.userservice.dto.UserResponseDto;
import ru.aston.userservice.exception.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceDeleteUserTest extends AbstractUserServiceTest {

    @Test
    void deleteUserShouldRemoveUserFromDatabase() {
        UserResponseDto createdUser = userService.createUser(testUserDto);
        assertDoesNotThrow(() -> userService.deleteUser(createdUser.getId()));
        assertFalse(userRepository.existsById(createdUser.getId()));
    }

    @Test
    void deleteUserShouldThrowWhenUserNotFound() {
        assertThrows(UserNotFoundException.class,
                () -> userService.deleteUser(999L));
    }
}
