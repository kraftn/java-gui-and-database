package ru.kraftn.client.controllers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.hibernate.service.spi.ServiceException;
import ru.kraftn.client.models.CategoryOfGood;
import ru.kraftn.client.models.Document;
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

public class CategoryOfGoodController implements Initializable {
    @FXML
    private TextField tfName;
    @FXML
    private TextArea taDescription;
    @FXML
    private ListView<Document> lvDocuments;

    public CategoryOfGood data;
    private HibernateManager hibernate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hibernate = HibernateManager.getInstance();

        lvDocuments.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<Document> documents = hibernate.getAllObjects(Document.class);
        ObservableList<Document> observableListDocuments = FXCollections.observableArrayList(documents);
        lvDocuments.setItems(observableListDocuments);

        if (data != null) {
            tfName.setText(data.getName());
            taDescription.setText(data.getDescription());
            for (Document document : data.getDocuments()) {
                lvDocuments.getSelectionModel().select(document);
            }
        }
    }

    @FXML
    public void ok() {
        if (null == data) {
            data = new CategoryOfGood();
        }

        data.setName(tfName.getText());
        data.setDescription(taDescription.getText());
        data.getDocuments().clear();
        for (Document document : lvDocuments.getSelectionModel().getSelectedItems()) {
            data.getDocuments().add(document);
        }

        hibernate.save(data);

        TableView<CategoryOfGood> table = TableManager.getTableCategoriesOfGoods();
        NavigationManager.from(tfName).goToTableScene(table, "Категории грузов");
    }

    @FXML
    public void cancel() {
        TableView<CategoryOfGood> table = TableManager.getTableCategoriesOfGoods();
        NavigationManager.from(tfName).goToTableScene(table, "Категории грузов");
    }
}
