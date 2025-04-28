package ru.aston.teamwork.input;

import java.util.Scanner;

/**
 * Реализация интерфейса {@link Input}, получающая данные из консоли.
 * Использует {@link Scanner} для чтения ввода пользователя.
 *
 * <p>При вводе нечисловых значений в методы {@code askInt} и {@code askLong}
 * выводится сообщение об ошибке, и ввод повторяется до получения корректных данных.
 *
 * @see Input
 */
public class ConsoleInput implements Input {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String askStr(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    @Override
    public int askInt(String msg) {
        while (true) {
            try {
                return Integer.parseInt(askStr(msg));
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
    }

    @Override
    public long askLong(String msg) {
        while (true) {
            try {
                return Long.parseLong(askStr(msg));
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
    }
}
