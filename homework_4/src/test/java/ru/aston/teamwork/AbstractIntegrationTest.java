package ru.aston.teamwork;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.teamwork.repository.UserRepository;


@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@Transactional
@Rollback
public abstract class AbstractIntegrationTest {

    @Autowired
    protected UserRepository userRepository;


    @Container
    public static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");


    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        System.out.println("PostgreSQL URL: " + postgres.getJdbcUrl());
    }

    @BeforeEach
    void clearDatabase() {
        userRepository.deleteAll();
    }
}
