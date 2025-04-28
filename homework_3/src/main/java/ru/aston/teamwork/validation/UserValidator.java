package ru.aston.teamwork.validation;

import ru.aston.teamwork.entity.User;

import java.util.List;

public interface UserValidator {
    boolean emailValidate(String email, List<User> users);

    boolean ageValidate(int age);

    boolean newDataValidate(String oldData, String newData);
}
