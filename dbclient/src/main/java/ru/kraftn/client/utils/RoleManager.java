package ru.kraftn.client.utils;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.kraftn.client.models.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class RoleManager {
    private static RoleManager instance = null;

    private List<UserInformation> informationAllUsers = null;
    private EntityManager entityManager;

    private RoleManager() {
        SessionFactory sessionFactory = buildSessionFactory();
        entityManager = sessionFactory.createEntityManager();
    }

    public static RoleManager getInstance() {
        if (instance == null) {
            instance = new RoleManager();
        }
        return instance;
    }

    private SessionFactory buildSessionFactory() throws Error {
        Properties dbConnectionProperties = new Properties();
        try {
            dbConnectionProperties.load(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate_role_checker.properties")
            );
        } catch (IOException e) {
            throw new Error("Cannot read hibernate properties");
        }

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Unit.class);

        configuration.addProperties(dbConnectionProperties);

        return configuration.buildSessionFactory();
    }

    //SQL: declare @x nvarchar(128); set @x = (select USER_NAME()); execute sp_helpuser @x;
    public String findRoleName() {
        String loginName = HibernateManager.getLoginName();
        if (null == informationAllUsers) {
            setInformationAllUsers();
        }
        int i = 0;

        while (i < informationAllUsers.size() && !loginName.equals(informationAllUsers.get(i).getLoginName())) {
            i++;
        }
        if (i == informationAllUsers.size()) {
            throw new RuntimeException(String.format("Пользователя с логином %s не существует", loginName));
        } else {
            return informationAllUsers.get(i).getRoleName();
        }
    }

    public String findUserName() {
        String loginName = HibernateManager.getLoginName();
        if (null == informationAllUsers) {
            setInformationAllUsers();
        }
        int i = 0;
        while (i < informationAllUsers.size() && !loginName.equals(informationAllUsers.get(i).getLoginName())) {
            i++;
        }
        if (i == informationAllUsers.size()) {
            throw new RuntimeException(String.format("Пользователя с логином %s не существует", loginName));
        } else {
            return informationAllUsers.get(i).getUserName();
        }
    }

    private void setInformationAllUsers() {
        entityManager.getTransaction().begin();
        Query nativeQuery = entityManager.createNativeQuery("execute sp_helpuser;",
                "userInformation");
        informationAllUsers = nativeQuery.getResultList();
        entityManager.getTransaction().commit();
    }

    public void addClassesToHibernate(Configuration configuration) {
        String roleName = findRoleName();
        switch (roleName) {
            case "Inspector":
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
                break;
            case "Operator":
                configuration.addAnnotatedClass(Document.class);
                configuration.addAnnotatedClass(Cooperator.class);
                configuration.addAnnotatedClass(Duty.class);
                configuration.addAnnotatedClass(Unit.class);
                configuration.addAnnotatedClass(CategoryOfGood.class);
                break;
            case "Declarant":
                configuration.addAnnotatedClass(ResultOfProcedure.class);
                configuration.addAnnotatedClass(Cooperator.class);
                break;
        }
    }

    public void setDisableMenuBar(MenuBar menuBar) {
        String roleName = findRoleName();
        switch (roleName) {
            case "Inspector":
                break;
            case "Operator":
                menuBar.getMenus().get(0).getItems().get(0).setDisable(true);
                menuBar.getMenus().get(0).getItems().get(1).setDisable(true);
                menuBar.getMenus().get(0).getItems().get(3).setDisable(true);
                menuBar.getMenus().get(1).setDisable(true);
                break;
            case "Declarant":
                menuBar.getMenus().get(0).getItems().get(0).setDisable(true);
                menuBar.getMenus().get(0).getItems().get(1).setDisable(true);
                menuBar.getMenus().get(0).getItems().get(4).setDisable(true);
                ((Menu) menuBar.getMenus().get(0).getItems().get(3)).getItems().get(0).setDisable(true);
                ((Menu) menuBar.getMenus().get(0).getItems().get(3)).getItems().get(1).setDisable(true);
                ((Menu) menuBar.getMenus().get(0).getItems().get(3)).getItems().get(2).setDisable(true);
                menuBar.getMenus().get(1).setDisable(true);
                break;
            default:
                menuBar.getMenus().get(0).setDisable(true);
                menuBar.getMenus().get(1).setDisable(true);
                break;
        }
    }

    public boolean isContextMenuNecessary(Class<?> elementContentClass) {
        String roleName = findRoleName();
        boolean result = false;
        switch (roleName) {
            case "Operator":
                result = true;
                break;
            case "Inspector":
                if (elementContentClass.equals(Declarant.class) || elementContentClass.equals(Good.class)
                        || elementContentClass.equals(CustomsProcedure.class)
                        || elementContentClass.equals(ResultOfProcedure.class)
                        || elementContentClass.equals(SubmittedDocument.class)
                        || elementContentClass.equals(SubmittedDuty.class)) {
                    result = true;
                }
                break;
        }
        return result;
    }
}
