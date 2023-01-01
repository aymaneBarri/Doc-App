package com.example.docapp.models;

import java.util.Vector;

public class Utilisateur extends Personne {
    private String email;
    private String password;
    private int idRole;
    public static Utilisateur currentUser;
    public static Vector<Permission> currentPermissions = new Vector<Permission>();

    public Utilisateur() {
        super();
        email = "";
        password = "";
        idRole = 0;
    }

    public Utilisateur(String firstName, String lastName, String email, String password, String cin, String phoneNumber, int idRole) {
        super(firstName, lastName, cin, phoneNumber);
        this.email = email;
        this.password = password;
        this.idRole = idRole;
    }

    public Utilisateur(int id, String firstName, String lastName, String email, String password, String cin, String phoneNumber, int idRole) {
        this(firstName, lastName, email, password, cin, phoneNumber, idRole);
        this.setId(id);
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

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    @Override
    public String toString() {
        return super.toString()+
                "Utilisateur{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", id_role='" + idRole + '\'' +
                '}';
    }
}
