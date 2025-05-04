package ru.aston.teamwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.aston.teamwork.dto.UserRequestDto;
import ru.aston.teamwork.dto.UserResponseDto;
import ru.aston.teamwork.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void whenCreateUser_thenUserCreatedAndReturnCreatedCodeAndData() throws Exception {
        UserRequestDto userRequestDto = getUserRequestDto();
        UserResponseDto userResponseDto = getUserResponseDto();

        when(userService.createUser(userRequestDto)).thenReturn(userResponseDto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto))
                )
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.name").value(userResponseDto.getName()),
                        jsonPath("$.email").value(userResponseDto.getEmail()),
                        jsonPath("$.age").value(userResponseDto.getAge()),
                        jsonPath("$.createdAt[0]").value(userResponseDto.getCreatedAt().getYear()),
                        jsonPath("$.createdAt[1]").value(userResponseDto.getCreatedAt().getMonthValue()),
                        jsonPath("$.createdAt[2]").value(userResponseDto.getCreatedAt().getDayOfMonth()),
                        jsonPath("$.createdAt[3]").value(userResponseDto.getCreatedAt().getHour()),
                        jsonPath("$.createdAt[4]").value(userResponseDto.getCreatedAt().getMinute()),
                        jsonPath("$.createdAt[5]").value(userResponseDto.getCreatedAt().getSecond()),
                        jsonPath("$.createdAt[6]").value(userResponseDto.getCreatedAt().getNano())
                );

        verify(userService, times(1)).createUser(userRequestDto);
    }

    @Test
    void whenGetUserById_thenReturnOkAndData() throws Exception {
        UserResponseDto userResponseDto = getUserResponseDto();

        when(userService.getUserById(1L)).thenReturn(userResponseDto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(userResponseDto.getId()),
                        jsonPath("$.name").value(userResponseDto.getName()),
                        jsonPath("$.email").value(userResponseDto.getEmail()),
                        jsonPath("$.age").value(userResponseDto.getAge()),
                        jsonPath("$.createdAt[0]").value(userResponseDto.getCreatedAt().getYear()),
                        jsonPath("$.createdAt[1]").value(userResponseDto.getCreatedAt().getMonthValue()),
                        jsonPath("$.createdAt[2]").value(userResponseDto.getCreatedAt().getDayOfMonth()),
                        jsonPath("$.createdAt[3]").value(userResponseDto.getCreatedAt().getHour()),
                        jsonPath("$.createdAt[4]").value(userResponseDto.getCreatedAt().getMinute()),
                        jsonPath("$.createdAt[5]").value(userResponseDto.getCreatedAt().getSecond()),
                        jsonPath("$.createdAt[6]").value(userResponseDto.getCreatedAt().getNano())
                );

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void whenGetAllUsers_thenReturnOkAndData() throws Exception {
        UserResponseDto userResponseDto = getUserResponseDto();

        when(userService.getAllUsers()).thenReturn(List.of(userResponseDto));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$[0].id").value(userResponseDto.getId()),
                        jsonPath("$[0].name").value(userResponseDto.getName()),
                        jsonPath("$[0].email").value(userResponseDto.getEmail()),
                        jsonPath("$[0].age").value(userResponseDto.getAge()),
                        jsonPath("$[0].createdAt[0]").value(userResponseDto.getCreatedAt().getYear()),
                        jsonPath("$[0].createdAt[1]").value(userResponseDto.getCreatedAt().getMonthValue()),
                        jsonPath("$[0].createdAt[2]").value(userResponseDto.getCreatedAt().getDayOfMonth()),
                        jsonPath("$[0].createdAt[3]").value(userResponseDto.getCreatedAt().getHour()),
                        jsonPath("$[0].createdAt[4]").value(userResponseDto.getCreatedAt().getMinute()),
                        jsonPath("$[0].createdAt[5]").value(userResponseDto.getCreatedAt().getSecond()),
                        jsonPath("$[0].createdAt[6]").value(userResponseDto.getCreatedAt().getNano())
                );

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void whenUpdateUser_thenReturnOkAndUpdatedData() throws Exception {
        UserRequestDto userRequestDto = getUserRequestDto();
        UserResponseDto userResponseDto = getUserResponseDto();

        when(userService.updateUser(1L, userRequestDto)).thenReturn(userResponseDto);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto))
                )
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(userResponseDto.getId()),
                        jsonPath("$.name").value(userResponseDto.getName()),
                        jsonPath("$.email").value(userResponseDto.getEmail()),
                        jsonPath("$.age").value(userResponseDto.getAge()),
                        jsonPath("$.createdAt[0]").value(userResponseDto.getCreatedAt().getYear()),
                        jsonPath("$.createdAt[1]").value(userResponseDto.getCreatedAt().getMonthValue()),
                        jsonPath("$.createdAt[2]").value(userResponseDto.getCreatedAt().getDayOfMonth()),
                        jsonPath("$.createdAt[3]").value(userResponseDto.getCreatedAt().getHour()),
                        jsonPath("$.createdAt[4]").value(userResponseDto.getCreatedAt().getMinute()),
                        jsonPath("$.createdAt[5]").value(userResponseDto.getCreatedAt().getSecond()),
                        jsonPath("$.createdAt[6]").value(userResponseDto.getCreatedAt().getNano())
                );
    }

    @Test
    void whenDeleteUser_thenReturnNoContentCode() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    private UserRequestDto getUserRequestDto() {
        return UserRequestDto.builder()
                .name("testName")
                .email("test@example.com")
                .age(30)
                .build();
    }

    private UserResponseDto getUserResponseDto() {
        return UserResponseDto.builder()
                .id(1L)
                .name("testName")
                .email("test@example.com")
                .age(30)
                .createdAt(LocalDateTime.now())
                .build();
    }
}