package ru.aston.teamwork.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private int age;
    private LocalDateTime createdAt;
}
