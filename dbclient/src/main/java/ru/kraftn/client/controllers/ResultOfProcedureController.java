package ru.kraftn.client.controllers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ru.kraftn.client.models.*;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;
import ru.kraftn.client.utils.InflateUtils;
import ru.kraftn.client.utils.TableManager;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResultOfProcedureController implements Initializable {
    @FXML
    private ComboBox cbCustomsProcedure;
    @FXML
    private ComboBox cbCooperator;
    @FXML
    private DatePicker dpDateResult;
    @FXML
    private TextField tfResult;

    public ResultOfProcedure data;
    private HibernateManager hibernate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hibernate = HibernateManager.getInstance();

        List<CustomsProcedure> contentCustomsProcedures = hibernate.getAllObjects(CustomsProcedure.class);
        ObservableList<CustomsProcedure> observableListCustomsProcedures =
                FXCollections.observableArrayList(contentCustomsProcedures);
        cbCustomsProcedure.setItems(observableListCustomsProcedures);

        List<Cooperator> contentCooperators = hibernate.getAllObjects(Cooperator.class);
        ObservableList<Cooperator> observableListCooperators = FXCollections.observableArrayList(contentCooperators);
        cbCooperator.setItems(observableListCooperators);

        if (data != null) {
            cbCustomsProcedure.setDisable(true);

            cbCustomsProcedure.getSelectionModel().select(hibernate.findByID(CustomsProcedure.class, data.getId()));
            cbCooperator.getSelectionModel().select(data.getCooperator());
            tfResult.setText(data.getResult());
            dpDateResult.setValue(data.getDateOfResult());
        }
    }

    @FXML
    public void ok() {
        if (null == data) {
            if (null == cbCustomsProcedure.getValue()) {
                InflateUtils.createAndShowAlert("Выберите заявление.");
                return;
            }

            data = new ResultOfProcedure();
            data.setId(((CustomsProcedure) cbCustomsProcedure.getValue()).getId());
        }
        data.setCooperator((Cooperator) cbCooperator.getValue());
        data.setResult(tfResult.getText());
        data.setDateOfResult(dpDateResult.getValue());

        try {
            hibernate.save(data);
        } catch (EntityExistsException e) {
            hibernate.rollBack();
            data = null;
            InflateUtils.createAndShowAlert(
                    "Результат по данному заявлению уже существует.");
            return;
        } catch (PersistenceException e) {
            Throwable errorNext = e;
            while (null != errorNext.getCause()) {
                errorNext = errorNext.getCause();
            }
            if (!(errorNext instanceof SQLServerException)) {
                InflateUtils.createAndShowAlert("Заполните все обязательные поля (без *).");
                hibernate.rollBack();
            } else if (errorNext.getMessage().contains(
                    "Не удается вставить повторяющийся ключ в объект \"dbo.Result_Of_Procedure\".")) {
                InflateUtils.createAndShowAlert(
                        "Результат по данному заявлению уже существует.");
            } else if (errorNext.getMessage().equals("Транзакция завершилась в триггере. Выполнение пакета прервано.")
                    || errorNext.getMessage().equals("Результирующий набор создан для обновления.")) {
                InflateUtils.createAndShowAlert(
                        "Данное заявление не может быть одобрено, так как по нему имеются неоплаченные пошлины или недостающие документы.");
            } else {
                InflateUtils.createAndShowAlert(errorNext.getMessage());
            }

            if (cbCustomsProcedure.isDisabled()) {
                data = hibernate.findByID(ResultOfProcedure.class, data.getId());
            } else {
                data = null;
            }

            return;
        }

        TableView<ResultOfProcedure> table = TableManager.getTableResults();
        NavigationManager.from(cbCustomsProcedure).goToTableScene(table, "Результаты рассмотрения");
    }

    @FXML
    public void cancel() {
        TableView<ResultOfProcedure> table = TableManager.getTableResults();
        NavigationManager.from(cbCustomsProcedure).goToTableScene(table, "Результаты рассмотрения");
    }
}
