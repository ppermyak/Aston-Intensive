package ru.aston.teamwork;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.aston.teamwork.action.*;
import ru.aston.teamwork.config.HibernateConfig;
import ru.aston.teamwork.dao.UserDaoImpl;
import ru.aston.teamwork.input.ConsoleInput;
import ru.aston.teamwork.input.Input;
import ru.aston.teamwork.output.ConsoleOutput;
import ru.aston.teamwork.output.Output;
import ru.aston.teamwork.service.UserServiceImpl;

import java.util.Arrays;
import java.util.List;

public class HibernateConsoleApp {
    private static final Logger LOGGER = LogManager.getLogger(HibernateConsoleApp.class);
    private final Output out;

    public HibernateConsoleApp(Output out) {
        this.out = out;
    }

    public void init(Input input, UserServiceImpl userService, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            showMenu(actions);
            int select = input.askInt("Выберите пункт: ");
            if (select < 0 || select >= actions.size()) {
                out.println("Неверный ввод, выберите: 0 .. " + (actions.size() - 1));
                continue;
            }
            UserAction action = actions.get(select);
            run = action.execute(input, userService, out);
        }
    }

    private void showMenu(List<UserAction> actions) {
        out.println("\n=== Меню управления пользователями ===");
        for (int i = 0; i < actions.size(); i++) {
            out.println(i + ". " + actions.get(i).name());
        }
    }

    public static void main(String[] args) {
        try {
            LOGGER.info("Приложение запущено");
            Output output = new ConsoleOutput();
            Input input =  new ConsoleInput();
            UserDaoImpl dao = new UserDaoImpl();
            UserServiceImpl userService = new UserServiceImpl(dao);
            List<UserAction> actions;
            actions = Arrays.asList(
                    new CreateUserAction(),
                    new FindUserByIdAction(),
                    new ShowAllUsersAction(),
                    new UpdateUserAction(),
                    new DeleteUserAction(),
                    new ExitAction()
            );
            new HibernateConsoleApp(output).init(input, userService, actions);
        } catch (Exception e) {
            LOGGER.error("Ошибка в приложении", e);
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            HibernateConfig.shutdown();
            LOGGER.info("Приложение остановлено");
        }
    }
}
