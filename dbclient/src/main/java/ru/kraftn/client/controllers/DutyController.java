package ru.kraftn.client.controllers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.service.spi.ServiceException;
import ru.kraftn.client.models.*;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;
import ru.kraftn.client.utils.InflateUtils;
import ru.kraftn.client.utils.TableManager;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DutyController implements Initializable {
    @FXML
    private ComboBox cbValue;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfValue;

    public Duty data;
    private HibernateManager hibernate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hibernate = HibernateManager.getInstance();

        List<Unit> units = hibernate.getAllObjects(Unit.class);
        ObservableList<Unit> observableListUnits =
                FXCollections.observableArrayList(units);
        cbValue.setItems(observableListUnits);

        if (data != null) {
            cbValue.getSelectionModel().select(data.getUnitOfMeasurement());
            tfName.setText(data.getName());
            tfValue.setText(String.format("%.2f", data.getValueOfDuty()));
        }
    }

    @FXML
    public void ok() {
        if (null == data) {
            data = new Duty();
        }

        data.setName(tfName.getText());
        NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
        try {
            data.setValueOfDuty(nf.parse(tfValue.getText()).doubleValue());
        } catch (ParseException e) {
            InflateUtils.createAndShowAlert("Неверная ставка пошлины");
            return;
        }
        data.setUnitOfMeasurement((Unit) cbValue.getValue());

        try {
            hibernate.save(data);
        } catch (PersistenceException e) {
            Throwable errorNext = e;
            while (null != errorNext.getCause()) {
                errorNext = errorNext.getCause();
            }
            if (!(errorNext instanceof SQLServerException)) {
                InflateUtils.createAndShowAlert("Заполните все обязательные поля (без *)");
                hibernate.rollBack();
            } else if (errorNext.getMessage().equals("Транзакция завершилась в триггере. Выполнение пакета прервано.")) {
                InflateUtils.createAndShowAlert(
                        "Отрицательная ставка пошлины");
            } else {
                InflateUtils.createAndShowAlert(errorNext.getMessage());
            }

            data = hibernate.findByID(Duty.class, data.getId());

            return;
        }

        TableView<Duty> table = TableManager.getTableDuties();
        NavigationManager.from(tfName).goToTableScene(table, "Пошлины");
    }

    @FXML
    public void cancel() {
        TableView<Duty> table = TableManager.getTableDuties();
        NavigationManager.from(tfName).goToTableScene(table, "Пошлины");
    }
}
