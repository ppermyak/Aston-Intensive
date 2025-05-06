package ru.aston.teamwork.service;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import ru.aston.teamwork.dto.UserRequestDto;
import ru.aston.teamwork.dto.UserResponseDto;
import ru.aston.teamwork.exception.EmailAlreadyExistsException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceCreateUserTest extends AbstractUserServiceTest{

    @Test
    void createUserShouldPersistUserWithCorrectData() {
        UserResponseDto response = userService.createUser(testUserDto);

        assertNotNull(response.getId());
        assertEquals(testUserDto.getName(), response.getName());
        assertEquals(testUserDto.getEmail(), response.getEmail());
        assertEquals(testUserDto.getAge(), response.getAge());
        assertNotNull(response.getCreatedAt());

        assertTrue(userRepository.existsById(response.getId()));
    }

    @Test
    void createUserShouldThrowWhenEmailExists() {
        userService.createUser(testUserDto);
        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.createUser(testUserDto));
    }

    @Test
    void createUserShouldThrowWhenNameIsBlank() {
        UserRequestDto invalidUser = UserRequestDto.builder()
                .name(" ")
                .email("valid@email.com")
                .age(30)
                .build();

        assertThrows(ConstraintViolationException.class,
                () -> userService.createUser(invalidUser));
    }

    @Test
    void createUserShouldThrowWhenEmailIsInvalid() {
        UserRequestDto invalidUser = UserRequestDto.builder()
                .name("Valid Name")
                .email("invalid-email")
                .age(30)
                .build();

        assertThrows(ConstraintViolationException.class,
                () -> userService.createUser(invalidUser));
    }

    @Test
    void createUserShouldThrowWhenAgeIsNegative() {
        UserRequestDto invalidUser = UserRequestDto.builder()
                .name("Valid Name")
                .email("valid@email.com")
                .age(-1)
                .build();

        assertThrows(ConstraintViolationException.class,
                () -> userService.createUser(invalidUser));
    }

    @Test
    void createUserShouldAcceptMinimumAge() {
        UserRequestDto user = testUserDto.toBuilder()
                .age(0)
                .build();

        assertDoesNotThrow(() -> userService.createUser(user));
    }

    @Test
    void createUserShouldAcceptMaximumAge() {
        UserRequestDto user = testUserDto.toBuilder()
                .age(122)
                .build();

        assertDoesNotThrow(() -> userService.createUser(user));
    }
}
