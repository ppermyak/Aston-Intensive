package ru.aston.teamwork.action;

public class UpdateUserAction implements UserAction {
    @Override
    public String name() {
        return "Обновить пользователя";
    }

    @Override
    public boolean execute(Input input, SimpleUserRepository simpleUserRepository, Output out) {
        out.println("\n=== Обновление пользователя ===");
        long id = input.askLong("Введите ID пользователя для обновления: ");
        User user = simpleUserRepository.findById(id);
        if (user == null) {
            out.println("Пользователь с ID " + id + " не найден");
            return true;
        }
        out.println("Текущие данные: " + user);
        String name = input.askStr("Новое имя (оставьте пустым чтобы не менять): ");
        if (!name.isEmpty()) {
            user.setName(name);
        }
        String email = input.askStr("Новый email (оставьте пустым чтобы не менять): ");
        if (!email.isEmpty()) {
            user.setEmail(email);
        }
        String ageInput = input.askStr("Новый возраст (оставьте пустым чтобы не менять): ");
        if (!ageInput.isEmpty()) {
            user.setAge(ageInput);
        }
        if (simpleUserRepository.update(user)) {
            out.println("Пользователь успешно обновлен");
        } else {
            out.println("Не удалось обновить пользователя");
        }
        return true;
    }
}
