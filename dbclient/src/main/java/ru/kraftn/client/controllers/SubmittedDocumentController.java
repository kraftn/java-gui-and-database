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

public class SubmittedDocumentController implements Initializable {
    @FXML
    private ComboBox cbCustomsProcedure;
    @FXML
    private ComboBox cbTypeDocument;
    @FXML
    private TextField tfNumberDocument;

    public SubmittedDocument data;
    private HibernateManager hibernate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hibernate = HibernateManager.getInstance();

        List<CustomsProcedure> contentCustomsProcedures = hibernate.getAllObjects(CustomsProcedure.class);
        ObservableList<CustomsProcedure> observableListCustomsProcedures =
                FXCollections.observableArrayList(contentCustomsProcedures);
        cbCustomsProcedure.setItems(observableListCustomsProcedures);

        List<Document> contentDocuments = hibernate.getAllObjects(Document.class);
        ObservableList<Document> observableListDocuments = FXCollections.observableArrayList(contentDocuments);
        cbTypeDocument.setItems(observableListDocuments);

        if (data != null) {
            cbCustomsProcedure.getSelectionModel().select(data.getProcedure());
            cbTypeDocument.getSelectionModel().select(data.getDocument());
            tfNumberDocument.setText(data.getDocumentNumber());
        }
    }

    @FXML
    public void ok() {
        if (null == data) {
            data = new SubmittedDocument();
        }
        data.setProcedure((CustomsProcedure)cbCustomsProcedure.getValue());
        data.setDocument((Document) cbTypeDocument.getValue());
        data.setDocumentNumber(tfNumberDocument.getText());

        try {
            hibernate.save(data);
        } catch (PersistenceException e) {
            InflateUtils.createAndShowAlert("Заполните все обязательные поля (без *)");
            hibernate.rollBack();
            data = hibernate.findByID(SubmittedDocument.class, data.getId());

            return;
        }

        TableView<SubmittedDocument> table = TableManager.getTableWithContentAndMenu(TableManager.headerSubmittedDocument,
                SubmittedDocument.class);
        NavigationManager.from(cbCustomsProcedure).goToTableScene(table, "Поданные документы");
    }

    @FXML
    public void cancel() {
        TableView<SubmittedDocument> table = TableManager.getTableWithContentAndMenu(TableManager.headerSubmittedDocument,
                SubmittedDocument.class);
        NavigationManager.from(cbCustomsProcedure).goToTableScene(table, "Поданные документы");
    }
}
