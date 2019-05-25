package ru.kraftn.client.controllers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.service.spi.ServiceException;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;
import ru.kraftn.client.utils.InflateUtils;

public class LoginController {
    @FXML
    TextField tfLogin;
    @FXML
    PasswordField pfPassword;

    @FXML
    private void enter() {
        String loginName = tfLogin.getText();
        String password = pfPassword.getText();
        if (loginName.isEmpty() || password.isEmpty()) {
            InflateUtils.createAndShowAlert("Введите логин и пароль.");
            return;
        }
        HibernateManager.setLoginName(loginName);
        HibernateManager.setPassword(password);
        try {
            HibernateManager.getInstance();
        }
        catch (ServiceException e){
            Throwable errorNext = e.getCause();
            while (!(errorNext instanceof SQLServerException)) {
                errorNext = errorNext.getCause();
            }
            InflateUtils.createAndShowAlert(errorNext.getMessage());
            return;
        }
        catch (RuntimeException e) {
            InflateUtils.createAndShowAlert(e.getMessage());
            return;
        }
        NavigationManager.from(tfLogin).goToBlankScene();
    }
}
