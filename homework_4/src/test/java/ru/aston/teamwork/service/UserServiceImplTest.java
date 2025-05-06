package ru.aston.teamwork.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.aston.teamwork.AbstractIntegrationTest;
import ru.aston.teamwork.dto.UserRequestDto;
import ru.aston.teamwork.dto.UserResponseDto;
import ru.aston.teamwork.exception.EmailAlreadyExistsException;
import ru.aston.teamwork.exception.UserNotFoundException;
import ru.aston.teamwork.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class UserServiceImplTest extends AbstractIntegrationTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    private UserRequestDto testUserDto;


    @BeforeEach
    void setUp() {
        testUserDto = UserRequestDto.builder()
                .name("TestUser")
                .email("test@example.com")
                .age(25)
                .build();
    }

    @Test
    void databaseShouldBeEmptyBeforeTest() {
        assertEquals(0, userRepository.count());
    }

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

        assertTrue(users.size() >= 2);
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals(testUserDto.getEmail())));
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals(anotherUser.getEmail())));
    }

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
    void deleteUserShouldRemoveUserFromDatabase() {
        UserResponseDto createdUser = userService.createUser(testUserDto);
        assertDoesNotThrow(() -> userService.deleteUser(createdUser.getId()));
        assertFalse(userRepository.existsById(createdUser.getId()));
    }

    @Test
    void deleteUser_ShouldThrowWhenUserNotFound() {
        assertThrows(UserNotFoundException.class,
                () -> userService.deleteUser(999L));
    }

    @Test
    void createUserShouldThrowWhenNameIsBlank() {
        UserRequestDto invalidUser = UserRequestDto.builder()
                .name(" ")
                .email("valid@email.com")
                .age(30)
                .build();

        assertThrows(MethodArgumentNotValidException.class,
                () -> userService.createUser(invalidUser));
    }

    @Test
    void createUserShouldThrowWhenEmailIsInvalid() {
        UserRequestDto invalidUser = UserRequestDto.builder()
                .name("Valid Name")
                .email("invalid-email")
                .age(30)
                .build();

        assertThrows(MethodArgumentNotValidException.class,
                () -> userService.createUser(invalidUser));
    }

    @Test
    void createUserShouldThrowWhenAgeIsNegative() {
        UserRequestDto invalidUser = UserRequestDto.builder()
                .name("Valid Name")
                .email("valid@email.com")
                .age(-1)
                .build();

        assertThrows(MethodArgumentNotValidException.class,
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

        assertThrows(DataIntegrityViolationException.class,
                () -> userService.updateUser(createdSecondUser.getId(), updateDto));
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
