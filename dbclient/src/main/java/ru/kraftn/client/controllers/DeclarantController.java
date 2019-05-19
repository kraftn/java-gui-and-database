package ru.kraftn.client.controllers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.service.spi.ServiceException;
import ru.kraftn.client.models.CustomsProcedure;
import ru.kraftn.client.models.Declarant;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;
import ru.kraftn.client.utils.InflateUtils;
import ru.kraftn.client.utils.TableManager;

import javax.persistence.PersistenceException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeclarantController implements Initializable {
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPatronymic;
    @FXML
    private TextField tfNationality;
    @FXML
    private TextField tfTypeDocument;
    @FXML
    private TextField tfNumberDocument;
    @FXML
    private TextField tfPhone;

    public Declarant data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (data != null){
            tfSurname.setText(data.getSurname());
            tfName.setText(data.getName());
            tfPatronymic.setText(data.getPatronymic());
            tfNationality.setText(data.getNationality());
            tfTypeDocument.setText(data.getTypeOfIdentifyDocument());
            tfNumberDocument.setText(data.getDocumentNumber());
            tfPhone.setText(data.getContactPhone());
        }
    }

    @FXML
    public void ok(){
        if (null == data){
            data = new Declarant();
        }
        data.setName(tfName.getText());
        data.setSurname(tfSurname.getText());
        data.setPatronymic(tfPatronymic.getText());
        data.setNationality(tfNationality.getText());
        data.setTypeOfIdentifyDocument(tfTypeDocument.getText());
        data.setDocumentNumber(tfNumberDocument.getText());
        data.setContactPhone(tfPhone.getText());

        HibernateManager hibernate = HibernateManager.getInstance();

        try {
            hibernate.save(data);
        } catch (PersistenceException e) {
            Throwable errorNext = e;
            while (null != errorNext.getCause()) {
                errorNext = errorNext.getCause();
            }
            if (errorNext instanceof SQLServerException
                    && errorNext.getMessage().equals("Транзакция завершилась в триггере. Выполнение пакета прервано.")) {
                InflateUtils.createAndShowAlert(
                        "Декларант с таким документом уже существует");
            } else {
                InflateUtils.createAndShowAlert(e.getMessage());
            }

            data = hibernate.findByID(Declarant.class, data.getId());

            return;
        }

        TableView<Declarant> table = TableManager.getTableWithContentAndMenu(TableManager.headerDeclarant, Declarant.class);
        NavigationManager.from(tfSurname).goToTableScene(table, "Декларанты");
    }

    @FXML
    public void cancel(){
        HibernateManager hibernate = HibernateManager.getInstance();
        TableView<Declarant> table = TableManager.getTableWithContentAndMenu(TableManager.headerDeclarant, Declarant.class);
        NavigationManager.from(tfSurname).goToTableScene(table, "Декларанты");
    }
}
