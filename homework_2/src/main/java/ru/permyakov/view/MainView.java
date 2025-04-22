package ru.permyakov.view;

public class MainView {
    private static final String screenText =
            """
                    1. Создать пользователя
                    2. Все пользователи
                    3. Выйти из приложения
                    """;

    public static void display() {
        System.out.print(screenText);
    }
}
