package ru.aston.teamwork.action;

import ru.aston.teamwork.dao.UserDao;
import ru.aston.teamwork.entity.User;
import ru.aston.teamwork.input.Input;
import ru.aston.teamwork.output.Output;

public class FindUserByIdAction implements UserAction {
    @Override
    public String name() {
        return "Найти пользователя по ID";
    }

    @Override
    public boolean execute(Input input, UserDao userDao, Output out) {
        out.println("\n=== Поиск пользователя по ID ===");
        long id = input.askLong("Введите ID пользователя: ");
        User user = userDao.findById(id);
        if (user != null) {
            out.println("Найден пользователь: " + user);
        } else {
            out.println("Пользователь с ID " + id + " не найден");
        }
        return true;
    }
}
