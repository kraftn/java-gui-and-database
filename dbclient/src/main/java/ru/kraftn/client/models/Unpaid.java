package ru.kraftn.client.models;

public class Unpaid {
    private Integer procedure;
    private String name;
    private Integer valueOfDuty;
    private String unit;
    private Integer sum;

    public Unpaid(Integer procedure, String name, Integer valueOfDuty, String unit, Integer sum) {
        this.procedure = procedure;
        this.name = name;
        this.valueOfDuty = valueOfDuty;
        this.unit = unit;
        this.sum = sum;
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

    public Integer getValueOfDuty() {
        return valueOfDuty;
    }

    public void setValueOfDuty(Integer valueOfDuty) {
        this.valueOfDuty = valueOfDuty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return String.format("%s %d%s %d", name, valueOfDuty, unit, sum);
    }
}
