package ru.aston.teamwork.action;

public class CreateUserAction implements UserAction {
    @Override
    public String name() {
        return "Создать пользователя";
    }

    @Override
    public boolean execute(Input input, SimpleUserRepository simpleUserRepository, Output out) {
        out.println("\n=== Создание нового пользователя ===");
        String name = input.askStr("Введите имя: ");
        String email = input.askStr("Введите email: ");
        int age = input.askInt("Введите возраст: ");
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAge(String.valueOf(age));
        Long id = simpleUserRepository.save(user);
        out.println("Пользователь создан с ID: " + id);
        return true;
    }
}
