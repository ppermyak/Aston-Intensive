package ru.aston.teamwork.action;

public class ShowAllUsersAction implements UserAction {
    @Override
    public String name() {
        return "Показать всех пользователей";
    }

    @Override
    public boolean execute(Input input, SimpleUserRepository simpleUserRepository, Output out) {
        out.println("\n=== Список всех пользователей ===");
        List<User> users = simpleUserRepository.findAll();
        if (users.isEmpty()) {
            out.println("Пользователи не найдены");
        } else {
            users.forEach(user -> out.println(user.toString()));
        }
        return true;
    }
}
