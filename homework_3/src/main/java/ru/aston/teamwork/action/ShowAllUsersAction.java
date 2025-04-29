package ru.aston.teamwork.action;

import ru.aston.teamwork.entity.User;
import ru.aston.teamwork.input.Input;
import ru.aston.teamwork.output.Output;
import ru.aston.teamwork.service.UserServiceImpl;

import java.util.List;

public class ShowAllUsersAction implements UserAction {
    @Override
    public String name() {
        return "Показать всех пользователей";
    }

    @Override
    public boolean execute(Input input, UserServiceImpl userService, Output out) {
        out.println("\n=== Список всех пользователей ===");
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            out.println("Пользователи не найдены");
        } else {
            users.forEach(user -> out.println(user.toString()));
        }
        return true;
    }
}
