package ru.kraftn.client.navigation;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ru.kraftn.client.controllers.BlankController;
import ru.kraftn.client.utils.AbleToGiveId;
import ru.kraftn.client.utils.InflateUtils;

import java.lang.reflect.InvocationTargetException;

public class NavigationManager {

    private Stage stage;

    public NavigationManager(Stage stage) {
        this.stage = stage;
    }

    public static NavigationManager from(Node node) {
        return (NavigationManager) node.getScene().getUserData();
    }

    public void goToLoginScene() {
        final Parent root = InflateUtils.loadFxml("/fxml/login_controller.fxml");
        final Scene scene = createSceneWithThisNavigationManager(root);
        stage.setScene(scene);
    }

    public void goToBlankScene() {
        final Parent root = InflateUtils.loadFxml("/fxml/blank_controller.fxml");
        final Scene scene = createSceneWithThisNavigationManager(root);
        stage.setScene(scene);
    }

    public void goToTableScene(TableView table) {
        BlankController blankController = new BlankController(table);
        final Parent root = InflateUtils.loadFxmlWithCustomController("/fxml/blank_controller.fxml", blankController);
        final Scene scene = createSceneWithThisNavigationManager(root);
        stage.setScene(scene);
    }

    public void goToChangeScene(Class<? extends AbleToGiveId> contentClass, AbleToGiveId contentInstance) {
        String fullClassName = contentClass.getName();
        String shortClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

        Object controller = null;
        try {
            Class controllerClass = Class.forName("ru.kraftn.client.controllers." + shortClassName + "Controller");
            controller = controllerClass.newInstance();
            controllerClass.getDeclaredField("data").set(controller, contentInstance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        final Parent root = InflateUtils.loadFxmlWithCustomController("/fxml/"+shortClassName+"_controller.fxml",
                controller);
        final Scene scene = createSceneWithThisNavigationManager(root);
        stage.setScene(scene);
    }

    public void goToCreateScene(Class<? extends AbleToGiveId> contentClass) {
        String fullClassName = contentClass.getName();
        String shortClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

        final Parent root = InflateUtils.loadFxml("/fxml/"+shortClassName+"_controller.fxml");
        final Scene scene = createSceneWithThisNavigationManager(root);
        stage.setScene(scene);
    }

    private Scene createSceneWithThisNavigationManager(Parent root) {
        final Scene scene = new Scene(root);
        scene.setUserData(this);
        return scene;
    }
}
