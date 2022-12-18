package com.example.docapp.models;

import java.util.Date;

public class Patient extends Personne {
    private Date birthDate;
    private String description;

    public Patient() {
        super();
        birthDate = null;
        description = "";
    }

    public Patient(int id, String firstName, String lastName, String cin, String phoneNumber, Date birthDate, String description) {
        super(id, firstName, lastName, cin, phoneNumber);
        this.birthDate = birthDate;
        this.description = description;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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
