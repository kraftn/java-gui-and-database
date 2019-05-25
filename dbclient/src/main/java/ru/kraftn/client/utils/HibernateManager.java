package ru.kraftn.client.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.kraftn.client.models.*;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

public class HibernateManager {
    private static HibernateManager instance = null;

    private static String loginName;
    private static String password;

    private EntityManager entityManager;

    private HibernateManager() {
        SessionFactory sessionFactory = buildSessionFactory();
        entityManager = sessionFactory.createEntityManager();
    }

    private SessionFactory buildSessionFactory() throws Error {
        Properties dbConnectionProperties = new Properties();
        try {
            dbConnectionProperties.load(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties")
            );
            dbConnectionProperties.setProperty("hibernate.connection.username", loginName);
            dbConnectionProperties.setProperty("hibernate.connection.password", password);
        } catch (IOException e) {
            throw new Error("Cannot read hibernate properties");
        }

        Configuration configuration = new Configuration();
        //Добавить классы с аннотациями hibernate
        RoleManager.getInstance().addClassesToHibernate(configuration);

        configuration.addProperties(dbConnectionProperties);

        return configuration.buildSessionFactory();
    }

    public static void setLoginName(String newLoginName) {
        loginName = newLoginName;
    }
    public static void setPassword(String newPassword) {
        password = newPassword;
    }

    public static HibernateManager getInstance() {
        if (instance == null) {
            instance = new HibernateManager();
        }
        return instance;
    }

    public static void close() {
        instance = null;
    }

    public static String getLoginName() {
        return loginName;
    }

    public static String getPassword() {
        return password;
    }

    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    public void endTransaction() {
        entityManager.getTransaction().commit();
    }

    public void save(Object entity) {
        beginTransaction();
        entityManager.persist(entity);
        endTransaction();
    }

    public void remove(Object entity) {
        beginTransaction();
        entityManager.remove(entity);
        endTransaction();
    }

    public void rollBack(){
        entityManager.getTransaction().rollback();
    }

    public <T> List<T> getAllObjects(Class<T> objectClass) {
        beginTransaction();
        TypedQuery<T> userQuery = entityManager.createQuery("Select x from " + objectClass.getName() + " x",
                objectClass);
        List<T> result = userQuery.getResultList();
        endTransaction();
        return result;
    }

    public <T> List<T> getAllRefreshedObjects(Class<T> objectClass) {
        beginTransaction();
        TypedQuery<T> userQuery = entityManager.createQuery("Select x from " + objectClass.getName() + " x",
                objectClass);
        List<T> objects = userQuery.getResultList();

        for (T object : objects) {
            entityManager.refresh(object);
        }
        endTransaction();
        return objects;
    }

    public <T> T findByID(Class<T> objectClass, int id) {
        beginTransaction();
        T result = entityManager.find(objectClass, id);
        endTransaction();
        return result;
    }

    public List<Paid> getPaid(int interval) {
        beginTransaction();
        Query nativeQuery = entityManager.createNativeQuery("execute GetPaidDuties ?1",
                "paid");
        nativeQuery.setParameter(1, interval);
        List<Paid> result = nativeQuery.getResultList();
        endTransaction();
        return result;
    }

    public List<CertainResult> getCertainResult(LocalDate beginDate, LocalDate endDate, String result) {
        beginTransaction();
        Query nativeQuery = entityManager.createNativeQuery("execute GetResults ?1, ?2, ?3",
                "certainResult");
        nativeQuery.setParameter(1, beginDate);
        nativeQuery.setParameter(2, endDate);
        nativeQuery.setParameter(3, result);
        List<CertainResult> resultQuery = nativeQuery.getResultList();
        endTransaction();
        return resultQuery;
    }

    public List<MissingDocument> getMissingDocuments(int numberProcedure) {
        beginTransaction();
        Query nativeQuery = entityManager.createNativeQuery("execute GetMissingDocuments ?1",
                "missingDocument");
        nativeQuery.setParameter(1, numberProcedure);
        List<MissingDocument> resultQuery = nativeQuery.getResultList();
        endTransaction();
        return resultQuery;
    }
}
