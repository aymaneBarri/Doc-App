package com.example.docapp.models;

import com.example.docapp.util.DBUtil;
import com.example.docapp.util.Encryptor;

import java.security.NoSuchAlgorithmException;

public class Utilisateur extends Personne {
    private String email;
    private String password;
    public static Utilisateur currentUser;

    public Utilisateur() {
        super();
        email = "";
        password = "";
    }

    public Utilisateur(int id, String firstName, String lastName, String email, String password, String cin, String phoneNumber) throws NoSuchAlgorithmException {
        super(id, firstName, lastName, cin, phoneNumber);
        this.email = email;
        this.password = Encryptor.encryptString(password);
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

    @Override
    public String toString() {
        return super.toString()+
                "Utilisateur{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
