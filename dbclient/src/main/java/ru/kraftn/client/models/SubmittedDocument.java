package ru.kraftn.client.models;

import javax.persistence.*;

@Entity
@Table(name = "Submitted_Documents")
public class SubmittedDocument implements AbleToGiveId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Of_Document")
    private int id;

    @ManyToOne
    @JoinColumn(name = "Number_Of_Procedure", nullable = false)
    private CustomsProcedure procedure;

    @ManyToOne
    @JoinColumn(name = "Type_Of_Document", nullable = false)
    private Document document;

    @Column(name = "Document_Number", nullable = false)
    private String documentNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomsProcedure getProcedure() {
        return procedure;
    }

    public void setProcedure(CustomsProcedure procedure) {
        this.procedure = procedure;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
