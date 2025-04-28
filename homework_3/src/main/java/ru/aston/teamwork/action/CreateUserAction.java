package ru.aston.teamwork.action;

import ru.aston.teamwork.dao.UserDaoImpl;
import ru.aston.teamwork.entity.User;
import ru.aston.teamwork.input.Input;
import ru.aston.teamwork.output.Output;
import ru.aston.teamwork.validation.UserValidator;
import ru.aston.teamwork.validation.UserValidatorImpl;

public class CreateUserAction implements UserAction {
    UserValidator userValidator = new UserValidatorImpl();

    @Override
    public String name() {
        return "Создать пользователя";
    }

    @Override
    public boolean execute(Input input, UserDaoImpl userDao, Output out) {
        out.println("\n=== Создание нового пользователя ===");
        String name = input.askStr("Введите имя: ");
        String email;
        do {
            email = input.askStr("Введите email: ");
        } while (!userValidator.emailValidate(email, userDao.findAll()));
        int age;
        do {
            age = input.askInt("Введите возраст: ");
        } while (!userValidator.ageValidate(age));
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAge(String.valueOf(age));
        Long id = userDao.save(user);
        out.println("Пользователь создан с ID: " + id);
        return true;
    }
}
