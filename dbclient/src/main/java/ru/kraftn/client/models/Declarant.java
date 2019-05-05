package ru.kraftn.client.models;

import ru.kraftn.client.utils.AbleToGiveId;

import javax.persistence.*;

@Entity
@Table(name = "Declarant")
public class Declarant implements AbleToGiveId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    int id;

    @Column(name = "Surname", nullable = false)
    private String surname;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Patronymic")
    private String patronymic;

    @Column(name = "Nationality", nullable = false)
    private String nationality;

    @Column(name = "Type_Of_Identify_Document", nullable = false)
    private String typeOfIdentifyDocument;

    @Column(name = "Document_Number", nullable = false)
    private String documentNumber;

    @Column(name = "Contact_Phone", nullable = false)
    private String contactPhone;

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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTypeOfIdentifyDocument() {
        return typeOfIdentifyDocument;
    }

    public void setTypeOfIdentifyDocument(String typeOfIdentifyDocument) {
        this.typeOfIdentifyDocument = typeOfIdentifyDocument;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public String toString() {
        return new StringBuilder(surname)
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
