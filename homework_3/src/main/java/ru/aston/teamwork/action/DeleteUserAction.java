package ru.aston.teamwork.action;

public class DeleteUserAction implements UserAction {
    @Override
    public String name() {
        return "Удалить пользователя";
    }

    @Override
    public boolean execute(Input input, SimpleUserRepository simpleUserRepository, Output out) {
        out.println("\n=== Удаление пользователя ===");
        long id = input.askLong("Введите ID пользователя для удаления: ");
        if (simpleUserRepository.deleteById(id)) {
            out.println("Пользователь с ID " + id + " успешно удален");
        } else {
            out.println("Пользователь с ID " + id + " не найден или удаление не удалось");
        }
        return true;
    }
}
