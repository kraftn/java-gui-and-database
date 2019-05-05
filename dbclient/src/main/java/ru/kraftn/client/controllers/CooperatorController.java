package ru.kraftn.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.kraftn.client.models.Cooperator;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;
import ru.kraftn.client.utils.TableManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CooperatorController implements Initializable {
    @FXML
    private GridPane gpLayout;
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPatronymic;
    @FXML
    private TextField tfPosition;

    public Cooperator data;

    public CooperatorController(){}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (data != null){
            tfSurname.setText(data.getSurname());
            tfName.setText(data.getName());
            tfPatronymic.setText(data.getPatronymic());
            tfPosition.setText(data.getPosition());
        }
    }

    @FXML
    public void onOk(){
        if (null == data){
            data = new Cooperator();
        }
        data.setName(tfName.getText());
        data.setSurname(tfSurname.getText());
        data.setPatronymic(tfPatronymic.getText());
        data.setPosition(tfPosition.getText());

        HibernateManager hibernate = HibernateManager.getInstance();

        hibernate.beginTransaction();
        hibernate.save(data);
        hibernate.endTransaction();

        List<Cooperator> content = hibernate.getAllObjects(Cooperator.class);
        TableView<Cooperator> table = TableManager.getTableWithContent(TableManager.headerCooperator, Cooperator.class,
                content);
        NavigationManager.from(gpLayout).goToTableScene(table);
    }

    @FXML
    public void onCancel(){
        HibernateManager hibernate = HibernateManager.getInstance();
        List<Cooperator> content = hibernate.getAllObjects(Cooperator.class);
        TableView<Cooperator> table = TableManager.getTableWithContent(TableManager.headerCooperator, Cooperator.class,
                content);
        NavigationManager.from(gpLayout).goToTableScene(table);
    }
}
