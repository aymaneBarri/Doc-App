package com.example.docapp.models;

public class Visite {
    private int id;
    private String visit_date;
    private String description;
    private String assurance;
    private String illness;
    private double amount;
    private String prescription;
    private int id_patient;

    public Visite() {
        id = 0;
        visit_date = "";
        description = "";
        assurance = "";
        illness = "";
        amount = 0.0;
        prescription = "";
        id_patient = 0;
    }

    public Visite(int id, String visit_date, String description, String assurance, String illness, double amount, String prescription, int id_patient) {
        this.id = id;
        this.visit_date = visit_date;
        this.description = description;
        this.assurance = assurance;
        this.illness = illness;
        this.amount = amount;
        this.prescription = prescription;
        this.id_patient = id_patient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssurance() {
        return assurance;
    }

    public void setAssurance(String assurance) {
        this.assurance = assurance;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    @Override
    public String toString() {
        return "Visite{" +
                "id=" + id +
                ", visit_date='" + visit_date + '\'' +
                ", description='" + description + '\'' +
                ", assurance='" + assurance + '\'' +
                ", illness='" + illness + '\'' +
                ", amount=" + amount +
                ", prescription='" + prescription + '\'' +
                ", id_patient=" + id_patient +
                '}';
    }
}
