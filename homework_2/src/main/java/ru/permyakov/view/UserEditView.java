package ru.permyakov.view;

public class UserEditView {
    private static final String screenText =
            """
                    1. Изменить имя
                    2. Изменить email
                    3. Изменить возраст
                    4. Удалить пользователя
                    5. Назад ко всем пользователям
                    """;

    public static void display() {
        System.out.print(screenText);
    }
}
