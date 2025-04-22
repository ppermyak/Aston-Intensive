package ru.permyakov.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.permyakov.dao.UserDAO;
import ru.permyakov.entity.User;
import ru.permyakov.validation.UserValidator;
import ru.permyakov.view.UserEditView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@NoArgsConstructor
@Slf4j
public class UserService {
    private final UserDAO usersDao = new UserDAO();
    private final UserValidator userValidator = new UserValidator();

    public List<User> findByEmail(String email) {
        return usersDao.findByEmail(email);
    }

    public void createUser(Scanner scanner) {
        User user = new User();
        System.out.println("Введите имя пользователя:");
        String name = scanner.nextLine();
        user.setName(name);

        while (true) {
            System.out.println("Введите email пользователя:");
            String email = scanner.nextLine();
            if (userValidator.emailValidate(email, this)) {
                user.setEmail(email);
                break;
            }
        }

        while (true) {
            System.out.println("Введите возраст пользователя:");
            String age = scanner.nextLine();
            if (userValidator.ageValidate(age)) {
                user.setAge(Integer.parseInt(age));
                break;
            }
        }

        user.setCreatedAt(LocalDateTime.now());

        usersDao.create(user);
        System.out.println("Пользователь успешно создан");
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
        System.out.println("Пользователь успешно удален");
    }

    public void updateUser(User user, Scanner scanner) {
        while (true) {
            System.out.println("Данные пользователя: " + user);
            UserEditView.display();
            String commandNumber = scanner.nextLine();
            switch (commandNumber) {
                case "1":
                    String oldName = user.getName();
                    while (true) {
                        System.out.println("Введите новое имя");
                        String newName = scanner.nextLine();
                        if (userValidator.newDataValidate(user.getName(), newName)) {
                            user.setName(newName);
                            break;
                        }
                    }
                    usersDao.update(user);
                    log.info("У пользователя с id: {}, поменяли имя '{}' на новое '{}'", user.getId(), oldName, user.getName());
                    System.out.println("Имя успешно изменено");
                    break;
                case "2":
                    String oldEmail = user.getEmail();
                    while (true) {
                        System.out.println("Введите новый email:");
                        String newEmail = scanner.nextLine();
                        if (userValidator.emailValidate(newEmail, this)
                                && userValidator.newDataValidate(user.getEmail(), newEmail)) {
                            user.setEmail(newEmail);
                            break;
                        }
                    }
                    usersDao.update(user);
                    log.info("У пользователя с id: {}, поменяли email '{}' на новый '{}'", user.getId(), oldEmail, user.getEmail());
                    System.out.println("email успешно изменен");
                    break;
                case "3":
                    int oldAge = user.getAge();
                    while (true) {
                        System.out.println("Введите новый возраст");
                        String newAge = scanner.nextLine();
                        if (userValidator.ageValidate(newAge)
                                && userValidator.newDataValidate(user.getAge().toString(), newAge)) {
                            user.setAge(Integer.parseInt(newAge));
                            break;
                        }
                    }
                    usersDao.update(user);
                    log.info("У пользователя с id: {}, поменяли возраст '{}' на новый '{}'", user.getId(), oldAge, user.getAge());
                    System.out.println("Возраст успешно изменен");
                    break;
                case "4":
                    deleteUser(user);
                    return;
                case "5":
                    return;
                default:
                    System.out.println("Такой команды не существует");
            }
        }
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }
}
