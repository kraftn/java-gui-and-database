package ru.kraftn.client;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.kraftn.client.navigation.NavigationManager;

import java.io.IOException;

public class StartApp extends Application {

    public static void main(String[] args) {
        StartApp.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.goToLoginScene();
        stage.show();
    }
}

