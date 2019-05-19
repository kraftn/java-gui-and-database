package ru.kraftn.client.models;

import javax.persistence.*;

@Entity
@Table(name = "Goods")
public class Good implements AbleToGiveId {
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
    private CategoryOfGood category;

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

    public CategoryOfGood getCategory() {
        return category;
    }

    public void setCategory(CategoryOfGood category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name;
    }
}
