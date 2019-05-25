package ru.kraftn.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import ru.kraftn.client.models.*;
import ru.kraftn.client.navigation.NavigationManager;
import ru.kraftn.client.utils.HibernateManager;
import ru.kraftn.client.utils.InflateUtils;
import ru.kraftn.client.utils.RoleManager;
import ru.kraftn.client.utils.TableManager;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BlankController implements Initializable {
    @FXML
    private BorderPane bpLayout;
    @FXML
    private MenuBar mbMainMenu;
    @FXML
    private Label lTitle;

    private TableView tvMainTable = null;
    private String title;

    public BlankController() {
    }

    public BlankController(TableView table, String title) {
        this.tvMainTable = table;
        this.title = title;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (null != tvMainTable) {
            lTitle.setText(title);
            bpLayout.setCenter(tvMainTable);
        }

        RoleManager.getInstance().setDisableMenuBar(mbMainMenu);
    }

    @FXML
    public void showTableDeclarants() {
        TableView table = TableManager.getTableWithContentAndMenu(TableManager.headerDeclarant, Declarant.class);
        NavigationManager.from(bpLayout).goToTableScene(table, "Декларанты");
    }

    @FXML
    public void showTableGoods() {
        TableView table = TableManager.getTableWithContentAndMenu(TableManager.headerGood, Good.class);
        NavigationManager.from(bpLayout).goToTableScene(table, "Грузы");
    }

    @FXML
    public void showTableCustomsProcedures() {
        TableView table = TableManager.getTableCustomsProcedures();
        NavigationManager.from(bpLayout).goToTableScene(table, "Заявления");
    }

    @FXML
    public void showTableDuties() {
        TableView table = TableManager.getTableSubmittedDuties();
        NavigationManager.from(bpLayout).goToTableScene(table, "Пошлины, подлежащие уплате");
    }

    @FXML
    public void showTableDocuments() {
        TableView table = TableManager.getTableWithContentAndMenu(TableManager.headerSubmittedDocument, SubmittedDocument.class);
        NavigationManager.from(bpLayout).goToTableScene(table, "Поданные документы");
    }

    @FXML
    public void showTableResults() {
        TableView table = TableManager.getTableResults();
        NavigationManager.from(bpLayout).goToTableScene(table, "Результаты рассмотрения");
    }

    @FXML
    public void showTableCooperators() {
        TableView table = TableManager.getTableWithContentAndMenu(TableManager.headerCooperator, Cooperator.class);
        NavigationManager.from(bpLayout).goToTableScene(table, "Таможенные инспекторы");
    }

    @FXML
    public void showTableCategories(){
        TableView table = TableManager.getTableCategoriesOfGoods();
        NavigationManager.from(bpLayout).goToTableScene(table, "Категории грузов");
    }

    @FXML
    public void showTablePossibleDocuments(){
        TableView table = TableManager.getTableWithContentAndMenu(TableManager.headerDocument, Document.class);
        NavigationManager.from(bpLayout).goToTableScene(table, "Документы");
    }

    @FXML
    public void showTablePossibleDuties(){
        TableView table = TableManager.getTableDuties();
        NavigationManager.from(bpLayout).goToTableScene(table, "Пошлины");
    }

    @FXML
    public void findPaidDuties(){
        int interval = showTextInputDialog();
        if (interval != -1){
            List<Paid> content = HibernateManager.getInstance().getPaid(interval);
            TableView table = TableManager.getTableWithCustomContent(TableManager.headerProcedurePaidDuties,
                    Paid.class, content);
            NavigationManager.from(mbMainMenu).goToTableScene(table, String.format("Пошлины оплаченные за последние %d дней",
                    interval));
        }
    }

    @FXML
    public void findCertainResults(){
        Dialog<FindResultController.ResultDialog> dialog = new Dialog<>();
        FindResultController controller = new FindResultController(dialog);
        Parent layout = InflateUtils.loadFxmlWithCustomController("/fxml/find_result_controller.fxml", controller);
        dialog.getDialogPane().setContent(layout);
        Optional<FindResultController.ResultDialog> resultDialog = dialog.showAndWait();

        if (resultDialog.isPresent()){
            if (null == resultDialog.get().getBeginDate() || null == resultDialog.get().getEndDate()){
                InflateUtils.createAndShowAlert("Укажите все параметры.");
            } else {
                List<CertainResult> content = HibernateManager.getInstance().getCertainResult(resultDialog.get().getBeginDate(),
                        resultDialog.get().getEndDate(), resultDialog.get().getResult());
                TableView table = TableManager.getTableWithCustomContent(TableManager.headerProcedureCertainResult,
                        CertainResult.class, content);
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.LL.yyyy");
                NavigationManager.from(mbMainMenu).goToTableScene(table, String.format(
                        "Информация о декларантах для заявлений с результатом \"%s\" за период %s-%s",
                        resultDialog.get().getResult(), resultDialog.get().getBeginDate().format(dateFormatter),
                        resultDialog.get().getEndDate().format(dateFormatter)));
            }
        }
    }

    @FXML
    public void findMissingDocuments(){
        int idProcedure = showChoiceDialog();
        if (idProcedure != -1){
            List<MissingDocument> content = HibernateManager.getInstance().getMissingDocuments(idProcedure);
            TableView table = TableManager.getTableWithCustomContent(TableManager.headerProcedureMissingDocuments,
                    MissingDocument.class, content);
            NavigationManager.from(mbMainMenu).goToTableScene(table, String.format("Недостающие документы для заявления с ID=%d",
                    idProcedure));
        }
    }

    @FXML
    public void close(){
        HibernateManager.close();
        NavigationManager.from(bpLayout).goToLoginScene();
    }

    private static int showTextInputDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Найти пошлины, оплаченные за последние n дней");
        dialog.setHeaderText("Укажите необходимые параметры поиска");
        dialog.setContentText("Укажите максимальное количество дней, которое может пройти с момента оплаты пошлин:");
        dialog.getDialogPane().getButtonTypes().clear();
        dialog.getDialogPane().getButtonTypes().addAll(new ButtonType("ОК",
                ButtonBar.ButtonData.OK_DONE), new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE));

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int resultInt = -1;

            try {
                resultInt = Integer.parseUnsignedInt(result.get());
            } catch (NumberFormatException e){
                InflateUtils.createAndShowAlert("Введённые данные не являются числом, большим или равным 0.");
            }

            return resultInt;
        } else {
            return -1;
        }
    }

    private static int showChoiceDialog() {
        List<CustomsProcedure> choices = HibernateManager.getInstance().getAllObjects(CustomsProcedure.class);
        ChoiceDialog<CustomsProcedure> dialog = new ChoiceDialog<>(null, choices);
        dialog.setTitle("Найти недостающие документы для заявления");
        dialog.setHeaderText("Укажите необходимые параметры поиска");
        dialog.setContentText("Выберите заявление:");
        dialog.getDialogPane().getButtonTypes().clear();
        dialog.getDialogPane().getButtonTypes().addAll(new ButtonType("ОК",
                ButtonBar.ButtonData.OK_DONE), new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE));

        Optional<CustomsProcedure> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get().getId();
        } else {
            return -1;
        }
    }
}
