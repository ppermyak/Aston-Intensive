package ru.aston.teamwork.service;

import jakarta.validation.Valid;
import ru.aston.teamwork.dto.UserRequestDto;
import ru.aston.teamwork.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(@Valid UserRequestDto userDto);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    UserResponseDto updateUser(Long id, @Valid UserRequestDto userDto);

    void deleteUser(Long id);
}
