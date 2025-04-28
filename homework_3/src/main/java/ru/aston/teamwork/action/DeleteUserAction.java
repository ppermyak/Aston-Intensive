package ru.aston.teamwork.action;

import ru.aston.teamwork.dao.UserDaoImpl;
import ru.aston.teamwork.input.Input;
import ru.aston.teamwork.output.Output;

public class DeleteUserAction implements UserAction {
    @Override
    public String name() {
        return "Удалить пользователя";
    }

    @Override
    public boolean execute(Input input, UserDaoImpl userDao, Output out) {
        out.println("\n=== Удаление пользователя ===");
        long id = input.askLong("Введите ID пользователя для удаления: ");
        if (userDao.deleteById(id)) {
            out.println("Пользователь с ID " + id + " успешно удален");
        } else {
            out.println("Пользователь с ID " + id + " не найден или удаление не удалось");
        }
        return true;
    }
}
