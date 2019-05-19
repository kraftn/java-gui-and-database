package ru.kraftn.client.models;

import javax.persistence.*;

@Entity
@Table(name = "Cooperator")
public class Cooperator implements AbleToGiveId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Surname", nullable = false)
    private String surname;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Patronymic", nullable = false)
    private String patronymic;

    @Column(name = "Position", nullable = false)
    private String position;

    /*@OneToMany(orphanRemoval = true)
    @JoinColumn(name = "Cooperator")
    private List<ResultOfProcedure> resultsOfProcedure;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    /*public List<ResultOfProcedure> getResultsOfProcedure() {
        return resultsOfProcedure;
    }*/

    @Override
    public String toString() {
        return new StringBuilder(position)
                .append(" ")
                .append(surname)
                .append(" ")
                .append(name)
                .append(" ")
                .append(patronymic)
                .append(" (ID=")
                .append(id)
                .append(")")
                .toString();
    }
}
