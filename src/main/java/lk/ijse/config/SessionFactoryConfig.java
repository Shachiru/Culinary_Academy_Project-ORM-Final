package lk.ijse.config;

import lk.ijse.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SessionFactoryConfig {
    private static SessionFactoryConfig factoryConfig;

    private final SessionFactory sessionFactory;

    private SessionFactoryConfig() {
        Properties properties = new Properties();

        try {
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties");
            if (inputStream == null) {
                System.out.println("Resource not found!");
            } else {
                properties.load(inputStream);
                System.out.println("Resource loaded successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(Student.class)
//                .addAnnotatedClass(Branch.class)
//                .addAnnotatedClass(Book.class)
//                .addAnnotatedClass(Admin.class)
//                .addAnnotatedClass(Transaction.class)
//                .addAnnotatedClass(TransactionDetail.class)
                .buildSessionFactory();
    }

    public static SessionFactoryConfig getInstance() {
        return (factoryConfig == null) ? factoryConfig = new SessionFactoryConfig() : factoryConfig;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
