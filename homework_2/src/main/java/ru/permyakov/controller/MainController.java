package ru.permyakov.controller;

import lombok.extern.slf4j.Slf4j;
import ru.permyakov.service.UserService;
import ru.permyakov.view.MainView;

import java.util.Scanner;

@Slf4j
public class MainController implements BaseController {
    private final UserService userService;
    private String nextView = "Main";

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void startAction(Scanner scanner) {
        MainView.display();
        String commandNumber = scanner.nextLine();
        switch (commandNumber) {
            case "1":
                userService.createUser(scanner);
                break;
            case "2":
                nextView = "Users";
                break;
            case "3":
                System.exit(0);
                break;
            default:
                System.out.println("Такой команды не существует");
                log.info("Введена не сушествующая команда");
        }
    }

    @Override
    public String nextView() {
        return nextView;
    }
}
