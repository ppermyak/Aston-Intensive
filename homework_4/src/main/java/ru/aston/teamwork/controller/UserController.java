package ru.aston.teamwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.teamwork.dto.UserRequestDto;
import ru.aston.teamwork.dto.UserResponseDto;
import ru.aston.teamwork.service.UserService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "User API", description = "Управление пользователями")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Создать пользователя")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto userDto) {
        UserResponseDto response = userService.createUser(userDto);
        response.add(linkTo(methodOn(UserController.class).getUserById(response.getId())).withSelfRel());
        return response;
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        UserResponseDto userDto = userService.getUserById(id);
        userDto.add(
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).updateUser(id, new UserRequestDto())).withRel("update"),
                linkTo(methodOn(UserController.class).deleteUser(id)).withRel("delete"),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users")
        );
        return userDto;
    }

    @Operation(summary = "Обновить пользователя")
    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id,
                                      @Valid @RequestBody UserRequestDto userDto) {
        UserResponseDto response = userService.updateUser(id, userDto);
        response.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        return response;
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получить всех пользователей")
    @GetMapping
    public CollectionModel<UserResponseDto> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        users.forEach(user ->
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel())
        );

        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }
}