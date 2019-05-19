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

public class CustomsProcedureController implements Initializable {
    private static final String formatDouble = "%.2f";

    @FXML
    private TextField tfAmount;
    @FXML
    private TextField tfWorth;
    @FXML
    private TextField tfTypeOfProcedure;
    @FXML
    private ComboBox cbDeclarant;
    @FXML
    private ComboBox cbGood;
    @FXML
    private ComboBox cbUnit;

    public CustomsProcedure data;
    private HibernateManager hibernate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hibernate = HibernateManager.getInstance();

        List<Declarant> contentDeclarants = hibernate.getAllObjects(Declarant.class);
        ObservableList<Declarant> observableListDeclarants = FXCollections.observableArrayList(contentDeclarants);
        cbDeclarant.setItems(observableListDeclarants);

        List<Good> contentGoods = hibernate.getAllObjects(Good.class);
        ObservableList<Good> observableListGoods = FXCollections.observableArrayList(contentGoods);
        cbGood.setItems(observableListGoods);

        List<Unit> contentUnits = hibernate.getAllObjects(Unit.class);
        ObservableList<Unit> observableListUnits = FXCollections.observableArrayList(contentUnits);
        cbUnit.setItems(observableListUnits);

        if (data != null) {
            tfAmount.setText(String.format(formatDouble, data.getAmountOfGoods()));
            tfWorth.setText(String.format(formatDouble, data.getWorthOfGoods()));
            tfTypeOfProcedure.setText(data.getTypeOfProcedure());
            cbDeclarant.getSelectionModel().select(data.getDeclarant());
            cbGood.getSelectionModel().select(data.getGood());
            cbUnit.getSelectionModel().select(data.getUnitOfMeasurement());
        }
    }

    @FXML
    public void ok() {
        if (null == data) {
            data = new CustomsProcedure();
        }

        NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
        try {
            data.setAmountOfGoods(nf.parse(tfAmount.getText()).doubleValue());
            data.setWorthOfGoods(nf.parse(tfWorth.getText()).doubleValue());
        } catch (ParseException e) {
            InflateUtils.createAndShowAlert("Неверное количество груза или его стоимость");
            return;
        }

        data.setTypeOfProcedure(tfTypeOfProcedure.getText());
        data.setDeclarant((Declarant) cbDeclarant.getValue());
        data.setGood((Good) cbGood.getValue());
        data.setUnitOfMeasurement((Unit) cbUnit.getValue());

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
                        "Отрицательное количество груза или его стоимость");
            } else {
                InflateUtils.createAndShowAlert(errorNext.getMessage());
            }

            data = hibernate.findByID(CustomsProcedure.class, data.getId());

            return;
        }

        TableView<CustomsProcedure> table = TableManager.getTableCustomsProcedures();
        NavigationManager.from(cbDeclarant).goToTableScene(table, "Заявления");
    }

    @FXML
    public void cancel() {
        TableView<CustomsProcedure> table = TableManager.getTableCustomsProcedures();
        NavigationManager.from(cbDeclarant).goToTableScene(table, "Заявления");
    }
}
