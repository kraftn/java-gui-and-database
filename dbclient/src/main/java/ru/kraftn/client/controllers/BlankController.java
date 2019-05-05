package ru.kraftn.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.hibernate.Hibernate;
import ru.kraftn.client.models.*;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;
import ru.kraftn.client.utils.TableManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BlankController implements Initializable {
    @FXML
    private BorderPane bpLayout;

    private TableView tvMainTable = null;

    public BlankController() {
    }

    public BlankController(TableView table) {
        this.tvMainTable = table;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (null != tvMainTable) {
            bpLayout.setCenter(tvMainTable);
        }
    }

    @FXML
    public void showTableDeclarants() {
        List<Declarant> content = HibernateManager.getInstance().getAllObjects(Declarant.class);
        TableView table = TableManager.getTableWithContent(TableManager.headerDeclarant, Declarant.class,content);
        NavigationManager.from(bpLayout).goToTableScene(table);
    }

    @FXML
    public void showTableGoods() {
        List<Good> content = HibernateManager.getInstance().getAllObjects(Good.class);
        TableView table = TableManager.getTableWithContent(TableManager.headerGood, Good.class, content);
        NavigationManager.from(bpLayout).goToTableScene(table);
    }

    @FXML
    public void showTableCustomsProcedures() {
        List<CustomsProcedure> content = HibernateManager.getInstance().getAllObjects(CustomsProcedure.class);
        TableView table = TableManager.getTableCustomsProcedures(content);
        NavigationManager.from(bpLayout).goToTableScene(table);
    }

    @FXML
    public void showTableDuties() {
        List<SubmittedDuty> content = HibernateManager.getInstance().getAllObjects(SubmittedDuty.class);
        TableView table = TableManager.getTableSubmittedDuties(content);
        NavigationManager.from(bpLayout).goToTableScene(table);
    }

    @FXML
    public void showTableDocuments() {
        List<SubmittedDocument> content = HibernateManager.getInstance().getAllObjects(SubmittedDocument.class);
        TableView table = TableManager.getTableWithContent(TableManager.headerDocument, SubmittedDocument.class, content);
        NavigationManager.from(bpLayout).goToTableScene(table);
    }

    @FXML
    public void showTableResults() {
        List<ResultOfProcedure> content = HibernateManager.getInstance().getAllObjects(ResultOfProcedure.class);
        TableView table = TableManager.getTableResults(content);
        NavigationManager.from(bpLayout).goToTableScene(table);
    }

    @FXML
    public void showTableCooperators() {
        List<Cooperator> content = HibernateManager.getInstance().getAllObjects(Cooperator.class);
        TableView table = TableManager.getTableWithContent(TableManager.headerCooperator, Cooperator.class, content);
        NavigationManager.from(bpLayout).goToTableScene(table);
    }

    @FXML
    public void close(){
        HibernateManager.close();
        NavigationManager.from(bpLayout).goToLoginScene();
    }
}
