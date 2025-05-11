package ru.aston.teamwork.dto;

import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String name;
    private String email;
    private int age;
    private Long id;
    private LocalDateTime createdAt;
}
