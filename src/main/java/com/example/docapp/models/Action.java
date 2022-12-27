package com.example.docapp.models;

public class Action {
    private int id;
    private String action;
    private String actionTime;
    private int id_utilisateur;

    public Action(){
        id = 0;
        action = "";
        actionTime = "";
        id_utilisateur = 0;
    }

    public Action(int id, String action, String actionTime, int id_utilisateur) {
        this.id = id;
        this.action = action;
        this.actionTime = actionTime;
        this.id_utilisateur = id_utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }
}
