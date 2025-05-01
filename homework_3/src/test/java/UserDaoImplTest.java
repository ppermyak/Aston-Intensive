import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.teamwork.config.HibernateConfig;
import ru.aston.teamwork.dao.UserDaoImpl;
import ru.aston.teamwork.entity.User;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class UserDaoImplTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("user_service_db_test")
            .withUsername("test")
            .withPassword("password");

    private static UserDaoImpl userDao;

    @BeforeAll
    static void setUp() {
        System.setProperty("hibernate.connection.url", POSTGRES.getJdbcUrl());
        System.setProperty("hibernate.connection.username", POSTGRES.getUsername());
        System.setProperty("hibernate.connection.password", POSTGRES.getPassword());

        userDao = new UserDaoImpl();
    }

    @BeforeEach
    void clearDatabase() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            tx.commit();
        }
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
    void shouldFindUserByIdSuccessfullyWhenUserExists() {
        User user = new User("Anna", "anna@exmple.com", "30");
        Long id = userDao.save(user);
        User foundUser = userDao.findById(id);
        assertNotNull(foundUser);
        assertEquals("Anna", foundUser.getName());
        assertEquals("anna@exmple.com", foundUser.getEmail());
        assertEquals("30", foundUser.getAge());
    }

    @Test
    void shouldReturnAllUsersWhenUsersExistInDatabase() {
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

    @Test
    void shouldThrowExceptionWhenSavingUserWithExistingEmail() {
        User user1 = new User("Bob", "bob@exmple.com", "25");
        userDao.save(user1);

        User user2 = new User("Bob2", "bob@exmple.com", "26");
        assertThrows(PersistenceException.class, () -> userDao.save(user2));
    }

    @Test
    void shouldThrowExceptionWhenSavingUserWithNullName() {
        User user = new User(null, "nullname@exmple.com", "25");
        assertThrows(PersistenceException.class, () -> userDao.save(user));
    }

    @Test
    void shouldThrowExceptionWhenSavingUserWithNullEmail() {
        User user = new User("Null", null, "25");
        assertThrows(PersistenceException.class, () -> userDao.save(user));
    }

    @Test
    void shouldRollbackTransactionOnError() {
        User user = new User("Max", "max@exmple.com", "30");
        userDao.save(user);

        User invalidUser = new User("", null, "35");
        assertThrows(PersistenceException.class, () -> userDao.save(invalidUser));

        List<User> users = userDao.findAll();
        assertEquals(1, users.size());
    }

    @Test
    void shouldNotUpdateCreatedAtWhenUserIsUpdated() {
        User user = new User("Max", "max@exmple.com", "30");
        Long id = userDao.save(user);

        LocalDateTime originalCreatedAt = userDao.findById(id).getCreatedAt();

        User toUpdate = userDao.findById(id);
        toUpdate.setName("Maxim");
        toUpdate.setAge("31");

        userDao.update(toUpdate);

        User updatedUser = userDao.findById(id);
        assertEquals(originalCreatedAt, updatedUser.getCreatedAt());
    }
}
