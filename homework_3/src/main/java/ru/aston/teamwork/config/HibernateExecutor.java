package ru.aston.teamwork.config;

import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Класс для упрощённого выполнения операций с Hibernate-сессиями и транзакциями.
 * Инкапсулирует открытие сессий, управление транзакциями и логирование.
 */
public class HibernateExecutor {
    private final SessionFactory sessionFactory;
    private final Logger logger;

    public HibernateExecutor(SessionFactory sessionFactory, Logger logger) {
        this.sessionFactory = sessionFactory;
        this.logger = logger;
    }

    /**
     * Выполняет операцию в сессии без транзакции.
     * Подходит для операций чтения.
     *
     * @param function    операция, принимающая сессию и возвращающая результат
     * @param successLog  функция для создания сообщения об успехе на основе результата
     * @param notFoundLog сообщение, если результат null (можно передать null)
     * @param <T>         тип возвращаемого значения
     * @return результат выполнения операции
     */
    public <T> T executeInSession(Function<Session, T> function,
                                  Function<T, String> successLog,
                                  Supplier<String> notFoundLog) {
        try (Session session = sessionFactory.openSession()) {
            T result = function.apply(session);
            if (result != null) {
                logger.info(successLog.apply(result));
            } else if (notFoundLog != null) {
                logger.info(notFoundLog.get());
            }
            return result;
        } catch (HibernateException e) {
            logger.error("Ошибка при выполнении операции", e);
            throw new PersistenceException("Ошибка доступа к данным", e);
        }
    }

    /**
     * Перегрузка метода {@link #executeInSession(Function, Function, Supplier)},
     * без сообщения при отсутствии результата.
     *
     * @param function   операция, принимающая сессию и возвращающая результат
     * @param successLog функция для логирования успешного результата
     * @param <T>        тип результата
     * @return результат выполнения
     */
    public <T> T executeInSession(Function<Session, T> function,
                                  Function<T, String> successLog) {
        return executeInSession(function, successLog, null);
    }

    /**
     * Выполняет транзакционную операцию с результатом.
     * Если операция прошла успешно — результат возвращается и логируется.
     *
     * @param operation      функция, принимающая сессию и возвращающая результат
     * @param successMessage сообщение для логирования при успехе
     * @param <T>            тип возвращаемого значения
     * @return результат выполнения
     */
    public <T> T executeInTransactionWithResult(Function<Session, T> operation,
                                                String successMessage) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            T result = operation.apply(session);
            transaction.commit();
            logger.info(successMessage);
            return result;
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    logger.error("Откат транзакции не удался", rollbackEx);
                }
            }
            logger.error("Транзакционная операция не удалась", e);
            throw new PersistenceException("Транзакционная операция не удалась", e);
        }
    }

    /**
     * Выполняет транзакционную операцию без возвращаемого значения.
     *
     * @param operation      потребитель, принимающий сессию
     * @param successMessage сообщение для логирования при успехе
     */
    public void executeInTransaction(Consumer<Session> operation,
                                     String successMessage) {
        executeInTransactionWithResult(session -> {
            operation.accept(session);
            return null;
        }, successMessage);
    }
}
