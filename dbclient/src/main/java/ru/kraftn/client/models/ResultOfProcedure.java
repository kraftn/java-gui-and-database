package ru.kraftn.client.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Result_Of_Procedure")
public class ResultOfProcedure implements AbleToGiveId {
    @Id
    @Column(name = "Number_Of_Procedure")
    private int id;

    @Column(name = "Result", nullable = false)
    private String result;

    @Column(name = "Date_Of_Result", nullable = false)
    private LocalDate dateOfResult;

    @ManyToOne
    @JoinColumn(name = "Cooperator", nullable = false)
    private Cooperator cooperator;

    /*@OneToOne
    @PrimaryKeyJoinColumn
    private CustomsProcedure procedure;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getDateOfResult() {
        return dateOfResult;
    }

    public void setDateOfResult(LocalDate dateOfResult) {
        this.dateOfResult = dateOfResult;
    }

    public Cooperator getCooperator() {
        return cooperator;
    }

    public void setCooperator(Cooperator cooperator) {
        this.cooperator = cooperator;
    }

    /*public CustomsProcedure getProcedure() {
        return procedure;
    }*/

    /*public void setProcedure(CustomsProcedure procedure) {
        this.procedure = procedure;
    }*/

    @Override
    public String toString() {
        return result;
    }
}
