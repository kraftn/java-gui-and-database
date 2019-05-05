package ru.kraftn.client.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.kraftn.client.models.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class HibernateManager {
    private static HibernateManager instance = null;
    private static String loginName;
    private static String password;

    private EntityManager entityManager;
    private UserInformation userInformation = null;

    private HibernateManager() {
        SessionFactory sessionFactory = buildSessionFactory();
        entityManager = sessionFactory.createEntityManager();
    }

    public static HibernateManager getInstance() {
        if (instance == null) {
            if (loginName.isEmpty() || password.isEmpty()){
                throw new RuntimeException("Empty username or login");
            }

            instance = new HibernateManager();
        }
        return instance;
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
        configuration.addAnnotatedClass(Good.class);
        configuration.addAnnotatedClass(Document.class);
        configuration.addAnnotatedClass(Cooperator.class);
        configuration.addAnnotatedClass(CustomsProcedure.class);
        configuration.addAnnotatedClass(Declarant.class);
        configuration.addAnnotatedClass(Duty.class);
        configuration.addAnnotatedClass(ResultOfProcedure.class);
        configuration.addAnnotatedClass(SubmittedDocument.class);
        configuration.addAnnotatedClass(SubmittedDuty.class);
        configuration.addAnnotatedClass(Unit.class);
        configuration.addAnnotatedClass(CategoryOfGood.class);

        configuration.addProperties(dbConnectionProperties);

        return configuration.buildSessionFactory();
    }

    public static void close(){
        instance = null;
    }

    public static void setLoginName(String newLoginName) {
        loginName = newLoginName;
    }

    public static void setPassword(String newPassword) {
        password = newPassword;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public String getRoleName(){
        if (null == userInformation){
            userInformation = getUserInformation().get(0);
        }
        return userInformation.getRoleName();
    }

    public String getUserName(){
        if (null == userInformation){
            userInformation = getUserInformation().get(0);
        }
        return userInformation.getUserName();
    }

    public List<UserInformation> getUserInformation() {
        Query nativeQuery = entityManager.createNativeQuery(
                "declare @x nvarchar(128); set @x = (select USER_NAME()); execute sp_helpuser @x;",
                "userInformation");
        return nativeQuery.getResultList();
    }

    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    public void endTransaction() {
        entityManager.getTransaction().commit();
    }

    public void save(Object entity) {
       entityManager.persist(entity);
    }

    public void remove(Object entity){
        entityManager.remove(entity);
    }

    public <T> List<T> getAllObjects(Class<T> objectClass){
        TypedQuery<T> userQuery = entityManager.createQuery("Select x from " + objectClass.getName() + " x",
                objectClass);
        return userQuery.getResultList();
    }

    /*public Good getGood(){
        return entityManager.find(Good.class, 1);
    }*/

    public List<Unpaid> getUnpaid() {
        Query nativeQuery = entityManager.createNativeQuery("execute GetNotPaidDuties", "unpaid");
        return nativeQuery.getResultList();
    }
}
