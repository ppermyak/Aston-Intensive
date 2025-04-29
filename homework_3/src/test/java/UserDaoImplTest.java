import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.teamwork.config.HibernateConfig;
import ru.aston.teamwork.dao.UserDaoImpl;
import ru.aston.teamwork.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class UserDaoImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("user_service_db_test")
            .withUsername("test")
            .withPassword("password");

    private static UserDaoImpl userDao;

    @BeforeAll
    static void setUp() {
        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());

        userDao = new UserDaoImpl();
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.shutdown();
    }

    @Test
    void shouldCreateUserSuccessfullyWhenValidDataIsProvided() {
        User user = new User("Bob", "bob@exmple.com", "25");
        Long id = userDao.save(user);

        assertNotNull(id);
    }

    @Test
    void shouldFindUserByIdSuccessfullyWhenUserExists () {
        User user = new User("Anna", "anna@exmple.com", "30");
        Long id = userDao.save(user);

        User foundUser = userDao.findById(id);
        assertNotNull(foundUser);
        assertEquals("Anna", foundUser.getName());
        assertEquals("anna@exmple.com", foundUser.getEmail());
        assertEquals("30", foundUser.getAge());
    }

    @Test
    void shouldReturnAllUsersWhenUsersExistInDatabase () {
        User user1 = new User("John", "john@exmple.com", "40");
        User user2 = new User("Ken", "ken@exmple.com", "45");

        userDao.save(user1);
        userDao.save(user2);

        List<User> users = userDao.findAll();
        assertFalse(users.isEmpty());
        assertTrue(users.size() >= 2);
    }

    @Test
    void shouldUpdateUserSuccessfullyWhenValidDataIsProvided() {
        User user = new User("Max", "max@exmple.com", "30");
        Long id = userDao.save(user);

        User toUpdate = userDao.findById(id);
        toUpdate.setName("Maxim");
        toUpdate.setAge("31");

        boolean update = userDao.update(toUpdate);
        assertTrue(update);

        User updateUser = userDao.findById(id);
        assertEquals("Maxim", updateUser.getName());
        assertEquals("31", updateUser.getAge());
    }

    @Test
    void shouldDeleteUserSuccessfullyWhenUserExists() {
        User user = new User("Ado", "ado@exmple.com", "35");
        Long id = userDao.save(user);

        boolean deleted = userDao.deleteById(id);
        assertTrue(deleted);

        User deletedUser = userDao.findById(id);
        assertNull(deletedUser);
    }

}
