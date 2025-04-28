package ru.aston.teamwork.validation;

import ru.aston.teamwork.entity.User;

import java.util.List;

/**
 * Интерфейс для проверки на корректность и доступность полей {@code User}.
 *
 * <p> Предоставляет методы для проверки {@code email}, {@code age}, и новых данных.
 */
public interface UserValidator {
    /**
     * Проверяет существует ли {@code User} с таким {@code email}
     * Проверяет на корректность значение {@code email}
     *
     * @param email электронная почта
     * @param users существующие пользователи
     * @return {@code true} - если валидация пройдена, {@code false} - в противном случае
     */
    boolean emailValidate(String email, List<User> users);

    /**
     * Проверяет на корректность значение возраста
     *
     * @param age возраст
     * @return {@code true} - если валидация пройдена, {@code false} - в противном случае
     */
    boolean ageValidate(int age);

    /**
     * Проверяет отличие новых данных от текущих
     *
     * @param oldData текущие данные
     * @param newData новые данные
     * @return {@code true} - если валидация пройдена, {@code false} - в противном случае
     */
    boolean newDataValidate(String oldData, String newData);
}
