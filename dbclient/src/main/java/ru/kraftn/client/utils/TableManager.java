package ru.kraftn.client.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.hibernate.service.spi.ServiceException;
import ru.kraftn.client.models.*;
import ru.kraftn.client.navigation.NavigationManager;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TableManager {
    private static final String numberAfterComma = "2";
    private static final String textNoContent = "Пусто";
    public static final String[] headerDeclarant = {"ID", "Фамилия", "Имя", "Отчество", "Национальность",
            "Тип документа", "Номер документа", "Контактный телефон"};
    public static final String[] headerGood = {"ID", "Название", "Страна происхождения", "Категория"};
    public static final String[] headerSubmittedDocument = {"ID", "Заявление", "Тип документа", "Номер документа"};
    public static final String[] headerCooperator = {"ID", "Фамилия", "Имя", "Отчество", "Должность"};
    public static final String[] headerDocument = {"ID", "Название"};
    public static final String[] headerProcedurePaidDuties = {"ID заявления", "Название пошлины", "Сумма",
            "Дата оплаты"};
    public static final String[] headerProcedureCertainResult = {"ID заявления", "Дата результата", "Фамилия",
            "Имя", "Отчество", "Контактный телефон"};
    public static final String[] headerProcedureMissingDocuments = {"Название документа"};

    private TableManager() {
    }

    public static <T extends AbleToGiveId> TableView<T> getTableWithContentAndMenu(String[] header,
                                                                            Class<T> elementContentClass) {
        TableView<T> table = new TableView<>();
        table.setPlaceholder(new Label(textNoContent));
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
        List<T> content = HibernateManager.getInstance().getAllObjects(elementContentClass);
        setContent(table, content);

        return table;
    }

    public static <T> TableView<T> getTableWithCustomContent(String[] header, Class<T> elementContentClass,
                                                             List<T> content) {
        TableView<T> table = new TableView<>();
        table.setPlaceholder(new Label(textNoContent));
        Field[] fields = elementContentClass.getDeclaredFields();

        for (int i = 0, j = 0; i < fields.length; i++) {
            if (!fields[i].getType().equals(java.util.List.class)) {
                TableColumn<T, ?> column = new TableColumn<>(header[j]);
                column.setCellValueFactory(new PropertyValueFactory<>(fields[i].getName()));
                table.getColumns().add(column);
                j++;
            }
        }

        setContent(table, content);

        return table;
    }

    public static TableView<CustomsProcedure> getTableCustomsProcedures() {
        TableView<CustomsProcedure> table = new TableView<>();
        table.setPlaceholder(new Label(textNoContent));

        TableColumn<CustomsProcedure, Integer> columnId = new TableColumn<>("ID");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<CustomsProcedure, String> columnDeclarant = new TableColumn<>("Декларант");
        columnDeclarant.setCellValueFactory(new PropertyValueFactory<>("declarant"));

        TableColumn<CustomsProcedure, String> columnGood = new TableColumn<>("Груз");
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
        List<CustomsProcedure> content = HibernateManager.getInstance().getAllRefreshedObjects(CustomsProcedure.class);
        setContent(table, content);

        return table;
    }

    public static TableView<SubmittedDuty> getTableSubmittedDuties() {
        TableView<SubmittedDuty> table = new TableView<>();
        table.setPlaceholder(new Label(textNoContent));

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
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LL.yyyy");
                    String dateString = submittedDuty.getPaymentDate().format(formatter);
                    return new SimpleStringProperty(dateString);
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });

        table.getColumns().addAll(columnId, columnProcedure, columnDuty, columnSum, columnPaid, columnDatePaid);

        setContextMenuToTable(table, SubmittedDuty.class);
        List<SubmittedDuty> content = HibernateManager.getInstance().getAllRefreshedObjects(SubmittedDuty.class);
        setContent(table, content);

        return table;
    }

    public static TableView<ResultOfProcedure> getTableResults() {
        TableView<ResultOfProcedure> table = new TableView<>();
        table.setPlaceholder(new Label(textNoContent));

        TableColumn<ResultOfProcedure, Integer> columnIdProcedure = new TableColumn<>("ID");
        columnIdProcedure.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ResultOfProcedure, String> columnProcedure = new TableColumn<>("Заявление");
        if (RoleManager.getInstance().findRoleName().equals("Inspector")) {
            columnProcedure.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ResultOfProcedure, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ResultOfProcedure, String> param) {
                    ResultOfProcedure resultOfProcedure = param.getValue();
                    CustomsProcedure customsProcedure = HibernateManager.getInstance().findByID(CustomsProcedure.class,
                            resultOfProcedure.getId());
                    return new SimpleStringProperty(customsProcedure.toString());
                }
            });
        } else {
            columnProcedure.setCellValueFactory(new PropertyValueFactory<>("id"));
        }

        TableColumn<ResultOfProcedure, String> columnResult = new TableColumn<>("Результат");
        columnResult.setCellValueFactory(new PropertyValueFactory<>("result"));

        TableColumn<ResultOfProcedure, String> columnDatePaid = new TableColumn<>("Дата результата");
        columnDatePaid.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ResultOfProcedure, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ResultOfProcedure, String> param) {
                ResultOfProcedure resultOfProcedure = param.getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LL.yyyy");
                String dateString = resultOfProcedure.getDateOfResult().format(formatter);
                return new SimpleStringProperty(dateString);
            }
        });

        TableColumn<ResultOfProcedure, String> columnCooperator = new TableColumn<>("Таможенный инспектор");
        columnCooperator.setCellValueFactory(new PropertyValueFactory<>("cooperator"));

        table.getColumns().addAll(columnIdProcedure, columnProcedure, columnResult, columnDatePaid, columnCooperator);

        setContextMenuToTable(table, ResultOfProcedure.class);
        List<ResultOfProcedure> content = HibernateManager.getInstance().getAllObjects(ResultOfProcedure.class);
        setContent(table, content);

        return table;
    }

    public static TableView<Duty> getTableDuties() {
        TableView<Duty> table = new TableView<>();
        table.setPlaceholder(new Label(textNoContent));

        TableColumn<Duty, Integer> columnId = new TableColumn<>("ID");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Duty, String> columnName = new TableColumn<>("Название");
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Duty, String> columnValue = new TableColumn<>("Величина");
        columnValue.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Duty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Duty, String> param) {
                Duty duty = param.getValue();
                String result = String.format("%."+numberAfterComma+"f %s", duty.getValueOfDuty(),
                        duty.getUnitOfMeasurement());
                return new SimpleStringProperty(result);
            }
        });


        table.getColumns().addAll(columnId, columnName, columnValue);

        setContextMenuToTable(table, Duty.class);
        List<Duty> content = HibernateManager.getInstance().getAllObjects(Duty.class);
        setContent(table, content);

        return table;
    }

    public static TableView<CategoryOfGood> getTableCategoriesOfGoods() {
        TableView<CategoryOfGood> table = new TableView<>();
        table.setPlaceholder(new Label(textNoContent));

        TableColumn<CategoryOfGood, Integer> columnId = new TableColumn<>("ID");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<CategoryOfGood, String> columnName = new TableColumn<>("Название");
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<CategoryOfGood, String> columnDescription = new TableColumn<>("Описание");
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<CategoryOfGood, String> columnDocuments = new TableColumn<>("Необходимые документы");
        columnDocuments.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CategoryOfGood, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CategoryOfGood, String> param) {
                CategoryOfGood category = param.getValue();
                StringBuilder stringBuilder = new StringBuilder();
                for (Document document : category.getDocuments()){
                    stringBuilder.append(document + ", ");
                }
                String result = stringBuilder.toString();
                if (!result.isEmpty()) {
                    result = result.substring(0, result.length() - 2);
                }
                return new SimpleStringProperty(result);
            }
        });


        table.getColumns().addAll(columnId, columnName, columnDocuments, columnDescription);

        setContextMenuToTable(table, CategoryOfGood.class);
        List<CategoryOfGood> content = HibernateManager.getInstance().getAllObjects(CategoryOfGood.class);
        setContent(table, content);

        return table;
    }

    private static <T> void setContent(TableView<T> table, List<T> content) {
        ObservableList<T> observableList = FXCollections.observableArrayList(content);
        table.setItems(observableList);
    }

    private static <T extends AbleToGiveId> void setContextMenuToTable(TableView<T> table,
                                              Class<T> elementContentClass) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItemCreate = new MenuItem("Создать");
        MenuItem menuItemChange = new MenuItem("Изменить");
        MenuItem menuItemDelete = new MenuItem("Удалить");
        contextMenu.getItems().addAll(menuItemCreate, menuItemChange, menuItemDelete);

        if (RoleManager.getInstance().isContextMenuNecessary(elementContentClass)) {
            menuItemCreate.setOnAction(event -> {
                NavigationManager.from(table).goToCreateScene(elementContentClass);
            });

            menuItemChange.setOnAction(event -> {
                List<Integer> choices = getAllChoices(table.getItems());
                int orderNumberChoice = showDialogWindow("Изменить",
                        "Выберите ID записи, которую необходимо изменить", choices);
                if (orderNumberChoice != -1) {
                    NavigationManager.from(table).goToChangeScene(elementContentClass,
                            table.getItems().get(orderNumberChoice));
                }
            });

            menuItemDelete.setOnAction(event -> {
                List<Integer> choices = getAllChoices(table.getItems());
                int orderNumberChoice = showDialogWindow("Удалить",
                        "Выберите ID записи, которую необходимо удалить", choices);
                if (orderNumberChoice != -1) {
                    HibernateManager hibernate = HibernateManager.getInstance();
                    try {
                        hibernate.remove(table.getItems().get(orderNumberChoice));
                    } catch (Exception e) {
                        InflateUtils.createAndShowAlert("Не удалось удалить запись");
                        setContent(table, hibernate.getAllObjects(elementContentClass));
                        return;
                    }
                    table.getItems().remove(orderNumberChoice);
                }
            });
        } else {
            menuItemChange.setDisable(true);
            menuItemCreate.setDisable(true);
            menuItemDelete.setDisable(true);
        }

        table.setContextMenu(contextMenu);
    }

    private static int showDialogWindow(String windowName, String headerText,
                                        List<Integer> choices) {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(null, choices);
        dialog.setTitle(windowName);
        dialog.setHeaderText(headerText);
        dialog.setContentText("Выберите ID:");
        dialog.getDialogPane().getButtonTypes().clear();
        dialog.getDialogPane().getButtonTypes().addAll(new ButtonType("ОК",
                ButtonBar.ButtonData.OK_DONE), new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE));

        Optional<Integer> result = dialog.showAndWait();
        if (result.isPresent()) {
            return choices.indexOf(result.get());
        } else {
            return -1;
        }
    }

    private static List<Integer> getAllChoices(ObservableList<? extends AbleToGiveId> list) {
        ArrayList<Integer> choices = new ArrayList<>();
        for (AbleToGiveId element : list) {
            choices.add(element.getId());
        }
        return choices;
    }
}
