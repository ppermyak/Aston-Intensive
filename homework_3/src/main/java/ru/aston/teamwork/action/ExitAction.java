package ru.aston.teamwork.action;

import ru.aston.teamwork.input.Input;
import ru.aston.teamwork.output.Output;
import ru.aston.teamwork.service.UserServiceImpl;

public class ExitAction implements UserAction {
    @Override
    public String name() {
        return "Выход";
    }

    @Override
    public boolean execute(Input input, UserServiceImpl userService, Output out) {
        out.println("=== Завершение работы приложения ===");
        if (input.askStr("Вы уверены? (y/n): ").equalsIgnoreCase("y")) {
            out.println("Работа приложения завершена");
            return false;
        }
        return true;
    }
}
