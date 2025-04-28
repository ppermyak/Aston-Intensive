package ru.aston.teamwork.action;

import ru.aston.teamwork.dao.UserDao;
import ru.aston.teamwork.entity.User;
import ru.aston.teamwork.input.Input;
import ru.aston.teamwork.output.Output;

public class UpdateUserAction implements UserAction {
    @Override
    public String name() {
        return "Обновить пользователя";
    }

    @Override
    public boolean execute(Input input, UserDao userDao, Output out) {
        out.println("\n=== Обновление пользователя ===");
        long id = input.askLong("Введите ID пользователя для обновления: ");
        User user = userDao.findById(id);
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
        if (userDao.update(user)) {
            out.println("Пользователь успешно обновлен");
        } else {
            out.println("Не удалось обновить пользователя");
        }
        return true;
    }
}
