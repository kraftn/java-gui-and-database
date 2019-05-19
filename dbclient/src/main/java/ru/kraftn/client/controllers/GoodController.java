package ru.kraftn.client.controllers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.service.spi.ServiceException;
import ru.kraftn.client.models.CategoryOfGood;
import ru.kraftn.client.models.Duty;
import ru.kraftn.client.models.Good;
import ru.kraftn.client.models.ResultOfProcedure;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;
import ru.kraftn.client.utils.InflateUtils;
import ru.kraftn.client.utils.TableManager;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GoodController implements Initializable {
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfCountry;
    @FXML
    private ComboBox cbCategory;

    public Good data;
    private HibernateManager hibernate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hibernate = HibernateManager.getInstance();
        List<CategoryOfGood> content = hibernate.getAllObjects(CategoryOfGood.class);
        ObservableList<CategoryOfGood> observableList = FXCollections.observableArrayList(content);
        cbCategory.setItems(observableList);

        if (data != null) {
            tfName.setText(data.getName());
            tfCountry.setText(data.getCountryOfOrigin());
            cbCategory.getSelectionModel().select(data.getCategory());
        }
    }

    @FXML
    public void ok() {
        if (null == data) {
            data = new Good();
        }
        data.setName(tfName.getText());
        data.setCountryOfOrigin(tfCountry.getText());
        data.setCategory((CategoryOfGood) cbCategory.getValue());

        try {
            hibernate.save(data);
        } catch (PersistenceException e) {
            InflateUtils.createAndShowAlert("Заполните все обязательные поля (без *)");
            hibernate.rollBack();
            data = hibernate.findByID(Good.class, data.getId());

            return;
        }

        TableView<Good> table = TableManager.getTableWithContentAndMenu(TableManager.headerGood, Good.class);
        NavigationManager.from(tfName).goToTableScene(table, "Грузы");
    }

    @FXML
    public void cancel() {
        TableView<Good> table = TableManager.getTableWithContentAndMenu(TableManager.headerGood, Good.class);
        NavigationManager.from(tfName).goToTableScene(table, "Грузы");
    }
}
