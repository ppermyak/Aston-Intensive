package ru.aston.teamwork.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.aston.teamwork.dto.UserRequestDto;
import ru.aston.teamwork.dto.UserResponseDto;
import ru.aston.teamwork.exception.EmailAlreadyExistsException;
import ru.aston.teamwork.exception.UserNotFoundException;
import ru.aston.teamwork.model.User;
import ru.aston.teamwork.repository.UserRepository;
import ru.aston.teamwork.utill.UserMapper;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    @Transactional
    public UserResponseDto createUser(@Valid UserRequestDto userDto) {
        validateEmailUniqueness(userDto.getEmail());

        User user = mapper.toUser(userDto);
        User savedUser = userRepository.save(user);
        return mapper.toUserResponseDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return mapper.toUserResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(mapper::toUserResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, @Valid UserRequestDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!existingUser.getEmail().equals(userDto.getEmail())) {
            validateEmailUniqueness(userDto.getEmail());
        }

        mapper.updateUserFromDto(userDto, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return mapper.toUserResponseDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
    }
}
