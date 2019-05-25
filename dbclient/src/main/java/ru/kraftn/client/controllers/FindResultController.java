package ru.kraftn.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.kraftn.client.utils.InflateUtils;

import java.time.LocalDate;

public class FindResultController {
    @FXML
    private TextField tfResult;
    @FXML
    private DatePicker dpBegin;
    @FXML
    private DatePicker dpEnd;

    public FindResultController(Dialog dialog) {
        dialog.setTitle("Найти информацию о декларантах для заявлений с заданным результатом");
        dialog.setHeaderText("Укажите необходимые параметры поиска:");
        ButtonType bOk = new ButtonType("ОК", ButtonBar.ButtonData.OK_DONE);
        ButtonType bCancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(bOk, bCancel);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == bOk) {
                LocalDate dateBegin = dpBegin.getValue();
                LocalDate dateEnd = dpEnd.getValue();
                String result = tfResult.getText();
                return new ResultDialog(dateBegin, dateEnd, result);
            } else {
                return null;
            }
        });

    }

    public static class ResultDialog {
        private LocalDate beginDate;
        private LocalDate endDate;
        private String result;

        public ResultDialog(LocalDate beginDate, LocalDate endDate, String result) {
            this.beginDate = beginDate;
            this.endDate = endDate;
            this.result = result;
        }

        public LocalDate getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(LocalDate beginDate) {
            this.beginDate = beginDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
