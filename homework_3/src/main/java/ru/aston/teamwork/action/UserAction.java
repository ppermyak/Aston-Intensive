package ru.aston.teamwork.action;

import ru.aston.teamwork.input.Input;
import ru.aston.teamwork.output.Output;
import ru.aston.teamwork.service.UserServiceImpl;

/**
 * Интерфейс для действий (команд), выполняемых над пользователями.
 * Каждая реализация представляет собой отдельную операцию (создание, удаление и т. д.).
 *
 * <p>Позволяет:
 * <ul>
 *   <li>Унифицировать вызов разных действий через один метод {@code execute()}.</li>
 *   <li>Легко добавлять новые операции без изменения существующего кода.</li>
 *   <li>Использовать полиморфизм для управления действиями.</li>
 * </ul>
 */
public interface UserAction {
    /**
     * Возвращает название действия (для отображения в меню).
     */
    String name();

    /**
     * Выполняет действие.
     *
     * @param input   источник ввода данных (например, консоль)
     * @param userService сервис где выполняется логика (создание, чтение, изменение, удаление)
     * @param out     способ вывода информации
     * @return {@code true}, если действие выполнено успешно
     */
    boolean execute(Input input, UserServiceImpl userService, Output out);
}
