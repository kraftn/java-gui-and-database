package ru.kraftn.client.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Customs_Duties")
public class SubmittedDuty implements AbleToGiveId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "Customs_Procedure", nullable = false)
    private CustomsProcedure customsProcedure;

    @ManyToOne
    @JoinColumn(name = "Duty", nullable = false)
    private Duty duty;

    @Column(name = "Sum")
    private double sum;

    @Column(name = "Paid", nullable = false)
    private int paid;

    @Column(name = "Payment_Date")
    private LocalDate paymentDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomsProcedure getCustomsProcedure() {
        return customsProcedure;
    }

    public void setCustomsProcedure(CustomsProcedure customsProcedure) {
        this.customsProcedure = customsProcedure;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public boolean isPaid() {
        return 1 == paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
