package ru.aston.teamwork.action;

public class FindUserByIdAction implements UserAction {
    @Override
    public String name() {
        return "Найти пользователя по ID";
    }

    @Override
    public boolean execute(Input input, SimpleUserRepository simpleUserRepository, Output out) {
        out.println("\n=== Поиск пользователя по ID ===");
        long id = input.askLong("Введите ID пользователя: ");
        User user = simpleUserRepository.findById(id);
        if (user != null) {
            out.println("Найден пользователь: " + user);
        } else {
            out.println("Пользователь с ID " + id + " не найден");
        }
        return true;
    }
}
