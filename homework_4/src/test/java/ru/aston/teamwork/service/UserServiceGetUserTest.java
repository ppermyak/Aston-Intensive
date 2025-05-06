package ru.aston.teamwork.service;

import org.junit.jupiter.api.Test;
import ru.aston.teamwork.dto.UserRequestDto;
import ru.aston.teamwork.dto.UserResponseDto;
import ru.aston.teamwork.exception.UserNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceGetUserTest extends AbstractUserServiceTest {

    @Test
    void getUserByIdShouldReturnCorrectUser() {
        UserResponseDto createUser = userService.createUser(testUserDto);
        UserResponseDto foundUser = userService.getUserById(createUser.getId());

        assertEquals(createUser.getId(), foundUser.getId());
        assertEquals(createUser.getEmail(), foundUser.getEmail());
    }

    @Test
    void getUserByIdShouldThrowWhenUserNotFound() {
        assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(999L));
    }

    @Test
    void getAllUsersShouldReturnAllCreatedUsers() {
        userService.createUser(testUserDto);
        UserRequestDto anotherUser = UserRequestDto.builder()
                .name("ATUser")
                .email("another@example.com")
                .age(30)
                .build();
        userService.createUser(anotherUser);

        List<UserResponseDto> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals(testUserDto.getEmail())));
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals(anotherUser.getEmail())));
    }

    @Test
    void getAllUsersShouldReturnEmptyListForEmptyDatabase() {
        userRepository.deleteAll();
        List<UserResponseDto> users = userService.getAllUsers();
        assertTrue(users.isEmpty());
    }

    @Test
    void getUserByIdShouldContainAllFields() {
        UserResponseDto createdUser = userService.createUser(testUserDto);
        UserResponseDto foundUser = userService.getUserById(createdUser.getId());

        assertEquals(createdUser.getId(), foundUser.getId());
        assertEquals(createdUser.getName(), foundUser.getName());
        assertEquals(createdUser.getEmail(), foundUser.getEmail());
        assertEquals(createdUser.getAge(), foundUser.getAge());
        assertNotNull(foundUser.getCreatedAt());
    }
}
