package ru.kraftn.client.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "List_Of_Documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "Documents_For_Category",
            joinColumns = @JoinColumn(name = "Document"),
            inverseJoinColumns = @JoinColumn(name = "Category"))
    private List<CategoryOfGood> categoriesOfGood;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "Type_Of_Document")
    List<SubmittedDocument> submittedDocuments;

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

    public List<CategoryOfGood> getCategoriesOfGood() {
        return categoriesOfGood;
    }

    public List<SubmittedDocument> getSubmittedDocuments() {
        return submittedDocuments;
    }

    @Override
    public String toString() {
        return name;
    }
}
