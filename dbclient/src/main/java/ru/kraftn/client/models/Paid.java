package ru.kraftn.client.models;

import java.time.LocalDate;

public class Paid {
    private Integer procedure;
    private String name;
    private Integer sum;
    private LocalDate paymentDate;

    public Paid(Integer procedure, String name, Integer sum, LocalDate paymentDate) {
        this.procedure = procedure;
        this.name = name;
        this.sum = sum;
        this.paymentDate = paymentDate;
    }

    public Integer getProcedure() {
        return procedure;
    }

    public void setProcedure(Integer procedure) {
        this.procedure = procedure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
