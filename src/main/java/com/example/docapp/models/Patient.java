package com.example.docapp.models;

import java.util.Vector;

public class Patient extends Personne {
    private String birthDate;
    private String description;
    private String join_date;
    private static Vector<Patient> patientList ;

    public Patient() {
        super();
        birthDate = null;
        description = "";
        join_date="";
    }

    public static Vector<Patient> getPatientList() {
        return patientList;
    }

    public static void setPatientList(Vector<Patient> patientList) {
        Patient.patientList = patientList;
    }

    public Patient(String firstName, String lastName, String cin, String phoneNumber, String birthDate, String description, String join_date) {
        super(firstName, lastName, cin, phoneNumber);
        this.birthDate = birthDate;
        this.description = description;
        this.join_date = join_date;
    }

    public Patient(int id, String firstName, String lastName, String cin, String phoneNumber, String birthDate, String description, String join_date) {
        this(firstName, lastName, cin, phoneNumber, birthDate, description, join_date);
        this.setId(id);
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    @Override
    public String toString() {
        return "Patient{" +super.toString()+
                "birthDate=" + birthDate +
                ", description='" + description + '\'' +
                ", joined= " + join_date +
                '}';
    }
}
