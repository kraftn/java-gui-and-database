package ru.overtired.javafx.sample3.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Goods")
@SqlResultSetMapping(
        name = "unpaid",
        classes = @ConstructorResult(
                targetClass = Unpaid.class,
                columns = {
                        @ColumnResult(name = "Procedure", type = Integer.class),
                        @ColumnResult(name = "Name", type = String.class),
                        @ColumnResult(name = "Value_Of_Duty", type = Integer.class),
                        @ColumnResult(name = "Unit_Of_Measurement", type = String.class),
                        @ColumnResult(name = "Sum", type = Integer.class),
                }
        )
)
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Country_Of_Origin", nullable = false)
    private String countryOfOrigin;

    @ManyToOne
    @JoinColumn(name = "Category", nullable = false)
    private CategoryOfGoods category;

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

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    @Override
    public String toString() {
        return String.format("%s из %s c категорией \"%s\"", name, countryOfOrigin, category.getName());
    }

    public CategoryOfGoods getCategory() {
        return category;
    }

    public void setCategory(CategoryOfGoods category) {
        this.category = category;
    }
}
