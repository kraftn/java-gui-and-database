package ru.overtired.javafx.sample3.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import ru.overtired.javafx.sample3.models.CategoryOfGoods;
import ru.overtired.javafx.sample3.models.Goods;
import ru.overtired.javafx.sample3.models.Unpaid;
import ru.overtired.javafx.sample3.utils.HibernateManager;

import java.util.List;

public class MainController {

    @FXML
    private VBox vboxLayout;

    @FXML
    private Label label;

    @FXML
    private TextArea textArea;

    public MainController() {
    }

    @FXML
    private void load() {
        HibernateManager instance = HibernateManager.getInstance();
        List<Unpaid> goodsList = instance.getUnpaid();
        textArea.clear();
        for (Unpaid x : goodsList) {
            textArea.appendText(x.toString() + "\n");
        }
    }
}
