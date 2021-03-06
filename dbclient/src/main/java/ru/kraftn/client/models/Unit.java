package ru.kraftn.client.models;

import javax.persistence.*;

@Entity
@Table(name = "List_Of_Units")

@SqlResultSetMapping(
        name = "userInformation",
        classes = @ConstructorResult(
                targetClass = UserInformation.class,
                columns = {
                        @ColumnResult(name = "UserName", type = String.class),
                        @ColumnResult(name = "RoleName", type = String.class),
                        @ColumnResult(name = "LoginName", type = String.class),
                }
        )
)

public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    int id;

    @Column(name = "Unit_Of_Measurement", nullable = false)
    private String unitOfMeasurement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    @Override
    public String toString() {
        return unitOfMeasurement;
    }
}
