package ru.aston.userservice.service;

import jakarta.validation.Valid;
import ru.aston.userservice.dto.UserRequestDto;
import ru.aston.userservice.dto.UserResponseDto;


import java.util.List;

public interface UserService {
    UserResponseDto createUser(@Valid UserRequestDto userDto);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    UserResponseDto updateUser(Long id, @Valid UserRequestDto userDto);

    void deleteUser(Long id);
}
