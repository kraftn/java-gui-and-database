package ru.kraftn.client.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Customs_Procedure")

//Mapping результата выполнения процедур
@SqlResultSetMapping(
        name = "paid",
        classes = @ConstructorResult(
                targetClass = Paid.class,
                columns = {
                        @ColumnResult(name = "Customs_Procedure", type = Integer.class),
                        @ColumnResult(name = "Name", type = String.class),
                        @ColumnResult(name = "Sum", type = Integer.class),
                        @ColumnResult(name = "Payment_Date", type = LocalDate.class),
                }
        )
)

@SqlResultSetMapping(
        name = "certainResult",
        classes = @ConstructorResult(
                targetClass = CertainResult.class,
                columns = {
                        @ColumnResult(name = "Customs_Procedure", type = Integer.class),
                        @ColumnResult(name = "Date_Of_Result", type = LocalDate.class),
                        @ColumnResult(name = "Surname", type = String.class),
                        @ColumnResult(name = "Name", type = String.class),
                        @ColumnResult(name = "Patronymic", type = String.class),
                        @ColumnResult(name = "Contact_Phone", type = String.class)
                }
        )
)

@SqlResultSetMapping(
        name = "missingDocument",
        classes = @ConstructorResult(
                targetClass = MissingDocument.class,
                columns = {
                        @ColumnResult(name = "Document", type = String.class),
                }
        )
)

public class CustomsProcedure implements AbleToGiveId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "Declarant", nullable = false)
    private Declarant declarant;

    @ManyToOne
    @JoinColumn(name = "Goods", nullable = false)
    private Good good;

    @Column(name = "Amount_Of_Goods", nullable = false)
    private double amountOfGoods;

    @ManyToOne
    @JoinColumn(name = "Unit_Of_Measurement", nullable = false)
    private Unit unitOfMeasurement;

    @Column(name = "Worth_Of_Goods", nullable = false)
    private double worthOfGoods;

    @Column(name = "Type_Of_Procedure", nullable = false)
    private String typeOfProcedure;

    @Column(name = "Total_Duties", nullable = false)
    private double totalDuties;

    @Column(name = "Average_Duty", nullable = false)
    private double averageDuty;

    @OneToOne
    @PrimaryKeyJoinColumn
    private ResultOfProcedure resultOfProcedure;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "Number_Of_Procedure")
    private List<SubmittedDocument> submittedDocuments;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "Customs_Procedure")
    private List<SubmittedDuty> submittedDuties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Declarant getDeclarant() {
        return declarant;
    }

    public void setDeclarant(Declarant declarant) {
        this.declarant = declarant;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public double getAmountOfGoods() {
        return amountOfGoods;
    }

    public void setAmountOfGoods(double amountOfGoods) {
        this.amountOfGoods = amountOfGoods;
    }

    public Unit getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(Unit unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public double getWorthOfGoods() {
        return worthOfGoods;
    }

    public void setWorthOfGoods(double worthOfGoods) {
        this.worthOfGoods = worthOfGoods;
    }

    public String getTypeOfProcedure() {
        return typeOfProcedure;
    }

    public void setTypeOfProcedure(String typeOfProcedure) {
        this.typeOfProcedure = typeOfProcedure;
    }

    public double getTotalDuties() {
        return totalDuties;
    }

    public void setTotalDuties(double totalDuties) {
        this.totalDuties = totalDuties;
    }

    public double getAverageDuty() {
        return averageDuty;
    }

    public void setAverageDuty(double averageDuty) {
        this.averageDuty = averageDuty;
    }

    public ResultOfProcedure getResultOfProcedure() {
        return resultOfProcedure;
    }

    public void setResultOfProcedure(ResultOfProcedure resultOfProcedure) {
        this.resultOfProcedure = resultOfProcedure;
    }

    public List<SubmittedDocument> getSubmittedDocuments() {
        return submittedDocuments;
    }

    public List<SubmittedDuty> getSubmittedDuties() {
        return submittedDuties;
    }

    @Override
    public String toString() {
        return new StringBuilder(declarant.getSurname())
                .append(" ")
                .append(declarant.getName())
                .append(" ")
                .append(declarant.getPatronymic())
                .append(" - ")
                .append(good.getName())
                .append(" (ID=")
                .append(id)
                .append(")")
                .toString();
    }
}
