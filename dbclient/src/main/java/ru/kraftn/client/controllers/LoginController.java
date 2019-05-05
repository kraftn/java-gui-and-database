package ru.kraftn.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.kraftn.client.models.*;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginController {
    @FXML
    TextField tfLogin;

    @FXML
    PasswordField pfPassword;

    @FXML
    private void enter() {
        String loginName = tfLogin.getText();
        HibernateManager.setLoginName(loginName);
        String password = pfPassword.getText();
        HibernateManager.setPassword(password);
        HibernateManager.getInstance();
        NavigationManager.from(tfLogin).goToBlankScene();
    }
}
