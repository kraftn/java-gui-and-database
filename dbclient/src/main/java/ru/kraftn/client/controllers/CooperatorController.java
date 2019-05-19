package ru.kraftn.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.hibernate.service.spi.ServiceException;
import ru.kraftn.client.models.CategoryOfGood;
import ru.kraftn.client.models.Cooperator;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;
import ru.kraftn.client.utils.InflateUtils;
import ru.kraftn.client.utils.TableManager;

import javax.persistence.PersistenceException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CooperatorController implements Initializable {
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
    public void ok(){
        if (null == data){
            data = new Cooperator();
        }
        data.setName(tfName.getText());
        data.setSurname(tfSurname.getText());
        data.setPatronymic(tfPatronymic.getText());
        data.setPosition(tfPosition.getText());

        HibernateManager hibernate = HibernateManager.getInstance();

        hibernate.save(data);

        TableView<Cooperator> table = TableManager.getTableWithContentAndMenu(TableManager.headerCooperator, Cooperator.class);
        NavigationManager.from(tfSurname).goToTableScene(table, "Таможенные инмпекторы");
    }

    @FXML
    public void cancel(){
        HibernateManager hibernate = HibernateManager.getInstance();
        TableView<Cooperator> table = TableManager.getTableWithContentAndMenu(TableManager.headerCooperator, Cooperator.class);
        NavigationManager.from(tfSurname).goToTableScene(table, "Таможенные инмпекторы");
    }
}
