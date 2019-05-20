package ru.kraftn.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.hibernate.service.spi.ServiceException;
import ru.kraftn.client.models.*;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;
import ru.kraftn.client.utils.InflateUtils;
import ru.kraftn.client.utils.TableManager;

import javax.persistence.PersistenceException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SubmittedDutyController implements Initializable {
    @FXML
    private ComboBox cbCustomsProcedure;
    @FXML
    private ComboBox cbDuty;
    @FXML
    private DatePicker dpDatePaid;
    @FXML
    private CheckBox checkPaid;

    public SubmittedDuty data;
    private HibernateManager hibernate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hibernate = HibernateManager.getInstance();

        List<CustomsProcedure> contentCustomsProcedures = hibernate.getAllObjects(CustomsProcedure.class);
        ObservableList<CustomsProcedure> observableListCustomsProcedures =
                FXCollections.observableArrayList(contentCustomsProcedures);
        cbCustomsProcedure.setItems(observableListCustomsProcedures);

        List<Duty> contentDuties = hibernate.getAllObjects(Duty.class);
        ObservableList<Duty> observableListDuties = FXCollections.observableArrayList(contentDuties);
        cbDuty.setItems(observableListDuties);

        if (data != null) {
            cbCustomsProcedure.getSelectionModel().select(data.getCustomsProcedure());
            cbDuty.getSelectionModel().select(data.getDuty());
            checkPaid.setSelected(data.isPaid());
            if (data.isPaid()){
                dpDatePaid.setValue(data.getPaymentDate());
                dpDatePaid.setDisable(false);
            }
        }
    }

    @FXML
    public void ok() {
        if (checkPaid.isSelected() && null == dpDatePaid.getValue()){
            InflateUtils.createAndShowAlert("Укажите дату оплаты");
            return;
        }

        if (null == data) {
            data = new SubmittedDuty();
        }
        data.setCustomsProcedure((CustomsProcedure)cbCustomsProcedure.getValue());
        data.setDuty((Duty) cbDuty.getValue());
        data.setPaid(checkPaid.isSelected() ? 1 : 0);
        data.setPaymentDate(dpDatePaid.getValue());

        try {
            hibernate.save(data);
        } catch (PersistenceException e) {
            InflateUtils.createAndShowAlert("Заполните все обязательные поля (без *)");
            hibernate.rollBack();
            data = hibernate.findByID(SubmittedDuty.class, data.getId());

            return;
        }

        TableView<SubmittedDuty> table = TableManager.getTableSubmittedDuties();
        NavigationManager.from(cbCustomsProcedure).goToTableScene(table, "Пошлины, подлежащие оплате");
    }

    @FXML
    public void cancel() {
        TableView<SubmittedDuty> table = TableManager.getTableSubmittedDuties();
        NavigationManager.from(cbCustomsProcedure).goToTableScene(table, "Пошлины, подлежащие оплате");
    }

    @FXML
    public void pick(){
        if (dpDatePaid.isDisabled()){
            dpDatePaid.setDisable(false);
        } else {
            dpDatePaid.setValue(null);
            dpDatePaid.setDisable(true);
        }
    }
}
