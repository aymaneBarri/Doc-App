package com.example.docapp.models;

public class Utilisateur extends Personne {
    private String email;
    private String password;

    public Utilisateur() {
        super();
        email = "";
        password = "";
    }

    public Utilisateur(int id, String firstName, String lastName, String email, String password, String cin, String phoneNumber) {
        super(id, firstName, lastName, cin, phoneNumber);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
