package ru.kraftn.client.models;

import javax.persistence.*;

@Entity
@Table(name = "List_Of_Duties")
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Value_Of_Duty", nullable = false)
    private double valueOfDuty;

    @ManyToOne
    @JoinColumn(name = "Unit_Of_Measurement", nullable = false)
    private Unit unitOfMeasurement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValueOfDuty() {
        return valueOfDuty;
    }

    public void setValueOfDuty(double valueOfDuty) {
        this.valueOfDuty = valueOfDuty;
    }

    public Unit getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(Unit unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    @Override
    public String toString() {
        return name;
    }
}
