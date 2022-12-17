package com.example.docapp.models;

public class Role {
    private int id;
    private String subject;

    public Role() {
        this.id = 0;
        this.subject = "";
    }

    public Role(int id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
