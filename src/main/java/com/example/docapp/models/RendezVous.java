package com.example.docapp.models;

public class RendezVous {
    private int id;
    private String rendezVousDate;
    private String description;
    private int id_patient;

    public RendezVous(){
        id = 0;
        rendezVousDate = "";
        description = "";
        id_patient = 0;
    }

    public RendezVous(String rendezVousDate, String description, int id_patient) {
        this.rendezVousDate = rendezVousDate;
        this.description = description;
        this.id_patient = id_patient;
    }

    public RendezVous(int id, String rendezVousDate, String description, int id_patient) {
        this(rendezVousDate, description, id_patient);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRendezVousDate() {
        return rendezVousDate;
    }

    public void setRendezVousDate(String rendezVousDate) {
        this.rendezVousDate = rendezVousDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", rendezVousDate='" + rendezVousDate + '\'' +
                ", description='" + description + '\'' +
                ", id_patient=" + id_patient +
                '}';
    }
}
