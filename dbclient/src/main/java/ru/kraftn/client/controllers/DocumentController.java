package ru.kraftn.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.service.spi.ServiceException;
import ru.kraftn.client.models.CategoryOfGood;
import ru.kraftn.client.models.CustomsProcedure;
import ru.kraftn.client.models.Document;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;
import ru.kraftn.client.utils.InflateUtils;
import ru.kraftn.client.utils.TableManager;

import javax.persistence.PersistenceException;
import java.net.URL;
import java.util.ResourceBundle;

public class DocumentController implements Initializable {
    @FXML
    private TextField tfName;

    public Document data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (data != null){
            tfName.setText(data.getName());
        }
    }

    @FXML
    public void ok(){
        if (null == data){
            data = new Document();
        }

        data.setName(tfName.getText());

        HibernateManager.getInstance().save(data);

        TableView<Document> table = TableManager.getTableWithContentAndMenu(TableManager.headerDocument,
                Document.class);
        NavigationManager.from(tfName).goToTableScene(table, "Документы");
    }

    @FXML
    public void cancel(){
        TableView<Document> table = TableManager.getTableWithContentAndMenu(TableManager.headerDocument,
                Document.class);
        NavigationManager.from(tfName).goToTableScene(table, "Документы");
    }
}
