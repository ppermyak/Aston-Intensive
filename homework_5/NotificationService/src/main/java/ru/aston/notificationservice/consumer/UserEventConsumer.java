package ru.aston.notificationservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.aston.notificationservice.dto.UserActionEvent;
import ru.aston.notificationservice.service.EmailService;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "user-events-topic", groupId = "notification-group")
    public void handleUserEvent(UserActionEvent event) {
        log.info("UserEvent received: {}", event);
        String massage = switch (event.getAction()) {
            case "create" -> "Пользователь с email %s создан".formatted(event.getEmail());
            case "delete" -> "Пользователь с email %s удалён".formatted(event.getEmail());
            default -> "Неизвестное действие для пользователя: %s".formatted(event.getEmail());
        };

        emailService.sendEmail(event.getEmail(), massage);

    }
}
