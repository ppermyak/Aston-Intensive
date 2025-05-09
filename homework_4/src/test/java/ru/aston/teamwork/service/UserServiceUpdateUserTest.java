package ru.aston.teamwork.service;

import org.junit.jupiter.api.Test;
import ru.aston.teamwork.dto.UserRequestDto;
import ru.aston.teamwork.dto.UserResponseDto;
import ru.aston.teamwork.exception.EmailAlreadyExistsException;
import ru.aston.teamwork.exception.UserNotFoundException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceUpdateUserTest extends AbstractUserServiceTest {

    @Test
    void updateUserShouldUpdateAllFields() {
        UserResponseDto createUser = userService.createUser(testUserDto);
        UserRequestDto updateDto = UserRequestDto.builder()
                .name("UName")
                .email("update@example.com")
                .age(18)
                .build();

        UserResponseDto updateUser = userService.updateUser(createUser.getId(), updateDto);

        assertEquals(createUser.getId(), updateUser.getId());
        assertEquals(updateDto.getName(), updateUser.getName());
        assertEquals(updateDto.getEmail(), updateUser.getEmail());
        assertEquals(updateDto.getAge(), updateUser.getAge());
    }

    @Test
    void updateUserShouldThrowWhenUserNotFound() {
        UserRequestDto updateDto = UserRequestDto.builder()
                .name("Updated")
                .email("updated@example.com")
                .age(30)
                .build();

        assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(999L, updateDto));
    }

    @Test
    void updateUserShouldNotChangeCreatedAt() {
        UserResponseDto createdUser = userService.createUser(testUserDto);
        LocalDateTime originalCreatedAt = createdUser.getCreatedAt();

        UserRequestDto updateDto = testUserDto.toBuilder()
                .name("UpdatedName")
                .build();

        UserResponseDto updatedUser = userService.updateUser(createdUser.getId(), updateDto);
        assertEquals(originalCreatedAt, updatedUser.getCreatedAt());
    }

    @Test
    void updateUserShouldThrowWhenEmailExistsForOtherUser() {

        userService.createUser(testUserDto);

        UserRequestDto secondUser = testUserDto.toBuilder()
                .email("second@example.com")
                .build();
        UserResponseDto createdSecondUser = userService.createUser(secondUser);

        UserRequestDto updateDto = secondUser.toBuilder()
                .email(testUserDto.getEmail())
                .build();

        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.updateUser(createdSecondUser.getId(), updateDto));
    }
}
