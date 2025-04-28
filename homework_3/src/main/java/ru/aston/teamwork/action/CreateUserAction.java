package ru.aston.teamwork.action;

import ru.aston.teamwork.dao.UserDaoImpl;
import ru.aston.teamwork.entity.User;
import ru.aston.teamwork.input.Input;
import ru.aston.teamwork.output.Output;

public class CreateUserAction implements UserAction {
    @Override
    public String name() {
        return "Создать пользователя";
    }

    @Override
    public boolean execute(Input input, UserDaoImpl userDao, Output out) {
        out.println("\n=== Создание нового пользователя ===");
        String name = input.askStr("Введите имя: ");
        String email = input.askStr("Введите email: ");
        int age = input.askInt("Введите возраст: ");
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAge(String.valueOf(age));
        Long id = userDao.save(user);
        out.println("Пользователь создан с ID: " + id);
        return true;
    }
}
