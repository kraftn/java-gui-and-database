package ru.kraftn.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ru.kraftn.client.models.Declarant;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;
import ru.kraftn.client.utils.TableManager;

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
    public void onOk(){
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

        hibernate.beginTransaction();
        hibernate.save(data);
        hibernate.endTransaction();

        List<Declarant> content = hibernate.getAllObjects(Declarant.class);
        TableView<Declarant> table = TableManager.getTableWithContent(TableManager.headerDeclarant, Declarant.class,
                content);
        NavigationManager.from(tfSurname).goToTableScene(table);
    }

    @FXML
    public void onCancel(){
        HibernateManager hibernate = HibernateManager.getInstance();
        List<Declarant> content = hibernate.getAllObjects(Declarant.class);
        TableView<Declarant> table = TableManager.getTableWithContent(TableManager.headerDeclarant, Declarant.class,
                content);
        NavigationManager.from(tfSurname).goToTableScene(table);
    }
}
