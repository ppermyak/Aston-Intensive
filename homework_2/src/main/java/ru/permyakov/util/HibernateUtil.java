package ru.permyakov.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.permyakov.entity.User;

public class HibernateUtil {
    private static SessionFactory factory;

    private HibernateUtil() {}

    public static SessionFactory getFactory() {
        if (factory == null) {
                Configuration configuration = new Configuration();
                configuration.configure();
                configuration.addAnnotatedClass(User.class);
                factory = configuration.buildSessionFactory();
        }
        return factory;
    }
}
