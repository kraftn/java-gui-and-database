package ru.overtired.javafx.sample3.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Category_Of_Goods")
public class CategoryOfGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Number")
    private int number;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description", nullable = false)
    private String description;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "Category", nullable = false, insertable = false, updatable = false)
    private List<Goods> goods;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Goods> getGoods() {
        return goods;
    }
}
