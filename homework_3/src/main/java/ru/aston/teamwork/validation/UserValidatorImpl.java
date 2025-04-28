package ru.aston.teamwork.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.aston.teamwork.dao.UserDaoImpl;
import ru.aston.teamwork.entity.User;

import java.util.List;

public class UserValidatorImpl implements UserValidator {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public boolean emailValidate(String email, List<User> users) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                System.out.println("Такой email уже существует");
                LOGGER.error("email: {} не прошел валидацию, такой email уже существует", email);
                return false;
            }
        }
        if (!email.matches("^[\\w-.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$")) {
            System.out.println("Не приемлимый адрес электронной почты");
            LOGGER.error("email: {} не прошел валидацию, не приемлимый адрес электронной почты", email);
            return false;
        }
        return true;
    }

    @Override
    public boolean ageValidate(int age) {
        if (age < 0) {
            System.out.println("Возраст не может быть меньше 0");
            LOGGER.error("Возраст {} не прошел валидацию, возраст не может быть меньше 0", age);
            return false;
        }
        if (age > 122) {
            System.out.println("Возраст не может быть больше 122");
            LOGGER.error("Возраст {} не прошел валидацию, возраст не может быть больше 122", age);
            return false;
        }
        return true;
    }

    @Override
    public boolean newDataValidate(String oldData, String newData) {
        if (oldData.equals(newData)) {
            System.out.println("Нужно ввести новые данные, вы ввели текущие данные");
            LOGGER.error("Новые данные совпадают со старыми данными");
            return false;
        }
        return true;
    }
}
