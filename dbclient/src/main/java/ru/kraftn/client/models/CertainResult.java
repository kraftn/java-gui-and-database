package ru.kraftn.client.models;

import java.time.LocalDate;

public class CertainResult {
    private Integer customsProcedure;
    private LocalDate dateOfResult;
    private String surname;
    private String name;
    private String patronymic;
    private String phone;

    public CertainResult(Integer customsProcedure, LocalDate dateOfResult, String surname, String name, String patronymic, String phone) {
        this.customsProcedure = customsProcedure;
        this.dateOfResult = dateOfResult;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phone = phone;
    }

    public Integer getCustomsProcedure() {
        return customsProcedure;
    }

    public void setCustomsProcedure(Integer customsProcedure) {
        this.customsProcedure = customsProcedure;
    }

    public LocalDate getDateOfResult() {
        return dateOfResult;
    }

    public void setDateOfResult(LocalDate dateOfResult) {
        this.dateOfResult = dateOfResult;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
