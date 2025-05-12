package ru.aston.notificationservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserActionEvent {
    private String action;
    private String email;
}
