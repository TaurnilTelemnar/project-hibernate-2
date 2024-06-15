package config;

import entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Closeable;

public class SessionCreator implements Closeable {

    private final SessionFactory sessionFactory;

    public SessionCreator() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Actor.class);
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(City.class);
        configuration.addAnnotatedClass(Country.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Film.class);
        configuration.addAnnotatedClass(FilmText.class);
        configuration.addAnnotatedClass(Inventory.class);
        configuration.addAnnotatedClass(Language.class);
        configuration.addAnnotatedClass(Payment.class);
        configuration.addAnnotatedClass(Rental.class);
        configuration.addAnnotatedClass(Staff.class);
        configuration.addAnnotatedClass(Store.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public void close() {
        sessionFactory.close();
    }

}
