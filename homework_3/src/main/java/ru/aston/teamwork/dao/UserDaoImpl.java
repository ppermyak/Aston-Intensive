package ru.aston.teamwork.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.aston.teamwork.config.HibernateConfig;
import ru.aston.teamwork.config.HibernateExecutor;
import ru.aston.teamwork.entity.User;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private final HibernateExecutor executor;

    public UserDaoImpl() {
        this.executor = new HibernateExecutor(
                HibernateConfig.getSessionFactory(),
                LOGGER
        );
    }

    @Override
    public Long save(User user) {
        return executor.executeInTransactionWithResult(
                session -> (Long) session.save(user),
                "Пользователь сохранен: " + user
        );
    }

    @Override
    public User findById(Long id) {
        return executor.executeInSession(
                session -> session.get(User.class, id),
                user -> "Найден пользователь: " + user,
                () -> "Ошибка при поиске пользователя по id: " + id
        );
    }

    @Override
    public List<User> findAll() {
        return executor.executeInSession(
                session -> session.createQuery("FROM User", User.class).list(),
                users -> "Найдено " + users.size() + " пользователей"
        );
    }

    @Override
    public boolean update(User user) {
        try {
            executor.executeInTransaction(
                    session -> session.update(user),
                    "Пользователь обновлен: " + user
            );
            return true;
        } catch (Exception e) {
            LOGGER.error("Ошибка при обновлении пользователя: {}", user, e);
            return false;
        }
    }

    @Override
    public void delete(User user) {
        executor.executeInTransaction(
                session -> session.delete(user),
                "Пользователь удален: " + user
        );
    }

    @Override
    public boolean deleteById(Long id) {
        return Optional.ofNullable(findById(id))
                .map(user -> {
                    delete(user);
                    return true;
                })
                .orElse(false);
    }
}
