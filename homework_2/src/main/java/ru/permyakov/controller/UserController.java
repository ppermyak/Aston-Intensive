package ru.permyakov.controller;

import lombok.extern.slf4j.Slf4j;
import ru.permyakov.entity.User;
import ru.permyakov.service.UserService;
import ru.permyakov.view.AllUsersView;

import java.util.List;
import java.util.Scanner;

@Slf4j
public class UserController implements BaseController {
    private final UserService userService;
    private String nextView = "Users";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void startAction(Scanner scanner) {
        List<User> users = AllUsersView.display(userService);
        String commandNumber = scanner.nextLine();
        try {
            if (Integer.parseInt(commandNumber) > users.size()) {
                System.out.println("Такой команды не существует");
            } else if (commandNumber.equals("0")) {
                nextView = "Main";
            } else {
                userService.updateUser(users.get(Integer.parseInt(commandNumber) - 1), scanner);
            }
        } catch (Exception e) {
            System.out.println("Такой команды не существует");
        }
    }

    @Override
    public String nextView() {
        return nextView;
    }
}
