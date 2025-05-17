package ru.aston.teamwork.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserResponseDto extends RepresentationModel<UserResponseDto> {
    private String name;
    private String email;
    private int age;
    private Long id;
    private LocalDateTime createdAt;
}