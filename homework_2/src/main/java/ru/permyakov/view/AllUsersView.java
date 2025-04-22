package ru.permyakov.view;

import ru.permyakov.entity.User;
import ru.permyakov.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class AllUsersView {
    public static List<User> display(UserService userService) {
        List<User> users = new ArrayList<>(userService.findAllUsers());
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).toString());
        }
        System.out.println("0. Вернуться в главное меню");
        return users;
    }
}
