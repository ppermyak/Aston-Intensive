package ru.aston.notificationservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @CircuitBreaker(name = "emailService", fallbackMethod = "fallbackSendEmail")
    @Override
    public void sendEmail(String to, String message) {
        log.info("Email (sent, test mode):\nTo: {}\nText: {}", to, message);
    }

    public void fallbackSendEmail(String to, String message, Throwable t) {
        log.warn("Fallback: email to {} has not been sent. Message: '{}'. Reason: {}", to, message, t.getMessage());
    }


}
