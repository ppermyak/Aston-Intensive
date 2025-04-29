package ru.aston.teamwork.action;

import ru.aston.teamwork.entity.User;
import ru.aston.teamwork.input.Input;
import ru.aston.teamwork.output.Output;
import ru.aston.teamwork.service.UserServiceImpl;
import ru.aston.teamwork.validation.UserValidator;
import ru.aston.teamwork.validation.UserValidatorImpl;

public class UpdateUserAction implements UserAction {
    UserValidator userValidator = new UserValidatorImpl();

    @Override
    public String name() {
        return "Обновить пользователя";
    }

    @Override
    public boolean execute(Input input, UserServiceImpl userService, Output out) {
        out.println("\n=== Обновление пользователя ===");
        long id = input.askLong("Введите ID пользователя для обновления: ");
        User user = userService.findById(id);
        if (user == null) {
            out.println("Пользователь с ID " + id + " не найден");
            return true;
        }
        out.println("Текущие данные: " + user);
        while (true) {
            String newName = input.askStr("Новое имя (оставьте пустым чтобы не менять): ");
            if (!newName.isEmpty() && userValidator.newDataValidate(newName, user.getName())) {
                user.setName(newName);
                break;
            } else if (newName.isEmpty()) {
                break;
            }
        }
        while (true) {
            String newEmail = input.askStr("Новый email (оставьте пустым чтобы не менять): ");
            if (!newEmail.isEmpty() && userValidator.emailValidate(newEmail, userService.findAll())) {
                user.setEmail(newEmail);
                break;
            } else if (newEmail.isEmpty()) {
                break;
            }
        }
        while (true) {
            String newAge = input.askStr("Новый возраст (оставьте пустым чтобы не менять): ");
            if (!newAge.isEmpty() && userValidator.ageValidate(Integer.parseInt(newAge))) {
                user.setAge(newAge);
                break;
            } else if (newAge.isEmpty()) {
                break;
            }
        }
        if (userService.update(user)) {
            out.println("Пользователь успешно обновлен");
        } else {
            out.println("Не удалось обновить пользователя");
        }
        return true;
    }
}
