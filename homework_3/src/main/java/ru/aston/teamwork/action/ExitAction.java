package ru.aston.teamwork.action;

public class ExitAction implements UserAction {
    @Override
    public String name() {
        return "Выход";
    }

    @Override
    public boolean execute(Input input, SimpleUserRepository simpleUserRepository, Output out) {
        out.println("=== Завершение работы приложения ===");
        if (input.askStr("Вы уверены? (y/n): ").equalsIgnoreCase("y")) {
            out.println("Работа приложения завершена");
            return false;
        }
        return true;
    }
}
