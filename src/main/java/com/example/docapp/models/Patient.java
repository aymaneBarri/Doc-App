package com.example.docapp.models;

import java.sql.Date;
import java.util.Vector;

public class Patient extends Personne {
    private String birthDate;
    private String description;

    private static Vector<Patient> patientList ;

    public Patient() {
        super();
        birthDate = null;
        description = "";
    }


    public static Vector<Patient> getPatientList() {
        return patientList;
    }

    public static void setPatientList(Vector<Patient> patientList) {
        Patient.patientList = patientList;
    }

    public Patient(int id, String firstName, String lastName, String cin, String phoneNumber, String birthDate, String description) {
        super(id, firstName, lastName, cin, phoneNumber);
        this.birthDate = birthDate;
        this.description = description;
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

    @Override
    public String toString() {
        return "Patient{" +super.toString()+
                "birthDate=" + birthDate +
                ", description='" + description + '\'' +
                '}';
    }
}
