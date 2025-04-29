package ru.aston.teamwork.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.teamwork.dao.UserDaoImpl;
import ru.aston.teamwork.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    UserServiceImpl userService;

    private User createTestUser() {
        User user = new User();
        user.setName("testName");
        user.setEmail("test@exampler.com");
        user.setAge("30");
        return user;
    }

    private List<User> createTestUsers() {
        User user1 = new User("testName1", "test1@example.com", "50");
        User user2 = new User("testName2", "test2@example.com", "100");

        return List.of(user1, user2);
    }

    @Test
    void saveNewUserWithValidDataShouldReturnNotNull() {
        User newUser = createTestUser();

        when(userDao.save(newUser)).thenReturn(1L);

        assertNotNull(userService.save(newUser));
        verify(userDao, times(1)).save(newUser);
    }

    @Test
    void getUserByExistIdShouldReturnUser() {
        User user = createTestUser();

        when(userDao.findById(1L)).thenReturn(user);

        assertEquals(user, userService.findById(1L));
        verify(userDao, times(1)).findById(1L);
    }

    @Test
    void getUserByNotExistIdShouldReturnNull() {
        when(userDao.findById(123L)).thenReturn(null);

        assertNull(userService.findById(123L));
        verify(userDao, times(1)).findById(123L);
    }

    @Test
    void getAllUsersWhenExistsUsersShouldReturnListUsers() {
        List<User> users = createTestUsers();

        when(userDao.findAll()).thenReturn(users);

        assertEquals(users, userService.findAll());
        verify(userDao, times(1)).findAll();
    }

    @Test
    void getAllUsersWhenNotExistsUsersShouldReturnNull() {
        when(userDao.findAll()).thenReturn(null);

        assertNull(userService.findAll());
        verify(userDao, times(1)).findAll();
    }

    @Test
    void updateUserShouldReturnTrue() {
        User user = createTestUser();

        when(userDao.update(user)).thenReturn(true);

        assertTrue(userService.update(user));
        verify(userDao, times(1)).update(user);
    }

    @Test
    void deleteByIdUserShouldReturnTrue() {
        User user = createTestUser();

        when(userDao.deleteById(user.getId())).thenReturn(true);

        assertTrue(userService.deleteById(user.getId()));
        verify(userDao, times(1)).deleteById(user.getId());
    }
}