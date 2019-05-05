package ru.kraftn.client.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import ru.kraftn.client.models.CustomsProcedure;
import ru.kraftn.client.models.ResultOfProcedure;
import ru.kraftn.client.models.SubmittedDuty;
import ru.kraftn.client.navigation.NavigationManager;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TableManager {
    private static final String numberAfterComma = "2";
    public static final String[] headerDeclarant = {"ID", "Фамилия", "Имя", "Отчество", "Национальность",
            "Тип документа", "Номер документа", "Контактный телефон"};
    public static final String[] headerGood = {"ID", "Название", "Страна происхождения", "Категория"};
    public static final String[] headerDocument = {"ID", "Заявление", "Тип документа", "Номер документа"};
    public static final String[] headerCooperator = {"ID", "Фамилия", "Имя", "Отчество", "Должность"};

    private TableManager() {
    }

    public static <T extends AbleToGiveId> TableView<T> getTableWithContent(String[] header,
                                                                            Class<T> elementContentClass,
                                                                            List<T> content) {
        TableView<T> table = new TableView<>();
        Field[] fields = elementContentClass.getDeclaredFields();

        for (int i = 0, j = 0; i < fields.length; i++) {
            if (!fields[i].getType().equals(java.util.List.class)) {
                TableColumn<T, ?> column = new TableColumn<>(header[j]);
                column.setCellValueFactory(new PropertyValueFactory<>(fields[i].getName()));
                table.getColumns().add(column);
                j++;
            }
        }

        setContextMenuToTable(table, elementContentClass);
        setContent(table, content);

        return table;
    }

    public static TableView<CustomsProcedure> getTableCustomsProcedures(List<CustomsProcedure> content) {
        TableView<CustomsProcedure> table = new TableView<>();

        TableColumn<CustomsProcedure, Integer> columnId = new TableColumn<>("ID");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<CustomsProcedure, String> columnDeclarant = new TableColumn<>("Декларант");
        columnDeclarant.setCellValueFactory(new PropertyValueFactory<>("declarant"));

        TableColumn<CustomsProcedure, String> columnGood = new TableColumn<>("Товар");
        columnGood.setCellValueFactory(new PropertyValueFactory<>("good"));

        TableColumn<CustomsProcedure, String> columnAmount = new TableColumn<>("Количество");
        columnAmount.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CustomsProcedure, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CustomsProcedure, String> param) {
                CustomsProcedure customsProcedure = param.getValue();
                String x = String.format("%." + numberAfterComma + "f %s", customsProcedure.getAmountOfGoods(),
                        customsProcedure.getUnitOfMeasurement().getUnitOfMeasurement());
                return new SimpleStringProperty(x);
            }
        });

        TableColumn<CustomsProcedure, String> columnWorth = new TableColumn<>("Стоимость");
        columnWorth.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CustomsProcedure, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CustomsProcedure, String> param) {
                CustomsProcedure customsProcedure = param.getValue();
                String x = String.format("%." + numberAfterComma + "f", customsProcedure.getWorthOfGoods());
                return new SimpleStringProperty(x);
            }
        });

        TableColumn<CustomsProcedure, String> columnTypeOfProcedure = new TableColumn<>("Тип процедуры");
        columnTypeOfProcedure.setCellValueFactory(new PropertyValueFactory<>("typeOfProcedure"));

        TableColumn<CustomsProcedure, String> columnTotalDuties = new TableColumn<>("Сумма всех пошлин");
        columnTotalDuties.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CustomsProcedure, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CustomsProcedure, String> param) {
                CustomsProcedure customsProcedure = param.getValue();
                String x = String.format("%." + numberAfterComma + "f", customsProcedure.getTotalDuties());
                return new SimpleStringProperty(x);
            }
        });

        TableColumn<CustomsProcedure, String> columnAverageDuty = new TableColumn<>("Средняя пошлина");
        columnAverageDuty.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CustomsProcedure, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CustomsProcedure, String> param) {
                CustomsProcedure customsProcedure = param.getValue();
                String x = String.format("%." + numberAfterComma + "f", customsProcedure.getAverageDuty());
                return new SimpleStringProperty(x);
            }
        });

        TableColumn<CustomsProcedure, String> columnResult = new TableColumn<>("Результат");
        columnResult.setCellValueFactory(new PropertyValueFactory<>("resultOfProcedure"));

        table.getColumns().addAll(columnId, columnDeclarant, columnGood, columnAmount, columnWorth,
                columnTypeOfProcedure, columnTotalDuties, columnAverageDuty, columnResult);

        setContextMenuToTable(table, CustomsProcedure.class);
        setContent(table, content);

        return table;
    }

    public static TableView<SubmittedDuty> getTableSubmittedDuties(List<SubmittedDuty> content) {
        TableView<SubmittedDuty> table = new TableView<>();

        TableColumn<SubmittedDuty, Integer> columnId = new TableColumn<>("ID");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<SubmittedDuty, String> columnProcedure = new TableColumn<>("Заявление");
        columnProcedure.setCellValueFactory(new PropertyValueFactory<>("customsProcedure"));

        TableColumn<SubmittedDuty, String> columnDuty = new TableColumn<>("Пошлина");
        columnDuty.setCellValueFactory(new PropertyValueFactory<>("duty"));

        TableColumn<SubmittedDuty, String> columnSum = new TableColumn<>("Сумма");
        columnSum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SubmittedDuty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SubmittedDuty, String> param) {
                SubmittedDuty submittedDuty = param.getValue();
                String x = String.format("%." + numberAfterComma + "f", submittedDuty.getSum());
                return new SimpleStringProperty(x);
            }
        });

        TableColumn<SubmittedDuty, String> columnPaid = new TableColumn<>("Оплата");
        columnPaid.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SubmittedDuty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SubmittedDuty, String> param) {
                SubmittedDuty submittedDuty = param.getValue();
                String x;
                if (submittedDuty.isPaid()) {
                    x = "Есть";
                } else {
                    x = "Нет";
                }
                return new SimpleStringProperty(x);
            }
        });

        TableColumn<SubmittedDuty, String> columnDatePaid = new TableColumn<>("Дата оплаты");
        columnDatePaid.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SubmittedDuty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SubmittedDuty, String> param) {
                if (null != param.getValue().getPaymentDate()) {
                    SubmittedDuty submittedDuty = param.getValue();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LL.yyyy HH:mm:ss");
                    String dateString = submittedDuty.getPaymentDate().format(formatter);
                    return new SimpleStringProperty(dateString);
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });

        table.getColumns().addAll(columnId, columnProcedure, columnDuty, columnSum, columnPaid, columnDatePaid);

        setContextMenuToTable(table, SubmittedDuty.class);
        setContent(table, content);

        return table;
    }

    public static TableView<ResultOfProcedure> getTableResults(List<ResultOfProcedure> content) {
        TableView<ResultOfProcedure> table = new TableView<>();

        TableColumn<ResultOfProcedure, Integer> columnIdProcedure = new TableColumn<>("ID");
        columnIdProcedure.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ResultOfProcedure, String> columnProcedure = new TableColumn<>("Заявление");
        columnProcedure.setCellValueFactory(new PropertyValueFactory<>("procedure"));

        TableColumn<ResultOfProcedure, String> columnResult = new TableColumn<>("Результат");
        columnResult.setCellValueFactory(new PropertyValueFactory<>("result"));

        TableColumn<ResultOfProcedure, String> columnDatePaid = new TableColumn<>("Дата результата");
        columnDatePaid.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ResultOfProcedure, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ResultOfProcedure, String> param) {
                ResultOfProcedure кesultOfProcedure = param.getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LL.yyyy HH:mm:ss");
                String dateString = кesultOfProcedure.getDateOfResult().format(formatter);
                return new SimpleStringProperty(dateString);
            }
        });

        TableColumn<ResultOfProcedure, String> columnCooperator = new TableColumn<>("Сотрудник");
        columnCooperator.setCellValueFactory(new PropertyValueFactory<>("cooperator"));

        table.getColumns().addAll(columnIdProcedure, columnProcedure, columnResult, columnDatePaid, columnCooperator);

        setContextMenuToTable(table, ResultOfProcedure.class);
        setContent(table, content);

        return table;
    }

    private static <T extends AbleToGiveId> void setContent(TableView<T> table, List<T> content) {
        ObservableList<T> observableList = FXCollections.observableArrayList(content);
        table.setItems(observableList);
    }

    private static void setContextMenuToTable(TableView<? extends AbleToGiveId> table,
                                                  Class<? extends AbleToGiveId> elementContentClass) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem menuItemCreate = new MenuItem("Создать");
        menuItemCreate.setOnAction(event -> {
            NavigationManager.from(table).goToCreateScene(elementContentClass);
        });

        MenuItem menuItemChange = new MenuItem("Изменить");
        menuItemChange.setOnAction(event -> {
            List<Integer> choices = getAllChoices(table.getItems());
            int orderNumberChoice = showDialogWindow("Изменить",
                    "Выберите ID записи, которую необходимо изменить", choices);
            if (orderNumberChoice != -1) {
                NavigationManager.from(table).goToChangeScene(elementContentClass,
                        table.getItems().get(orderNumberChoice));
            }
        });

        MenuItem menuItemDelete = new MenuItem("Удалить");
        menuItemDelete.setOnAction(event -> {
            List<Integer> choices = getAllChoices(table.getItems());
            int orderNumberChoice = showDialogWindow("Удалить",
                    "Выберите ID записи, которую необходимо удалить", choices);
            if (orderNumberChoice != -1){
                HibernateManager hibernate = HibernateManager.getInstance();
                hibernate.beginTransaction();
                hibernate.remove(table.getItems().get(orderNumberChoice));
                table.getItems().remove(orderNumberChoice);
                hibernate.endTransaction();
            }
        });

        contextMenu.getItems().addAll(menuItemCreate, menuItemChange, menuItemDelete);
        table.setContextMenu(contextMenu);
    }

    private static int showDialogWindow(String windowName, String headerText,
                                            List<Integer> choices) {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(null, choices);
        dialog.setTitle(windowName);
        dialog.setHeaderText(headerText);
        dialog.setContentText("Выберите ID:");

        Optional<Integer> result = dialog.showAndWait();
        if (result.isPresent()) {
            return choices.indexOf(result.get());
        } else {
            return -1;
        }
    }

    private static List<Integer> getAllChoices(ObservableList<? extends AbleToGiveId> list){
        ArrayList<Integer> choices = new ArrayList<>();
        for (AbleToGiveId element : list) {
            choices.add(element.getId());
        }
        return choices;
    }
}
