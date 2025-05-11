package ru.aston.userservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.aston.userservice.AbstractIntegrationTest;
import ru.aston.userservice.dto.UserRequestDto;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractUserServiceTest extends AbstractIntegrationTest {

    @Autowired
    protected UserService userService;

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
