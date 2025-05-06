package ru.aston.teamwork.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aston.teamwork.AbstractIntegrationTest;
import ru.aston.teamwork.dto.UserRequestDto;
import ru.aston.teamwork.repository.UserRepository;

public abstract class AbstractUserServiceTest extends AbstractIntegrationTest {

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserRepository userRepository;

    protected UserRequestDto testUserDto;

    @BeforeEach
    void initUserDto() {
        testUserDto = UserRequestDto.builder()
                .name("TestUser")
                .email("test@example.com")
                .age(25)
                .build();
    }
}
