package ru.aston.teamwork.service;

import ru.aston.teamwork.entity.User;

import java.util.List;

public interface UserService {
    Long save(User user);

    User findById(Long id);

    List<User> findAll();

    boolean update(User user);

    void delete(User user);

    boolean deleteById(Long id);
}
