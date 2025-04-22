package ru.permyakov;

import ru.permyakov.controller.BaseController;
import ru.permyakov.controller.MainController;
import ru.permyakov.controller.UserController;
import ru.permyakov.service.UserService;

import java.util.Scanner;

public class HibernateApp {
    public static void main(String[] args) {
        UserService userService = new UserService();
        MainController mainController = new MainController(userService);
        UserController userController = new UserController(userService);
        Scanner scanner = new Scanner(System.in);
        String currentView = "Main";
        while (true) {
            switch (currentView) {
                case "Main":
                    currentView = runController(scanner, mainController);
                    break;
                case "Users":
                    currentView = runController(scanner, userController);
                    break;
            }
        }
    }

    public static String runController(Scanner scanner, BaseController baseController) {
        baseController.startAction(scanner);
        return baseController.nextView();
    }
}
