package ru.permyakov.validation;

import lombok.extern.slf4j.Slf4j;
import ru.permyakov.service.UserService;

@Slf4j
public class UserValidator {
    public boolean emailValidate(String email, UserService userService) {
        if (!userService.findByEmail(email).isEmpty()) {
            System.out.println("Такой email уже существует");
            log.warn("email: {} не прошел валидацию, такой email уже существует", email);
            return false;
        }
        if (!email.matches("^[\\w-.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$")) {
            System.out.println("Не приемлимый адрес электронной почты");
            log.warn("email: {} не прошел валидацию, не приемлимый адрес электронной почты", email);
            return false;
        }
        return true;
    }

    public boolean ageValidate(String age) {
        try {
            if (Integer.parseInt(age) < 0) {
                System.out.println("Возраст не может быть меньше 0");
                log.warn("возраст {} не прошел валидацию, возраст не может быть меньше 0", age);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Возраст должен состоять только из цифр");
            log.warn("возраст {} не прошел валидацию, возраст должен состоять только из цифр", age);
            return false;
        }
        return true;
    }

    public boolean newDataValidate(String oldData, String newData) {
        if (oldData.equals(newData)) {
            System.out.println("Нужно ввести новые данные, вы ввели текущие данные");
            log.warn("Новые данные совпадают со старыми данными");
            return false;
        }
        return true;
    }
}
