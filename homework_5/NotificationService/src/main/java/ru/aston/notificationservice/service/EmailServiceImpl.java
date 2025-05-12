package ru.aston.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(String to, String message) {
        log.info("Email (sent, test mode):\nTo: {}\nText: {}", to, message);
    }

}
