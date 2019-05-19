package ru.kraftn.client.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Category_Of_Goods")
public class CategoryOfGood implements AbleToGiveId{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Number")
    private int id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description", nullable = false)
    private String description;

    /*@OneToMany(orphanRemoval = true)
    @JoinColumn(name = "Category", insertable = true, updatable = true)
    private List<Good> goods;*/

    @ManyToMany
    @JoinTable(name = "Documents_For_Category",
            joinColumns = @JoinColumn(name = "Category"),
            inverseJoinColumns = @JoinColumn(name = "Document"))
    private List<Document> documents;

    public CategoryOfGood(){
        documents = new ArrayList<>();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public List<Good> getGoods() {
        return goods;
    }*/

    public List<Document> getDocuments() {
        return documents;
    }

    @Override
    public String toString(){
        return name;
    }
}
