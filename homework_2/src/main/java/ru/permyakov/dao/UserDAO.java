package ru.permyakov.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.permyakov.entity.User;
import ru.permyakov.util.HibernateUtil;

import java.util.List;

@Slf4j
public class UserDAO {

    public List<User> findByEmail(String email) {
        Session session = HibernateUtil.getFactory().openSession();
        log.info("Открываем session для поиска пользователей по email");
        String hql = "FROM User WHERE email = :email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        log.info("Взяли из БД пользователей с таким email: {}", email);
        session.close();
        log.info("Закрываем session для поиска пользователей по email");
        return users;
    }

    public List<User> findAll() {
        Session session = HibernateUtil.getFactory().openSession();
        log.info("Открываем session для поиска всех пользователей");
        String hql = "FROM User";
        Query query = session.createQuery(hql);
        List<User> users = query.getResultList();
        log.info("Берем всех пользователей из БД");
        session.close();
        log.info("Закрываем session для поиска всех пользователей");
        return users;
    }

    public void create(User user) {
        Session session = HibernateUtil.getFactory().openSession();
        log.info("Открываем session для создания пользователя");
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.persist(user);
            transaction.commit();
            log.info("Создали нового пользователя: {}", user);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Не удалось создать пользователя: {}, ошибка транзакции", user);
        } finally {
            session.close();
            log.info("Закрываем session для создания пользователя");
        }
    }

    public void update(User user) {
        Session session = HibernateUtil.getFactory().openSession();
        log.info("Открываем session для изменения пользователя");
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(user);
            transaction.commit();
            log.info("Изменили пользователя с id: {}", user.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Не удалось изменить пользователя с id: {}, ошибка транзакции", user.getId());
        } finally {
            session.close();
            log.info("Закрываем session для изменения пользователя");
        }
    }

    public void delete(User user) {
        Session session = HibernateUtil.getFactory().openSession();
        log.info("Открываем session для удаления пользователя");
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.remove(user);
            transaction.commit();
            log.info("Удалили пользователя с id: {}", user.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Не удалось удалить пользователя с id: {}, ошибка транзакции", user.getId());
        } finally {
            session.close();
            log.info("Закрываем session для удаления пользователя");
        }
    }
}
